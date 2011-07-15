package com.ibm.tivoli.cars.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ApplicationConfigHelper {

  private static Log log = LogFactory.getLog(ApplicationConfigHelper.class);

  public static class ApplicationAndJunction {
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return String.format("ApplicationAndJunction [matchedJunction=%s, application=%s]", matchedJunction, application);
    }

    private Application application = null;
    private String matchedJunction = null;

    public ApplicationAndJunction() {
      super();
    }

    public ApplicationAndJunction(Application application, String matchedJunction) {
      super();
      this.application = application;
      this.matchedJunction = matchedJunction;
    }

    /**
     * @return the application
     */
    public Application getApplication() {
      return application;
    }

    /**
     * @param application
     *          the application to set
     */
    public void setApplication(Application application) {
      this.application = application;
    }

    /**
     * @return the matchedJunction
     */
    public String getMatchedJunction() {
      return matchedJunction;
    }

    /**
     * @param matchedJunction
     *          the matchedJunction to set
     */
    public void setMatchedJunction(String matchedJunction) {
      this.matchedJunction = matchedJunction;
    }

  }

  private Map<String, Pattern> jmt = new LinkedHashMap<String, Pattern>();
  private List<Application> applications = new ArrayList<Application>();

  public ApplicationConfigHelper() {
    super();
  }

  public ApplicationConfigHelper(InputStream jmtConfig, InputStream applicationConfig) throws IOException {
    loadConfig(jmtConfig, applicationConfig);
  }

  public ApplicationAndJunction getMatchedApplication(String requestUrl) {
    if (requestUrl == null) {
      return null;
    }
    // 直接查询Junction
    ApplicationAndJunction result = findFromApplicationJunctions(requestUrl);
    if (result == null) {
      // 查询JMT
      for (String junction : jmt.keySet()) {
        Pattern pattern = jmt.get(junction);
        Matcher m = pattern.matcher(requestUrl);
        if (m.matches()) {
          result = findFromApplicationJunctions(junction + requestUrl);
          if (result != null) {
            break;
          }
        }
      }
    }
    return result;
  }

  private ApplicationAndJunction findFromApplicationJunctions(String requestUrl) {
    for (Application app : applications) {
      for (String junction : app.getJunctions()) {
        if (requestUrl.equals(junction) || requestUrl.startsWith(junction + "/") || junction.equals("/") && requestUrl.startsWith(junction)) {
          return new ApplicationAndJunction(app, junction);
        }
      }
    }
    return null;
  }

  public Action getMatchedAction(ApplicationAndJunction appAndJunction, String requestUrl) {
    if (appAndJunction == null) {
      return null;
    }
    Application app = appAndJunction.getApplication();
    if (app == null) {
      return null;
    }
    if (!requestUrl.startsWith(appAndJunction.getMatchedJunction())) {
      requestUrl = appAndJunction.getMatchedJunction() + requestUrl;
    }
    if (app != null) {
      for (Action action : app.getActions()) {
        for (String actionUrl : action.getUrlPatterns()) {
          if (requestUrl.equals(actionUrl)) {
            return action;
          } else {
            Pattern p = Pattern.compile(convertToRegexp(actionUrl));
            Matcher m = p.matcher(requestUrl);
            if (m.matches()) {
              return action;
            }
          }
        }
      }
    }
    return null;
  }

  private String convertToRegexp(String actionUrl) {
    String result = actionUrl;
    result = StringUtils.replace(result, ".", "\\.");
    result = StringUtils.replace(result, "*", ".*");
    return result;
  }

  private void loadConfig(InputStream jmtConfig, InputStream applicationConfig) throws IOException {
    List<Application> apps = (List<Application>) this.getXStream().fromXML(applicationConfig);
    this.applications = apps;

    // Load JMT
    Map<String, Pattern> map = new LinkedHashMap<String, Pattern>();
    BufferedReader reader = new BufferedReader(new InputStreamReader(jmtConfig));
    String line = reader.readLine();
    while (line != null) {
      if (line.trim().length() > 0) {
        String[] fields = StringUtils.split(line, " \t");
        if (fields != null && fields.length == 2) {
          Pattern pattern = Pattern.compile(convertToRegexp(fields[1].trim()));
          map.put(fields[0].trim(), pattern);
          log.info("Load JMT item: [" + fields[0] + "\t" + pattern.toString() + "]");
        }
      }
      line = reader.readLine();
    }
    reader.close();
    this.jmt = map;
  }

  private XStream getXStream() {
    XStream xs = new XStream(new DomDriver("UTF-8"));
    xs.alias("Application", Application.class);
    xs.alias("Action", Action.class);
    return xs;
  }

  /**
   * @return the jmt
   */
  public Map<String, Pattern> getJmt() {
    return jmt;
  }

  /**
   * @param jmt
   *          the jmt to set
   */
  public void setJmt(Map<String, Pattern> jmt) {
    this.jmt = jmt;
  }

  /**
   * @return the applications
   */
  public List<Application> getApplications() {
    return applications;
  }

  /**
   * @param applications
   *          the applications to set
   */
  public void setApplications(List<Application> applications) {
    this.applications = applications;
  }

}

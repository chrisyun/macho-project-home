/**
 * 
 */
package com.ibm.tivoli.icbc.result;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Record;

import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.LookupEntity;
import com.ibm.tivoli.icbc.result.dns.a.AItem;
import com.ibm.tivoli.icbc.result.dns.a.DNSResult4A;
import com.ibm.tivoli.icbc.result.dns.cname.DNSCNameResult;
import com.ibm.tivoli.icbc.result.dns.ns.DNSResult4NS;
import com.ibm.tivoli.icbc.result.dns.ns.Domain;
import com.ibm.tivoli.icbc.result.dns.ns.NameServer;
import com.ibm.tivoli.icbc.result.http.HttpResult;
import com.ibm.tivoli.icbc.result.http.TargetURL;
import com.ibm.tivoli.icbc.result.http.URLAccessResult;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ResultFormaterV1 implements ResultFormater {

  /**
   * 
   */
  public ResultFormaterV1() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.result.ResultFormater#format(java.util.Date, java.util.Collection, java.io.OutputStream, java.lang.String)
   */
  public void format(Date timestamp, Collection<Result> results, OutputStream out, String charset) throws ProbeException {
    format(timestamp, results.toArray(new Result[] {}), out, charset);

  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.result.ResultFormater#format(java.util.Date, java.util.Collection, java.io.Writer)
   */
  public void format(Date timestamp, Collection<Result> results, Writer writer) throws ProbeException {
    format(timestamp, results.toArray(new Result[] {}), writer);

  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.result.ResultFormater#format(java.util.Date, com.ibm.tivoli.icbc.result.Result[], java.io.OutputStream, java.lang.String)
   */
  public void format(Date timestamp, Result[] results, OutputStream out, String charset) throws ProbeException {
    Writer writer;
    try {
      writer = new OutputStreamWriter(out, charset);
    } catch (UnsupportedEncodingException e) {
      throw new ProbeException("Failure to output result", e);
    }
    format(timestamp, results, writer);

  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.result.ResultFormater#format(java.util.Date, com.ibm.tivoli.icbc.result.Result[], java.io.Writer)
   */
  public void format(Date timestamp, Result[] results, Writer writer) throws ProbeException {
    try {
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      writer.write("<dataresult>\n");
      writer.write("    <ctime>" + timestamp.getTime() / 1000 + "</ctime>\n");

      Set<Result> sortedResults = new TreeSet<Result>();
      sortedResults.addAll(Arrays.asList(results));
      for (Result result : sortedResults) {
        if (result instanceof HttpResult) {
          format(writer, (HttpResult) result);
        } else if (result instanceof DNSCNameResult) {
          format(writer, (DNSCNameResult) result);
        } else if (result instanceof DNSResult4NS) {
          format(writer, (DNSResult4NS) result);
        } else if (result instanceof DNSResult4A) {
          format(writer, (DNSResult4A) result);
        }
      }
      writer.write("</dataresult>\n");
    } catch (IOException e) {
      throw new ProbeException("Failure to output result", e);
    }
  }

  /**
   * Output ICBC XML Message format
   * 
   * @param writer
   * @param httpResult
   * @throws IOException
   */
  private void format(Writer writer, HttpResult httpResult) throws IOException {
    List<TargetURL> urls = httpResult.getTargetURLs();
    for (int j = 0; urls != null && j < urls.size(); j++) {
      TargetURL targetURL = urls.get(j);
      if (targetURL.getError() != null) {
        writer.write("    <resultcontext>\n");
        writer.write("        <ctime>" + httpResult.getTimestamp().getTime() / 1000 + "</ctime>\n");
        writer.write("        <type>" + httpResult.getTaskType() + "</type>\n");
        writer.write("        <btype>" + httpResult.getTaskBtype() + "</btype>\n");
        writer.write("        <businessId>" + httpResult.getTaskId() + "</businessId>\n");
        writer.write("        <name>" + httpResult.getTaskName() + "</name>\n");
        writer.write("        <request>" + targetURL.getUrl() + "</request>\n");
        writer.write("        <result1>fail</result1>\n");
        writer.write("        <result2>" + targetURL.getError() + "</result2>\n");
        writer.write("    </resultcontext>\n");
      } else {
        List<URLAccessResult> accesses = targetURL.getAccesses();
        for (int k = 0; accesses != null && k < accesses.size(); k++) {
          URLAccessResult record = accesses.get(k);
          writer.write("    <resultcontext>\n");
          writer.write("        <ctime>" + httpResult.getTimestamp().getTime() / 1000 + "</ctime>\n");
          writer.write("        <type>" + httpResult.getTaskType() + "</type>\n");
          writer.write("        <btype>" + httpResult.getTaskBtype() + "</btype>\n");
          writer.write("        <businessId>" + httpResult.getTaskId() + "</businessId>\n");
          writer.write("        <name>" + httpResult.getTaskName() + "</name>\n");
          writer.write("        <request>" + targetURL.getUrl() + "</request>\n");
          writer.write("        <result1>" + record.getDnsTime() + "</result1>\n");
          writer.write("        <result2>" + record.getNetTime() + "</result2>\n");
          writer.write("        <result3>" + record.getDownloadTime() + "</result3>\n");
          writer.write("        <result4>" + record.getHttpCode() + "</result4>\n");
          writer.write("        <result5>" + record.getDownloadSize() + "</result5>\n");
          writer.write("        <result6>" + record.getUrl() + "</result6>\n");
          writer.write("    </resultcontext>\n");
        }
      }
    }
  }

  /**
   * Output ICBC XML Message format
   * 
   * @param writer
   * @param result
   * @throws IOException
   */
  private void format(Writer writer, DNSCNameResult result) throws IOException {
    List<LookupEntity> entities = result.getLookupEntities();
    for (int j = 0; entities != null && j < entities.size(); j++) {
      LookupEntity lookupEntity = entities.get(j);
      String targetName = lookupEntity.getLookupTarget();
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + lookupEntity.getBeginTime().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>DNS_CNAME</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      writer.write("        <name>" + result.getTaskName() + "</name>\n");
      writer.write("        <request>" + targetName + "</request>\n");

      if (!StringUtils.isEmpty(lookupEntity.getError())) {
        writer.write("        <result1>fail</result1>\n");
        writer.write("        <result2>" + lookupEntity.getError() + "</result2>\n");
        writer.write("        <result3></result3>\n");
      } else {
        String ip = "";
        String firstCName = "";
        String secondCName = "";
        List<Record> rs = lookupEntity.getResult();
        for (Record r : rs) {
          if (r instanceof CNAMERecord) {
            firstCName = ((CNAMERecord) r).getAlias().toString();

            LookupEntity entityL2 = lookupEntity.getNextLookups().get(r);
            if (entityL2 != null) {

              for (Record r2 : entityL2.getResult()) {
                if (r2 instanceof CNAMERecord) {
                  secondCName = ((CNAMERecord) r2).getAlias().toString();
                }
              }
            }
          }
        }

        boolean quit = false;
        while (lookupEntity != null && !quit && rs != null && rs.size() > 0) {
          for (Record r : rs) {
            if (r instanceof ARecord) {
              ip += ((ARecord) r).getAddress().getHostAddress() + ",";
              quit = true;
            } else {
              lookupEntity = lookupEntity.getNextLookups().get(r);
              break;
            }
          }
        }

        writer.write("        <result1>" + firstCName + "</result1>\n");
        writer.write("        <result2>" + secondCName + "</result2>\n");
        writer.write("        <result3>" + StringUtils.removeEnd(ip, ",") + "</result3>\n");
      }
      writer.write("    </resultcontext>\n");
    }
  }

  /**
   * Output ICBC XML Message format
   * 
   * @param writer
   * @param result
   * @throws IOException
   */
  private void format(Writer writer, DNSResult4NS result) throws IOException {
    List<Domain> entities = result.getDomains();
    for (int j = 0; entities != null && j < entities.size(); j++) {
      Domain domain = entities.get(j);
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + domain.getTimeStamp().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>DNS_NS</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      writer.write("        <name>" + result.getTaskName() + "</name>\n");
      writer.write("        <request>" + domain.getName() + "</request>\n");

      if (!StringUtils.isEmpty(domain.getError())) {
        writer.write("        <result1>fail</result1>\n");
        writer.write("        <result2>" + domain.getError() + "</result2>\n");
      } else {
        List<NameServer> nsServers = domain.getNameServers();
        String ip = "";
        for (NameServer nameServer : nsServers) {
          for (String iAdd : nameServer.getInetAddresses()) {
            ip += iAdd + ",";
          }
        }

        writer.write("        <result1>" + StringUtils.removeEnd(StringUtils.replace(ip, "/", ""), ",") + "</result1>\n");
      }
      writer.write("    </resultcontext>\n");
    }
  }

  /**
   * Output ICBC XML Message format
   * 
   * @param writer
   * @param result
   * @throws IOException
   */
  private void format(Writer writer, DNSResult4A result) throws IOException {
    
    if (!StringUtils.isEmpty(result.getError())) {
       writer.write("    <resultcontext>\n");
       writer.write("        <ctime>" + result.getBeginTime().getTime() / 1000 + "</ctime>\n");
       writer.write("        <type>DNS_A</type>\n");
       writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
       writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
       writer.write("        <name>" + result.getTaskName() + "</name>\n");
       writer.write("        <request>fail</request>\n");
       writer.write("        <result1>" + result.getError() + "</result1>\n");
       writer.write("    </resultcontext>\n");
       return;
    }
    
    List<AItem> entities = result.getAitems();
    for (int j = 0; entities != null && j < entities.size(); j++) {
      AItem aitem = entities.get(j);
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + result.getBeginTime().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>DNS_A</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      writer.write("        <name>" + result.getTaskName() + "</name>\n");
      writer.write("        <request>" + aitem.getTargetHostname() + "</request>\n");
      writer.write("        <result1>" + aitem.getServerIPAddress() + "</result1>\n");
      writer.write("        <result2>" + aitem.getTargetHostIPAddress() + "</result2>\n");
      writer.write("    </resultcontext>\n");
    }
  }
}

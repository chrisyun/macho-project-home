package com.ibm.tivoli.icbc.probe.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

public class SchedulePatternMatcher {

  private Set<String> times = new TreeSet<String>();

  public SchedulePatternMatcher(String pattern) {
    super();
    expandPattern(pattern);
  }

  private void expandPattern(String pattern) {
    if (pattern == null) {
      return;
    }
    if (!pattern.startsWith("schedule:[")) {
      return;
    }
    String p = pattern.substring("schedule:[".length());
    p = p.substring(0, p.length() - 1);

    String[] pp = StringUtils.split(p, ',');
    for (String s : pp) {
      if (s.indexOf('*') < 0) {
        this.times.add(s.trim());
      } else {
        String h = s.substring(0, s.indexOf(':'));
        List<String> allH = new ArrayList<String>();
        {
          if (h.indexOf('*') < 0) {
            allH.add(h.trim());
          } else {
            if (h.length() == 1) {
              for (int i = 0; i < 24; i++) {
                allH.add((i < 10) ? "0" + i : "" + i);
              }
            } else {
              for (int i = 0; i < 10; i++) {
                String t = StringUtils.replace(h, "*", "" + i);
                if (Integer.parseInt(t) < 24) {
                  allH.add(t.trim());
                }
              }
            }
          }
        }
        String m = s.substring(s.indexOf(':') + 1, s.length());
        List<String> allM = new ArrayList<String>();
        {
          if (m.indexOf('*') < 0) {
            allM.add(m.trim());
          } else {
            if (m.length() == 1) {
              for (int i = 0; i < 60; i++) {
                allM.add((i < 10) ? "0" + i : "" + i);
              }
            } else {
              for (int i = 0; i < 10; i++) {
                String t = StringUtils.replace(m, "*", "" + i);
                if (Integer.parseInt(t) < 60) {
                  allM.add(m.trim());
                }
              }
            }
          }
        }
        for (String i: allH) {
            for (String j: allM) {
                this.times.add((i + ":" + j).trim());
            }
        }
      }
    }
  }

  public boolean match(Date lastTime, Date currentTime) {
    DateFormat formatter = new SimpleDateFormat("HH:mm");
    String lt = formatter.format(lastTime);
    String ct = formatter.format(currentTime);
    for (String time : times) {
      if (time.compareTo(lt) >= 0 && time.compareTo(ct) <= 0) {
        return true;
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return "SchedulePatternMatcher [times=" + (times != null ? toString(times, maxLen) : null) + "]";
  }

  private String toString(Collection<?> collection, int maxLen) {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    int i = 0;
    for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
      if (i > 0)
        builder.append(", ");
      builder.append(iterator.next());
    }
    builder.append("]");
    return builder.toString();
  }

}

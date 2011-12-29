/**
 * 
 */
package com.ibm.tivoli.icbc.result;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Record;

import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.LookupEntity;
import com.ibm.tivoli.icbc.result.dns.a.AItem;
import com.ibm.tivoli.icbc.result.dns.a.DNSNativeAResult;
import com.ibm.tivoli.icbc.result.dns.a.DNSResult4A;
import com.ibm.tivoli.icbc.result.dns.cname.DNSCNameResult;
import com.ibm.tivoli.icbc.result.dns.ns.DNSResult4NS;
import com.ibm.tivoli.icbc.result.dns.ns.Domain;
import com.ibm.tivoli.icbc.result.dns.ns.NameServer;
import com.ibm.tivoli.icbc.result.http.HttpResult;
import com.ibm.tivoli.icbc.result.http.IEBrowserResult;
import com.ibm.tivoli.icbc.result.http.PageElementItem;
import com.ibm.tivoli.icbc.result.http.PageElementResult;
import com.ibm.tivoli.icbc.result.http.TargetURL;
import com.ibm.tivoli.icbc.result.http.URLAccessResult;
import com.ibm.tivoli.icbc.result.icmp.ICMPResult;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ResultFormaterV2 implements ResultFormater {

  /**
   * 
   */
  public ResultFormaterV2() {
    super();
  }

  public void format(Date timestamp, Collection<Result> results, OutputStream out, String charset) throws ProbeException {
    format(timestamp, results.toArray(new Result[] {}), out, charset);

  }

  public void format(Date timestamp, Collection<Result> results, Writer writer) throws ProbeException {
    format(timestamp, results.toArray(new Result[] {}), writer);

  }

  public void format(Date timestamp, Result[] results, OutputStream out, String charset) throws ProbeException {
    Writer writer;
    try {
      writer = new OutputStreamWriter(out, charset);
    } catch (UnsupportedEncodingException e) {
      throw new ProbeException("Failure to output result", e);
    }
    format(timestamp, results, writer);

  }

  /**
   * Output to ICBC XML format
   * 
   * @param timestamp
   * @param results
   * @param writer
   * @throws ProbeException
   */
  public void format(Date timestamp, Result[] results, Writer writer) throws ProbeException {
    try {

      // writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      writer.write("<dataresult>\n");
      writer.write("    <ctime>" + timestamp.getTime() / 1000 + "</ctime>\n");
      writer.write("    <version>2.0</version>\n");

      // Sorting
      Arrays.sort(results, new Comparator<Result>() {
        public int compare(Result o1, Result o2) {
          if (o2 == null && o1 == null) {
            return 0;
          }
          if (o1 == null) {
            return -1;
          }
          if (o2 == null) {
            return 1;
          }

          return o1.getIndex() - o2.getIndex();
        }
      });
      // List<Result> sortedResults = new java.util.TreeSet<Result>();
      // sortedResults.addAll(Arrays.asList(results));
      for (Result result : results) {
        if (result instanceof HttpResult) {
          format(writer, (HttpResult) result);
        } else if (result instanceof DNSCNameResult) {
          formatV2(writer, (DNSCNameResult) result);
        } else if (result instanceof DNSResult4NS) {
          format(writer, (DNSResult4NS) result);
        } else if (result instanceof DNSResult4A) {
          format(writer, (DNSResult4A) result);
        } else if (result instanceof ICMPResult) {
          format(writer, (ICMPResult) result);
        } else if (result instanceof DNSNativeAResult) {
          format(writer, (DNSNativeAResult) result);
        } else if (result instanceof IEBrowserResult) {
          format(writer, (IEBrowserResult) result);
        }
      }
      writer.write("</dataresult>\n");
    } catch (IOException e) {
      throw new ProbeException("Failure to output result", e);
    }
  }

  private void format(Writer writer, IEBrowserResult result) throws IOException {
    List<TargetURL> urls = result.getTargetURLs();
    for (int j = 0; urls != null && j < urls.size(); j++) {
      TargetURL targetURL = urls.get(j);
      if (targetURL.getError() != null) {
        writer.write("    <resultcontext>\n");
        writer.write("        <ctime>" + result.getTimestamp().getTime() / 1000 + "</ctime>\n");
        writer.write("        <type>" + result.getTaskType() + "</type>\n");
        writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
        if (result.getTaskId() != null) {
          writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
        }
        if (result.getTaskName() != null) {
          writer.write("        <name>" + result.getTaskName() + "</name>\n");
        }
        writer.write("        <request>" + targetURL.getUrl() + "</request>\n");
        writer.write("        <result1>fail</result1>\n");
        if (!StringUtils.isEmpty(targetURL.getError())) {
          writer.write("        <result2><![CDATA[" + targetURL.getError() + "]]></result2>\n");
        }
        writer.write("    </resultcontext>\n");
      } else {
        List<URLAccessResult> accesses = targetURL.getAccesses();
        // Only need 1 record
        for (int k = 0; accesses != null && k < accesses.size() && k < 1; k++) {
          URLAccessResult record = accesses.get(k);
          writer.write("    <resultcontext ver=\"2.0\">\n");
          writer.write("        <ctime>" + result.getTimestamp().getTime() / 1000 + "</ctime>\n");
          writer.write("        <type>" + result.getTaskType() + "</type>\n");
          writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
          if (result.getTaskId() != null) {
            writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
          }
          if (result.getTaskName() != null) {
            writer.write("        <name>" + result.getTaskName() + "</name>\n");
          }
          writer.write("        <request><![CDATA[" + targetURL.getUrl() + "]]></request>\n");
          writer.write("        <result1>" + record.getDnsTime() + "</result1>\n");
          writer.write("        <result2></result2>\n");
          writer.write("        <result3>" + record.getDownloadTime() + "</result3>\n");
          writer.write("        <result4>" + ((record.getHttpCode() == null)?"":record.getHttpCode()) + "</result4>\n");
          writer.write("        <result5>" + record.getDownloadElements() + "</result5>\n");

          // Size TopN
          PageElementResult per = record.getPageElementResult();
          if (per != null && per.getTopN4Size() != null) {
             writer.write("        <result6>\n");
             writer.write("        <![CDATA[\n");
             int maxTopNItem = result.getMaxTopNItem();
             for (PageElementItem e: per.getTopN4Size()) {
                 writer.write("          <record>\n");
                 writer.write("            <url>" + e.getUrl() + "</url>\n");
                 writer.write("            <!-- size in bytes -->\n");
                 writer.write("            <value>" + e.getDownloadSize() + "</value>\n");
                 writer.write("          </record>\n");
                 maxTopNItem--;
                 if (maxTopNItem <= 0) {
                    break;
                 }
             }
             writer.write("        ]]>\n");
             writer.write("        </result6>\n");
          }
          
          if (per != null && per.getTopN4Slow() != null) {
            writer.write("        <result7>\n");
            writer.write("        <![CDATA[\n");
            int maxTopNItem = result.getMaxTopNItem();
            for (PageElementItem e: per.getTopN4Slow()) {
                writer.write("          <record>\n");
                writer.write("            <url>" + e.getUrl() + "</url>\n");
                writer.write("            <!-- elapse time in ms -->\n");
                writer.write("            <value>" + e.getDownloadElapseTime() + "</value>\n");
                writer.write("          </record>\n");
                maxTopNItem--;
                if (maxTopNItem <= 0) {
                  break;
                }
            }
            writer.write("        ]]>\n");
            writer.write("        </result7>\n");
          }

          String r = "";
          if (!StringUtils.isEmpty(record.getImageFile())) {
            File imgFile = new File(record.getImageFile());
            byte[] img = FileUtils.readFileToByteArray(imgFile);
            Base64 base64 = new Base64();
            r = new String(base64.encode(img));

            //imgFile.delete();
          }
          writer.write("        <blobResult1>" + r + "</blobResult1>\n");
          writer.write("    </resultcontext>\n");
        }
      }
    }
  }

  private void format(Writer writer, DNSNativeAResult result) throws IOException {
    String target = result.getTarget();
    if (result.getError() != null) {
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + result.getTimestamp().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>" + result.getTaskType() + "</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      if (result.getTaskId() != null) {
        writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      }
      if (result.getTaskName() != null) {
        writer.write("        <name>" + result.getTaskName() + "</name>\n");
      }
      writer.write("        <request>" + target + "</request>\n");
      writer.write("        <result1>fail</result1>\n");
      writer.write("        <result2>" + result.getError() + "</result2>\n");
      writer.write("    </resultcontext>\n");
    } else {
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + result.getTimestamp().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>" + result.getTaskType() + "</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      if (result.getTaskId() != null) {
        writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      }
      if (result.getTaskName() != null) {
        writer.write("        <name>" + result.getTaskName() + "</name>\n");
      }
      writer.write("        <request>" + target + "</request>\n");
      writer.write("        <result1>" + result.getElapseTime() + "</result1>\n");
      writer.write("        <result2>" + result.getIpAddress() + "</result2>\n");
      writer.write("    </resultcontext>\n");
    }
  }

  private void format(Writer writer, ICMPResult result) throws IOException {
    String target = result.getTarget();
    if (result.getFailMsg() != null) {
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + result.getTimestamp().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>" + result.getTaskType() + "</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      if (result.getTaskId() != null) {
        writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      }
      if (result.getTaskName() != null) {
        writer.write("        <name>" + result.getTaskName() + "</name>\n");
      }
      writer.write("        <request>" + target + "</request>\n");
      writer.write("        <result1></result1>\n");
      writer.write("        <result2></result2>\n");
      writer.write("        <result3></result3>\n");
      writer.write("        <result4>100</result4>\n");
      writer.write("    </resultcontext>\n");
    } else {
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + result.getTimestamp().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>" + result.getTaskType() + "</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      if (result.getTaskId() != null) {
        writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      }
      if (result.getTaskName() != null) {
        writer.write("        <name>" + result.getTaskName() + "</name>\n");
      }
      writer.write("        <request>" + target + "</request>\n");
      writer.write("        <result1>" + result.getMax() + "</result1>\n");
      writer.write("        <result2>" + result.getMin() + "</result2>\n");
      writer.write("        <result3>" + result.getAvg() + "</result3>\n");
      writer.write("        <result4>" + result.getLostPercent() + "</result4>\n");
      writer.write("    </resultcontext>\n");
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
        if (httpResult.getTaskId() != null) {
          writer.write("        <businessId>" + httpResult.getTaskId() + "</businessId>\n");
        }
        if (httpResult.getTaskName() != null) {
          writer.write("        <name>" + httpResult.getTaskName() + "</name>\n");
        }
        writer.write("        <request>" + targetURL.getUrl() + "</request>\n");
        writer.write("        <result1>fail</result1>\n");
        if (!StringUtils.isEmpty(targetURL.getError())) {
          writer.write("        <result2><![CDATA[" + targetURL.getError() + "]]></result2>\n");
        }
        writer.write("    </resultcontext>\n");
      } else {
        List<URLAccessResult> accesses = targetURL.getAccesses();
        // Only need 1 record
        for (int k = 0; accesses != null && k < accesses.size() && k < 1; k++) {
          URLAccessResult record = accesses.get(k);
          writer.write("    <resultcontext ver=\"2.0\">\n");
          writer.write("        <ctime>" + httpResult.getTimestamp().getTime() / 1000 + "</ctime>\n");
          writer.write("        <type>" + httpResult.getTaskType() + "</type>\n");
          writer.write("        <btype>" + httpResult.getTaskBtype() + "</btype>\n");
          if (httpResult.getTaskId() != null) {
            writer.write("        <businessId>" + httpResult.getTaskId() + "</businessId>\n");
          }
          if (httpResult.getTaskName() != null) {
            writer.write("        <name>" + httpResult.getTaskName() + "</name>\n");
          }
          writer.write("        <request><![CDATA[" + targetURL.getUrl() + "]]></request>\n");
          writer.write("        <result1>" + record.getDnsTime() + "</result1>\n");
          writer.write("        <result2>" + record.getNetTime() + "</result2>\n");
          writer.write("        <result3>" + record.getDownloadTime() + "</result3>\n");
          writer.write("        <result4>" + record.getHttpCode() + "</result4>\n");
          writer.write("        <result5>0</result5>\n");
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
      writer.write("        <type>DNS-C</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      if (result.getTaskId() != null) {
        writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      }
      if (result.getTaskName() != null) {
        writer.write("        <name>" + result.getTaskName() + "</name>\n");
      }
      writer.write("        <request>" + targetName + "</request>\n");

      if (!StringUtils.isEmpty(lookupEntity.getError())) {
        writer.write("        <result1>fail</result1>\n");
        writer.write("        <result2>" + lookupEntity.getError() + "</result2>\n");
        // writer.write("        <result3></result3>\n");
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
              rs = lookupEntity.getResult();
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
  private void formatV2(Writer writer, DNSCNameResult result) throws IOException {
    List<LookupEntity> entities = result.getLookupEntities();
    for (int j = 0; entities != null && j < entities.size(); j++) {
      LookupEntity lookupEntity = entities.get(j);
      String targetName = lookupEntity.getLookupTarget();
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + lookupEntity.getBeginTime().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>DNS-C</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      if (result.getTaskId() != null) {
        writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      }
      if (result.getTaskName() != null) {
        writer.write("        <name>" + result.getTaskName() + "</name>\n");
      }
      writer.write("        <request>" + targetName + "</request>\n");

      if (!StringUtils.isEmpty(lookupEntity.getError())) {
        writer.write("        <result1>fail</result1>\n");
        writer.write("        <result2>" + lookupEntity.getError() + "</result2>\n");
        // writer.write("        <result3></result3>\n");
      } else {
        String ip = "";
        String firstCName = "";
        String secondCName = "";
        List<Record> rs = lookupEntity.getResult();
        if (rs.size() > 2 && rs.get(0) instanceof CNAMERecord) {
          firstCName = ((CNAMERecord) rs.get(0)).getTarget().toString();
        }
        if (rs.size() > 3 && rs.get(1) instanceof CNAMERecord) {
          secondCName = ((CNAMERecord) rs.get(1)).getTarget().toString();
        }
        if (rs.size() > 0 && rs.get(rs.size() - 1) instanceof ARecord) {
          ip = ((ARecord) rs.get(rs.size() - 1)).getAddress().getHostAddress();
        }

        writer.write("        <result1>" + firstCName + "</result1>\n");
        writer.write("        <result2>" + secondCName + "</result2>\n");
        writer.write("        <result3>" + ip + "</result3>\n");
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
      writer.write("        <type>DNS-NS</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      if (result.getTaskId() != null) {
        writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      }
      if (result.getTaskName() != null) {
        writer.write("        <name>" + result.getTaskName() + "</name>\n");
      }
      writer.write("        <request>" + domain.getName() + "</request>\n");

      if (!StringUtils.isEmpty(domain.getError())) {
        writer.write("        <result1>fail</result1>\n");
        if (!StringUtils.isEmpty(domain.getError())) {
          writer.write("        <result2>" + domain.getError() + "</result2>\n");
        }
      } else {
        List<NameServer> nsServers = domain.getNameServers();
        String ip = "";
        int index = 1;
        for (NameServer nameServer : nsServers) {
          for (String iAdd : nameServer.getInetAddresses()) {
            writer.write("        <result" + index + ">" + iAdd + "</result" + index + ">\n");
            index++;
          }
        }
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

    List<AItem> entities = result.getAitems();
    if (!StringUtils.isEmpty(result.getError()) || entities == null || entities.size() == 0) {
      writer.write("    <resultcontext>\n");
      writer.write("        <ctime>" + result.getBeginTime().getTime() / 1000 + "</ctime>\n");
      writer.write("        <type>DNS-A</type>\n");
      writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
      if (result.getTaskId() != null) {
        writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
      }
      if (result.getTaskName() != null) {
        writer.write("        <name>" + result.getTaskName() + "</name>\n");
      }
      writer.write("        <request>" + result.getTarget() + "</request>\n");
      writer.write("        <result1>fail</result1>\n");
      if (result.getError() != null) {
        writer.write("        <result2>" + result.getError() + "</result2>\n");
      }
      writer.write("    </resultcontext>\n");
      return;
    }

    writer.write("    <resultcontext>\n");
    writer.write("        <ctime>" + result.getBeginTime().getTime() / 1000 + "</ctime>\n");
    writer.write("        <type>DNS-A</type>\n");
    writer.write("        <btype>" + result.getTaskBtype() + "</btype>\n");
    if (result.getTaskId() != null) {
      writer.write("        <businessId>" + result.getTaskId() + "</businessId>\n");
    }
    if (result.getTaskName() != null) {
      writer.write("        <name>" + result.getTaskName() + "</name>\n");
    }

    for (int j = 0; entities != null && j < entities.size(); j++) {
      AItem aitem = entities.get(j);
      if (j == 0) {
        writer.write("        <request>" + aitem.getTargetHostname() + "</request>\n");
      }
      if (StringUtils.isEmpty(aitem.getError())) {
        writer.write("        <result" + (j + 1) + ">" + aitem.getTargetHostIPAddress() + "</result" + (j + 1) + ">\n");
      } else {
        writer.write("        <result" + (j + 1) + ">fail</result" + (j + 1) + ">\n");
      }
    }
    writer.write("    </resultcontext>\n");
  }
}

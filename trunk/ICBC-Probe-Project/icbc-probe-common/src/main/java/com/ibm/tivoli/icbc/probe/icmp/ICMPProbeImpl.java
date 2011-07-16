/**
 * 
 */
package com.ibm.tivoli.icbc.probe.icmp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.helpers.IOUtils;

import com.ibm.tivoli.icbc.probe.ICMPProbe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.icmp.ICMPResult;

/**
 * @author zhaodonglu
 *
 */
public class ICMPProbeImpl implements ICMPProbe {
  
  private static Log log = LogFactory.getLog(ICMPProbeImpl.class);

  private String target = null;
  private int totalPacket = 10;
  private int timeout = 0;
  
  /**
   * 
   */
  public ICMPProbeImpl() {
    super();
  }

  /**
   * @return the target
   */
  public String getTarget() {
    return target;
  }

  /**
   * @param target the target to set
   */
  public void setTarget(String target) {
    this.target = target;
  }

  /**
   * @return the totalPacket
   */
  public int getTotalPacket() {
    return totalPacket;
  }

  /**
   * @param totalPacket the totalPacket to set
   */
  public void setTotalPacket(int totalPacket) {
    this.totalPacket = totalPacket;
  }

  /**
   * @return the timeout
   */
  public int getTimeout() {
    return timeout;
  }

  /**
   * @param timeout the timeout to set
   */
  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

  @Override
  public ICMPResult run() throws ProbeException {
    ICMPResult result = new ICMPResult();
    result.setTarget(this.target);
    try {
      InetAddress ip = InetAddress.getByName(this.target);
      
      long beginTime = System.currentTimeMillis();
      Runtime rt = Runtime.getRuntime();
      String cmd = "ping ";
      if (this.totalPacket > 0) {
         cmd += " -n " + this.totalPacket;
      }
      if (this.timeout > 0) {
         cmd += " -w " + this.timeout;
      }
      Process proc = rt.exec(cmd + " " + ip.getHostAddress());
      String content = new String(IOUtils.readBytesFromStream(proc.getInputStream()),"GBK");
      log.info(content);
      
      int exitVal = proc.exitValue();

      if (content != null && content.indexOf("最短 = ") > 0) {
        parse4Win7(result, content);
      } else {
        parse4WinXP(result, content);
      }

    } catch (UnknownHostException e) {
      log.error("fail ping target server: " + this.target + ", cause: " + e.getCause(), e);
      result.setFailMsg("fail ping target server: " + this.target + ", cause: " + e.getCause());
    } catch (IOException e) {
      log.error("fail ping target server: " + this.target + ", cause: " + e.getCause(), e);
      result.setFailMsg("fail ping target server: " + this.target + ", cause: " + e.getCause());
    }
    return result;
  }

  private void parse4WinXP(ICMPResult result, String content) {
    String minStr = extractStr(content, "Minimum =", "ms,");
    String maxStr = extractStr(content, "Maximum =", "ms,");
    String avgStr = extractStr(content, "Average =", "ms");
    String lostStr = extractStr(content, "Lost =", ",");
    if (lostStr != null) {
       lostStr = extractStr(lostStr, "(", "% loss");
    }
    if (minStr != null) {
      result.setMin(Long.parseLong(minStr.trim()));
    }
    if (maxStr != null) {
      result.setMax(Long.parseLong(maxStr.trim()));
    }
    if (avgStr != null) {
      result.setAvg(Long.parseLong(avgStr.trim()));
    }
    if (lostStr != null) {
      result.setLostPercent(Integer.parseInt(lostStr.trim()));
    }
  }

  private void parse4Win7(ICMPResult result, String content) {
    String minStr = extractStr(content, "最短 = ", "ms，");
    String maxStr = extractStr(content, "最长 = ", "ms，");
    String avgStr = extractStr(content, "平均 = ", "ms");
    String lostStr = extractStr(content, "丢失 = ", "，");
    if (lostStr != null) {
       lostStr = extractStr(lostStr, "(", "% 丢失");
    }
    if (minStr != null) {
      result.setMin(Long.parseLong(minStr.trim()));
    }
    if (maxStr != null) {
      result.setMax(Long.parseLong(maxStr.trim()));
    }
    if (avgStr != null) {
      result.setAvg(Long.parseLong(avgStr.trim()));
    }
    if (lostStr != null) {
      result.setLostPercent(Integer.parseInt(lostStr.trim()));
    }
  }
  
  private String extractStr(String src, String begin, String end) {
    int k = src.indexOf(begin);
    if (k < 0) {
       return null;
    }
    int m = src.indexOf(end, k + begin.length());
    if (m < 0) {
      return null;
    }
    return src.substring(k + begin.length(), m);
  }

}

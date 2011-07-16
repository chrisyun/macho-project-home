package com.ibm.tivoli.icbc.probe.icmp;

import com.ibm.tivoli.icbc.result.icmp.ICMPResult;

import junit.framework.TestCase;

public class ICMPProbeTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testCase1() throws Exception {
    ICMPProbeImpl probe = new ICMPProbeImpl();
    probe.setTarget("www.icbc.com.cn");
    ICMPResult result = probe.run();
    assertNotNull(result);
    assertNull(result.getFailMsg());
  }

  public void testCase2() throws Exception {
    ICMPProbeImpl probe = new ICMPProbeImpl();
    probe.setTarget("www.icbcaaa.com.cn");
    ICMPResult result = probe.run();
    assertNotNull(result);
    assertNull(result.getFailMsg());
  }

  public void testCase3() throws Exception {
    ICMPProbeImpl probe = new ICMPProbeImpl();
    probe.setTarget("1.2.3.4");
    ICMPResult result = probe.run();
    assertNotNull(result);
    assertNull(result.getFailMsg());
  }
}

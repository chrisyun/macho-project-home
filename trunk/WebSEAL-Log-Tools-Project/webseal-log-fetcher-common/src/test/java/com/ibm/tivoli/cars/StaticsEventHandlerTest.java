/**
 * 
 */
package com.ibm.tivoli.cars;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

import junit.framework.TestCase;

import com.ibm.tivoli.cars.fetcher.Launcher;
import com.ibm.tivoli.cars.handler.ChainEventHandler;
import com.ibm.tivoli.cars.handler.EventHandler;
import com.ibm.tivoli.cars.handler.FileSoapEventHandlerImpl;
import com.ibm.tivoli.cars.handler.SoapEventHandlerImpl;
import com.ibm.tivoli.cars.handler.StaticsEventHandler;

/**
 * @author zhaodonglu
 *
 */
public class StaticsEventHandlerTest extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testCase1() throws Exception {
    StaticsEventHandler handler = new StaticsEventHandler();
    // Launch Log processor
    Launcher launcher = new Launcher(new WebSEALRequestLogProcessor(), handler);
    //Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.2011-05-15-02-17-56"), "GBK");
    Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.sample.1"), "GBK");
    launcher.process(reader, this.getClass().getResourceAsStream("/jmt.sgm.intranet.conf"), this.getClass().getResourceAsStream("/app.config.sgm.intranet.xml"));
    System.out.println(handler);
    assertEquals("appBytes=[RTPMS=96292, SGMBRMWeb会议预定系统=112, Root=886, Universe Portal=8688], appRequests=[RTPMS=3, SGMBRMWeb会议预定系统=1, Root=6, Universe Portal=2]]", 
        handler.toString().substring(handler.toString().indexOf("appBytes")));
  }

  public void testCaseOneDay() throws Exception {
    FileSoapEventHandlerImpl fileHandler = new FileSoapEventHandlerImpl();
    StaticsEventHandler handler = new StaticsEventHandler();
    // Launch Log processor
    Launcher launcher = new Launcher(new WebSEALRequestLogProcessor(), new ChainEventHandler(new EventHandler[]{handler, fileHandler}));
    //Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.2011-05-15-02-17-56"), "GBK");
    
    File dir = new File("D:/Zhao/Projects/SGM/2011/SGM_WebsealLogs/InternalWebseal227/request.log.2011-05-22");
    File[] logFiles = dir.listFiles(new FileFilter(){

      public boolean accept(File pathname) {
        return pathname.isFile() && pathname.getName().startsWith("request.log.2011-05-16");
      }});
    for (File logFile: logFiles) {
        System.err.println((new Date()) + ": Reader file: " + logFile.getCanonicalPath());
        Reader reader = new InputStreamReader(new FileInputStream(logFile), "GBK");
        launcher.process(reader, this.getClass().getResourceAsStream("/jmt.sgm.intranet.conf"), this.getClass().getResourceAsStream("/app.config.sgm.intranet.xml"));
        System.err.println((new Date()) + ":" + handler);
    }
    System.out.println(handler);
    // Elapse 1500 seconds, 7791875 requests, log file size: 1GB
    assertEquals("appBytes=[MyGod(CEM)=4213915, RTPMS=48708193, SGMPM=6826879, EP(电子采购)=1682605301, SGMBRMWeb会议预定系统=21427238, QiLin Portal=8530723278, DMES=39484169, SMART(EDW)=10998539, DSM(Siebel)=2156081, Root=37355202, Vitas QIP(pdi)=22950536, BPMA(EIT)=573547380, VOTING(党建直选)=1046401, PATAC ETS=138892471, tim/itim_expi=37743, EAM（刀具管理系统）=301915357, CI SGM市场研究分析平台=1241948, VAS(Vitas QIP subsys)=9663703, spsts=36245001, Dfsmdms=711628, ITIM=822677, authorwps=7651643, Universe Portal=1510921682, PATAC SDAC=38229302, PMS=59086359], appRequests=[MyGod(CEM)=937, RTPMS=1667, SGMPM=664, EP(电子采购)=465731, SGMBRMWeb会议预定系统=8420, QiLin Portal=241511, DMES=9357, SMART(EDW)=1881, DSM(Siebel)=426, Root=103050, Vitas QIP(pdi)=4155, BPMA(EIT)=60002, VOTING(党建直选)=167, PATAC ETS=74504, tim/itim_expi=17, EAM（刀具管理系统）=31800, CI SGM市场研究分析平台=187, VAS(Vitas QIP subsys)=965, spsts=10422, Dfsmdms=467, ITIM=302, authorwps=502, Universe Portal=810840, PATAC SDAC=35375, PMS=8485]]", 
                  handler.toString().substring(handler.toString().indexOf("appBytes")));
  }


  public void testCaseSixWeeksIntranet() throws Exception {
    FileSoapEventHandlerImpl fileHandler = new FileSoapEventHandlerImpl();
    StaticsEventHandler handler = new StaticsEventHandler();
    // Launch Log processor
    Launcher launcher = new Launcher(new WebSEALRequestLogProcessor(), new ChainEventHandler(new EventHandler[]{handler, fileHandler}));
    //Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.2011-05-15-02-17-56"), "GBK");
    
    File dir = new File("D:/Zhao/Projects/SGM/2011/SGM_WebsealLogs/InternalWebseal227/request.log.2011-05-22");
    File[] logFiles = dir.listFiles(new FileFilter(){

      public boolean accept(File pathname) {
        return pathname.isFile() && pathname.getName().startsWith("request.log");
      }});
    for (File logFile: logFiles) {
        System.err.println((new Date()) + ": Reader file: " + logFile.getCanonicalPath());
        Reader reader = new InputStreamReader(new FileInputStream(logFile), "GBK");
        launcher.process(reader, this.getClass().getResourceAsStream("/jmt.sgm.intranet.conf"), this.getClass().getResourceAsStream("/app.config.sgm.intranet.xml"));
        System.err.println((new Date()) + ":" + handler);
    }
    System.out.println(handler);
    // Elapse 1500 seconds, 7791875 requests, log file size: 1GB
    assertEquals("appBytes=[webmail=13021770, QILINOIS=18100694, ets=1903179633, SGMPM=344644931, brandfamily=24529162, bpma=13179682103, DMECandidateWeb=2591893, eprpt=4004903900, DMEWeb=597026032, SGMBRMWeb=349080113, tdi=70629, rol=10557229, QILINCP=44006349, ccpvote=14363383, edw=245416268, rtpms=2100890205, pricing=185937384, eproc=37847801332, qledge=19476886732, Root=1153986161, EAM=6140239898, dsm=148575544, ITMS=14487359, PkgmsSts=44403108, QILINBBS=79746619, sgmci=49739546, QILINMMS=9006770, pms=669567285, tim/itim_expi=76166, itim=21547674, vas=827949174, sdac=1204125685, qlparty=187065271, spsts=894473588, DMEDealerWeb=21850, universe=40896626542, DMSUC=90167, QILINBRM=307157543, authorwps=2706974963, QILINSITE=20214803470, dfsmdms=4919974, mygod=419124786, pdi=547517338, QILINWP=15868252, qlwps=131708245730], appRequests=[webmail=452, QILINOIS=5034, ets=988123, SGMPM=120741, brandfamily=18, bpma=1007723, DMECandidateWeb=642, eprpt=476746, DMEWeb=112542, SGMBRMWeb=143660, tdi=38, rol=3116, QILINCP=11762, ccpvote=3575, edw=55002, rtpms=61783, pricing=43290, eproc=11659945, qledge=238043, Root=3331369, EAM=1181551, dsm=15004, ITMS=3395, PkgmsSts=3328, QILINBBS=31461, sgmci=6596, QILINMMS=2938, pms=113314, tim/itim_expi=232, itim=9629, vas=702117, sdac=499251, qlparty=59001, spsts=263077, DMEDealerWeb=13, universe=22330513, DMSUC=90, QILINBRM=47231, authorwps=131370, QILINSITE=250320, dfsmdms=2748, mygod=32861, pdi=97295, QILINWP=5358, qlwps=3970690]]", 
                  handler.toString().substring(152));
  }

  public void testCaseSixWeeksInternet() throws Exception {
    FileSoapEventHandlerImpl fileHandler = new FileSoapEventHandlerImpl();
    StaticsEventHandler handler = new StaticsEventHandler();
    // Launch Log processor
    Launcher launcher = new Launcher(new WebSEALRequestLogProcessor(), new ChainEventHandler(new EventHandler[]{handler, fileHandler}));
    //Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.2011-05-15-02-17-56"), "GBK");
    
    File dir = new File("D:/Zhao/Projects/SGM/2011/SGM_WebsealLogs/ExternalWebseal149");
    File[] logFiles = dir.listFiles(new FileFilter(){

      public boolean accept(File pathname) {
        return pathname.isFile() && pathname.getName().startsWith("request.log");
      }});
    for (File logFile: logFiles) {
        System.err.println((new Date()) + ": Reader file: " + logFile.getCanonicalPath());
        Reader reader = new InputStreamReader(new FileInputStream(logFile), "GBK");
        launcher.process(reader, this.getClass().getResourceAsStream("/jmt.sgm.internet.conf"), this.getClass().getResourceAsStream("/app.config.sgm.intranet.xml"));
        System.err.println((new Date()) + ":" + handler);
    }
    System.out.println(handler);
    // Elapse 1500 seconds, 7791875 requests, log file size: 1GB
    assertEquals("appBytes=[webmail=13021770, QILINOIS=18100694, ets=1903179633, SGMPM=344644931, brandfamily=24529162, bpma=13179682103, DMECandidateWeb=2591893, eprpt=4004903900, DMEWeb=597026032, SGMBRMWeb=349080113, tdi=70629, rol=10557229, QILINCP=44006349, ccpvote=14363383, edw=245416268, rtpms=2100890205, pricing=185937384, eproc=37847801332, qledge=19476886732, Root=1153986161, EAM=6140239898, dsm=148575544, ITMS=14487359, PkgmsSts=44403108, QILINBBS=79746619, sgmci=49739546, QILINMMS=9006770, pms=669567285, tim/itim_expi=76166, itim=21547674, vas=827949174, sdac=1204125685, qlparty=187065271, spsts=894473588, DMEDealerWeb=21850, universe=40896626542, DMSUC=90167, QILINBRM=307157543, authorwps=2706974963, QILINSITE=20214803470, dfsmdms=4919974, mygod=419124786, pdi=547517338, QILINWP=15868252, qlwps=131708245730], appRequests=[webmail=452, QILINOIS=5034, ets=988123, SGMPM=120741, brandfamily=18, bpma=1007723, DMECandidateWeb=642, eprpt=476746, DMEWeb=112542, SGMBRMWeb=143660, tdi=38, rol=3116, QILINCP=11762, ccpvote=3575, edw=55002, rtpms=61783, pricing=43290, eproc=11659945, qledge=238043, Root=3331369, EAM=1181551, dsm=15004, ITMS=3395, PkgmsSts=3328, QILINBBS=31461, sgmci=6596, QILINMMS=2938, pms=113314, tim/itim_expi=232, itim=9629, vas=702117, sdac=499251, qlparty=59001, spsts=263077, DMEDealerWeb=13, universe=22330513, DMSUC=90, QILINBRM=47231, authorwps=131370, QILINSITE=250320, dfsmdms=2748, mygod=32861, pdi=97295, QILINWP=5358, qlwps=3970690]]", 
                  handler.toString().substring(152));
  }


  public void testSoapHandler() throws Exception {
    SoapEventHandlerImpl soapHandler = new SoapEventHandlerImpl();
    soapHandler.setCarsServiceURL("http://10.203.2.177:9080/CommonAuditService/services/Emitter");
    soapHandler.setWebSEALUrl("http://10.203.2.177");
    soapHandler.setWebSEALInstaceId("webseal177");
    soapHandler.setWebSEALLocation("webseal177");
    soapHandler.setCarsUsername("wasadmin");
    soapHandler.setCarsPassword("Passw0rd");

    StaticsEventHandler staticsHandler = new StaticsEventHandler();
    FileSoapEventHandlerImpl fileHandler = new FileSoapEventHandlerImpl();
    // Launch Log processor
    Launcher launcher = new Launcher(new WebSEALRequestLogProcessor(), new ChainEventHandler(new EventHandler[]{staticsHandler, soapHandler, fileHandler}));
    //Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.2011-05-15-02-17-56"), "GBK");
    Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.sample.2"), "GBK");
    launcher.process(reader, this.getClass().getResourceAsStream("/jmt.sgm.intranet.conf"), this.getClass().getResourceAsStream("/app.config.sgm.intranet.xml"));
    System.out.println(staticsHandler);
    assertEquals("appBytes=[MyGod(CEM)=419124786, RTPMS=2100896667, SGMPM=346222951, EP(电子采购)=41879760385, SGMBRMWeb会议预定系统=349276981, tdi=70629, QiLin Portal=172060929845, DMES=600646819, SMART(EDW)=245419136, DSM(Siebel)=148601840, Root=1002233122, ITMS=14714776, PkgmsSts=44403108, Vitas QIP(pdi)=548923026, BPMA(EIT)=13179724518, VOTING(党建直选)=14497917, PATAC ETS=1903407471, ROL=10558663, tim/itim_expi=871688, WebMail=13099756, EAM（刀具管理系统）=6151443835, CI SGM市场研究分析平台=49739546, VAS(Vitas QIP subsys)=827949174, spsts=998259955, Partsplan(SVG)=185937384, BDW(品牌差异化)=24529162, DMSUC=90167, Dfsmdms=4919974, ITIM=21547674, authorwps=2706974963, Universe Portal=40896652759, PATAC SDAC=1205780072, PMS=671821456], appRequests=[MyGod(CEM)=32861, RTPMS=61784, SGMPM=121296, EP(电子采购)=12148216, SGMBRMWeb会议预定系统=143699, tdi=38, QiLin Portal=4621843, DMES=113408, SMART(EDW)=55004, DSM(Siebel)=15009, Root=3294116, ITMS=3435, PkgmsSts=3328, Vitas QIP(pdi)=97859, BPMA(EIT)=1007728, VOTING(党建直选)=3595, PATAC ETS=988169, ROL=3117, tim/itim_expi=503, WebMail=467, EAM（刀具管理系统）=1183675, CI SGM市场研究分析平台=6596, VAS(Vitas QIP subsys)=702117, spsts=283853, Partsplan(SVG)=43290, BDW(品牌差异化)=18, DMSUC=90, Dfsmdms=2748, ITIM=9629, authorwps=131370, Universe Portal=22330531, PATAC SDAC=499846, PMS=113749]]", 
        staticsHandler.toString().substring(staticsHandler.toString().indexOf("appBytes")));
  }


  public void testUploader4Internet() throws Exception {
    SoapEventHandlerImpl soapHandler = new SoapEventHandlerImpl();
    soapHandler.setCarsServiceURL("http://10.203.2.177:9080/CommonAuditService/services/Emitter");
    soapHandler.setWebSEALUrl("http://10.203.2.177");
    soapHandler.setWebSEALInstaceId("webseal149");
    soapHandler.setWebSEALLocation("webseal149");
    soapHandler.setCarsUsername("wasadmin");
    soapHandler.setCarsPassword("Passw0rd");

    StaticsEventHandler staticsHandler = new StaticsEventHandler();
    FileSoapEventHandlerImpl fileHandler = new FileSoapEventHandlerImpl();
    // Launch Log processor
    Launcher launcher = new Launcher(new WebSEALRequestLogProcessor(), new ChainEventHandler(new EventHandler[]{staticsHandler, soapHandler, fileHandler}));
    //Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.2011-05-15-02-17-56"), "GBK");
    Reader reader = new InputStreamReader(new FileInputStream("D:/Zhao/Projects/SGM/2011/SGM_WebsealLogs/filtered/Internet/request.upload.log"), "UTF-8");
    launcher.process(reader, this.getClass().getResourceAsStream("/jmt.sgm.internet.conf"), this.getClass().getResourceAsStream("/app.config.sgm.internet.xml"));
    System.out.println(staticsHandler);
    assertEquals("appBytes=[MyGod(CEM)=236999300, RTPMS=28525137372, EP(电子采购)=38972, DMES=2391031861, SMART(EDW)=16765309, DSM(Siebel)=509453229043, Root=229773843286, ITMS=465423, ROL=12518241801, tim/itim_expi=2402046, WebMail=60004817, EAM（刀具管理系统）=1587967137, spsts=5171, DMSUC=6771596509, ITIM=6699, Universe Portal=78321], appRequests=[MyGod(CEM)=33465, RTPMS=3033343, EP(电子采购)=16, DMES=219970, SMART(EDW)=3468, DSM(Siebel)=71536792, Root=22989245, ITMS=60, ROL=1781467, tim/itim_expi=842, WebMail=2360, EAM（刀具管理系统）=91465, spsts=1, DMSUC=1143253, ITIM=2, Universe Portal=34]]", 
        staticsHandler.toString().substring(staticsHandler.toString().indexOf("appBytes")));
  }

}

/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/command/TestCompiler4CommandScript.java,v 1.9 2008/12/01 11:29:04 zhao Exp $
  * $Revision: 1.9 $
  * $Date: 2008/12/01 11:29:04 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.XMLUnit;

import sync4j.framework.core.AbstractCommand;
import sync4j.framework.core.AlertCode;
import sync4j.framework.engine.dm.AddManagementOperation;
import sync4j.framework.engine.dm.AtomicManagementOperation;
import sync4j.framework.engine.dm.DeleteManagementOperation;
import sync4j.framework.engine.dm.ExecManagementOperation;
import sync4j.framework.engine.dm.GetManagementOperation;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ReplaceManagementOperation;
import sync4j.framework.engine.dm.SequenceManagementOperation;
import sync4j.framework.engine.dm.TreeNode;
import sync4j.framework.engine.dm.UserAlertManagementOperation;
import sync4j.framework.engine.dm.Util;
import sync4j.framework.tools.CommandIdGenerator;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/12/01 11:29:04 $
 */
public class TestCompiler4CommandScript extends TestCase {
  
  private String FILENAME_ADD_SCRIPT = "/metadata/command/test/test.add.xml";
  private String FILENAME_GET_SCRIPT = "/metadata/command/test/test.get.xml";
  private String FILENAME_DEL_SCRIPT = "/metadata/command/test/test.delete.xml";
  private String FILENAME_REPLACE_SCRIPT = "/metadata/command/test/test.replace.xml";
  private String FILENAME_EXEC_SCRIPT = "/metadata/command/test/test.exec.xml";
  private String FILENAME_TEXTFILE_SCRIPT = "/metadata/command/test/test.text.xml";
  private String FILENAME_BINARYFILE_SCRIPT = "/metadata/command/test/test.binary.xml";
  private String FILENAME_ATOMIC_SCRIPT = "/metadata/command/test/test.atomic.xml";
  private String FILENAME_SEQUENCE_SCRIPT = "/metadata/command/test/test.sequence.xml";
  private String FILENAME_ALERT_SCRIPT = "/metadata/command/test/test.alert.xml";
  
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    XMLUnit.setIgnoreWhitespace(true);   
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.verify()'
   */
  public void testVerify() {

  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4AddCommand() throws Exception {
    try {
        {
          // Testcase: Add script
          File script = new File(AllTests.BASE_DIR, FILENAME_ADD_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 5);
          assertTrue(operations.get(0) instanceof AddManagementOperation);
          assertTrue(operations.get(1) instanceof AddManagementOperation);
          {
            AddManagementOperation op1 = (AddManagementOperation)operations.get(0);
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = nodes.get("./SCTSValue1");
            assertEquals(node.getName(), "./SCTSValue1");
            assertEquals(node.getFormat(), "int");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "8000");
          }
          {
            AddManagementOperation op1 = (AddManagementOperation)operations.get(1);
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSValue2");
            assertEquals(node.getName(), "./SCTSValue2");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "leaf node 2");
          }
          {
            AddManagementOperation op1 = (AddManagementOperation)operations.get(2);
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSValue3");
            assertEquals(node.getName(), "./SCTSValue3");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "leaf node 3");
          }
          {
            AddManagementOperation op2 = (AddManagementOperation)operations.get(3);
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSNode");
            assertEquals(node.getName(), "./SCTSNode");
            assertEquals(node.getFormat(), "node");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), null);
          }
          {
            AddManagementOperation op2 = (AddManagementOperation)operations.get(4);
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCM/Inventory/Delivered/BareApp/InstallOpts");
            assertEquals(node.getName(), "./SCM/Inventory/Delivered/BareApp/InstallOpts");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/xml");
            assertEquals(("<InstOpts>\n" +
                "<StdOpt name=\"drive\" value=\"c\"/>\n" +
                "<StdOpt name=\"lang\" value=\"EN\" />\n" +
                "<StdOpt name=\"upgrade\" value=\"yes\"/>\n" +
                "<StdOpt name=\"kill\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"pkginfo\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"optionals\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"ocsp\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"capabilities\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"untrusted\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/>\n" +
                "</InstOpts>\n").trim()
                , node.getValue().toString().trim());
          }

          ManagementOperation[] opers = (ManagementOperation[])operations.toArray(new ManagementOperation[0]);
          AbstractCommand[] cmds = Util.managementOperations2commands(opers, new CommandIdGenerator(), "");
          assertNotNull(cmds);
          assertEquals(5, cmds.length);
        }
        
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }

  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4ReplaceCommand() throws Exception {
    try {
        {
          // Testcase: Add script
          File script = new File(AllTests.BASE_DIR, FILENAME_REPLACE_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 5);
          assertTrue(operations.get(0) instanceof ReplaceManagementOperation);
          assertTrue(operations.get(1) instanceof ReplaceManagementOperation);
          {
            ReplaceManagementOperation op1 = (ReplaceManagementOperation)operations.get(0);
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSValue/1");
            assertEquals(node.getName(), "./SCTSValue/1");
            assertEquals(node.getFormat(), "int");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "8000");
          }
          {
            ReplaceManagementOperation op1 = (ReplaceManagementOperation)operations.get(1);
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSValue/2");
            assertEquals(node.getName(), "./SCTSValue/2");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "replaced leaf node 2");
          }
          {
            ReplaceManagementOperation op1 = (ReplaceManagementOperation)operations.get(2);
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSValue/3");
            assertEquals(node.getName(), "./SCTSValue/3");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "replaced leaf node 3");
          }
          {
            ReplaceManagementOperation op2 = (ReplaceManagementOperation)operations.get(3);
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSValue/2");
            assertEquals(node.getName(), "./SCTSValue/2");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "replaced leaf node 2");
          }
          {
            ReplaceManagementOperation op2 = (ReplaceManagementOperation)operations.get(4);
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCM/Inventory/Delivered/BareApp/InstallOpts");
            assertEquals(node.getName(), "./SCM/Inventory/Delivered/BareApp/InstallOpts");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/xml");
            assertEquals(("<InstOpts>\n" +
                "<StdOpt name=\"drive\" value=\"c\"/>\n" +
                "<StdOpt name=\"lang\" value=\"EN\" />\n" +
                "<StdOpt name=\"upgrade\" value=\"yes\"/>\n" +
                "<StdOpt name=\"kill\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"pkginfo\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"optionals\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"ocsp\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"capabilities\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"untrusted\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>\n" +
                "<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/>\n" +
                "</InstOpts>\n").trim()
                , node.getValue().toString().trim());
          }
        }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }
  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4GetCommand() throws Exception {
    try {
        {
          // Testcase: Add script
          File script = new File(AllTests.BASE_DIR, FILENAME_GET_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 2);
          assertTrue(operations.get(0) instanceof GetManagementOperation);
          assertTrue(operations.get(1) instanceof GetManagementOperation);
          GetManagementOperation op1 = (GetManagementOperation)operations.get(0);
          {
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTNode/DMv");
            assertEquals(node.getName(), "./SCTNode/DMv");
            assertEquals(node.getFormat(), "node");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), null);
          }
          GetManagementOperation op2 = (GetManagementOperation)operations.get(1);
          {
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTNode/FWv");
            assertEquals(node.getName(), "./SCTNode/FWv");
            assertEquals(node.getFormat(), "node");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), null);
          }
        }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }

  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4DeleteCommand() throws Exception {
    try {
        {
          // Testcase: Add script
          File script = new File(AllTests.BASE_DIR, FILENAME_DEL_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 2);
          assertTrue(operations.get(0) instanceof DeleteManagementOperation);
          assertTrue(operations.get(1) instanceof DeleteManagementOperation);
          DeleteManagementOperation op1 = (DeleteManagementOperation)operations.get(0);
          {
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSValue/1");
            assertEquals(node.getName(), "./SCTSValue/1");
            assertEquals(node.getFormat(), "node");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), null);
          }
          DeleteManagementOperation op2 = (DeleteManagementOperation)operations.get(1);
          {
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSValue/2");
            assertEquals(node.getName(), "./SCTSValue/2");
            assertEquals(node.getFormat(), "node");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), null);
          }
        }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }

  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4Command4TextFile() throws Exception {
    try {
        {
          // Testcase: Add script
          File script = new File(AllTests.BASE_DIR, FILENAME_TEXTFILE_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 2);
          assertTrue(operations.get(0) instanceof AddManagementOperation);
          assertTrue(operations.get(1) instanceof ReplaceManagementOperation);
          AddManagementOperation op1 = (AddManagementOperation)operations.get(0);
          {
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSLrgObjText");
            assertEquals(node.getName(), "./SCTSLrgObjText");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "GBK:中文信息1");
          }
          ReplaceManagementOperation op2 = (ReplaceManagementOperation)operations.get(1);
          {
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSLrgObjText");
            assertEquals(node.getName(), "./SCTSLrgObjText");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "GBK:中文信息2");
          }
        }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }

  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  
  public void testCompile4Command4BinaryFile() throws Exception {
    try {
        {
          // Testcase: Add script
          File script = new File(AllTests.BASE_DIR, FILENAME_BINARYFILE_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 2);
          assertTrue(operations.get(0) instanceof AddManagementOperation);
          assertTrue(operations.get(1) instanceof ReplaceManagementOperation);
          AddManagementOperation op1 = (AddManagementOperation)operations.get(0);
          {
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSLrgObjBin");
            assertEquals(node.getName(), "./SCTSLrgObjBin");
            assertEquals(node.getFormat(), "b64");
            assertEquals(node.getType(), "text/plain");
            assertTrue(node.getValue() instanceof byte[]);
            byte[] bytes = (byte[])node.getValue();
            assertEquals(bytes.length, 131610);
          }
          ReplaceManagementOperation op2 = (ReplaceManagementOperation)operations.get(1);
          {
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTSLrgObjBin");
            assertEquals(node.getName(), "./SCTSLrgObjBin");
            assertEquals(node.getFormat(), "b64");
            assertEquals(node.getType(), "text/plain");
            assertTrue(node.getValue() instanceof byte[]);
            byte[] bytes = (byte[])node.getValue();
            assertEquals(bytes.length, 131610);
          }
        }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }
  }
  
  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4AtomicCommand() throws Exception {
    try {
        {
          // Testcase: Add script
          File script = new File(AllTests.BASE_DIR, FILENAME_ATOMIC_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 1);
          assertTrue(operations.get(0) instanceof AtomicManagementOperation);
          AtomicManagementOperation op = (AtomicManagementOperation)operations.get(0);
          assertEquals(op.getOperations().length, 3);
          ManagementOperation[] ops = op.getOperations();
          ReplaceManagementOperation op1 = (ReplaceManagementOperation)ops[0];
          {
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SequenceNode/x");
            assertEquals(node.getName(), "./SequenceNode/x");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "x-replaced again via atomic");
          }
          ReplaceManagementOperation op2 = (ReplaceManagementOperation)ops[1];
          {
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SequenceNode/y");
            assertEquals(node.getName(), "./SequenceNode/y");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "y-replaced again via atomic");
          }
          AddManagementOperation op3 = (AddManagementOperation)ops[2];
          {
            assertFalse(op3.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op3.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SequenceNode/z");
            assertEquals(node.getName(), "./SequenceNode/z");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "z-added again via atomic");
          }
        }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }

  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4SequenceCommand() throws Exception {
    try {
        {
          // Testcase: Add script
          File script = new File(AllTests.BASE_DIR, FILENAME_SEQUENCE_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 1);
          assertTrue(operations.get(0) instanceof SequenceManagementOperation);
          SequenceManagementOperation op = (SequenceManagementOperation)operations.get(0);
          assertEquals(op.getOperations().length, 3);
          ManagementOperation[] ops = op.getOperations();
          ReplaceManagementOperation op1 = (ReplaceManagementOperation)ops[0];
          {
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SequenceNode/x");
            assertEquals(node.getName(), "./SequenceNode/x");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "x-replaced leaf node");
          }
          ReplaceManagementOperation op2 = (ReplaceManagementOperation)ops[1];
          {
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SequenceNode/y");
            assertEquals(node.getName(), "./SequenceNode/y");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "y-replaced leaf node");
          }
          AddManagementOperation op3 = (AddManagementOperation)ops[2];
          {
            assertFalse(op3.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op3.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SequenceNode/z");
            assertEquals(node.getName(), "./SequenceNode/z");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getValue(), "z-added");
          }
        }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }

  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4ExecCommand() throws Exception {
    try {
        {
          // Testcase: Exec script
          File script = new File(AllTests.BASE_DIR, FILENAME_EXEC_SCRIPT);
          FileInputStream in = new FileInputStream(script);
          Compiler compiler = new Compiler4CommandScript(in);
          List<ManagementOperation> operations = compiler.compile();
          assertEquals(operations.size(), 4);
          assertTrue(operations.get(0) instanceof ExecManagementOperation);
          assertTrue(operations.get(1) instanceof ExecManagementOperation);
          assertTrue(operations.get(2) instanceof ExecManagementOperation);
          ExecManagementOperation op1 = (ExecManagementOperation)operations.get(0);
          {
            assertFalse(op1.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op1.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTNode/DMv1");
            assertEquals(node.getName(), "./SCTNode/DMv1");
            assertEquals(node.getType(), "text/plain");
            assertNull(node.getFormat());
            assertEquals(node.getValue(), null);
          }
          ExecManagementOperation op2 = (ExecManagementOperation)operations.get(1);
          {
            assertFalse(op2.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op2.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTNode/DMv2");
            assertEquals(node.getName(), "./SCTNode/DMv2");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getValue(), "Parameter1");
          }
          ExecManagementOperation op3 = (ExecManagementOperation)operations.get(2);
          {
            assertFalse(op3.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op3.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./SCTNode/FWv");
            assertEquals(node.getName(), "./SCTNode/FWv");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getFormat(), "chr");
            assertEquals(node.getValue(), "Parameter2");
          }
          ExecManagementOperation op4 = (ExecManagementOperation)operations.get(3);
          {
            assertFalse(op4.getNodes().isEmpty());
            Map<String, TreeNode> nodes = op4.getNodes();
            assertEquals(nodes.size(), 1);
            TreeNode node = (TreeNode)nodes.get("./PolicyMgmt/Delivery/Sink");
            assertEquals(node.getName(), "./PolicyMgmt/Delivery/Sink");
            assertEquals(node.getType(), "text/plain");
            assertEquals(node.getFormat(), "xml");
            String exptectedValue = "<Package>\n" +
            "<Operation\n" +
            "action_id = \"Add\"\n" +
            "target_id = \"roles_mapping_policy\"\n" +
            "use_bearer_certificate = \"true\">\n" +
            "<Data>\n" +
            "<![CDATA[\n" +
            "<Rule\n" +
            "RuleId=\"roles_mapping_policy_rule1\"\n" +
            "Effect=\"Permit\">\n" +
            "<Target>\n" +
            "<Subjects>\n" +
            "<Subject>\n" +
            "<SubjectMatch\n" +
            "MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\n" +
            "<AttributeValue\n" +
            "DataType=\"http://www.w3.org/2001/XMLSchema#string\">\n" +
            "COMCOM\n" +
            "</AttributeValue>\n" +
            "<ResourceAttributeDesignator\n" +
            "AttributeId=\"trusted_subject\"\n" +
            "DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "</SubjectMatch>\n" +
            "<SubjectMatch\n" +
            "MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\n" +
            "<AttributeValue\n" +
            "DataType=\"http://www.w3.org/2001/XMLSchema#string\">\n" +
            "trustedadmin\n" +
            "</AttributeValue>\n" +
            "<ResourceAttributeDesignator\n" +
            "AttributeId=\"urn:nokia:names:s60:corporate:1.0:subject:role_id\"\n" +
            "DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>\n" +
            "</SubjectMatch>\n" +
            "</Subject>\n" +
            "</Subjects>\n" +
            "</Target>\n" +
            "</Rule>\n" +
            "</Data>\n" +
            "]]>\n" +
            "</Operation>\n" +
            "</Package>";
            //Diff diff = new Diff(exptectedValue, node.getValue().toString());
            //assertTrue("XML Similar " + diff.toString(), diff.similar());
            assertEquals(exptectedValue, node.getValue().toString());
          }
        }
    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }
  
  }
  
  public void testUserAlertManagementOperation() throws Exception {
    UserAlertManagementOperation alert = new UserAlertManagementOperation();
    assertNull(alert.getAlerts());
    alert.addAlert("abc");
    assertNotNull(alert.getAlerts());
    assertEquals(1, alert.getAlerts().length);
    assertEquals("abc", alert.getAlerts()[0]);
    
    alert.addAlert("bcd");
    assertNotNull(alert.getAlerts());
    assertEquals(2, alert.getAlerts().length);
    assertEquals("abc", alert.getAlerts()[0]);
    assertEquals("bcd", alert.getAlerts()[1]);
  }

  /*
   * Test method for 'com.npower.dm.command.Compiler4CommandScript.compile()'
   */
  public void testCompile4AlertCommand() throws Exception {
    try {
        // Testcase: Exec script
        File script = new File(AllTests.BASE_DIR, FILENAME_ALERT_SCRIPT);
        FileInputStream in = new FileInputStream(script);
        Compiler compiler = new Compiler4CommandScript(in);
        List<ManagementOperation> operations = compiler.compile();
        
        assertEquals(operations.size(), 5);
        assertTrue(operations.get(0) instanceof UserAlertManagementOperation);

        {
          UserAlertManagementOperation oper = (UserAlertManagementOperation)operations.get(0);
          assertNotNull(oper.getAlerts());
          assertEquals(1, oper.getAlerts().length);
          assertEquals("Management in progress", oper.getAlerts()[0]);
          assertEquals(AlertCode.DISPLAY, oper.getAlertCode());
          assertEquals(1100, oper.getAlertCode());
          assertEquals(60, oper.getMinDisplayTime());
          assertEquals(120, oper.getMaxDisplayTime());
          assertEquals(' ', oper.getEchoType());
          assertEquals(' ', oper.getInputType());
          assertEquals(null, oper.getDefaultResponse());
          assertEquals(-1, oper.getMaxLength());
        }

        {
          UserAlertManagementOperation oper = (UserAlertManagementOperation)operations.get(1);
          assertNotNull(oper.getAlerts());
          assertEquals(1, oper.getAlerts().length);
          assertEquals("Do you want to add the CNN access point?", oper.getAlerts()[0]);
          assertEquals(AlertCode.CONFIRM_OR_REJECT, oper.getAlertCode());
          assertEquals(1101, oper.getAlertCode());
          assertEquals(30, oper.getMinDisplayTime());
          assertEquals(60, oper.getMaxDisplayTime());
          assertEquals(' ', oper.getEchoType());
          assertEquals(' ', oper.getInputType());
          assertEquals(null, oper.getDefaultResponse());
          assertEquals(-1, oper.getMaxLength());
        }
        
        {
          UserAlertManagementOperation oper = (UserAlertManagementOperation)operations.get(2);
          assertNotNull(oper.getAlerts());
          assertEquals(1, oper.getAlerts().length);
          assertEquals("Type in the name of the service you would like to configure", oper.getAlerts()[0]);
          assertEquals(AlertCode.INPUT, oper.getAlertCode());
          assertEquals(1102, oper.getAlertCode());
          assertEquals(30, oper.getMinDisplayTime());
          assertEquals(60, oper.getMaxDisplayTime());
          assertEquals('T', oper.getEchoType());
          assertEquals('N', oper.getInputType());
          assertEquals("1234", oper.getDefaultResponse());
          assertEquals(6, oper.getMaxLength());
        }

        {
          UserAlertManagementOperation oper = (UserAlertManagementOperation)operations.get(3);
          assertNotNull(oper.getAlerts());
          assertEquals(AlertCode.SINGLE_CHOICE, oper.getAlertCode());
          assertEquals(1103, oper.getAlertCode());
          assertEquals(10, oper.getMinDisplayTime());
          assertEquals(60, oper.getMaxDisplayTime());
          assertEquals("1", oper.getDefaultResponse());
          
          assertEquals(4, oper.getAlerts().length);
          assertEquals("Select service to configure", oper.getAlerts()[0]);
          assertEquals("CNN", oper.getAlerts()[1]);
          assertEquals("Mobilbank", oper.getAlerts()[2]);
          assertEquals("Game Channel", oper.getAlerts()[3]);
        }

        {
          UserAlertManagementOperation oper = (UserAlertManagementOperation)operations.get(4);
          assertNotNull(oper.getAlerts());
          assertEquals(AlertCode.MULTIPLE_CHOICE, oper.getAlertCode());
          assertEquals(1104, oper.getAlertCode());
          assertEquals(10, oper.getMinDisplayTime());
          assertEquals(60, oper.getMaxDisplayTime());
          assertEquals("2-3", oper.getDefaultResponse());
          
          assertEquals(4, oper.getAlerts().length);
          assertEquals("Select service to configure", oper.getAlerts()[0]);
          assertEquals("CNN", oper.getAlerts()[1]);
          assertEquals("Mobilbank", oper.getAlerts()[2]);
          assertEquals("Game Channel", oper.getAlerts()[3]);
        }

    } catch (FileNotFoundException e) {
      throw e;
    } catch (DMException e) {
      throw e;
    }
  
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestCompiler4CommandScript.class);
  }

}
/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/command/Compiler4CommandScript.java,v 1.11 2008/12/01 11:29:04 zhao Exp $
  * $Revision: 1.11 $
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;

import sync4j.framework.engine.dm.ManagementOperation;

import com.npower.dm.command.alert.ConfirmCreateFactory;
import com.npower.dm.command.alert.DisplayCreateFactory;
import com.npower.dm.command.alert.InputCreateFactory;
import com.npower.dm.command.alert.MultipleChoiceCreateFactory;
import com.npower.dm.command.alert.SingleChoiceCreateFactory;
import com.npower.dm.core.DMException;

/**
 * Parser Command Scripts into DM XXXXOperations.<br>
 * 
 * Command script is a XML and it's syntax was defined by script_1_0.dtd.<br>
 * Reference metadata/command/script_1_0.dtd<br>
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.11 $ $Date: 2008/12/01 11:29:04 $
 */
public class Compiler4CommandScript implements Compiler {
  
  private InputStream input = null;

  /**
   * 
   */
  public Compiler4CommandScript(InputStream in) {
    super();
    this.input = in;
  }
  
  /**
   * 
   */
  public Compiler4CommandScript(String script) throws DMException {
    super();
    try {
        this.input = new ByteArrayInputStream(script.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new DMException(e);
    }
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.command.Compiler#verify()
   */
  public boolean verify() throws DMException {
    this.compile();
    return true;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.command.Compiler#compile()
   */
  public List<ManagementOperation> compile() throws DMException {
    List<ManagementOperation> result = new ArrayList<ManagementOperation>();
    // Create and execute our Digester
    Digester digester = createDigester();
    try {
        // Push a List into the stack.
        digester.push(result);
        digester.parse(this.input);
        return result;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }
  
  /**
   * Create Digester for CommandScripter DTD
   * @return
   */
  private Digester createDigester() {
    // Initialize the digester
    Digester digester = new Digester();
    //digester.register("-//OTAS, Inc.//DTD DM Server//EN", dtdURL);
    digester.setValidating(false);
    
    this.createDigester4Add(digester);
    this.createDigester4Get(digester);
    this.createDigester4Delete(digester);
    this.createDigester4Replace(digester);
    this.createDigester4Atomic(digester);
    this.createDigester4Sequence(digester);
    this.createDigester4Exec(digester);
    
    this.createDigester4Alert(digester);
    
    return digester;
  }
  
  /**
   * Create Digester rules for Add command.
   * @param digester
   */
  private void createDigester4Add(Digester digester) {
    digester.addObjectCreate("*/Add", sync4j.framework.engine.dm.AddManagementOperation.class);
    digester.addSetNext("*/Add", "add", "sync4j.framework.engine.dm.AddManagementOperation");
    
    // Parse the add leafnode
    digester.addObjectCreate("*/Add/LeafNode", sync4j.framework.engine.dm.TreeNode.class);
    digester.addSetNext("*/Add/LeafNode", "addTreeNode", "sync4j.framework.engine.dm.TreeNode");
    
    digester.addBeanPropertySetter("*/Add/LeafNode/Target", "name");
    digester.addBeanPropertySetter("*/Add/LeafNode/Data", "value");

    digester.addCallMethod("*/Add/LeafNode/Data", "setType", 1);
    //digester.addObjectParam("*/Add/LeafNode/Data", 0, "text/plain"); // pass literal "text/plain" string
    digester.addCallParam("*/Add/LeafNode/Data", 0, "type"); // pass type: text/plain, text/xml
    digester.addCallMethod("*/Add/LeafNode/Data", "setFormat", 1);
    digester.addCallParam("*/Add/LeafNode/Data", 0, "format"); // pass format: chr, node, ...
    
    digester.addBeanPropertySetter("*/Add/LeafNode/TextFile", "textFile");
    digester.addBeanPropertySetter("*/Add/LeafNode/Charset", "charset");
    digester.addCallMethod("*/Add/LeafNode/TextFile", "setType", 1);
    digester.addObjectParam("*/Add/LeafNode/TextFile", 0, "text/plain"); // pass literal "text/plain" string
    digester.addCallMethod("*/Add/LeafNode/TextFile", "setFormat", 1);
    digester.addObjectParam("*/Add/LeafNode/TextFile", 0, "chr"); // pass literal "node" string
    
    digester.addBeanPropertySetter("*/Add/LeafNode/BinaryFile", "binaryFile");
    digester.addBeanPropertySetter("*/Add/LeafNode/Format", "format");
    digester.addCallMethod("*/Add/LeafNode/BinaryFile", "setType", 1);
    digester.addObjectParam("*/Add/LeafNode/BinaryFile", 0, "text/plain"); // pass literal "text/plain" string
    digester.addCallMethod("*/Add/LeafNode/BinaryFile", "setFormat", 1);
    digester.addObjectParam("*/Add/LeafNode/BinaryFile", 0, "b64"); // pass literal "node" string

    digester.addObjectCreate("*/Add/InteriorNode", sync4j.framework.engine.dm.TreeNode.class);
    digester.addBeanPropertySetter("*/Add/InteriorNode/Target", "name");
    digester.addCallMethod("*/Add/InteriorNode", "setFormat", 1);
    digester.addObjectParam("*/Add/InteriorNode", 0, "node"); // pass literal "node" string
    digester.addSetNext("*/Add/InteriorNode", "addTreeNode", "sync4j.framework.engine.dm.TreeNode");

  }

  /**
   * Create Digester rule for Get Command.
   * @param digester
   */
  private void createDigester4Get(Digester digester) {
    digester.addObjectCreate("*/Get", sync4j.framework.engine.dm.GetManagementOperation.class);
    digester.addSetNext("*/Get", "add", "sync4j.framework.engine.dm.GetManagementOperation");
    
    // Parse the add leafnode
    digester.addObjectCreate("*/Get/Target", sync4j.framework.engine.dm.TreeNode.class);
    digester.addCallMethod("*/Get/Target", "setName", 1);
    digester.addCallParam("*/Get/Target", 0);
    digester.addCallMethod("*/Get/Target", "setFormat", 1);
    digester.addObjectParam("*/Get/Target", 0, "node"); // pass literal "node" string
    digester.addSetNext("*/Get/Target", "addTreeNode", "sync4j.framework.engine.dm.TreeNode");
  }

  /**
   * Create Digester rule for Get Command.
   * @param digester
   */
  private void createDigester4Delete(Digester digester) {
    digester.addObjectCreate("*/Delete", sync4j.framework.engine.dm.DeleteManagementOperation.class);
    digester.addSetNext("*/Delete", "add", "sync4j.framework.engine.dm.DeleteManagementOperation");
    
    // Parse the add leafnode
    digester.addObjectCreate("*/Delete/Target", sync4j.framework.engine.dm.TreeNode.class);
    digester.addCallMethod("*/Delete/Target", "setName", 1);
    digester.addCallParam("*/Delete/Target", 0);
    digester.addCallMethod("*/Delete/Target", "setFormat", 1);
    digester.addObjectParam("*/Delete/Target", 0, "node"); // pass literal "node" string
    digester.addSetNext("*/Delete/Target", "addTreeNode", "sync4j.framework.engine.dm.TreeNode");
  }

  /**
   * Create Digester rules for Replace command.
   * @param digester
   */
  private void createDigester4Replace(Digester digester) {
    digester.addObjectCreate("*/Replace", sync4j.framework.engine.dm.ReplaceManagementOperation.class);
    digester.addSetNext("*/Replace", "add", "sync4j.framework.engine.dm.ReplaceManagementOperation");
    
    // Parse the add leafnode
    digester.addObjectCreate("*/Replace/LeafNode", sync4j.framework.engine.dm.TreeNode.class);
    
    digester.addBeanPropertySetter("*/Replace/LeafNode/Target", "name");
    digester.addBeanPropertySetter("*/Replace/LeafNode/Data", "value");
    digester.addCallMethod("*/Replace/LeafNode/Data", "setType", 1);
    //digester.addObjectParam("*/Replace/LeafNode/Data", 0, "text/plain"); // pass literal "text/plain" string
    digester.addCallParam("*/Replace/LeafNode/Data", 0, "type"); // pass type: text/plain, text/xml
    digester.addCallMethod("*/Replace/LeafNode/Data", "setFormat", 1);
    digester.addCallParam("*/Replace/LeafNode/Data", 0, "format"); // pass literal "node" string
    
    digester.addBeanPropertySetter("*/Replace/LeafNode/TextFile", "textFile");
    digester.addBeanPropertySetter("*/Replace/LeafNode/Charset", "charset");
    digester.addCallMethod("*/Replace/LeafNode/TextFile", "setType", 1);
    digester.addObjectParam("*/Replace/LeafNode/TextFile", 0, "text/plain"); // pass literal "text/plain" string
    digester.addCallMethod("*/Replace/LeafNode/TextFile", "setFormat", 1);
    digester.addObjectParam("*/Replace/LeafNode/TextFile", 0, "chr"); // pass literal "node" string
    digester.addSetNext("*/Replace/LeafNode", "addTreeNode", "sync4j.framework.engine.dm.TreeNode");
    
    digester.addBeanPropertySetter("*/Replace/LeafNode/BinaryFile", "binaryFile");
    digester.addBeanPropertySetter("*/Replace/LeafNode/Format", "format");
    digester.addCallMethod("*/Replace/LeafNode/BinaryFile", "setType", 1);
    digester.addObjectParam("*/Replace/LeafNode/BinaryFile", 0, "text/plain"); // pass literal "text/plain" string
    digester.addCallMethod("*/Replace/LeafNode/BinaryFile", "setFormat", 1);
    digester.addObjectParam("*/Replace/LeafNode/BinaryFile", 0, "b64"); // pass literal "node" string
    digester.addSetNext("*/Replace/LeafNode", "addTreeNode", "sync4j.framework.engine.dm.TreeNode");
  }
  
  /**
   * Create Digester rules for Replace command.
   * @param digester
   */
  private void createDigester4Exec(Digester digester) {
    digester.addObjectCreate("*/Exec", sync4j.framework.engine.dm.ExecManagementOperation.class);
    digester.addSetNext("*/Exec", "add", "sync4j.framework.engine.dm.ExecManagementOperation");
    
    // Parse the add leafnode
    digester.addObjectCreate("*/Exec/Item", sync4j.framework.engine.dm.TreeNode.class);
    
    digester.addBeanPropertySetter("*/Exec/Item/Target", "name");
    digester.addBeanPropertySetter("*/Exec/Item/Data", "value");
    digester.addCallMethod("*/Exec/Item/Data", "setType", 1);
    digester.addCallParam("*/Exec/Item/Data", 0, "type"); // pass literal "text/plain" string
    digester.addCallMethod("*/Exec/Item/Data", "setFormat", 1);
    digester.addCallParam("*/Exec/Item/Data", 0, "format"); // pass literal "node" string
    
    digester.addSetNext("*/Exec/Item", "addTreeNode", "sync4j.framework.engine.dm.TreeNode");
  }
  
  /**
   * Create Digester rules for Alert command.
   * @param digester
   */
  private void createDigester4Alert(Digester digester) {
    digester.addFactoryCreate("*/Display", DisplayCreateFactory.class);
    digester.addSetNext("*/Display", "add");
    
    digester.addFactoryCreate("*/Confirm", ConfirmCreateFactory.class);
    digester.addSetNext("*/Confirm", "add");

    digester.addFactoryCreate("*/Input", InputCreateFactory.class);
    digester.addSetNext("*/Input", "add");

    digester.addFactoryCreate("*/SingleChoice", SingleChoiceCreateFactory.class);
    digester.addSetNext("*/SingleChoice", "add");

    digester.addFactoryCreate("*/MultipleChoice", MultipleChoiceCreateFactory.class);
    digester.addSetNext("*/MultipleChoice", "add");

    // Parse the add leafnode
    digester.addCallMethod("*/Text", "addAlert", 1);
    digester.addCallParam("*/Text", 0);
    
    digester.addCallMethod("*/SingleChoice/Options/Option", "addAlert", 1);
    digester.addCallParam("*/SingleChoice/Options/Option", 0);

    digester.addCallMethod("*/MultipleChoice/Options/Option", "addAlert", 1);
    digester.addCallParam("*/MultipleChoice/Options/Option", 0);

    digester.addBeanPropertySetter("*/MinDisplayTime", "minDisplayTime");
    digester.addBeanPropertySetter("*/MaxDisplayTime", "maxDisplayTime");
    digester.addBeanPropertySetter("*/DefaultResponse", "defaultResponse");
    digester.addBeanPropertySetter("*/MaxLength", "maxLength");
    digester.addBeanPropertySetter("*/InputType", "inputType");
    digester.addBeanPropertySetter("*/EchoType", "echoType");
  }
  
  /**
   * Create Digester rule for Get Command.
   * @param digester
   */
  private void createDigester4Atomic(Digester digester) {
    digester.addObjectCreate("*/Atomic", sync4j.framework.engine.dm.AtomicManagementOperation.class);
    digester.addSetNext("*/Atomic", "add", "sync4j.framework.engine.dm.AtomicManagementOperation");
  }
  
  /**
   * Create Digester rule for Get Command.
   * @param digester
   */
  private void createDigester4Sequence(Digester digester) {
    digester.addObjectCreate("*/Sequence", sync4j.framework.engine.dm.SequenceManagementOperation.class);
    digester.addSetNext("*/Sequence", "add", "sync4j.framework.engine.dm.SequenceManagementOperation");
  }

}
/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/multiplexor/TestDispatcher4NumberMapping.java,v 1.2 2008/12/25 03:27:59 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/12/25 03:27:59 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.multiplexor;

import java.io.InputStream;

import com.npower.dm.multiplexor.MappingRuleBasedPerl5;
import com.npower.dm.multiplexor.MappingTable;
import com.npower.dm.multiplexor.MappingTableFactory;

import junit.framework.TestCase;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/12/25 03:27:59 $
 */

public class TestDispatcher4NumberMapping extends TestCase {

  /**
   * @param name
   */
  public TestDispatcher4NumberMapping(String name) {
    super(name);
  }

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

  public void testXMLParser() throws Exception {
    MappingTableFactory factory = new MappingTableFactory();
    
    InputStream file = this.getClass().getResourceAsStream("/com/npower/dm/multiplexor/multiplexor.mapping.xml");
    
    MappingTable mappingTable = factory.createMappingTable(file);
    assertEquals(14, mappingTable.getRules().size());

    assertEquals("8{3}[0-9]([0-9]|[a-z]|[A-Z])", mappingTable.getRules().get(0).getNumberPattern());
    assertEquals("DefalutQueue", mappingTable.getRules().get(0).getQueueName());
    assertEquals("([0-9]|[a-z]|[A-Z])8{3}[0-9]", mappingTable.getRules().get(1).getNumberPattern());
    assertEquals("DefalutQueue", mappingTable.getRules().get(1).getQueueName());
    assertEquals("([0-9]|[a-z]|[A-Z])8{3}[0-9]([0-9]|[a-z]|[A-Z])", mappingTable.getRules().get(2).getNumberPattern());
    assertEquals("DefalutQueue", mappingTable.getRules().get(2).getQueueName());            
    assertEquals("8{3}[0]", mappingTable.getRules().get(3).getNumberPattern());
    assertEquals("Q0", mappingTable.getRules().get(3).getQueueName());
    assertEquals("8{3}[1]", mappingTable.getRules().get(4).getNumberPattern());
    assertEquals("Q1", mappingTable.getRules().get(4).getQueueName());
    assertEquals("8{3}[2]", mappingTable.getRules().get(5).getNumberPattern());
    assertEquals("Q2", mappingTable.getRules().get(5).getQueueName());
    assertEquals("8{3}[3]", mappingTable.getRules().get(6).getNumberPattern());
    assertEquals("Q3", mappingTable.getRules().get(6).getQueueName());
    assertEquals("8{3}[4]", mappingTable.getRules().get(7).getNumberPattern());
    assertEquals("Q4", mappingTable.getRules().get(7).getQueueName());
    assertEquals("8{3}[5]", mappingTable.getRules().get(8).getNumberPattern());
    assertEquals("Q5", mappingTable.getRules().get(8).getQueueName());
    assertEquals("8{3}[6]", mappingTable.getRules().get(9).getNumberPattern());
    assertEquals("Q6", mappingTable.getRules().get(9).getQueueName());
    assertEquals("8{3}[7]", mappingTable.getRules().get(10).getNumberPattern());
    assertEquals("Q7", mappingTable.getRules().get(10).getQueueName());
    assertEquals("8{3}[8]", mappingTable.getRules().get(11).getNumberPattern());
    assertEquals("Q8", mappingTable.getRules().get(11).getQueueName());
    assertEquals("8{3}[9]", mappingTable.getRules().get(12).getNumberPattern());
    assertEquals("Q9", mappingTable.getRules().get(12).getQueueName());
    assertEquals("8{3}[^0-9]", mappingTable.getRules().get(13).getNumberPattern());
    assertEquals("DefalutQueue", mappingTable.getRules().get(13).getQueueName());

  }
  
  public void testMatch() throws Exception {
 
    MappingTable mappingTable = new MappingTable();
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[0-9]([0-9]|[a-z]|[A-Z])", "DefalutQueue"));
    mappingTable.addRule(new MappingRuleBasedPerl5("([0-9]|[a-z]|[A-Z])8{3}[0-9]", "DefalutQueue"));
    mappingTable.addRule(new MappingRuleBasedPerl5("([0-9]|[a-z]|[A-Z])8{3}[0-9]([0-9]|[a-z]|[A-Z])", "DefalutQueue"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[0]", "Q0"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[1]", "Q1"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[2]", "Q2"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[3]", "Q3"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[4]", "Q4"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[5]", "Q5"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[6]", "Q6"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[7]", "Q7"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[8]", "Q8"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[9]", "Q9"));
    mappingTable.addRule(new MappingRuleBasedPerl5("8{3}[^0-9]", "DefalutQueue"));
    
    assertEquals("Q1", mappingTable.getTarget("8881"));
    assertEquals("Q9", mappingTable.getTarget("8889"));

    assertEquals("DefalutQueue", mappingTable.getTarget("888v"));
    assertEquals("DefalutQueue", mappingTable.getTarget("888vbg"));
    assertEquals("DefalutQueue", mappingTable.getTarget("3t888SE56"));
    assertEquals("DefalutQueue", mappingTable.getTarget("88867"));
    assertEquals("DefalutQueue", mappingTable.getTarget("88867VBFG"));
    assertEquals("DefalutQueue", mappingTable.getTarget("8886Ag"));
    assertEquals("DefalutQueue", mappingTable.getTarget("358887"));
    assertEquals("DefalutQueue", mappingTable.getTarget("fdDFG453tr8887"));
    assertEquals("DefalutQueue", mappingTable.getTarget("fdDFG453tr88874SdfE56"));
    assertEquals("DefalutQueue", mappingTable.getTarget("fdDFG453tr888SdfE56"));
    assertNull(mappingTable.getTarget("37218486872836"));
  }
  

}

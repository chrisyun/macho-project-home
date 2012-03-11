/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/util/TestNumberGenerator.java,v 1.8 2007/03/19 11:15:00 zhao Exp $
 * $Revision: 1.8 $
 * $Date: 2007/03/19 11:15:00 $
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
package com.npower.dm.util;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2007/03/19 11:15:00 $
 */
public class TestNumberGenerator extends TestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCase1() throws Exception {
		NumberGeneratorFactory factory = NumberGeneratorFactory.newInstance();
		NumberGenerator generator = factory.getNumberGenerator(null);

		List<String> result = generator.generate();
		assertNull(result);
	}

	public void testCase2() throws Exception {

		NumberGeneratorFactory factory = NumberGeneratorFactory.newInstance();

		// 1个"?"
		String numberTemplateTest1 = "138?0000000";
		NumberGenerator generatorTest1 = factory
				.getNumberGenerator(numberTemplateTest1);
		List<String> testResult1 = generatorTest1.generate();
		assertNotNull(testResult1);
		assertEquals(testResult1.size(), 10);
		assertTrue(testResult1.contains("13840000000"));
		assertTrue(testResult1.contains("13870000000"));
		assertTrue(testResult1.contains("13890000000"));

		// 2个"?"
		String numberTemplateTest2 = "138?0?00000";
		NumberGenerator generatorTest2 = factory
				.getNumberGenerator(numberTemplateTest2);
		List<String> testResult2 = generatorTest2.generate();
		assertNotNull(testResult2);
		assertEquals(testResult2.size(), 100);
		assertTrue(testResult2.contains("13810300000"));
		assertTrue(testResult2.contains("13850700000"));
		assertTrue(testResult2.contains("13890400000"));
		assertTrue(testResult2.contains("13880200000"));

		// 3个"?"
		String numberTemplateTest3 = "138?0?0?000";
		NumberGenerator generatorTest3 = factory
				.getNumberGenerator(numberTemplateTest3);
		List<String> testResult3 = generatorTest3.generate();
		assertNotNull(testResult3);
		assertEquals(testResult3.size(), 1000);
		assertTrue(testResult3.contains("13810306000"));
		assertTrue(testResult3.contains("13820409000"));
		assertTrue(testResult3.contains("13800000000"));
		assertTrue(testResult3.contains("13850007000"));
		assertTrue(testResult3.contains("13860308000"));
		assertTrue(testResult3.contains("13840105000"));

		// 4个"?"
		String numberTemplateTest4 = "1384000????";
		NumberGenerator generatorTest4 = factory
				.getNumberGenerator(numberTemplateTest4);
		List<String> testResult4 = generatorTest4.generate();
		assertNotNull(testResult4);
		assertEquals(testResult4.size(), 10000);
		assertTrue(testResult4.contains("13840001234"));
		assertTrue(testResult4.contains("13840005432"));
		assertTrue(testResult4.contains("13840001468"));
		assertTrue(testResult4.contains("13840000965"));
		assertTrue(testResult4.contains("13840004568"));
		assertTrue(testResult4.contains("13840008147"));
		assertTrue(testResult4.contains("13840000624"));
		assertTrue(testResult4.contains("13840001689"));

		// 5个"?"
		String numberTemplateTest5 = "138?000????";
		NumberGenerator generatorTest5 = factory
				.getNumberGenerator(numberTemplateTest5);
		List<String> testResult5 = generatorTest5.generate();
		assertNotNull(testResult5);
		assertEquals(testResult5.size(), 100000);
		assertTrue(testResult5.contains("13820001234"));
		assertTrue(testResult5.contains("13840005432"));
		assertTrue(testResult5.contains("13860001468"));
		assertTrue(testResult5.contains("13850000965"));
		assertTrue(testResult5.contains("13870004568"));
		assertTrue(testResult5.contains("13880008147"));
		assertTrue(testResult5.contains("13890000624"));
		assertTrue(testResult5.contains("13800001689"));

	}

	public void testCase3() throws Exception {

		NumberGeneratorFactory factory = NumberGeneratorFactory.newInstance();

		// "*"匹配1个数字
		String numberTemplateTest1 = "138*0004444";
		NumberGenerator generatorTest1 = factory
				.getNumberGenerator(numberTemplateTest1);
		List<String> testResult1 = generatorTest1.generate();
		assertNotNull(testResult1);
		assertEquals(testResult1.size(), 10);
		assertTrue(testResult1.contains("13820004444"));
		assertTrue(testResult1.contains("13810004444"));
		assertTrue(testResult1.contains("13860004444"));
		assertTrue(testResult1.contains("13880004444"));
		assertTrue(testResult1.contains("13800004444"));

		// "*"匹配2个数字
		String numberTemplateTest2 = "138*004444";
		NumberGenerator generatorTest2 = factory
				.getNumberGenerator(numberTemplateTest2);
		List<String> testResult2 = generatorTest2.generate();
		assertNotNull(testResult2);
		assertEquals(testResult2.size(), 100);
		assertTrue(testResult2.contains("13812004444"));
		assertTrue(testResult2.contains("13843004444"));
		assertTrue(testResult2.contains("13876004444"));
		assertTrue(testResult2.contains("13889004444"));
		assertTrue(testResult2.contains("13810004444"));

		// "*"匹配3个数字
		String numberTemplateTest3 = "138*04444";
		NumberGenerator generatorTest3 = factory
				.getNumberGenerator(numberTemplateTest3);
		List<String> testResult3 = generatorTest3.generate();
		assertNotNull(testResult3);
		assertEquals(testResult3.size(), 1000);
		assertTrue(testResult3.contains("13812304444"));
		assertTrue(testResult3.contains("13887404444"));
		assertTrue(testResult3.contains("13803204444"));
		assertTrue(testResult3.contains("13823404444"));
		assertTrue(testResult3.contains("13891204444"));

		// "*"匹配4个数字
		String numberTemplateTest4 = "138*4444";
		NumberGenerator generatorTest4 = factory
				.getNumberGenerator(numberTemplateTest4);
		List<String> testResult4 = generatorTest4.generate();
		assertNotNull(testResult4);
		assertEquals(testResult4.size(), 10000);
		assertTrue(testResult4.contains("13812364444"));
		assertTrue(testResult4.contains("13887484444"));
		assertTrue(testResult4.contains("13803214444"));
		assertTrue(testResult4.contains("13823444444"));
		assertTrue(testResult4.contains("13891264444"));

	}
	
	
	public void testCase4() throws Exception {
		NumberGeneratorFactory factory = NumberGeneratorFactory.newInstance();
		
		String numberTemplateTest1 = "13011114444,13066661111,13099992222,1350??01200,13500001???";
		NumberGenerator generator1 = factory.getNumberGenerator(numberTemplateTest1);
		List<String> testResult1 = generator1.generate();
		assertNotNull(testResult1);
		assertEquals(testResult1.size(), 1103);
		assertTrue(testResult1.contains("13066661111"));
		assertTrue(testResult1.contains("13099992222"));
		assertTrue(testResult1.contains("13501201200"));
		assertTrue(testResult1.contains("13504301200"));
		assertTrue(testResult1.contains("13500001111"));
		assertTrue(testResult1.contains("13500001222"));
		assertTrue(testResult1.contains("13500001333"));
		assertTrue(testResult1.contains("13500001777"));

	}
	
    public void testCase5() throws Exception {
        NumberGeneratorFactory factory = NumberGeneratorFactory.newInstance();
        
        String numberTemplateTest1 = "13011114444";
        NumberGenerator generator1 = factory.getNumberGenerator(numberTemplateTest1);
        List<String> testResult1 = generator1.generate();
        assertNotNull(testResult1);
        assertEquals(1, testResult1.size());
        assertTrue(testResult1.contains("13011114444"));

    }
    
    public void testStringUtils() throws Exception {
      String a = null;
      String result = StringUtils.trimToEmpty(a);
      assertEquals("", result);
      
      a = " aaa ";
      result = StringUtils.trimToEmpty(a);
      assertEquals("aaa", result);

      a = "";
      result = StringUtils.trimToEmpty(a);
      assertEquals("", result);
    }
    
    public void testAPName() throws Exception {
      {
        String linkedPath = "./Application/AP/OTAS1";
        String result = null;
        int endIndex = linkedPath.lastIndexOf('/');
        if (endIndex > 0 && endIndex < linkedPath.length()) { 
           result = linkedPath.substring(endIndex + 1, linkedPath.length());
        }
        assertEquals("OTAS1", result);
      }
      {
        String linkedPath = "OTAS1";
        String result = null;
        int endIndex = linkedPath.lastIndexOf('/');
        if (endIndex > 0 && endIndex < linkedPath.length()) { 
           result = linkedPath.substring(endIndex + 1, linkedPath.length());
        }
        assertNull(result);
      }
      {
        String linkedPath = "./Application/AP/OTAS1/";
        String result = null;
        int endIndex = linkedPath.lastIndexOf('/');
        if (endIndex > 0 && endIndex < linkedPath.length()) { 
           result = linkedPath.substring(endIndex + 1, linkedPath.length());
        }
        assertEquals("", result);
      }
    }
    

}

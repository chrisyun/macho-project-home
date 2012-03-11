/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/multiplexor/MappingRuleBasedPerl5.java,v 1.1 2008/12/24 08:51:39 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/24 08:51:39 $
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

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.1 $ $Date: 2008/12/24 08:51:39 $
 */

public class MappingRuleBasedPerl5 extends MappingRule {

  /**
   * 
   */
  public MappingRuleBasedPerl5() {
    super();
  }

  /**
   * @param numberPattern
   * @param queueName
   */
  public MappingRuleBasedPerl5(String numberPattern, String queueName) {
    super(numberPattern, queueName);
  }

  public boolean match(String source) throws MalformedPatternException {
    PatternCompiler compiler = new Perl5Compiler();
    PatternMatcher matcher = new Perl5Matcher();
    return matcher.contains(source, compiler.compile(this.getNumberPattern()));
  }

}

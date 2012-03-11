/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/convertor/ValueFetcherChain.java,v 1.1 2007/08/29 10:06:15 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/08/29 10:06:15 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.cp.convertor;

import java.util.ArrayList;
import java.util.List;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/08/29 10:06:15 $
 */
public class ValueFetcherChain<Category, Key, Value> implements ValueFetcher<Category, Key, Value> {
  
  private List<ValueFetcher<Category, Key, Value>> fetchers = new ArrayList<ValueFetcher<Category, Key, Value>>();

  /**
   * 
   */
  public ValueFetcherChain() {
    super();
  }

  /**
   * @return the fetchers
   */
  public List<ValueFetcher<Category, Key, Value>> getFetchers() {
    return fetchers;
  }

  /**
   * @param fetchers the fetchers to set
   */
  public void setFetchers(List<ValueFetcher<Category, Key, Value>> fetchers) {
    this.fetchers = fetchers;
  }
  
  public void addFetcher(ValueFetcher<Category, Key, Value> fetcher) {
    this.fetchers.add(fetcher);
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ValueFetcher#getValue(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  public Value getValue(Category category, Key key, Value defaultValue) throws DMException {
    Value v = this.getValue(category, key);
    return (v == null)?defaultValue:v;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.convertor.ValueFetcher#getValue(java.lang.Object, java.lang.Object)
   */
  public Value getValue(Category category, Key key) throws DMException {
    for (ValueFetcher<Category, Key, Value> fetcher: this.getFetchers()) {
        Value v = fetcher.getValue(category, key);
        if (v != null) {
           return v;
        }
    }
    return null;
  }

}

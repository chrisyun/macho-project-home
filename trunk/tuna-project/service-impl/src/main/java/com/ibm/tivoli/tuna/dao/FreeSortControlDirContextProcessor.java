package com.ibm.tivoli.tuna.dao;

import javax.naming.ldap.Control;
import javax.naming.ldap.SortKey;

import org.springframework.ldap.control.SortControlDirContextProcessor;

public class FreeSortControlDirContextProcessor extends
		SortControlDirContextProcessor {
	
	private boolean ascend;
	
	public boolean isAscend() {
		return ascend;
	}

	public void setAscend(boolean ascend) {
		this.ascend = ascend;
	}

	public FreeSortControlDirContextProcessor(String sortKey) {
		super(sortKey);
	}
	
	public FreeSortControlDirContextProcessor(String sortKey, boolean ascend) {
		super(sortKey);
		this.ascend = ascend;
	}
	
	public Control createRequestControl() {
		return super.createRequestControl(new Class[] { SortKey[].class, boolean.class }, new Object[] {
				new SortKey[] { new SortKey(this.getSortKey(), ascend, null) }, Boolean.valueOf(critical) });
	}

}

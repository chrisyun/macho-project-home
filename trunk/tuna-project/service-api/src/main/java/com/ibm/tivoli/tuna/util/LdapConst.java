package com.ibm.tivoli.tuna.util;

public class LdapConst {
	
	public static int maxRowCount = 300;
	
	public static int maxSearchRow = 500;

	public int getMaxSearchRow() {
		return maxSearchRow;
	}

	public void setMaxSearchRow(int maxSearchRow) {
		LdapConst.maxSearchRow = maxSearchRow;
	}

	public int getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(int maxRowCount) {
		LdapConst.maxRowCount = maxRowCount;
	}
	
	
}

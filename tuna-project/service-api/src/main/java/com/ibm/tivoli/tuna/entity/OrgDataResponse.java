/**
 * 
 */
package com.ibm.tivoli.tuna.entity;

import javax.xml.bind.annotation.XmlElement;

import com.ibm.tivoli.tuna.service.Status;


/**
 * @author ZhaoDongLu
 *
 */
public class OrgDataResponse {
	
	private int countSize = 0;
	
	private Status status = new Status();
	
	private OrgData[] result = null;

	public OrgDataResponse() {
		super();
	}

	/**
	 * @param status
	 * @param result
	 */
	public OrgDataResponse(Status status, OrgData[] result) {
		super();
		this.status = status;
		this.result = result;
	}
	
	@XmlElement(name = "CountSize")
	public int getCountSize() {
		return countSize;
	}

	public void setCountSize(int countSize) {
		this.countSize = countSize;
	}
	
	@XmlElement(name = "Status")
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@XmlElement(name = "Org")
	public OrgData[] getResult() {
		return result;
	}

	public void setResult(OrgData[] result) {
		this.result = result;
	}

}

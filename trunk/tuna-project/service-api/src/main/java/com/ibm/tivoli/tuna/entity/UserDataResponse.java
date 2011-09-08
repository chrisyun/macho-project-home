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
public class UserDataResponse {
	
	private int countSize = 0;
	
	private Status status = new Status(Status.SUCCESS,"");

	private UserData[] result = null;

	public UserDataResponse() {
		super();
	}

	/**
	 * @param status
	 * @param result
	 */
	public UserDataResponse(Status status, UserData[] result) {
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

	@XmlElement(name = "User")
	public UserData[] getResult() {
		return result;
	}

	public void setResult(UserData[] result) {
		this.result = result;
	}

}

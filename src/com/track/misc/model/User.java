package com.track.misc.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	private int id;
	@Expose
	private String password;
	@Expose
	private String uname;
	@Expose
	private int role;
	@Expose
	private String telcode;
	@Expose
	private int status;
	@Expose
	private String dptid;
	@Expose
	private String receivepid;
	@Expose
	private String deliverpid;
	@Expose
	private String transpid;

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getTelcode() {
		return telcode;
	}

	public void setTelcode(String telCode) {
		this.telcode = telCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDptid() {
		return dptid;
	}

	public void setDptid(String dptid) {
		this.dptid = dptid;
	}

	public String getReceivepid() {
		return receivepid;
	}

	public void setReceivepid(String receivepid) {
		this.receivepid = receivepid;
	}

	public String getDeliverpid() {
		return deliverpid;
	}

	public void setDeliverpid(String deliverpid) {
		this.deliverpid = deliverpid;
	}

	public String getTranspid() {
		return transpid;
	}

	public void setTranspid(String transpid) {
		this.transpid = transpid;
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getId());
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("UserInfo[ ");
			sb.append("UID=").append(getId()).append(" ");
			sb.append("PWD=").append(getPassword()).append(" ");
			sb.append("Name=").append(getUname()).append(" ");
			sb.append("URull=").append(getRole()).append(" ");
			sb.append("TelCode=").append(getTelcode()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("DptID=").append(getDptid()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
}

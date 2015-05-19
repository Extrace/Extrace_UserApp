package com.track.misc.model;

public class User {
	private int id;
	private String password;
	private String uname;
	private int role;
	private String telCode;
	private int status;
	private String dptid;
	private String receivepid;
	private String deliverpid;
	private String transpid;

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

	public String getTelCode() {
		return telCode;
	}

	public void setTelCode(String telCode) {
		this.telCode = telCode;
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
			sb.append("TelCode=").append(getTelCode()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("DptID=").append(getDptid()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
}

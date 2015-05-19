package com.track.misc.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3267943602377867497L;

	public Customer() {
	}

	@Expose
	private int id;
	@Expose
	private String cname;
	@Expose
	private String telcode;
	@Expose
	private String department;
	@Expose
	private String regioncode;
	@Expose
	private String address;
	@Expose
	private int poscode;

	public int getORMID() {
		return getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getTelcode() {
		return telcode;
	}

	public void setTelcode(String telcode) {
		this.telcode = telcode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPoscode() {
		return poscode;
	}

	public void setPoscode(int poscode) {
		this.poscode = poscode;
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
			sb.append("CustomerInfo[ ");
			sb.append("ID=").append(getId()).append(" ");
			sb.append("Name=").append(getCname()).append(" ");
			sb.append("TelCode=").append(getTelcode()).append(" ");
			sb.append("Department=").append(getDepartment()).append(" ");
			sb.append("RegionCode=").append(getRegioncode()).append(" ");
			sb.append("RegionString=").append(getRegionString()).append(" ");
			sb.append("Address=").append(getAddress()).append(" ");
			sb.append("PostCode=").append(getPoscode()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	private String regionString;

	public String getRegionString() {
		return regionString;
	}

	public void setRegionString(String value) {
		this.regionString = value;
	}

	private boolean _saved = false;

	public void onSave() {
		_saved = true;
	}

	public void onLoad() {
		_saved = true;
	}

	public boolean isSaved() {
		return _saved;
	}
}

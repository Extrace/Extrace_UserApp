package com.track.misc.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class TransHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransHistory() {
	}

	@Expose
	private int sn;
	@Expose
	private Package packageid;
	@Expose
	private Date acttime;
	@Expose
	private Integer poscode;
	@Expose
	private int uidfrom;
	@Expose
	private int uidto;

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public int getORMId() {
		return getSn();
	}

	public Package getPackageid() {
		return packageid;
	}

	public void setPackageid(Package packageid) {
		this.packageid = packageid;
	}

	public Date getActtime() {
		return acttime;
	}

	public void setActtime(Date acttime) {
		this.acttime = acttime;
	}

	public Integer getPoscode() {
		return poscode;
	}

	public void setPoscode(Integer poscode) {
		this.poscode = poscode;
	}

	public int getUidfrom() {
		return uidfrom;
	}

	public void setUidfrom(int uidfrom) {
		this.uidfrom = uidfrom;
	}

	public int getUidto() {
		return uidto;
	}

	public void setUidto(int uidto) {
		this.uidto = uidto;
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getSn());
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("TransHistory[ ");
			sb.append("SN=").append(getSn()).append(" ");
			if (getPackageid() != null)
				sb.append("Packageid.Persist_ID=")
						.append(getPackageid().toString()).append(" ");
			else
				sb.append("Packageid=null ");
			sb.append("ActTime=").append(getActtime()).append(" ");
			sb.append("PosCode=").append(getPoscode()).append(" ");
			sb.append("UidFrom=").append(getUidfrom()).append(" ");
			sb.append("UidTo=").append(getUidto()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	private User userTo;

	private User userFrom;

	private Position position;

	public User getUserTo() {
		return userTo;
	}

	public void setUserTo(User userTo) {
		this.userTo = userTo;
	}

	public User getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(User userFrom) {
		this.userFrom = userFrom;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}

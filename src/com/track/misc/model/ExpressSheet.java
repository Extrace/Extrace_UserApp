package com.track.misc.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class ExpressSheet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4941503468986892397L;

	public ExpressSheet() {
	}

	@Expose private String ID;
	@Expose private int type;
	@Expose private Customer sender;	
	@Expose private Customer recever;
	
	@Expose private Float weight;
	
	@Expose private Float tranFee;
	@Expose private Float packageFee;
	@Expose private Float insuFee;
	@Expose private String accepter;
	@Expose private String deliver;
	@Expose private Date accepteTime;
	@Expose private Date deliveTime;
	@Expose private String acc1;
	@Expose private String acc2;
	@Expose private Integer status;
	
	public void setID(String value) {
		this.ID = value;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getORMID() {
		return getID();
	}
	
	public void setType(int value) {
		this.type = value;
	}
	
	public int getType() {
		return type;
	}
	
	public void setWeight(Float value) {
		this.weight = value;
	}
	
	public Float getWeight() {
		return weight;
	}
	
	public void setTranFee(Float value) {
		this.tranFee = value;
	}
	
	public Float getTranFee() {
		return tranFee;
	}
	
	public void setPackageFee(Float value) {
		this.packageFee = value;
	}
	
	public Float getPackageFee() {
		return packageFee;
	}
	
	public void setInsuFee(Float value) {
		this.insuFee = value;
	}
	
	public Float getInsuFee() {
		return insuFee;
	}
	
	public void setAccepter(String value) {
		this.accepter = value;
	}
	
	public String getAccepter() {
		return accepter;
	}
	
	public void setDeliver(String value) {
		this.deliver = value;
	}
	
	public String getDeliver() {
		return deliver;
	}
	
	public void setAccepterTime(Date value) {
		this.accepteTime = value;
	}
	
	public Date getAccepterTime() {
		return accepteTime;
	}
	
	public void setDeleveTime(Date value) {
		this.deliveTime = value;
	}
	
	public Date getDeleveTime() {
		return deliveTime;
	}
	
	public void setAcc1(String value) {
		this.acc1 = value;
	}
	
	public String getAcc1() {
		return acc1;
	}
	
	public void setAcc2(String value) {
		this.acc2 = value;
	}
	
	public String getAcc2() {
		return acc2;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setSender(Customer value) {
		this.sender = value;
	}
	
	public Customer getSender() {
		return sender;
	}
	
	public void setRecever(Customer value) {
		this.recever = value;
	}
	
	public Customer getRecever() {
		return recever;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("ExpreesSheet[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("Type=").append(getType()).append(" ");
			if (getSender() != null)
				sb.append("Sender.Persist_ID=").append(getSender().toString(true)).append(" ");
			else
				sb.append("Sender=null ");
			if (getRecever() != null)
				sb.append("Recever.Persist_ID=").append(getRecever().toString(true)).append(" ");
			else
				sb.append("Recever=null ");
			sb.append("Weight=").append(getWeight()).append(" ");
			sb.append("TranFee=").append(getTranFee()).append(" ");
			sb.append("PacgageFee=").append(getPackageFee()).append(" ");
			sb.append("InsuFee=").append(getInsuFee()).append(" ");
			sb.append("Accepter=").append(getAccepter()).append(" ");
			sb.append("Deliver=").append(getDeliver()).append(" ");
			sb.append("AccepterTime=").append(getAccepterTime()).append(" ");
			sb.append("DeleveTime=").append(getDeleveTime()).append(" ");
			sb.append("Acc1=").append(getAcc1()).append(" ");
			sb.append("Acc2=").append(getAcc2()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	private boolean _saved = false;
	
	public void onSave() {
		_saved=true;
	}
	
	
	public void onLoad() {
		_saved=true;
	}
	
	
	public boolean isSaved() {
		return _saved;
	}
	
	public static final class STATUS{
		public static final int STATUS_CREATED = 0;
		public static final int STATUS_RECEIVED = 1;
		public static final int STATUS_PARTATION = 2;
		public static final int STATUS_TRANSPORT = 3;
		public static final int STATUS_DISPATCHED = 4;
		public static final int STATUS_DELIVERIED = 5;
	}
	
}

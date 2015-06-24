package com.track.misc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ExpressSheet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4941503468986892397L;

	public ExpressSheet() {
	}

	@Expose
	private String id;
	@Expose
	private int expresstype;
	@Expose
	private Customer sender;
	@Expose
	private Customer receiver;

	@Expose
	private Float weight;

	@Expose
	private Float transcost;
	@Expose
	private Float packagecost;
	@Expose
	private Float insurcost;
	@Expose
	private String accepter;
	@Expose
	private String deliver;
	@Expose
	private Date acceptetime;
	@Expose
	private Date delivetime;
	@Expose
	private String acc1;
	@Expose
	private String acc2;
	@Expose
	private Integer status;

	public String getORMID() {
		return getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getExpresstype() {
		return expresstype;
	}

	public void setExpresstype(int expresstype) {
		this.expresstype = expresstype;
	}

	public Customer getSender() {
		return sender;
	}

	public void setSender(Customer sender) {
		this.sender = sender;
	}

	public Customer getReceiver() {
		return receiver;
	}

	public void setReceiver(Customer receiver) {
		this.receiver = receiver;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getTranscost() {
		return transcost;
	}

	public void setTranscost(Float transcost) {
		this.transcost = transcost;
	}

	public Float getPackagecost() {
		return packagecost;
	}

	public void setPackagecost(Float packagecost) {
		this.packagecost = packagecost;
	}

	public Float getInsurcost() {
		return insurcost;
	}

	public void setInsurcost(Float insurcost) {
		this.insurcost = insurcost;
	}

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public String getDeliver() {
		return deliver;
	}

	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}

	public Date getAcceptetime() {
		return acceptetime;
	}

	public void setAcceptetime(Date acceptetime) {
		this.acceptetime = acceptetime;
	}

	public Date getDelivetime() {
		return delivetime;
	}

	public void setDelivetime(Date delivetime) {
		this.delivetime = delivetime;
	}

	public String getAcc1() {
		return acc1;
	}

	public void setAcc1(String acc1) {
		this.acc1 = acc1;
	}

	public String getAcc2() {
		return acc2;
	}

	public void setAcc2(String acc2) {
		this.acc2 = acc2;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
			sb.append("ExpreesSheet[ ");
			sb.append("ID=").append(getId()).append(" ");
			sb.append("Type=").append(getExpresstype()).append(" ");
			if (getSender() != null)
				sb.append("Sender.Persist_ID=")
						.append(getSender().toString(true)).append(" ");
			else
				sb.append("Sender=null ");
			if (getReceiver() != null)
				sb.append("Recever.Persist_ID=")
						.append(getReceiver().toString(true)).append(" ");
			else
				sb.append("Recever=null ");
			sb.append("Weight=").append(getWeight()).append(" ");
			sb.append("TranFee=").append(getTranscost()).append(" ");
			sb.append("PacgageFee=").append(getPackagecost()).append(" ");
			sb.append("InsuFee=").append(getInsurcost()).append(" ");
			sb.append("Accepter=").append(getAccepter()).append(" ");
			sb.append("Deliver=").append(getDeliver()).append(" ");
			sb.append("AccepterTime=").append(getAcceptetime()).append(" ");
			sb.append("DeleveTime=").append(getDelivetime()).append(" ");
			sb.append("Acc1=").append(getAcc1()).append(" ");
			sb.append("Acc2=").append(getAcc2()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

	private List<Package> Package = new ArrayList<Package>();

	public List<Package> getPackage() {
		return Package;
	}

	public void setPackage(List<Package> package1) {
		Package = package1;
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

	public static final class STATUS {
		// 新建
		public static final int STATUS_CREATED = 0;
		// 揽收
		public static final int STATUS_RECEIVED = 1;
		// 分拣
		public static final int STATUS_PARTATION = 2;
		// 转运
		public static final int STATUS_TRANSPORT = 3;
		// 派送
		public static final int STATUS_DISPATCHED = 4;
		// 交付
		public static final int STATUS_DELIVERIED = 5;
	}

}

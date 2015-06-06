package com.track.misc.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class TransNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransNode() {
	}

	@Expose
	private String id;
	@Expose
	private String nodename;
	@Expose
	private int nodetype;
	@Expose
	private String regioncode;
	@Expose
	private Integer poscode;
	@Expose
	private String telcode;

	public String getORMId() {
		return getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public int getNodetype() {
		return nodetype;
	}

	public void setNodetype(int nodetype) {
		this.nodetype = nodetype;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public Integer getPoscode() {
		return poscode;
	}

	public void setPoscode(Integer poscode) {
		this.poscode = poscode;
	}

	public String getTelcode() {
		return telcode;
	}

	public void setTelcode(String telcode) {
		this.telcode = telcode;
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
			sb.append("TransNode[ ");
			sb.append("ID=").append(getId()).append(" ");
			sb.append("NodeName=").append(getNodename()).append(" ");
			sb.append("NodeType=").append(getNodetype()).append(" ");
			sb.append("RegionCode=").append(getRegioncode()).append(" ");
			sb.append("PosCode=").append(getPoscode()).append(" ");
			sb.append("TelCode=").append(getTelcode()).append(" ");
			if (getPosition() != null) {
				sb.append("Position=").append(getPosition().toString())
						.append(" ");
			} else {
				sb.append("Position=").append("null").append(" ");
			}
			sb.append("]");
			return sb.toString();
		}
	}

	private Position position;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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

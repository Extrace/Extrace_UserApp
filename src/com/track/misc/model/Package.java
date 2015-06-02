/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: 
 * License Type: Evaluation
 */
package com.track.misc.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class Package implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3050390478904210174L;

	public Package() {
	}

	@Expose
	private String id;
	@Expose
	private String sourcenode;
	@Expose
	private String targetnode;
	@Expose
	private java.sql.Timestamp createtime;
	@Expose
	private int status;

	public String getORMID() {
		return getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourcenode() {
		return sourcenode;
	}

	public void setSourcenode(String sourcenode) {
		this.sourcenode = sourcenode;
	}

	public String getTargetnode() {
		return targetnode;
	}

	public void setTargetnode(String targetnode) {
		this.targetnode = targetnode;
	}

	public java.sql.Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.sql.Timestamp createtime) {
		this.createtime = createtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
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
			sb.append("TransPackage[ ");
			sb.append("ID=").append(getId()).append(" ");
			sb.append("SourceNode=").append(getSourcenode()).append(" ");
			sb.append("TargetNode=").append(getTargetnode()).append(" ");
			sb.append("CreateTime=").append(getCreatetime()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("]");
			return sb.toString();
		}
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
		public static final int STATUS_CREATED = 0;
		public static final int STATUS_PACKED = 1;
		public static final int STATUS_TRANSPORT = 2;
		public static final int STATUS_PARTATION = 3;
		public static final int STATUS_DELIVED = 4;
		public static final int STATUS_DISPACHED = 5;
	}

}

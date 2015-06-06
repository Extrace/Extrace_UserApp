package com.track.misc.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Position() {
	}

	@Expose
	private int poscode;

	@Expose
	private float x;

	@Expose
	private float y;

	public int getPoscode() {
		return poscode;
	}

	public int getORMId() {
		return getPoscode();
	}

	public void setPoscode(int poscode) {
		this.poscode = poscode;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getPoscode());
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("Position[ ");
			sb.append("PosCode=").append(getPoscode()).append(" ");
			sb.append("X=").append(getX()).append(" ");
			sb.append("Y=").append(getY()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
}

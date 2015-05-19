package com.track.misc.model;

import java.io.Serializable;

public class Region implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2531774702426085013L;

	public Region() {
	}

	private String regioncode;

	private String province;

	private String city;

	private String town;

	private int stage;

	public String getORMID() {
		return getRegioncode();
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getRegioncode());
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("Region[ ");
			sb.append("RegionCode=").append(getRegioncode()).append(" ");
			sb.append("Prv=").append(getProvince()).append(" ");
			sb.append("Cty=").append(getCity()).append(" ");
			sb.append("Twn=").append(getTown()).append(" ");
			sb.append("Stage=").append(getStage()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
}

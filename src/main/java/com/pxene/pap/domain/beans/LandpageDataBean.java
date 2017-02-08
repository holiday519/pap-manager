package com.pxene.pap.domain.beans;

public class LandpageDataBean {
    private String id;

    private String name;

    private Long clickAmount;

    private Long arrivalAmount;
    
    private Float arrivalRate;

    private Long uniqueAmount;

    private Long residentTime;

    private Long jumpAmount;
    
    private Float jumpRate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getClickAmount() {
		return clickAmount;
	}

	public void setClickAmount(Long clickAmount) {
		this.clickAmount = clickAmount;
	}

	public Long getArrivalAmount() {
		return arrivalAmount;
	}

	public void setArrivalAmount(Long arrivalAmount) {
		this.arrivalAmount = arrivalAmount;
	}

	public Float getArrivalRate() {
		return arrivalRate;
	}

	public void setArrivalRate(Float arrivalRate) {
		this.arrivalRate = arrivalRate;
	}

	public Long getUniqueAmount() {
		return uniqueAmount;
	}

	public void setUniqueAmount(Long uniqueAmount) {
		this.uniqueAmount = uniqueAmount;
	}

	public Long getResidentTime() {
		return residentTime;
	}

	public void setResidentTime(Long residentTime) {
		this.residentTime = residentTime;
	}

	public Long getJumpAmount() {
		return jumpAmount;
	}

	public void setJumpAmount(Long jumpAmount) {
		this.jumpAmount = jumpAmount;
	}

	public Float getJumpRate() {
		return jumpRate;
	}

	public void setJumpRate(Float jumpRate) {
		this.jumpRate = jumpRate;
	}

	@Override
	public String toString() {
		return "LandpageDataBean [id=" + id + ", name=" + name
				+ ", clickAmount=" + clickAmount + ", arrivalAmount="
				+ arrivalAmount + ", arrivalRate=" + arrivalRate
				+ ", uniqueAmount=" + uniqueAmount + ", residentTime="
				+ residentTime + ", jumpAmount=" + jumpAmount + ", jumpRate="
				+ jumpRate + "]";
	}
    
}
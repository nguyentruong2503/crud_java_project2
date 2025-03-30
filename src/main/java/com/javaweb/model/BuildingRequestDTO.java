package com.javaweb.model;

public class BuildingRequestDTO {
	private String name;
	private String street;
	private String ward;
	private Long districtid;
	private String managerName;
	private String managerPhoneNumber;
	private Long floorArea;
	private Long numberOfBasement; 
	private Long rentPrice;
	private String serviceFee;
	private Long brokerageFee;
	public String getName() {
		return name;
	}
	public String getStreet() {
		return street;
	}
	public String getWard() {
		return ward;
	}
	public Long getDistrictid() {
		return districtid;
	}
	public String getManagerName() {
		return managerName;
	}
	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}
	public Long getFloorArea() {
		return floorArea;
	}
	public Long getNumberOfBasement() {
		return numberOfBasement;
	}
	public Long getRentPrice() {
		return rentPrice;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public Long getBrokerageFee() {
		return brokerageFee;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public void setDistrictid(Long districtid) {
		this.districtid = districtid;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}
	public void setFloorArea(Long floorArea) {
		this.floorArea = floorArea;
	}
	public void setNumberOfBasement(Long numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public void setRentPrice(Long rentPrice) {
		this.rentPrice = rentPrice;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public void setBrokerageFee(Long brokerageFee) {
		this.brokerageFee = brokerageFee;
	}
	
	
}

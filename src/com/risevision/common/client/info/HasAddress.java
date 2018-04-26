package com.risevision.common.client.info;

public interface HasAddress {
	void setAddressDescription(String addressDescription);
	String getAddressDescription();
	void setStreet(String street);
	String getStreet();
	void setUnit(String unit);
	String getUnit();
	void setCity(String city);
	String getCity();
	void setProvince(String province);
	String getProvince();
	void setCountry(String country);
	String getCountry();
	void setPostalCode(String postalCode);
	String getPostalCode();
	void setTimeZone(String timeZone);
	String getTimeZone();
}

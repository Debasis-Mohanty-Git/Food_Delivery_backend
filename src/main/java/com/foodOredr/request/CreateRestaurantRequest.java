package com.foodOredr.request;

import java.util.List;

import com.foodOredr.model.Address;
import com.foodOredr.model.ContactInformation;

public class CreateRestaurantRequest {
	
	private Long id;
	private String name;
	private String description;
	private String cuisinType;
	private Address address;
	private ContactInformation contactInformation;
	private String openingHours;
	private List<String> images;
	public CreateRestaurantRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CreateRestaurantRequest(Long id, String name, String description, String cuisinType, Address address,
			ContactInformation contactInformation, String openingHours, List<String> images) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.cuisinType = cuisinType;
		this.address = address;
		this.contactInformation = contactInformation;
		this.openingHours = openingHours;
		this.images = images;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCuisinType() {
		return cuisinType;
	}
	public void setCuisinType(String cuisinType) {
		this.cuisinType = cuisinType;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public ContactInformation getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
	}
	public String getOpeningHours() {
		return openingHours;
	}
	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	

}

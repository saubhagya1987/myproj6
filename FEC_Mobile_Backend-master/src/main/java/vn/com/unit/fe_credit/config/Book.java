package vn.com.unit.fe_credit.config;

public class Book {
	private String lastName;
	private String idCardNumber;
	private String primaryPhone;
	private String city;
	private String address;
	private String productCode;
	private String email;
	private String sourceInfo;
	private String campaign;
	
	public Book() {
	}
	
	public Book(String lastName, String idCardNumber, String primaryPhone, String city, String address, String productCode, String email,
			String sourceInfo, String campaign) {
		super();
		this.lastName = lastName;
		this.idCardNumber = idCardNumber;
		this.primaryPhone = primaryPhone;
		this.city = city;
		this.address = address;
		this.productCode = productCode;
		this.email = email;
		this.sourceInfo = sourceInfo;
		this.campaign = campaign;
	}


	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSourceInfo() {
		return sourceInfo;
	}

	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}
	
	
}
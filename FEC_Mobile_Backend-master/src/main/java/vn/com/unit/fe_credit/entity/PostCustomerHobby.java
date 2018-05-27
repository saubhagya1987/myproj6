package vn.com.unit.fe_credit.entity;

import java.util.List;

public class PostCustomerHobby {
	private String customerId;
	private DataImg avartar;

	public DataImg getAvartar() {
		return avartar;
	}

	public void setAvartar(DataImg avartar) {
		this.avartar = avartar;
	}

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	List<Hobby> list;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<Hobby> getList() {
		return list;
	}

	public void setList(List<Hobby> list) {
		this.list = list;
	}

	
}

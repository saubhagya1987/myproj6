package vn.com.unit.utils;

public enum OperationType {
RECORD("RECORD"),
CONTRACT_TERMINATION("CONTRACT TERMINATION"),
CONTRACT_REPOSSESSION("CONTRACT REPOSSESSION"), BIDDER("BIDDER");
	
	private final String value;
	private OperationType(String value) {
		// TODO Auto-generated constructor stub
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}

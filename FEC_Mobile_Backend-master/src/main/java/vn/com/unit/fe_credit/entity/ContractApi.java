package vn.com.unit.fe_credit.entity;

import java.util.Date;

public class ContractApi {

	public String getAPP_ID() {
		return APP_ID;
	}
	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}
	public String getCUSTOMER_ID() {
		return CUSTOMER_ID;
	}
	public void setCUSTOMER_ID(String cUSTOMER_ID) {
		CUSTOMER_ID = cUSTOMER_ID;
	}
	public String getCONTRACT_NUMBER() {
		return CONTRACT_NUMBER;
	}
	public void setCONTRACT_NUMBER(String cONTRACT_NUMBER) {
		CONTRACT_NUMBER = cONTRACT_NUMBER;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	public String getCUSTOMER_ID_CARD_NUMBER() {
		return CUSTOMER_ID_CARD_NUMBER;
	}
	public void setCUSTOMER_ID_CARD_NUMBER(String cUSTOMER_ID_CARD_NUMBER) {
		CUSTOMER_ID_CARD_NUMBER = cUSTOMER_ID_CARD_NUMBER;
	}
	public String getCREDIT_AMOUNT() {
		return CREDIT_AMOUNT;
	}
	public void setCREDIT_AMOUNT(String cREDIT_AMOUNT) {
		CREDIT_AMOUNT = cREDIT_AMOUNT;
	}
	public String getCONTRACT_STATUS() {
		return CONTRACT_STATUS;
	}
	public void setCONTRACT_STATUS(String cONTRACT_STATUS) {
		CONTRACT_STATUS = cONTRACT_STATUS;
	}
	public String getCONTRACT_TITLE() {
		return CONTRACT_TITLE;
	}
	public void setCONTRACT_TITLE(String cONTRACT_TITLE) {
		CONTRACT_TITLE = cONTRACT_TITLE;
	}
	
	public String getCONTRACT_TENURE() {
		return CONTRACT_TENURE;
	}
	public void setCONTRACT_TENURE(String cONTRACT_TENURE) {
		CONTRACT_TENURE = cONTRACT_TENURE;
	}
	public String getNEXT_INSTALLMENT_AMOUNT() {
		return NEXT_INSTALLMENT_AMOUNT;
	}
	public void setNEXT_INSTALLMENT_AMOUNT(String nEXT_INSTALLMENT_AMOUNT) {
		NEXT_INSTALLMENT_AMOUNT = nEXT_INSTALLMENT_AMOUNT;
	}
	
	public String getPRODUCT_INFORMATION() {
		return PRODUCT_INFORMATION;
	}
	public void setPRODUCT_INFORMATION(String pRODUCT_INFORMATION) {
		PRODUCT_INFORMATION = pRODUCT_INFORMATION;
	}
	public String getCONTRACT_FLAG() {
		return CONTRACT_FLAG;
	}
	public void setCONTRACT_FLAG(String cONTRACT_FLAG) {
		CONTRACT_FLAG = cONTRACT_FLAG;
	}
	
	public Date getACTIVE_DATE() {
		return ACTIVE_DATE;
	}
	public void setACTIVE_DATE(Date aCTIVE_DATE) {
		ACTIVE_DATE = aCTIVE_DATE;
	}
	public Date getCONTRACT_END_DATE() {
		return CONTRACT_END_DATE;
	}
	public void setCONTRACT_END_DATE(Date cONTRACT_END_DATE) {
		CONTRACT_END_DATE = cONTRACT_END_DATE;
	}
	public Date getDUE_DATE() {
		return DUE_DATE;
	}
	public void setDUE_DATE(Date dUE_DATE) {
		DUE_DATE = dUE_DATE;
	}

	private String APP_ID;
	private String CUSTOMER_ID;
	private String CONTRACT_NUMBER;
	private String CUSTOMER_NAME;
	private String CUSTOMER_ID_CARD_NUMBER;
	private String CREDIT_AMOUNT;
	private String CONTRACT_STATUS;
	private String CONTRACT_TITLE;
	private Date ACTIVE_DATE;
	private Date CONTRACT_END_DATE;
	private String CONTRACT_TENURE;
	private String NEXT_INSTALLMENT_AMOUNT;
	private Date DUE_DATE;
	private String PRODUCT_INFORMATION;
	private String CONTRACT_FLAG;

}

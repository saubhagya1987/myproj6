package vn.com.unit.fe_credit.config;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author CongDT
 */
@Data
public class DataExportRedeemPayment {

	private String refNum;

	private String contractNumber;

	private String customerName;

	private String description;

	private String paidDate;

	private String currency;

	private String paidAmountHeader;
	
	private BigDecimal paidAmount;

	private String customerBankAccount;

}

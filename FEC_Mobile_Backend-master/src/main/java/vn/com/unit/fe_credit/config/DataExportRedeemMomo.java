package vn.com.unit.fe_credit.config;

import java.math.BigDecimal;

/**
 * @author CongDT
 */

public class DataExportRedeemMomo {

	private String fullName;

	private String contractNo;

	private String idCardNo;

	private BigDecimal moneyWasConvert;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public BigDecimal getMoneyWasConvert() {
		return moneyWasConvert;
	}

	public void setMoneyWasConvert(BigDecimal moneyWasConvert) {
		this.moneyWasConvert = moneyWasConvert;
	}

}

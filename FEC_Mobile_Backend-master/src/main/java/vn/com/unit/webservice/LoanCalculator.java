package vn.com.unit.webservice;

public class LoanCalculator {

	private Double amountMin;
	private Double amountMax;
	private Double amountslide;	
	private Integer TenureMin;
	private Integer TenureMax;
	private Integer tenureperslide;
	private String tenure;
	private ProofList proofList;
	private Long loanID;
	
	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public Double getAmountslide() {
		return amountslide;
	}

	public void setAmountslide(Double amountslide) {
		this.amountslide = amountslide;
	}

	public Integer getTenureperslide() {
		return tenureperslide;
	}

	public void setTenureperslide(Integer tenureperslide) {
		this.tenureperslide = tenureperslide;
	}

	public Long getLoanID() {
		return loanID;
	}

	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}

	public Double getAmountMin() {
		return amountMin;
	}

	public void setAmountMin(Double amountMin) {
		this.amountMin = amountMin;
	}

	public Double getAmountMax() {
		return amountMax;
	}

	public void setAmountMax(Double amountMax) {
		this.amountMax = amountMax;
	}

	public Integer getTenureMin() {
		return TenureMin;
	}

	public void setTenureMin(Integer tenureMin) {
		TenureMin = tenureMin;
	}

	public Integer getTenureMax() {
		return TenureMax;
	}

	public void setTenureMax(Integer tenureMax) {
		TenureMax = tenureMax;
	}

	public ProofList getProofList() {
		return proofList;
	}

	public void setProofList(ProofList proofList) {
		this.proofList = proofList;
	}

}

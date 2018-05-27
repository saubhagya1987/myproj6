package vn.com.unit.fe_credit.bean;

public class ActitvityLogChartBean {

	private String name;
	private double y;
	private Integer referID;

	public Integer getReferID() {
		return referID;
	}

	public void setReferID(Integer referID) {
		this.referID = referID;
	}

	public ActitvityLogChartBean(String name, double y, Integer referID) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.y = y;
		this.referID = referID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}

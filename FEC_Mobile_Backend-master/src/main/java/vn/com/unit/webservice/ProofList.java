package vn.com.unit.webservice;

public class ProofList {

	private String id;

	private String text;

	private String text_en;

	private String text_vi;

	private Integer condition_value;

	public Integer getCondition_value() {
		return condition_value;
	}

	public void setCondition_value(Integer condition_value) {
		this.condition_value = condition_value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText_en() {
		return text_en;
	}

	public void setText_en(String text_en) {
		this.text_en = text_en;
	}

	public String getText_vi() {
		return text_vi;
	}

	public void setText_vi(String text_vi) {
		this.text_vi = text_vi;
	}

}

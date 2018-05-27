package vn.com.unit.fe_credit.utils.ajax;

/**
 * @author CongDT
 * @since Mar 2, 2015 4:50:38 PM
 *
 */
public class ReturnObject {

	public static final String ERROR = "error";
	public static final String INFO = "info";
	public static final String SUCCESS = "success";
	public static final String WARNING = "warning";

	private String status;

	private String message;

	private Object retObj;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getRetObj() {
		return retObj;
	}

	public void setRetObj(Object retObj) {
		this.retObj = retObj;
	}

}

package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.entity.ContractApi;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;

public class ContractDeatailApiBean extends AbstractBean<ContractApi> {

	private ContractApi contractdetailApi;
	private List<MessageDashBoard> messgesLst;
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<MessageDashBoard> getMessgesLst() {
		return messgesLst;
	}

	public void setMessgesLst(List<MessageDashBoard> messgesLst) {
		this.messgesLst = messgesLst;
	}

	public ContractApi getContractdetailApi() {
		return contractdetailApi;
	}

	public void setContractdetailApi(ContractApi contractdetailApi) {
		this.contractdetailApi = contractdetailApi;
	}

}

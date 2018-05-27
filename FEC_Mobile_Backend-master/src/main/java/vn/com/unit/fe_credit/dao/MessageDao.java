package vn.com.unit.fe_credit.dao;

import java.util.List;
import java.util.Map;

import vn.com.unit.fe_credit.bean.MessageDashBoardBean;
import vn.com.unit.fe_credit.bean.MessageInboxBean;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface MessageDao extends GenericDAO<MessageDashBoard, Long> {

	MessageDashBoardBean search(MessageDashBoardBean bean);

	MessageDashBoardBean searchImport(MessageDashBoardBean bean);

	List<MessageDashBoard> findByCustomerId(Long customerId);

	List<MessageDashBoard> findByCustomerIdAndContractMSID(String customerId, String contractmsgId);

	List<MessageDashBoard> findByCustomerIdNoCon(Long customerId);

	List<MessageDashBoard> findByCustomerIdConTract(Long customerId);

	Map<String, Object> getInboxList(Long customerId, Integer category, String typeInbox, int typeSearch, int page, int limit);

	List<Customer> search(Long typeId);

	List<MessageInboxBean> getMessageDetail(Long messageID, Long customerId);

	int assignAccountId(Long parentMsgId);

	List<MessageDashBoard> checkMessageNew();

	int changeStatusMsg(int status, Long customerId, String reason, Long accountId, Long typeId, Long parentMsgId);

	List<MessageInboxBean> getListMessage(Long customerId);

	List<MessageDashBoard> getLstMessageByCustomerId(Long customerId, Long typeId, Long parentMsgId, Long accountId, Boolean dashboard,
			Long messageId);

	int changeStatusIsRead(Long parentMsgId);

	MessageDashBoard findParentCustomerIdConTract(Long customerId, String contractId);

	void assignParentToMessageContract(String contractMsgId, Long parentMsgId);

	MessageDashBoard findParentCustomerIdparentMsgId(Long customerId, Long parentMsgId);

	List<MessageInboxBean> getListMessageContract(Long customerId, String contractId);

	List<MessageInboxBean> getMessageDetailContract(Long messageID, Long customerId);

	List<MessageDashBoard> findbyTypeOfUser();

	void savePushMessage(MessageDashBoard item);
}

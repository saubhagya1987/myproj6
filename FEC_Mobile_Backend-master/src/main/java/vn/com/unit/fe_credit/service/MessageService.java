package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.fe_credit.bean.MessageDashBoardBean;
import vn.com.unit.fe_credit.bean.MessageInboxBean;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;

public interface MessageService {

	MessageDashBoard findMessagesByIdAndType(Long messagesID, String messagesType);

	void save(MessageDashBoard message);

	void saveMessageDashBoard(List<MessageDashBoard> messageDashBoard) throws Exception;

	void deleteMessageDashBoard(List<MessageDashBoard> messageDashBoard) throws Exception;

	MessageDashBoardBean search(MessageDashBoardBean bean);

	MessageDashBoardBean searchImport(MessageDashBoardBean bean);

	MessageDashBoard findById(Long messagesID);

	List<MessageDashBoard> findByCustomerId(Long customerId);

	List<MessageDashBoard> findByCustomerIdAndContractMSID(String customerId, String contractmsgId);

	List<MessageDashBoard> findByCustomerIdNoCon(Long customerId);

	List<MessageDashBoard> findByCustomerIdConTract(Long customerId);

	List<MessageDashBoard> findAll();

	List<Customer> findAll(Long typeId);

	Map<String, Object> getInboxList(Long customerId, Integer category, String typeInbox, int typeSearch, int page, int limit);

	MessageDashBoard findMessagesWebService(Long messagesID, Long customerID);

	void delete(MessageDashBoard message);

	List<MessageInboxBean> getMessageDetail(Long messageID, Long customerId);

	boolean saveMsg(MessageDashBoard msg);

	int assignAccountId(Long parentMsgId);

	List<MessageDashBoard> checkMessageNew();

	int changeStatusMsg(int status, Long customerId, String reason, Long accountId, Long typeId, Long parentMsgId);

	List<MessageInboxBean> getListMessage(Long customerId);

	List<MessageDashBoard> getLstMessageByCustomerId(Long customerId, Long typeId, Long parentMsgId, Long accountId, Boolean dashboard,
			Long messageId) throws Exception;

	int changeStatusIsRead(Long parentMsgId);

	MessageDashBoard findParentCustomerIdConTract(Long customerId, String string);

	List<MessageDashBoard> findbycontractMsgId(String string);

	List<MessageDashBoard> findImportAuto(String string);

	MessageDashBoard findParentCustomerIdparentMsgId(Long customerId, Long parentMsgId);

	void assignParentToMessageContract(String contractMsgId, Long parentMsgId);

	List<MessageInboxBean> getMessageDetailContract(Long messageID, Long customerId);

	List<MessageDashBoard> findbyTypeOfUser();

	List<MessageInboxBean> getListMessageContract(Long customerId, String contractId);

	MessageDashBoard findMessByMessageId(Long messageId);

	void savePushMessage(List<MessageDashBoard> message) throws Exception;

}

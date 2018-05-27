package vn.com.unit.fe_credit.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.MessageDashBoardBean;
import vn.com.unit.fe_credit.bean.MessageInboxBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.MessageDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.MessageService;

import com.googlecode.genericdao.search.Search;

@Service("messagesService")
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

	@Autowired
	AccountService accountService;

	@Autowired
	CustomerService customerService;

	@Autowired
	MessageDao messagesDao;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private UserProfile userProfile;

	@Override
	@Transactional
	public void save(MessageDashBoard message) {
		messagesDao.save(message);

	}

	@Override
	@Transactional
	public void delete(MessageDashBoard message) {
		messagesDao.remove(message);

	}

	@Override
	public MessageDashBoardBean search(MessageDashBoardBean bean) {

		return messagesDao.search(bean);
	}

	@Override
	public MessageDashBoard findMessagesByIdAndType(Long messagesID, String messagesType) {
		Search search = new Search(MessageDashBoard.class);
		if (messagesID != null) {
			search.addFilterEqual("messageID", messagesID);
		}
		if (!StringUtils.isEmpty(messagesType)) {
			search.addFilterEqual("type", messagesType);
		}

		return messagesDao.searchUnique(search);
	}

	@Override
	public List<MessageDashBoard> findbycontractMsgId(String contractMsgId) {
		Search search = new Search(MessageDashBoard.class);
		search.addFilterEqual("contractMsgId", contractMsgId);
		return messagesDao.search(search);
	}

	@Override
	public List<MessageDashBoard> findbyTypeOfUser() {
		return messagesDao.findbyTypeOfUser();
	}

	@Override
	public MessageDashBoard findMessagesWebService(Long messagesID, Long customerID) {

		Search search = new Search(MessageDashBoard.class);
		search.addFilterEqual("messageID", messagesID);
		search.addFilterEqual("customer.customerId", customerID);

		return messagesDao.searchUnique(search);
	}

	@Override
	public Map<String, Object> getInboxList(Long customerId, Integer category, String typeInbox, int typeSearch, int page, int limit) {

		return messagesDao.getInboxList(customerId, category, typeInbox, typeSearch, page, limit);

	}

	@Override
	public MessageDashBoard findById(Long messagesID) {
		return messagesDao.find(messagesID);
	}

	@Override
	public List<MessageDashBoard> findByCustomerId(Long customerId) {
		return messagesDao.findByCustomerId(customerId);
	}

	@Override
	public MessageDashBoard findMessByMessageId(Long messageId) {
		return messagesDao.find(messageId);
	}

	@Override
	public MessageDashBoard findParentCustomerIdConTract(Long customerId, String contractId) {
		return messagesDao.findParentCustomerIdConTract(customerId, contractId);
	}

	@Override
	public MessageDashBoard findParentCustomerIdparentMsgId(Long customerId, Long parentMsgId) {
		return messagesDao.findParentCustomerIdparentMsgId(customerId, parentMsgId);
	}

	@Override
	public List<MessageDashBoard> findAll() {
		return messagesDao.findAll();
	}

	@Override
	public List<Customer> findAll(Long typeId) {
		try {
			return messagesDao.search(typeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<MessageInboxBean> getMessageDetail(Long messageID, Long customerId) {
		List<MessageInboxBean> messageInboxBeans = messagesDao.getMessageDetail(messageID, customerId);
		for (MessageInboxBean messageInboxBean : messageInboxBeans) {

			Customer customer = customerService.findById(messageInboxBean.getCustomerID());

			if (customer != null) {
				messageInboxBean
						.setImgPathCustomer(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + customer.getImagePath());
			} else {
				messageInboxBean.setImgPathCustomer(null);
			}

			if (messageInboxBean.getIsmsgUser().equals("0")) {
				if (messageInboxBean.getAccountID() != null) {
					Account account = accountService.findById(messageInboxBean.getAccountID());
					if (account != null) {
						messageInboxBean.setImgPathAdmin(
								systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + account.getImagePath());
					} else {
						messageInboxBean.setImgPathAdmin(null);
					}
				}

			} else {
				messageInboxBean.setImgPathAdmin(null);
			}
		}
		return messageInboxBeans;
	}

	@Override
	public List<MessageInboxBean> getMessageDetailContract(Long messageID, Long customerId) {
		List<MessageInboxBean> messageInboxBeans = messagesDao.getMessageDetailContract(messageID, customerId);
		for (MessageInboxBean messageInboxBean : messageInboxBeans) {

			Customer customer = customerService.findById(messageInboxBean.getCustomerID());
			if (customer != null) {
				messageInboxBean
						.setImgPathCustomer(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + customer.getImagePath());
			} else {
				messageInboxBean.setImgPathCustomer(null);
			}

			if (messageInboxBean.getIsmsgUser().equals("0")) {
				if (messageInboxBean.getAccountID() != null) {
					Account account = accountService.findById(messageInboxBean.getAccountID());
					if (account != null) {
						messageInboxBean.setImgPathAdmin(
								systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + account.getImagePath());
					} else {
						messageInboxBean.setImgPathAdmin(null);
					}
				}

			} else {
				messageInboxBean.setImgPathAdmin(null);
			}
		}
		return messageInboxBeans;
	}

	@Override
	@Transactional
	public boolean saveMsg(MessageDashBoard msg) {
		try {
			return messagesDao.save(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int assignAccountId(Long parentMsgId) {
		return messagesDao.assignAccountId(parentMsgId);
	}

	@Override
	public List<MessageDashBoard> checkMessageNew() {
		return messagesDao.checkMessageNew();
	}

	@Override
	public int changeStatusMsg(int status, Long customerId, String reason, Long accountId, Long typeId, Long parentMsgId) {
		return messagesDao.changeStatusMsg(status, customerId, reason, accountId, typeId, parentMsgId);
	}

	@Override
	public List<MessageInboxBean> getListMessage(Long customerId) {
		return messagesDao.getListMessage(customerId);
	}

	@Override
	public List<MessageInboxBean> getListMessageContract(Long customerId, String contractId) {
		return messagesDao.getListMessageContract(customerId, contractId);
	}

	@Override
	public List<MessageDashBoard> getLstMessageByCustomerId(Long customerId, Long typeId, Long parentMsgId, Long accountId, Boolean dashboard,
			Long messageId) throws Exception {

		return messagesDao.getLstMessageByCustomerId(customerId, typeId, parentMsgId, accountId, dashboard, messageId);

	}

	@Override
	public List<MessageDashBoard> findByCustomerIdNoCon(Long customerId) {
		return messagesDao.findByCustomerIdNoCon(customerId);
	}

	@Override
	public List<MessageDashBoard> findByCustomerIdConTract(Long customerId) {
		return messagesDao.findByCustomerIdConTract(customerId);
	}

	@Override
	@Transactional
	public int changeStatusIsRead(Long parentMsgId) {
		return messagesDao.changeStatusIsRead(parentMsgId);
	}

	@Override
	public List<MessageDashBoard> findByCustomerIdAndContractMSID(String customerId, String contractmsgId) {
		return messagesDao.findByCustomerIdAndContractMSID(customerId, contractmsgId);
	}

	@Override
	public void assignParentToMessageContract(String contractMsgId, Long parentMsgId) {
		messagesDao.assignParentToMessageContract(contractMsgId, parentMsgId);

	}

	@Override
	@Transactional
	public void saveMessageDashBoard(List<MessageDashBoard> messageDashBoard) throws Exception {
		if (CollectionUtils.isEmpty(messageDashBoard)) {
			return;
		}
		try {
			messagesDao.save(messageDashBoard.toArray(new MessageDashBoard[messageDashBoard.size()]));
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	@Override
	@Transactional
	public void deleteMessageDashBoard(List<MessageDashBoard> messageDashBoard) throws Exception {
		if (CollectionUtils.isEmpty(messageDashBoard)) {
			return;
		}
		try {
			messagesDao.remove(messageDashBoard.toArray(new MessageDashBoard[messageDashBoard.size()]));
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	@Override
	public MessageDashBoardBean searchImport(MessageDashBoardBean bean) {
		return messagesDao.searchImport(bean);
	}

	@Override
	public List<MessageDashBoard> findImportAuto(String string) {
		Search search = new Search(MessageDashBoard.class);
		search.addFilterEqual("deviceID", string);
		return messagesDao.search(search);
	}

	@Override
	public void savePushMessage(List<MessageDashBoard> message) throws Exception {
		if (CollectionUtils.isEmpty(message)) {
			return;
		}
		try {
			for (MessageDashBoard item : message) {
				messagesDao.savePushMessage(item);
			}
			// messagesDao.save(message.toArray(new MessageDashBoard[message.size()]));
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

}

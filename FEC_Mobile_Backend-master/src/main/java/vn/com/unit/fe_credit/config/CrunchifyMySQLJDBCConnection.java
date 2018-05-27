package vn.com.unit.fe_credit.config;

import static vn.com.unit.fe_credit.config.SystemConfig.DRIVERCLASS;
import static vn.com.unit.fe_credit.config.SystemConfig.JDBCURL;
import static vn.com.unit.fe_credit.config.SystemConfig.PASSWORD_KH;
import static vn.com.unit.fe_credit.config.SystemConfig.USER_KH;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.POSService;

public class CrunchifyMySQLJDBCConnection extends SpringBeanAutowiringSupport {

	Logger logger = LoggerFactory.getLogger(CrunchifyMySQLJDBCConnection.class);

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	POSService posService;

	@Autowired
	CustomerService customerService;

	static Connection crunchifyConn = null;

	static Statement crunchifyStmt = null;

	static Statement crunchifyStmtBRANCH = null;

	static ResultSet crunchifyResultsetPOS = null;

	static ResultSet crunchifyResultsetBRANCH = null;

	static ResultSet crunchifyResultsetCustomer = null;

	public void saveDAO() throws SQLException {
		try {
			// Returns the Class object associated with the class
			Class.forName(systemConfig.getConfig(DRIVERCLASS));
		} catch (ClassNotFoundException exception) {
			return;
		}

		// Set connection timeout. Make sure you set this correctly as per your need
		DriverManager.setLoginTimeout(5);

		try {
			// Attempts to establish a connection
			// here DB name: localhost, sid: crunchify
			crunchifyConn = DriverManager.getConnection(systemConfig.getConfig(JDBCURL), systemConfig.getConfig(USER_KH),
					systemConfig.getConfig(PASSWORD_KH));
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		// Creates a Statement object for sending SQL statements to the database
		crunchifyStmt = crunchifyConn.createStatement();
		// Executes the given SQL statement, which returns a single ResultSet object
		crunchifyResultsetPOS = crunchifyStmt.executeQuery("SELECT POS_NAME,ADDRESS,POS_CODE from MOBILE_POS");
		try {
			while (crunchifyResultsetPOS.next()) {
				PosEmtity addValue = new PosEmtity();
				String posName = crunchifyResultsetPOS.getString("POS_NAME");
				String address = crunchifyResultsetPOS.getString("ADDRESS");
				String poscode = crunchifyResultsetPOS.getString("POS_CODE");
				List<PosEmtity> checkDATA = posService.findCodePosBranch(poscode);
				if (checkDATA.isEmpty()) {
					addValue.setPos_name(posName);
					addValue.setAddress_number(address);
					addValue.setCode_branch_pos(poscode);
					addValue.setBuyOrPay("buy");
					// luu data
					posService.savePOSAuto(addValue);
				}

			}
		} catch (Exception e) {
			logger.debug("##Exception##", e);
			throw new SQLException("Can NOT retrieve Employee details from table 'CrunchifyEmployee'");
		}

		// try {
		// crunchifyResultsetBRANCH = crunchifyStmt.executeQuery("SELECT PARTNER_NAME,BRANCH_NAME,ADDRESS,BRANCH_CODE
		// from MOBILE_RECOLLECTION_BRANCH");
		// } catch (Exception e) {
		// // Do nothing
		// // DB khách hàng thiếu 2 cột ADDRESS,BRANCH_CODE
		// }
		// try {
		// while (crunchifyResultsetBRANCH.next()) {
		// PosEmtity addValue = new PosEmtity();
		// String posName = crunchifyResultsetBRANCH.getString("BRANCH_NAME");
		// String address = crunchifyResultsetBRANCH.getString("ADDRESS");
		// String poscode = crunchifyResultsetBRANCH.getString("BRANCH_CODE");
		// String branchNamePos = crunchifyResultsetBRANCH.getString("PARTNER_NAME");
		// List<PosEmtity> checkDATA =posService.findCodePosBranch(poscode);
		// if (checkDATA.isEmpty()) {
		// addValue.setPos_name(posName);
		// addValue.setAddress_number(address);
		// addValue.setCode_branch_pos(poscode);
		// addValue.setBranch_namepos(branchNamePos);
		// addValue.setBuyOrPay("pay");
		// // luu data
		// posService.savePOS(addValue);
		// }
		//
		// }
		// } catch (Exception e) {
		// throw new SQLException(e.getMessage());
		// }

	}

	public void saveCustomer(String idCardNumber, Long idCustomer) throws SQLException {
		try {
			// Returns the Class object associated with the class
			Class.forName(systemConfig.getConfig(DRIVERCLASS));
		} catch (ClassNotFoundException exception) {
			return;
		}

		// Set connection timeout. Make sure you set this correctly as per your need
		DriverManager.setLoginTimeout(5);

		try {
			// Attempts to establish a connection
			// here DB name: localhost, sid: crunchify
			crunchifyConn = DriverManager.getConnection(systemConfig.getConfig(JDBCURL), systemConfig.getConfig(USER_KH),
					systemConfig.getConfig(PASSWORD_KH));
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		// Creates a Statement object for sending SQL statements to the database
		crunchifyStmt = crunchifyConn.createStatement();
		// Executes the given SQL statement, which returns a single ResultSet object
		crunchifyResultsetCustomer = crunchifyStmt.executeQuery(
				" SELECT MOBILE_CUSTOMER.CUSTOMER_ID,FIRST_NAME ,LAST_NAME," + " MIDDLE_NAME, ID_CARD_NUMBER ,CELL_PHONE ,EMAIL_ADDRESS, "
						+ " STREET ,ADDRESS_NO,WARD,DISTRICT,PROVINCE_NAME " + "FROM MOBILE_CUSTOMER LEFT JOIN  MOBILE_CUSTOMER_ADDRESS "
						+ " ON MOBILE_CUSTOMER.CUSTOMER_ID=MOBILE_CUSTOMER_ADDRESS.CUSTOMER_ID WHERE ROWNUM <=1"
						+ " AND MOBILE_CUSTOMER.ID_CARD_NUMBER='" + idCardNumber + "'");
		try {
			while (crunchifyResultsetCustomer.next()) {
				Customer addValue = customerService.findById(idCustomer);
				if (addValue != null) {
					String CUSTOMER_ID = crunchifyResultsetCustomer.getString("CUSTOMER_ID");
					String FIRST_NAME = crunchifyResultsetCustomer.getString("FIRST_NAME");
					String LAST_NAME = crunchifyResultsetCustomer.getString("LAST_NAME");
					String MIDDLE_NAME = crunchifyResultsetCustomer.getString("MIDDLE_NAME");
					String ID_CARD_NUMBER = crunchifyResultsetCustomer.getString("ID_CARD_NUMBER");
					String CELL_PHONE = crunchifyResultsetCustomer.getString("CELL_PHONE");
					String STREET = crunchifyResultsetCustomer.getString("STREET");
					String ADDRESS_NO = crunchifyResultsetCustomer.getString("ADDRESS_NO");
					String WARD = crunchifyResultsetCustomer.getString("WARD");
					String DISTRICT = crunchifyResultsetCustomer.getString("DISTRICT");
					String PROVINCE_NAME = crunchifyResultsetCustomer.getString("PROVINCE_NAME");
					String EMAIL_ADDRESS = crunchifyResultsetCustomer.getString("EMAIL_ADDRESS");
					if (StringUtils.isNotEmpty(ADDRESS_NO) && ADDRESS_NO.equalsIgnoreCase("None")) {
						ADDRESS_NO = null;
					}
					if (StringUtils.isNotEmpty(STREET) && STREET.equalsIgnoreCase("None")) {
						STREET = null;
					}
					if (StringUtils.isNotEmpty(WARD) && WARD.equalsIgnoreCase("None")) {
						WARD = null;
					}
					if (StringUtils.isNotEmpty(DISTRICT) && DISTRICT.equalsIgnoreCase("None")) {
						DISTRICT = null;
					}
					if (StringUtils.isNotEmpty(PROVINCE_NAME) && PROVINCE_NAME.equalsIgnoreCase("None")) {
						PROVINCE_NAME = null;
					}
					
					crunchifyStmt.executeUpdate("UPDATE MOBILE_CUSTOMER SET DEVICE_INSTALLED = '1' WHERE CUSTOMER_ID = '" + CUSTOMER_ID + "'");

					addValue.setOldUserId(CUSTOMER_ID);
					addValue.setFirstName(FIRST_NAME);
					addValue.setLastName(LAST_NAME);
					addValue.setMiddleName(MIDDLE_NAME);
					addValue.setIdCardNumber(ID_CARD_NUMBER);
					String fullNameAdd = "";
					if (StringUtils.isNotEmpty(FIRST_NAME)) {
						fullNameAdd += FIRST_NAME += " ";
					}
					if (StringUtils.isNotEmpty(MIDDLE_NAME)) {
						fullNameAdd += MIDDLE_NAME += " ";
					}
					if (StringUtils.isNotEmpty(LAST_NAME)) {
						fullNameAdd += LAST_NAME;
					}
					addValue.setFullName(fullNameAdd);
					if (CELL_PHONE != null) {
						String cutCellPhone = CELL_PHONE.substring(0, 2);
						String endCellPhone = CELL_PHONE.substring(2, CELL_PHONE.length());
						String newCellPhone = "";
						if (cutCellPhone.equalsIgnoreCase("84")) {
							newCellPhone = "0" + endCellPhone;
						} else {
							newCellPhone = CELL_PHONE;

						}
						addValue.setCellPhone(newCellPhone);
					} else {
						addValue.setCellPhone(CELL_PHONE);
					}

					addValue.setEmailAddress(EMAIL_ADDRESS);
					addValue.setStreet(STREET);
					addValue.setAddressNo(ADDRESS_NO);
					addValue.setWard(WARD);
					addValue.setDistrict(DISTRICT);
					addValue.setProvince(PROVINCE_NAME);
					try {
						customerService.saveCustomerAccount(addValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}

	}

}
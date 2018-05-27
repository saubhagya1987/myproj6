package vn.com.unit.fe_credit.service.impl;

import static vn.com.unit.fe_credit.config.SystemConfig.UPLOAD_APPLYNOW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import vn.com.unit.fe_credit.bean.ApplyNowBean;
import vn.com.unit.fe_credit.config.Book;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.ApplyNowDao;
import vn.com.unit.fe_credit.entity.ApplyNow;
import vn.com.unit.fe_credit.service.ApplyNowService;
import vn.com.unit.fe_credit.utils.Utils;

@Service("ApplyNowService")
@Transactional
public class ApplyNowServiceImpl implements ApplyNowService {

	static final Logger logger = LoggerFactory.getLogger(ApplyNowServiceImpl.class);

	@Autowired
	ApplyNowDao applyNowDao;

	@Autowired
	SystemConfig systemConfig;

	public ApplyNowServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public List<Object[]> search(ApplyNowBean bean) {
		List<Object[]> searchResult = applyNowDao.search(bean);
		return searchResult;
	}

	@Override
	public Integer countSearch(ApplyNowBean bean) {
		// TODO Auto-generated method stub
		return applyNowDao.countSearch(bean);
	}

	@Override
	@Transactional
	public ApplyNowBean searchEx(ApplyNowBean bean) {
		// TODO Auto-generated method stub
		Search search = new Search(ApplyNow.class);

		if (bean.getSubmitedFrom() != null) {
			search.addFilterGreaterOrEqual("submittedDate", Utils.setTimeToZero(bean.getSubmitedFrom()));
		}
		if (bean.getSubmitedTo() != null) {
			search.addFilterLessOrEqual("submittedDate", Utils.setTimeToMax(bean.getSubmitedTo()));
		}
		/*
		 * if (bean.getStatus() != 0) { search.addFilterEqual("status", bean.getStatus()); }
		 */
		search.addFilterEqual("status", 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(), "desc".equalsIgnoreCase(bean.getSort()));
		} else {
			search.addSortDesc("applyNowID", true);
		}

		if (bean.getLimit() > 0) {
			search.setMaxResults(bean.getLimit());
		}

		SearchResult<ApplyNow> searchResult = applyNowDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());

		bean.setTotal(searchResult.getTotalCount());

		return bean;
	}

	@Transactional
	@Override
	public ApplyNow findById(Long id) {
		// TODO Auto-generated method stub
		return applyNowDao.find(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveApplyNow(ApplyNow entity) {
		applyNowDao.save(entity);
	}

	@Override
	public List<ApplyNow> searchApplyNow(long customerId, Long loanId, String product, long status) throws Exception {

		Search search = new Search(ApplyNow.class);
		search.addFilterEqual("customer.customerId", customerId);
		if (loanId != null) {
			search.addFilterEqual("loan.loanID", loanId);
		}
		if (StringUtils.isNotBlank(product)) {
			search.addFilterEqual("product", product);
		}
		search.addFilterEqual("status", status);
		return applyNowDao.search(search);

	}

	@Override
	public void exportCSVToVTiger(ApplyNowBean applyNowBean) throws Exception {

		System.out.println("##exportCSVToVTiger##");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'_'HHmmss");
		String csvFileName = "ApllyNow_MobileApp" + "_" + sdf.format(new Date()) + ".csv";

		applyNowBean.setLimit(20000);
		ApplyNowBean resultBean = this.searchEx(applyNowBean);
		List<ApplyNow> applyNowList = resultBean.getListResult();

		if (CollectionUtils.isNotEmpty(applyNowList)) {

			// creates mock data
			try {

				String filePath = systemConfig.getConfig(UPLOAD_APPLYNOW);
				// filePath = "d:\\";
				String pathToFileTmp = filePath + csvFileName;
				File fileDir = new File(pathToFileTmp);
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));
				writer.write("\ufeff");

				try (ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {

					String[] header = { "LastName", "IdCardNumber", "PrimaryPhone", "City", "Address", "ProductCode", "Email", "SourceInfo",
							"Campaign" };
					csvWriter.writeHeader(header);

					try {
						for (ApplyNow applyNow : applyNowList) {

							applyNow.setExportedDate(new Date());
							applyNow.setUpdate_date(new Date());
							applyNow.setStatus(2l);

							saveApplyNow(applyNow);

							Book book4 = new Book(applyNow.getFullName(), applyNow.getIdCardNumber(), applyNow.getCellPhone(), applyNow.getCity(),
									applyNow.getAddress(), applyNow.getProduct(), applyNow.getEmail(), "Mobile Application", "Mobile Application");
							csvWriter.write(book4, header);

						}
					} catch (Exception e) {
						try {
							Path file = Paths.get(pathToFileTmp + ".txt");
							Files.write(file, e.toString().getBytes(Charset.forName("UTF-8")));
						} catch (Exception e2) {
							// Do something
						}
						logger.debug("##exportCSVToVTiger##", e);
						csvWriter.flush();
					}

				}

				try {
					// String path = systemConfig.getConfig(SystemConfig.VTIGER_PATH);
					String pathOther = systemConfig.getConfig(SystemConfig.VTIGER_PATH_OTHER);
					String urlDest = "smb:" + pathOther + csvFileName;
					String domainLDAP = systemConfig.getConfig(SystemConfig.CONFIG_LDAP_DOMAIN);
					String userLDAP = systemConfig.getConfig(SystemConfig.USER_LDAP);
					String passLDAP = systemConfig.getConfig(SystemConfig.PASSWORD_LDAP);
					NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domainLDAP, userLDAP, passLDAP);
					SmbFile dest = new SmbFile(urlDest, auth);

					try (SmbFileOutputStream sfos = new SmbFileOutputStream(dest)) {
						sfos.write(IOUtils.toByteArray(new FileInputStream(fileDir)));
					} catch (Exception e) {
						throw e;
					}
				} catch (Exception e) {
					throw e;
				}

			} catch (Exception e) {
				throw e;
			}
		}
	}
}

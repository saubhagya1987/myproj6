package vn.com.unit.fe_credit.controller;

import static vn.com.unit.fe_credit.config.SystemConfig.UPLOAD_APPLYNOW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import vn.com.unit.fe_credit.bean.ApplyNowBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.Book;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.ApplyNow;
import vn.com.unit.fe_credit.service.ApplyNowService;

@Controller
@RequestMapping("/exportApplyNow")
public class ExportApplyNow {

	@Autowired
	ApplyNowService applyNowService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private UserProfile userProfile;

	@RequestMapping(value = "/exporttoexcell", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public void exportToExelGET(@ModelAttribute(value = "bean") ApplyNowBean applyNowBean, HttpServletRequest request, HttpServletResponse response,
			Locale locale) {
		ApplyNowBean resultBean = applyNowService.searchEx(applyNowBean);
		if (resultBean.getListResult().size() != 0) {
			ApplyNow wo = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Calendar cal = Calendar.getInstance();

			String csvFileName = "ApllyNow_MobileApp" + "_" + sdf.format(cal.getTime()) + "_" + new Date().getHours() + ".csv";
			// creates mock data
			ICsvBeanWriter csvWriter;
			String pathToFileTmp = systemConfig.getConfig(UPLOAD_APPLYNOW) + csvFileName;
			try {
				File fileDir = new File(pathToFileTmp);

				Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));

				csvWriter = new CsvBeanWriter(out, CsvPreference.STANDARD_PREFERENCE);
				String[] header = { "LastName", "IdCardNumber", "PrimaryPhone", "City", "Address", "ProductCode", "Email", "SourceInfo", "Campaign" };
				csvWriter.writeHeader(header);

				for (int i = 0; i < resultBean.getListResult().size(); i++) {

					wo = resultBean.getListResult().get(i);

					wo.setExportedDate(new Date());
					wo.setUpdate_date(new Date());
					wo.setStatus(2L);

					applyNowService.saveApplyNow(wo);
					Book book4 = new Book(wo.getFullName(), wo.getIdCardNumber(), wo.getCellPhone(), wo.getCity(), wo.getAddress(), wo.getProduct(),
							wo.getEmail(), "Mobile Application", "Mobile Application");
					csvWriter.write(book4, header);

				}
				csvWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

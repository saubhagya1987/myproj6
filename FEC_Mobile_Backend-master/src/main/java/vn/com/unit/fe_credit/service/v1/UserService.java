package vn.com.unit.fe_credit.service.v1;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.AccountBean;

public interface UserService {

	public Model getUsersForPost(Model model, AccountBean accountBean, HttpServletRequest request, Locale locale);

	public Model getUsersForGet(Model model, AccountBean accountBean, HttpServletRequest request, Locale locale, String message);

	public Model addOrEditForGet(Model model, HttpServletRequest request, Long id,AccountBean accountBean);

	public Model addOrEditForPost(Model model, HttpServletRequest request, AccountBean accountBean, BindingResult bindingResult, List<Long> chkTeamLeft, Locale locale, RedirectAttributes redirectAttributes);

	public Model deleteUserById(Model model, Long id, Locale locale);

	public WebDataBinder getBinder(WebDataBinder binder, Locale locale, HttpServletRequest request);

	public Model uploadExcelData(Model model, Locale locale, MultipartFile excelfile);
	
	public Model unlockUser(Model model,String email,AccountBean accountBean, Locale locale);
	
	public Model clearDevice(Model model,String email,AccountBean accountBean, Locale locale,String appId);
}
package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.SearchMapBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.BranchEmtity;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.fe_credit.entity.StatusTable;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.BranchService;
import vn.com.unit.fe_credit.service.POSService;
import vn.com.unit.fe_credit.service.SearchMapService;
import vn.com.unit.fe_credit.service.StatusTableService;

@Controller
@RequestMapping("/master_data/searchmap")
public class SearchMapController {
	@Autowired
	private MessageSource msgSrc;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	AccountTeamService accountTeamService;
	@Autowired
	AccountService accountService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	SearchMapService searchMapService;
	@Autowired
	BranchService branchService;
	@Autowired
	POSService posService;
	@Autowired
	StatusTableService statusTableService;
	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale,
			HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request
				.getSession().getAttribute("formatDate"));
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale,
				"#,###.##"));
	}

	@RequestMapping(value = "/serach", method = RequestMethod.GET)
	public String serachMap(@ModelAttribute(value = "bean") SearchMapBean bean,
			Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		return "master_data.serachMap.search";
	}

	@RequestMapping(value = "/serach", method = RequestMethod.POST)
	public String listStudent(
			@ModelAttribute(value = "bean") @Valid SearchMapBean bean,
			BindingResult bindingResult, Model model, Locale locale,
			RedirectAttributes redirectAttributes, HttpServletRequest request)
			throws Exception {
		List<StatusTable>  status =statusTableService.findAllex();
		String banchName=bean.getBranchName();
		// luu
		//kiem tra co nhap du lieu k
		if(bean.getEntity().getDistrict().equalsIgnoreCase("") &&bean.getEntity().getCity().equalsIgnoreCase("City")){
			bean.addMessage(Message.ERROR,
					messageSource.getMessage("msg.save.fail", null, locale));
			
		}
		
		else{
			if(bean.getEntity().getDistrict().equalsIgnoreCase("") && bean.getBranchName().equalsIgnoreCase("Branch Name") ){
				bean.addMessage(Message.ERROR,
						messageSource.getMessage("SearchMap.savedata.fail.pos", null, locale));
				return "master_data.serachMap.search";
				
			}
//		searchMapService.save(bean.getEntity());
		//luu branch
		if(bean.getEntity().getDistrict().equalsIgnoreCase("") ){
			//kiem tra duoi data coi co chua.neu chua co toa do thi cho up.co toa do roi thi k cho up
			List<BranchEmtity> getDAO= new ArrayList<BranchEmtity>();
			getDAO=	branchService.findProvince(bean.getEntity().getCity());
			if(getDAO.size()>=1){
			branchService.updateLocation(bean.getEntity().getCity(), bean.getEntity().getLongitude(), bean.getEntity().getLatitude());
			bean.addMessage(Message.SUCCESS,
					messageSource.getMessage("msg.save.success", null, locale));
			}
			else{
				//chua có thì luu moi
				BranchEmtity saveData = new BranchEmtity();
				saveData.setLatitude(bean.getEntity().getLatitude());
				saveData.setLongitude(bean.getEntity().getLongitude());
				saveData.setProvince(bean.getEntity().getCity());
				saveData.setStatusTable(status.get(0));
				branchService.saveBranch(saveData);
				bean.addMessage(Message.SUCCESS,
						messageSource.getMessage("msg.save.success", null, locale));
			}
			}
		//luu pos
		else{
			
			// kiem tra coi co id cha chua 
			List<BranchEmtity> getDAO= new ArrayList<BranchEmtity>();
			getDAO=	branchService.findProvince(bean.getEntity().getCity());
			BranchEmtity saveData = new BranchEmtity();
			saveData.setProvince(bean.getEntity().getCity());
			saveData.setStatusTable(status.get(0));
			if(getDAO.size()<=0){
				//chua có thì luu moi
				branchService.saveBranch(saveData);
				bean.addMessage(Message.INFO,messageSource.getMessage("SearchMap.savedata.fail", null, locale));
			}
			List<BranchEmtity> getDAOGetID= new ArrayList<BranchEmtity>();
			getDAOGetID=branchService.findProvince(saveData.getProvince());
			Long branchId=getDAOGetID.get(0).getBranchid();   
			System.out.println("sssssssssssssssss"+branchId);
			List<PosEmtity> getDAOPOS= new ArrayList<PosEmtity>();
			getDAOPOS=	posService.findDistrict(bean.getEntity().getDistrict());
				//chua có thì luu moi
			
			PosEmtity saveDataPOS = new PosEmtity();
			//kiem tra thanh pho + quan
			
			
		    /*//Pos flag = tìm thanh pho + quan service
			List<PosEmtity> checkDAO=posService.findProvinceDistrict(branchId, bean.getEntity().getDistrict());
		    //neu co id distric cu = id distric
		    Long getidDisTrict=1L;
		    if(checkDAO.size()==0){
		    	
		    	List<PosEmtity> getMax=	posService.maxId();
		    	Long saveId = null;
		    	if(getMax.size()!=0){
		    		saveId=getMax.get(0).getIddistrict();
		    		saveDataPOS.setIddistrict(saveId+1);
		    	}
		    	else{
//		    		if(getMax.get(0).getIddistrict()==null){
			    		 saveId=1L;
			    		saveDataPOS.setIddistrict(saveId);
//			    	}
		    	}
		    	
		    }else{
		    if(checkDAO.get(0).getIddistrict()!=null){
		    	 getidDisTrict=checkDAO.get(0).getIddistrict();
		    }
		   
		    Long idSomeTime=1L;
		    for (PosEmtity posEmtity : checkDAO) {
		    	if(posEmtity.getIddistrict()!=null){
		    		idSomeTime=posEmtity.getIddistrict();
		    	}
		    	
		    	if(idSomeTime>getidDisTrict){
		    		getidDisTrict=idSomeTime;
		    	}
			}
		    if(getidDisTrict!=1){
		    	saveDataPOS.setIddistrict(getidDisTrict);
		    }
		    else{
		    	List<PosEmtity> getMax=	posService.maxId();
		    	Long saveId = null;
		    	if(getMax.size()!=0){
		    		saveId=getMax.get(0).getIddistrict();
		    		saveDataPOS.setIddistrict(saveId);
		    	}
		    	else{
//		    		if(getMax.get(0).getIddistrict()==null){
			    		 saveId=1L;
			    		saveDataPOS.setIddistrict(saveId+1);
//			    	}
		    	}
		    	
		    
		    }
		    }*/
		    //neu chua co 
		    // tim 1 top id distric + 1 service
		    // sau co id distric = top 1
	
		    
		    
		    // lam lai
		    
		    // kiem tra duoi data co chua.chua co thi gan =1
		    List<PosEmtity> checkDAOCheck=posService.findAllex();
		    if(checkDAOCheck.size()==0){
		    	saveDataPOS.setIddistrict(1L);
		    }
		    else{
		    //Pos flag = tìm thanh pho + quan service
			List<PosEmtity> checkDAO1=posService.findProvinceDistrict(branchId, bean.getEntity().getDistrict());
			List<PosEmtity> getMax=	posService.maxId();
			// neu co thi luu duoi cai  iddistrin cua no
			if(checkDAO1.size()!=0 && getMax.size()!=0){
				saveDataPOS.setIddistrict(checkDAO1.get(0).getIddistrict());
				
			}
			else{
				// chua co thang do duoi data
				// tim so max dang co hien tai
				Long maxNow = 1L;
				if(getMax.size()!=0){
				if(getMax.get(0).getIddistrict()>maxNow){
					maxNow=getMax.get(0).getIddistrict();
				}
				}
				saveDataPOS.setIddistrict(maxNow+1);
			}
		    }
				
				saveDataPOS.setLatitude(bean.getEntity().getLatitude());
				saveDataPOS.setLongitude(bean.getEntity().getLongitude());
				saveDataPOS.setProvince(bean.getEntity().getCity());
				saveDataPOS.setDistrict(bean.getEntity().getDistrict());
				saveDataPOS.setBranchEmtity(getDAOGetID.get(0));
				saveDataPOS.setStatusTable(status.get(0));
				saveDataPOS.setBranch_namepos(bean.getBranchName());
				saveDataPOS.setBuyOrPay(bean.getBuyOrPay());
				posService.savePOS(saveDataPOS);
				bean.addMessage(Message.SUCCESS,
						messageSource.getMessage("msg.save.success", null, locale));
			
			
			//kiem tra co chua va luu
			
		}
		}
		/*bean.addMessage(Message.SUCCESS,
				messageSource.getMessage("msg.save.success", null, locale));*/
		redirectAttributes.addFlashAttribute("bean", bean);
		return "redirect:/master_data/searchmap/serach";
	}
}

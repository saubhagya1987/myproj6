package vn.com.unit.fe_credit.bean;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.StatusTable;

public class CMSBean extends AbstractBean<CMSEmtity> {

	private List<CMSEmtity> listCMS;

	private String searchField;

	private List<StatusTable> listStatusTable;

	private List<CMSCategory> listCMSCategory;

	private List<CMSCategory> listCMSCategoryByStock;

	private List<Hobby> listCMSHobbyByStock;

	private List<String> listCMSCategoryView;

	private Integer category;

	private Integer status;

	private String tag;

	private List<Long> cmsCategoryIds;

	private List<Long> cmsHobbyIds;

	private String CMSView;

	private String CMSHobbyView;

	private Date createDayfrom;

	private Date createDayto;

	// Push notification
	private String pushTitle;

	private String pushDescription;

	private Integer pushOption;

	private Date pushDate;

	private Integer pushRecipientType;

	@Getter
	@Setter
	private Boolean isPushWhenSave = false;

	public List<CMSEmtity> getListCMS() {
		return listCMS;
	}

	public void setListCMS(List<CMSEmtity> listCMS) {
		this.listCMS = listCMS;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public List<StatusTable> getListStatusTable() {
		return listStatusTable;
	}

	public void setListStatusTable(List<StatusTable> listStatusTable) {
		this.listStatusTable = listStatusTable;
	}

	public List<CMSCategory> getListCMSCategory() {
		return listCMSCategory;
	}

	public void setListCMSCategory(List<CMSCategory> listCMSCategory) {
		this.listCMSCategory = listCMSCategory;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Long> getCmsCategoryIds() {
		return cmsCategoryIds;
	}

	public void setCmsCategoryIds(List<Long> cmsCategoryIds) {
		this.cmsCategoryIds = cmsCategoryIds;
	}

	public List<CMSCategory> getListCMSCategoryByStock() {
		return listCMSCategoryByStock;
	}

	public void setListCMSCategoryByStock(List<CMSCategory> listCMSCategoryByStock) {
		this.listCMSCategoryByStock = listCMSCategoryByStock;
	}

	public List<String> getListCMSCategoryView() {
		return listCMSCategoryView;
	}

	public void setListCMSCategoryView(List<String> listCMSCategoryView) {
		this.listCMSCategoryView = listCMSCategoryView;
	}

	public String getCMSView() {
		return CMSView;
	}

	public void setCMSView(String cMSView) {
		CMSView = cMSView;
	}

	public List<Hobby> getListCMSHobbyByStock() {
		return listCMSHobbyByStock;
	}

	public void setListCMSHobbyByStock(List<Hobby> listCMSHobbyByStock) {
		this.listCMSHobbyByStock = listCMSHobbyByStock;
	}

	public List<Long> getCmsHobbyIds() {
		return cmsHobbyIds;
	}

	public void setCmsHobbyIds(List<Long> cmsHobbyIds) {
		this.cmsHobbyIds = cmsHobbyIds;
	}

	public String getCMSHobbyView() {
		return CMSHobbyView;
	}

	public void setCMSHobbyView(String cMSHobbyView) {
		CMSHobbyView = cMSHobbyView;
	}

	public Date getCreateDayfrom() {
		return createDayfrom;
	}

	public void setCreateDayfrom(Date createDayfrom) {
		this.createDayfrom = createDayfrom;
	}

	public Date getCreateDayto() {
		return createDayto;
	}

	public void setCreateDayto(Date createDayto) {
		this.createDayto = createDayto;
	}

	public String getPushTitle() {
		return pushTitle;
	}

	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}

	public String getPushDescription() {
		return pushDescription;
	}

	public void setPushDescription(String pushDescription) {
		this.pushDescription = pushDescription;
	}

	public Integer getPushOption() {
		return pushOption;
	}

	public void setPushOption(Integer pushOption) {
		this.pushOption = pushOption;
	}

	public Date getPushDate() {
		return pushDate;
	}

	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}

	public Integer getPushRecipientType() {
		return pushRecipientType;
	}

	public void setPushRecipientType(Integer pushRecipientType) {
		this.pushRecipientType = pushRecipientType;
	}

}

package vn.com.unit.fe_credit.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;

@Data
@EqualsAndHashCode(callSuper = false)
public class MgmExportVtigerBean extends AbstractBean<MgmSuggestedContacts> {
	private List<MgmSuggestedContacts> listRedeemTras;
	private String searchField;
	private String succesorfail;
	private Boolean flagExport;

}

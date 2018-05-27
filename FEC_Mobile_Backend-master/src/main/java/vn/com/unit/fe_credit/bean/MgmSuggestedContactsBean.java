package vn.com.unit.fe_credit.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;

@Data
@EqualsAndHashCode(callSuper=true)
public class MgmSuggestedContactsBean extends AbstractBean<MgmSuggestedContacts> {
	List<MgmSuggestedContacts> mgmSuggestedContactsLst;
	private String searchField;
	private String succesorfail;
	
}

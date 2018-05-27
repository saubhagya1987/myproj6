package vn.com.unit.fe_credit.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;

@Data
@EqualsAndHashCode(callSuper = false)
public class RedeemTrasPointBean extends AbstractBean<MgmRedeemTrans> {
	private List<MgmRedeemTrans> listRedeemTras;
	private String searchField;
	private String succesorfail;
	private Boolean flagExport;

}

package vn.com.unit.fe_credit.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExportRedeemTrasPointBean extends AbstractBean<MgmExportFileRedeemPoint> {
	private List<MgmExportFileRedeemPoint> listRedeemTrasPoint;
	private String searchField;
	private String succesorfail;

}

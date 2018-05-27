package vn.com.unit.fe_credit.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.unit.fe_credit.entity.MgmExportFileDataToVTiger;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExportDataToVTigerBean extends AbstractBean<MgmExportFileDataToVTiger> {
	private List<MgmExportFileDataToVTiger> listVTiger;
	private String searchField;
	private String succesorfail;

}

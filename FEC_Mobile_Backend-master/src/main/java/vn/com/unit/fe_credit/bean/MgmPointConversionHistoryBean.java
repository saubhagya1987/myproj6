package vn.com.unit.fe_credit.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;

@Data
@EqualsAndHashCode(callSuper = true)
public class MgmPointConversionHistoryBean extends AbstractBean<MgmRedeemTrans> {
	List<MgmRedeemTrans> mgmPointConverHistory;

}

package vn.com.unit.fe_credit.storeprocedure;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.object.StoredProcedure;

import oracle.jdbc.OracleTypes;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;

public class MgmExportVtigerSP extends StoredProcedure {

	private static final String SPROC_NAME = "GETVTIGER";
	
	public MgmExportVtigerSP(DataSource datasource) {
		super(datasource, SPROC_NAME );
		declareParameter(new SqlOutParameter("sys_refcursor", OracleTypes.CURSOR, 
                ParameterizedBeanPropertyRowMapper.newInstance(MgmSuggestedContacts.class)));
		compile();
	}
	
	public List<MgmSuggestedContacts> execute() {
		Map<String, Object> out = super.execute();
		List<MgmSuggestedContacts> results = (List) out.get("sys_refcursor");
        return results;
	}
	
}

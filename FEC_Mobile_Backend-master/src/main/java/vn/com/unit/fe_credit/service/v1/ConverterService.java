package vn.com.unit.fe_credit.service.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.v1.RecordDto;
import vn.com.unit.fe_credit.entity.collection.Record;

public interface ConverterService {

	public RecordDto entityToDtoRecord(Object[] record);

	public List<RecordDto> entitiesToDtosRecord(List<Object[]> records);

}

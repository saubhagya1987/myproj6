package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.bean.v1.RecordDto;
import vn.com.unit.fe_credit.entity.collection.Record;
public interface RecordsDao {
	
	public List<Record> findAllRecord();
	
	public List<Object[]> findAll();

	public boolean save(Record record);

	List<Record> findAllByStatus();

	public List<Record> findAllByStatusAndFC();

	public List<Record> findAllByStatusAndSF();
	
	public void updateStatus(String tablename, String ids);

	public RecordDto getAttachmentId(int id);

}

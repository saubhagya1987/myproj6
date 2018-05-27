package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.collection.CheckIn;

public interface CheckInDao {
	public List<CheckIn> findAll();
	public boolean save(CheckIn record);
	public List<Object[]> findByAppIDAndCreationDate();
	public Object findFcCheckInDaily(String date);
	public List<Object[]> findAppCheckInDaily(String date);
	public Object findFcCheckInMonthly();
	public List<Object[]> findAppCheckInMonthly();
	public void updateStatus(String tablename, String ids);
}

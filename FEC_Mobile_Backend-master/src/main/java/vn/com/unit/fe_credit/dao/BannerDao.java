package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.bean.BannerBean;
import vn.com.unit.fe_credit.entity.Banner;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface BannerDao extends GenericDAO<Banner, Long>{

	BannerBean search(BannerBean bean);

	List<Banner> findByCategory(String category);

	List<Banner> findByCategory_WS_getTitlebyCategory(String category);

	Banner getContentByID(Long bannerId);
	List<Banner> searchInStatus();

}

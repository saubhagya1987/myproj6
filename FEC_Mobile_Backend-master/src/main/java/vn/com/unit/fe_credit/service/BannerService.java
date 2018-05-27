package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.BannerBean;
import vn.com.unit.fe_credit.entity.Banner;

public interface BannerService {

	Banner findById(Long id);

	void deleteBanner(Long id);

	void saveBanner(Banner banner);

	BannerBean search(BannerBean bean);
	List<Banner> searchInStatus();

	void save(BannerBean bannerBean);

	List<Banner> findByCategory(String category);

	List<Banner> findByCategory_WS_getTitlebyCategory(String category);

	Banner getContentByID(Long bannerId);
	
	
}

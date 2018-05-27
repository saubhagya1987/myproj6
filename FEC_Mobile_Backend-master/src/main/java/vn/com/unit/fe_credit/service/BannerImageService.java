package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.entity.BannerImages;

public interface BannerImageService {

	BannerImages findById(Long id);

	void deleteBannerImage(Long id);

	void saveBannerImage(BannerImages bannerImage);

	void deleteBannerImagebyLst(List<BannerImages> lstBannerImage);

	List<BannerImages> findByBannerId(Long bannerId);

}

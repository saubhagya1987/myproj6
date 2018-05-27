package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.BannerImages;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface BannerImageDao extends GenericDAO<BannerImages, Long>{

	void deleteBannerImagebyLst(List<BannerImages> lstBannerImage);

	List<BannerImages> findByBannerId(Long bannerId);

}

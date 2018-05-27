package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.dao.BannerImageDao;
import vn.com.unit.fe_credit.entity.BannerImages;
import vn.com.unit.fe_credit.service.BannerImageService;

@Service("BannerImageService")
@Transactional(readOnly = true)
public class BannerImageServiceImpl implements BannerImageService{
	@Autowired
	BannerImageDao bannerImageDao;
	
	public BannerImageServiceImpl() {
		super();
	}

	@Override
	public BannerImages findById(Long id) {
		return bannerImageDao.find(id);
	}

	@Transactional
	@Override
	public void deleteBannerImage(Long id) {
		BannerImages bannerImage=findById(id);
		if(bannerImage!=null) 
			bannerImageDao.remove(bannerImage);
	}
	
	@Transactional
	@Override
	public void saveBannerImage(BannerImages bannerImage) {
		bannerImageDao.save(bannerImage);
		
	}
	
	@Override
	public List<BannerImages> findByBannerId(Long bannerId) {
//		Search search = new Search(BannerImages.class);
//		search.addFilter(Filter.equal("bannerId", bannerId));
//		List<BannerImages> result = bannerImageDao.search(search);
//
//		return result;
		
		return bannerImageDao.findByBannerId(bannerId);
	}
	
	@Override
	public void deleteBannerImagebyLst(List<BannerImages> lstBannerImage) {
		bannerImageDao.deleteBannerImagebyLst(lstBannerImage);
	}
}

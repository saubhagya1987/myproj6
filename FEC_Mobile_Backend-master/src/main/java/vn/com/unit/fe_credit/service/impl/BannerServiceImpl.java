package vn.com.unit.fe_credit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.BannerBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.BannerDao;
import vn.com.unit.fe_credit.entity.Banner;
import vn.com.unit.fe_credit.entity.BannerImages;
import vn.com.unit.fe_credit.service.BannerImageService;
import vn.com.unit.fe_credit.service.BannerService;
import vn.com.unit.fe_credit.utils.Utils;

@Service("bannerService")
@Transactional(readOnly = true)
public class BannerServiceImpl implements BannerService{
	@Autowired
	BannerDao bannerDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	BannerImageService bannerImageService;
	@Autowired
	private UserProfile userProfile;
	public BannerServiceImpl() {
		super();
	}

	@Override
	public Banner findById(Long id) {
		return bannerDao.find(id);
	}

	@Transactional
	@Override
	public void deleteBanner(Long id) {
		Banner banner=findById(id);
		if(banner!=null) 
			bannerDao.remove(banner);
	}
	
	@Transactional
	@Override
	public void saveBanner(Banner banner) {
		if(banner.getBannerId() == null){
			banner.setCreated_date(new Date());
			if(userProfile.getAccount() != null)
			banner.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			banner.setUpdate_date(new Date());
			if(userProfile.getAccount() != null)
			banner.setUpdate_by(userProfile.getAccount().getUsername());
		}
		bannerDao.save(banner);
		
	}
	
	@Transactional
	@Override
	public void save(BannerBean bannerBean) {
		
		if(bannerBean.getEntity().getBannerId() == null){
			bannerBean.getEntity().setCreated_date(new Date());
			bannerBean.getEntity().setCreated_by(userProfile.getAccount().getUsername());
		}else{
			bannerBean.getEntity().setUpdate_date(new Date());
			bannerBean.getEntity().setUpdate_by(userProfile.getAccount().getUsername());
		}
		bannerDao.save(bannerBean.getEntity());
		if(StringUtils.isNotEmpty(bannerBean.getLstImage())){
			List<BannerImages> lstBannerImage = bannerImageService.findByBannerId(bannerBean.getEntity().getBannerId());
			if(lstBannerImage != null && lstBannerImage.size() > 0){
				bannerImageService.deleteBannerImagebyLst(lstBannerImage);
			}
			String[] images = bannerBean.getLstImage().split(",");
			String[] imagesLink = bannerBean.getLstImageLink().split("@@@");
			for(int i = 0; i < images.length; i++){
				String fileTempName = images[i];
				String newName = Utils.moveTempToUploadFolder(fileTempName, systemConfig);
				
				BannerImages bannerImage = new BannerImages();
				bannerImage.setBannerId(bannerBean.getEntity().getBannerId());
				bannerImage.setImagePath(imagesLink[i].trim());
				bannerImage.setImageFileName(newName);
				bannerImage.setStatus(systemConfig.STATUS_ACTIVE);
				bannerImageService.saveBannerImage(bannerImage);
			}
		}else{
			List<BannerImages> lstBannerImage = bannerImageService.findByBannerId(bannerBean.getEntity().getBannerId());
			if(lstBannerImage != null && lstBannerImage.size() > 0){
				bannerImageService.deleteBannerImagebyLst(lstBannerImage);
			}
		}
	}
	
	@Override
	public List<Banner> findByCategory(String category) {

		List<Banner> bannerLst = bannerDao.findByCategory(category.toLowerCase());

		for (int i = 0; i < bannerLst.size(); i++) {
			bannerLst.get(i).setImageLink(
					systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + bannerLst.get(i).getImageFileName());
			bannerLst.get(i).setImageFileName(null);
		}

		return bannerLst;

	}
	
	@Override
	public List<Banner> findByCategory_WS_getTitlebyCategory(String category) {
		return bannerDao.findByCategory_WS_getTitlebyCategory(category.toLowerCase());
	}
	
	@Override
	public Banner getContentByID(Long bannerId) {
		Banner banner = bannerDao.getContentByID(bannerId);
		List<String> categorysArr = new ArrayList<String>();
		for(Entry<String, String> entry : systemConfig.getCategoryBannerMap().entrySet()){
			categorysArr.add(entry.getKey());
		}
		banner.setCatList(categorysArr);
		return banner;
	}
	
	@Override
	public BannerBean search(BannerBean bean) {
	/*	Search search = new Search(Banner.class);
		if (StringUtils.isNotEmpty(bean.getEntity().getCategory())) {
			search.addFilterLike("category", "%" + bean.getEntity().getCategory() + "%");
		}
		
		if (StringUtils.isNotEmpty(bean.getEntity().getBannerCode())) {
			search.addFilterLike("bannerCode", "%" + bean.getEntity().getBannerCode() + "%");
		}
		
		if (bean.getEntity().getStatus() != null) {
			search.addFilterAnd(Filter.equal("status", bean.getEntity().getStatus()));
		}
		
		search.addSort("bannerId", true);
			
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		
		SearchResult<Banner> searchResult = bannerDao.searchAndCount(search);

		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());*/
		return bannerDao.search(bean);
	}

	@Override
	public List<Banner> searchInStatus() {
		
		return bannerDao.searchInStatus();
	}

}

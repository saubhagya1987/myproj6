package vn.com.unit.fe_credit.service;

import vn.com.unit.fe_credit.entity.Wish;

public interface WishService {

	Wish findById(Long id);

	void deleteWish(Long id);

	void saveWish(Wish wish);

}

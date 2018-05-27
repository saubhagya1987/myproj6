package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name="CustomerWish")
@JsonAutoDetect
@SequenceGenerator(allocationSize=1,name="seq_customerwish",sequenceName="SEQ_CUSTOMERWISHID")
public class CustomerWish {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_customerwish")
	@Column(name = "CustomerWishId")
	private Long customerWishId;
	
	@Column(name = "AddDate")
	private Date addDate;
	
	@Column(name = "CustomerId")
	private Long customerId;
	
	@Column(name = "WishId")
	private Long wishId;
	
	@Transient
	private String wishes;
	
	@Transient
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWishes() {
		return wishes;
	}

	public void setWishes(String wishes) {
		this.wishes = wishes;
	}

	public Long getCustomerWishId() {
		return customerWishId;
	}

	public void setCustomerWishId(Long customerWishId) {
		this.customerWishId = customerWishId;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getWishId() {
		return wishId;
	}

	public void setWishId(Long wishId) {
		this.wishId = wishId;
	}
	
}

package vn.com.unit.fe_credit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name="Wish")
@JsonAutoDetect
@SequenceGenerator(allocationSize=1,name="seq_wish",sequenceName="SEQ_WISH_WISHID")
public class Wish {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_wish")
	@Column(name = "WishId")
	private Long wishId;
	
	@Column(name = "Wishes", length = 255)
	private String wishes;
	
	@Column(name = "Category", length = 255)
	private String category;

	public Long getWishId() {
		return wishId;
	}

	public void setWishId(Long wishId) {
		this.wishId = wishId;
	}

	public String getWishes() {
		return wishes;
	}

	public void setWishes(String wishes) {
		this.wishes = wishes;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}

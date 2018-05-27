package vn.com.unit.fe_credit.bean;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeBean<E> {
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	Long id;
	String code;
	String text;
	List<E> children;
	Object bean;
	Long parent;
	Integer count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<E> getChildren() {
		return children;
	}

	public void setChildren(List<E> children) {
		this.children = children;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public void addChild(E treeBean) {
		if (children == null)
			children = new ArrayList<E>();
		children.add(treeBean);
	}

	public void addChildEquipmentCategoryTreeBean(E treeBean) {
		if (children == null)
			children = new ArrayList<E>();
		children.add(treeBean);

	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}

package vn.com.unit.fe_credit.service.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.DepartmentBean;
import vn.com.unit.fe_credit.bean.DepartmentTreeBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.DepartmentDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Department;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.DepartmentService;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * DepartmentServiceImpl
 * 
 * @author user
 * @version 1.0 7-10-2014
 */
@Service("departmentService")
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService{
	@Autowired
	DepartmentDao departmentDao;	
	@Autowired
	AccountService accountService;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private UserProfile userProfile;
	@Override	
	public DepartmentBean search(DepartmentBean bean) {

		SearchResult<Long> rootsAndTotal=departmentDao.searchRootAndTotal(bean);
		List<Long>  rootResult=rootsAndTotal.getResult();
		bean.setTotal(rootsAndTotal.getTotalCount());
		Search search=new Search(Department.class);
		if(StringUtils.isNotBlank(bean.getSearchContent())){
			search.addFilterOr(Filter.ilike("name", "%"+bean.getSearchContent()+"%"),
					Filter.ilike("parent.name", "%"+bean.getSearchContent()+"%"),
					Filter.ilike("phone", "%"+bean.getSearchContent()+"%"),
					Filter.ilike("note", "%"+bean.getSearchContent()+"%"));
			//search.addFilterIn("rootId", rootResult);

		}
		search.addFilterIn("rootId", rootResult);
		List<Department> result= departmentDao.search(search);

		String parentnode="";

		for(int i=0;i< result.size();i++){
			parentnode += result.get(i).getParentLink();
		}			
		List<Department> leaf = new ArrayList<Department>();
		for(int i=0;i< result.size();i++){
			if(parentnode.indexOf(";"+result.get(i).getDepartmentId()+";")==-1){
				leaf.add(result.get(i));
			}
		}
		result.clear();
		List<DepartmentTreeBean> listroot = new ArrayList<DepartmentTreeBean>();
		Map<String, String> mapAccountName=new LinkedMap();
		while(leaf.size()!=0){
			DepartmentTreeBean node=new DepartmentTreeBean();
			Department department = (Department)leaf.get(0);
			leaf.remove(0);
			node.setId(department.getDepartmentId());
			node.setText(department.getName());				
			node.setParentName(department.getParent()==null?"":department.getParent().getName());
			node.setCostCenterNo(department.getDepartmentCode());			
			
	/*		node.setManager(department.getManager());
			node.setLocation(department.getLocation());*/
			node.setNote(department.getNote());
			node.setBean(department);
			DepartmentTreeBean root;

			if(department.getParent()!=null){
				node.setParentObj(department.getParent());
				if(!findToAdd(department,listroot))
				{	
					root = buildTree(node,null);
					listroot.add(root);
				}
			}else{
				root = node;
				listroot.add(root);
			}
		}

		bean.setTree(listroot);

		return bean;
	}
	@Override
	public
	List<Long> getDivision(){
		Search search=new Search();
		search.addFilterCustom("parent.departmentId=rootId");
		search.addField("departmentId");
		return departmentDao.search(search);
	}
	
	private DepartmentTreeBean buildTree(DepartmentTreeBean childnode,
			DepartmentTreeBean parentNode) {
		Department department = childnode.getParentObj();
		Map<String, String> mapAccountName=new LinkedMap();
		childnode.setParent(department.getDepartmentId());		
		if(parentNode!=null){
			if(parentNode.getId().equals(department.getDepartmentId())){
				parentNode.addChild(childnode);
				return parentNode;
			}
		}
		DepartmentTreeBean parent = new DepartmentTreeBean();
		parent.setId(department.getDepartmentId());
		parent.setText(department.getName());
		parent.setCostCenterNo(department.getDepartmentCode());
		parent.setBean(department);
		parent.addChild(childnode);
		parent.setCostCenterNo(department.getDepartmentCode());
/*		parent.setManager(department.getManager());
		parent.setLocation(department.getLocation());*/
		parent.setNote(department.getNote());
		if(department.getParent()!=null){
			parent.setParentObj(department.getParent());
			parent=buildTree(parent,parentNode);
		}		
		return parent;
	}

	private boolean findToAdd(Department department,
			List<DepartmentTreeBean> listroot) {
		if(listroot==null||listroot.size()==0)
			return false;
		Map<String, String> mapAccountName=new LinkedMap();
		for(int i=0;i<listroot.size();i++){
			DepartmentTreeBean root = (DepartmentTreeBean)listroot.get(i);
			if(department.getParentLink().indexOf(";"+root.getId()+";")!=-1){
				DepartmentTreeBean node=new DepartmentTreeBean();
				node.setId(department.getDepartmentId());
				node.setText(department.getName());

				node.setParentName(department.getParent()==null?"":department.getParent().getName());
				node.setCostCenterNo(department.getDepartmentCode());
	/*			node.setManager(department.getManager());
				node.setLocation(department.getLocation());*/
				node.setNote(department.getNote());
				//node.setCostCenterNo(department.getCostCenterNo());
				node.setBean(department);
				node.setParentObj(department.getParent());
				node.setParent(root.getId());

				boolean flag=false;
				if(root.getId()==department.getParent().getDepartmentId()){
					root.addChild(node);
					return true;
				}else{
					flag=findToAdd(department,root.getChildren());
				}
				if(!flag)
				{
					buildTree(node,root);
				}
				return true;

			}

		}
		return false;
	}

	@Override
	public Department findById(Long id) {
		return departmentDao.find(id);
	}
	@Override
	@Transactional
	public void saveOrUpdateDeapartment(Department dept) {
		departmentDao.save(dept);

	}

	@Transactional
	@Override
	public void deleteDepartment(Long id) {
		Department dept=findById(id);
		if(dept!=null){ 
			Set<Department> childs = dept.getDepartments();
			for (Iterator<Department> it = childs.iterator(); it.hasNext(); ) {
				Department e = it.next();
				deleteDepartment(e.getDepartmentId());				
			}
			departmentDao.remove(dept);		
		}

	}
	@Override
	public List<DepartmentTreeBean> createTree(Long Id) {
		List<DepartmentTreeBean> treeBean= new ArrayList<DepartmentTreeBean>();
		List<Department> roots=departmentDao.searchByParent(null);
		treeBean.add(new DepartmentTreeBean());
		for(Department dept:roots){
			DepartmentTreeBean node = createTreeNode(dept,Id);
			if(node!=null)
				treeBean.add(node);
		}
		return treeBean;
	}

	private DepartmentTreeBean createTreeNode(Department dept,Long Id) {
		if(Id!=null && Id.equals(dept.getDepartmentId())){
			return null;
		}
		DepartmentTreeBean root=new DepartmentTreeBean();
		root.setId(dept.getDepartmentId());
		root.setText(dept.getName());
		root.setBean(dept);
		root.setParentName(dept.getParent()==null?null:dept.getParent().getName());
		root.setParent(dept.getParent()==null?null:dept.getParent().getDepartmentId());
		List<Department> childs= departmentDao.searchByParent(dept);
		for(Department child:childs){
			DepartmentTreeBean node = createTreeNode(child,Id);
			if(node!=null)
				root.addChild(node);
		}
		return root;
	}
	@Override
	public void updateDeapartmentChild(Department rootNode) {
		String separate=systemConfig.getConfig(SystemConfig.PARENT_LINK_SYMBOL);
		String parentLink="";
		if(rootNode.getParent()==null){
			rootNode.setParentLink("");
			rootNode.setRootId(rootNode.getDepartmentId());
			departmentDao.save(rootNode);
			updateParentForChild(rootNode);
		}
		else {
			parentLink=rootNode.getParent().getParentLink();
			if(parentLink==null) parentLink="";
			if(!parentLink.endsWith(separate)) parentLink+=separate;
			parentLink+=rootNode.getParent().getDepartmentId();
			parentLink+=separate;
			rootNode.setParentLink(parentLink);
			if(StringUtils.isEmpty(parentLink)) rootNode.setRootId(rootNode.getDepartmentId());
			else {
				String id=new StringTokenizer(parentLink, ";").nextToken();
				rootNode.setRootId(Long.parseLong(id));
			}
			departmentDao.save(rootNode);
			updateParentForChild(rootNode);
		}

		/*List<Department> childs= departmentDao.searchByParent(dept);
		for(Department child:childs){
			Long parentId=dept.getDepartmentId();
			String parentSymbol = systemConfig.getConfig(SystemConfig.PARENT_LINK_SYMBOL);
			if (parentId != null) {	
				String parentLink = dept.getParentLink();
				if (parentLink == null)
					parentLink = "";
				if (!parentLink.endsWith(parentSymbol))
					parentLink += parentSymbol;
				parentLink += dept.getDepartmentId() + parentSymbol;
				child.setParentLink(parentLink);				
				departmentDao.save(child);

				updateDeapartmentChild(child);
			}


		}
		 */


	}
	private void updateParentForChild(Department rootNode) {
		String separate=systemConfig.getConfig(SystemConfig.PARENT_LINK_SYMBOL);
		Search search=new Search(Department.class);
		String pattern=separate+rootNode.getDepartmentId()+separate;
		search.addFilterLike("parentLink", "%"+pattern+"%");
		List<Department> department = departmentDao.search(search);
		for(Department departments:department){
			String childParentLink=departments.getParentLink();
			int indexOf= childParentLink.indexOf(pattern);
			String newLink=childParentLink.substring(indexOf,childParentLink.length());
			if(StringUtils.isNotEmpty(rootNode.getParentLink()))
				newLink=rootNode.getParentLink().substring(0,rootNode.getParentLink().length()-1)+newLink;
			departments.setParentLink(newLink);
			if(StringUtils.isEmpty(newLink)) departments.setRootId(departments.getDepartmentId());
			else {
				String id=new StringTokenizer(newLink, separate).nextToken();
				departments.setRootId(Long.parseLong(id));
			}
			departmentDao.save(departments);
		}
	}

	@Override
	public Map<Long, Department> searchByListAccount(List<Account> result) {


		return departmentDao.searchByListAccount(result);
	}


	@Override
	public List<Department> findDepartments(String code, Long branchId) {
		Search search=new Search();
		if (code!=null){
			search.addFilterOr(
					Filter.ilike("costCenterNo", "%"+code+"%"),
					Filter.ilike("name", "%"+code+"%"));
			
		}
		if(branchId != null){
			search.addFilterEqual("branch.branchId", branchId);
		}
		return departmentDao.search(search);
	}
	
	

	@Override
	public List<Department> findDepartmentsByType(String code, Long branchId, Integer type) {
		Search search=new Search();
		if (code!=null){
			search.addFilterOr(
					Filter.ilike("costCenterNo", "%"+code+"%"),
					Filter.ilike("name", "%"+code+"%"));
			
		}
		if(branchId != null){
			search.addFilterEqual("branch.branchId", branchId);
		}
		if(type != null){
			search.addFilterEqual("locationType", type);
		}
		return departmentDao.search(search);
	}
	
	@Override
	public List<DepartmentTreeBean> createTreeForCombo(Long currentNode) {
		List<DepartmentTreeBean> treeBean= new ArrayList<DepartmentTreeBean>();
		List<Department> roots=departmentDao.searchByParent(null);
		for(Department department:roots){
			DepartmentTreeBean node=createTreeNodeForCombo(department,currentNode);
			if(currentNode==null||!department.getDepartmentId().equals(currentNode)) {
				if(node!=null) treeBean.add(node);
			}
		}
		return treeBean;
	}
	
	private DepartmentTreeBean createTreeNodeForCombo(Department department,Long currentNode) {
		if(department.getDepartmentId().equals(currentNode)) return null;
		DepartmentTreeBean root=new DepartmentTreeBean();
		root.setId(department.getDepartmentId());
		root.setText(department.getDepartmentCode());
		//root.setIdAction(accountChart.getAccountId());
		root.setBean(department);
		root.setParent(department.getParent()==null?null:department.getParent().getDepartmentId());
		List<Department> childs= departmentDao.searchByParent(department);
		for(Department child:childs){
			DepartmentTreeBean node=createTreeNodeForCombo(child,currentNode);
			if(node!=null) root.addChild(node);
		}
		return root;
	}
	
	@Override
	@Transactional
	public void deleteByNode(Long id) {
		Department department=findById(id);
		if(department!=null){
			Set<Department> childs = department.getDepartments();
			for (Iterator<Department> it = childs.iterator(); it.hasNext(); ) {
				Department e = it.next();
				deleteByNode(e.getDepartmentId());				
			}
			departmentDao.remove(department);
		}
	}

	@Override
	public List<Department> findDepartmentsByCodeOrName(String query,
			boolean isTransaction) {
		Search search=new Search(Department.class);
		search.addFilterOr(Filter.ilike("costCenterNo", "%"+query+"%"),Filter.ilike("name", "%"+query+"%"));
		if(isTransaction){

			search.addFilterOr(Filter.equal("departmentId", userProfile.getDepartment().getDepartmentId()),
					Filter.equal("parent",userProfile.getDepartment()));
		}
		return departmentDao.search(search);
	}
	@Override
	public List<Department> findOneDepartmentsByUser(Long departmentId) {
		Search search=new Search(Department.class);
		search.addFilterEqual("departmentId", departmentId);
		return departmentDao.search(search);
	}
	@Override
	public Department findByCode(String code) {
		Search search=new Search(Department.class);
		search.addFilterEqual("costCenterNo", code);
		if(departmentDao.search(search).size() > 0){
			return (Department)departmentDao.search(search).get(0);
		}

		return null;
	}
	@Override
	public Department findByDepartmentCode(String code) {
		Search search=new Search(Department.class);
		search.addFilterEqual("departmentCode", code);
		if(departmentDao.search(search).size() > 0){
			return (Department)departmentDao.search(search).get(0);
		}

		return null;
	}
	@Override
	public List<Long> getAuthorizedDepartments(Department dept) {
		Search search=new Search();
		search.addField("departmentId");
		search.addFilterOr(Filter.equal("departmentId", userProfile.getDepartment().getDepartmentId()),
				Filter.equal("parent",dept));
		return departmentDao.search(search);
	}
	@Override
	public List<Long> getDepartmentsByParent(Long dept) {
		Search search=new Search();
		search.addField("departmentId");
		search.addFilterOr(Filter.equal("departmentId", dept),
				Filter.ilike("parentLink","%;"+dept+";%"));
		return departmentDao.search(search);
	}
	@Override
	public List<Department> findDepartmentsByCodeOrName(
			SecurityContextHolderAwareRequestWrapper request, String query,
			boolean isTransaction) {
		Search search=new Search(Department.class);
		if(isTransaction){				
			if (request.isUserInRole("BMS_POPR_ALL")||request.isUserInRole("BMS_CONTRACT_ALL")){
				search.addFilterOr(Filter.ilike("costCenterNo", "%"+query+"%"),Filter.ilike("name", "%"+query+"%"));

			}else{
				search.addFilterOr(Filter.ilike("costCenterNo", "%"+query+"%"),Filter.ilike("name", "%"+query+"%"));
				search.addFilterOr(Filter.equal("departmentId", userProfile.getDepartment().getDepartmentId()),
						Filter.equal("parent",userProfile.getDepartment()));
			}

		}else{
			search.addFilterOr(Filter.ilike("costCenterNo", "%"+query+"%"),Filter.ilike("name", "%"+query+"%"));	
		}
		return departmentDao.search(search);
	}

	@Override
	public List<Department> findDepartmentsByCodeOrName(
			SecurityContextHolderAwareRequestWrapper request, String query,
			boolean isTransaction, Integer transType) {
		Search search=new Search(Department.class);
//		if(transType.equals(systemConfig.CONTRACT)){
//			if (request.isUserInRole("BMS_CONTRACT_ALL")){
//				search.addFilterOr(Filter.ilike("costCenterNo", "%"+query+"%"),Filter.ilike("name", "%"+query+"%"));
//
//			}else{
//				search.addFilterOr(Filter.ilike("costCenterNo", "%"+query+"%"),Filter.ilike("name", "%"+query+"%"));
//				search.addFilterOr(Filter.equal("departmentId", userProfile.getDepartment().getDepartmentId()),
//						Filter.equal("parent",userProfile.getDepartment()));
//			}
//		}
//		if(transType.equals(systemConfig.POPR)){
//			if (request.isUserInRole("BMS_POPR_ALL")){
//				search.addFilterOr(Filter.ilike("costCenterNo", "%"+query+"%"),Filter.ilike("name", "%"+query+"%"));
//
//			}else{
//				search.addFilterOr(Filter.ilike("costCenterNo", "%"+query+"%"),Filter.ilike("name", "%"+query+"%"));
//				search.addFilterOr(Filter.equal("departmentId", userProfile.getDepartment().getDepartmentId()),
//						Filter.equal("parent",userProfile.getDepartment()));
//			}
//		}
		return departmentDao.search(search);
	}

	@Override
	public List<Department> findDepartmentsByLstCostCenter(List<Long> lstCostCenter) {
//		Search search=new Search(Department.class);
//		search.addFilterIn("costCenterNo", lstCostCenter);
		
		return null;
	}
	
	@Override
	public List<Long> findDepartmentsByLstCode(List<String> lstCostCenter) {
		Search search=new Search(Department.class);
		search.addField("departmentId");
		search.addFilterIn("departmentCode", lstCostCenter);
		
		return departmentDao.search(search);
	}
	
	@Override
	public List<Department> findDepartmentsWithDepartmentHead() {
//		List<Department> lst = departmentDao.findDepartmentsWithDepartmentHead();
		return null;
	}
	@Override
	public List<Department> findDepartmentsWithDepartmentCOO() {
//		List<Department> lst = departmentDao.findDepartmentsWithDepartmentCOO();
		return null;
	}
	
	@Override
	public DepartmentBean searchName(DepartmentBean bean,String querySearch,HttpServletRequest request) {

//		SearchResult<Long> rootsAndTotal=departmentDao.searchRootAndTotalName(querySearch,request);
//		List<Long>  rootResult=rootsAndTotal.getResult();
//		Search search=new Search(Department.class);
//		if(StringUtils.isNotBlank(querySearch)){
//			search.addFilterOr(Filter.ilike("name", "%"+querySearch+"%"));
//		}
//		search.addFilterIn("rootId", rootResult);
//		List<Department> result= departmentDao.search(search);
//
//		String parentnode="";
//
//		for(int i=0;i< result.size();i++){
//			parentnode += result.get(i).getParentLink();
//		}			
//		List<Department> leaf = new ArrayList<Department>();
//		for(int i=0;i< result.size();i++){
//			if(parentnode.indexOf(";"+result.get(i).getDepartmentId()+";")==-1){
//				leaf.add(result.get(i));
//			}
//		}
//		result.clear();
//		List<DepartmentTreeBean> listroot = new ArrayList<DepartmentTreeBean>();
//		Map<String, String> mapAccountName=new LinkedMap();
//		while(leaf.size()!=0){
//			DepartmentTreeBean node=new DepartmentTreeBean();
//			Department department = (Department)leaf.get(0);
//			leaf.remove(0);
//			node.setId(department.getDepartmentId());
//			node.setText(department.getName());	
//
//			node.setParentName(department.getParent()==null?"":department.getParent().getName());
//			node.setCostCenterNo(department.getDepartmentCode());
//	/*		node.setManager(department.getManager());
//			node.setLocation(department.getLocation());*/
//			node.setNote(department.getNote());
//			node.setBean(department);
//			DepartmentTreeBean root;
//
//			if(department.getParent()!=null){
//				node.setParentObj(department.getParent());
//				if(!findToAdd(department,listroot))
//				{	
//					root = buildTree(node,null);
//					listroot.add(root);
//				}
//			}else{
//				root = node;
//				listroot.add(root);
//			}
//		}
//
//		bean.setTree(listroot);

		return bean;
	}
	
	/**
	 * This method will return the list of CostCenter of departments children by rootId
	 * HungNN added 18/11/2014
	 */
	@Override
	public List<String> getLstCostCenterNo(Long rootId) {
		Department department=departmentDao.find(rootId);
		Search search=new Search(Department.class);
		search.addField("costCenterNo");
		search.addFilterILike("parentLink", "%;"+rootId+";%");
		List<String> list=departmentDao.search(search);
		return list;
	}
	
	/**
	 * This method will return the list of children department by parent Id
	 * HungNN added 19/11/2014
	 */
	@Override
	public List<Department> getLstDepartmentChildByParent(Long dept) {
		Search search=new Search(Department.class);
//		search.addFilterOr(Filter.equal("departmentId", dept),
//				Filter.ilike("parentLink","%;"+dept+";%"));
		search.addFilterILike("parentLink","%;"+dept+";%");
		return departmentDao.search(search);
	}
	@Override
	public List<Department> findAll() {
		return departmentDao.findAll();
	}
	
	@Override
	public List<Long> getDepartmentsFindParent(Long departmentId) {
		Department department=findById(departmentId);	
		if(department==null){
			return new ArrayList<Long>();
		}
		Search search=new Search();
		search.addField("departmentId");
		List<Long> departmentIds=new ArrayList<Long>();
		try{
			String[] deptIds = department.getParentLink().split(",");
			for (String deptId : deptIds) {
				try{
					Long.parseLong(deptId);
				}catch(Exception e){
					
				}				
			}
			
		}catch(Exception e){			
		}		
		search.addFilterOr(Filter.equal("departmentId", departmentId),
				Filter.in("departmentId",departmentIds));
		
		return departmentDao.search(search);
	}
	@Override
	public Long findByLikeParentLinks(String departmentRoot,Long departmentId) {
		Search search=new Search(Department.class);
		search.addFilterILike("parentLink", "%;"+departmentRoot+";%");
		search.addFilterEqual("departmentId", departmentId);		
		search.addField("departmentId");
		List<Long> result= departmentDao.search(search);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
}

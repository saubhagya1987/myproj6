package vn.com.unit.fe_credit.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

import vn.com.unit.fe_credit.bean.DepartmentBean;
import vn.com.unit.fe_credit.bean.DepartmentTreeBean;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Department;

/**
 * DepartmentService
 * 
 * @author user
 * @version 1.0 7-10-2014
 */
public interface DepartmentService {

	DepartmentBean search(DepartmentBean bean);
	
	Department findById(Long id);
	
	void saveOrUpdateDeapartment(Department dept);
	
	void updateDeapartmentChild(Department dept);
	
	void deleteDepartment(Long id);
	
	public List<DepartmentTreeBean> createTree(Long Id);
	
	Map<Long, Department> searchByListAccount(List<Account> result);
	
	List<Department> findDepartments(String code, Long branchId);
	
	List<Department> findDepartmentsByCodeOrName(String query,boolean isTransaction);
	
	List<DepartmentTreeBean> createTreeForCombo(Long currentNode);
	
	void deleteByNode(Long id);
	
	Department findByCode(String code);
	
	List<Long> getAuthorizedDepartments(Department dept);
	
	Department findByDepartmentCode(String code);
	
	List<Department> findOneDepartmentsByUser(Long departmentId);
	
	List<Department> findDepartmentsByCodeOrName(SecurityContextHolderAwareRequestWrapper request, String code,boolean b);
	
	List<Department> findDepartmentsByCodeOrName(SecurityContextHolderAwareRequestWrapper request, String code,boolean b, Integer transType);
	
	List<Long> getDepartmentsByParent(Long dept);
	
	List<Long> getDivision();
	
	List<Department> findDepartmentsByType(String code, Long branchId, Integer type);
	
	List<Department> findDepartmentsByLstCostCenter(List<Long> lstCostCenter);
	
	List<Department> findDepartmentsWithDepartmentHead();
	
	List<Department> findDepartmentsWithDepartmentCOO();
	
	DepartmentBean searchName(DepartmentBean bean, String querySearch, HttpServletRequest request);
	
	/**
	 * This method will return the list of CostCenter of departments children by rootId
	 * HungNN added 18/11/2014
	 */
	List<String> getLstCostCenterNo(Long rootId) ;
	
	/**
	 * This method will return the list of departments children by parent Id
	 * HungNN added 19/11/2014
	 */
	List<Department> getLstDepartmentChildByParent(Long dept);
	
	List<Department> findAll();
	List<Long> getDepartmentsFindParent(Long departmentId);

	Long findByLikeParentLinks(String departmentRoot,Long departmentId);

	List<Long> findDepartmentsByLstCode(List<String> lstCostCenter);
}

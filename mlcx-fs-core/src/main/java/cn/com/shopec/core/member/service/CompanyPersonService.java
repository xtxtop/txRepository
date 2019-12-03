package cn.com.shopec.core.member.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.member.model.CompanyPerson;

/**
 * 集团人员名单表 服务接口类
 */
public interface CompanyPersonService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CompanyPerson的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CompanyPerson> getCompanyPersonList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CompanyPerson的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CompanyPerson> getCompanyPersonPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CompanyPerson对象
	 * @param id 主键id
	 * @return
	 */
	public CompanyPerson getCompanyPerson(String id);

	/**
	 * 根据主键数组，查询并返回一组CompanyPerson对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CompanyPerson> getCompanyPersonByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CompanyPerson记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CompanyPerson> delCompanyPerson(String id, Operator operator);
	
	/**
	 * 新增一条CompanyPerson记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param companyPerson 新增的CompanyPerson数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CompanyPerson> addCompanyPerson(CompanyPerson companyPerson, Operator operator);
	
	/**
	 * 根据主键，更新一条CompanyPerson记录
	 * @param companyPerson 更新的CompanyPerson数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CompanyPerson> updateCompanyPerson(CompanyPerson companyPerson, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CompanyPerson对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CompanyPerson obj);

	public ResultInfo<CompanyPerson> importCompanyPersonInfo(MultipartFile file, Operator operator)throws Exception ;

	public String exportCompanyPerson()throws Exception ;
	
	/**
	 * 根据手机号，取的集团会员的常用基础信息
	 * @param memberNo
	 * @return
	 */
	public CompanyPerson getCompanyPersonPhone(String mobilePhone); 
	
}

package cn.com.shopec.core.resource.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.ParkCompanyRel;

/**
 * ParkCompanyRel 服务接口类
 */
public interface ParkCompanyRelService extends BaseService {

	/**
	 * 根据查询条件，查询并返回ParkCompanyRel的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<ParkCompanyRel> getParkCompanyRelList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回ParkCompanyRel的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<ParkCompanyRel> getParkCompanyRelPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个ParkCompanyRel对象
	 * @param id 主键id
	 * @return
	 */
	public ParkCompanyRel getParkCompanyRel(String id);

	/**
	 * 根据主键数组，查询并返回一组ParkCompanyRel对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ParkCompanyRel> getParkCompanyRelByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的ParkCompanyRel记录
	 * @param id 主键id
	 * @param companyId 
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkCompanyRel> delParkCompanyRel(String id, String companyId, Operator operator);
	
	/**
	 * 新增一条ParkCompanyRel记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param parkCompanyRel 新增的ParkCompanyRel数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkCompanyRel> addParkCompanyRel(ParkCompanyRel parkCompanyRel, Operator operator);
	
	/**
	 * 根据主键，更新一条ParkCompanyRel记录
	 * @param parkCompanyRel 更新的ParkCompanyRel数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkCompanyRel> updateParkCompanyRel(ParkCompanyRel parkCompanyRel, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为ParkCompanyRel对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ParkCompanyRel obj);
		
}

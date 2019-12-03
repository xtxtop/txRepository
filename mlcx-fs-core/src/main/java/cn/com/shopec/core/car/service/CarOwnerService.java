package cn.com.shopec.core.car.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarOwner;

/**
 * 车主表 服务接口类
 */
public interface CarOwnerService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarOwner的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarOwner> getCarOwnerList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarOwner的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarOwner> getCarOwnerPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarOwner对象
	 * @param id 主键id
	 * @return
	 */
	public CarOwner getCarOwner(String id);

	/**
	 * 根据主键数组，查询并返回一组CarOwner对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarOwner> getCarOwnerByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarOwner记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarOwner> delCarOwner(String id, Operator operator);
	
	/**
	 * 新增一条CarOwner记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carOwner 新增的CarOwner数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarOwner> addCarOwner(CarOwner carOwner, Operator operator);
	
	/**
	 * 根据主键，更新一条CarOwner记录
	 * @param carOwner 更新的CarOwner数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarOwner> updateCarOwner(CarOwner carOwner, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarOwner对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarOwner obj);
/**
 * 批量导入车主信息 @author zln
 * */
	public ResultInfo<CarOwner> importCarOwner (MultipartFile file, Operator operator)throws Exception;
		
}

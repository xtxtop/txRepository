package cn.com.shopec.core.ml.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CLoveCar;
import cn.com.shopec.core.ml.vo.BannerVo;

/**
 * CLoveCar 服务接口类
 */
public interface CLoveCarService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CLoveCar的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CLoveCar> getCLoveCarList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CLoveCar的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CLoveCar> getCLoveCarPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个CLoveCar对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CLoveCar getCLoveCar(String id);

	/**
	 * 根据主键数组，查询并返回一组CLoveCar对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CLoveCar> getCLoveCarByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CLoveCar记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CLoveCar> delCLoveCar(String id, Operator operator);

	/**
	 * 新增一条CLoveCar记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cLoveCar
	 *            新增的CLoveCar数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CLoveCar> addCLoveCar(CLoveCar cLoveCar, Operator operator);

	/**
	 * 根据主键，更新一条CLoveCar记录
	 * 
	 * @param cLoveCar
	 *            更新的CLoveCar数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CLoveCar> updateCLoveCar(CLoveCar cLoveCar, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CLoveCar对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CLoveCar obj);


	ResultInfo toInsertLoveCar(CLoveCar cLoveCar, Operator operator);

	/**
	 * 获取爱车的顶部banner集合
	 * 
	 * @param pos
	 * @param type
	 * @param
	 * @param imgPath
	 * @return
	 */
	public List<BannerVo> getLoveCarBannerList(Integer pos, Integer type, String imgPath);
	//查看爱车信息
	public  List<CLoveCar> getLaveCar(String memberNo);
}

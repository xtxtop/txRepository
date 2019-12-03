package cn.com.shopec.core.monitor.service;

import java.util.List;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.monitor.model.CarTrack;

/**
 * 车辆轨迹表 服务接口类
 */
public interface CarTrackService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarTrack的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarTrack> getCarTrackList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarTrack的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarTrack> getCarTrackPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarTrack对象
	 * @param id 主键id
	 * @return
	 */
	public CarTrack getCarTrack(String id);

	/**
	 * 根据主键数组，查询并返回一组CarTrack对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarTrack> getCarTrackByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarTrack记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarTrack> delCarTrack(String id);
	
	/**
	 * 新增一条CarTrack记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carTrack 新增的CarTrack数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarTrack> addCarTrack(CarTrack carTrack);
	
	/**
	 * 根据主键，更新一条CarTrack记录
	 * @param carTrack 更新的CarTrack数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarTrack> updateCarTrack(CarTrack carTrack);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarTrack对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarTrack obj);
	
	/**
	 * 添加一组车辆轨迹数据
	 * @param carTracks
	 * @return
	 */
	public ResultInfo<String> addCarTracks(List<CarTrack> carTracks);
		
}

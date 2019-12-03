package cn.com.shopec.core.ml.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.vo.AdvertCenterVo;
import cn.com.shopec.core.ml.vo.BannerVo;

/**
 * Advert 服务接口类
 */
public interface AdvertMengLongService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Advert的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<AdvertMengLong> getAdvertList(Query q);

	/**
	 * 根据查询条件，分页查询并返回Advert的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<AdvertMengLong> getAdvertPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个Advert对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public AdvertMengLong getAdvert(String id);

	/**
	 * 根据主键数组，查询并返回一组Advert对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<AdvertMengLong> getAdvertByIds(String[] ids);

	/**
	 * 根据主键，删除特定的Advert记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<AdvertMengLong> delAdvert(String id, Operator operator);

	/**
	 * 新增一条Advert记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param advert
	 *            新增的Advert数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<AdvertMengLong> addAdvert(AdvertMengLong advert, Operator operator);

	/**
	 * 根据主键，更新一条Advert记录
	 * 
	 * @param advert
	 *            更新的Advert数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<AdvertMengLong> updateAdvert(AdvertMengLong advert, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为Advert对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(AdvertMengLong obj);

	/**
	 * 充电站首页中间广告显示接口
	 */
	public List<AdvertMengLong> getHomeList(Query q);

	/**
	 * 获取最新动态
	 * 
	 * @return
	 */
	public AdvertMengLong getRecentDevelopment(Integer advertPosition, Integer type);

	/**
	 * 根据广告类型和位置获取信息
	 *
	 * @return
	 */
	public AdvertCenterVo selectByTypeAndPosition(Integer advertPosition, String imgPath, Integer type);

	/*
	 * 修改
	 */
	public ResultInfo<AdvertMengLong> updateAdverts(AdvertMengLong advert, Operator operator);

	/*
	 * 获取轮播图
	 */
	public ResultInfo<List<BannerVo>> getBannerByPos(String type, String pos);

	/**
	 * 分页查询动态数据
	 * 
	 * @param q
	 * @return
	 */
	public PageFinder<AdvertMengLong> getAdvertRecentPagedList(Query q);

}

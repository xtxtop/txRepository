package cn.com.shopec.core.ml.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.vo.AdvertVo;
import cn.com.shopec.core.ml.vo.BannerVo;

/**
 * Advert 广告学数据库处理类
 */
public interface AdvertMengLongDao extends BaseDao<AdvertMengLong, String> {
	/**
	 * 充电站首页中部显示接口
	 * 
	 */
	public List<AdvertMengLong> getHomeList(Query q);

	public int updateAdverts(AdvertMengLong advert);

	/**
	 * 
	 * @param pos
	 *            区域位置
	 * @param type
	 *            广告类型
	 * @param imgPath
	 *            广告url前缀
	 * @param advertType
	 *            每种广告类型
	 * @return
	 */
	List<BannerVo> searchBannerByPosAndType(Integer pos, Integer type, String imgPath, Integer advertType);

	/**
	 * @param pos
	 *            区域位置
	 * @param type
	 *            广告类型
	 * @param imgPath
	 *            广告url前缀
	 * @return
	 */
	List<BannerVo> searchBannerByPosAndType_two(Integer pos, Integer type, String imgPath);

	public List<AdvertMengLong> pageRecentList(Query q);

	/**
	 * 获取最新动态
	 * 
	 * @param advertPosition
	 *            区域位置
	 * @Param type 广告类型
	 * 
	 * @return
	 */
	public AdvertMengLong getRecentDevement(Integer advertPostion, Integer type);

	/**
	 * 根据广告类型和区域位置获取信息
	 * 
	 * @param advertPosition
	 *            区域位置
	 * @param advertType
	 *            每种广告类型
	 * @param type
	 *            广告类型
	 * @return
	 */
	public List<AdvertVo> selectAdvertByTypeAndPosition(Integer advertPosition, Integer advertType, Integer type);

}

package cn.com.shopec.core.ml.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.vo.PileVo;
import cn.com.shopec.core.ml.vo.TerminalDetailsVo;

/**
 * 充电桩 数据库处理类
 */
public interface ChargingPileDao extends BaseDao<ChargingPile, String> {
	/** 获取终端列表 */
	List<PileVo> pagePileQuery(Query q);

	/** 获取终端详情 */
	TerminalDetailsVo getTerminalDetails(@Param("chargingPileNo") String chargingPileNo,
			@Param("parkingLockNo") String parkingLockNo);
}

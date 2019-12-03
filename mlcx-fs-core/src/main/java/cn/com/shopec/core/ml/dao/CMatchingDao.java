package cn.com.shopec.core.ml.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.ml.model.CMatching;
import cn.com.shopec.core.ml.vo.MatchingVo;

/**
 * 充电站周边配套 数据库处理类
 */
public interface CMatchingDao extends BaseDao<CMatching, String> {
	List<MatchingVo> searchInMatchingNos(@Param("ids") String[] ids, @Param("imgPath") String imgPath);
}

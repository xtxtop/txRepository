package cn.com.shopec.core.member.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.member.model.MemberLevel;

/**
 * 会员等级表 数据库处理类
 */
public interface MemberLevelDao extends BaseDao<MemberLevel, String> {
	/**
	 * 获取会员当前等级的积分值 zln
	 */
	Integer getNowLevelPoints(int memberPoint);

	/**
	 * 获取会员下一等级的积分值 zln
	 */
	Integer getNextLevelPoints(int memberPoint);

	// 根据等级积分查找 名称
	MemberLevel getMemberLevelNex(int nextLevelPoint);

}

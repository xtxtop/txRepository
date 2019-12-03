package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.RedeemCode;

/**
 * 优惠券表 服务接口类
 */
public interface RedeemCodeService extends BaseService {

	/**
	 * 根据查询条件，查询并返回RedeemCode的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<RedeemCode> getRedeemCodeList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回RedeemCode的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<RedeemCode> getRedeemCodePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个RedeemCode对象
	 * @param redCode 
	 * @return
	 */
	public RedeemCode getRedeemCode(String redCode);

	/**
	 * 根据主键数组，查询并返回一组RedeemCode对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<RedeemCode> getRedeemCodeByIds(String[] redCodes);
	
	/**
	 * 新增一条RedeemCode记录
	 * @param memberArray 
	 * @param RedeemCode 
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<RedeemCode> addRedeemCode(RedeemCode redeemCode, Operator operator);
	
	/**
	 * 修改一条RedeemCode记录
	 * @param redeemCode
	 * @param operator 操作人对象
	 * @return
	 */
	ResultInfo<RedeemCode> updateRedeemCode(RedeemCode redeemCode, Operator operator);

	/**
	 * 生成redCode（随机8个大写字母与数字组合）
	 * 
	 * @return
	 */
	public String generateRedCode();

	/**
	 * 为RedeemCode对象设置更新时必要变更的值
	 * 
	 * @param obj
	 */
	void fillUpdateValues(RedeemCode redeemCode, Operator operator);

	/**
	 * 兑换优惠券
	 * @param redCode
	 * @param memberNo
	 * @param operator
	 * @return
	 */
	ResultInfo<RedeemCode> redeemCoupon(String redCode, String memberNo, Operator operator);

	
}

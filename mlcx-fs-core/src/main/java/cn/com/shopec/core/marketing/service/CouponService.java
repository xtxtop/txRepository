package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Coupon;

/**
 * 优惠券表 服务接口类
 */
public interface CouponService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Coupon的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<Coupon> getCouponList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回Coupon的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<Coupon> getCouponPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个Coupon对象
	 * @param id 主键id
	 * @return
	 */
	public Coupon getCoupon(String id);

	/**
	 * 根据主键数组，查询并返回一组Coupon对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Coupon> getCouponByIds(String[] ids);
	
	/**
	 * 新增一条Coupon记录，该新增将会无视方案限制数量
	 * @param memberArray 
	 * @param Coupon 新增的Coupon数据
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Coupon> addCoupon(Coupon coupon, Operator operator);
	
	/**
	 * 批量增加
	 * @param coupon
	 * @param memberArray
	 * @param operator
	 * @return
	 */
	public ResultInfo<Coupon> addCoupon(Coupon coupon, String[] memberArray, Operator operator);
	/**
	 * 根据主键，更新一条Coupon记录
	 * @param order 更新的Coupon数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Coupon> updateCoupon(Coupon coupon, Operator operator);
	/**
	 * 根据主键，启动或停用一条Coupon
	 * @param advert 更新的Coupon数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Coupon> updateCouponAvailable(Coupon coupon, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();

	/**
	 * 填充新增时必须要的值
	 * @param coupon
	 */
	public void fillDefaultValues(Coupon coupon);

	public List<Coupon> getCouponListOrder(Query q);

	/**
	 * 得到优惠卷信息（管理端用）
	 * @param q
	 * @return
	 */
	public PageFinder<Coupon> getMemberCouponPageList(Query q);
	
}

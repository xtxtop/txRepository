package cn.com.shopec.core.finace.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.PaymentRecord;

/**
 * 支付记录表 服务接口类
 */
public interface PaymentRecordService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PaymentRecord的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<PaymentRecord> getPaymentRecordList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回PaymentRecord的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<PaymentRecord> getPaymentRecordPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个PaymentRecord对象
	 * @param id 主键id
	 * @return
	 */
	public PaymentRecord getPaymentRecord(String id);

	/**
	 * 根据主键数组，查询并返回一组PaymentRecord对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PaymentRecord> getPaymentRecordByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的PaymentRecord记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PaymentRecord> delPaymentRecord(String id, Operator operator);
	
	/**
	 * 新增一条PaymentRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param paymentRecord 新增的PaymentRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PaymentRecord> addPaymentRecord(PaymentRecord paymentRecord, Operator operator);
	
	/**
	 * 根据主键，更新一条PaymentRecord记录
	 * @param paymentRecord 更新的PaymentRecord数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PaymentRecord> updatePaymentRecord(PaymentRecord paymentRecord, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为PaymentRecord对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PaymentRecord obj);
	
    //根据支付流水号 查出 支付记录表
	public PaymentRecord getPaymentFlowNoById(String paymentFlowNo);
		
	/**
	 * 供日租统计对账使用-按指定时间统计
	 * @param orderNos
	 * @param paidTimeStart
	 * @param paidTimeEnd
	 * @return
	 */
	public Map<String, Object> getMerchantOrderPaidAmount(PaymentRecord paymentRecord);
	/**
	 *
	 * 供日租统计对账使用-按月统计
	 * @param orderNos
	 * @return
	 */
	public List<Map<String, Object>> getMerchantOrderPaidAmountByMonth(PaymentRecord paymentRecord);
}

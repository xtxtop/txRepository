package cn.com.shopec.core.finace.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.Invoice;

/**
 * 发票申请表 服务接口类
 */
public interface InvoiceService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Invoice的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<Invoice> getInvoiceList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回Invoice的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<Invoice> getInvoicePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个Invoice对象
	 * @param id 主键id
	 * @return
	 */
	public Invoice getInvoice(String id);

	/**
	 * 根据主键数组，查询并返回一组Invoice对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Invoice> getInvoiceByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的Invoice记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Invoice> delInvoice(String id, Operator operator);
	
	/**
	 * 新增一条Invoice记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param invoice 新增的Invoice数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Invoice> addInvoice(Invoice invoice, Operator operator);
	
	/**
	 * 根据主键，更新一条Invoice记录
	 * @param invoice 更新的Invoice数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Invoice> updateInvoice(Invoice invoice, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为Invoice对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Invoice obj);
	/**
	 * @author zln 查询一个月内的开票记录
	 * */
	public List<Invoice> getInvoiceListByMemberMonth(Query q);
	/**
	* @author zln 查询更多的开票记录
	* */
	public PageFinder<Invoice> getMoreMonthMyInvoice(Query q);
/**
 * 导入发票开票记录
 * */
	public ResultInfo<Invoice> importInvoice(MultipartFile file, Operator operator)throws Exception;

	public List<Invoice> getInvoiceByOrderNo(String orderNo);

	public Long count(Query q);

	public Invoice getInvoiceDetailByOrderNo(String orderNo);
		
}

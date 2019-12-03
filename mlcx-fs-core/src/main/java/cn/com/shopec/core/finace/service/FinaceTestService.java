package cn.com.shopec.core.finace.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.FinaceTest;

/**
 * FinaceTest 服务接口类
 */
public interface FinaceTestService extends BaseService {

	/**
	 * 根据查询条件，查询并返回FinaceTest的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<FinaceTest> getFinaceTestList(Query q);

	/**
	 * 根据查询条件，分页查询并返回FinaceTest的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<FinaceTest> getFinaceTestPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个FinaceTest对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public FinaceTest getFinaceTest(String id);

	/**
	 * 根据主键数组，查询并返回一组FinaceTest对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<FinaceTest> getFinaceTestByIds(String[] ids);

	/**
	 * 根据主键，删除特定的FinaceTest记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<FinaceTest> delFinaceTest(String id, Operator operator);

	/**
	 * 新增一条FinaceTest记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param finaceTest
	 *            新增的FinaceTest数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<FinaceTest> addFinaceTest(FinaceTest finaceTest, Operator operator);

	/**
	 * 根据主键，更新一条FinaceTest记录
	 * 
	 * @param finaceTest
	 *            更新的FinaceTest数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<FinaceTest> updateFinaceTest(FinaceTest finaceTest, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为FinaceTest对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(FinaceTest obj);

	public ResultInfo<FinaceTest> importInvoice(MultipartFile file, Operator operator);

	/**
	 * 批量增加FinaceTest
	 * @param finaceTests
	 * @param resultInfo 
	 * @return 
	 */
	public ResultInfo<FinaceTest> addFinaceTests(List<FinaceTest> finaceTests);

	/**
	 * 将读取到的支付宝账单信息转换为FinaceTest
	 * @param data
	 * @return
	 */
	public List<FinaceTest> getFinaceTestFromAlipayData(List<String> data);
	
	/**
	 * 将读取到的微信账单信息转换为FinaceTest
	 * @param data
	 * @return
	 */
	public List<FinaceTest> getFinaceTestFromWeChart(String dataString, String time);	
	
	/**
	 * 导入支付宝转账数据
	 * 
	 * @param multipartFile
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	public ResultInfo<FinaceTest> importTransferOfAccount(MultipartFile multipartFile) throws Exception;


}

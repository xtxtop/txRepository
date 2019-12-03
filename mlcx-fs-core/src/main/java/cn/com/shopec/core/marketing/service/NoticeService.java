package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Notice;

/**
 * Notice 服务接口类
 */
public interface NoticeService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Notice的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<Notice> getNoticeList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回Notice的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<Notice> getNoticePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个Notice对象
	 * @param id 主键id
	 * @return
	 */
	public Notice getNotice(String id);

	/**
	 * 根据主键数组，查询并返回一组Notice对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Notice> getNoticeByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的Notice记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Notice> delNotice(String id, Operator operator);
	
	/**
	 * 新增一条Notice记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param notice 新增的Notice数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Notice> addNotice(Notice notice, Operator operator);
	
	/**
	 * 根据主键，更新一条Notice记录
	 * @param notice 更新的Notice数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Notice> updateNotice(Notice notice, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为Notice对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Notice obj);

	public PageFinder<Notice> getNoticePagedLists(Query q);
		
}

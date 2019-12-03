package cn.com.shopec.core.member.service;

import java.util.List;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.MemberGlodBeans;

/**
 * MemberGlodBeans 服务接口类
 */
public interface MemberGlodBeansService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MemberGlodBeans的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MemberGlodBeans> getMemberGlodBeansList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MemberGlodBeans的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MemberGlodBeans> getMemberGlodBeansPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MemberGlodBeans对象
	 * @param id 主键id
	 * @return
	 */
	public MemberGlodBeans getMemberGlodBeans(String id);

	/**
	 * 根据主键数组，查询并返回一组MemberGlodBeans对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MemberGlodBeans> getMemberGlodBeansByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MemberGlodBeans记录
	 * @param id 主键id
	 * @return
	 */
	public ResultInfo<MemberGlodBeans> delMemberGlodBeans(String id);
	
	/**
	 * 新增一条MemberGlodBeans记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param memberGlodBeans 新增的MemberGlodBeans数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @return
	 */
	public ResultInfo<MemberGlodBeans> addMemberGlodBeans(MemberGlodBeans memberGlodBeans);
	
	/**
	 * 根据主键，更新一条MemberGlodBeans记录
	 * @param memberGlodBeans 更新的MemberGlodBeans数据，且其主键不能为空
	 * @return
	 */
	public ResultInfo<MemberGlodBeans> updateMemberGlodBeans(MemberGlodBeans memberGlodBeans);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为MemberGlodBeans对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MemberGlodBeans obj);
		
}

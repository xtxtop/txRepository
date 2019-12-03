package cn.com.shopec.core.member.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.zmxy.ZhimaUtil;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.dao.MemberZhimaScoreDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberZhimaScore;
import cn.com.shopec.core.member.service.MemberZhimaScoreService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 会员信用表 服务实现类
 */
@Service
public class MemberZhimaScoreServiceImpl implements MemberZhimaScoreService {

	private static final Log log = LogFactory.getLog(MemberZhimaScoreServiceImpl.class);
	
	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private MemberDao memberDao;
	
	@Resource
	private MemberZhimaScoreDao memberZhimaScoreDao;
	
	/**
	 * 根据查询条件，查询并返回MemberZhimaScore的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberZhimaScore> getMemberZhimaScoreList(Query q) {
		List<MemberZhimaScore> list = null;
		try {
			//直接调用Dao方法进行查询
			list = memberZhimaScoreDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberZhimaScore>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回MemberZhimaScore的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MemberZhimaScore> getMemberZhimaScorePagedList(Query q) {
		PageFinder<MemberZhimaScore> page = new PageFinder<MemberZhimaScore>();
		
		List<MemberZhimaScore> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = memberZhimaScoreDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = memberZhimaScoreDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberZhimaScore>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个MemberZhimaScore对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MemberZhimaScore getMemberZhimaScore(String id) {
		MemberZhimaScore obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = memberZhimaScoreDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MemberZhimaScore对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MemberZhimaScore> getMemberZhimaScoreByIds(String[] ids) {
		List<MemberZhimaScore> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = memberZhimaScoreDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberZhimaScore>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的MemberZhimaScore记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MemberZhimaScore> delMemberZhimaScore(String id, Operator operator) {
		ResultInfo<MemberZhimaScore> resultInfo = new ResultInfo<MemberZhimaScore>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = memberZhimaScoreDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			}		  
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条MemberZhimaScore记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param memberZhimaScore 新增的MemberZhimaScore数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MemberZhimaScore> addMemberZhimaScore(MemberZhimaScore memberZhimaScore, Operator operator) {
		ResultInfo<MemberZhimaScore> resultInfo = new ResultInfo<MemberZhimaScore>();
		
		if (memberZhimaScore == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberZhimaScore = " + memberZhimaScore);
		} else {
			try {
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				memberZhimaScore.setCreateTime(now);
				memberZhimaScore.setUpdateTime(now);
				
				//调用Dao执行插入操作
				memberZhimaScoreDao.add(memberZhimaScore);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(memberZhimaScore);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}	
		}
		
		return resultInfo;
	}			
	
	/**
	 * 根据主键，更新一条MemberZhimaScore记录
	 * @param memberZhimaScore 更新的MemberZhimaScore数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MemberZhimaScore> updateMemberZhimaScore(MemberZhimaScore memberZhimaScore, Operator operator) {
		ResultInfo<MemberZhimaScore> resultInfo = new ResultInfo<MemberZhimaScore>();
		
		if (memberZhimaScore == null || memberZhimaScore.getMemberNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberZhimaScore = " + memberZhimaScore);
		} else {
			try {
				
				//设置更新时间为当前时间
				memberZhimaScore.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = memberZhimaScoreDao.update(memberZhimaScore);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(memberZhimaScore);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}
		
		return resultInfo;
	}	
	
	@Override
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	@Override
	public String generateTransactionId() {
		String pre = String.valueOf(new Date().getTime() * 10000);
		String suf = "";
		while (suf.length() != 13) {
			suf = String.valueOf((long)((Math.random()*9+1)*1000000000000l));
		}
		return pre + suf;//前17为日期 * 10000;后13位为随机;总共30位
	}

	@Override
	public ResultInfo<MemberZhimaScore> getScoreForZhiMa(MemberZhimaScore memberZhimaScore) throws Exception {
		ResultInfo<MemberZhimaScore> resultInfo = new ResultInfo<MemberZhimaScore>();
		
		//判断参数
		if (memberZhimaScore == null || memberZhimaScore.getMemberNo() == null || memberZhimaScore.getOpenId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			return resultInfo;
		} 
		
		//调用芝麻接口
		String  transactionId = this.generateTransactionId();
		ZhimaCreditScoreGetResponse response = ZhimaUtil.queryScore(memberZhimaScore.getOpenId(), transactionId);
		if(response == null){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("调用芝麻信用接口失败");
			return resultInfo;
		}
		
		//开始维护到数据库中
		memberZhimaScore.setScore(Integer.valueOf(response.getZmScore()));
		memberZhimaScore.setResponseErrorCode(response.getErrorCode());
		memberZhimaScore.setResponseErrorMsg(response.getErrorMessage());
		memberZhimaScore.setTransactionId(transactionId);
		memberZhimaScore.setTransactionTime(new Date());

		//判断是更新还是新增
		MemberZhimaScore memberZhimaScoremTemp = memberZhimaScoreDao.get(memberZhimaScore.getMemberNo());
		if(memberZhimaScoremTemp != null){
			//设置更新时间为当前时间
			memberZhimaScore.setUpdateTime(new Date());
			memberZhimaScore.setMemberName(memberZhimaScoremTemp.getMemberName());
			memberZhimaScore.setMobilePhone(memberZhimaScoremTemp.getMobilePhone());
			memberZhimaScoreDao.updateForBusiness(memberZhimaScore);
		}else{
			if(memberZhimaScore.getMemberName() == null || memberZhimaScore.getMobilePhone() == null){
				Member member = memberDao.get(memberZhimaScore.getMemberNo());
				if(member != null){
					memberZhimaScore.setMemberName(member.getMemberName());
					memberZhimaScore.setMobilePhone(member.getMobilePhone());
				}
			}
			this.addMemberZhimaScore(memberZhimaScore, null);
		}
		
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(memberZhimaScore);
		return resultInfo;
	}

}

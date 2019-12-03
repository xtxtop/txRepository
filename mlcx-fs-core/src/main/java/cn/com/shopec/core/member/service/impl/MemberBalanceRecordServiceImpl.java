package cn.com.shopec.core.member.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberBalanceDao;
import cn.com.shopec.core.member.dao.MemberBalanceRecordDao;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.MemberBalanceRecord;
import cn.com.shopec.core.member.service.MemberBalanceRecordService;

/**
 * Member 服务实现类
 */
@Service
public class MemberBalanceRecordServiceImpl implements MemberBalanceRecordService {

	private static final Log log = LogFactory.getLog(MemberBalanceRecordServiceImpl.class);

	@Resource
	private MemberDao memberDao;

	@Resource
	private MemberBalanceDao memberBalanceDao;

	@Resource
	private MemberBalanceRecordDao memberBalanceRecordDao;

	@Override
	public List<MemberBalanceRecord> getMemberBalanceRecordList(Query q) {
		List<MemberBalanceRecord> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = memberBalanceRecordDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberBalanceRecord>(0) : list;
		return list;
	}

	@Override
	public PageFinder<MemberBalanceRecord> getMemberBalanceRecordPagedList(Query q) {
		PageFinder<MemberBalanceRecord> page = new PageFinder<MemberBalanceRecord>();
		List<MemberBalanceRecord> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = memberBalanceRecordDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = memberBalanceRecordDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberBalanceRecord>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public MemberBalanceRecord getMemberBalanceRecord(String id) {
		MemberBalanceRecord obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = memberBalanceRecordDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	@Override
	public List<MemberBalanceRecord> getMemberBalanceRecordByIds(String[] ids) {
		List<MemberBalanceRecord> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = memberBalanceRecordDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberBalanceRecord>(0) : list;

		return list;
	}

	@Override
	public ResultInfo<MemberBalanceRecord> addMemberBalanceRecord(MemberBalanceRecord memberBalance,
			Operator operator) {
		ResultInfo<MemberBalanceRecord> resultInfo = new ResultInfo<MemberBalanceRecord>();

		if (memberBalance == null || memberBalance.getMemberNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " MemberBalanceRecord = " + memberBalance);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (memberBalance.getId() == null) {
					memberBalance.setId(this.generatePK());
				}
				
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					memberBalance.setOperatorType(operator.getOperatorType());
					memberBalance.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				memberBalance.setCreateTime(now);
				memberBalance.setUpdateTime(now);

				// 调用Dao执行插入操作
				memberBalanceRecordDao.add(memberBalance);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(memberBalance);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}
	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
}

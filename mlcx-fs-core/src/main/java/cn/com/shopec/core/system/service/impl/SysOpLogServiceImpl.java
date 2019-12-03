package cn.com.shopec.core.system.service.impl;



import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.dao.SysOpLogDao;
import cn.com.shopec.core.system.model.SysOpLog;
import cn.com.shopec.core.system.service.SysOpLogService;




/**
 * SysOpLog 服务实现类
 */
@Service
public class SysOpLogServiceImpl implements SysOpLogService{
	
	@Resource
	private SysOpLogDao sysOpLogDao;

	private ResultInfo<SysOpLog> resultInfo = new ResultInfo<SysOpLog>();
	
	@Override
	public PageFinder<SysOpLog> pageList(Query q) {
		PageFinder<SysOpLog> sysOpLogPage = new PageFinder<SysOpLog>();
		sysOpLogPage.setData(sysOpLogDao.pageList(q));
		sysOpLogPage.setRowCount(sysOpLogDao.count(q));
		return sysOpLogPage;
	}

	@Override
	@Transactional
	public ResultInfo<SysOpLog> add(SysOpLog sysOpLog,Operator operator) {
		try {
			if (sysOpLog != null) {
				if (sysOpLog.getLogId() == null) {
					sysOpLog.setLogId(this.generatePK());
				}
				sysOpLog.setCreateTime(new Date());
				sysOpLog.setStartCreateTime(new Date());
				if(operator!=null){
					sysOpLog.setOperatorType(operator.getOperatorType());
					sysOpLog.setOperatorId(operator.getOperatorId());
				}
				
				sysOpLogDao.add(sysOpLog);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(sysOpLog);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("日志不能为空！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}

		
		return resultInfo;
	}

	@Override
	public SysOpLog detail(String logId) {
		SysOpLog sysOpLog = sysOpLogDao.get(logId);
		return sysOpLog;
	}
	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(System.nanoTime());
	}
	
}

package cn.com.shopec.core.finace.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.finace.dao.TransToAccountDao;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.TransToAccount;
import cn.com.shopec.core.finace.service.TransToAccountService;

/**
 * 转账记录表 服务实现类
 */
@Service
public class TransToAccountServiceImpl implements TransToAccountService{

	private static final Log log = LogFactory.getLog(TransToAccountServiceImpl.class);
	
	@Resource
	private TransToAccountDao transToAccountDao;
	
	@Override
	public TransToAccount getTransToAccount(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public ResultInfo<TransToAccount> addTransToAccount(TransToAccount transToAccount, Operator operator) {
		ResultInfo<TransToAccount> resultInfo = new ResultInfo<TransToAccount>();
		
		if (transToAccount == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " transToAccount = " + transToAccount);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (transToAccount.getTransNo() == null) {
					transToAccount.setTransNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					transToAccount.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				transToAccount.setTransTime(now);
			
				//调用Dao执行插入操作
				transToAccountDao.add(transToAccount);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(transToAccount);
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
	public ResultInfo<TransToAccount> updateTransToAccount(TransToAccount transToAccount, Operator operator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generatePK() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fillDefaultValues(DepositRefund obj) {
		// TODO Auto-generated method stub
		
	}
	
	

}

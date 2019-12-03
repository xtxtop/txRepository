package cn.com.shopec.core.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.BillingScheme;
import cn.com.shopec.core.ml.service.impl.BillingSchemeServiceImpl;
import cn.com.shopec.core.system.dao.SysUserCityDao;
import cn.com.shopec.core.system.dao.SysUserDao;
import cn.com.shopec.core.system.dao.SysUserRoleRelDao;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.model.SysUserCity;
import cn.com.shopec.core.system.model.SysUserRoleRel;
import cn.com.shopec.core.system.service.SysUserService;

/**
 * SysUser 服务实现类
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	private static final Log log = LogFactory.getLog(SysUserServiceImpl.class);
	@Resource
	private SysUserDao sysUserDao;

	@Resource
	private SysUserRoleRelDao sysUserRoleRelDao;

	@Resource
	private SysUserCityDao sysUserCityDao;

	private ResultInfo<SysUser> resultInfo = new ResultInfo<SysUser>();

	@Override
	public PageFinder<SysUser> pageList(Query q) {
		PageFinder<SysUser> sysUserPage = new PageFinder<SysUser>();
		sysUserPage.setData(sysUserDao.pageList(q));
		sysUserPage.setRowCount(sysUserDao.count(q));
		return sysUserPage;
	}

	@Override
	public PageFinder<SysUser> pageList2(Query q) {
		PageFinder<SysUser> sysUserPage = new PageFinder<SysUser>();
		sysUserPage.setData(sysUserDao.pageList2(q));
		sysUserPage.setRowCount(sysUserDao.count2(q));
		return sysUserPage;
	}

	@Override
	@Transactional
	public ResultInfo<SysUser> addOrEditSysUser(SysUser sysUser) {
		ResultInfo<SysUser> resultInfo = new ResultInfo<SysUser>();

		try {
			if (sysUser != null) {
				SysUserRoleRel sysUserRoleRel = new SysUserRoleRel();
				// 添加系统用户
				Date now = new Date();
				if (sysUser.getUserId() == null || sysUser.getUserId().trim().equals("")) {
					Long count = sysUserDao.getCheckExistsByName(sysUser.getUserName());
					if (count != null && count.longValue() >= 1) {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("用户:" + sysUser.getUserName() + "，已经存在！");
						resultInfo.setData(sysUser);
						return resultInfo;
					}
					sysUser.setUserId(this.generatePK());
					sysUser.setPassword(ECMd5Utils.crypt(sysUser.getPassword()));
					sysUser.setIsDeleted(0);
					sysUser.setCreateTime(now);
					sysUser.setUpdateTime(now);
					sysUser.setLastLoginTime(now);
					sysUserDao.add(sysUser);

					for (int i = 0; i < sysUser.getSysRole().size(); i++) {
						SysRole sysRole = sysUser.getSysRole().get(i);
						sysUserRoleRel.setRoleId(sysRole.getRoleId());
						sysUserRoleRel.setUserId(sysUser.getUserId());
						sysUserRoleRelDao.add(sysUserRoleRel);
						resultInfo.setCode(Constant.SECCUESS);
						resultInfo.setData(sysUser);
					}

					// 当用户标识为城市运营时
					if (sysUser.getIdentification() != null && sysUser.getIdentification() == 3) {
						List<SysUserCity> sysUserCitys = sysUser.getSysUserCitys();
						sysUserCityDao.delete(sysUser.getUserId());
						for (int i = 0; i < sysUserCitys.size(); i++) {
							sysUserCitys.get(i).setUserId(sysUser.getUserId());
							sysUserCityDao.add(sysUserCitys.get(i));
							resultInfo.setCode(Constant.SECCUESS);
							resultInfo.setData(sysUser);
						}
					}

				} else {
					// 修改
					SysUser user = sysUserDao.get(sysUser.getUserId());
					if (!user.getPassword().equals(sysUser.getPassword())) {
						sysUser.setPassword(ECMd5Utils.crypt(sysUser.getPassword()));
					}
					// 当用户标识为城市运营时
					sysUserCityDao.delete(sysUser.getUserId());
					if (sysUser.getIdentification() != null && sysUser.getIdentification() == 3) {
						List<SysUserCity> sysUserCitys = sysUser.getSysUserCitys();
						if (sysUserCitys != null) {
							for (int i = 0; i < sysUserCitys.size(); i++) {
								sysUserCitys.get(i).setUserId(sysUser.getUserId());
								sysUserCityDao.add(sysUserCitys.get(i));
								resultInfo.setCode(Constant.SECCUESS);
								resultInfo.setData(sysUser);
							}
						}
					}
					sysUserRoleRelDao.deleteByUserId(sysUser.getUserId());
					for (int i = 0; i < sysUser.getSysRole().size(); i++) {
						SysRole sysRole = sysUser.getSysRole().get(i);
						sysUserRoleRel.setRoleId(sysRole.getRoleId());
						sysUserRoleRel.setUserId(sysUser.getUserId());
						sysUserRoleRelDao.add(sysUserRoleRel);
						resultInfo.setCode(Constant.SECCUESS);
						resultInfo.setData(sysUser);
						sysUserRoleRel.setCreateTime(now);
					}
					sysUser.setUpdateTime(now);
					sysUserDao.update(sysUser);
				}
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("系统用户不能为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());

		}
		return resultInfo;
	}

	@Override
	@Transactional
	public ResultInfo<SysUser> delete(String userId, Operator operator) {
		try {
			// 删除用户角色关系
			sysUserRoleRelDao.deleteByUserId(userId);
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			SysUser sysUser = new SysUser();
			sysUser.setUserId(userId);
			sysUser.setIsDeleted(Constant.DEL_STATE_YES);
			sysUser.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				sysUser.setOperatorType(operator.getOperatorType());
				sysUser.setOperatorId(operator.getOperatorId());
			}
			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = sysUserDao.update(sysUser);
			if (count == 1) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("未找到此用户，删除失败！");
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}

	@Override
	@Transactional
	public ResultInfo<SysUser> batchDelete(String[] sysUserIds, Operator operator) {
		ResultInfo<SysUser> resultInfo = new ResultInfo<SysUser>();
		for (String id : sysUserIds) {
			resultInfo = delete(id, operator);
		}
		return resultInfo;
	}

	@Override
	public SysUser getSysUser(String loginName, String loginPassword) {
		SysUser sysUser = sysUserDao.getSysUser(loginName, loginPassword);
		if (sysUser != null && sysUser.getIdentification() != null && sysUser.getIdentification().intValue() == 3) {
			SysUserCity sysUserCity = new SysUserCity();
			sysUserCity.setUserId(sysUser.getUserId());
			sysUser.setSysUserCitys(sysUserCityDao.queryAll(new Query(sysUserCity)));
		}
		return sysUser;
	}

	@Override
	public SysUser detail(String userId) {
		SysUser sysUser = sysUserDao.get(userId);
		if (sysUser != null && sysUser.getIdentification() != null && sysUser.getIdentification().intValue() == 3) {
			SysUserCity sysUserCity = new SysUserCity();
			sysUserCity.setUserId(sysUser.getUserId());
			sysUser.setSysUserCitys(sysUserCityDao.queryAll(new Query(sysUserCity)));
		}
		return sysUser;
	}

	private String generatePK() {
		return String.valueOf(System.nanoTime());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SysUser> getSysUserByIds(String[] ids) {
		List<SysUser> list = null;
		if (ids != null) {
			try {
				// 调用dao，根据主键数组查询
				list = sysUserDao.getByIds(ids);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<SysUser>(0) : list;

		return list;
	}
	@Transactional
	public SysUser getUser(String id){
		SysUser obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = sysUserDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}
}

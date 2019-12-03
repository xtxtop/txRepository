package cn.com.shopec.core.ml.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.dao.AdvertMengLongDao;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.vo.AdvertCenterVo;
import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.AdvertVo;
import cn.com.shopec.core.ml.vo.BannerVo;

/**
 * AdvertMengLong 服务实现类
 */
@Service
public class AdvertMengLongServiceImpl implements AdvertMengLongService {

	private static final Log log = LogFactory.getLog(AdvertMengLongServiceImpl.class);

	@Resource
	private AdvertMengLongDao advertMengLongDao;
	@Value("${image_path}")
	private String imgPath;

	/**
	 * 根据查询条件，查询并返回Advert的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AdvertMengLong> getAdvertList(Query q) {
		List<AdvertMengLong> list = null;
		try {
			// 已删除的不查出
			AdvertMengLong advert = (AdvertMengLong) q.getQ();
			if (advert != null) {
				advert.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = advertMengLongDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<AdvertMengLong>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回Advert的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<AdvertMengLong> getAdvertPagedList(Query q) {
		PageFinder<AdvertMengLong> page = new PageFinder<AdvertMengLong>();

		List<AdvertMengLong> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			AdvertMengLong advert = (AdvertMengLong) q.getQ();
			if (advert != null) {
				advert.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = advertMengLongDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = advertMengLongDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<AdvertMengLong>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个Advert对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public AdvertMengLong getAdvert(String id) {
		AdvertMengLong obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = advertMengLongDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Advert对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AdvertMengLong> getAdvertByIds(String[] ids) {
		List<AdvertMengLong> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = advertMengLongDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<AdvertMengLong>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的Advert记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<AdvertMengLong> delAdvert(String id, Operator operator) {
		ResultInfo<AdvertMengLong> resultInfo = new ResultInfo<AdvertMengLong>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			AdvertMengLong advert = new AdvertMengLong();
			advert.setAdvertNo(id);
			advert.setIsDeleted(Constant.DEL_STATE_YES);
			advert.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				advert.setOperatorType(operator.getOperatorType());
				advert.setOperatorId(operator.getOperatorId());
			}

			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = advertMengLongDao.update(advert);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(advert);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条Advert记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param advert
	 *            新增的Advert数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<AdvertMengLong> addAdvert(AdvertMengLong advert, Operator operator) {
		ResultInfo<AdvertMengLong> resultInfo = new ResultInfo<AdvertMengLong>();

		if (advert == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " advert = " + advert);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (advert.getAdvertNo() == null) {
					advert.setAdvertNo(this.generatePK());
					if (advert.getLinkType() == 1) {
						advert.setLinkUrl(advert.getFilePath() + "/advert/" + advert.getAdvertNo().substring(0, 2) + "/"
								+ advert.getAdvertNo().substring(2, advert.getAdvertNo().length()) + ".html");
					}

				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					advert.setOperatorType(operator.getOperatorType());
					advert.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				advert.setCreateTime(now);
				advert.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(advert);

				// 调用Dao执行插入操作
				advertMengLongDao.add(advert);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(advert);
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
	 * 根据主键，更新一条Advert记录
	 * 
	 * @param advert
	 *            更新的Advert数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<AdvertMengLong> updateAdvert(AdvertMengLong advert, Operator operator) {
		ResultInfo<AdvertMengLong> resultInfo = new ResultInfo<AdvertMengLong>();

		if (advert == null || advert.getAdvertNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " advert = " + advert);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					advert.setOperatorType(operator.getOperatorType());
					advert.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				advert.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = advertMengLongDao.update(advert);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(advert);
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
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}

	/**
	 * 为Advert对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(AdvertMengLong obj) {
		if (obj != null) {
			if (obj.getIsDeleted() == null) {
				obj.setIsDeleted(0);
			}
		}
	}

	@Override
	public ResultInfo<AdvertMengLong> updateAdverts(AdvertMengLong advert, Operator operator) {
		ResultInfo<AdvertMengLong> resultInfo = new ResultInfo<AdvertMengLong>();

		if (advert == null || advert.getAdvertNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " advert = " + advert);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					advert.setOperatorType(operator.getOperatorType());
					advert.setOperatorId(operator.getOperatorId());
				}
				if (advert.getAdvertType() == null) {
					if (advert.getLinkType() == 1) {
						advert.setLinkUrl(advert.getFilePath() + "/advert/" + advert.getAdvertNo().substring(0, 2) + "/"
								+ advert.getAdvertNo().substring(2, advert.getAdvertNo().length()) + ".html");
					}
				} else {
					if (advert.getLinkType() == 1 && advert.getAdvertType() != 5) {
						advert.setLinkUrl(advert.getFilePath() + "/advert/" + advert.getAdvertNo().substring(0, 2) + "/"
								+ advert.getAdvertNo().substring(2, advert.getAdvertNo().length()) + ".html");
					}
				}

				// 设置更新时间为当前时间
				advert.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = advertMengLongDao.updateAdverts(advert);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(advert);
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
	public List<AdvertMengLong> getHomeList(Query q) {
		List<AdvertMengLong> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = advertMengLongDao.getHomeList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<AdvertMengLong>(0) : list;
		return list;
	}

	/**
	 * 获取最新动态
	 */
	@Override
	public AdvertMengLong getRecentDevelopment(Integer advertPosition, Integer type) {
		AdvertMengLong recentAdvert = advertMengLongDao.getRecentDevement(advertPosition, type);

		return recentAdvert == null ? new AdvertMengLong() : recentAdvert;

	}

	@Override
	public ResultInfo<List<BannerVo>> getBannerByPos(String type, String pos) {
		ResultInfo<List<BannerVo>> resultInfo = new ResultInfo<>();
		try {
			List<BannerVo> lst = advertMengLongDao.searchBannerByPosAndType_two(Integer.valueOf(pos),
					Integer.valueOf(type), imgPath);
			if (lst == null)
				lst = new ArrayList<>();
			resultInfo.setData(lst);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg("操作成功");
		} catch (NumberFormatException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		}
		return resultInfo;
	}

	@Override
	public AdvertCenterVo selectByTypeAndPosition(Integer advertPosition, String imgPath, Integer type) {

		AdvertCenterVo centerVo = new AdvertCenterVo();
		// banner集合
		List<BannerVo> bannerList = advertMengLongDao.searchBannerByPosAndType(advertPosition, 3, imgPath, 2);// 获取banner轮播图
		if (bannerList.size() > 0) {
			centerVo.setBannerVoList(bannerList);
		}
		// 图片（只需要一张）
		List<AdvertVo> advertPicList = advertMengLongDao.selectAdvertByTypeAndPosition(advertPosition, 4, type);// 获取图片集合
		if (advertPicList.size() > 0) {
			AdvertVo ad = advertPicList.get(0);
			AdvertVo adt = new AdvertVo();
			adt.setAdvertName(ad.getAdvertName());
			adt.setAdvertPicUrl(imgPath + "/" + ad.getAdvertPicUrl());
			adt.setLinkUrl(ad.getLinkUrl());
			adt.setLinkType(ad.getLinkType());
			adt.setText(ad.getText());
			centerVo.setAdvertPic(adt);
		}
		// 广告
		List<AdvertVo> advert = advertMengLongDao.selectAdvertByTypeAndPosition(advertPosition, 3, type);// 获取最新的广告
		if (null != advert && advert.size() > 0) {
			AdvertVo av = new AdvertVo();
			av.setAdvertName(advert.get(0).getAdvertName());
			av.setAdvertPicUrl(imgPath + "/" + advert.get(0).getAdvertPicUrl());
			av.setLinkUrl(advert.get(0).getLinkUrl());
			av.setLinkType(advert.get(0).getLinkType());
			av.setText(advert.get(0).getText());
			centerVo.setAdvert(av);

		}
		// 滚动文字
		List<AdvertVo> advertTextList = advertMengLongDao.selectAdvertByTypeAndPosition(advertPosition, 5, type);// 获取头条文本集合
		List<AdvertTextVo> advertTextVoList = new ArrayList<>();
		if (null != advertTextList && advertTextList.size() > 0) {
			for (AdvertVo av : advertTextList) {
				AdvertTextVo adt = new AdvertTextVo();
				adt.setAdvertName(av.getAdvertName());
				advertTextVoList.add(adt);
			}
			centerVo.setAdvertTextVoList(advertTextVoList);
		}

		if (bannerList.size() > 0 || advertPicList.size() > 0 || advertTextList.size() > 0 || null != advert) {
			return centerVo;
		} else {
			return null;
		}

	}

	/**
	 * 根据查询条件，分页查询并返回Advert的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<AdvertMengLong> getAdvertRecentPagedList(Query q) {
		PageFinder<AdvertMengLong> page = new PageFinder<AdvertMengLong>();

		List<AdvertMengLong> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			AdvertMengLong advert = (AdvertMengLong) q.getQ();
			if (advert != null) {
				advert.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = advertMengLongDao.pageRecentList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = advertMengLongDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<AdvertMengLong>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

}

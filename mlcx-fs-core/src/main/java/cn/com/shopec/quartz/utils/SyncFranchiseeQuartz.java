package cn.com.shopec.quartz.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.franchisee.dao.FranchiseeSettleDao;
import cn.com.shopec.core.franchisee.model.FranchiseeProfit;
import cn.com.shopec.core.franchisee.model.FranchiseeSettle;
import cn.com.shopec.core.franchisee.model.FranchiseeVo;
import cn.com.shopec.core.franchisee.service.FranchiseeProfitService;
import cn.com.shopec.core.franchisee.service.FranchiseeService;
import cn.com.shopec.core.franchisee.service.FranchiseeSettleService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 同步加盟商的月结算单数据 
 * 定时任务触发时间 每月的第一天凌晨1点执行
 * @author LiHuan Date 2017年9月26日 下午5:23:09
 */
public class SyncFranchiseeQuartz {

	private Log logger = LogFactory.getLog(SyncFranchiseeQuartz.class);
	
	
	@Resource
	private FranchiseeService franchiseeService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private FranchiseeProfitService franchiseeProfitService;
	@Resource
	private FranchiseeSettleService franchiseeSettleService;
	@Resource
	private FranchiseeSettleDao franchiseeSettledao;

	public void quartzStart() throws Exception {
		// 获取定时任务触发信息
		SysParam param = sysParamService.getByParamKey("SyncFranchiseeQuartzParam");
		if (param != null && param.getParamValue() != null) {
			// 获取系统当前月上一个月的第一天和最后一天
			String firstDay = ECDateUtils.getFirstDayOfLastMonth();
			String lastDay = ECDateUtils.getLastDayOfLastMonth()+" 23:59:59";
			// 开始统计当前自然月的加盟商收益数据
			FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
			franchiseeProfit.setCreateTimeStart(ECDateUtils.parseDate(firstDay));
			franchiseeProfit.setCreateTimeEnd(ECDateUtils.parseTime(lastDay));
			List<FranchiseeVo> listFranchiseeProfit = this.franchiseeProfitService.listFranchiseeProfit(new Query(franchiseeProfit));
			if (listFranchiseeProfit != null && listFranchiseeProfit.size() > 0) {
				save(listFranchiseeProfit,firstDay,lastDay);
			}
		}

	}
	
	protected void save(List<FranchiseeVo> datas,String firstDay,String lastDay) throws ParseException{
		for (FranchiseeVo franchiseeVo : datas) {
			FranchiseeSettle franchiseeSettle = new FranchiseeSettle();
			franchiseeSettle.setFranchiseeSettleNo(franchiseeSettleService.generatePK());
			franchiseeSettle.setFranchiseeNo(franchiseeVo.getFranchiseeNo());
			Long carshareOrderCount=0L;
			Long parkshareOrderCount=0l;
			Double carshareOrderAmount=0d;
			Double parkshareOrderAmount=0d;
			Double carShareAmount=0d;
			Double parkShareAmount=0d;
			
			
			FranchiseeSettle f=new FranchiseeSettle();
			f.setCreateTimeStart(ECDateUtils.parseDate(firstDay));
			f.setCreateTimeEnd(ECDateUtils.parseTime(lastDay));
			f.setFranchiseeNo(franchiseeVo.getFranchiseeNo());
			Query qf=new Query(f);
			List<FranchiseeSettle> fs=franchiseeSettleService.getFranchiseeSettleList(qf);
			if(fs != null){
				continue;
			}
			FranchiseeProfit fp =new FranchiseeProfit();
			String firstDay2 = ECDateUtils.getFirstDayOfLastMonth();
			String lastDay2 = ECDateUtils.getLastDayOfLastMonth()+" 23:59:59";
			fp.setCreateTimeStart(ECDateUtils.parseDate(firstDay2));
			fp.setCreateTimeEnd(ECDateUtils.parseTime(lastDay2));
			fp.setFranchiseeNo(franchiseeVo.getFranchiseeNo());
			Query  q =new Query(fp);
			List<FranchiseeProfit> fps=franchiseeProfitService.getFranchiseeProfitList(q);
			if(fps != null && fps.size()>0){
				for (FranchiseeProfit franchiseeProfit : fps) {
					if(franchiseeProfit.getCarProfit() != null &&  franchiseeProfit.getCarProfit()  > 0){
						carshareOrderCount++;//车辆产生分润订单数
						carshareOrderAmount+=franchiseeProfit.getOrderAmount();//车辆产生分润订单总金额
						carShareAmount+=franchiseeProfit.getCarProfit();//车辆产生分润总金额
					}
					if(franchiseeProfit.getParkProfit() != null && franchiseeProfit.getParkProfit() >0 ){//场站产生分润订单数
						parkshareOrderCount++;//场站产生分润订单数
						parkshareOrderAmount+=franchiseeProfit.getOrderAmount();//场站产生分润订单总金额
						parkShareAmount+=franchiseeProfit.getParkProfit();//场站产生分润总金额
					}
					
				}
			}
			
			franchiseeSettle.setCarshareOrderCount(carshareOrderCount.doubleValue());
			franchiseeSettle.setParkshareOrderCount(parkshareOrderCount.doubleValue());
			franchiseeSettle.setCarshareOrderAmount(ECCalculateUtils.round(carshareOrderAmount,2));
			franchiseeSettle.setCarShareAmount(ECCalculateUtils.round(carShareAmount,2));
			franchiseeSettle.setParkshareOrderAmount(ECCalculateUtils.round(parkshareOrderAmount,2));
			franchiseeSettle.setParkShareAmount(ECCalculateUtils.round(parkShareAmount,2));
			
			franchiseeSettle.setFranchiseeName(franchiseeVo.getFranchiseeName());
			franchiseeSettle.setOrderNo(franchiseeVo.getOrderNo());
			franchiseeSettle.setOrderAmount(franchiseeVo.getOrderAmount());
			franchiseeSettle.setShareAmount(franchiseeVo.getProfitAmount());
			franchiseeSettle.setOrderCount(Double.parseDouble(String.valueOf(franchiseeVo.getOrderNumber())));
		 lastDay = ECDateUtils.getLastDayOfLastMonth();
			franchiseeSettle.setSettleDay(firstDay + "至" + lastDay);
			franchiseeSettleService.addFranchiseeSettle(franchiseeSettle,null);
		}
	}

	protected void execute(String arg0) throws Exception {

		try {
			logger.info("--------统计加盟商结算数据，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------统计加盟商结算数据定时任务完成...");
		} catch (Exception e) {
			logger.error("--------统计加盟商结算数据，错误信息：" + e.getMessage(), e);
		}
	}
}

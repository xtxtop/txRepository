package cn.com.shopec.mapi.mall.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mall.model.MallItem;
import cn.com.shopec.core.mall.model.MallItemPointsRecord;
import cn.com.shopec.core.mall.model.MallItemSort;
import cn.com.shopec.core.mall.service.MallItemPointsRecordService;
import cn.com.shopec.core.mall.service.MallItemService;
import cn.com.shopec.core.mall.service.MallItemSortService;
import cn.com.shopec.mapi.mall.vo.MallItemPointsRecordVo;
import cn.com.shopec.mapi.mall.vo.MallItemSortVo;
import cn.com.shopec.mapi.mall.vo.MallItemVo;

@Controller
@RequestMapping("/app/mall")
public class MallController {

	@Resource
	private MallItemService mallItemService;

	@Resource
	private MallItemSortService mallItemSortService;

	@Resource
	private MallItemPointsRecordService mallItemPointsRecordService;

	/**
	 * 获取所有的商品分类
	 * 
	 * @return
	 */
	@RequestMapping("/getAllMallItemSort")
	@ResponseBody
	public ResultInfo<List<MallItemSortVo>> getAllMallItemSort() {
		ResultInfo<List<MallItemSortVo>> resultInfo = new ResultInfo<>();
		Query q = new Query(1, 10000, new MallItemSort());
		List<MallItemSortVo> data = new ArrayList<>();
		List<MallItemSort> list = mallItemSortService.getMallItemSortList(q);
		for (MallItemSort mallItemSort : list) {
			MallItemSortVo vo = new MallItemSortVo();
			vo.setSortNo(mallItemSort.getSortNo());
			vo.setParentSortNo(mallItemSort.getParentSortNo());
			vo.setSortName(mallItemSort.getSortName());
			vo.setSortLevel(mallItemSort.getSortLevel());
			data.add(vo);
		}
		resultInfo.setData(data);
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

	/**
	 * 根据商品分类获取商品列表
	 * 
	 * @return
	 */
	@RequestMapping("/getAllMallItem")
	@ResponseBody
	public ResultInfo<List<MallItemVo>> getAllMallItem() {
		ResultInfo<List<MallItemVo>> resultInfo = new ResultInfo<>();
		Query q = new Query(1, 10000, new MallItem());
		List<MallItemVo> data = new ArrayList<>();
		List<MallItem> list = mallItemService.getMallItemList(q);
		for (MallItem mallItem : list) {
			MallItemVo vo = new MallItemVo();
			vo.setItemNo(mallItem.getItemNo());
			vo.setPicUrl(mallItem.getPicUrl());
			vo.setItemName(mallItem.getItemName());
			vo.setPoints(mallItem.getPoints());
			data.add(vo);
		}
		resultInfo.setData(data);
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

	/**
	 * 根据商品分类获取商品列表
	 * 
	 * @param sortNo
	 * @return
	 */
	@RequestMapping("/getMallItemListBySortNo")
	@ResponseBody
	public ResultInfo<List<MallItemVo>> getMallItemListBySortNo(String sortNo) {
		ResultInfo<List<MallItemVo>> resultInfo = new ResultInfo<>();
		MallItem item = new MallItem();
		item.setSortNo(sortNo);
		Query q = new Query(item);
		List<MallItemVo> data = new ArrayList<>();
		List<MallItem> list = mallItemService.getMallItemList(q);
		for (MallItem mallItem : list) {
			MallItemVo vo = new MallItemVo();
			vo.setItemNo(mallItem.getItemNo());
			vo.setPicUrl(mallItem.getPicUrl());
			vo.setItemName(mallItem.getItemName());
			vo.setPoints(mallItem.getPoints());
			data.add(vo);
		}
		resultInfo.setData(data);
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

	/**
	 * 根据商品编号获取商品
	 * 
	 * @param itemNo
	 * @return
	 */
	@RequestMapping("/getMallItemByItemNo")
	@ResponseBody
	public ResultInfo<MallItem> getMallItemByItemNo(String itemNo) {
		ResultInfo<MallItem> resultInfo = new ResultInfo<>();
		MallItem data = mallItemService.getMallItem(itemNo);
		if (data == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			return resultInfo;
		}
		resultInfo.setData(data);
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

	/**
	 * 商品兑换
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping("/addMallItemPointsRecord")
	@ResponseBody
	public ResultInfo<MallItemPointsRecord> addMallItemPointsRecord(MallItemPointsRecord record) {
		return mallItemPointsRecordService.addMallItemPointsRecord(record);
	}

	/**
	 * 更新商品兑换记录
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping("/updateMallItemPointsRecord")
	@ResponseBody
	public ResultInfo<MallItemPointsRecord> updateMallItemPointsRecord(MallItemPointsRecord record) {
		return mallItemPointsRecordService.updateMallItemPointsRecord(record);
	}

	/**
	 * 根据商品兑换记录编号获取商品兑换记录
	 * 
	 * @param recordNo
	 * @return
	 */
	@RequestMapping("/getRecordByRecordNo")
	@ResponseBody
	public ResultInfo<MallItemPointsRecordVo> getRecordByRecordNo(String recordNo) {
		ResultInfo<MallItemPointsRecordVo> resultInfo = new ResultInfo<>();
		MallItemPointsRecord record = mallItemPointsRecordService.getMallItemPointsRecord(recordNo);
		if (record == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			return resultInfo;
		}
		MallItemPointsRecordVo data = new MallItemPointsRecordVo();
		data.setRecordNo(record.getRecordNo());
		data.setItemName(record.getItemName());
		data.setPicUrl(record.getPicUrl());
		data.setPoints(record.getPoints());
		data.setPhone(record.getPhone());
		data.setAddress(record.getAddress());
		data.setStatus(record.getStatus());
		resultInfo.setData(data);
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

	/**
	 * 根据会员编号获取商品兑换记录列表
	 * 
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("/getRecordListByMemberNo")
	@ResponseBody
	public ResultInfo<List<MallItemPointsRecordVo>> getRecordListByMemberNo(String memberNo) {
		ResultInfo<List<MallItemPointsRecordVo>> resultInfo = new ResultInfo<>();
		MallItemPointsRecord record = new MallItemPointsRecord();
		record.setMemberNo(memberNo);
		Query q = new Query(record);
		List<MallItemPointsRecordVo> data = new ArrayList<>();
		List<MallItemPointsRecord> list = mallItemPointsRecordService.getMallItemPointsRecordList(q);
		for (MallItemPointsRecord o : list) {
			MallItemPointsRecordVo vo = new MallItemPointsRecordVo();
			vo.setRecordNo(o.getRecordNo());
			vo.setItemName(o.getItemName());
			vo.setPicUrl(o.getPicUrl());
			vo.setPoints(o.getPoints());
			vo.setPhone(o.getPhone());
			vo.setAddress(o.getAddress());
			vo.setStatus(o.getStatus());
			data.add(vo);
		}
		resultInfo.setData(data);
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

}

package cn.com.shopec.core.mlparking.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.mlparking.model.CPliesNumber;

/**
 * 楼层表 数据库处理类
 */
public interface CPliesNumberDao extends BaseDao<CPliesNumber,String> {
	//插入多层车位
	 Integer addTwoVo(List<CPliesNumber> list);
	 //删除站下面的所有车位
	 Integer deleteParkingNo(String id,Integer type);
	 //更新车位 升锁
	 int upSpaceNum(String id);
	 //更新车位 升锁
	 int upSpaceNumTwo(String id);
}

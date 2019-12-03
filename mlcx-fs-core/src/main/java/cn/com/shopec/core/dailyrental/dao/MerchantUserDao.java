package cn.com.shopec.core.dailyrental.dao;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.dailyrental.model.MerchantUser;

/**
 * MerchantUser 数据库处理类
 */
public interface MerchantUserDao extends BaseDao<MerchantUser,String> {
	
	/**
	 * 根据商家ID和会员手机查询商家用户
	 * @param merchantId
	 * @param mobilePhone
	 * @return
	 */
	MerchantUser getByMobilePhone(@Param("merchantId") String merchantId,@Param("mobilePhone") String mobilePhone,@Param("password") String password);
	
	/**
	 * 根据token查询商家用户
	 * @param token
	 * @param userNo
	 * @return
	 */
	MerchantUser getByToken(@Param("token") String token,@Param("userNo")String userNo);
	
	int updateMerchantUserByMerchantId(MerchantUser merchantUser);
}

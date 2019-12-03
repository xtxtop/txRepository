package cn.com.shopec.mapi.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import cn.com.shopec.common.utils.ECMd5Utils;

public class SignUtil {
	
	/**
	 * 对传过来的的参数属性进行排序，将得到的字符串首、尾拼接Secret得到拼接字符，最后进行MD5加密
	 * @param params
	 * @return
	 */
	public static String signParam(HttpServletRequest request,String secret) {
		//请求参数map
		Map<String, String[]> paramMap = request.getParameterMap();
		//过滤掉时间戳和签名
		Map<String, Object> map = new HashMap<String, Object>();
		//存放参数属性的数组，供排序
		String[] paramArr = new String[paramMap.size()-2];
		int i = 0;
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			String keyName = entry.getKey();
			String[] mvs = entry.getValue();
			if(!keyName.equals("sign")&&!keyName.equals("timestamp")){
				paramArr[i] = keyName;
				if (mvs.length == 1) {
					if(mvs[0].indexOf("[")>-1){
						String BizObjIds=StringUtils.join(mvs,",");
						String BizObjId=BizObjIds.substring(BizObjIds.indexOf('[')+1, BizObjIds.indexOf(']'));
						BizObjId=BizObjId.replace("\"","");
						String[] biz=BizObjId.split(",");
						String value = "";
						for(String temp:biz){
							value+=temp;
						}
						map.put(keyName, value);
					}else{
						map.put(keyName, mvs[0]);
					}
				}
				i++;
			}
		}
		//生成签名
		String mysign = "";
		strSort(paramArr);
		for(String key:paramArr){
			mysign+=key+map.get(key);
		}
		mysign+=secret;
		return  ECMd5Utils.hash(mysign);
		
	}
	
	 /**
     * 
     * 对字符串进行由小到大排序
     * @param str    String[] 需要排序的字符串数组
     */
    private static void strSort(String[] str){
        for (int i = 0; i < str.length; i++) {
            for (int j = i+1; j < str.length; j++) {
                if(str[i].compareTo(str[j])>0){    //对象排序用camparTo方法
                    swap(str,i,j);
                }
            }
        }
        
    }
    /**
     * 交换两个元素的位置的方法
     * @param strSort    需要交换元素的数组    
     * @param i    索引i
     * @param j 索引j
     */
    private static void swap(String[] strSort, int i, int j) {
        String t = strSort[i];
        strSort[i] = strSort[j];
        strSort[j] = t;
    }
    /**
     * 打印字符串数组
     * @param str
     */
    private void printArr(String[] str) {
        for (int i = 0; i < str.length; i++) {
            System.out.print(str[i]+"\t");
        }
        System.out.println();
    }
}

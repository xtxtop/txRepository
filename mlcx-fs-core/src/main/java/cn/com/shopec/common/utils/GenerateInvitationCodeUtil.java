package cn.com.shopec.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GenerateInvitationCodeUtil {

	/** 自定义进制(0,o没有加入,0，o容易混淆) */
    private static final char[] r=new char[]{ '1', '2', '3', '4', '5',  
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',  
            'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',  
            'W', 'X', 'Y', 'Z' };

    /** (不能与自定义进制有重复) */
    private static final char b='o';

    /** 进制长度 */
    private static final int binLen=r.length;

    /** 序列最小长度 */
    private static final int s=8;

    /**
     * 根据ID生成八位随机码
     * @param id ID
     * @return 随机码
     */
    public static String toRandomCode(long id) {
        char[] buf=new char[32];
        int charPos=32;

        while((id / binLen) > 0) {
            int ind=(int)(id % binLen);
            buf[--charPos]=r[ind];
            id /= binLen;
        }
        buf[--charPos]=r[(int)(id % binLen)];
        String str=new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if(str.length() < s) {
            StringBuilder sb=new StringBuilder();
            sb.append(b);
            Random rnd=new Random();
            for(int i=1; i < s - str.length(); i++) {
            sb.append(r[rnd.nextInt(binLen)]);
            }
            str+=sb.toString();
        }
        return str.toUpperCase();
    }
    public static long codeToId(String code) {
        char chs[]=code.toCharArray();
        long res=0L;
        for(int i=0; i < chs.length; i++) {
            int ind=0;
            for(int j=0; j < binLen; j++) {
                if(chs[i] == r[j]) {
                    ind=j;
                    break;
                }
            }
            if(chs[i] == b) {
                break;
            }
            if(i > 0) {
                res=res * binLen + ind;
            } else {
                res=ind;
            }
        }
        return res;
    }

	public static void  main(String[] args) {
		List<String> list = new ArrayList<>();
		Set<String> set = new HashSet<>();
		for(int i=10000000;i<10009999;i++){
//			list.add(UUID.randomUUID().toString());
//			set.add(UUID.randomUUID().toString());
			long l = System.nanoTime();
			list.add(toRandomCode(l));
			set.add(toRandomCode(l));
			System.err.println(toRandomCode(l));
		}
		System.err.println(list.size());
		System.err.println(set.size());
//		System.err.println(r.length);
//		System.out.println(toRandomCode(99999999l));
//		System.err.println(codeToId("F8EZJOZ3"));
	}
}

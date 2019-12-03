package cn.com.shopec.common.utils;

import java.util.Date;

public class Test4 {
    
    public static void main(String[] args) {
        Uint32 min = Uint32.MIN_VALUE;
        Uint32 max = Uint32.MAX_VALUE;
        String timeStamp=ECDateUtils.formatStringTimeWX(new Date());
        Long time=System.currentTimeMillis()/1000; 
        Uint32 a = new Uint32(time);
        Uint32 b = new Uint32(0);
        Uint32 c = new Uint32(1);
        
        System.out.println(min.getValue());
        System.out.println(max.getValue());
        System.out.println(a.getValue());
        System.out.println(b.getValue());
        System.out.println(c.getValue());

        test(max);
        
        System.out.println(a.equals(max));
    }
    
    public static void test(Uint32 num) {        
        System.out.println(num == null ? "null" : num.getValue());
    }
}



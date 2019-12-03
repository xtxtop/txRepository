package cn.com.shopec.mapi.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.shopec.core.system.model.SysPermission;

//懒汉式单例类.在第一次调用的时候实例化自己  
public class PermissionListSingleton {
	
	private List<SysPermission> perList;
	private Map<String,String> perUrlMap;
	private static Map<String,PermissionListSingleton> map = new HashMap<String,PermissionListSingleton>();  
    static{  
    	PermissionListSingleton single = new PermissionListSingleton();  
        map.put(single.getClass().getName(), single);  
    }  
    //保护的默认构造子  
    protected PermissionListSingleton(){}  
    //静态工厂方法,返还此类惟一的实例  
    public static PermissionListSingleton getInstance(String name) {  
        if(name == null) {  
            name = PermissionListSingleton.class.getName();  
            System.out.println("name == null"+"--->name="+name);  
        }  
        if(map.get(name) == null) {  
            try {  
                map.put(name, (PermissionListSingleton) Class.forName(PermissionListSingleton.class.getName()).newInstance());  
            } catch (InstantiationException e) {  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                e.printStackTrace();  
            } catch (ClassNotFoundException e) {  
                e.printStackTrace();  
            }  
        }  
        return map.get(name);  
    }  

	public List<SysPermission> getPerList() {
		return perList;
	}

	public void setPerList(List<SysPermission> perList) {
		this.perList = perList;
	}
	public Map<String, String> getPerUrlMap() {
		return perUrlMap;
	}
	public void setPerUrlMap(Map<String, String> perUrlMap) {
		this.perUrlMap = perUrlMap;
	}  
	
	public void setPerUrlMap(List<SysPermission> perList) {
		Map<String,String> newMap = new HashMap<String,String>();
		if(perList != null && perList.size() > 0){
			for(SysPermission per : perList){
				newMap.put(per.getPermName(), per.getPermResource());
			}
		}		
		this.perUrlMap = newMap;
	} 
    
}

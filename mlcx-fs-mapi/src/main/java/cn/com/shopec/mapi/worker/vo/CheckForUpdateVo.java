package cn.com.shopec.mapi.worker.vo;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 安卓app更新实体类
 */
public class CheckForUpdateVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	 private String name;//App名称：行知出行
	 
     private String version;//版本：v1.0.1
     
     private String md5;//安卓MD5
     
     private String changelog;//更新日志：1.优化App
     
     private String update_url;//更新链接:http://abc.com/update.apk 
     
     private int build;//biild版本：2

 	 private boolean force;//是否强制更新：true
 	
	//private double iOSversion;//ios版本号
	private String iOSversion;//ios版本号
	
	public String getiOSversion() {
		return iOSversion;
	}

	public void setiOSversion(String iOSversion) {
		this.iOSversion = iOSversion;
	}

	private double adFileSize;//文件大小
 	
     public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChangelog() {
		return changelog;
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	public String getUpdate_url() {
		return update_url;
	}

	public void setUpdate_url(String update_url) {
		this.update_url = update_url;
	}

	public int getBuild() {
		return build;
	}

	public void setBuild(int build) {
		this.build = build;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public double getAdFileSize() {
		return adFileSize;
	}

	public void setAdFileSize(double adFileSize) {
		this.adFileSize = adFileSize;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}



	
	
	
	
	
}

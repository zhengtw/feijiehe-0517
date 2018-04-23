package com.jfhealthcare.common.entity;

import java.util.HashMap;
import java.util.Map;

public class CommonStaticValue {
	//按钮 常量
	private static Map<String, String> btnsMap = new HashMap<String, String>();
	//报告流 常量
	private static Map<String, String> flowMap = new HashMap<String, String>();
	
	static {
		//报告流 常量
		//ai部分
		flowMap.put("5002", "ai未参与->待报告");
		flowMap.put("4999", "ai转分片->待报告");
		flowMap.put("5000", "ai转报告->待报告");
		flowMap.put("5001", "ai转审核->待一审");
		//报告部分
		flowMap.put("3552", "待报告->报告中");//ai后直接分配给医师 
		flowMap.put("3434_3552", "待报告->报告中");
		flowMap.put("3552_3445", "报告中->待一审");
		flowMap.put("3552_3552", "报告中->暂存（退出、再打开）");
		flowMap.put("3552_3555", "报告中->拒绝");
		flowMap.put("3552_3434", "报告中->待报告");
		flowMap.put("3552_3558", "报告中->转会报");
		flowMap.put("3552_3556", "报告中->审核下发");
		flowMap.put("3558_3552", "待会报->报告中");
		//审核部分
		flowMap.put("3553", "待一审->审核中");//ai后直接分配给医师 
		flowMap.put("3445_3553", "待一审->审核中");
		flowMap.put("3449_3553", "待二审->审核中");
		flowMap.put("3550_3553", "待三审->审核中");
		flowMap.put("3559_3553", "待会审->审核中");
		flowMap.put("3553_3553", "审核中->暂存（退出、再打开）");
		flowMap.put("3553_3555", "审核中->拒绝");
		flowMap.put("3553_3559", "审核中->转会审");
		flowMap.put("3553_3445", "审核中->待一审");
		flowMap.put("3553_3449", "审核中->待二审");
		flowMap.put("3553_3550", "审核中->待三审");
		flowMap.put("3553_3556", "审核中->审核下发");
		flowMap.put("3553_3552", "审核中->退回重写");
		
		//按钮 常量
		//所有--状态
		btnsMap.put("00000000", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,wctj,shxf,zf,dhcx");
		// 报告--状态
		btnsMap.put("37063434", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,wctj");
		// 一审--状态
		btnsMap.put("37053434", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,wctj,shxf");
		btnsMap.put("37053445", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,wctj,shxf,thcx");
		// 二审--状态
		btnsMap.put("37043434", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,wctj,shxf");
		btnsMap.put("37043445", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,wctj,shxf,thcx");
		btnsMap.put("37043449", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,wctj,shxf,thcx");
		// 三审--状态
		btnsMap.put("37033434", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,shxf");
		btnsMap.put("37033445", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,shxf,thcx");
		btnsMap.put("37033449", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,shxf,thcx");
		btnsMap.put("37033550", "isOpen,isWork,tc,jj,fq,zc,yl,zhz,shxf,thcx");
		btnsMap.put("37033556", "isOpen,tc,yl,zf");
		btnsMap.put("37033557", "isOpen,tc,yl,zf");
		btnsMap.put("37033560", "isOpen,tc,yl");
	}
	
	public static String getFlowByKey(String key){
		return flowMap.get(key);
	}

	public static Map<String,String> getFlowMap(){
		return flowMap;
	}
	
	public static String getBtnsByKey(String key){
		return btnsMap.get(key);
	}

	public static Map<String,String> getBtnsMap(){
		return btnsMap;
	}
}

package com.jfhealthcare.common.enums;

/**
 * redis key值枚举
 */
public enum RedisEnum {
	
	LOGINCODE("logincode"),//loginuser 的key值      例如： logincode：cjbg
	ALLMENU("allmenu"),
    REPGROUP("repgroup"),//缓存分组信息
    UPLOADDCM("uploaddcm"),
    PUBLICDR("public_dr"),//缓存公共模板
    REPGROUP_NUM("repgroup_num"),//缓存当前组列表下表   与分布式锁结合使用
    DISTRIBUTELOCK_REPGROUP("distributelock_repgroup"),//缓存分布式锁 分组
    DISTRIBUTELOCK_APPLY("distributelock_apply");//缓存分布式锁 申请
	
    private String value;
    
	private RedisEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}

package com.jfhealthcare.modules.basics;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@MappedSuperclass
public class BasicPageEntity {

	@Transient
	@ApiModelProperty(value = "当前页码")
	private Integer pageNum = 1;

	@Transient
	@ApiModelProperty(value = "当前页容量")
	private Integer pageSize = 30;

}

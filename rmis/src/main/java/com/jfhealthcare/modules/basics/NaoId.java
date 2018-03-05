package com.jfhealthcare.modules.basics;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


import lombok.Data;

@Data
@MappedSuperclass
public abstract class NaoId implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 分组编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
	
}

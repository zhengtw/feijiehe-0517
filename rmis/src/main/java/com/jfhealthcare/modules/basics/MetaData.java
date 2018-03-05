package com.jfhealthcare.modules.basics;
import java.util.ArrayList;

import lombok.Data;

@Data
public class MetaData {
	private int count ;
	private String key ;
	private String user ;
	private String original_file;
	private ArrayList<Pieces> pieces;
}

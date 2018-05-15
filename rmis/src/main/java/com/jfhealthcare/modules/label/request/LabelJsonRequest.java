package com.jfhealthcare.modules.label.request;


import java.util.ArrayList;
import java.util.HashMap;

import lombok.Data;

@Data
public class LabelJsonRequest {
	private ArrayList<HashMap<String,Object>> jsonValue;
	private String labelAccnum;
}

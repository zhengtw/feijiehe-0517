package com.jfhealthcare.modules.label.response;

import java.util.HashMap;
import java.util.List;

import com.jfhealthcare.modules.label.entity.LabelInfo;
import com.jfhealthcare.modules.label.entity.LabelJson;

import lombok.Data;

@Data
public class LabelInfoWebViewResponse {
	private List<LabelInfo> labelInfo;

	private HashMap<String,List<LabelJson>> labelJson;
}

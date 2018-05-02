package com.jfhealthcare.modules.label.request;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;

import lombok.Data;

@Data
public class LabelDiseaseRequest {
	@NotBlank(message = "uid不能为空!", groups = { Insert.class })
	private String uid;
	@NotBlank(message = "diseaseCode不能为空!", groups = { Insert.class })
	private String diseaseCode;
	@NotBlank(message = "disease不能为空!", groups = { Insert.class })
	private String disease;
	@NotBlank(message = "ID不能为空!", groups = { Edit.class })
	private String id;
}

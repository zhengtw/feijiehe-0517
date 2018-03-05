package com.jfhealthcare.common.enums;

/**
 * DCMkey值对
 */
public enum DCMEnum {
	
	CharSet("00080005"),
	SOPClassUID("00080016"),
	SOPInstanceUID("00080018"),
	StudyDate("00080020"),
	SeriesDate("00080021"),
	StudyTime("00080030"),
	SeriesTime("00080031"),
	AccessionNumber("00080050"),
	Modality("00080060"),
	ConversionType("00080064"),
	Manufacturer("00080070"),
	InstitutionName("00080080"),
	StationName("00081010"),
	StudyDescription("00081030"),
	PerformingPhysicianName("00081050"),
	PatientName("00100010"),
	PatientID("00100020"),
	PatientBirthDate("00100030"),
	PatientSex("00100040"),
	PatientAge("00101010"),
	PatientSize("00101020"),
	PatientWeight("00101030"),
	BodyPartExamined("00180015"),
	StudyInstanceUID("0020000D"),
	SeriesInstanceUID("0020000E"),
	StudyID("00200010"),
	SeriesNumber("00200011"),
	InstanceNumber("00200013"),
	NumberofFrames("00280008"),
//	InstanceReceiveDateTime("77771040"),
	Rows("00280010"),
	Columns("00280011"),
//	StorageTransferUID("77771052"),
	RetrieveAETitle("00080054"),
//	StorageObjectSize("77771053"),
//	StorageTransferSyntaxUID("77771052"),
	BitsAllocated("00280100"),
	PrivateCreator("00190010"),
	PixelData("7FE00010"),//http://192.168.10.97:8080/dcm4chee-arc/aets/piclarc/rs/studies/1.2.826.0.1.3680043.2.461.6908835.3207647828/series/1.2.826.0.1.3680043.2.461.6908831.2750626085/instances/1.2.826.0.1.3680043.2.461.6908832.1617364220
	InstanceCreationDate("00080012"),
	InstanceCreationTime("00080013")
	;
	
    private String dcmKey;
    
    
	private DCMEnum(String dcmKey) {
		this.dcmKey = dcmKey;
		
	}
	public String getDcmKey() {
		return dcmKey;
	}

	public void setDcmKey(String dcmKey) {
		this.dcmKey = dcmKey;
	}
}

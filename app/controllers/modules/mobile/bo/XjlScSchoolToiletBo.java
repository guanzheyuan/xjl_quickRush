package controllers.modules.mobile.bo;

import models.modules.mobile.XjlScSchoolToilet;
import utils.DateUtil;
import utils.SeqUtil;

public class XjlScSchoolToiletBo {

	
	public static XjlScSchoolToilet save(XjlScSchoolToilet xjlScSchoolToilet){
		xjlScSchoolToilet.id = SeqUtil.maxValue("xjl_sc_school_toilet","id");
		xjlScSchoolToilet.status ="0AA";
		xjlScSchoolToilet.createTime=DateUtil.getNowDate();
		
		xjlScSchoolToilet = xjlScSchoolToilet.save();
		return xjlScSchoolToilet;
	}
}

package controllers.modules.mobile.bo;

import models.modules.mobile.XjlScSchool;
import utils.DateUtil;
import utils.SeqUtil;

public class XjlScSchoolBo {

	
	public static XjlScSchool save(XjlScSchool xjlScSchool){
		xjlScSchool.schoolId = SeqUtil.maxValue("xjl_sc_school","SCHOOL_ID");
		xjlScSchool.status="0AA";
		xjlScSchool.createTime = DateUtil.getNowDate();
		
		xjlScSchool = xjlScSchool.save();
		
		return xjlScSchool;
	}
}

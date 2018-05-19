package controllers.modules.mobile.bo;

import models.modules.mobile.XjlScSchoolUser;
import utils.DateUtil;
import utils.SeqUtil;

public class XjlScSchoolUserBo {

	
	public static XjlScSchoolUser save(XjlScSchoolUser xjlScSchoolUser){
		xjlScSchoolUser.id=SeqUtil.maxValue("xjl_sc_school_user","id");
		xjlScSchoolUser.createTime = DateUtil.getNowDate();
		xjlScSchoolUser.status = "0UU";
		
		xjlScSchoolUser = xjlScSchoolUser.save();
		return xjlScSchoolUser;
	}
}

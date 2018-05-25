package controllers.modules.mobile.bo;

import models.modules.mobile.XjlScDeviceManage;
import utils.DateUtil;
import utils.SeqUtil;

public class XjlScDeviceManageBo {

	
	public static XjlScDeviceManage save(XjlScDeviceManage xjlScDeviceManage){
		xjlScDeviceManage.status ="0AA";
		xjlScDeviceManage.createTime = DateUtil.getNowDate();
		xjlScDeviceManage.id = SeqUtil.maxValue("xjl_sc_device_manage","id");
		xjlScDeviceManage = xjlScDeviceManage.save();
		return xjlScDeviceManage;
	}
}

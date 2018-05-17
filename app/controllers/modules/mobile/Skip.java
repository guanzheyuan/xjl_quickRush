package controllers.modules.mobile;
import java.util.HashMap;
import java.util.Map;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.filter.MobileFilter;
import models.modules.mobile.WxServer;
import models.modules.mobile.WxUser;
import play.Logger;
import play.i18n.Messages;
import play.mvc.Http;
import utils.StringUtil;
import utils.WxPushMsg;
public class Skip extends MobileFilter {
	
	/**
	 * 跳转闪冲列表
	 */
	public static void toRush(){
		render("modules/xjldw/rush/sc_rush.html");
	}
	/**
	 * 跳转闪冲图标统计
	 */
	public static void toRushChat(){
		renderArgs.put("status",params.get("status"));
		render("modules/xjldw/rush/sc_chat.html");
	}
	 /**
	  * 冲水细则
	  */
	public static void toRushWater(){
		renderArgs.put("time", params.get("time"));
		render("modules/xjldw/rush/sc_waterList.html");
	}
	/**
	 * 跳转闪冲日志
	 */
	public static void toScLog(){
		render("modules/xjldw/rush/sc_log.html");
	}
	
	/**
	 * 跳转闪冲配置
	 */
	public static void toScConfig(){
		render("modules/xjldw/rush/sc_config.html");
	}
	
	/**
	 * 跳转闪冲绑定学校
	 */
	public static void toBindSchool(){
		render("modules/xjldw/rush/sc_bindSchool.html");
	}
}

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
	
	public static void toRush(){
		render("modules/xjldw/rush/sc_rush.html");
	}
	public static void toRushChat(){
		renderArgs.put("status",params.get("status"));
		render("modules/xjldw/rush/sc_chat.html");
	}
	public static void toRushChat1(){
		render("modules/xjldw/rush/sc_chat1.html");
	}
	public static void toRushWater(){
		renderArgs.put("time", params.get("time"));
		render("modules/xjldw/rush/sc_waterList.html");
	}
	public static void toScLog(){
		render("modules/xjldw/rush/sc_log.html");
	}
	public static void toScMaintain(){
		render("modules/xjldw/rush/sc_maintain.html");
	}
	public static void toScConfig(){
		render("modules/xjldw/rush/sc_config.html");
	}
}

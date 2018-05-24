package controllers.modules.mobile;
import java.util.HashMap;
import java.util.Map;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.filter.MobileFilter;
import models.modules.mobile.WxServer;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlScToiletInfo;
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
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
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
		WxUser wxUser = getWXUser();
		if(wxUser.isAdmin){
			render("modules/xjldw/rush/sc_config.html");
		}else{
			render("modules/xjldw/rush/error/sc_error.html");
		}
		
	}
	
	/**
	 * 跳转闪冲绑定学校
	 */
	public static void toBindSchool(){
		render("modules/xjldw/rush/sc_bindSchool.html");
	}
	
	/**
	 * 跳转至关联上设备页面
	 */
	public static void toBindExplain(){
		WxUser wxUser = getWXUser();
		XjlScToiletInfo xjlScToiletInfo = XjlScToiletInfo.queryScToiletById(Long.valueOf(params.get("id")));
		renderArgs.put("wxUser", wxUser);
		renderArgs.put("toilet",params.get("params"));
		renderArgs.put("id",params.get("id"));
		renderArgs.put("toiletInfo", xjlScToiletInfo);
		render("modules/xjldw/rush/bind/sc_bindExplain.html");
	}
	
	/**
	 * 关联控制器
	 */
	public static void toBindControl(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		renderArgs.put("toilet",params.get("params"));
		renderArgs.put("id",params.get("id"));
		render("modules/xjldw/rush/bind/sc_bindControl.html");
	}
	/**
	 * 关联传感器
	 */
	public static void toBindSensor(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		renderArgs.put("toilet",params.get("params"));
		renderArgs.put("id",params.get("id"));
		render("modules/xjldw/rush/bind/sc_bindSensor.html");
	}
	
	/**
	 * 关联电磁阀
	 */
	public static void toBindRadiotube(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		renderArgs.put("toilet",params.get("params"));
		renderArgs.put("id",params.get("id"));
		render("modules/xjldw/rush/bind/sc_bindRadiotube.html");
	}
	
	/**
	 * 关联液位仪
	 */
	public static void toBindLiquid(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		renderArgs.put("toilet",params.get("params"));
		renderArgs.put("id",params.get("id"));
		render("modules/xjldw/rush/bind/sc_bindLiquid.html");
	}
	
	/**
	 * 关联wifi
	 */
	public static void toWifi(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		renderArgs.put("toilet",params.get("params"));
		renderArgs.put("id",params.get("id"));
		render("modules/xjldw/rush/bind/sc_bindWifi.html");
	}
	
	/**
	 * 驱蚊除味
	 */
	public static void toMosq(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		renderArgs.put("toilet",params.get("params"));
		renderArgs.put("id",params.get("id"));
		render("modules/xjldw/rush/bind/sc_bindMosq.html");
	}
	
	/**
	 * 学校关注人
	 */
	public static void toAttentionSchool(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		render("modules/xjldw/rush/attention/sc_attentionSchool.html");
	}
	
	/**
	 * 查看详细信息
	 */
	public static void toAttentionUserInfo(){
		renderArgs.put("wxOpenId",params.get("wxOpenId"));
		render("modules/xjldw/rush/attention/sc_userInfo.html");
	}
	
	/**
	 * 待审核页面
	 */
	public static void toCheckPending(){
		render("modules/xjldw/rush/check/sc_checkUser.html");
	}
	/**
	 * 我的
	 */
	public static void toMine(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		render("modules/xjldw/rush/mine/sc_mine.html");
	}
	
	/**
	 * 异常提示
	 */
	public static void toError(){
		WxUser wxUser = getWXUser();
		renderArgs.put("wxUser", wxUser);
		render("modules/xjldw/rush/error/sc_error.html");
	}
	
}

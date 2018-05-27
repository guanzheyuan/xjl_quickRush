package controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import play.Logger;
import play.cache.Cache;
import play.i18n.Messages;
import controllers.comm.BOResult;
import controllers.comm.BaseController;
import controllers.comm.SessionInfo;
import controllers.modules.mobile.bo.XjlScSchoolUserBo;
import controllers.modules.mobile.filter.MobileFilter;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlScSchool;
import models.modules.mobile.XjlScSchoolUser;
import utils.CommonValidateUtil;
import utils.SecurityUtil;
import utils.StringUtil;
import utils.SysParamUtil;
import controllers.modules.mobile.filter.MobileFilter;

public class LoginService extends BaseController {

	public static void index() {
		// pc登录入口
		render("modules/xjldw/pc/scan_upload.html");
	}

	public static void mIndex() {
		  render("modules/xjldw/mobile/main.html");
	}
	public static void addStudent() {
		  render("modules/xjldw/mobile/my/student_bind.html");
	}
	
	public static void login() {
		
	}
	public static void logout() {
		Cache.delete(MobileFilter.getSessionKey());
		index();
	}

	public static void mlogout() {
		Cache.delete(MobileFilter.getSessionKey());
		mIndex();
	}

	//忘记密码
	public static void forgetPwd(){
		String MOBILE_SYSTEM_NAME = SysParamUtil.getGlobalParamByMask("MOBILE_SYSTEM_NAME");
        renderArgs.put("MOBILE_SYSTEM_NAME", MOBILE_SYSTEM_NAME);
		String TECHNICAL_SUPPORT = SysParamUtil.getGlobalParamByMask("TECHNICAL_SUPPORT");
        renderArgs.put("TECHNICAL_SUPPORT", TECHNICAL_SUPPORT);
		render("modules/zzb/mobile/forgetPwd.html");
	}
	
	
	//家长根据微信号绑定学生
 	public static void bindStudent() {
 	}

 	/**
	 * 跳转闪冲绑定学校
	 */
	public static void toBindSchool(){
		render("modules/xjldw/rush/sc_bindSchool.html");
	}
	
	
	
	/**
	 * 查询所有学校
	 */
	public static void querySchool(){
		Map<String,String> condition = new HashMap<>();
		List<XjlScSchool> data = XjlScSchool.querySchoolList(condition, 1, 99999);
		Logger.info("===================]");
		ok(data);
	}
	
	/**
	 * 绑定学校
	 */
	public static void bindSchool(){
		SessionInfo sessionInfo=MobileFilter.getSessionInfo();
 		WxUser wxUser =sessionInfo.getWxUser();
 		String schoolId = params.get("schoolId");
 		String userName = params.get("userName");
 		String telephone = params.get("telephone");
 		XjlScSchoolUser xjlScSchoolUser = new XjlScSchoolUser();
 		xjlScSchoolUser.schoolId = Long.valueOf(schoolId);
 		xjlScSchoolUser.wxOpenId = wxUser.wxOpenId;
 		xjlScSchoolUser.username = userName;
 		xjlScSchoolUser.telephone = telephone;
 		xjlScSchoolUser = XjlScSchoolUserBo.save(xjlScSchoolUser);
 		ok(xjlScSchoolUser);
	}
	
	/**
	 * 验证是否绑定过
	 */
	public static void querySchoolUserByWxOpenId(){
		SessionInfo sessionInfo=MobileFilter.getSessionInfo();
 		WxUser wxUser =sessionInfo.getWxUser();
 		Map<String,String> condition = new HashMap<>();
 		condition.put("wxOpenId",wxUser.wxOpenId);
 		XjlScSchoolUser xjlSchoolUser = XjlScSchoolUser.queryFindByWxOpenId(condition, 1,10);
 		ok(StringUtil.isNotEmpty(xjlSchoolUser));
	}
	
	/**
	 * 验证是否绑定过
	 */
	public static void querySchoolCheckUserByWxOpenId(){
		SessionInfo sessionInfo=MobileFilter.getSessionInfo();
 		WxUser wxUser =sessionInfo.getWxUser();
 		Map<String,String> condition = new HashMap<>();
 		condition.put("wxOpenId",wxUser.wxOpenId);
 		XjlScSchoolUser xjlSchoolUser = XjlScSchoolUser.queryFindCheckByWxOpenId(condition, 1,10);
 		ok(StringUtil.isNotEmpty(xjlSchoolUser));
	}
}

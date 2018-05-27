package controllers.modules.mobile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.common.io.Files;
import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.jdbc.log.Log;

import controllers.comm.SessionInfo;
import controllers.modules.mobile.bo.WxUserBo;
import controllers.modules.mobile.bo.XjlScConfigBo;
import controllers.modules.mobile.bo.XjlScDeviceManageBo;
import controllers.modules.mobile.bo.XjlScLogBo;
import controllers.modules.mobile.bo.XjlScSchoolToiletBo;
import controllers.modules.mobile.bo.XjlScToiletInfoBo;
import controllers.modules.mobile.filter.MobileFilter;
import models.modules.mobile.WxServer;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlScConfig;
import models.modules.mobile.XjlScDeviceManage;
import models.modules.mobile.XjlScLog;
import models.modules.mobile.XjlScSchool;
import models.modules.mobile.XjlScSchoolToilet;
import models.modules.mobile.XjlScSchoolUser;
import models.modules.mobile.XjlScToiletInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.Logger;
import play.Play;
import play.cache.Cache;
import utils.CreateHtml;
import utils.CreateQRCode;
import utils.DateUtil;
import utils.FileUploadPathUtil;
import utils.FileUtil;
import utils.HttpClientUtil;
import utils.MsgPush;
import utils.Preview;
import utils.QRCode;
import utils.StringUtil;
import utils.WechatTicket;
import utils.WxPushMsg;

public class Execute  extends MobileFilter {
	
	/**
	 * 记录日志
	 */
	public static void saveScLog(){
		 String errorCode = params.get("errorCode");
		 String errorDesc = params.get("errorDesc");
		 String methodName = params.get("methodName");
		 String exceptionType = params.get("exceptionType");
		 XjlScLog xjlScLog = new XjlScLog();
		 xjlScLog.errorCode = errorCode;
		 xjlScLog.errorDesc = errorDesc;
		 xjlScLog.methodName = methodName;
		 xjlScLog.exceptionType =exceptionType;
		 XjlScLogBo.save(xjlScLog);
	}
	
	/**
	 * 查询日志
	 */
	public static void queryScLog(){
		Map<String,String> condition = new HashMap<>();
		Map map = XjlScLog.query(condition,1,1000000);
		ok(map);
	}
	
	/**
	 * 配置时间段
	 */
	public static void timeQuantumConfig(){
		String startTime = params.get("startTime");
		String endTime = params.get("endTime");
		String interval = params.get("interval");
		String leaveRush = params.get("leaveRush");
		String configStatus = params.get("configStatus");
		XjlScConfig xjlScConfig = new XjlScConfig();
		xjlScConfig.timeQuantum = startTime.replace("：",":").replace(" ", "")+" — "+endTime.replace("：",":").replace(" ", "");
		xjlScConfig.interval = interval;
		xjlScConfig.leaveRush = leaveRush;
		xjlScConfig.configStatus = configStatus;
		XjlScConfigBo.save(xjlScConfig);
	}
	
	/**
	 * 配置时间段
	 */
	public static void modifyInterval(){
		String timeQuanTum = params.get("timeQuantum");
		String interval = params.get("interval");
		XjlScConfig.modifyInterval(timeQuanTum, interval);
	}
	
	/**
	 * 查询时间配置
	 */
	public static void queryScConfig(){
		Map<String,String> condition = new HashMap<>();
		Map map = XjlScConfig.query(condition, 1, 1000000);
		ok(map);
	}
	
	/**
	 * 查询未配置时间间隔
	 */
	public static void queryScConfigNotinterval(){
		Map<String,String> condition = new HashMap<>();
		Map map = XjlScConfig.queryNotTime(condition, 1, 1000000);
		ok(map);
	}
	
	/**
	 * 查询驱蚊配置
	 */
	public static void queryScMosq(){
		Map<String,String> condition = new HashMap<>();
		Map map = XjlScConfig.queryMosq(condition, 1, 1000000);
		ok(map);
	}
	
	/**
	 * 查询已配置间隔时间
	 */
	public static void queryInterval(){
		Map<String,String> condition = new HashMap<>();
		Map map =XjlScConfig.queryInterval(condition, 1, 1000000);
		ok(map);
	}
	
	/**
	 * 通过id查询单条信息
	 */
	public static void queryConfigById(){
		String id = params.get("id");
		XjlScConfig xjlconfig = XjlScConfig.queryScConfigById(Long.valueOf(id));
		ok(xjlconfig);
	}
	/**
	 * 根据id删除
	 */
	public static void modifyStatus(){
		String id = params.get("id");
		XjlScConfig.modifyStatus(Long.valueOf(id));
	}
	
	/**
	 * 根据id清空配置
	 */
	public static void modifyItervalIsNull(){
		String id = params.get("id");
		XjlScConfig.modifyIntervalIsNull(Long.valueOf(id));
	}
	
	/**
	 * 根据ID编辑时间段
	 */
	public static void modifyTimeQuanTunById(){
		String id = params.get("id");
		String timeQuanTum = params.get("timeQuanTum");
		String startTime = timeQuanTum.split("-")[0];
		String end = timeQuanTum.split("-")[1];
		String new_timeQuanTum = startTime.replace("：",":").replace(" ","")+" — "+end.replace("：",":").replace(" ", "");
		XjlScConfig.modifyTimeQuanTumById(new_timeQuanTum,id);
	}
	
	/**
	 * 根据ID编辑间隔时间
	 */
	public static void modifyItervalById(){
		String id = params.get("id");
		String iterval = params.get("iterval");
		String timeQuanTum = params.get("timeQuanTum");
		String startTime = timeQuanTum.split("-")[0];
		String end = timeQuanTum.split("-")[1];
		String new_timeQuanTum = startTime.replace("：",":").replace(" ","")+" — "+end.replace("：",":").replace(" ", "");
		XjlScConfig.modifyIntervalById(new_timeQuanTum,iterval, id);
	}
	
	/**
	 * 配置离开冲水
	 */
	public static void modifyLeaveRush(){
		String leaveRush = params.get("leaveRush");
		XjlScConfig.modifyLeaveRush(leaveRush);
	}
	
	/**
	 * 查询卫生间
	 */
	public static void querytoilet(){
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		WxUser wxUser = getWXUser();
		Map<String,String> condition = new HashMap<>();
		condition.put("schoolId",String.valueOf(wxUser.xjlScSChool.schoolId));
		Map ret = XjlScToiletInfo.query(condition, pageIndex, pageSize);
		ok(ret);
	}
	/**
	 * 新增
	 */
	public static void saveToilet(){
		Logger.info("============================================开始新增");
		String toiletName = params.get("toiletName");
		XjlScToiletInfo xjlScToiletInfo = new XjlScToiletInfo();
		xjlScToiletInfo.toiletName = toiletName;
		xjlScToiletInfo.toiletCode="AAB";
		WxUser wxUser = getWXUser();
		xjlScToiletInfo = XjlScToiletInfoBo.save(xjlScToiletInfo);
		XjlScSchoolToilet xjlScSchoolToilet = new XjlScSchoolToilet();
		xjlScSchoolToilet.toiletId = xjlScToiletInfo.id;
		xjlScSchoolToilet.schoolId = wxUser.schoolId;
		xjlScSchoolToilet = XjlScSchoolToiletBo.save(xjlScSchoolToilet);
	}
	
	/**
	 * 允许运行
	 */
	public static void modifyRun(){
		String id = params.get("id");
		String status = params.get("status");
		XjlScToiletInfo.modifyRun(status, id);
	}
	
	
	/**
	 * 删除卫生间
	 */
	public static void delToilet(){
		String id = params.get("id");
		XjlScToiletInfo.delete(id);
		XjlScSchoolToilet.delete(id);
	}
	
	/**
	 * 根据主键查询
	 */
	public static void queryToiletById(){
		String id = params.get("id");
		XjlScToiletInfo xjlScToiletInfo = XjlScToiletInfo.queryScToiletById(Long.valueOf(id));
		ok(xjlScToiletInfo);
	}
	
	/**
	 * 通过主键修改卫生间名称
	 */
	public static void modifyToilet(){
		String id = params.get("id");
		String toiletName = params.get("toiletName");
		XjlScToiletInfo.modifyToilet(toiletName, id);
	}
	
	/**
	 * 配置绑定设备
	 */
	public static void modifyBindCode(){
		String controllerCode = params.get("controllerCode");
		String sensorCode = params.get("sensorCode");
		String radiotubeCode = params.get("radiotubeCode");
		String liquidCode = params.get("liquidCode");
		String controlCode = params.get("controlCode");
		String wifiCode = params.get("wifiCode");
		String mosqCode = params.get("mosqCode");
		String id = params.get("id");
		int ret = XjlScToiletInfo.modifyToiletCode(controllerCode, sensorCode, radiotubeCode, liquidCode,controlCode,wifiCode,mosqCode, id);
		ok(ret);
	}
	
	/**
	 * 关注学校的人
	 */
	public static void queryAttentionUserForSchool(){
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		WxUser wxUser = getWXUser();
		Map<String,String> condition = new HashMap<>();
		condition.put("schoolId", String.valueOf(wxUser.schoolId));
		Map map = WxUser.queryUserBySchool(condition, pageIndex, pageSize);
		ok(map);
	}
	
	/**
	 * 通过微信公众号得到信息
	 */
	public static void queryUserByWxOpenId(){
		String openid = params.get("wxOpenId");
		WxUser wxUser = WxUser.getUserByOpenId(openid);
		int pageIndex = 1;
        int pageSize = 100;
        Map condition = new HashMap<String, String>();
        condition.put("wxOpenId", openid);
        XjlScSchoolUser xjlSchoolUser = XjlScSchoolUser.queryFindByWxOpenId(condition, pageIndex, pageSize);
		if(StringUtil.isNotEmpty(xjlSchoolUser)){
			wxUser.xjlScSChool = XjlScSchool.getSchoolBySchoolId(xjlSchoolUser.schoolId,"");
		}
		ok(wxUser);
	}
	
	/**
	 * 待审核
	 */
	public static void queryCheckPending(){
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		WxUser wxUser = getWXUser();
		Map<String,String> condition = new HashMap<>();
		condition.put("schoolId",String.valueOf(wxUser.xjlScSChool.schoolId));
		List<XjlScSchoolUser> data = XjlScSchoolUser.queryCheckPending(condition, pageIndex, pageSize);
		ok(data);
	}
	
	/**
	 * 审核操作
	 */
	public static void doModifyCheck(){
		String id = params.get("id");
		String status = params.get("status");
		String _status = "pass".equals(status)?"0AA":"0XX";
		int ret = XjlScSchoolUser.modifyCheck(id,_status);
		ok(ret);
	}
	
	/**
	 * 审核通过消息推送
	 */
	public static void doCheckPushMsg(){
		 String wxOpenId = params.get("wxOpenId");
		 WxUser wxUser = WxUser.getFindByOpenId(wxOpenId);
		 Map<String, Object> mapData = new HashMap<String, Object>();
		 Map<String, Object> mapDataSon = new HashMap<String, Object>();
		 mapDataSon.put("value", "学校绑定已经通过审核");
		 mapDataSon.put("color", "#68A8C3");
		 mapData.put("first", mapDataSon);
		 mapDataSon = new HashMap<String, Object>();
		 mapDataSon.put("value",UUID.randomUUID().toString().substring(0, 10));
		 mapData.put("keyword1", mapDataSon);
		 mapDataSon = new HashMap<String, Object>();
		 mapDataSon.put("value",wxUser.nickName);
		 mapData.put("keyword2", mapDataSon);
		 mapDataSon = new HashMap<String, Object>();
		 mapDataSon.put("value", "2013-01-1");
		 mapDataSon.put("color","#808080");
		 mapData.put("keyword3", mapDataSon);
		 mapDataSon = new HashMap<String, Object>();
		 mapDataSon.put("value", "感谢您的使用");
		 mapDataSon.put("color","#808080");
		 mapData.put("remark", mapDataSon);
		 String url="";
		 MsgPush.wxMsgPusheTmplate("IztPNos-0Hp7-SVdpclYQy25OwIeazJvn9TLzAgTL7A", url, mapData,wxUser.wxOpenId);
	}
	
	/**
	 * 保存设别
	 */
	public static void saveDevice(){
		String businessStatus = params.get("businessStatus");
		String deviceStatus = params.get("deviceStatus");
		String name = params.get("name");
		WxUser wxUser = getWXUser();
		XjlScDeviceManage xjlScDeviceManage = new XjlScDeviceManage();
		xjlScDeviceManage.name = name;
		xjlScDeviceManage.businessStatus = businessStatus;
		xjlScDeviceManage.deviceStatus = deviceStatus;
		xjlScDeviceManage.wxOpenId = wxUser.wxOpenId;
		xjlScDeviceManage = XjlScDeviceManageBo.save(xjlScDeviceManage);
		ok(xjlScDeviceManage);
	}
	/**
	 * 查询设备号
	 */
	public static void queryDevice(){
		String businessStatus = params.get("businessStatus");
		String deviceStatus = params.get("deviceStatus");
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		Map<String,String> condition = new HashMap<>();
		condition.put("businessStatus",businessStatus);
		condition.put("deviceStatus",deviceStatus);
		Map ret = XjlScDeviceManage.query(condition, pageIndex, pageSize);
		ok(ret);
	}
	
	/**
	 * 通过主键获取信息
	 */
	public static void queryDeviceById(){
		String id = params.get("id");
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		Map<String,String> condition = new HashMap<>();
		condition.put("id", id);
		XjlScDeviceManage deviceManage = XjlScDeviceManage.queryById(condition, pageIndex, pageSize);
		ok(deviceManage);
	}
	
	/**
	 * 修改设备名称
	 */
	public static void modifyDevice(){
		String id = params.get("id");
		String name = params.get("name");
		int ret = XjlScDeviceManage.modifyDevice(name, id);
		ok(ret);
	}
	
	/**
	 * 逻辑删除
	 */
	public static void modifyDeviceStatus(){
		String id = params.get("id");
		int ret = XjlScDeviceManage.modifyStatus(id);
		ok(ret);
	}
	
	/**
	 * 执行二维码生成
	 * @throws IOException 
	 */
	private static String root = "xjl_quickRush";
	public static void doQRCode() throws IOException{
	    WxUser wxUser = getWXUser();
	    String newName = wxUser.wxOpenId+".png";
	    String qrcode = params.get("qrcode");
	    String id = params.get("id");
	    String type = params.get("type");
	    String savePath = QRCode.getUploadPath(type);
	    savePath = savePath +newName;
	    boolean flag = QRCode.zxingCodeCreate(qrcode,300,300,savePath,"png");
	    int ret = 0;
	    String [] path = savePath.split(root);
	    if(flag){
	    	ret = XjlScDeviceManage.modifyQRCode(qrcode,path[1], id);
	    }
	    ok(path[1]);
	}
	
	/**
	 * 验证二维码是否有效
	 */
	public static void queryQrCode(){
		String qrCode = params.get("qrCode");
		String deviceStatus = params.get("deviceStatus");
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		Map<String,String> condition = new HashMap<>();
		condition.put("deviceStatus", deviceStatus);
		condition.put("qrCode", qrCode);
		XjlScDeviceManage deviceManage = XjlScDeviceManage.queryByQRCode(condition, pageIndex, pageSize);
		ok(StringUtil.isNotEmpty(deviceManage));
	}
	
	/**
	 * 出库操作
	 */
	public static void modifyBussinessStatus(){
		String qrCode = params.get("qrCode");
		String deviceStatus = params.get("deviceStatus");
		int ret = XjlScDeviceManage.modifyBussinessDevice(qrCode, deviceStatus);
		ok(ret>0);
	}
	
	/**
	 * 生成二维码校验
	 */
	public static void queryRetByQRCode(){
		String qrCode = params.get("qrCode");
		String deviceStatus = params.get("deviceStatus");
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		Map<String,String> condition = new HashMap<>();
		condition.put("deviceStatus", deviceStatus);
		condition.put("qrCode", qrCode);
		XjlScDeviceManage deviceManage = XjlScDeviceManage.queryRetByQRCode(condition, pageIndex, pageSize);
		ok(StringUtil.isNotEmpty(deviceManage) == false);
	}
	
	/**
	 * 统计每个类型
	 */
	public static void queryCount(){
		int pageIndex = StringUtil.getInteger(params.get("PAGE_INDEX"), 1);
		int pageSize = StringUtil.getInteger(params.get("PAGE_SIZE"), 100);
		Map<String,String> condition = new HashMap<>();
		Map map = XjlScDeviceManage.queryCount(condition, pageIndex, pageSize);
		ok(map);
	}
}

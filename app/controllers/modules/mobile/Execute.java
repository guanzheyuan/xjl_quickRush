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
import controllers.modules.mobile.bo.XjlScLogBo;
import controllers.modules.mobile.bo.XjlScToiletInfoBo;
import controllers.modules.mobile.filter.MobileFilter;
import models.modules.mobile.WxServer;
import models.modules.mobile.WxUser;
import models.modules.mobile.XjlScConfig;
import models.modules.mobile.XjlScLog;
import models.modules.mobile.XjlScSchool;
import models.modules.mobile.XjlScToiletInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.Logger;
import play.Play;
import play.cache.Cache;
import utils.CreateHtml;
import utils.CreateQRCode;
import utils.DateUtil;
import utils.FileUtil;
import utils.HttpClientUtil;
import utils.Preview;
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
		 XjlScLog xjlScLog = new XjlScLog();
		 xjlScLog.errorCode = errorCode;
		 xjlScLog.errorDesc = errorDesc;
		 xjlScLog.methodName = methodName;
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
		XjlScConfig xjlScConfig = new XjlScConfig();
		xjlScConfig.timeQuantum = startTime.replace("：",":").replace(" ", "")+" — "+endTime.replace("：",":").replace(" ", "");
		xjlScConfig.interval = interval;
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
	 * 查询卫生间
	 */
	public static void querytoilet(){
		Map<String,String> condition = new HashMap<>();
		Map ret = XjlScToiletInfo.query(condition, 1, 100000000);
		ok(ret);
	}
	/**
	 * 新增
	 */
	public static void saveToilet(){
		String toiletName = params.get("toiletName");
		XjlScToiletInfo xjlScToiletInfo = new XjlScToiletInfo();
		xjlScToiletInfo.toiletName = toiletName;
		xjlScToiletInfo.toiletCode="AAB";
		XjlScToiletInfoBo.save(xjlScToiletInfo);
	}
	
	
	/**
	 * 删除卫生间
	 */
	public static void delToilet(){
		String id = params.get("id");
		XjlScToiletInfo.delete(id);
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
	
}

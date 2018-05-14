package controllers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.ivy.Main;
import org.json.JSONException;
import org.json.XML;
import models.modules.mobile.WxServer;
import net.sf.json.JSONObject;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.mvc.Http.Request;
import utils.EncoderHandler;
import utils.HttpClientUtil;
import utils.MD5Encoder;
import utils.MatrixToImageWriterWithLogo;
import utils.SysParamUtil;
import utils.UrlHelper;
import utils.WXPayConstants.SignType;
import utils.WXPayUtil;
import controllers.comm.BaseController;
import controllers.modules.weixin.utils.AccessTokenHolder;
import controllers.modules.weixin.utils.WXRequestAddr;

public class Weixin extends BaseController {

	
	private static String API_KEY="79159FC319A347D391A5E8F0B974951E";
	private static String SERVICE_CODE = "gh_00e09da4fab0";
	public static void getWxSdkInfo(Long vnoId) {
		String url = params.get("url");
		String openId = params.get("openId");

		Logger.info(" +++getWxSdkInfo vnoId =%s ", vnoId);
		Logger.info(" +++getWxSdkInfo openId =%s ", openId);

		WxServer server = WxServer.findById(1l);

		String appId = server.appId;
		Logger.info(" +++getWxSdkInfo appId =%s ", appId);

		String timestamp = create_timestamp();
		String nonce = create_nonce_str();
		String accessToken = AccessTokenHolder.getAccessTokenByAppId(appId);

		String jsapi_ticket = (String) Cache.get(appId);

		if (jsapi_ticket == null) {
			String getTicket = String.format(WXRequestAddr.POST_JSAPI_TICKET,
					accessToken);
			JSONObject json = HttpClientUtil.invoke(getTicket, "POST", null);
			// 去SHA1 散列值
			jsapi_ticket = json.getString("ticket");
			Cache.set(appId, jsapi_ticket, "1h");
		}

		String[] paramArr = new String[] { "jsapi_ticket=" + jsapi_ticket,
				"timestamp=" + timestamp, "noncestr=" + nonce, "url=" + url };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		String content = paramArr[0].concat("&" + paramArr[1])
				.concat("&" + paramArr[2]).concat("&" + paramArr[3]);

		//
		// String getSinature = "jsapi_ticket=" + jsapi_ticket + "&noncestr="
		// + nonce + "&timestamp=" + timestamp + "&url=" + url;
		// Logger.info("getSinature = " + content);

		// String sortStr = CheckSignature.getSortString(timestamp, nonce,
		// token);
		// 去SHA1 散列值
		String signature = EncoderHandler.encode("SHA1", content);

		// Logger.info("jsapi_ticket = " + jsapi_ticket);
		// Logger.info("signature = " + signature);
		// Logger.info("timestamp = " + timestamp);
		// Logger.info("nonce = " + nonce);
		 Logger.info("url = " + url);
		HashMap map = new HashMap();
		map.put("appId", appId);
		map.put("signature", signature);
		map.put("timestamp", timestamp);
		map.put("nonce", nonce);
		map.put("jsapi_ticket", jsapi_ticket);
		map.put("url", url);
		renderJSON(map);
		// renderTemplate("/weixin.html", appId, signature, timestamp, nonce);
	}

	
	 
	 
	public static String getRequestXml(SortedMap<Object,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        sb.append("<xml>");  
        Set es = parameters.entrySet();  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = String.valueOf(entry.getKey());  
            String v = String.valueOf(entry.getValue());  
            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {  
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");  
            }else {  
                sb.append("<"+k+">"+v+"</"+k+">");  
            }  
        }  
        sb.append("</xml>");  
        return sb.toString();  
    }  
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	private static String create_nonce_str() {
		return "Wm3WZYTPz0wzccnW";
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	public static void main(String[] args) {
		System.out.println(Long.toString(System.currentTimeMillis() / 1000));
	}
	
}

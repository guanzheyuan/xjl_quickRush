package models.modules.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.GenericModel;
import utils.StringUtil;
import utils.jpa.SQLResult;

@Entity
@Table(name = "xjl_sc_toiletinfo")
public class XjlScToiletInfo extends GenericModel {

	@Id
	@Column(name = "id")
	public Long id;
	
	@Column(name = "toilet_name")
	public String toiletName;
	
	@Column(name = "toilet_code")
	public String toiletCode;
	
	@Column(name = "device_code")
	public String deviceCode;
	
	
	@Column(name = "sensor_code")
	public String sensorCode;
	
	@Column(name = "radiotube_code")
	public String radiotubeCode;
	
	@Column(name = "liquid_code")
	public String liquidCode;
	
	@Column(name = "control_code")
	public String controlCode;
	
	@Column(name = "wifi_code")
	public String wifiCode;
	
	@Column(name="mosq_code")
	public String mosqCode;
	
	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	

	@Transient
	public boolean isControl;
	@Transient
	public boolean isSensor;
	@Transient
	public boolean isRadiotube;
	@Transient
	public boolean isLiquid;
	@Transient
	public boolean isWifi;
	@Transient
	public boolean isMosq;
	
	
	public static Map query(Map<String, String> condition,
			int pageIndex, int pageSize){
		String sql = "select a.id,a.toilet_name,a.toilet_code,a.status,a.device_code,a.control_code,a.sensor_code,a.radiotube_code,a.liquid_code,a.wifi_code,a.mosq_code from xjl_sc_toiletinfo a ,xjl_sc_school_toilet b where a.id = b.toilet_id and b.status='0AA' and b.school_id='"+condition.get("schoolId")+"'";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object[]> retData = ModelUtils.queryData(pageIndex, pageSize, ret);
		List<XjlScToiletInfo> data =  new ArrayList<XjlScToiletInfo>();
		XjlScToiletInfo xjlScToiletInfo ;
		for(Object[]m :retData){
			xjlScToiletInfo = new XjlScToiletInfo();
			if(m[0]!=null){
				xjlScToiletInfo.id = StringUtil.getLong(m[0].toString());
			}
			if(m[1]!=null){
				xjlScToiletInfo.toiletName = m[1].toString();
			}
			if(m[2]!=null){
				xjlScToiletInfo.toiletCode = m[2].toString();
			}
			if(m[3]!=null){
				xjlScToiletInfo.status = m[3].toString();
			}
			if(m[4]!=null){
				xjlScToiletInfo.deviceCode = m[4].toString();
			}
			if(m[5]!=null){
				xjlScToiletInfo.isControl = true;
			}
			if(m[6]!=null){
				xjlScToiletInfo.isSensor = true;
			}
			if(m[7]!=null){
				xjlScToiletInfo.isRadiotube  =true;
			}
			if(m[8]!=null){
				xjlScToiletInfo.isLiquid  =true;
			}
			if(m[9]!=null){
				xjlScToiletInfo.isWifi  =true;
			}
			if(m[10]!=null){
				xjlScToiletInfo.isMosq  =true;
			}
			data.add(xjlScToiletInfo);
		}
		return ModelUtils.createResultMap(ret, data);
	}
	
	public static int delete(String id){
		String sql = "update  xjl_sc_toiletinfo set status='0XX' where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
	
	public static XjlScToiletInfo queryScToiletById(Long id){
		String sql = "select * from xjl_sc_toiletinfo where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlScToiletInfo> data = ModelUtils.queryData(1, -1, ret,XjlScToiletInfo.class);
		XjlScToiletInfo toiletInfo = null;
		if(data.isEmpty()){
			return null;
		}else{
			toiletInfo = data.get(0);
			if(!StringUtil.isEmpty(toiletInfo.controlCode)){
				toiletInfo.isControl = true;
			}
			if(!StringUtil.isEmpty(toiletInfo.sensorCode)){
				toiletInfo.isSensor = true;
			}
			if(!StringUtil.isEmpty(toiletInfo.radiotubeCode)){
				toiletInfo.isRadiotube  =true;
			}
			if(!StringUtil.isEmpty(toiletInfo.liquidCode)){
				toiletInfo.isLiquid  =true;
			}
			if(!StringUtil.isEmpty(toiletInfo.wifiCode)){
				toiletInfo.isWifi  =true;
			}
			if(!StringUtil.isEmpty(toiletInfo.mosqCode)){
				toiletInfo.isMosq  =true;
			}
		}
		return toiletInfo;
	}
	
	public static int modifyToilet(String toiletName,String id){
		String sql="update xjl_sc_toiletinfo set toilet_name='"+toiletName+"' where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
	
	public static int modifyRun(String status,String id){
		String sql="update xjl_sc_toiletinfo set status='"+status+"' where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
	
	
	
	public static int modifyToiletCode(String controllerCode,String sensorCode,String radiotubeCode,String liquidCode,String controlCode,String wifiCode,String mosqCode,String id){
		String sql="update xjl_sc_toiletinfo set ";
		if(!StringUtil.isEmpty(controllerCode)){
			sql +=" controller_code = '"+controllerCode+"'";
		}
		else if(!StringUtil.isEmpty(sensorCode)){
			sql +=" sensor_code = '"+sensorCode+"'";
		}
		else if(!StringUtil.isEmpty(radiotubeCode)){
			sql +=" radiotube_code = '"+radiotubeCode+"'";
		}
		else if(!StringUtil.isEmpty(liquidCode)){
			sql +=" liquid_code = '"+liquidCode+"'";
		}
		else if(!StringUtil.isEmpty(controlCode)){
			sql +=" control_code = '"+controlCode+"'";
		}
		else if(!StringUtil.isEmpty(wifiCode)){
			sql +=" wifi_code = '"+wifiCode+"'";
		}
		else if(!StringUtil.isEmpty(mosqCode)){
			sql +=" mosq_code='"+mosqCode+"'";
		}
		sql +=" where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}

}

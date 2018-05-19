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
	
	@Column(name = "controller_code")
	public String controllerCode;
	
	@Column(name = "sensor_code")
	public String sensorCode;
	
	@Column(name = "radiotube_code")
	public String radiotubeCode;
	
	@Column(name = "liquid_code")
	public String liquidCode;
	
	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	public static Map query(Map<String, String> condition,
			int pageIndex, int pageSize){
		String sql = "select a.id,a.toilet_name,a.toilet_code,a.status,a.device_code from xjl_sc_toiletinfo a ,xjl_sc_school_toilet b where a.id = b.toilet_id and b.status='0AA' and b.school_id='"+condition.get("schoolId")+"'";
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
			data.add(xjlScToiletInfo);
		}
		return ModelUtils.createResultMap(ret, data);
	}
	
	public static int delete(String id){
		String sql = "delete from xjl_sc_toiletinfo where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
	
	public static XjlScToiletInfo queryScToiletById(Long id){
		String sql = "select * from xjl_sc_toiletinfo where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlScToiletInfo> data = ModelUtils.queryData(1, -1, ret,XjlScToiletInfo.class);
		if(data.isEmpty()){
			return null;
		}
		return data.get(0);
	}
	
	public static int modifyToilet(String toiletName,String id){
		String sql="update xjl_sc_toiletinfo set toilet_name='"+toiletName+"' where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
	
	public static int modifyToiletCode(String controllerCode,String sensorCode,String radiotubeCode,String liquidCode,String id){
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
		sql +=" where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}

}

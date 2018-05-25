package models.modules.mobile;

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
@Table(name = "xjl_sc_device_manage")
public class XjlScDeviceManage extends GenericModel  {

	@Id
	@Column(name = "id")
	public Long id;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "device_status")
	public String deviceStatus;
	
	@Column(name = "business_status")
	public String businessStatus;
	
	@Column(name = "status")
	public String status;
	
	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;
	
	@Column(name="QR_code")
	public String QRcode;
	
	@Column(name="QRCODE_PATH")
	public String QRcodePath;
	
	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Transient
	public boolean isQrcode;
	
	public static Map query(Map<String, String> condition,
			int pageIndex, int pageSize){
		String sql="select * from xjl_sc_device_manage where status='0AA' and business_status='"+condition.get("businessStatus")+"' and  device_status='"+condition.get("deviceStatus")+"' order by CREATE_TIME desc";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlScDeviceManage> data = ModelUtils.queryData(pageIndex, pageSize, ret,XjlScDeviceManage.class);
		if(!data.isEmpty()){
			for (XjlScDeviceManage xjlScDeviceManage : data) {
				if(!StringUtil.isEmpty(xjlScDeviceManage.QRcode)){
					xjlScDeviceManage.isQrcode = true;
				}
			}
		}
		return ModelUtils.createResultMap(ret, data);
	}
	
	public static XjlScDeviceManage queryById(Map<String, String> condition,
			int pageIndex, int pageSize){
		String sql = "select * from xjl_sc_device_manage where status='0AA' and id='"+condition.get("id")+"'";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlScDeviceManage> data = ModelUtils.queryData(pageIndex, pageSize, ret,XjlScDeviceManage.class);
		if(data.isEmpty()){
			return null;
		}else{
			return data.get(0);
		}
	}
	
	public static int modifyDevice(String name,String id){
		String sql = "update xjl_sc_device_manage set name='"+name+"' where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
	
	public static int modifyStatus(String id){
		String sql = "update xjl_sc_device_manage set status='0XX'  where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
	
	public static int modifyQRCode(String QRCode,String QRCodePath,String id){
		String sql="update xjl_sc_device_manage set QR_code='"+QRCode+"',QRCODE_PATH='"+QRCodePath+"' where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
}

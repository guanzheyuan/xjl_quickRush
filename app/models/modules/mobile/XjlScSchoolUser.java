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
@Table(name = "xjl_sc_school_user")
public class XjlScSchoolUser extends GenericModel {

	
	
	@Id
	@Column(name = "ID")
	public Long id;
	
	@Column(name = "WX_OPEN_ID")
	public String wxOpenId;
	
	@Column(name = "SCHOOL_ID")
	public Long schoolId;
	
	@Column(name = "ISADMIN")
	public String isAdmin;
	
	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Column(name = "username")
	public String username;
	
	@Column(name = "telephone")
	public String telephone;
	
	@Transient
	public String nickName;
	
	/**
	 * 通过微信编号得用户绑定信息
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static XjlScSchoolUser queryFindByWxOpenId(Map<String, String> condition,int pageIndex, int pageSize){
		String sql = "select * from xjl_sc_school_user where status='0AA'  and WX_OPEN_ID='"+condition.get("wxOpenId")+"'";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlScSchoolUser> data = ModelUtils.queryData(pageIndex, pageSize, ret, XjlScSchoolUser.class);
		if(data.isEmpty()){
			return null;
		}else{
			return data.get(0);
		}
	}
	
	public static XjlScSchoolUser queryFindCheckByWxOpenId(Map<String, String> condition,int pageIndex, int pageSize){
		String sql = "select * from xjl_sc_school_user where status='0UU'  and WX_OPEN_ID='"+condition.get("wxOpenId")+"'";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlScSchoolUser> data = ModelUtils.queryData(pageIndex, pageSize, ret, XjlScSchoolUser.class);
		if(data.isEmpty()){
			return null;
		}else{
			return data.get(0);
		}
	}
	
	public static List<XjlScSchoolUser> queryCheckPending(Map<String, String> condition,
			int pageIndex, int pageSize){
		String sql="select b.nick_name,b.wx_open_id,a.id,a.username,a.telephone from xjl_sc_school_user a,wx_user b where a.wx_open_id=b.wx_open_id and a.status='0UU' and a.school_id='"+condition.get("schoolId")+"' order by a.create_time desc";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object[]> retData = ModelUtils.queryData(pageIndex, pageSize, ret);
		List<XjlScSchoolUser> data  = new ArrayList<>();
		XjlScSchoolUser xjlScSchoolUser;
		for(Object[]m :retData){
			xjlScSchoolUser = new XjlScSchoolUser();
			if(m[0]!=null){
				xjlScSchoolUser.nickName =m[0].toString(); 
			}
			if(m[1]!=null){
				xjlScSchoolUser.wxOpenId = m[1].toString();
			}
			if(m[2]!=null){
				xjlScSchoolUser.id = StringUtil.getLong(m[2].toString());
			}
			if(m[3]!=null){
				xjlScSchoolUser.username = m[3].toString();
			}
			if(m[4]!=null){
				xjlScSchoolUser.telephone = m[4].toString();
			}
			data.add(xjlScSchoolUser);
		}
		return data;
	}
	
	public static int modifyCheck(String id,String status){
		String sql = "update xjl_sc_school_user set status='"+status+"' where id='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
}

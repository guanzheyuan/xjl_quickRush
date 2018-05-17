package models.modules.mobile;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
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
	
	
	/**
	 * 通过微信编号得用户绑定信息
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static XjlScSchoolUser queryFindByWxOpenId(Map<String, String> condition,int pageIndex, int pageSize){
		String sql = "select * from xjl_sc_school_user where status='0AA' and WX_OPEN_ID='"+condition.get("wxOpenId")+"'";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<XjlScSchoolUser> data = ModelUtils.queryData(pageIndex, pageSize, ret, XjlScSchoolUser.class);
		if(data.isEmpty()){
			return null;
		}else{
			return data.get(0);
		}
	}
}

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

import org.slf4j.LoggerFactory;

import play.db.jpa.GenericModel;
import utils.StringUtil;
import utils.jpa.SQLResult;

@Entity
@Table(name = "wx_user")
public class WxUser extends GenericModel {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(WxUser.class);
	@Id
	@Column(name = "WX_OPEN_ID")
	public String wxOpenId; 


	@Column(name = "TEACHER_ID")
	public Long teacherId;
	
	@Column(name = "SCHOOL_ID")
	public Long schoolId;


	@Column(name = "NICK_NAME")
	public String nickName;

	@Column(name = "HEAD_IMG_URL")
	public String headImgUrl;

	@Column(name = "SEX")
	public String sex;

	@Column(name = "LANGUAGE")
	public String language;

	@Column(name = "COUNTRY")
	public String country;

	@Column(name = "PROVINCE")
	public String province;

	@Column(name = "CITY")
	public String city;

	@Column(name = "WX_PHONE")
	public String wxPhone;
	
	@Column(name = "USER_PWD")
	public String userPwd;

	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	@Column(name = "UP_OPENID_TIME")
	public Date upOpenidTime;

	@Column(name = "IS_CONCERNED")
	public String isConcerned;
	
	@Column(name = "OPEN_ID_CHANNCEL")
	public String openIdChanncel;
	
	@Column(name = "user_type")
	public String userType;
	
	@Transient
	public String  attentionTime;
	
	@Transient
	public WxServer wxServer;
	
	@Transient
	public XjlScSchool xjlScSChool;
	
	@Transient
	public XjlScSchoolUser scSchoolUser;
	
	@Transient
	public boolean isBinding;
	
	@Transient
	public boolean isAdmin;
	
	@Transient
	public boolean isApply;
	
	
	/**
	 * 通过微信编号得到用户信息
	 * @param openid
	 * @return
	 */
	public static WxUser getFindByOpenId(String openid){
		log.debug("getFindByOpenId方法openId:" + openid);
		int pageIndex = 1;
        int pageSize = 100;
        Map condition = new HashMap<String, String>();
        if(StringUtil.isNotEmpty(openid)){
            condition.put("wxOpenId", openid);
        }
        Map returnMap = queryWxUserListByPage(condition,pageIndex,pageSize);
        List<WxUser> retData = (List<WxUser>)returnMap.get("data");
        if(retData.isEmpty()){
        	throw new RuntimeException("没有该用户:"+openid);
        }else{
        	log.debug("........................."+retData.get(0));
        	log.debug("一共查询符合条件的数据有:" + retData.size());
        	WxUser wxUser = retData.get(0);
        	//查询该用户是否绑定学校
        	XjlScSchoolUser xjlSchoolUser = XjlScSchoolUser.queryFindByWxOpenId(condition, pageIndex, pageSize);
        	wxUser.isBinding = StringUtil.isNotEmpty(xjlSchoolUser);
        	if(StringUtil.isNotEmpty(xjlSchoolUser)){
        		wxUser.xjlScSChool =  XjlScSchool.getSchoolBySchoolId(xjlSchoolUser.schoolId,"");
        		wxUser.scSchoolUser = xjlSchoolUser;
        	}
        	//查询该用户是否绑定为管理员
        	if(StringUtil.isNotEmpty(xjlSchoolUser)){
        		wxUser.isAdmin = "true".equals(xjlSchoolUser.isAdmin);
        	}
        	//判断是否以提交过但未审核
     		 xjlSchoolUser = XjlScSchoolUser.queryFindCheckByWxOpenId(condition, 1,10);
     		 if(StringUtil.isNotEmpty(xjlSchoolUser)){
     			wxUser.isApply = true;
     		 }
        	
        	return wxUser;
        }
	}
	
	public static WxUser getUserByOpenId(String openid){
		log.debug("getFindByOpenId方法openId:" + openid);
		int pageIndex = 1;
        int pageSize = 100;
        Map condition = new HashMap<String, String>();
        if(StringUtil.isNotEmpty(openid)){
            condition.put("wxOpenId", openid);
        }
        Map returnMap = queryWxUserListByPage(condition,pageIndex,pageSize);
        List<WxUser> retData = (List<WxUser>)returnMap.get("data");
        if(retData.isEmpty()){
        	return null;
        }else{
        	log.debug("一共查询符合条件的数据有:" + retData.size());
        	WxUser wxUser = retData.get(0);
        	log.debug("........................."+wxUser.nickName);
        	return wxUser;
        }
	}
	/**
	 * 查询用户
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static Map queryWxUserListByPage(Map<String, String> condition,
		int pageIndex, int pageSize) {
		String sql = "select * ";
		sql += "from wx_user a ";
		sql += "where 1=1 and status='0AA' ";
		if(StringUtil.isNotEmpty(condition.get("wxOpenId"))){
			String searchKeyWord = condition.get("wxOpenId");
			sql += "and a.wx_open_id='"+searchKeyWord+"' ";
	    }
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<WxUser> data = ModelUtils.queryData(pageIndex, pageSize, ret, WxUser.class);
		return ModelUtils.createResultMap(ret, data);
	}
	
	public static Map queryUserBySchool(Map<String, String> condition,int pageIndex, int pageSize){
		String sql ="select  a.wx_open_id,a.nick_name,a.sex,a.country,a.city,a.is_concerned,b.create_time from wx_user a ,xjl_sc_school_user b "
				+ "where b.status='0AA'and a.wx_open_id=b.wx_open_id and b.school_id='"+condition.get("schoolId")+"'";
		SQLResult ret = ModelUtils.createSQLResult(condition, sql);
		List<Object[]> retData = ModelUtils.queryData(pageIndex, pageSize, ret);
		List<WxUser> data =  new ArrayList<WxUser>();
		WxUser wxUser;
		for(Object[]m :retData){
			wxUser = new WxUser();
			if(m[0]!=null){
				wxUser.wxOpenId = m[0].toString();
			}
			if(m[1]!=null){
				wxUser.nickName = m[1].toString();
			}
			if(m[2]!=null){
				wxUser.sex = m[2].toString();
			}
			if(m[3]!=null){
				wxUser.country = m[3].toString();
			}
			if(m[4]!=null){
				wxUser.city = m[4].toString();
			}
			if(m[5]!=null){
				wxUser.isConcerned = m[5].toString();
			}
			if(m[6]!=null){
				wxUser.attentionTime = m[6].toString();
			}
			data.add(wxUser);
		}
		return ModelUtils.createResultMap(ret, data);
	}
}

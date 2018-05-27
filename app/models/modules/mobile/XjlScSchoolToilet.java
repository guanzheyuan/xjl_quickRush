package models.modules.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;

@Entity
@Table(name = "xjl_sc_school_toilet")
public class XjlScSchoolToilet extends GenericModel {

	
	@Id
	@Column(name = "ID")
	public Long id;
	
	@Column(name = "TOILET_ID")
	public Long toiletId;
	
	@Column(name = "SCHOOL_ID")
	public Long schoolId;
	
	@Column(name = "STATUS")
	public String status;

	@Column(name = "CREATE_TIME")
	public Date createTime;
	
	public static int delete(String id){
		String sql = "update  xjl_sc_school_toilet set status='0XX' where TOILET_ID='"+id+"'";
		Map<String, String> condition = new HashMap<String, String>();
		return ModelUtils.executeDelete(condition, sql);
	}
}

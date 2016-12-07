package com.pxene.pap.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pxene.pap.domain.beans.ProjectBean;

@Repository
public class ProjectDao {

	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 public int createProject(ProjectBean bean) throws Exception{
		 String id = bean.getId();
		 String advertiserId = bean.getAdvertiserId();
		 String name = bean.getName();
		 int totalBudget = bean.getTotalBudget();
		 String status = bean.getStatus();
		 //插入项目信息
		 String sql = "insert into pap_t_project (id,advertiserid,name,totalbudget,status)"+
				 		" values(?,?,?,?,?)";
		 int num = jdbcTemplate.update(sql, id,advertiserId,name,totalBudget,status);
		 return num;
	 }
	 
	 /**
	  * 根据名称和id查询项目，用于判断名称是否重复
	  * @param name
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 public int selectProjectByNameAndId(String name,String id) throws Exception{
		 //插入项目信息
		 String sql = "select t.* from pap_t_project t"+
				 " where t.name = '" + name + "'";
		if (id != null) {
			sql = sql + "and t.id !=" + id;
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if (list == null || list.isEmpty()) {
			return 0;
		}
		 return list.size();
	 }
}


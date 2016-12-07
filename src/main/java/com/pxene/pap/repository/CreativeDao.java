package com.pxene.pap.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pxene.pap.domain.beans.CreativeBean;

@Repository
public class CreativeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 添加创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int createCreative(CreativeBean bean) throws Exception {
		String creativeId = bean.getId();
		String name = bean.getName();
		String campaignId = bean.getCampaignId();
		List<Float> prices = bean.getPrice();
		List<String> materialIds = bean.getMaterialIds();

		for (int i = 0; i < materialIds.size(); i++) {
			String mapid = UUID.randomUUID().toString();
			String materialId = materialIds.get(i);
			Float price = prices.get(i);
			String sql = "insert into pap_t_creative_material (id,creativeid,materialid,price) values (?,?,?,?)";
			jdbcTemplate.update(sql, mapid, creativeId, materialId, price);
		}
		String cSql = "insert into pap_t_creative (id,campaignid,name) values (?,?,?)";
		int num = jdbcTemplate.update(cSql, creativeId, campaignId, name);
		return num;
	}

}

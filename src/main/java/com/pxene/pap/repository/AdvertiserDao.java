package com.pxene.pap.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pxene.pap.domain.beans.AdvertiserBean;

@Repository
public class AdvertiserDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    public int save(AdvertiserBean advertiser)
    {
        String sql = "insert into pap_t_advertiser (id, name, company, contact, phone, qq, industryid, licenseno, organizationno, "
                + "logourl, icpurl, organizationurl, licenseurl, accounturl, siteurl, sitename, email, zip, address, status, remark) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        int affectedRows = jdbcTemplate.update(sql, UUID.randomUUID().toString(), advertiser.getName(), advertiser.getCompany(), advertiser.getContact(), 
                advertiser.getPhone(), advertiser.getQQ(), advertiser.getIndustryId(), advertiser.getLicenseNO(), 
                advertiser.getOrganizationNO(), advertiser.getLogoURL(), advertiser.getIcpURL(), advertiser.getOrganizationURL(), 
                advertiser.getAccountURL(), advertiser.getSiteURL(), advertiser.getSiteName(), advertiser.getEmail(), 
                advertiser.getEmail(), advertiser.getZip(), advertiser.getAddress(), advertiser.getStatus(), advertiser.getRemark());
        
        return affectedRows;
    }
    
    
}

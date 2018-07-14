package com.pxene.pap.repository.custom;

import java.util.List;
import java.util.Map;


public interface CustomLandpageDao
{
    
    List<Map<String, String>> selectLandPages();
    
    
    Map<String, String> selectLandPagesByPrimaryKey(String id);
}

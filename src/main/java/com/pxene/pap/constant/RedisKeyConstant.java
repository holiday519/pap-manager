package com.pxene.pap.constant;

import java.util.HashMap;
import java.util.Map;

public class RedisKeyConstant {

	/**
	 * 正在投放的活动id
	 */
	public static final String CAMPAIGN_IDS = "pap_groupids";
	/**
	 * 活动下创意
	 */
	public static final	String CAMPAIGN_MAPIDS = "dsp_groupid_mapids_";
	/**
	 * 活动定向
	 */
	public static final	String CAMPAIGN_TARGET = "dsp_groupid_target_";
	/**
	 * 活动基本信息
	 */
	public static final	String CAMPAIGN_INFO = "dsp_groupid_info_";
	/**
	 * 活动频次
	 */
	public static final	String CAMPAIGN_FREQUENCY = "dsp_groupid_frequencycapping_";
	/**
	 * 监测地址模版
	 */
	public static final String MONITOR_TEMPLATES = "var imgdc{index} = new Image();imgdc{index}.src = '{imonitorurl}';window[new Date()] = imgdc{index};";
	/**
	 * 创意基本信息
	 */
	public static final String CREATIVE_INFO = "dsp_mapid_"; 
	/**
	 * 定向标志位
	 */
	public static final Map<String, Integer[]> TARGET_CODES = new HashMap<String, Integer[]>();
    static {
        TARGET_CODES.put("region", new Integer[]{0x0, 0x1, 0x2, 0x3});
        TARGET_CODES.put("network", new Integer[]{0x0, 0x4, 0x8, 0xc});
        TARGET_CODES.put("os", new Integer[]{0x0, 0x10, 0x20, 0x30});
        TARGET_CODES.put("operator", new Integer[]{0x0, 0x40, 0x80, 0xc0});
        TARGET_CODES.put("device", new Integer[]{0x0, 0x100, 0x200, 0x300});
        TARGET_CODES.put("brand", new Integer[]{0x0, 0x400, 0x800, 0xc00});
    }

}

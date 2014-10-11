package com.xiuman.xingduoduo.testdata;

import java.util.ArrayList;
import java.util.List;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.Ad;
import com.xiuman.xingduoduo.model.AdBBS;
import com.xiuman.xingduoduo.model.BBSPlate;

/**
 * 
 * 名称：Test.java 描述：测试数据
 * 
 * @author danding
 * @version 2014-6-12
 */
public class Test {
	/**
	 * 
	 * 描述：添加广告的测试数据
	 * 
	 * @return
	 */
	public static List<Ad> addTestAd() {
		List<Ad> ads = new ArrayList<Ad>();
		String ad_poster_url = "";
		String ad_url = "";
		String ad_content = "";

		Ad ad = new Ad(ad_poster_url, ad_url, ad_content);

		ads.add(ad);
		ads.add(ad);
		ads.add(ad);
		ads.add(ad);
		ads.add(ad);
		ads.add(ad);
		return ads;

	}

	/**
	 * 
	 * 描述：添加广告的测试数据
	 * 
	 * @return
	 */
	public static List<AdBBS> addTestCommunicationAd() {
		List<AdBBS> ads = new ArrayList<AdBBS>();
		String ad_poster_url = "";
		String ad_url = "";
		String ad_content = "七夕七重惠，和开发速度很快就会";

		AdBBS ad = new AdBBS(ad_poster_url, ad_url, ad_content);

		ads.add(ad);
		ads.add(ad);
		ads.add(ad);
		ads.add(ad);
		ads.add(ad);
		ads.add(ad);
		return ads;

	}

	/**
	 * 
	 * 描述：获取最近搜索的关键词
	 * 
	 * @return
	 */
	public static List<String> getTestSearchRencently() {
		List<String> recently_keywords = new ArrayList<String>();

		recently_keywords.add("避孕套");
		recently_keywords.add("延时");
		recently_keywords.add("振动棒");
		recently_keywords.add("制服诱惑");
		recently_keywords.add("比基尼");
		recently_keywords.add("杜蕾斯");
		recently_keywords.add("飞机杯");
		recently_keywords.add("蕾丝");
		return recently_keywords;
	}

	/**
	 * 
	 * 描述：获取热门搜索的关键词
	 * 
	 * @return
	 */
	public static List<String> getTestSearchHot() {
		List<String> hot_keywords = new ArrayList<String>();

		hot_keywords.add("避孕套");
		hot_keywords.add("延时");
		hot_keywords.add("振动棒");
		hot_keywords.add("制服诱惑");
		hot_keywords.add("比基尼");
		hot_keywords.add("杜蕾斯");
		hot_keywords.add("飞机杯");
		hot_keywords.add("蕾丝");
		return hot_keywords;
	}


	/**
	 * 
	 * @描述：获取论坛板块
	 * @date：2014-7-29
	 * @return
	 */
	public static ArrayList<BBSPlate> getBBSPlates() {
		ArrayList<BBSPlate> plates = new ArrayList<BBSPlate>();
		String plate_id_1 = "1";
		String palte_name_1 = "涨姿势";
		String plate_description_1 = "性爱姿势，两性健康，相爱知识，性爱误区，性爱心理";
		String plate_tag_1 = "hot";
		int plate_icon_1 = R.drawable.ic_plate_1_zhangzishi;
		BBSPlate palte_1 = new BBSPlate(plate_id_1, palte_name_1,
				plate_description_1, plate_tag_1, plate_icon_1);

		String plate_id_2 = "2";
		String palte_name_2 = "情趣说";
		String plate_description_2 = "男欢女爱、两性恋爱等话题";
		String plate_tag_2 = "";
		int plate_icon_2 = R.drawable.ic_plate_2_xingqushuo;
		BBSPlate palte_2 = new BBSPlate(plate_id_2, palte_name_2,
				plate_description_2, plate_tag_2, plate_icon_2);

		String plate_id_3 = "3";
		String palte_name_3 = "爆照区";
		String plate_description_3 = "除了果照，我们都要！";
		String plate_tag_3 = "hot";
		int plate_icon_3 = R.drawable.ic_plate_3_baozhao;
		BBSPlate palte_3 = new BBSPlate(plate_id_3, palte_name_3,
				plate_description_3, plate_tag_3, plate_icon_3);

		String plate_id_4 = "4";
		String palte_name_4 = "内涵段子";
		String plate_description_4 = "节操是什么？能吃吗？";
		String plate_tag_4 = "";
		int plate_icon_4 = R.drawable.ic_plate_4_neihan;
		BBSPlate palte_4 = new BBSPlate(plate_id_4, palte_name_4,
				plate_description_4, plate_tag_4, plate_icon_4);

		String plate_id_5 = "5";
		String palte_name_5 = "攻德无量、万受无疆";
		String plate_description_5 = "挑战禁忌，同性才是真爱！";
		String plate_tag_5 = "";
		int plate_icon_5 = R.drawable.ic_plate_5_gay;
		BBSPlate palte_5 = new BBSPlate(plate_id_5, palte_name_5,
				plate_description_5, plate_tag_5, plate_icon_5);

		String plate_id_6 = "6";
		String palte_name_6 = "晒出自性";
		String plate_description_6 = "在性多多购买商品的用户，通过晒单，晒出你的自信和性感吧！";
		String plate_tag_6 = "hot";
		int plate_icon_6 = R.drawable.ic_plate_6_shaidan;
		BBSPlate palte_6 = new BBSPlate(plate_id_6, palte_name_6,
				plate_description_6, plate_tag_6, plate_icon_6);

		String plate_id_7 = "7";
		String palte_name_7 = "我们去哪儿";
		String plate_description_7 = "哪里的麻辣烫最好吃？吃完麻辣烫去哪里？你知道吗？";
		String plate_tag_7 = "";
		int plate_icon_7 = R.drawable.ic_plate_7_way;
		BBSPlate palte_7 = new BBSPlate(plate_id_7, palte_name_7,
				plate_description_7, plate_tag_7, plate_icon_7);

		String plate_id_8 = "8";
		String palte_name_8 = "白富美，高大上";
		String plate_description_8 = "不好！我的24k钛合金狗眼被亮瞎了！";
		String plate_tag_8 = "new";
		int plate_icon_8 = R.drawable.ic_plate_8_baifumei;
		BBSPlate palte_8 = new BBSPlate(plate_id_8, palte_name_8,
				plate_description_8, plate_tag_8, plate_icon_8);

		String plate_id_9 = "9";
		String palte_name_9 = "美女游戏";
		String plate_description_9 = "精彩美女游戏专区";
		String plate_tag_9 = "new";
		int plate_icon_9 = R.drawable.ic_plate_10_youxi;
		BBSPlate palte_9 = new BBSPlate(plate_id_9, palte_name_9,
				plate_description_9, plate_tag_9, plate_icon_9);

		String plate_id_11 = "11";
		String palte_name_11 = "二次元狂热";
		String plate_description_11 = "一入宅腐深似海，从此节操是路人。";
		String plate_tag_11 = "new";
		int plate_icon_11 = R.drawable.ic_plate_11_erciyuan;
		BBSPlate palte_11 = new BBSPlate(plate_id_11, palte_name_11,
				plate_description_11, plate_tag_11, plate_icon_11);
		String plate_id_12 = "12";
		String palte_name_12 = "不能说的秘密";
		String plate_description_12 = "快来吐槽、爆尿和八卦吧，这是一个大树洞哦！";
		String plate_tag_12 = "new";
		int plate_icon_12 = R.drawable.ic_plate_12_mimi;
		BBSPlate palte_12 = new BBSPlate(plate_id_12, palte_name_12,
				plate_description_12, plate_tag_12, plate_icon_12);
		plates.add(palte_1);
		plates.add(palte_2);
		plates.add(palte_3);
		plates.add(palte_4);
		plates.add(palte_5);
		plates.add(palte_6);
		plates.add(palte_7);
		plates.add(palte_8);
//		plates.add(palte_9);
		plates.add(palte_11);
		plates.add(palte_12);

		return plates;

	}


}

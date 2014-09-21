package com.xiuman.xingduoduo.testdata;

import java.util.ArrayList;
import java.util.List;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.Ad;
import com.xiuman.xingduoduo.model.AdBBS;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.model.GoodsCart;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.model.GoodsTwo;
import com.xiuman.xingduoduo.model.PostReply;
import com.xiuman.xingduoduo.model.PostStarter;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.model.UserAddress;

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
		recently_keywords.add("女性用具");
		recently_keywords.add("男性用具");
		recently_keywords.add("情趣内衣");
		recently_keywords.add("振动棒");
		recently_keywords.add("性玩偶");
		recently_keywords.add("润滑剂");
		recently_keywords.add("阴臀倒模");
		recently_keywords.add("G点后庭");
		recently_keywords.add("阴蒂刺激");
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
		hot_keywords.add("女性用具");
		hot_keywords.add("男性用具");
		hot_keywords.add("情趣内衣");
		hot_keywords.add("振动棒");
		hot_keywords.add("性玩偶");
		hot_keywords.add("润滑剂");
		hot_keywords.add("阴臀倒模");
		hot_keywords.add("G点后庭");
		hot_keywords.add("阴蒂刺激");
		return hot_keywords;
	}


	/**
	 * 收货地址
	 * 
	 * @描述：
	 * @return 2014-8-12
	 */
	public static UserAddress getTestUserAddress() {
		String id = "1213121";
		String user_receipt_address = "福建厦门思明区";
		String user_receipt_address_detail = "软件园二期观日路28号之三403";
		String user_receipt_name = "种生祥";
		String user_receipt_phone = "12345678866";
		String user_receipt_ems = "361021";
		UserAddress user_address = new UserAddress(id,user_receipt_address,
				user_receipt_address_detail, user_receipt_name,
				user_receipt_phone, user_receipt_ems);
		return user_address;
	}


	/**
	 * 
	 * @描述：获取测试商品详情图
	 * @date：2014-6-27
	 * @return
	 */
	public static ArrayList<String> getGoodsImgUrls() {
		ArrayList<String> img_urls = new ArrayList<String>();
		img_urls.add("http://h.hiphotos.baidu.com/image/pic/item/0ff41bd5ad6eddc48ecc77e63bdbb6fd52663380.jpg");
		img_urls.add("http://f.hiphotos.baidu.com/image/pic/item/b8389b504fc2d562e8a4f2eee51190ef76c66c00.jpg");
		img_urls.add("http://e.hiphotos.baidu.com/image/pic/item/6d81800a19d8bc3efd3a6c39808ba61ea8d34516.jpg");
		img_urls.add("http://e.hiphotos.baidu.com/image/pic/item/a5c27d1ed21b0ef46f78f3addfc451da80cb3eb4.jpg");
		img_urls.add("http://f.hiphotos.baidu.com/image/pic/item/7acb0a46f21fbe098eb2a8e769600c338644ad51.jpg");
		img_urls.add("http://a.hiphotos.baidu.com/image/pic/item/e7cd7b899e510fb34790de93db33c895d1430c2c.jpg");
		img_urls.add("http://g.hiphotos.baidu.com/image/pic/item/d62a6059252dd42aa60af6aa013b5bb5c9eab8b4.jpg");
		return img_urls;
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
		String plate_description_1 = "性爱姿势，两性健康，相爱只是，性爱误区，性爱心理";
		String plate_tag_1 = "hot";
		int plate_icon_1 = R.drawable.ic_plate_1_zhangzishi;
		BBSPlate palte_1 = new BBSPlate(plate_id_1, palte_name_1,
				plate_description_1, plate_tag_1, plate_icon_1);

		String plate_id_2 = "2";
		String palte_name_2 = "性趣说";
		String plate_description_2 = "男欢女爱的话题，两性恋爱等话题";
		String plate_tag_2 = "";
		int plate_icon_2 = R.drawable.ic_plate_2_xingqushuo;
		BBSPlate palte_2 = new BBSPlate(plate_id_2, palte_name_2,
				plate_description_2, plate_tag_2, plate_icon_2);

		String plate_id_3 = "3";
		String palte_name_3 = "爆照区";
		String plate_description_3 = "自拍区，自拍美图，自拍重口味";
		String plate_tag_3 = "hot";
		int plate_icon_3 = R.drawable.ic_plate_3_baozhao;
		BBSPlate palte_3 = new BBSPlate(plate_id_3, palte_name_3,
				plate_description_3, plate_tag_3, plate_icon_3);

		String plate_id_4 = "4";
		String palte_name_4 = "内涵段子";
		String plate_description_4 = "黄色笑话，内涵漫画段子";
		String plate_tag_4 = "";
		int plate_icon_4 = R.drawable.ic_plate_4_neihan;
		BBSPlate palte_4 = new BBSPlate(plate_id_4, palte_name_4,
				plate_description_4, plate_tag_4, plate_icon_4);

		String plate_id_5 = "5";
		String palte_name_5 = "Gay Park";
		String plate_description_5 = "同性恋的话题";
		String plate_tag_5 = "";
		int plate_icon_5 = R.drawable.ic_plate_5_gay;
		BBSPlate palte_5 = new BBSPlate(plate_id_5, palte_name_5,
				plate_description_5, plate_tag_5, plate_icon_5);

		String plate_id_6 = "6";
		String palte_name_6 = "晒出自性";
		String plate_description_6 = "在性多多买了商品的用户，通过晒单，晒出自己的自信，性感，可以在这获得积分";
		String plate_tag_6 = "hot";
		int plate_icon_6 = R.drawable.ic_plate_6_shaidan;
		BBSPlate palte_6 = new BBSPlate(plate_id_6, palte_name_6,
				plate_description_6, plate_tag_6, plate_icon_6);

		String plate_id_7 = "7";
		String palte_name_7 = "走走停停，一直在路上";
		String plate_description_7 = "吃喝玩乐的主题，情侣分享情侣主题酒店，情侣约会等地";
		String plate_tag_7 = "";
		int plate_icon_7 = R.drawable.ic_plate_7_way;
		BBSPlate palte_7 = new BBSPlate(plate_id_7, palte_name_7,
				plate_description_7, plate_tag_7, plate_icon_7);

		String plate_id_8 = "8";
		String palte_name_8 = "白富美，高大上";
		String plate_description_8 = "美空网里的一些美女分享等";
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

		plates.add(palte_1);
		plates.add(palte_2);
		plates.add(palte_3);
		plates.add(palte_4);
		plates.add(palte_5);
		plates.add(palte_6);
		plates.add(palte_7);
		plates.add(palte_8);
		plates.add(palte_9);

		return plates;

	}


	/**
	 * @描述：测试收货地址列表
	 * @return
	 * 2014-8-20
	 */
	public static ArrayList<UserAddress> getTestAddresses(){
		ArrayList<UserAddress> addresses = new ArrayList<UserAddress>();
		
		addresses.add(Test.getTestUserAddress());
		addresses.add(Test.getTestUserAddress());
		addresses.add(Test.getTestUserAddress());
		addresses.add(Test.getTestUserAddress());
		addresses.add(Test.getTestUserAddress());
		addresses.add(Test.getTestUserAddress());
		return addresses;
	}
}

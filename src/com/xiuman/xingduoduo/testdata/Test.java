package com.xiuman.xingduoduo.testdata;

import java.util.ArrayList;
import java.util.List;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.Ad;
import com.xiuman.xingduoduo.model.AdBBS;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.model.GoodsCart;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.model.GoodsStandard;
import com.xiuman.xingduoduo.model.GoodsTwo;
import com.xiuman.xingduoduo.model.Order;
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
	 * 
	 * @描述：获取测试用户信息
	 * @date：2014-6-20
	 * @return
	 */
	public static User getTestUser() {
		User user;
		String userId = "5454634631321185";
		String rankNmae = "普通会员";
		String userName = "hqucsx";
		String email = "598281865@qq.com";
		String createDate = "2014-08-12 11:30:28";
		double preferntial = 7.0d;

		user = new User(userName, userId, email, createDate, preferntial,
				rankNmae);

		return user;
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
	 * @描述：获取测试商品数据
	 * @date：2014-6-25
	 * @return
	 */
	public static ArrayList<GoodsOne> getGoods() {
		ArrayList<GoodsOne> goods = new ArrayList<GoodsOne>();
		String goods_id = "123456789";
		String goods_name = "霏慕性感薄纱露乳睡裙【新品尝鲜】";
		String ThumbnailGoodsImagePath = "";
		String imagePath = "";
		String SmallGoodsImagePath = "";
		double goods_price = 25.00d;
		int goods_sales = 1532;
		String Value0 = "";
		String Value1 = "";
		String goodsSn = "";

		GoodsOne goodsone = new GoodsOne(goods_id, ThumbnailGoodsImagePath,
				imagePath, SmallGoodsImagePath, goods_name, goods_price,
				goods_sales, goodsSn, Value0, Value1);

		goods.add(goodsone);
		goods.add(goodsone);
		goods.add(goodsone);
		goods.add(goodsone);
		goods.add(goodsone);
		goods.add(goodsone);
		goods.add(goodsone);
		goods.add(goodsone);
		goods.add(goodsone);
		goods.add(goodsone);

		return goods;
	}

	/**
	 * 
	 * @描述：获取测试商品详情
	 * @date：2014-6-26
	 * @return
	 */
	public static GoodsTwo getGoodsTwo() {
		String class_id = "13131313";
		String goods_id = "123456789";
		String goods_name = "霏慕性感薄纱露乳睡裙【新品尝鲜】";
		String goods_description = "霏慕性感薄纱露乳睡裙【新品尝鲜】";
		String goods_time = "2014-07-31";

		List<String> goods_img_urls = new ArrayList<String>();
		goods_img_urls.add((R.drawable.test_1) + "");
		goods_img_urls.add((R.drawable.test_2) + "");
		goods_img_urls.add((R.drawable.test_3) + "");
		goods_img_urls.add((R.drawable.test_4) + "");

		double goods_cost_price = 35.00d;
		double goods_price = 25.00d;

		// ArrayList<GoodsStandard> goods_standard_params = new
		// ArrayList<GoodsStandard>();
		// goods_standard_params.add(new GoodsStandard("黑色挑逗的精灵(L)", 25.00d));
		// goods_standard_params.add(new GoodsStandard("黑色(XL)", 28.00d));
		// goods_standard_params.add(new GoodsStandard("黑色性爱你得有一套(XXL)",
		// 30.00d));
		// goods_standard_params.add(new GoodsStandard("粉蓝色(XXL)", 35.00d));
		// goods_standard_params.add(new GoodsStandard("QQ锁灵环粉色(色色猫)", 40.00d));
		//
		// int goods_sale = 1500;
		// String goods_params = "品牌:其他品牌\n面料分类:蕾丝\n款式:肚兜\n货号:NY8572003";
		//
		// GoodsTwo goods_two = new GoodsTwo(class_id, goods_id, goods_time,
		// goods_name, goods_description, goods_img_urls,
		// goods_cost_price, goods_price, goods_standard_params,
		// goods_sale, goods_params);

		return null;
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
	 * @描述：获取测试的购物车产品
	 * @date：2014-7-22
	 * @return
	 */
	public static ArrayList<GoodsCart> getTestGoodsCart() {
		ArrayList<GoodsCart> cart_goods = new ArrayList<GoodsCart>();

//		String goods_id = "123456789";
//		String goods_name = "霏慕性感薄纱露乳睡裙发送到凤凰酒店开设分行开立jfdkj";
//		String goods_price = "156.00";
//		int goods_number = 1;
//		String goods_size = "颜色:蓝色;尺码:XL";
//		String goods_poster = "";
//		String goods_total = "156.00";
//		GoodsCart goods_1 = new GoodsCart(goods_id, goods_name, goods_price,
//				goods_poster, goods_size, goods_number, goods_total);
//
//		String goods_id_2 = "987654321";
//		String goods_name_2 = "杉姿2014夏装新款";
//		String goods_price_2 = "96.05";
//		int goods_number_2 = 2;
//		String goods_size_2 = "颜色:粉红色;尺码:XXL";
//		String goods_poster_2 = "";
//		String goods_total_2 = "192.10";
//		GoodsCart goods_2 = new GoodsCart(goods_id_2, goods_name_2,
//				goods_price_2, goods_poster_2, goods_size_2, goods_number_2,
//				goods_total_2);
//
//		String goods_id_3 = "123654789";
//		String goods_name_3 = "串珠蕾丝镶钻黑色连衣裙心霓儿同款菁菁秀韩国名媛礼服裙夏07046";
//		String goods_price_3 = "488.00";
//		int goods_number_3 = 1;
//		String goods_size_3 = "颜色:黑色;尺码:XXL";
//		String goods_poster_3 = "";
//		String goods_total_3 = "488.00";
//		GoodsCart goods_3 = new GoodsCart(goods_id_3, goods_name_3,
//				goods_price_3, goods_poster_3, goods_size_3, goods_number_3,
//				goods_total_3);
//
//		cart_goods.add(goods_1);
//		cart_goods.add(goods_2);
//		cart_goods.add(goods_3);

		return cart_goods;

	}

	/**
	 * 
	 * @描述：获取论坛板块
	 * @date：2014-7-29
	 * @return
	 */
	public static ArrayList<BBSPlate> getBBSPlates() {
		ArrayList<BBSPlate> plates = new ArrayList<BBSPlate>();
		String plate_id_1 = "1001";
		String palte_name_1 = "涨姿势";
		String plate_description_1 = "性爱姿势，两性健康，相爱只是，性爱误区，性爱心理";
		String plate_tag_1 = "hot";
		int plate_icon_1 = R.drawable.ic_plate_1_zhangzishi;
		BBSPlate palte_1 = new BBSPlate(plate_id_1, palte_name_1,
				plate_description_1, plate_tag_1, plate_icon_1);

		String plate_id_2 = "1002";
		String palte_name_2 = "性趣说";
		String plate_description_2 = "男欢女爱的话题，两性恋爱等话题";
		String plate_tag_2 = "";
		int plate_icon_2 = R.drawable.ic_plate_2_xingqushuo;
		BBSPlate palte_2 = new BBSPlate(plate_id_2, palte_name_2,
				plate_description_2, plate_tag_2, plate_icon_2);

		String plate_id_3 = "1003";
		String palte_name_3 = "爆照区";
		String plate_description_3 = "自拍区，自拍美图，自拍重口味";
		String plate_tag_3 = "hot";
		int plate_icon_3 = R.drawable.ic_plate_3_baozhao;
		BBSPlate palte_3 = new BBSPlate(plate_id_3, palte_name_3,
				plate_description_3, plate_tag_3, plate_icon_3);

		String plate_id_4 = "1004";
		String palte_name_4 = "内涵段子";
		String plate_description_4 = "黄色笑话，内涵漫画段子";
		String plate_tag_4 = "";
		int plate_icon_4 = R.drawable.ic_plate_4_neihan;
		BBSPlate palte_4 = new BBSPlate(plate_id_4, palte_name_4,
				plate_description_4, plate_tag_4, plate_icon_4);

		String plate_id_5 = "1005";
		String palte_name_5 = "Gay Park";
		String plate_description_5 = "同性恋的话题";
		String plate_tag_5 = "";
		int plate_icon_5 = R.drawable.ic_plate_5_gay;
		BBSPlate palte_5 = new BBSPlate(plate_id_5, palte_name_5,
				plate_description_5, plate_tag_5, plate_icon_5);

		String plate_id_6 = "1006";
		String palte_name_6 = "晒出自性";
		String plate_description_6 = "在性多多买了商品的用户，通过晒单，晒出自己的自信，性感，可以在这获得积分";
		String plate_tag_6 = "hot";
		int plate_icon_6 = R.drawable.ic_plate_6_shaidan;
		BBSPlate palte_6 = new BBSPlate(plate_id_6, palte_name_6,
				plate_description_6, plate_tag_6, plate_icon_6);

		String plate_id_7 = "1007";
		String palte_name_7 = "走走停停，一直在路上";
		String plate_description_7 = "吃喝玩乐的主题，情侣分享情侣主题酒店，情侣约会等地";
		String plate_tag_7 = "";
		int plate_icon_7 = R.drawable.ic_plate_7_way;
		BBSPlate palte_7 = new BBSPlate(plate_id_7, palte_name_7,
				plate_description_7, plate_tag_7, plate_icon_7);

		String plate_id_8 = "1008";
		String palte_name_8 = "白富美，高大上";
		String plate_description_8 = "美空网里的一些美女分享等";
		String plate_tag_8 = "new";
		int plate_icon_8 = R.drawable.ic_plate_8_baifumei;
		BBSPlate palte_8 = new BBSPlate(plate_id_8, palte_name_8,
				plate_description_8, plate_tag_8, plate_icon_8);

		String plate_id_9 = "1009";
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
	 * 测试用的订单数据
	 * 
	 * @return
	 */
	public static ArrayList<Order> getTestOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();
		String order_state = "未付款";
		String order_id = "123145467467631";
		String order_time = "2014-08-06 10:22:56";
		String order_user_id = "123456789";
		UserAddress order_user_address = getTestUserAddress();
		String order_pay_way = "支付宝";
		ArrayList<GoodsCart> goods_list = getTestGoodsCart();
		String order_trans_pay = "12.00";// 包邮
		String order_preferential = "满100包邮";
		double order_total = 1;
		String order_user_words = "请尽快安排发货";

		Order order = new Order(order_state, order_id, order_time,
				order_user_id, order_user_address, order_pay_way, goods_list,
				order_trans_pay, order_preferential, order_total,
				order_user_words);
		orders.add(order);

		String order_state1 = "已取消";
		String order_id1 = "123145467123456";
		String order_time1 = "2014-08-06 10:22:56";
		String order_user_id1 = "123456789";
		UserAddress order_user_address1 = getTestUserAddress();
		String order_pay_way1 = "财付通";
		ArrayList<GoodsCart> goods_list1 = getTestGoodsCart();
		String order_trans_pay1 = "12.00";// 包邮
		String order_preferential1 = "满120包邮";
		double order_total1 = 1;
		String order_user_words1 = "请尽快安排发货，快点哦！";

		Order order1 = new Order(order_state1, order_id1, order_time1,
				order_user_id1, order_user_address1, order_pay_way1,
				goods_list1, order_trans_pay1, order_preferential1,
				order_total1, order_user_words1);

		orders.add(order1);

		return orders;
	}

	/**
	 * 测试帖子
	 * 
	 * @return
	 */
	public static ArrayList<PostStarter> getTestPost() {
		ArrayList<PostStarter> posts = new ArrayList<PostStarter>();
		String post_id = "123213213";
		String post_title = "天涯海角三日游";
		String post_content = "求过哦，";
		String post_time = "2014-08-11 10:28";
		String post_style = "匿名";
		String post_tag = "精华";
		User post_user = getTestUser();
		int post_reply = 128;
		ArrayList<String> post_imgs = getGoodsImgUrls();

		PostStarter post = new PostStarter(post_id, post_time, post_title,
				post_content, post_style, post_tag, post_user, post_reply,
				post_imgs);
		// 置顶帖子
		String post_id1 = "123213213";
		String post_title1 = "天涯海角三日游";
		String post_content1 = "求过哦，";
		String post_time1 = "2014-08-11 10:28";
		String post_style1 = "匿名";
		String post_tag1 = "置顶";
		User post_user1 = getTestUser();
		int post_reply1 = 128;
		ArrayList<String> post_imgs1 = getGoodsImgUrls();

		PostStarter post1 = new PostStarter(post_id1, post_time1, post_title1,
				post_content1, post_style1, post_tag1, post_user1, post_reply1,
				post_imgs1);

		posts.add(post);
		posts.add(post);
		posts.add(post);
		posts.add(post);
		posts.add(post);
		posts.add(post);
		posts.add(post);

		posts.add(post1);
		posts.add(post1);
		posts.add(post1);
		posts.add(post1);
		posts.add(post1);
		return posts;
	}

	/**
	 * @描述：测试回复帖
	 * @return 2014-8-16
	 */
	public static ArrayList<PostReply> getTestRelpy() {
		ArrayList<PostReply> replys = new ArrayList<PostReply>();
		String reply_id = "123213213";
		String reply_content = "哎呦不错哦";
		String reply_time = "2014-08-16 10:28";
		User reply_user = getTestUser();
		boolean isStarter = true;
		PostReply reply = new PostReply(reply_id, reply_content, reply_time,
				reply_user,isStarter);

		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);
		replys.add(reply);

		return replys;
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

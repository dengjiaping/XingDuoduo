package com.xiuman.xingduoduo.testdata;

import java.util.ArrayList;

import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.model.SearchKeyWord;

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
	 * 描述：获取热门搜索的关键词
	 * 
	 * @return
	 */
	public static ArrayList<SearchKeyWord> getTestSearchHot() {
		ArrayList<SearchKeyWord> hot_keywords = new ArrayList<SearchKeyWord>();

		hot_keywords.add(new SearchKeyWord("避孕套"));
		hot_keywords.add(new SearchKeyWord("延时"));
		hot_keywords.add(new SearchKeyWord("振动棒"));
		hot_keywords.add(new SearchKeyWord("制服诱惑"));
		hot_keywords.add(new SearchKeyWord("比基尼"));
		hot_keywords.add(new SearchKeyWord("杜蕾斯"));
		hot_keywords.add(new SearchKeyWord("飞机杯"));
		hot_keywords.add(new SearchKeyWord("蕾丝"));
		return hot_keywords;
	}


	/**
	 * 
	 * @描述：获取论坛板块
	 * @date：2014-7-29
	 * @return
	 *
	 */
	public static ArrayList<BBSPlate> getBBSPlates() {
		ArrayList<BBSPlate> plates = new ArrayList<BBSPlate>();
		String id = "1";
		String title = "涨姿势";
		String description = "性爱姿势，两性健康，相爱知识，性爱误区，性爱心理";
		int isShow = 0;
		String logo = "/bbs3/r/cms/www/blue/bbs_forum/img/top/1.png";
		BBSPlate palte_1 = new BBSPlate(id, title, description, isShow, logo);


		String id2 = "3";
		String title2 = "爆照区";
		String description2 = "除了果照，我们都要！";
		int isShow2 = 0;
		String logo2 = "/bbs3/r/cms/www/blue/bbs_forum/img/top/3.png";
		BBSPlate palte_2 = new BBSPlate(id2, title2, description2, isShow2, logo2);




		String id3 = "8";
		String title3 = "白富美，高大上";
		String description3 = "不好！我的24k钛合金狗眼被亮瞎了！";
		int isShow3 = 0;
		String logo3 ="/bbs3/r/cms/www/blue/bbs_forum/img/top/8.png";
		BBSPlate palte_3 = new BBSPlate(id3, title3, description3, isShow3, logo3);
		plates.add(palte_2);
		plates.add(palte_3);
		plates.add(palte_1);

		return plates;

	}

}

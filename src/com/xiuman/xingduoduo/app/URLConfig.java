package com.xiuman.xingduoduo.app;

/**
 * 
 * @名称：URLConfig.java
 * @描述：应用请求的网址信息
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class URLConfig {
	// 内网图片ip
	public static final String PRIVATE_IMG_IP = "http://192.168.16.57:8080";
	// bbs内网测试地址
	public static final String PRIVATE_IP = "http://192.168.16.57:8080/bbs/app/";
	// bbs板块帖子列表
	public static final String FORUM_LIST_IP = "appTopForumList.jhtml?forumId=";
	// bbs 置顶帖
	public static final String FORUM_TOP_LIST_IP = "appUpTopForumList.jhtml?";
	// 帖子详情回复
	public static final String FORUM_REPLY_LIST_IP = "appPostInfo.jhtml?topicId=";
	// 帖子回复
	public static final String POST_REPLY__IP = "appPostReply.jhtml?";

	// 接口地址前缀 121.199.57.38
	public static final String BASE_IP = "http://121.199.57.38/shopxx/shop";
	// 图片地址前缀
	public static final String IMG_IP = "http://121.199.57.38/shopxx";
	// 图文详情
	public static final String IMG_TXT_IP = "http://121.199.57.38/shopxx/shop/goods!introduction.action?goodId=";
	// 注册后缀
	public static final String REGISTER = "/member!Registara.action?";
	// 获取个人信息
	public static final String USERINFO = "/member!UserList.action?";
	// 用户登录
	public static final String USER_LOGION = "/member!LonginVer.action?";
	// 修改密码
	public static final String UPDATE_PSW = "/member!updateUser.action?";
	// 收货地址列表
	public static final String USER_ADDRESSES = "/member!MemberRecive.action?";
	// 添加收货地址
	public static final String ADD_ADDRESS = "/member!addMemberRecive.action?";
	// 修改收货地址
	public static final String UPDATE_ADDRESS = "/member!UpdateMemberRecive.action?";
	// 删除收货地址
	public static final String DELETE_ADDRESS = "/member!deleteMemberRecive.action?";

	// 添加收藏
	public static final String ADD_COLLECTION = "/member!addFavoriteApp.action?";
	// 删除收藏
	public static final String DELETE_COLLECTION = "/member!deleteFavoriteApp.action?";
	// 收藏列表
	public static final String COLLECTIONS = "/member!listFavoriteApp.action?";

	/*--------------------------------订单---------------------------------------*/
	// 生成订单
	public static final String CREATE_ORDER = "/member!orderseAppsave.action";
	// 删除订单
	public static final String DELETE_ORDER = "/member!DeleteOrderAppList.action?";
	// 订单列表
	public static final String ORDER_HISTORY = "/member!OrderListApp.action?";
	//订单详情
	public static final String ORDER_INFO = "/member!OrderListDetail.action?";
	//确认收货
	public static final String ORDER_TAKER = "/member!ConfirmReceipt.action?";
	//平价商品
	public static final String DISCUSS_GOODS = "/comment!SaveReview.action?";
	
	// 获取分类
	public static final String CLASSIFY = "/goods!getGoodsCategoryApp.action";
	// 搜索
	public static final String SEARCH = "/goods!getSerachGoodsApp.action?";

	/*--------------------商品分类排序接口-------------------*/
	// 综合排序
	public static final String SORT_ZONGHE = "/goods!getGoodsAppByCompre.action?";
	// 销量排序
	public static final String SORT_XIAOLIANG = "/goods!getGoodsAppByVolume.action?";
	// 时间排序
	public static final String SORT_SHIJIAN = "/goods!getGoodsAppBycreateDate.action?";
	// 价格排序
	public static final String SORT_JIAGE = "/goods!getGoodsAppBymarketPrice.action?";

	// 商品详情
	public static final String GOODS_INFO = "/goods!getSerachGoodsDetail.action?";
	// 加入购物车
	public static final String ADD_GOODS_CART = "/member!saveCartItem.action?";
	// 购物车列表
	public static final String GOODS_CART = "/member!showCartItem.action?";
	// 移除购物车
	public static final String DELETE_GOODS_CART = "/member!deleteCareItem.action?";
	// 修改购物车数量
	public static final String UPDATE_GOODS_NUMBER = "/member!updateCartItem.action?";

	// 商品评价列表
	public static final String GOODS_DISCUSS_LIST = "/comment!ListCommentApp.action?";

	/*----------------------------------支付宝相关---------------------------------------*/
	// 支付宝支付接口
	public static final String ALIPAY_URL = "/member!payOrderStatus.action?";
	//传递支付结果到服务端
	public static final String PAY_STATUS_CODE = "/member!payorderApp.action?";
	
	//商城首页板块
	public static final String CENTER_HOME_PLATE = "/goods!getGoodsAppByIt.action?";
	
}

package com.xiuman.xingduoduo.app;

/**
 * 
 * @名称：AppConfig.java
 * @描述：常量信息的定义
 * @author danding
 * @version 2014-6-12
 */
public class AppConfig {

	// bbs参数------------------------------------------
	// 帖子列表返回
	public final static int BBS_POST_BACK = 9001;
	// 帖子列表返回
	public final static int BBS_TOP_POST_BACK = 9002;
	// 帖子回复列表返回
	public final static int BBS_REPLY_POST_BACK = 9003;
	// 帖子回复返回
	public final static int BBS_REPLY_SEND_BACK = 9004;

	// 购物车Fragment Activity请求码与返回码-----------------------------
	public final static int REQUEST_CODE = 1;
	// 返回码1成功刷新
	public final static int RESULT_CODE_OK = 2;
	// 返回码2不刷新
	public final static int RESULT_CODE_CANCEL = 3;

	// 收货地址请求码与返回码----------------------------------------------------
	// 新增收货地址
	public final static int RESULT_CODE_ADD_ADDRESS = 4;
	// 修改（删除）收货地址
	public final static int RESULT_CODE_UPDATE_ADDRESS = 5;

	// 提交订单界面请求返回收货地址----------------------------------------------
	public final static int ORDER_SUBMIT_ADDRESS = 6;

	/*---------------------网络连接--------------------*/
	/*** 成功 */
	public final static int NET_SUCCED = 0;
	/*** 无数据 */
	public final static int NET_NOTHING = 1;
	/*** 无网络 */
	public final static int NET_ERROR_NOTNET = 2;
	/*** 连接出错 */
	public final static int NET_ERROR_CONN = 3;
	/*** 数据出错 */
	public final static int NET_ERROR_NODATA = 4;
	/*** 地址出错 */
	public final static int NET_ERROR_URL = 5;

	/*-----------------搜索界面--------------*/
	// 添加搜索历史
	public static final int SEARCH_ADD = 1001;
	// 删除某个搜索历史成功
	public static final int SEARCH_DELETE = 1002;

	/*-----------------更换用户头像----------*/
	// 打开相机
	public static final int OPEN_CAMERA = 2001;
	// 获取相机数据
	public static final int CUT_CAMERA_RESULT = 2002;
	// 获取相册
	public static final int CUT_GALLERY_RESULT = 2003;

	// 用户收货地址更新
	public static final int UPDATE_USER_ADDRESS = 3001;
	// 用户信息更新
	public static final int UPDATE_USER_INFO = 3002;

	// 倒计时 时间（60秒）
	public static final long SMS_COUNTDOWN = 60000L;

	/*----------------------购物车更新------------------------*/
	// 添加商品到购物车
	public static final int ADD2Cart = 40004;
	// 更新购物车数据(结算的总价)
	public static final int UPDATE_SHOPPING_CART = 40001;
	// 更新购物车选择(结算)
	public static final int UPDATE_SHOPPING_CART_SELECT = 4002;
	// 更新购物车商品（列表）
	public static final int UPDATE_SHOPPING_CART_GOODS = 4003;
	// 请求删除购物车商品(请求接口)
	public static final int UPDATE_DELETE_SHOPPING_CART_GOODS = 4005;
	// 请求修改购物车商品数量
	public static final int UPDATE_SHOPPING_CART_GOODS_NUMBER = 4006;

	/*--------------------------我的订单历史--------------------*/
	// 生成订单
	public static final int CREATE_ORDER = 5003;
	// 订单支付宝签名
	public static final int ALIPAY_BACK = 5005;
	// 订单历史（adapter）
	public static final int UPDATE_ORDER_HISTORY = 5001;
	// 删除订单(请求)
	public static final int UPDATE_ORDER = 5002;

	/*--------------------------我的收藏列表--------------------*/
	// 删除收藏(接口)
	public static final int DELETE_COLLECTION = 6001;
	// 删除收藏adapter
	public static final int DELETE_COLLECTION_ADAPTER = 6003;
	// 添加收藏
	public static final int ADD_COLLECTION = 6002;

	/*--------------------------用户信息------------------------*/
	// 获取用户信息成功
	public static final int SUCCESS_USER_INFO = 101;

	/*--------------------------评价商品-------------------------*/
	// 评价商品
	public static final int GISCUSS_GOODS = 201;

	/*-------------------------收货地址----------------------------*/
	// 删除收货地址
	public static final int GET_DELETE_ADDRESS = 301;
	// 修改收货地址
	public static final int GET_UPDATE_ADDRESS = 302;

	/*---------------------SP文件名--------------------------------*/
	// 应用信息------------------------------------------------------
	public static final String FILE_APPCONFIG = "AppConfig";

	// 收藏collection的商品(需要与网络同步)--------------------------
	public static final String FILE_COLLECTION = "file_collection";
	// 收藏key
	public static final String KEY_COLLECTION = "key_collection";

	// 购物车数量file
	public static final String FILE_CART_NUMBER = "file_cart_number";
	// key
	public static final String KEY_CART_NUMBER = "key_cart_number";

	// 购物车（本地）(用户提交订单则从购物车中删除数据，如果用户取消订单则重新添加订单商品到购物车)
	public static final String FILE_SHOPPING_CART = "file_shopping_cart";
	// 用户信息file
	public static final String FILE_USER_INFO = "file_user_info";
	// 用户信息key
	public static final String KEY_USER_INFO = "key_user_info";
	// 用户是否登录key
	public static final String KEY_USER_LOGIN = "key_user_login";
	// 用户地址信息
	public static final String FILE_USER_ADDRESS = "file_user_address";

	// 用户默认的收货地址---------------------------------------------------
	// file
	public static final String FILE_DEFAULT_ADDRESS = "file_default_address";
	// key
	public static final String KEY_DEFAULT_ADDRESS = "key_default_address";

	// 用户进入购物车是否刷新---------------------------------------------------
	// file
	public static final String FILE_IS_FRESH_CART = "file_is_fresh_cart";
	// key
	public static final String KEY_IS_FRESH_CART = "key_is_fresh_cart";
}

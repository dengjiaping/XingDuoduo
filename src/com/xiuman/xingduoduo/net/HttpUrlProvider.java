package com.xiuman.xingduoduo.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;

import com.xiuman.xingduoduo.app.URLConfig;

/**
 * @名称：HttpUrlProvider.java
 * @描述：请求连接
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class HttpUrlProvider extends HttpConnWorker {
	// 实例
	private static HttpUrlProvider instance;

	/**
	 * @描述：单例
	 * @date：2014-7-29
	 * @return
	 */
	public static synchronized HttpUrlProvider getIntance() {
		if (instance == null) {

			instance = new HttpUrlProvider();
		}
		return instance;
	}

	/**
	 * 构造函数
	 */
	public HttpUrlProvider() {
		super();
	}

	/**
	 * 设置参数
	 */
	@Override
	public void setParems(String str, Object object) {

	}

	/**
	 * @描述：用户注册
	 * @param mContext
	 * @param httpTaskListener
	 * @param registrappusername
	 *            用户名
	 * @param singleurl
	 *            特殊后缀
	 * @param registrapppassw
	 *            密码
	 * @param registrappemail
	 *            邮箱 2014-8-12
	 * @param nickname 昵称
	 */
	public void getRegister(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String registrappusername, String registrapppassw,
			String registrappemail,String nickname) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		try {
			url += httpDataTask
					.jointToUrl("registrappusername", URLEncoder.encode(registrappusername, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("registrapppassw", registrapppassw);
		url += httpDataTask.jointToUrl("registrappemail", registrappemail);
		try {
			url += httpDataTask
					.jointToUrl("nickname", URLEncoder.encode(nickname, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpDataTask.execute(url);
	}

	/**
	 * @描述：获取用户个人信息
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param usernameapp
	 *            用户名 2014-8-12
	 */
	public void getUserInfo(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String usernameapp) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		try {
			url += httpDataTask.jointToUrl("usernameapp", URLEncoder.encode(usernameapp, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpDataTask.execute(url);
	}

	/**
	 * @描述：用户登录
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param verappusername
	 *            用户名
	 * @param verapppawss
	 *            密码 2014-8-12
	 */
	public void getUserLogin(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String verappusername, String verapppawss) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		try {
			url += httpDataTask.jointToUrl("verappusername", URLEncoder.encode(verappusername, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("verapppawss", verapppawss);
		
		httpDataTask.execute(url);
	}

	/**
	 * @描述：修改密码
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param updateusernameapp
	 *            用户名
	 * @param oldpassw
	 *            旧密码
	 * @param newpassw
	 *            新密码 2014-8-12
	 */
	public void getUpdateUserPsw(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String updateusernameapp, String oldpassw, String newpassw) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		try {
			url += httpDataTask.jointToUrl("updateusernameapp", URLEncoder.encode(updateusernameapp, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("oldpassw", oldpassw);
		url += httpDataTask.jointToUrl("newpassw", newpassw);
		httpDataTask.execute(url);
	}
	
	/**
	 * @描述：添加收货地址
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param OrderusernameId 用户id
	 * @param receiveApprName 收货人
	 * @param receiverMobile 手机
	 * @param receiverPhone 电话
	 * @param areaId 省市区
	 * @param receiverAderss 详细地址
	 * @param receiverZipCode 邮编
	 * 2014-8-21
	 */
	public void getAddAddresses(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,String OrderusernameId,String receiveApprName,
			String receiverMobile,String receiverPhone,String areaId,String receiverAderss,String receiverZipCode) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("OrderusernameId", OrderusernameId);
		try {
			url += httpDataTask.jointToUrl("receiveApprName",  URLEncoder.encode(receiveApprName, "utf-8"));
			url += httpDataTask.jointToUrl("areaId",  URLEncoder.encode(areaId, "utf-8"));
			url += httpDataTask.jointToUrl("receiverAderss",  URLEncoder.encode(receiverAderss, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("receiverMobile", receiverMobile);
		url += httpDataTask.jointToUrl("receiverPhone", receiverPhone);
		url += httpDataTask.jointToUrl("receiverZipCode", receiverZipCode);
		httpDataTask.execute(url);
	}
	/**
	 * @描述：修改收货地址
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param receiverId 已生成的收货地址Id
	 * @param OrderusernameId
	 * @param receiveApprName
	 * @param receiverMobile
	 * @param receiverPhone
	 * @param areaId
	 * @param receiverAderss
	 * @param receiverZipCode
	 * 2014-8-21
	 */
	public void getUpdateAddresses(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,String receiverId,String OrderusernameId,String receiveApprName,
			String receiverMobile,String receiverPhone,String areaId,String receiverAderss,String receiverZipCode) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("receiverId", receiverId);
		url += httpDataTask.jointToUrl("OrderusernameId", OrderusernameId);
		try {
			url += httpDataTask.jointToUrl("receiveApprName",  URLEncoder.encode(receiveApprName, "utf-8"));
			url += httpDataTask.jointToUrl("areaId",  URLEncoder.encode(areaId, "utf-8"));
			url += httpDataTask.jointToUrl("receiverAderss",  URLEncoder.encode(receiverAderss, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("receiverMobile", receiverMobile);
		url += httpDataTask.jointToUrl("receiverPhone", receiverPhone);
		url += httpDataTask.jointToUrl("receiverZipCode", receiverZipCode);
		httpDataTask.execute(url);
	}
	/**
	 * @描述：删除收货地址
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param receiverId
	 * 2014-8-21
	 */
	public void getDeleteAddresses(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,String receiverId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("receiverId", receiverId);
		httpDataTask.execute(url);
	}
	
	/**
	 * @描述：获取用户收货地址
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp 第一页
	 * @param pageSizeApp 默认值1000(获取所有，差不多了吧)
	 * @param userId
	 * 2014-8-19
	 */
	public void getUserAddresses(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,String userId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("pageApp", 1);
		url += httpDataTask.jointToUrl("pageSizeApp", 1000);
		url += httpDataTask.jointToUrl("userId", userId);
		httpDataTask.execute(url);
	}

	
	
	/**
	 * @描述：添加收藏
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param goodAppId
	 *            商品id
	 * @param memberAppId
	 *            用户id
	 */
	public void getAddCollection(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String goodAppId, String memberAppId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("goodAppId", goodAppId);
		url += httpDataTask.jointToUrl("memberAppId", memberAppId);

		httpDataTask.execute(url);
	}

	/**
	 * @描述：删除收藏
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param deleteGoodAppId
	 * @param deleteMemberAppId
	 *            2014-8-12
	 */
	public void getDeleteCollection(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String deleteGoodAppId, String deleteMemberAppId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("deleteGoodAppId", deleteGoodAppId);
		url += httpDataTask.jointToUrl("deleteMemberAppId", deleteMemberAppId);

		httpDataTask.execute(url);
	}
	/**
	 * @描述：获取用户收藏列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp 请求页
	 * @param pageSizeApp 单页数据量
	 * @param UID 用户Id
	 * 2014-8-14
	 */
	public void getCollections(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			int pageApp,String UID) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 40);
		url += httpDataTask.jointToUrl("UID", UID);
		
		System.out.println("收藏接口"+url);
		httpDataTask.execute(url);
	}
	/**
	 * @描述：获取分类
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 *            2014-8-13
	 */
	public void getClassify(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数

		httpDataTask.execute(url);
	}

	/**
	 * @描述：获取分类界面分类商品列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp
	 *            请求第几页
	 * @param categoryId
	 *            分类id
	 * @param pageSizeApp每页数据条数
	 *            (默认20) 
	 * @param sorting排序方式(desc降序，asc升序)           
	 * 2014-8-13
	 */
	public void getClassifyGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			String categoryId,String sorting) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 20);
		url += httpDataTask.jointToUrl("categoryId", categoryId);
		url += httpDataTask.jointToUrl("sorting", sorting);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：查看商品详情
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param goodId 商品Id
	 *            2014-8-13
	 */
	public void getGoodsInfo(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String goodId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("goodId", goodId);

		httpDataTask.execute(url);
		System.out.println("商品详情"+url);
	}
	
	
	/**
	 * @描述：加入购物车
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param userId
	 *            用户id
	 * @param productId
	 *            产品Id(规格)
	 * @param quantity
	 *            数量 2014-8-13
	 */
	public void getAdd2Cart(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String userId,
			String productId, int quantity) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("userId", userId);
		url += httpDataTask.jointToUrl("productId", productId);
		url += httpDataTask.jointToUrl("quantity", quantity);
		httpDataTask.execute(url);
	}
	/**
	 * @描述：移除购物车商品
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param cartItemId 购物车id
	 * 2014-8-19
	 */
	public void getDeleteCartGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String cartItemId){
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("cartItemId", cartItemId);
		httpDataTask.execute(url);
	}
	
	/**
	 * @描述：获取购物车
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp
	 * @param pageSizeApp
	 * @param userId
	 * 2014-8-18
	 */
	public void getShoppingCart(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			 String userId){
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 100);
		url += httpDataTask.jointToUrl("userId", userId);
		httpDataTask.execute(url);
	}
	
	/**
	 * @描述：修改购物车数量
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param cartItemId
	 * @param quantity
	 * 2014-8-21
	 */
	public void getUpdateShoppingCart(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String cartItemId,
			 int quantity){
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("cartItemId", cartItemId);
		url += httpDataTask.jointToUrl("quantity", quantity);
		httpDataTask.execute(url);
	}
	
	/**
	 * @描述：获取首页分类商品(各个模块的第一块)
	 * @date：2014-7-29
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp当前页
	 * @param pageSizeApp每页数据
	 * @param sortType 页面
	 */
	public void getCenterClassifyGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,int pageApp,String sortType) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 20);
		url += httpDataTask.jointToUrl("sortType", sortType);
		httpDataTask.execute(url);
	}
	/**
	 * @描述：生成订单
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param OrderusernameId 用户id
	 * @param paymentConfigID 支付方式id
	 * @param deliveryID 运费id
	 * @param receiverAppID 收货地址id
	 * @param memo 留言
	 * @param shopId 商品ids
	 * 2014-8-26
	 */
	public void getCreateOrder(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String OrderusernameId,String paymentConfigID,String deliveryID,String receiverAppID,String memo,String shopId){
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("post");
//		// 设置请求参数
		httpDataTask.setParams("OrderusernameId", OrderusernameId);
		httpDataTask.setParams("paymentConfigID", paymentConfigID);
		httpDataTask.setParams("deliveryID", deliveryID);
		httpDataTask.setParams("receiverAppID", receiverAppID);
		try {
			httpDataTask.setParams("memo",URLEncoder.encode(memo, "utf-8") );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		httpDataTask.setParams("shopId", shopId);
		
		httpDataTask.execute(url);
	}
	
	
	/**
	 * @描述：删除订单
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param OrderId
	 *            订单id 2014-8-12
	 */
	public void getDeleteOrder(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String OrderId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("OrderId", OrderId);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：获取历史订单
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param i 当前页
	 * @param pageSizeApp 每页数据
	 * @param userId 用户id
	 *            2014-8-13
	 */
	public void getOrderHistory(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int i, String userId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("ageApp", i);
		url += httpDataTask.jointToUrl("pageSizeApp", 10);
		url += httpDataTask.jointToUrl("userId", userId);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：评价商品
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param reviesUID
	 *            用户id
	 * @param reviesgoodsId
	 *            商品id
	 * @param contendApp
	 *            评论内容 2014-8-12
	 */
	public void getDisCussGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String reviesUID, String reviesgoodsId, String contendApp) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("reviesUID", reviesUID);
		url += httpDataTask.jointToUrl("reviesgoodsId", reviesgoodsId);
		url += httpDataTask.jointToUrl("contendApp", contendApp);

		httpDataTask.execute(url);
	}
	
	/**
	 * @描述：获取商品评价列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp
	 * @param pageSizeApp 默认加载20条评论
	 * @param commentGoodId
	 * 2014-8-14
	 */
	public void getGoodsDiscuss(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,int pageApp,String commentGoodId) {
		String url = URLConfig.BASE_IP+singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 20);
		url += httpDataTask.jointToUrl("commentGoodId", commentGoodId);
		
		httpDataTask.execute(url);
	}

	/**
	 * @描述：商品搜索接口
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp
	 *            第几页
	 * @param pageSizeApp
	 *            每页数据量（默认20条）
	 * @param serachName
	 *            搜索关键字 2014-8-13
	 */
	public void getSearchGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			String serachName) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 20);
		try {
			url += httpDataTask.jointToUrl("serachName",
					URLEncoder.encode(serachName, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpDataTask.execute(url);
	}
	
	
	/**
	 * @描述:获取支付宝签名字串
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param orderId
	 */
	public void getAlipay(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,String orderId) {
		String url = URLConfig.BASE_IP+singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("goodId", orderId);
		
		
		httpDataTask.execute(url);
	}
	/**
	 * @描述:普通帖列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 */
	public void getPost(Context mContext,
			HttpTaskListener httpTaskListener, String forumId ) {
		String url = URLConfig.PRIVATE_IP+URLConfig.FORUM_LIST_IP+forumId;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		
		
		httpDataTask.execute(url);
	}
	/**
	 * @描述:置顶帖列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 * @param pageNo
	 * @param pageSize
	 */
	public void getTopPost(Context mContext,
			HttpTaskListener httpTaskListener, String forumId ,int pageNo,int pageSize) {
		String url = URLConfig.PRIVATE_IP+URLConfig.FORUM_TOP_LIST_IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("goodId", forumId);
		url += httpDataTask.jointToUrl("pageNo", pageNo);
		url += httpDataTask.jointToUrl("pageSize", pageSize);
		
		
		httpDataTask.execute(url);
	}
	
	
	/**
	 * @描述:帖子回复列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 */
	public void getPostReply(Context mContext,
			HttpTaskListener httpTaskListener, int postId ) {
		String url = URLConfig.PRIVATE_IP+URLConfig.FORUM_REPLY_LIST_IP+postId;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		
		
		httpDataTask.execute(url);
	}
	
	
	
	
	/**
	 * @描述:回帖接口
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 * @param postTypeId
	 * @param topicId
	 * @param title
	 * @param content
	 * @param userId
	 */
	public void getPostReplySend(Context mContext, HttpTaskListener httpTaskListener, String forumId,String postTypeId,String topicId,String title,String content,String userId){
		String url = URLConfig.PRIVATE_IP + URLConfig.POST_REPLY__IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("post");
//		// 设置请求参数
		httpDataTask.setParams("forumId", forumId);
		httpDataTask.setParams("postTypeId", postTypeId);
		httpDataTask.setParams("topicId", topicId);
		httpDataTask.setParams("userId", userId);
		try {
			httpDataTask.setParams("title",URLEncoder.encode(title, "utf-8") );
			httpDataTask.setParams("content",URLEncoder.encode(content, "utf-8") );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		httpDataTask.setParams("shopId", shopId);
		
		httpDataTask.execute(url);
	}
	
}

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
	 * @param nickname
	 *            昵称
	 */
	public void getRegister(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String registrappusername, String registrapppassw,
			String registrappemail, String nickname, String gender) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		try {
			url += httpDataTask.jointToUrl("registrappusername",
					URLEncoder.encode(registrappusername, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("registrapppassw", registrapppassw);
		url += httpDataTask.jointToUrl("registrappemail", registrappemail);
		try {
			url += httpDataTask.jointToUrl("nickname",
					URLEncoder.encode(nickname, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			url += httpDataTask.jointToUrl("gender",
					URLEncoder.encode(gender, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 注册渠道标识
		url += httpDataTask.jointToUrl("channel", "guimimeizhuang");
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
			url += httpDataTask.jointToUrl("usernameapp",
					URLEncoder.encode(usernameapp, "utf-8"));
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
			url += httpDataTask.jointToUrl("verappusername",
					URLEncoder.encode(verappusername, "utf-8"));
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
			url += httpDataTask.jointToUrl("updateusernameapp",
					URLEncoder.encode(updateusernameapp, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("oldpassw", oldpassw);
		url += httpDataTask.jointToUrl("newpassw", newpassw);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：修改个人信息
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param usernameId
	 * @param gender
	 * @param birth
	 * @param name
	 * @param areaStore
	 * @param address
	 * @param zipCode
	 * @param mobile
	 * @param nickname
	 *            2014-9-20
	 */
	public void getUpdateUserInfo(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String usernameId, String gender, String birth, String name,
			String areaStore, String address, String zipCode, String phone,
			String nickname,String email) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("usernameId", usernameId);
		try {
			url += httpDataTask.jointToUrl("gender",
					URLEncoder.encode(gender, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("birth", birth);
		try {
			url += httpDataTask.jointToUrl("name",
					URLEncoder.encode(name, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			url += httpDataTask.jointToUrl("areaStore",
					URLEncoder.encode(areaStore, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			url += httpDataTask.jointToUrl("address",
					URLEncoder.encode(address, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("zipCode", zipCode);
		url += httpDataTask.jointToUrl("phone", phone);
		try {
			url += httpDataTask.jointToUrl("nickname",
					URLEncoder.encode(nickname, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("email", email);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：添加收货地址
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param OrderusernameId
	 *            用户id
	 * @param receiveApprName
	 *            收货人
	 * @param receiverMobile
	 *            手机
	 * @param receiverPhone
	 *            电话
	 * @param areaId
	 *            省市区
	 * @param receiverAderss
	 *            详细地址
	 * @param receiverZipCode
	 *            邮编 2014-8-21
	 */
	public void getAddAddresses(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String OrderusernameId, String receiveApprName,
			String receiverMobile, String receiverPhone, String areaId,
			String receiverAderss, String receiverZipCode) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("OrderusernameId", OrderusernameId);
		try {
			url += httpDataTask.jointToUrl("receiveApprName",
					URLEncoder.encode(receiveApprName, "utf-8"));
			url += httpDataTask.jointToUrl("areaId",
					URLEncoder.encode(areaId, "utf-8"));
			url += httpDataTask.jointToUrl("receiverAderss",
					URLEncoder.encode(receiverAderss, "utf-8"));
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
	 * @param receiverId
	 *            已生成的收货地址Id
	 * @param OrderusernameId
	 * @param receiveApprName
	 * @param receiverMobile
	 * @param receiverPhone
	 * @param areaId
	 * @param receiverAderss
	 * @param receiverZipCode
	 *            2014-8-21
	 */
	public void getUpdateAddresses(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String receiverId, String OrderusernameId, String receiveApprName,
			String receiverMobile, String receiverPhone, String areaId,
			String receiverAderss, String receiverZipCode) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("receiverId", receiverId);
		url += httpDataTask.jointToUrl("OrderusernameId", OrderusernameId);
		try {
			url += httpDataTask.jointToUrl("receiveApprName",
					URLEncoder.encode(receiveApprName, "utf-8"));
			url += httpDataTask.jointToUrl("areaId",
					URLEncoder.encode(areaId, "utf-8"));
			url += httpDataTask.jointToUrl("receiverAderss",
					URLEncoder.encode(receiverAderss, "utf-8"));
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
	 *            2014-8-21
	 */
	public void getDeleteAddresses(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String receiverId) {
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
	 * @param pageApp
	 *            第一页
	 * @param pageSizeApp
	 *            默认值1000(获取所有，差不多了吧)
	 * @param userId
	 *            2014-8-19
	 */
	public void getUserAddresses(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String userId) {
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
	 * @param pageApp
	 *            请求页
	 * @param pageSizeApp
	 *            单页数据量
	 * @param UID
	 *            用户Id 2014-8-14
	 */
	public void getCollections(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			String UID) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 40);
		url += httpDataTask.jointToUrl("UID", UID);

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
	 * @param sorting排序方式
	 *            (desc降序，asc升序) 2014-8-13
	 */
	public void getClassifyGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			String categoryId, String sorting) {
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
	 * @param goodId
	 *            商品Id 2014-8-13
	 */
	public void getGoodsInfo(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String goodId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("goodId", goodId);

		httpDataTask.execute(url);
	}

	/**
	 * @描述：相关商品推荐
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp
	 * @param categoryId
	 * @param sorting
	 *            2014-9-17
	 */
	public void getGoodsRecommend(Context mContext,
			HttpTaskListener httpTaskListener, String categoryId) {
		String url = URLConfig.BASE_IP + URLConfig.SORT_ZONGHE;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("pageApp", 1);
		url += httpDataTask.jointToUrl("pageSizeApp", 9);
		url += httpDataTask.jointToUrl("categoryId", categoryId);
		url += httpDataTask.jointToUrl("sorting", "desc");
		httpDataTask.execute(url);
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
	 * @param cartItemId
	 *            购物车id 2014-8-19
	 */
	public void getDeleteCartGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String cartItemId) {
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
	 *            2014-8-18
	 */
	public void getShoppingCart(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			String userId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 1000);
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
	 *            2014-8-21
	 */
	public void getUpdateShoppingCart(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String cartItemId, int quantity) {
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
	 * @param sortType
	 *            页面
	 */
	public void getCenterClassifyGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			String sortType) {
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
	 * @描述：获取首页广告（商品，专区）
	 * @param context
	 * @param httpTaskListener
	 * @param singleurl
	 * @param showAdress
	 *            1：广告，2：专区，3：商品
	 * @时间 2014-10-18
	 */
	public void getCenterAd(Context context, HttpTaskListener httpTaskListener,
			String singleurl, String showAdress) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(context, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("showAdress", showAdress);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：获取置顶商品
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 *            2014-9-21
	 */
	public void getStickGoods(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		httpDataTask.execute(url);
	}

	/**
	 * @描述：生成订单
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param OrderusernameId
	 *            用户id
	 * @param paymentConfigID
	 *            支付方式id
	 * @param deliveryID
	 *            运费id
	 * @param receiverAppID
	 *            收货地址id
	 * @param memo
	 *            留言
	 * @param shopId
	 *            商品ids 2014-8-26
	 */
	public void getCreateOrder(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String OrderusernameId, String paymentConfigID, String deliveryID,
			String receiverAppID, String memo, String shopId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("post");
		// // 设置请求参数
		httpDataTask.setParams("OrderusernameId", OrderusernameId);
		httpDataTask.setParams("paymentConfigID", paymentConfigID);
		httpDataTask.setParams("deliveryID", deliveryID);
		httpDataTask.setParams("receiverAppID", receiverAppID);
		try {
			httpDataTask.setParams("memo", URLEncoder.encode(memo, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpDataTask.setParams("shopId", shopId);

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
	 * @描述：取消订单
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param OrderId
	 *            订单id 2014-8-12
	 */
	public void getCancelOrder(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String orderId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("orderId", orderId);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：获取历史订单
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param i
	 *            当前页
	 * @param pageSizeApp
	 *            每页数据
	 * @param userId
	 *            用户id 2014-8-13
	 */
	public void getOrderHistory(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			String userId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("pageApp", pageApp);
		url += httpDataTask.jointToUrl("pageSizeApp", 20);
		url += httpDataTask.jointToUrl("userId", userId);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：获取订单详情
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param orderId
	 *            2014-9-18
	 */
	public void getOrderInfo(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String orderId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("orderId", orderId);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：确认收货
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param orderId
	 *            2014-9-19
	 */
	public void getOrderTaker(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, String orderId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("orderId", orderId);
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
			String reviesUID, String reviesgoodsId, String contendApp,
			float goodsQuality, float serviceAttitude, float deliverySpeed) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("reviesUID", reviesUID);
		url += httpDataTask.jointToUrl("reviesgoodsId", reviesgoodsId);
		try {
			url += httpDataTask.jointToUrl("contendApp",
					URLEncoder.encode(contendApp, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += httpDataTask.jointToUrl("goodsQuality", goodsQuality);
		url += httpDataTask.jointToUrl("serviceAttitude", serviceAttitude);
		url += httpDataTask.jointToUrl("deliverySpeed", deliverySpeed);

		httpDataTask.execute(url);
	}

	/**
	 * @描述：获取商品评价列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param pageApp
	 * @param pageSizeApp
	 *            默认加载20条评论
	 * @param commentGoodId
	 *            2014-8-14
	 */
	public void getGoodsDiscuss(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl, int pageApp,
			String commentGoodId) {
		String url = URLConfig.BASE_IP + singleurl;
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
	 * @描述：热门搜索关键字
	 * @param mContext
	 * @param httpTaskListener
	 * @param pageApp
	 * @param serachName
	 * @时间 2014-10-24
	 */
	public void getSearchKeyWords(Context mContext,
			HttpTaskListener httpTaskListener) {
		String url = URLConfig.BASE_IP + URLConfig.HOT_SEARCH;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		httpDataTask.execute(url);
	}

	/**
	 * @描述:获取支付宝签名字串
	 * @param mContext
	 * @param httpTaskListener
	 * @param singleurl
	 * @param orderId
	 */
	public void getAlipay(Context mContext, HttpTaskListener httpTaskListener,
			String singleurl, String orderId) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("orderId", orderId);

		httpDataTask.execute(url);
	}

	/**
	 * @描述：将支付结果返回码传递到后台
	 * @param httpTaskListener
	 * @param singleurl
	 * @param orderId
	 * @param statusCode
	 *            2014-9-19
	 */
	public void sendPayStatusCode(Context mContext,
			HttpTaskListener httpTaskListener, String singleurl,
			String orderId, String statusCode) {
		String url = URLConfig.BASE_IP + singleurl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		// 设置请求参数
		url += httpDataTask.jointToUrl("orderId", orderId);
		url += httpDataTask.jointToUrl("statusCode", statusCode);

		httpDataTask.execute(url);
	}

	/**
	 * @描述:普通帖列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 */
	public void getPost(Context mContext, HttpTaskListener httpTaskListener,
			String singleUrl, String forumId, int pageNo, int pageSize) {
		String url = URLConfig.PRIVATE_IP + singleUrl;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");

		url += httpDataTask.jointToUrl("forumId", forumId);
		url += httpDataTask.jointToUrl("pageNo", pageNo);
		url += httpDataTask.jointToUrl("pageSize", pageSize);

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
	public void getTopPost(Context mContext, HttpTaskListener httpTaskListener,
			String forumId, int pageNo, int pageSize) {
		String url = URLConfig.PRIVATE_IP + URLConfig.FORUM_TOP_LIST_IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("forumId", forumId);
		url += httpDataTask.jointToUrl("pageNo", pageNo);
		url += httpDataTask.jointToUrl("pageSize", pageSize);

		httpDataTask.execute(url);
	}

	/**
	 * 
	 * @描述：帖子回复列表
	 * @param mContext
	 * @param httpTaskListener
	 * @param postId
	 * @param pageNo
	 * @param pageSize
	 *            2014-9-25
	 */
	public void getPostReply(Context mContext,
			HttpTaskListener httpTaskListener, String topicId, int pageNo) {
		String url = URLConfig.PRIVATE_IP + URLConfig.FORUM_REPLY_LIST_IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("topicId", topicId);
		url += httpDataTask.jointToUrl("pageNo", pageNo);
		url += httpDataTask.jointToUrl("pageSize", 20);
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
	public void getPostReplySend(Context mContext,
			HttpTaskListener httpTaskListener, String forumId,
			String postTypeId, String topicId, String title, String content,
			String userId) {
		String url = URLConfig.PRIVATE_IP + URLConfig.POST_REPLY__IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("post");
		// // 设置请求参数
		httpDataTask.setParams("forumId", forumId);
		httpDataTask.setParams("postTypeId", postTypeId);
		httpDataTask.setParams("topicId", topicId);
		httpDataTask.setParams("userId", userId);
		httpDataTask.setParams("title", title);
		httpDataTask.setParams("content", content);
		System.out.println("回帖链接" + url + forumId);
		httpDataTask.execute(url);
	}

	/**
	 * @描述:发表帖子
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 * @param postTypeId
	 * @param category
	 * @param title
	 * @param content
	 * @param userId
	 */
	public void getPostPublish(Context mContext,
			HttpTaskListener httpTaskListener, String forumId,
			String postTypeId, String category, String title, String content,
			String userId, String attachment, String scode) {
		String url = URLConfig.PRIVATE_IP + URLConfig.POST_PUBLISH__IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("post");
		// // 设置请求参数
		httpDataTask.setParams("forumId", forumId);
		httpDataTask.setParams("postTypeId", postTypeId);
		httpDataTask.setParams("category", category);
		httpDataTask.setParams("attachment", attachment);
		httpDataTask.setParams("userId", userId);
		httpDataTask.setParams("scode", scode);
		// httpDataTask.setParams("title",title);
		// httpDataTask.setParams("content",content);
		try {
			httpDataTask.setParams("title", new String(title.getBytes("utf-8"),
					"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			httpDataTask.setParams("content",
					new String(content.getBytes("utf-8"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		httpDataTask.execute(url);
	}

	/**
	 * @描述:我的发帖
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 * @param pageNo
	 * @param pageSize
	 */
	public void getMyPost(Context mContext, HttpTaskListener httpTaskListener,
			String userId, int pageNo) {
		String url = URLConfig.PRIVATE_IP + URLConfig.MY_POST__IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("userId", userId);
		url += httpDataTask.jointToUrl("pageNo", pageNo);
		url += httpDataTask.jointToUrl("pageSize", 10);

		httpDataTask.execute(url);
	}

	/**
	 * @描述:我的回帖
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 * @param pageNo
	 * @param pageSize
	 */
	public void getMyReplyPost(Context mContext,
			HttpTaskListener httpTaskListener, String userId, int pageNo) {
		String url = URLConfig.PRIVATE_IP + URLConfig.MY_REPLY_POST_IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");
		url += httpDataTask.jointToUrl("userId", userId);
		url += httpDataTask.jointToUrl("pageNo", pageNo);
		url += httpDataTask.jointToUrl("pageSize", 10);

		httpDataTask.execute(url);
	}

	/**
	 * @描述:广告贴
	 * @param mContext
	 * @param httpTaskListener
	 * @param forumId
	 * @param pageNo
	 * @param pageSize
	 */
	public void getAdPost(Context mContext, HttpTaskListener httpTaskListener,
			String forumId, int pageNo, int pageSize) {
		String url = URLConfig.PRIVATE_IP + URLConfig.MY_AD_POST_IP;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");

		url += httpDataTask.jointToUrl("forumId", forumId);
		httpDataTask.execute(url);
	}

	/**
	 * @描述：获取论坛板块
	 * @param mContext
	 * @param httpTaskListener
	 * @时间 2014-10-22
	 */
	public void getBBSPlate(Context mContext, HttpTaskListener httpTaskListener) {
		String url = URLConfig.PRIVATE_IP + URLConfig.BBS_PLATE;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");

		httpDataTask.execute(url);
	}

	/**
	 * @描述：应用推荐
	 * @param mContext
	 * @param httpTaskListener
	 *            2014-9-22
	 */
	public void getAppRecommend(Context mContext,
			HttpTaskListener httpTaskListener) {
		String url = URLConfig.BASE_IP + URLConfig.APP_RECOMMEND;
		HttpDataTask httpDataTask = new HttpDataTask(mContext, httpTaskListener);
		httpDataTask.setHttpMethod("get");

		httpDataTask.execute(url);
	}

}

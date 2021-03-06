package com.xiuman.xingduoduo.app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiuman.xingduoduo.lock.util.LockPatternUtils;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Ad;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.model.Classify;
import com.xiuman.xingduoduo.model.GoodsStick;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.model.UserAddress;
import com.xiuman.xingduoduo.util.SharedPreUtils;

/**
 * @名称：MyApplication.java
 * @描述：
 * @author danding
 * @version 2014-6-6
 */
public class MyApplication extends Application {
	private HttpClient httpClient;
	// 单例
	private static MyApplication mInstance;
	// 滑动解锁
	private LockPatternUtils mLockPatternUtils;
	// 保存json数据缓存<url,json>
	private HashMap<String, String> jsonCache = new HashMap<String, String>();

	/**
	 * @描述：获取Application实例
	 * @date：2014-7-29
	 * @return
	 */
	public static MyApplication getInstance() {
		return mInstance;
	}

	/**
	 * @描述：获取滑动解锁实例
	 * @date：2014-7-29
	 * @return
	 */
	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}

	/**
	 * onCreate
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		mLockPatternUtils = new LockPatternUtils(this);
		int threadPoolSize = 3;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			threadPoolSize = 5;
		} else {
			threadPoolSize = 3;
		}
		File cacheDir = getOwnCacheDirectory(getApplicationContext(),
				"xddcache");
		/** 初始化图片加载类配置信息 **/
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				// .memoryCacheExtraOptions(480, 800)
				// // max width, max
				// .threadPriority(Thread.NORM_PRIORITY - 2)
				// // 加载图片的线程数
				// .threadPoolSize(1)
				// // 线程池内加载的数量
				// .memoryCacheSize(2 * 1024 * 1024)
				// .discCacheSize(50 * 1024 * 1024)
				// // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 *
				// // 1024))
				// .memoryCache(new UsingFreqLimitedMemoryCache(50))
				// .imageDownloader(new BaseImageDownloader(this))
				// .denyCacheImageMultipleSizesInMemory()//
				// 解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
				// .diskCacheFileNameGenerator(new Md5FileNameGenerator())//
				// 设置磁盘缓存文件名称
				// .tasksProcessingOrder(QueueProcessingType.LIFO)//
				// 设置加载显示图片队列进程
				// .writeDebugLogs()
				// 原
				// .threadPoolSize(threadPoolSize) // 线程池个数
				// .threadPriority(Thread.NORM_PRIORITY - 1) // 线程的优先级
				// .tasksProcessingOrder(QueueProcessingType.FIFO) // 任务处理顺序
				// .memoryCacheExtraOptions(480, 800) // 在内存中存放的图片大小
				// .discCacheExtraOptions(480, 800, null) //
				// 在硬盘在中存放的图片大小、压缩类型、压缩率
				// .denyCacheImageMultipleSizesInMemory()
				// .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) // ?
				// .memoryCacheSizePercentage(13) // 缓存大小的百分比
				// .memoryCacheSize(2 * 1024 * 1024)
				// .discCache(new UnlimitedDiscCache(cacheDir))
				// .discCacheSize(50 * 1024 * 1024) // 设置缓存的大小(内存大小) ?
				// .discCacheFileCount(200) // 设置缓存文件的数量
				// .discCacheFileNameGenerator(new Md5FileNameGenerator()) //
				// 文件名(MD5)
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// // default
				// .writeDebugLogs() // 写调试日志

				// 现
				.threadPoolSize(threadPoolSize).threadPriority(0)
				.memoryCacheExtraOptions(480, 800) // 在内存中存放的图片大小
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache((int) (Runtime.getRuntime().maxMemory()*0.2f)))
				.discCache(new UnlimitedDiscCache(cacheDir))
				.memoryCacheSize((int) (Runtime.getRuntime().maxMemory()*0.2f))
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		ImageLoader.getInstance().init(config);
		httpClient = this.createHttpClient();
	}

	/**
	 * @描述：缓存目录
	 * @param context
	 * @param name
	 * @return 2014-9-29
	 */
	public static File getOwnCacheDirectory(Context context, String name) {
		File appCacheDir = null;
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			appCacheDir = new File(context.getExternalCacheDir(), name);
		}
		if (appCacheDir == null
				|| (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}

	/**
	 * 创建HttpCilent实例
	 * 
	 * @return
	 */
	private HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(connMgr, params);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		this.shutdownHttpClient();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		this.shutdownHttpClient();
	}

	/**
	 * 关闭连接释放资源
	 */
	private void shutdownHttpClient() {
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 获取HttpCilent实例
	 * 
	 * @return
	 */
	public HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * 
	 * @描述：获取安装包信息
	 * @date：2014-7-29
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * @描述：获取应用版本
	 * @return 2014-10-11
	 */
	public String getVersionName() {
		return getPackageInfo().versionName;
	}

	/**
	 * @描述：获取应用版本号
	 * @return 2014-10-11
	 */
	public int getVersionCode() {
		return getPackageInfo().versionCode;
	}

	/*-----------------------------------------json数据缓存--------------------------------------*/
	/**
	 * 获取数据缓存
	 * 
	 * @param url
	 * @return
	 */
	public String getJsonCache(String url) {

		return jsonCache.get(url);
	}

	/**
	 * @描述：设置缓存数据
	 * @date：2014-7-29
	 * @param url
	 * @param value
	 */
	public synchronized void setJsonCache(String url, String value) {

		this.jsonCache.put(url, value);
	}

	/**
	 * @描述：清除JSON缓存
	 * @date：2014-7-29
	 */
	public void cleanJsonCache() {
		this.jsonCache.clear();
	}

	/*----------------------------------------用户信息---------------------------------------------*/
	/**
	 * @描述：将用户信息作为json保存起来,并将用户是否登录设置为true
	 * @param json_user_info
	 *            2014-8-12
	 */
	public synchronized void saveUserInfo(String json_user_info) {
		SharedPreUtils.setString(this, json_user_info,
				AppConfig.FILE_USER_INFO, AppConfig.KEY_USER_INFO);
		SharedPreUtils.setBoolean(this, true, AppConfig.FILE_USER_INFO,
				AppConfig.KEY_USER_LOGIN);
	}

	/**
	 * @描述：获取用户信息
	 * @return 2014-8-12
	 */
	public User getUserInfo() {
		String json_user_info = SharedPreUtils.getString(this,
				AppConfig.FILE_USER_INFO, AppConfig.KEY_USER_INFO);
		User user = null;
		if (!json_user_info.equals("")) {
			user = new Gson().fromJson(json_user_info, User.class);
		}
		return user;
	}

	/**
	 * @描述：不删除用户信息,并设置用户登录为false(用于获取购物车信息) 2014-8-12
	 */
	public void deleteUserInfo() {
		// SharedPreUtils.setString(this, "", AppConfig.FILE_USER_INFO ,
		// AppConfig.KEY_USER_INFO);
		SharedPreUtils.setBoolean(this, false, AppConfig.FILE_USER_INFO,
				AppConfig.KEY_USER_LOGIN);
		// 删除默认地址
		deleteDefaultAddress();
		// 将购物车数量设置为0
		setCartGoodsNumber(0);
	}

	/**
	 * @描述：查看用户是否登录
	 * @return 2014-8-12
	 */
	public synchronized boolean isUserLogin() {
		boolean result = SharedPreUtils.getBoolean(this, false,
				AppConfig.FILE_USER_INFO, AppConfig.KEY_USER_LOGIN);
		return result;
	}

	/*----------------------------------------收藏（本地）(item)-----------------------------------*/
	// 收藏列表
	private ArrayList<String> goods = new ArrayList<String>();

	/**
	 * 加入收藏（item） 列表并更新
	 * 
	 * @param goods
	 */
	public void addCollection(String goods_one) {
		// 如果要收藏的商品不再收藏中则进行收藏
		if (!getCollection().contains(goods_one)) {
			goods.add(goods_one);
		}
		// 更新收
		updateCollection(goods);
	}

	/**
	 * 获取收藏列表(从SharedPreferences中)
	 * 
	 * @return
	 */
	public ArrayList<String> getCollection() {

		Gson gson = new Gson();
		String collection = SharedPreUtils.getString(this,
				AppConfig.FILE_COLLECTION, AppConfig.KEY_COLLECTION);
		// 如果获取到的数据为空则返回空的集合
		if (!collection.equals("")) {
			goods = gson.fromJson(collection,
					new TypeToken<ArrayList<String>>() {
					}.getType());
		}
		return goods;

	}

	/**
	 * 删除收藏（item） 更新收藏
	 */
	public void deleteCollection(String goods_one) {
		if (getCollection().contains(goods_one)) {
			goods.remove(goods_one);
		}
		updateCollection(goods);

	}

	/**
	 * 更新收藏列表
	 * 
	 * @param goods
	 */
	public void updateCollection(ArrayList<String> goods) {
		Gson gson = new Gson();
		String collection = gson.toJson(goods).toString();
		SharedPreUtils.setString(this, collection, AppConfig.FILE_COLLECTION,
				AppConfig.KEY_COLLECTION);
	}

	/**
	 * 是否已经收藏商品
	 * 
	 * @param goods_one
	 */
	public boolean isCollected(String goods_one) {
		if (getCollection().contains(goods_one)) {
			return true;
		}
		return false;
	}

	/*------------------------------------------购物车(本地)(集合)-------------------------------------*/
	/**
	 * @描述：设置购物车商品数
	 * @param number
	 *            2014-8-18
	 */
	public void setCartGoodsNumber(int number) {
		SharedPreUtils.setString(this, number + "", AppConfig.FILE_CART_NUMBER,
				AppConfig.KEY_CART_NUMBER);
	}

	/**
	 * @描述：获取购物车商品数
	 * @return 2014-8-18
	 */
	public int getCartGoodsNumber() {
		String temp = SharedPreUtils.getString(this,
				AppConfig.FILE_CART_NUMBER, AppConfig.KEY_CART_NUMBER);
		if (temp.equals("")) {
			return 0;
		}
		int number = Integer.parseInt(temp);
		return number;
	}

	/*-----------------------------------------保存默认收货地址(用户上一次提交订单时使用的地址)-------------------*/
	/**
	 * @描述：将默认收货地址转成json字符串保存在sp中
	 * @param address
	 *            2014-8-21
	 */
	public void setDefaultAddress(UserAddress address) {
		String json_user_address = new Gson().toJson(address).toString();
		SharedPreUtils.setString(this, json_user_address,
				AppConfig.FILE_DEFAULT_ADDRESS, AppConfig.KEY_DEFAULT_ADDRESS);
	}

	/**
	 * @描述：获取默认收货地址
	 * @return 2014-8-21
	 */
	public UserAddress getDefaultAddress() {
		UserAddress address = null;
		String json = SharedPreUtils.getString(this,
				AppConfig.FILE_DEFAULT_ADDRESS, AppConfig.KEY_DEFAULT_ADDRESS);
		if (!json.equals("")) {
			address = new Gson().fromJson(json, UserAddress.class);
		}
		return address;
	}

	/**
	 * @描述：用户退出时删除 2014-9-21
	 */
	public void deleteDefaultAddress() {
		SharedPreUtils.setString(this, "", AppConfig.FILE_DEFAULT_ADDRESS,
				AppConfig.KEY_DEFAULT_ADDRESS);
	}

	/*----------------------------------------------保存首页广告--------------------------------------------------------*/
	/**
	 * @描述：保存广告
	 * @param ads
	 *            2014-9-20
	 */
	public void saveAds(ActionValue<Ad> ads) {
		String json_ads = new Gson().toJson(ads).toString();
		SharedPreUtils.setString(this, json_ads, AppConfig.FILE_SAVE_ADS,
				AppConfig.KEY_SAVE_ADS);
	}

	/**
	 * @描述：获取广告
	 * @return 2014-9-21
	 */
	public ActionValue<Ad> getAds() {
		ActionValue<Ad> ads = null;
		String json = SharedPreUtils.getString(this, AppConfig.FILE_SAVE_ADS,
				AppConfig.KEY_SAVE_ADS);
		if (!json.equals("")) {
			ads = new Gson().fromJson(json, new TypeToken<ActionValue<Ad>>() {
			}.getType());
		}
		return ads;
	}

	/*----------------------------------------------保存首页商品--------------------------------------------------------*/
	/**
	 * @描述：保存首页商品
	 * @param ads
	 *            2014-9-20
	 */
	public void saveCenterGoods(ActionValue<Ad> goods) {
		String json_goods = new Gson().toJson(goods).toString();
		SharedPreUtils.setString(this, json_goods, AppConfig.FILE_SAVE_GOODS,
				AppConfig.KEY_SAVE_GOODS);
	}

	/**
	 * @描述：获取首页商品
	 * @return 2014-9-21
	 */
	public ActionValue<Ad> getCenterGoods() {
		ActionValue<Ad> goods = null;
		String json = SharedPreUtils.getString(this, AppConfig.FILE_SAVE_GOODS,
				AppConfig.KEY_SAVE_GOODS);
		if (!json.equals("")) {
			goods = new Gson().fromJson(json, new TypeToken<ActionValue<Ad>>() {
			}.getType());
		}
		return goods;
	}

	/*----------------------------------------------保存首页专区--------------------------------------------------------*/
	/**
	 * @描述：保存首页专区
	 * @param ads
	 *            2014-9-20
	 */
	public void saveCenterCategory(ActionValue<Ad> category) {
		String json_goods = new Gson().toJson(category).toString();
		SharedPreUtils.setString(this, json_goods,
				AppConfig.FILE_SAVE_CATEGORY, AppConfig.KEY_SAVE_CATEGORY);
	}

	/**
	 * @描述：获取首专区
	 * @return 2014-9-21
	 */
	public ActionValue<Ad> getCenterCategory() {
		ActionValue<Ad> category = null;
		String json = SharedPreUtils.getString(this,
				AppConfig.FILE_SAVE_CATEGORY, AppConfig.KEY_SAVE_CATEGORY);
		if (!json.equals("")) {
			category = new Gson().fromJson(json,
					new TypeToken<ActionValue<Ad>>() {
					}.getType());
		}
		return category;
	}

	/*----------------------------------------------论坛广告--------------------------------------------------------*/
	/**
	 * @描述：保存广告
	 * @param ads
	 *            2014-9-20
	 */
	public void saveBBSAds(ActionValue<BBSPost> ads) {
		String json_ads = new Gson().toJson(ads).toString();
		SharedPreUtils.setString(this, json_ads, AppConfig.FILE_SAVE_BBS_ADS,
				AppConfig.KEY_SAVE_BBS_ADS);
	}

	/**
	 * @描述：获取广告
	 * @return 2014-9-21
	 */
	public ActionValue<BBSPost> getBBSAds() {
		ActionValue<BBSPost> ads = null;
		String json = SharedPreUtils.getString(this,
				AppConfig.FILE_SAVE_BBS_ADS, AppConfig.KEY_SAVE_BBS_ADS);
		if (!json.equals("")) {
			ads = new Gson().fromJson(json,
					new TypeToken<ActionValue<BBSPost>>() {
					}.getType());
		}
		return ads;
	}

	/*----------------------------------------------保存首页置顶商品--------------------------------------------------------*/
	/**
	 * @描述：保存首页置顶商品
	 * @param ads
	 *            2014-9-20
	 */
	public void saveStick(ActionValue<GoodsStick> stcik) {
		String json_stcik = new Gson().toJson(stcik).toString();
		SharedPreUtils.setString(this, json_stcik, AppConfig.FILE_SAVE_STCIK,
				AppConfig.KEY_SAVE_STICK);
	}

	/**
	 * @描述：获取首页置顶商品
	 * @return 2014-9-21
	 */
	public ActionValue<GoodsStick> getStcik() {
		ActionValue<GoodsStick> stcik = null;
		String json = SharedPreUtils.getString(this, AppConfig.FILE_SAVE_STCIK,
				AppConfig.KEY_SAVE_STICK);
		if (!json.equals("")) {
			stcik = new Gson().fromJson(json,
					new TypeToken<ActionValue<GoodsStick>>() {
					}.getType());
		}
		return stcik;
	}

	/*------------------------------------------------保存屏幕宽度和高度----------------------------------------------------------*/
	/**
	 * @描述：保存屏幕宽度
	 * @param width
	 *            2014-9-23
	 */
	public void saveScreenWidth(int width) {
		SharedPreUtils.setString(this, width + "", AppConfig.FILE_SWCRREN,
				AppConfig.KEY_SWCRRENWIDTH);
	}

	/**
	 * @描述：获取屏幕宽度
	 * @return 2014-9-23
	 */
	public int getScreenWidth() {
		int width = 0;
		String result = SharedPreUtils.getString(this, AppConfig.FILE_SWCRREN,
				AppConfig.KEY_SWCRRENWIDTH);
		if (result != "") {
			width = Integer.parseInt(result);
		}

		return width;
	}

	/**
	 * @描述：保存屏幕高度
	 * @param width
	 *            2014-9-23
	 */
	public void saveScreenHeight(int height) {
		SharedPreUtils.setString(this, height + "", AppConfig.FILE_SWCRREN,
				AppConfig.KEY_SWCRRENHEIGHT);
	}

	/**
	 * @描述：获取屏幕高度
	 * @return 2014-9-23
	 */
	public int getScreenHeight() {
		int height = 0;
		String result = SharedPreUtils.getString(this, AppConfig.FILE_SWCRREN,
				AppConfig.KEY_SWCRRENHEIGHT);
		if (result != "") {
			height = Integer.parseInt(result);
		}

		return height;
	}

	/*--------------------------------------------保存应用版本信息--------------------------------------*/
	/**
	 * @描述：保存版本信息
	 * @param version_name
	 * @param version_cede
	 *            2014-10-11
	 */
	public void saveVersion(String version_name, int version_cede) {
		SharedPreUtils.setString(this, version_name + "",
				AppConfig.FILE_VERSION, AppConfig.KEY_VERSION_NAME);
		SharedPreUtils.setString(this, version_cede + "",
				AppConfig.FILE_VERSION, AppConfig.KEY_VERSION_CODE);
	}

	/**
	 * @描述：获取保存的版本信息
	 * @return 2014-10-11
	 */
	public String getLocalVersionName() {
		String versin_name = SharedPreUtils.getString(this,
				AppConfig.FILE_VERSION, AppConfig.KEY_VERSION_NAME);
		if (versin_name != "") {
			return versin_name;
		}
		return "";
	}

	/**
	 * @描述：获取保存的版本号
	 * @return 2014-10-11
	 */
	public int getLocalVersionCode() {
		int version_code = 0;
		String result = SharedPreUtils.getString(this, AppConfig.FILE_VERSION,
				AppConfig.KEY_VERSION_CODE);
		if (result != "") {
			version_code = Integer.parseInt(result);
		}
		return version_code;
	}

	/*--------------------------------------------保存论坛板块------------------------------------------*/
	/**
	 * @描述：保存论坛板块
	 * @param ads
	 *            2014-9-20
	 */
	public void saveBBSPlate(ActionValue<BBSPlate> plates) {
		String json_stcik = new Gson().toJson(plates).toString();
		SharedPreUtils.setString(this, json_stcik,
				AppConfig.FILE_SAVE_BBS_PLATE, AppConfig.KEY_SAVE_BBS_PLATE);
	}

	/**
	 * @描述：获取论坛板块
	 * @return 2014-9-21
	 */
	public ActionValue<BBSPlate> getBBSPlate() {
		ActionValue<BBSPlate> plates = null;
		String json = SharedPreUtils.getString(this,
				AppConfig.FILE_SAVE_BBS_PLATE, AppConfig.KEY_SAVE_BBS_PLATE);
		if (!json.equals("")) {
			plates = new Gson().fromJson(json,
					new TypeToken<ActionValue<BBSPlate>>() {
					}.getType());
		}
		return plates;
	}

	/*---------------------------------------------保存商品分类---------------------------------------------*/
	/**
	 * @描述：商品分类
	 * @param ads
	 *            2014-9-20
	 */
	public void saveCategory(ActionValue<Classify> classify) {
		String json_ads = new Gson().toJson(classify).toString();
		SharedPreUtils.setString(this, json_ads, AppConfig.FILE_CATEGORY,
				AppConfig.KEY_CATEGORY);
	}

	/**
	 * @描述：获取商品分类
	 * @return 2014-9-21
	 */
	public ActionValue<Classify> getCategory() {
		ActionValue<Classify> classify = null;
		String json = SharedPreUtils.getString(this, AppConfig.FILE_CATEGORY,
				AppConfig.KEY_CATEGORY);
		if (!json.equals("")) {
			classify = new Gson().fromJson(json,
					new TypeToken<ActionValue<Classify>>() {
					}.getType());
		}
		return classify;
	}
}

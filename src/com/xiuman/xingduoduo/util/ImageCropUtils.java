package com.xiuman.xingduoduo.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * 
 * @名称：ImageCropUtils.java
 * @描述：头像改变工具类
 * @author danding
 * @version
 * @date：2014-6-19
 */
public class ImageCropUtils {
	// 头像文件路径
	private String strFilePath;
	// 头像文件
	private File file = null;
	private Context context = null;
	// 头像路径
	private Uri imageUri;
	private Activity activity;

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	public ImageCropUtils(Context context) {
		this.context = context;
		activity = (Activity) context;
		strFilePath = createDirectory();
	}

	/**
	 * 
	 * @描述：获取头像路径
	 * @date：2014-6-19
	 * @return
	 */
	public String getUri() {
		return imageUri.toString();
	}

	/**
	 * 设置文件保存路径
	 * 
	 * @param strfile
	 */
	public void setfile(String strFilePath) {
		this.strFilePath = strFilePath;

	}

	/**
	 * 
	 * @描述：文件名
	 * @date：2014-6-19
	 * @return
	 */
	public String createNewPhotoName() {

		return "xingduoduo_user_head.jpg";
	}

	/**
	 * 
	 * @描述：打开相机
	 * @date：2014-6-19
	 * @param resultCode
	 */
	public void openCamera(int resultCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		autoResetImageUri();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		activity.startActivityForResult(intent, resultCode);
	}

	/**
	 * 
	 * @描述：重置文件路径
	 * @date：2014-6-19
	 */
	public void autoResetImageUri() {
		imageUri = Uri.parse(Uri.fromFile(file) + "/" + createNewPhotoName());
	}

	/**
	 * 
	 * @描述：重置文件路径
	 * @date：2014-6-19
	 * @param uri
	 */
	private void resetImageUri(Uri uri) {
		imageUri = uri;
	}

	/**
	 * @描述：打开画廊
	 * @date：2014-6-19
	 * @param resultCode
	 */
	public void openGallery(int resultCode) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		resetImageUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(imageUri, "image/*");
		activity.startActivityForResult(intent, resultCode);
	}

	/**
	 * 
	 * @描述：剪切大图片
	 * @date：2014-6-19
	 * @param requestCode
	 */
	public void cropBigPhotoByCamera(int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imageUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * @描述：剪切小图片
	 * @date：2014-6-19
	 * @param requestCode
	 */
	public void cropSmallPhotoByCamera(int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imageUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 
	 * @描述：获取画廊中的图片并剪切
	 * @date：2014-6-19
	 * @param requestCode
	 */
	public void openGalleryAndCropSmallPhoto(int requestCode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 
	 * @描述：获取画廊中的图片并剪切
	 * @date：2014-6-19
	 * @param requestCode
	 */
	public void openGalleryAndCropBigPhoto(int requestCode) {
		autoResetImageUri();
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false); // no face detection
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 
	 * @描述：根据路径获取图片
	 * @date：2014-6-19
	 * @return
	 */
	public Bitmap getBitmapByUri() {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(imageUri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * @描述：通过Intent 获取图片
	 * @date：2014-6-19
	 * @param data
	 * @return
	 */
	public Bitmap getBitmapByIntent(Intent data) {
		Bundle bundle = data.getExtras();

		if (bundle != null) {
			return bundle.getParcelable("data");
		}
		return null;
	}

	/**
	 * 
	 * @描述：将图片转化成为byte
	 * @date：2014-6-19
	 * @param data
	 * @return
	 */
	public InputStream getInPutStreamByIntent(Intent data) {
		Bitmap bitmap = getBitmapByIntent(data);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

		byte[] b = stream.toByteArray(); // 将图片流以字符串形式存储下来
		return new ByteArrayInputStream(b);
	}

	/**
	 * 
	 * @描述：文件存储地址
	 * @date：2014-6-19
	 */
	public String createDirectory() {
		String strFilePath;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			strFilePath = Environment.getExternalStorageDirectory().getPath()
					+ "/xingduoduo/";
		} else {
			strFilePath = Environment.getDataDirectory().getPath()
					+ "/xingduoduo/";
		}
		if (strFilePath != null && !strFilePath.isEmpty()) {
			file = new File(strFilePath);

			if (!file.exists()) {
				file.mkdirs();
			}
		}
		return strFilePath;
	}

	/**
	 * 
	 * @描述：保存图片
	 * @date：2014-6-19
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */
	public void saveFile(Bitmap bm, String fileName) throws IOException {
		File dirFile = new File(strFilePath);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(strFilePath + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}
}

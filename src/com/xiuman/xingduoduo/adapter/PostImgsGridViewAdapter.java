package com.xiuman.xingduoduo.adapter;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.util.pic.Bimp;
import com.xiuman.xingduoduo.util.pic.FileUtils;

/**
 * @名称：PostImgsGridViewAdapter.java
 * @描述：帖子发表界面(图片列表)
 * @author danding 2014-8-11
 */
public class PostImgsGridViewAdapter extends BaseAdapter {

	private LayoutInflater inflater; // 视图容器
	private int selectedPosition = -1;// 选中的位置
	private boolean shape;
	private Context context;
	private Handler handler;
	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}

	public PostImgsGridViewAdapter(Context context,Handler handler) {
		this.context = context;
		this.handler = handler;
		inflater = LayoutInflater.from(context);
	}

	public void update() {
		loading();
	}

	public int getCount() {
		return (Bimp.bmp.size() + 1);
	}

	public Object getItem(int arg0) {

		return null;
	}

	public long getItemId(int arg0) {

		return 0;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.item_published_grida,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == Bimp.bmp.size()) {
			holder.image.setImageBitmap(BitmapFactory.decodeResource(
					context.getResources(), R.drawable.icon_addpic_unfocused));
			if (position == 9) {
				holder.image.setVisibility(View.GONE);
			}
		} else {
			holder.image.setImageBitmap(Bimp.bmp.get(position));
		}

		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
	}

	public void loading() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (Bimp.max == Bimp.drr.size()) {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						break;
					} else {
						try {
							String path = Bimp.drr.get(Bimp.max);
							System.out.println(path);
							Bitmap bm = Bimp.revitionImageSize(path);
							Bimp.bmp.add(bm);
							String newStr = path.substring(
									path.lastIndexOf("/") + 1,
									path.lastIndexOf("."));
							FileUtils.saveBitmap(bm, "" + newStr);
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

}

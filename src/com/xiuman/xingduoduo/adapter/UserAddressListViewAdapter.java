package com.xiuman.xingduoduo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.model.UserAddress;
/**
 * @名称：UserAddressListViewAdapter.java
 * @描述：用户收货地址管理Adapter
 * @author danding
 * 2014-8-20
 */
public class UserAddressListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<UserAddress> addresses;
	
	public UserAddressListViewAdapter(Context context,
			ArrayList<UserAddress> addresses) {
		super();
		this.context = context;
		this.addresses = addresses;
	}

	@Override
	public int getCount() {
		return addresses.size();
	}

	@Override
	public Object getItem(int position) {
		return addresses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_user_address, null);
			holder = new ViewHolder();
			holder.tv_item_address_name = (TextView) convertView.findViewById(R.id.tv_item_address_name);
			holder.tv_item_address_phone = (TextView) convertView.findViewById(R.id.tv_item_address_phone);
			holder.tv_item_address_detail = (TextView) convertView.findViewById(R.id.tv_item_address_detail);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		UserAddress address = addresses.get(position);
		
		holder.tv_item_address_name.setText(address.getReceiveName());
		holder.tv_item_address_phone.setText(address.getTelephone());
		holder.tv_item_address_detail.setText(address.getAreaId()+address.getAddress());
		
		return convertView;
	}

	/**
	 * @名称：UserAddressListViewAdapter.java
	 * @描述：优化
	 * @author danding
	 * 2014-8-20
	 */
	static class ViewHolder{
		//姓名
		TextView tv_item_address_name;
		//电话
		TextView tv_item_address_phone;
		//详细地址
		TextView tv_item_address_detail;
	}
}

package com.develapp.idcards;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sriven on 6/1/2018.
 */

public class AdapterBanners extends PagerAdapter {
ArrayList<DataBanners> items;
Context context;

	public AdapterBanners(Context context, ArrayList<DataBanners> items){
	this.context=context;
	this.items=items;
	}
	@Override
	public int getCount(){
	return items.size();
	}
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		do {
			context = this.context; //Using just "this" doesn't work either.
		} while (context==null);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_banners, container,false);

		ImageView imageView = (ImageView)view.findViewById(R.id.banner_img_item);
		Picasso.get().load(items.get(position).image).into(imageView);
		ViewPager viewPager = (ViewPager) container;
		viewPager.addView(view, 0);

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ViewPager viewPager = (ViewPager) container;
		View view = (View) object;
		viewPager.removeView(view);
	}

}

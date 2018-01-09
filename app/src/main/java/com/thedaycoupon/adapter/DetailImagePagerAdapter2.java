package com.thedaycoupon.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thedaycoupon.R;

/**
 * Created by Administrator on 2017-11-18.
 */

public class DetailImagePagerAdapter2 extends PagerAdapter {

    String[] urls;

    public void setData(String[] urls) {
        this.urls = urls;
        notifyDataSetChanged();
    }

    public DetailImagePagerAdapter2(String[] urls) {
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_detail_image2, container, false);
        ImageView detailImage = view.findViewById(R.id.itemDetailImage2);
        Glide.with(container.getContext()).load(urls[position]).into(detailImage);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

}

package com.thedaycoupon.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thedaycoupon.R;
import com.thedaycoupon.domain.detailImage.DetailImage;
import com.thedaycoupon.activity.DetailImageActivity;
import com.thedaycoupon.util.GoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-18.
 */
public class DetailImagePagerAdapter extends PagerAdapter {

    List<DetailImage> detailImages = new ArrayList<>();

    public void setData(List<DetailImage> detailImages) {
        this.detailImages = detailImages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return detailImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_detail_image, container, false);
        final ImageView detailImage = (ImageView) view.findViewById(R.id.itemDetailImage);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] urls = new String[detailImages.size()];
                for (int i = 0; i < detailImages.size(); i++) {
                    urls[i] = detailImages.get(i).detail_url;
                }
                GoUtil.startActivity(v.getContext(), DetailImageActivity.class, urls);
            }
        });
        Glide.with(container.getContext()).load(detailImages.get(position).detail_url).into(detailImage);
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

package com.thedaycoupon.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.thedaycoupon.R;
import com.thedaycoupon.activity.MainActivity;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.GoUtil;
import com.thedaycoupon.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-08.
 */
public class IntroPagerAdapter extends PagerAdapter {

    List<Integer> images = new ArrayList<>();

    public IntroPagerAdapter() {
        images.add(R.drawable.intro01);
        images.add(R.drawable.intro02);
        images.add(R.drawable.intro03);
        images.add(R.drawable.intro04);
        images.add(R.drawable.intro05);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_intro, container, false);
        ImageView intro = view.findViewById(R.id.imageIntro);
        intro.setImageResource(images.get(position));
        ImageView button = view.findViewById(R.id.imageStart);
        if(position == 4){
            button.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(container.getContext(), R.anim.anim);
            button.startAnimation(animation);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreferenceUtil.putBoolean(v.getContext(), Const.PREF_KEY_IS_FIRST, true);
                    GoUtil.startActivityWithFlag(container.getContext(), MainActivity.class);

                }
            });
        } else {
            button.setVisibility(View.INVISIBLE);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
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

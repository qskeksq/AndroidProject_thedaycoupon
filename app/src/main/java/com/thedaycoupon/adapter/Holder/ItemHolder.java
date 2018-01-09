package com.thedaycoupon.adapter.Holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedaycoupon.R;
import com.thedaycoupon.activity.DetailActivity;
import com.thedaycoupon.util.GoUtil;

/**
 * Created by mac on 2017. 12. 17..
 */

public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
    public ImageView couponLogo;
    public ImageView couponFavorite;
    public TextView couponExp;
    public TextView couponDiscountRate;
    public TextView couponPlace;
    public TextView couponNotice;
    public String name;
    public int recyclerPosition;
    public int parentNo;
    public View itemView;
    public Context context;
    public IItemHolder adapter;

    public ItemHolder(View itemView, IItemHolder adapter) {
        super(itemView);
        this.itemView = itemView;
        this.context = itemView.getContext();
        this.adapter = adapter;
        initView();
        setListener();

    }

    private void initView(){
        couponLogo = itemView.findViewById(R.id.couponLogo);
        couponFavorite = itemView.findViewById(R.id.couponFavorite);
        couponExp = itemView.findViewById(R.id.couponExp);
        couponDiscountRate = itemView.findViewById(R.id.couponDiscountRate);
        couponPlace = itemView.findViewById(R.id.couponPlace);
        couponNotice = itemView.findViewById(R.id.couponNotice);

    }

    private void setListener(){
        couponLogo.setOnClickListener(this);
        couponExp.setOnClickListener(this);
        couponDiscountRate.setOnClickListener(this);
        couponFavorite.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.couponLogo:
                GoUtil.startActivity(context, DetailActivity.class, parentNo);
                break;
            case R.id.couponExp:
                GoUtil.startActivity(context, DetailActivity.class, parentNo);
                break;
            case R.id.couponDiscountRate:
                adapter.checkIsFavorite(recyclerPosition);
                break;
            case R.id.couponFavorite:
                adapter.checkIsFavorite(recyclerPosition);
                break;
        }
    }

    public void setPosition(int recyclerPosition){
        this.recyclerPosition = recyclerPosition;
    }

    public interface IItemHolder {
        void checkIsFavorite(int recyclerPosition);
    }
}

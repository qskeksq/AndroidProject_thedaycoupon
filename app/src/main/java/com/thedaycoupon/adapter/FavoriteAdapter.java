package com.thedaycoupon.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.FavoriteService;
import com.thedaycoupon.adapter.Holder.FilterHolder;
import com.thedaycoupon.adapter.Holder.ItemHolder;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponDao;
import com.thedaycoupon.domain.favorite.FavoriteDao;
import com.thedaycoupon.util.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-07.
 */
public class FavoriteAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemHolder.IItemHolder {

    List<Coupon> couponList = new ArrayList<>();
    private Activity context;
    private FavoriteService favoriteService;
    private OnFavoriteCallback callback;

    public FavoriteAdapter(List<Coupon> couponList, Activity context, OnFavoriteCallback callback, FavoriteService favoriteService) {
        this.couponList = couponList;
        this.context = context;
        this.callback = callback;
        this.favoriteService = favoriteService;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Const.VIEWTYPE_FILETER;
        } else {
            return Const.VIEWTYPE_COUPON;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case Const.VIEWTYPE_FILETER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
                return new FilterHolder(view);
            case Const.VIEWTYPE_COUPON:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon, parent, false);
                return new ItemHolder(view, this);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            position = position - 1;
            Coupon coupon = couponList.get(position);
            Glide.with(context).load(coupon.logoFilePath).into(iHolder.couponLogo);
            iHolder.couponExp.setText(coupon.no + "");
            iHolder.name = coupon.name;
            iHolder.parentNo = coupon.no;
            iHolder.recyclerPosition = position + 1;
            iHolder.setPosition(position + 1);
            if (couponList.get(position).isFavorite) {
                iHolder.couponFavorite.setImageResource(R.drawable.favor_checked);
            } else {
                iHolder.couponFavorite.setImageResource(R.drawable.favor);
            }
        }
    }

    @Override
    public int getItemCount() {
        return couponList.size() + 1;
    }

    @Override
    public void checkIsFavorite(int recyclerPosition) {
        unCheckFavorite(recyclerPosition);
    }

    /**
     * 즐겨찾기 삭제
     */
    private void unCheckFavorite(int recyclerPosition) {
        Coupon coupon = couponList.get(recyclerPosition-1);
        // 1. 쿠폰 체크 해제
        couponList.remove(recyclerPosition - 1);
        notifyDataSetChanged(); // TODO 이렇게 하든가 배열을 사용하든가 해야함
        // 2. 로컬 DB - Coupon 데이터 업데이트
        CouponDao.getInstance(context).updateChecked(coupon.no, false);
        // 3. 로컬 DB - Favorite 데이터 삭제
        FavoriteDao.getInstance(context).delete(coupon.no);
        // 3. Remote 서버에서 삭제
        favoriteService.deleteFavorite(coupon.no);
        // 4. main 화면에 반영
        callback.setHasChange(true);
        callback.addChangedCategory(coupon.mainCategory);
    }

    /**
     * 삭제된 리스트 반환받기 위한 인터페이스
     */
    public interface OnFavoriteCallback {
        void setHasChange(boolean hasChange);

        void addChangedCategory(String mainCategory);
    }

}

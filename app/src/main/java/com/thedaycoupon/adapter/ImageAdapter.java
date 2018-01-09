package com.thedaycoupon.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thedaycoupon.R;
import com.thedaycoupon.activity.RequestCouponActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {

    Context context;
    List<Uri> imageUri = new ArrayList<>();
    RequestCouponActivity requestCouponActivity;

    public ImageAdapter(RequestCouponActivity requestCouponActivity) {
        this.requestCouponActivity = requestCouponActivity;
    }

    public void addData(Uri uri){
        imageUri.add(uri);
        notifyItemInserted(imageUri.size()-1);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_image, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Uri uri = imageUri.get(position);
        Glide.with(context).load(uri).into(holder.image);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return imageUri.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView image, cancel;
        int position;

        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.requestImage);
            cancel = itemView.findViewById(R.id.requestDeleteImage);
            cancel.setOnClickListener(listener);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이미지 제거
                imageUri.remove(position);
                requestCouponActivity.deleteImageFile(position);
                notifyDataSetChanged();
                // 만약 이미지가 0개이면 리사이클러뷰를 숨긴다.

            }
        };
    }
}

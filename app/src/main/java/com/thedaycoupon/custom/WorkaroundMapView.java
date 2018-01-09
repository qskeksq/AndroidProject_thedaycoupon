package com.thedaycoupon.custom;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.MapView;

/**
 * Created by mac on 2017. 12. 18..
 */
public class WorkaroundMapView extends MapView {

    private OnTouchListener mListener;

    public WorkaroundMapView(Context context) {
        super(context);
    }

    public WorkaroundMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        addTouchableWrapperView();
    }

    public void setListener(OnTouchListener mListener) {
        this.mListener = mListener;
    }

    private void addTouchableWrapperView(){
        TouchableWrapper frameLayout = new TouchableWrapper(getContext());
        int bgColor = ContextCompat.getColor(getContext(), android.R.color.transparent);
        frameLayout.setBackgroundColor(bgColor);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(frameLayout, params);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("맵뷰", "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("맵뷰", "onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("맵뷰", "dispatchTouchEvent ");
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 터치 리스너 인터페이스
     */
    public interface OnTouchListener {
        void onTouch();
    }


    /**
     * 터치 가능한 영역을 처리하기 위한 래퍼 클래스
     */
    public class TouchableWrapper extends FrameLayout {

        public TouchableWrapper(Context context) {
            super(context);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            Log.e("자식", "onInterceptTouchEvent");
            return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.e("자식", "onTouchEvent");
            return super.onTouchEvent(event);
        }
        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mListener.onTouch();
                    break;
                case MotionEvent.ACTION_UP:
                    mListener.onTouch();
                    break;
            }
            Log.e("자식", "dispatchTouchEvent");
            return super.dispatchTouchEvent(event);
        }
    }
}

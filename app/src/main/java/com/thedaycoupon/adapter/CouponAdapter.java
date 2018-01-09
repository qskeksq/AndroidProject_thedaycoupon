package com.thedaycoupon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.thedaycoupon.R;
import com.thedaycoupon.activity.RemoteService.MainService;
import com.thedaycoupon.activity.SignInActivity;
import com.thedaycoupon.adapter.Holder.FilterHolder;
import com.thedaycoupon.adapter.Holder.ItemHolder;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponDao;
import com.thedaycoupon.domain.favorite.Favorite;
import com.thedaycoupon.domain.favorite.FavoriteDao;
import com.thedaycoupon.domain.member.Member;
import com.thedaycoupon.domain.tempFavorite.TempFavoriteDao;
import com.thedaycoupon.util.Const;
import com.thedaycoupon.util.DeviceInfoUtil;
import com.thedaycoupon.util.DialogUtil;
import com.thedaycoupon.util.GoUtil;
import com.thedaycoupon.util.NoticeUtil;
import com.thedaycoupon.util.PreferenceUtil;
import com.thedaycoupon.util.SignInUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-07.
 */
public class CouponAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements DialogUtil.IArrayDialog, ItemHolder.IItemHolder {

    List<Coupon> couponList = new ArrayList<>();
    private Context context;
    private ICouponAdapter iCouponAdapter;
    private MainService mainService;

    public void setData(List<Coupon> couponList) {
        this.couponList = couponList;
        notifyDataSetChanged();
    }

    public CouponAdapter(final Context context, ICouponAdapter iCouponAdapter, MainService mainService) {
        this.iCouponAdapter = iCouponAdapter;
        this.context = context;
        this.mainService = mainService;
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
        // position 0에 필터 기능을 넣었기 때문에 recyclerView의 position과 List의 position이
        // 1칸씩 차이가 난다. 아래에서는 두 기능을 섞어서 쓰기 때문에 각각 위치마다 신경을 써줘야 한다.
        // 또한 getItemCount()에 추가한 뷰만큼 +1해주는 것 잊지 말자.
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

    /**
     * 이미 즐겨찾기인지 확인
     */
    public void checkIsFavorite(int recyclerPosition) {
        Coupon coupon = couponList.get(recyclerPosition - 1);
        // 기존에 추가되지 않은 경우
        if (!coupon.isFavorite) {
            createFavorite(recyclerPosition);
            // 이미 추가된 경우 - 즐겨찾기 삭제
        } else {
            deleteFavorite(recyclerPosition);
        }
    }

    /**
     * 즐겨찾기 추가
     */
    private void createFavorite(int recyclerPosition) {
        Coupon coupon = couponList.get(recyclerPosition - 1);
        // 추가할 즐겨찾기 정보
        Favorite favorite = new Favorite();
        favorite.parentNo = coupon.no;
        favorite.onServer = 0;
        //
        checkHasId(recyclerPosition, favorite);
    }

    /**
     * 즐겨찾기 삭제
     */
    private void deleteFavorite(int recyclerPosition) {
        Coupon coupon = couponList.get(recyclerPosition - 1);
        // 1. 쿠폰 체크 해제
        setFavoriteUnChecked(coupon, recyclerPosition);
        // 2. 로컬 DB - Coupon 데이터 업데이트
        CouponDao.getInstance(context).updateChecked(coupon.no, false);
        // 3. Remote 서버에서 삭제
        mainService.deleteFavorite(coupon.no);
    }

    /**
     * 아이디 존재하는지 확인
     */
    private void checkHasId(int recyclerPosition, Favorite favorite) {
        String signInId = PreferenceUtil.getString(context, Const.PREF_KEY_ID);
        String tempId = PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID);
        if (SignInUtil.hasId(signInId, tempId)) {
            checkCurrentId(signInId, tempId, recyclerPosition, favorite);
            return;
        }
        showSignInDialog();
    }

    /**
     * 현재 아이디 확인
     */
    private void checkCurrentId(String signInId, String tempId, int recyclerPosition, Favorite favorite) {
        if (SignInUtil.hasSignedIn(signInId)) {
            createFavoriteBySignInId(signInId, recyclerPosition, favorite);
            return;
        }
        createFavoriteByTempId(tempId, recyclerPosition, favorite);
        return;
    }

    /**
     * 회원 아이디로 즐겨찾기 추가
     */
    private void createFavoriteBySignInId(String id, int recyclerPosition, Favorite favorite) {
        Coupon coupon = couponList.get(recyclerPosition - 1);
        // 1. 로컬 db에 저장
        createLocalFavorite(id, favorite);
        // 2. 만약 서버가 끊긴 상태에서 삭제를 했을 경우 TempFavorite 에는 서버에 가서 삭제될 데이터가 남아있다
        // 그 상황에서 만약 다시 그 데이터를 추가하고 서버에 연결이 된다면 서버에 먼저 추가가 될 것이고
        // 그 후 Favorite 페이지에 들어갈 때 이미 추가된 데이터가 다시 삭제될 것이다. 따라서 서버가 끊긴 상태에서
        // 다시 추가된 데이터는 추후 삭제하러 갈 목록에서 삭제해준다.
        deleteTempFavorite(coupon.no);
        // 3. 즐겨찾기 체크
        setFavoriteChecked(coupon, recyclerPosition);
        // 4. 로컬 DB Coupon 업데이트
        updateLocalFavorite(coupon.no);
        // 5. 즐겨찾기 POST
        mainService.createFavorite(favorite);
    }

    /**
     * 임시 아이디로 즐겨찾기 추가
     */
    private void createFavoriteByTempId(String id, int recyclerPosition, Favorite favorite) {
        Coupon coupon = couponList.get(recyclerPosition - 1);
        // 로컬 db에 저장
        createLocalFavorite(id, favorite);
        // 임시 즐겨찾기 삭제
        deleteTempFavorite(coupon.no);
        // 즐겨찾기 체크
        setFavoriteChecked(coupon, recyclerPosition);
        // 로컬 DB Coupon 업데이트
        updateLocalFavorite(coupon.no);
        // 즐겨찾기 POST
        mainService.createFavorite(favorite);
        // 기존에 생성한 임시아이디가 서버에 올라가 있는지 확인(매 즐겨찾기마다 확인한다)
        checkTempIdOnServer(id);
    }

    private void createLocalFavorite(String id, Favorite favorite) {
        favorite.memberId = id;
        FavoriteDao.getInstance(context).create(favorite);
    }

    private void deleteTempFavorite(int parentNo) {
        TempFavoriteDao.getInstance(context).delete(parentNo);
    }

    private void setFavoriteChecked(Coupon coupon, int recyclerPosition) {
        coupon.isFavorite = true;
        couponList.set(recyclerPosition - 1, coupon);
        iCouponAdapter.notifyMainFavoriteChanged(coupon.mainCategory);
        notifyItemChanged(recyclerPosition);
    }

    private void updateLocalFavorite(int parentNo) {
        CouponDao.getInstance(context).updateChecked(parentNo, true);
    }

    private void checkTempIdOnServer(String id) {
        if (PreferenceUtil.getInt(context, Const.PREF_KEY_TEMP_ID_ON_SERVER) == 0) {
            Member member = new Member();
            member.memberId = id;
            mainService.createTempMemberInfo(member);
        }
    }

    /**
     * 아이디가 존재하지 않을 경우
     */
    private void showSignInDialog() {
        DialogUtil.showDialog(context,
                context.getResources().getString(R.string.coupon_favorite_sign_in),
                context.getResources().getStringArray(R.array.coupon_favorite_select_sign_in),
                this, 0);
    }

    @Override
    public void onItemSelected(int id, int which, String[] array) {
        switch (which) {
            case Const.MEMBER_SIGN_IN:
                GoUtil.startActivity(context, SignInActivity.class);
                break;
            case Const.TEMP_SIGN_IN:
                createTempId();
                break;
        }
    }

    @Override
    public void onItemCanceled(int id) {

    }

    private void createTempId() {
        // 1. 임시아이디 생성
        String tempId = DeviceInfoUtil.createTempId();
        // 2. 임시아이디 저장
        saveTempId(tempId);
        // 3. POST
        Member member = makeTempMember(tempId);
        mainService.createTempMemberInfo(member);
    }

    private void saveTempId(String tempId) {
        PreferenceUtil.putString(context, Const.PREF_KEY_TEMP_ID, tempId);
        NoticeUtil.makeToast(context, "임시 아이디가 생성되었습니다");
    }

    private Member makeTempMember(String tempId) {
        Member member = new Member();
        member.memberId = tempId;
        return member;
    }

    private void setFavoriteUnChecked(Coupon coupon, int recyclerPosition) {
        coupon.isFavorite = false;
        couponList.set(recyclerPosition - 1, coupon);
        iCouponAdapter.notifyMainFavoriteChanged(coupon.mainCategory);
        notifyItemChanged(recyclerPosition);
    }

    public interface ICouponAdapter {
        void notifyMainFavoriteChanged(String mainCategory);
    }

}

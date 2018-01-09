package com.thedaycoupon.remote;

import com.thedaycoupon.BuildConfig;
import com.thedaycoupon.domain.coupon.Coupon;
import com.thedaycoupon.domain.coupon.CouponInfo;
import com.thedaycoupon.domain.detailImage.DetailImageInfo;
import com.thedaycoupon.domain.favorite.Favorite;
import com.thedaycoupon.domain.favorite.FavoriteInfo;
import com.thedaycoupon.domain.logoImage.LogoImageInfo;
import com.thedaycoupon.domain.member.Member;
import com.thedaycoupon.domain.member.MemberInfo;
import com.thedaycoupon.domain.tempFavorite.TempFavorite;
import com.thedaycoupon.domain.version.VersionInfo;
import com.thedaycoupon.domain.wrongInfo.WrongInfo;
import com.thedaycoupon.domain.wrongInfo.WrongInfoInfo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Service interface for retrofit networking
 */
public interface IRemoteService {

    String BASE_URL = BuildConfig.BASE_URL;

//=================================================================
//    dday_info_detail
//=================================================================
    /**
     * 전부 받아오기
     */
    @GET("/coupon/all")
    Call<CouponInfo> selectCouponInfoByAll();

    /**
     * 현재위치 기반으로 데이터 받아오기
     */
    @GET("/coupon/list")
    Call<CouponInfo> selectCouponInfoByLocation(@Query("latitude") double latitude,
                                                  @Query("longitude") double longitude);

//=================================================================
//    request_logo
//=================================================================

    /**
     * 로고파일 하나씩 받아오기
     */
    @GET("/logo/info")
    Call<LogoImageInfo> selectLogoByName(@Query("logo_filename") String logoFileName);

//=================================================================
//    dday_info_version
//=================================================================
    /**
     * 버전확인
     */
    @GET("/version")
    Call<VersionInfo> selectCouponInfoVersion();


//=================================================================
//    dday_info_photo_url
//=================================================================

    /**
     * 사진이미지 가져오기
     */
    @GET("/coupon/photo")
    Call<DetailImageInfo> selectCouponDetailImageByName(@Query("parent_no") int parentNo);


//=================================================================
//    member_info
//=================================================================

    /**
     * 임시 아이디 서버에 등록
     */
    @POST("/member/info")
    Call<MemberInfo> insertMemberInfo(@Body Member member);

    /**
     * 회원가입 아이디 서버에 등록, 동시에 임시 저장 아이디 삭제
     */
    @POST("/member/info")
    Call<MemberInfo> insertMemberInfo(@Body Member member, @Query("temp_id") String tempId);

    /**
     * 멤버 정보 서버에서 가져오기
     */
    @GET("/member/info")
    Call<MemberInfo> searchMemberInfo(@Query("member_id") String id);

    /**
     * 로그인
     */
    @POST("/member/login")
    Call<MemberInfo> login(@Body Member member);

//=================================================================
//    favorite_info
//=================================================================

    /**
     * SignInActivity - 기존에 저장해 둔 회원의 쿠폰 정보를 불러올 때/아이디로 즐겨찾기 정보 가져오기
     */
    @GET("/favorite/info")
    Call<FavoriteInfo> selectFavoriteInfo(@Query("member_id") String member_id);

    /**
     * CouponAdapter - 쿠폰 눌렀을 때/즐겨찾기 정보 서버에 등록
     */
    @POST("/favorite/info")
    Call<FavoriteInfo> insertFavoriteInfo(@Body Favorite favorite);

    /**
     * FavoriteActivity - 네트워크 문제로 남겨진 데이터 리스트로 보내기
     */
    @POST("favorite/leftOvers/create")
    Call<FavoriteInfo> insertFavoriteLeftOver(@Body List<Favorite> favoriteList);

    /**
     * FavoriteActivity - 네트워크 문제로 삭제되지 않은 정보 삭제
     */
    @POST("/favorite/leftOvers/delete")
    Call<FavoriteInfo> deleteFavoriteLeftOvers(@Body List<TempFavorite> tempFavorites);

    /**
     * SignInActivity -  임시아이디로 저장한 데이터를 선택, 회원가입한 아이디로 선택한 데이터 삭제
     */
    @DELETE("favorite/signedUp")
    Call<FavoriteInfo> deleteSignedUpFavorite(@Query("member_id") String signupId, @Query("temp_id") String tempId);

    /**
     * SignInActivity -  임시아이디로 저장한 데이터를 삭제
     */
    @DELETE("favorite/temp")
    Call<FavoriteInfo> deleteTempFavorite(@Query("temp_id") String tempId);

    /**
     * SignInActivity - 임시아이디로 저장힌 쿠폰 데이터를 유지함. 다만 처음 회원가입이기 때문에 서버에서 불러올 데이터는 없음
     */
    @PUT("favorite/temp")
    Call<FavoriteInfo> updateTempFavorite(@Query("signup_id") String signupId, @Query("temp_id") String tempId);

    /**
     * FavoriteAdapter - 쿠폰 눌렀을 때/즐겨찾기 정보 서버에서 삭제
     */
    @DELETE("/favorite/info")
    Call<FavoriteInfo> deleteFavoriteInfo(@Query("member_id") String memberId, @Query("parent_no") int parentNo);


//=================================================================
//    request_info
//=================================================================

    /**
     * 쿠폰 요청
     * @param logoFile 로고 파일
     * @param imageFiles 이미지 파일들(리스트로 업로드 가능), 다중 멀티파트 가능
     * @param requestInfo RequestBody 를 키로 가지는 맵을 통해 객체 전송 가능
     * @return
     */
    @Multipart
    @POST("/request/info")
    Call<CouponInfo> insertRequestInfo(@Part MultipartBody.Part logoFile,
                                         @Part List<MultipartBody.Part> imageFiles,
                                         @PartMap Map<String, Coupon> requestInfo);

//=================================================================
//    request_info
//=================================================================

    /**
     * 잘못된 정보
     */
    @POST("/wrongInfo")
    Call<WrongInfoInfo> insertWrongInfo(@Body WrongInfo wrongInfo);


}

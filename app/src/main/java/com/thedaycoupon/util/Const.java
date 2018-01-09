package com.thedaycoupon.util;

/**
 * Created by Administrator on 2017-11-11.
 */
public class Const {

    // 와이파이 정보
    public static final int NETWORK_DISABLED = 2;

    // 로컬 데이터베이스 정보
    public static final int LOCAL_DB_VERSION = 1;
    public static final String LOCAL_DB_NAME = "thedaycoupon.db";

    // 데이터베이스 칼럼
    public static final String COLUMN_NAME = "column_name";
    public static final String COLUMN_NO = "no";
    public static final String COLUMN_MAIN_CATEGORY = "mainCategory";
    public static final String COLUMN_MEMBER_ID = "memberId";
    public static final String COLUMN_ON_SERVER = "onServer";
    public static final String COLUMN_IS_FAVORITE = "isFavorite";
    public static final String COLUMN_PARENT_NO = "parentNo";

    // 즐겨찾기가 서버에 올라갔는지 여부
    public static final int ON_SERVER = 1;
    public static final int OFF_SERVER = 0;

    // 임시아이디가 서버에 올라갔는지 여부
    public static final int TEMP_ID_NOT_ON_SERVER = 0;
    public static final int TEMP_ID_ON_SERVER = 1;

    // 공유 프레퍼런스 키
    public static final String PREF_KEY_ID = "PREF_KEY_ID";
    public static final String PREF_KEY_TEMP_ID = "PREF_KEY_TEMP_ID";
    public static final String PREF_KEY_NAME = "PREF_KEY_NAME";
    public static final String PREF_KEY_PASSWORD = "PREF_KEY_PASSWORD";
    public static final String PREF_KEY_IS_FIRST = "PREF_KEY_IS_FIRST";
    public static final String PREF_KEY_LOCAL_DATA_VERSION = "PREF_KEY_VERSION";
    public static final String PREF_KEY_TEMP_ID_ON_SERVER = "PREF_KEY_TEMP_ID_ON_SERVER";
    public static final String PREF_KEY_PASS_PERMISSION = "PREF_KEY_PASS_PERMISSION";
    public static final String PREF_KEY_LONGITUDE = "PREF_KEY_LONGITUDE";
    public static final String PREF_KEY_LATITUDE = "PREF_KEY_LATITUDE";

    // 카테고리 순서
    public static final int MAIN_CATEGORY_ALL = 0;

    // 인텐트 엑스트라
    public static final String GOUTIL_EXTRA_1 = "GOUTIL_EXTRA_1";
    public static final String GOUTIL_EXTRA_2 = "GOUTIL_EXTRA_2";

    // 리사이클러뷰 뷰타입
    public static final int VIEWTYPE_FILETER = 0;
    public static final int VIEWTYPE_COUPON = 1;

    // 임시아이디, 회원아이디가 모두 없을 경우 어떤 아이디를 생성할 것인지 선택
    public static final int MEMBER_SIGN_IN = 0;
    public static final int TEMP_SIGN_IN = 1;

    // 아이디 타입 구분자
    public static final int FACEBOOK = 0;
    public static final int GOOGLE = 1;
    public static final int KAKAO = 2;

    // 멤버, Id 중복 확인
    public static final String EXISTS = "exists";

    // 쿠폰 요청
    public static final int REQ_CODE_PICK_LOGO_FROM_ALBUM = 0;
    public static final int REQ_CODE_FIND_PLACE = 2;
    public static final int REQ_CODE_PICK_IMAGE_FROM_ALBUM = 3;
    public static final int REQ_CODE_WIFI_SETTINGS = 0;
    public static final String SET_RESULT_EXTRA1 = "SET_RESULT_EXTRA1";
    public static final String SET_RESULT_EXTRA2 = "SET_RESULT_EXTRA2";

    // 서버 응답
    public static final String OK = "200";
    public static final String ERROR = "400";

    // 로그인 아이디 종류
    public static final String PREFIX_FACEBOOK = "facebook";
    public static final String PREFIX_GOOGLE = "google";
    public static final String PREFIX_KAKAO = "kakao";

    // 콜백 메소드 구분
    public static final int DIALOG_SEARCH_MEMBER = 0;
    public static final int DIALOG_INSERT_MEMBER = 1;
    public static final int DIALOG_SELECT_NETWORK = 2;

    // 구글 로그인
    public static final int GOOGLE_SIGN_IN = 999;

    // 메인 혹은 로그인 화면으로 복귀시 받을 데이터 있는지 확인
    public static final int REQ_CODE_LOGIN = 0;
    public static final int REQ_CODE_FAVORITE = 1;
}

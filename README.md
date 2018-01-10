# 그날쿠폰 클라이언트 / Android :open_file_folder:

### Project Manage - Android

- __설계패턴__ : MVP, MVC, MVVM 등 코드를 나누는 기준이 없이 한 곳에 모든 코드를 몰아넣다 보니 정리가 되지 않고 구현에 급급하게 된다. 정리가 되지 않기 때문에 전체 코드를 조망하기 어렵고 따라서 문제가 생겨도 부분적으로 해결하며 임시방편으로 해결하기 바쁘다. 당연히 퍼포먼스를 따지기도 힘들어진다. 완벽하게 패턴을 구현해 내지 않더라도 최소한의 적용은 필요하다.
    - MVC
    - MVP
    - MVVM

- __코딩원칙__ : 최소한의 코딩 원칙이 있어야 한다. 하나의 함수 길이, 모듈화 원칙, 인터페이스 사용과 통신, 네트워크 통신 등 기본적인 원칙이 없어서 기능은 비슷하고 구현은 됬으나 코드가 달라 문제가 생겼을 때 빨리 대처하기가 힘들다
    - 어플리케이션 단위
        - manifest
        - gradle
        - 보안
        - 버전관리
    - 설계 단위
        - 패키지
        - 로컬데이터베이스
        - *네트워크 통신
        - *비동기
        - *퍼포먼스
        - 객체지향과 디자인패턴
    - 구현 단위
        - *생명주기
        - 클래스
        - 인터페이스
        - *메소드
        - 유틸화
        - *모듈화
        - *재사용

- __버전관리__ : 이때까지는 버전관리를 하지 않고 했지만 이는 무계획성을 여실히 보여준다. 기능을 모듈화 하고, issue를 발행해서 필요한 부분을 나눠 개발해야 하루 코딩량을 할 수 있고, 개인 퍼포먼스를 측정할 수도 있다. 적어도 스스로가 얼만큼 할 수 있는지는 알아야 한다. 뿐만 아니라 중간에 끊어질 경우 어디까지 했는지도 모르겠고, 문제가 생길 경우에 코드를 되돌릴 수도 없으니 시간이 수배로 들어가는 것이다. 혼자 개발하더라도 버전관리가 반드시 필요하다.
    - GitHub

- __스크럼__ : 매일 할 일을 정하지 않고 하고 싶은 곳을 골라서 하고, 계획을 세우지 않았으니 디테일하게 구현되지 않은 부분이나 중간에 넘어간 부분을 알기도 어렵고, 하루 개발한 내용을 정리하지 않았기 때문에 다음날 바로 개발을 이어나가기 힘들다. 반드시 할 일을 정하고 또 하루 중 개발한 내용은 구체적으로 정리할 필요 있음.
    - 일단위 : 할 일, 한 일 정리, 버전관리, 코드정리, 공부내용 정리(daily readme)
    - 주단위 : 진도 파악, 주 단위 버전관리, 코드정리, 공부내용 정리, 유지보수
    - 월단위

- __(공부)내용 정리__ : 필요한 자료를 찾아볼 때 정리하면 시간이 더 걸리는 게 아니라 정리하지 않으면 오히려 시간이 더 걸린다. 뿐만 아니라 빨리 구현하려고 실행되는 코드만 알고 있으면 오히려 시간이 더 걸린다. 따라서 필요한 내용이 있으면 반드시 공식문서부터 찾아보고, 그 후 블로그나 스택에 가서 실제 어떻게 코드를 구현했는지, 장단점은 무엇인지 찾아봐야 한다. 또한 중간에 구현하면서 실패한 부분 또한 공부한 내용으로 기록해야 한다. 정리하지 않을 경우 프로젝트를 지나고 나면 많은 부분 다음에도 같은 실수를 하게 됨.
    - 데일리 README 작성
    - 블로그 정리

- __주기적인 코드정리__ :  패키지, 메소드, 클래스 정리(모듈화)하지 않음. 마지막에 한꺼번에 하는게 아니라 주기를 정해서 하루 코딩한 내용은 그날 정리해야지 마지막에 하려고 하면 부분부분에 치우치게 되고, 하나하나의 부분은 잘 알지만
    전체 안에서의 부분을 알기 힘들다
    - by 설계패턴
    - by 코딩원칙
    - manifest
    - gradle
    - .gitignore

- __주기적, 계획적인 테스트__
    - 단위테스트 : TDD by junit
    - 통합테스트 : Travis
    - UI테스트 : espresso

- __미루기__ : 부족한 부분을 '이만큼만 하고 나중에 해야지' 해서 넘어가고 실제로 까먹고 있다가 오류가 발생한 부분이 너무도 많다. 역시 계획을 세우지
    않아서 발생한 부분이긴 하나 절대 반복하지 말 것.

- __개발목표와 기획목표__

## __1. 설계__ :open_file_folder:

    [화면설계](), [데이터베이스 설계](), [서버 설계]()가 완성된 상황에서 진행해야 한다. 중간에 변경될 여지가 있지만 변경될 때 변경하더라도 사전에 하나라도 확실하지 않으면 클라이언트 구현에 시간이 오래 걸린다.

### (1) __어플리케이션 단위__

    아직 익숙하지 않기 때문에 계속 오픈소스 코드에서 적용사례를 찾아보고 공부해야 함

- manifest
    - Application
    - package : example 들어가면 등록 안됨
    - icon
    - label

- gradle
    - compileSdkVersion
    - buildToolsVersion
    - defaultConfig
        - applicationId
        - targetSdkVersion
        - minSdkVersion : 이번 버전과 이후 버전에 대한 코드가 모두 분기되어야 함
        - versionCode
        - versionName
    - signingConfigs
        - debuggable : false
        - storeFile
        - storePassword
        - keyAlias
        - keyPassword
    - buildTypes : 처음 설계부터 디버깅용과 릴리즈용을 미리 생각해야 한다
        - debug
            - buildConfigField
        - release
            - buildConfigField
            - signingConfig signingConfigs.release
            - minifyEnabled false
            - proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        - buildTypes.each

- 어플리케이션 보안
    - .gitignore
    - gradle.properties
    - keyStore 관리

- 버전관리
    - Git

- 관리 툴
    - 메모리 누수 툴

### (2) __설계 단위__

    설계 패턴 또한 오픈소스 적용 사례와 다른 코드를 많이 비교해 보면서 계속 공부해야 함
    설계 패턴이나 패키지 관리의 경우 공부하지 않으면 계속 하나의 패턴에 얽매이고 벗어나지 못하게 될 수 있다.

- 설계 패턴
    - MVC
    - MVP
    - MVVM
    - 반응형(Rx)

- 패키지 관리 : 설계 패턴과 서비스 특성, 데이터와 뷰 관리의 용의성과 성능에 입각해 어떻게 모듈화 할 것인지 결정
    - domain
    - remote
    - util
        - Log
        - Notice
        - Go
        - .Permission
        - Device
        - Network : 모든 서비스에서 인터넷 연결 확인해야함
        - Remote
        - Dialog
        - Validate
    - adapter
    - custom
    - activity, fragment(presenter)
        - BaseActivity
        - .controller/presenter
        - .service
    - view
    - exception

- domain(데이터베이스)
    - local 통신 : SQLite 그대로 사용할 것인지 데이터베이스에 객체로 접근 가능한 ORM을 사용할 것인지 선택
        - DBHelper
        - Model
        - DAO : 데이터베이스와 통신은 비동기로 처리해준다
    - server 통신
        - Model
        - Info
        - RESULT
    - 임시저장소
        - Const
    - 영구저장소
        - SharedPreferences
        - Files
    - 캐싱처리 : 서버나 로컬, 파일로 받아온 데이터를 최대한 재사용 할 수 있는 방향으로 설계한다.

- 네트워크통신
    - 통신 방법 : 설계 패턴과 서비스 특성에 따라 어떻게 서버와 통신할 것인지 선택한다.
        - (Socket)
        - (HttpUrlConnection)
        - OkHttp
        - Retrofit
        - (*Rx Observable)
    - 통신 클래스
        - ServiceGenerator : 통신 객체 생성. 라이브러리를 사용할 경우 필요
        - IRemoteService : 통신 URL, METHOD 설정
        - RemoteService : LauncherService, SignInService와 같이 각 클래스의 네트워킹만을 담당해주는 클래스. 주로 액티비티에서 분리해서 서비스를 만드는데 싱글턴으로 사용할 경우액티비티의 자원을 넘겨줘야 하기 때문에 해당 액티비티 자원이 static 메모리에 올라간다. 따라서 액티비티를 종료한 이후에도 계속 남아있을 수 있다. 다른 곳에서도 빈번히 호출하거나 미리 메모리에 올라가 있어야 한다면 필요하지만 그렇지 않다면 의미없이 싱글턴을 사용하지 않는다.
        - RemoteUtil : 사진업로드와 같이 기능이 정해져 있는 경우, 다른 곳에서도 호출할 경우 Util 클래스에 구현한다
    - 보안
        - Apache
        - HTTPS
        - OAuth 2.0(토큰 인증&권한)

- 인터페이스, 추상클래스 설계
    - 미리 각 클래스를 설계해 보는 것이 좋다. 통신이 매끄러워지고 재사용성이 높아지며 다형성을 통해 객체지향적 구현이 일부 가능해진다.
    - BaseActivity
    - GlobalActivity

- Error, Crash 설계
    - error : KakaoException, FacebookException과 같이 각 모듈은 필요한 상황에 맞게 예외를 커스터마이징 해야 한다.
    - crash : 앱이 다운로드 받아진 후 예외가 발생했을 때 보고받기 위해 Fabric 사용한다. throw 상황에서 Crashlytics.logException(e)을 호출하면
    에러상황을 보고받을 수 있다.

- 객체지향과 디자인패턴

### (3) __구현 단위__

- 클래스
    - 유틸화
    - 모듈화
    - 재사용
- 메소드
    - 유틸화
    - 모듈화
    - 재사용
- 비동기(스레드 중심)
    - *Thread : 할 수 있는 한 메인UI 제외 전부
    - Async
    - (*Observable)
- 생명주기 : 생명주기를 관리한다는 것은 메모리에 올라갈 때부터 메모리에서 제거될 때까지 관리해준다는 것을 뜻한다. 동시에 메모리를 신경쓰도록 한다
    - *static : 특히 싱글턴이나, 디자인패턴, 외부 라이브러리에서 static 사용 하는 경우 더이상 사용되지 않는 상황까지도 생명주기를 확인해야 한다
    - *new : new를 해놓고 관리해 주지 않으면 메모리 누수, 리소스 낭비를 막을 수 없다
    - 액티비티 : 액티비티와 함께 메모리에 올라간 것들은 반드시 그 생명주기와 함께 제거해 줘야 한다. 특히 외부 라이브러리의 경우
    static으로 메모리에 올라가는 경우가 있는데 반드시 처리해줘야 한다.
    - 프래그먼트
    - 뷰
- 퍼포먼스 : 종합영역
    - *재사용성
    - 메모리
    - *생명주기
    - *비동기
    - 캐싱
    - 오버헤드
- .gitignore


### (4) __Resource 단위__

- res
    - 언어/국가
    - anim
    - layout
    - drawable
    - mipmap
    - value : 프로젝트를 진행하면서 추가하는 부분도 있지만 시작 전에 미리 설계해야 하는 부분이 있음
        - strings
        - dimens
        - styles : 뷰가 정해졌으면 스타일을 먼저 바꾼다. 스타일을 바꾸지 않으면 모든 액티비티, 프래그먼트, 뷰에 각각 적용해 줘야 함
        - colors : 공통된 부분은 미리 정해 놓는다.
        - secrets : 키
- assets
    - .sql
    - .ttf
    - .mp3

## __2. 구현__ :open_file_folder:

### (1) domain

    가장 먼저 서버에서 받아올 데이터를 다룰 기반을 만든다. helper, model, dao 구현

#### A. local 영역

- __DBHelper__

    - SQLiteOpenHelper 클래스 혹은 SQLiteOpenHelper를 상속받아서 만든 Helper 라이브러리((ORMLiteHelper)) 클래스
        ```java
        @Override
        public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            try {
                TableUtils.createTable(connectionSource, Coupon.class);
                TableUtils.createTable(connectionSource, Favorite.class);
                TableUtils.createTable(connectionSource, TempFavorite.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ```

- __Model__

    - 로컬 데이터베이스(ORMLite)
        - @DatabaseTable(tableName = "favorite_info") : 테이블명
        - @DatabaseField : 칼럼
        - @DatabaseField(id = true) : primary key 설정하지 않으면 select, delete, update 할 때 일일이 조건문 달아줘야 한다

    - 서버 통신(Retrofit)
        - @SerializedName("member_id") : 서버와 통신할 때는 반드시 서버에서 보내준 값과 이름이 같아야 한다. 다를 경우 어노테이션으로 맞춰줘야 한다.
        - public transient int onServer : GET, POST 할 때 값을 무시할 수 있다.
        - @Expose(deserialize = true/false || serialize = true/false) : transient는 온전히 무시되는 값이라면 받아올 때 혹은 보낼 때 값을 포함시킬지를 선택할 수 있다.

        ```java
        // ORMLite를 통해 테이블 연결
        @DatabaseTable(tableName = "favorite_info")
        public class Favorite {

            // 필드값으로 선언된 것만 테이블 칼럼으로 들어간다
            @DatabaseField
            @SerializedName("member_id") public String memberId;
            // ORMLite는 select, update, delete 모두 id값으로 접근한다. 따라서 즐겨찾기 값을 구분해주는 id를 쿠폰의 키 값으로 설정해준다
            @DatabaseField(id = true)
            // 서버와 통신할 때는 json의 키 값과 선언된 이름이 대소문자까지 완전히 같아야 한다. 따라서 이름이 다를 경우 @SerializedName 어노테이션을 설정해준다
            @SerializedName("parent_no") public int parentNo;
            @SerializedName("reg_date") public String regDate;
            @DatabaseField
            @SerializedName("on_server") public int onServer;
        }
        ```

- __DAO__

    - MySql 데이터베이스 할 때도 그렇지만 디테일하게 쿼리를 날리지 못하면 리소스를 심하게 낭비할 수 있다. SQL문 쿼리와 라이브러리 최소한의 쿼리문은 숙지해야 한다.

    - create
        ```java
        // 한 개 생성
        public void create(Favorite favorite) {
            try {
                dao.createIfNotExists(favorite);
            } catch (SQLException e) {
                Crashlytics.logException(e);
            }
        }

        // 다중 생성
        public void create(List<Favorite> favoriteList) {
            try {
                dao.create(favoriteList);
            } catch (SQLException e) {
                Crashlytics.logException(e);
            }
        }
        ```

    - read
        ```java
        // 테이블 전체 읽어오기
        public List<Favorite> readAll() {
            List<Favorite> favoriteList = new ArrayList<>();
            try {
                favoriteList = dao.queryForAll();
            } catch (SQLException e) {
                Crashlytics.logException(e);
            }
            return favoriteList;
        }

        // value로 쿼리
        public List<Coupon> readByValue(String columnName, String value) {
            QueryBuilder<Coupon, Integer> builder = dao.queryBuilder();
            List<Coupon> datas = null;
            try {
                datas = builder.where().eq(columnName, value).query();
            } catch (SQLException e) {
                Crashlytics.logException(e);
            }
            return datas;
        }
        ```

    - update
        ```java
        public void updateChecked(int parentNo, boolean value) {
            UpdateBuilder<Coupon, Integer> builder = dao.updateBuilder();
            try {
                builder.where().eq(Const.COLUMN_NO, parentNo);
                builder.updateColumnValue(Const.COLUMN_IS_FAVORITE, value);
                builder.update();
            } catch (SQLException e) {
                Crashlytics.logException(e);
            }
        }

        public void updateUnChecked() {
            UpdateBuilder<Coupon, Integer> builder = dao.updateBuilder();
            try {
                builder.updateColumnValue(Const.COLUMN_IS_FAVORITE, false);
                builder.update();
            } catch (SQLException e) {
                Crashlytics.logException(e);
            }
        }
        ```

    - delete
        ```java
        // model에서 설정한 아이디 값으로 찾아서 삭제한다. 아이디 설정이 없으면 삭제되지 않는다.
        public void deleteAll() {
            List<Favorite> favoriteList = readAll();
            try {
                dao.delete(favoriteList);
            } catch (SQLException e) {
                Crashlytics.logException(e);
            }
        }
        ```

- __영구저장소__

    - Files
        ```java
        public static File getFilesDir(Context context) {
            String state = Environment.getExternalStorageState();
            File file = null;
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                file = context.getExternalFilesDir(null);
            } else {
                file = context.getFilesDir();
            }
            return file;
        }

        public static File getPNGFile(Context context, String name) {
            return new File(FileUtil.getFilesDir(context), name + ".png");
        }

        public static File getJPEGFile(Context context, String name) {
            return new File(FileUtil.getFilesDir(context), name + ".jpeg");
        }
        ```

    - SharedPreferences
        ```java
        private static SharedPreferences getPreference(Context context) {
            return context.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE);
        }

        private static SharedPreferences.Editor getPreferenceEditor(Context context) {
            if (sp == null) {
                sp = getPreference(context);
            }
            return sp.edit();
        }

        public static void putString(Context context, String key, String value) {
            sp = getPreference(context);
            editor = getPreferenceEditor(context);
            editor.putString(key, value);
            editor.commit();
        }

        public static String getString(Context context, String key) {
            sp = getPreference(context);
            return sp.getString(key, "");
        }
        ```


- __임시저장소__

    - 메모리
        ```java
        private void mapCategoryAndList(){
            String[] mainCategory = getMainCategoryArray();
            categorizedListMap = new HashMap<>();
            categorizedListMap.put(mainCategory[0], totalList);

                    ```
        }

        private void mapCategoryAndHasChange(){
            String[] mainCategory = getMainCategoryArray();
            hasChangeMap = new HashMap<>();
            hasChangeMap.put(mainCategory[0], totalHaschange);

                    ```
        }
        ```
    - Const

#### B. Server 영역

- __Info__ : 서버 응답 객체. 먼저 서버가 구현되어 있어야 설계할 수 있다. 서버에서 정확하게 정해지지 않고 중간에 바뀌면 클라이언트상에서 도메인부터 모든 적용 대상을 바꿔야 하기 때문에 서버 변동 여부를 꼭 확인해야 한다.

    - token
    - totalCount
    - RESULT
        - CODE
        - MESSAGE
    - data[]

        ```java
            private RESULT RESULT;
            private String token;
            private String totalCount;
            private RESULT RESULT;
            private Coupon[] data;

                ```
            @Override
            public String toString() {
                return "ClassPojo [RESULT = " + RESULT + ", totalCount = " + totalCount + ", coupon = " + coupon + "]";
            }
        ```

- __RESULT__

    - CODE
    - MESSAGE

        ```java
            private String MESSAGE;
            private String CODE;

                ```
        ```

- 이미지 받아오기

    - 이미지, 음악, 동영상은 모두 바이트 스트림으로 넘어오기 때문에 저장할 경우 바이트 배열로 받아서 저장한다
        ```java
            public String type;
            public byte[] data;
        ```

### (2) Launcher

    크게 세가지 처리를 함. 권한설정, 데이터, 처음확인. 자꾸 헷갈리는 것 같은데 런처에서는 로그인 상태를 확인하지 않는다. 방문자 입장으로 해결하고 나머지 요청에서 유효성 검사

- __WorkFlow__

    - a. 권한체크 : 처음에는 위치권한과 쓰기권한, 디바이스 정보권한을 받아서 권한이 있을 경우만 다음 페이지로 넘어갈 수 있도록 하려고 했으나 사용자
    입장에서는 첫 화면에서 권한을 받는 것이 거부감이 들 수 있다. 따라서 처음에는 최대한 서비스를 바로 이용할 수 있도록 하고 어느정도 서비스를 이용하다가 필요에 의해 권한을 받도록 한다. 권한을 받을 때 왜 권한을 받는지 이유를 대화상자로 띄워주면 거부감을 많이 줄일 수 있다. 쓰기권한은 default 위치설정(서울)을 한 다음 사용자가 서비스를 사용해 보고 사용자가 필요하다고 느낄 때 위치를 변경하면서 받고, 내부저장소에 저장할 경우는 쓰기권한이 필요 없으며, 외부에 저장할 경우는 위치설정처럼 서비스 사용중 필요에 의해 받는 경우가 대부분이기 때문에 필요할 때 권한처리를 받는다. 물론 권한이 없으면 서비스가 진행되지 않는 상황이라면 반드시 entry에서 권한을 받아야 하고 그에 대한 합당한 이유를 대화상자나 페이지를 통해 꼭 명시하도록 한다
        - util.Permission 패키지에 각 권한을 분리해준다. 각 권한을 받는 경우와 상황이 다르기 때문에 한꺼번에 받을 수 없다.
            - util.Permission.Location
            - util.Permission.DeviceInfo
            - util.Permission.ExternalStorage
        - PREF_KEY_PERMISSION_GRANTED : 권한을 승인받았다면 SharedPreferences에 권한 승인을 받았음을 저장해둔다. 다만 이렇게 하면 설정에서 앱 권한을 차단해버렸을 경우 능동적으로 대처할 수 없다. SharedPreferences 이외 입장할 때마다 권한을 체크해 줄 수 있지만 런처에서 시간이 많이 걸리면 안 되기에 가장 좋기는 각 상황별 사용자의 선택에 따라 사용할 때마다 권한을 체크해 주고 권한이 있으면 넘어가도록 하는 것이다.

    - b. 인터넷 연결 : 권한 설정 없이 바로 사용 가능. Remote 관련 메소드에서는 일련의 과정을 거친 후 연결되지 않았음을 콜백으로 인지하기 때문에 시간이 많이 걸린다. 따라서 인터넷이 연결되지 않았거나 WIFI가 필요한 경우 연결 상태만을 빠르게 검사해서 Remote 관련 메소드를 여러개 호출하지 않도록 한다.

    - c. 연결 타입 확인 : 데이터를 받아오기 때문에 wifi인지 확인해줘야 함

    - d. 버전체크 : 로컬에 저장된 쿠폰을 최신으로 유지하기 위해 서버와 통신할 때 버전이 같으면 넘어가고 다르면 서버에서 데이터를 받아온다. 받아온 버전은 바로 저장하지 않고 로고파일까지 전부 저장이 되었을 때 SharedPreferences에 저장해준다. 이렇게 함으로써 중간에 데이터를 받아오다가 문제가 생기는 경우 버전이 최신으로 변경되지 않았기 때문에 다시 데이터를 받아올 수 있다.

    - e. 데이터 : 버전이 다를 경우 받아오는 데이터

    - f. 로고(이미지)파일 : 쿠폰 데이터를 다 받아온 후 쿠폰 데이터에서 로고파일 이름만 받아서 하나씩 다시 요청한다. 하나씩 요청함으로써 로고파일 하나하나에 대한 성공여부를 정확하게 확인할 수 있고 중간에 실패하더라도 실패한 부분만 다시 받아올 수 있다.

    - g. 처음 입장인지 확인 : PREF_KEY_FIRST_ENTRY를 통해 Introduction 페이지로 넘어갈 것인지 확인한다

    - h. 다음화면으로 넘어감

- __LauncherService__

- __구현__

    - 인터넷 연결확인
        ```java
        if(NetworkUtil.isConnected(this)) {
            // 여기서는 싱글턴을 사용할 상황이 아니다. 함부로 싱글턴 사용해서 정적영역에 메모리 올리지 말자.
            launcherService = new LauncherService(this, this);
            launcherService.getServerVersion();
            return;
        }
        checkIsFirstEntry();
        ```

    - 버전확인 : 버전이 다를 경우 데이터를 받아와야 하기 때문에 모바일데이터, WIFI를 확인한다
        ```java
        if (localVersion == serverVersion) {
            checkIsFirstEntry();
        } else {
            checkNetworkStatus();
        }
        ```

    - 연결타입 확인
        ```java
        int status = NetworkUtil.checkNetworkStatus(this);
        switch (status) {
            case ConnectivityManager.TYPE_WIFI:
                updateData();
                break;
            case ConnectivityManager.TYPE_MOBILE:
                showNetworkStatusDialog();
                break;
            default:
                checkIsFirstEntry();
                break;
        }
        ```

    - 로컬 데이터 삭제

    - 서버에서 쿠폰 데이터 받아오기 & 로컬에 저장

    - 로고파일 내부저장 경로 설정
        ```java
        for (Coupon coupon : couponList) {
            coupon.logoFilePath = getFileStreamPath(coupon.logoFilename).getPath();
        }
        ```

    - 로고 파일 존재 확인
        ```java
        @Override
        public void checkLogoFileExists() {
            if (iterator == null) iterator = couponList.iterator();
            if (iterator.hasNext()) {
                String logoFileName = iterator.next().logoFilename;
                logoFile = getFileStreamPath(logoFileName);
                if (logoFile.exists()) {
                    checkLogoFileExists();
                    return;
                }
                callLogoService(logoFileName);
                return;
            }
            // 로고파일까지 모두 확인한 후에 버전을 저장해주자
            saveServerVersion();
            checkIsFirstEntry();
        }
        ```

    - 서버에서 로고 받아오기
        ```java
        private void callLogoService(String logoFileName) {
            launcherService.loadLogoFile(logoFileName);
        }

        @Override
        public void createLogoFile(LogoImageInfo info) {
            try {
                OutputStream fos = new FileOutputStream(logoFile);
                fos.write(info.getLogoImage().data);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                Crashlytics.logException(e);
            }
            checkLogoFileExists();
        }
        ```

    - 로컬에 서버 데이터 버전 저장

    - 처음 입장인지 확인

    - 다음 페이지로 넘어감

### (3) 로그인/회원가입 & 회원관리

- 로컬과 서버연동의 이해 선행 : 로컬은 로컬대로, 서버는 원래 하던대로 통신하면 된다. 연결이 끊어졌을 때 동기화 하는게 헷갈릴 수 있는데 로컬은 로컬대로, 리모트 통신윽 원래대로 하면 된다.
    - 아이디 확인
        - 회원아이디 확인 : 이미 로그인이 된 경우 로컬에 로그인 이력이 남는다. 만약 이 상황에서 인터넷에 연결되지 않았다면 그대로 서비스를 이용할 수 있도록 해줘야 한다. 다만 서버에 올라가지 않았음을 명시해주거나 테이블에 따로 저장해줘야 하고 서버에 연결됬을 때 먼저 토큰 유효성 확인하고 응답에 따라 재로그인 후 서버에 동기화하든지 바로 동기화 할 수 있다.
        - 회원아이디가 없을 경우 임시아이디 확인
        - 임시아이디도 없으면 아이디 생성 요구.
    - 로컬 데이터베이스에 반영 : 서버에 연결되든 되지 않든 일단 로컬데이터베이스는 반영이 돼야 함
    - UI(화면)에 반영 : 로컬 데이터베이스에 반영된 사항은 바로 화면에 반영
    - __서버에 반영__ : 인터넷 연결이 안 돼 있거나 서버 반영에 실패할 경우 로컬 데이터베이스에 실패한 데이터 목록을 유지하거나 데이터에서 status를 관리해준다
        - 임시아이디 : 대부분 경우 임시아이디라는 것이 거의 사용되지 않고, 임시아이디 자격으로 즐겨찾기를 사용하거나 좋아요 기능을 사용하지 않는다. 임시아이디를
        사용할 경우 토큰이 필요 없고 단순히 아이디와 정보값만 넘겨준다
        - 회원아이디 : 인증과 함꼐 받아온 토큰, 회원을 구분할 수 있는 키(아이디), 데이터를 넘겨준다

- Work Flow
    - 로컬 데이터베이스와 서버를 연동하는 경우나 데이터베이스를 서버에만 두는 경우나 서버와 연동하는 방법은 아래와 같이 동일하다.
    - __임시아이디__
    - __소셜로그인__
        - 방문자로 시작 : 공적 데이터를 보여주는 서비스인데 첫 화면부터 로그인이나 여러 권한처리를 요구하는 것은 소비자 입장에서 부담스럽다. 어떤 서비스인지
        믿을만한지도 모르는데 개인정보를 주기 꺼려지기 때문이다. 따라서 첫 입장은 방문자 자격으로 일반적인 데이터와 기능들을 둘러볼 수 있도록 한다. 개인페이지도
        들어가서 어떤 기능이 있는지는 보여주도록 한다.
        - 회원가입 : 회원가입을 하면서 받아온 정보를 HTTPS로 서버사이드에 전달 후 저장(다만 소셜 로그인은 회원가입과 로그인이 동시에 진행됨)
        - 로그인 : 회원가입, 로그인 하면서 모바일에서 발급받은 토큰을 리소스서버(서버사이드, 개발서버)에 HTTPS로 전달
        - 인증 : 서버사이드에서 구글, 페이스북, 카카오톡 등의 서비스에서 제공하는 인증서버를 통해 토큰 인증
        - 응답 : 서버에서 인증을 통해 받아온 아이디, 이름, 이메일, 전화번호 등 인증된 정보를 클라이언트에 보내주면 클라이언트는 토큰과 같이 SharedPreference에 저장한다. 따라서 인터넷이 연결되지 않은 상태에서도 바로 로그아웃이 되는게 아니라 모바일에 저장된 정보에 의해(토큰이나 아이디가 존재하면) 로그인이 된 상태로 보인다. 추후 서버를 이용해야만 하는 기능일 경우에 토큰으로 요청했다가 토큰이 만료가 되었으면 그 때 로그아웃 페이지로 넘겨준다.
        - 서비스 이용중
            - 로그인을 한 후 다시 앱에 들어올 때 : 들어올 때마다 로그인이 되었는지 확인해야 하는가 하는데 그것은 경우에 따라 다르다. 만약 런처에서 많은 정보를 받아봐야 하거나, 메인 페이지에 바로 개인 데이터가 노출될 경우 앱에 들어갈 때 먼저 토큰이 있는지 조사하고 그에 따른 요청을 한다. 두 경우가 아니라면 굳이 앱에 입장할 때마다 인증되었는지 확인할 필요는 없을 듯 하다.
            - 로그인 이력이 있는 상황에서 인터넷 연결이 되지 않은 경우 : 물론 서비스에 따라 다르다. 카톡은 연결이 되지 않으면 서비스를 이용할 수 없고, 에버노트의 경우는 연결이 되지 않아도 기록 기능을 사용할 수 있다. 일단 로그인 이력이 있기 때문에 그대로 진행하고 추가된 내용은 따로 테이블에 저장하거나 데이터에 status로 표시를 해 둘 수 있다. 그 후 연결이 되면 가장 먼저 토큰 유효성을 검사하고 그 후 서버에 반영되지 못한 데이터를 동기화한다.
            - 비정기적인 광고는 어떻게 설계하는가 : 대다수 어플리케이션은 평소에는 그대로 입장하다가 이벤트 기간에 입장하면 광고페이지가 뜨는 것을 볼 수 있다. 이는
            앱을 설계할 때 방문자 자격에서도 항상 서버에 이러한 응답을 받을 수 있도록 한 것이다. 평소에는 매번 요청하지만 아무런 응답이 없기 때문에 띄우지 않다가 이벤트 기간이 되면
            띄워주는 것이다.
        - 토큰 : 소셜로그인도 토큰을 일단 발급받으면 ShardPreferences에 저장해 둔다. 모바일에서 계속 받아서 사용하지 않고 서버에 보내서 서버에서 인증하도록 한다. 참고로 모든 서버 요청은 토큰으로 인증을 하고 나머지를 진행한다.
        - 로그아웃 : 개인 선택에 의해서든 토큰 만료에 의해서든 로그아웃을 할 경우 먼저 소셜로그인 로그아웃을 해서 세션을 삭제하고 완료 후 저장된 토큰과 개인정보를 지운다
    - __일반로그인__
        - 방문자로 시작 : 공적 데이터를 보여주는 서비스인데 첫 화면부터 로그인이나 여러 권한처리를 요구하는 것은 소비자 입장에서 부담스럽다. 어떤 서비스인지
            믿을만한지도 모르는데 개인정보를 주기 꺼려지기 때문이다. 따라서 첫 입장은 방문자 자격으로 일반적인 데이터와 기능들을 둘러볼 수 있도록 한다. 개인페이지도
            들어가서 어떤 기능이 있는지는 보여주도록 한다.
        - 회원가입 : 회원가입을 하면서 받아온 정보를 서버사이드에 저장
        - 로그인 : 회원가입, 로그인 하면서 자체적으로 발급한 토큰을 모바일에 저장해 뒀다가 리소스서버(서버사이드, 개발서버)에 HTTPS로 전달
        - 인증 : 자체적인 서버사이드에서 토큰 인증
        - 응답 : 서버에서 인증을 통해 받아온 아이디, 이름, 이메일, 전화번호 등 인증된 정보를 클라이언트에 보내주면 클라이언트는 토큰과 같이 SharedPreference에 저장한다. 따라서 인터넷이 연결되지 않은 상태에서도 바로 로그아웃이 되는게 아니라 모바일에 저장된 정보에 의해(토큰이나 아이디가 존재하면) 로그인이 된 상태로 보인다. 추후 서버를 이용해야만 하는 기능일 경우에 토큰으로 요청했다가 토큰이 만료가 되었으면 그 때 로그아웃 페이지로 넘겨준다.
        - 서비스 이용중
            - 로그인을 한 후 다시 앱에 들어올 때 : 들어올 때마다 로그인이 되었는지 확인해야 하는가 하는데 그것은 경우에 따라 다르다. 만약 런처에서 많은 정보를 받아봐야 하거나, 메인 페이지에 바로 개인 데이터가 노출될 경우 앱에 들어갈 때 먼저 토큰이 있는지 조사하고 그에 따른 요청을 한다. 두 경우가 아니라면 굳이 앱에 입장할 때마다 인증되었는지 확인할 필요는 없을 듯 하다.
            - 로그인 이력이 있는 상황에서 인터넷 연결이 되지 않은 경우 : 물론 서비스에 따라 다르다. 카톡은 연결이 되지 않으면 서비스를 이용할 수 없고, 에버노트의 경우는 연결이 되지 않아도 기록 기능을 사용할 수 있다. 일단 로그인 이력이 있기 때문에 그대로 진행하고 추가된 내용은 따로 테이블에 저장하거나 데이터에 status로 표시를 해 둘 수 있다. 그 후 연결이 되면 가장 먼저 토큰 유효성을 검사하고 그 후 서버에 반영되지 못한 데이터를 동기화한다.
            - 비정기적인 광고는 어떻게 설계하는가 : 대다수 어플리케이션은 평소에는 그대로 입장하다가 이벤트 기간에 입장하면 광고페이지가 뜨는 것을 볼 수 있다. 이는
            앱을 설계할 때 방문자 자격에서도 항상 서버에 이러한 응답을 받을 수 있도록 한 것이다. 평소에는 매번 요청하지만 아무런 응답이 없기 때문에 띄우지 않다가 이벤트 기간이 되면
            띄워주는 것이다.
        - 토큰 : 로컬에 저장된 모든 요청에는 토큰을 함께 보내준다.
        - 로그아웃 : 개인 선택에 의해서든 토큰 만료에 의해서든 로그아웃을 할 경우 저장된 토큰과 개인정보를 지운다.

- __SigninService__

- __Kakao Login__

    - Application에서 세션 초기화

    ```java
    public static void initKakao(){
        KakaoSDK.init(new KakaoSDKAdapter());
    }
    ```

    - 세션 클래스 : 로그인되어 세션 생성시 호출되는 콜백 메서드 정의, 성공시 토큰도 같이 가져온다
    ```java
    private static class KakaoSessionCallback implements ISessionCallback {

        Activity activity;
        ISignInCallback signInCallback;

        public KakaoSessionCallback(Activity context, ISignInCallback signInCallback) {
            this.activity = context;
            this.signInCallback = signInCallback;
        }

        @Override
        public void onSessionOpened() {
            // 현재 모바일에 로그인 된 정보가 있는지 확인함
            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    NoticeUtil.makeToast(activity, "카카오톡 사용자 정보가 없습니다");
                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        activity.finish();
                    } else {
                        GoUtil.startActivity(activity, SignInActivity.class);
                    }
                }

                        ```

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    LogUtil.e(TAG, "kakao onSuccess");
                    String token = Session.getCurrentSession().getAccessToken();
                    signInCallback.searchMember(Const.KAKAO, userProfile.getId() + "", userProfile.getNickname());
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            LogUtil.e(TAG, "kakao onSessionOpenFailed");
            Crashlytics.logException(exception);
            NoticeUtil.makeToast(activity, "카카오톡 연결오류가 발생했습니다");
        }
    }
    ```

     - Kakao login 콜백 등록
    ```java
    public static void initKakao(Activity activity, ISignInCallback signInCallback) {
        /** 카카오 로그인 */
        kakaoCallback = new KakaoSessionCallback(activity, signInCallback);
        Session.getCurrentSession().addCallback(kakaoCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }
    ```

    - 로그인 : onClickSignup 하면 초기화하면서 등록해 둔 콜백 클래스의 onSessionOpened()가 콜백으로 호출된다
    ```java
    public void loginKakao(View view) {
        if(!NetworkUtil.isConnected(this)) return;
        KakaoUtil.onClickSignup(this);
    }
    ```

     - onActivityResult

    ```java
    Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)
    ```

    - searchMember : 로그인과 함께 받아온 토큰, 아이디를 서버로 보내주면 서버사이드에서 아래의 주소로 요청하여 토큰이 유효한지 확인하고 토큰이 유효하면 가입한 아이디인지 확인한다.
        - 가입이 안 된 경우 : 응답으로 주어진 id만 저장하거나 두번째 URL로 요청하여 추가적인 정보를 받아온다. 받아온 필요한 정보(id, name) 등을 HTTPS로 클라이언트에 응답해주고 안드로이드에서는 SharedPreferences에 저장해서 토큰이 인증되기 전에 로그인이 되어 있음을 UI로 확인할 수 있게 해준다
        - 이미 가입 된 경우 : 즐겨찾기 정보와 함께 모바일에 저장할 회원 정보를 보내준다. 인증을 서버에서 해주면 클라이언트와 2-3번씩 요청을 주고받을 필요 없음.
        - 가입 실패 : 가입 실패로 응답을 보내주고 로그아웃, 세션정리 필요하다면 다시 탈퇴 해준다.
        - [참고](https://developers.kakao.com/docs/android/getting-started)
        ```java
        GET /v1/user/access_token_info HTTP/1.1
        Host: kapi.kakao.com
        Authorization: Bearer {access_token}
        Content-type: application/x-www-form-urlencoded;charset=utf-8
        ```

        ```java
        GET/POST /v1/user/me HTTP/1.1
        Host: kapi.kakao.com
        Authorization: Bearer {access_token}
        Content-type: application/x-www-form-urlencoded;charset=utf-8
        ```

    ```java
    public void searchMember(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.searchMemberInfo(id);
        call.enqueue(searchMemberInfoCallback);
    }
    ```

    - insertMember : searchMember에서 설계한 대로 하지 않을 경우 아래와 같이 3-4회로 나눠서 요청해야 한다.

    ```java
    public void insertMember(Member member, String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.insertMemberInfo(member, PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(insertMemberInfoCallback);
    }
    ```

    - selectFavorite

    ```java
    public void selectFavorite(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.selectFavoriteInfo(id);
        call.enqueue(selectFavoriteInfoCallback);
    }
    ```

    - updateFavorite

    ```java
    public void updateFavorite(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.updateTempFavorite(id, PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(updateFavoriteInfoCallback);
    }
    ```

    - deleteSignedUpFavorite

    ```java
    public void deleteSignedUpFavorite(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.deleteSignedUpFavorite(id, PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(deleteSignedUpFavoriteCallback);
    }
    ```

    - deleteTempFavorite

    ```java
    public void deleteTempFavorite(boolean isFirst, String id, String name) {
        this.isFirst = isFirst;
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<FavoriteInfo> call = service.deleteTempFavorite(PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID));
        call.enqueue(deleteTempFavoriteCallback);
    }
    ```

- __Facebook Login__

    - 콜백 세션 클래스

    ```java
     public static class FacebookSessionCallback implements FacebookCallback<LoginResult> {

        Activity activity;
        ISignInCallback signInCallback;

        public FacebookSessionCallback(Activity activity, ISignInCallback signInCallback) {
            this.activity = activity;
            this.signInCallback = signInCallback;
        }

        @Override
        public void onSuccess(LoginResult loginResult) {

            // String token = AccessToken.getCurrentAccessToken().getToken();
            // String token = loginResult.getAccessToken().getUserId()

            GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        signInCallback.searchMember(Const.FACEBOOK, object.getString("id"), object.getString("name"));
                    } catch (JSONException e) {
                        Crashlytics.logException(e);
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
            AccessToken.getCurrentAccessToken();
        }

            ```
    }
    ```

     - Facebook login 콜백 등록
    ```java
    public static void initFacebook(Activity activity, ISignInCallback signInCallback) {
        facebookCallback = new FacebookSessionCallback(activity, signInCallback);
        facebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(facebookCallbackManager, facebookCallback);
    }
    ```

    - Facebook login
    ```java
    public static void signInFacebook(Activity activity, ISignInCallback signInCallback) {
        if (facebookCallback == null) {
            initFacebook(activity, signInCallback);
        }
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
    }
    ```

    - onActivityResult

    ```java
    facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    ```

    - searchMember : 로그인과 함께 받아온 토큰, 아이디를 서버로 보내주면 서버사이드에서 아래의 주소로 요청하여 토큰이 유효한지 확인하고 토큰이 유효하면 가입한 아이디인지 확인한다.
        - 가입이 안 된 경우 : 응답으로 주어진 id만 저장하거나 두번째 URL로 요청하여 추가적인 정보를 받아온다. 받아온 필요한 정보(id, name) 등을 HTTPS로 클라이언트에 응답해주고 안드로이드에서는 SharedPreferences에 저장해서 토큰이 인증되기 전에 로그인이 되어 있음을 UI로 확인할 수 있게 해준다
        - 이미 가입 된 경우 : 즐겨찾기 정보와 함께 모바일에 저장할 회원 정보를 보내준다. 인증을 서버에서 해주면 클라이언트와 2-3번씩 요청을 주고받을 필요 없음.
        - [참고](https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow#checktoken)
        ```
        https://graph.facebook.com/app?access_token=TOKEN
        ```
        ```java
        GET /debug_token?
        input_token={input-token}&
        access_token={access-token}
        ```

    ```java
    public void searchMember(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.searchMemberInfo(id);
        call.enqueue(searchMemberInfoCallback);
    }
    ```

- __Google login__

    - Google signin 초기화
    ```java
    public static void initGoogle(final FragmentActivity activity) {
        /** 구글 로그인 */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        NoticeUtil.makeToast(activity, "구글 로그인 오류");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    ```
    - Google signin
    ```java
    public static void googleSignIn(FragmentActivity activity) {
        if (googleApiClient == null) {
            initGoogle(activity);
        }
        Intent signin = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        activity.startActivityForResult(signin, Const.GOOGLE_SIGN_IN);
    }
    ```

    - Google signin은 콜백 대신 activity result에서 직접 해준다
    ```java
    public static void handleSignIn(GoogleSignInResult result, ISignInCallback signInCallback) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            signInCallback.searchMember(Const.GOOGLE, account.getId(), account.getDisplayName());
        } else {
            Log.e(TAG, "Google 실패 : "+result.toString());
        }
    }
    ```
    - searchMember : 로그인과 함께 받아온 토큰, 아이디를 서버로 보내주면 서버사이드에서 아래의 주소로 요청하여 토큰이 유효한지 확인하고 토큰이 유효하면 가입한 아이디인지 확인한다.
        - 가입이 안 된 경우 : 응답으로 주어진 추가적인 정보를 받아온다. 받아온 필요한 정보(id, name) 등을 HTTPS로 클라이언트에 응답해주고 안드로이드에서는 SharedPreferences에 저장해서 토큰이 인증되기 전에 로그인이 되어 있음을 UI로 확인할 수 있게 해준다
        - 이미 가입 된 경우 : 즐겨찾기 정보와 함께 모바일에 저장할 회원 정보를 보내준다. 인증을 서버에서 해주면 클라이언트와 2-3번씩 요청을 주고받을 필요 없음.
        - [참고](https://developers.google.com/identity/sign-in/android/backend-auth)
        ```javaScript
        var GoogleAuth = require('google-auth-library');
        var auth = new GoogleAuth;
        var client = new auth.OAuth2(CLIENT_ID, '', '');
        client.verifyIdToken(
            token,
            CLIENT_ID,
            function(e, login) {
            var payload = login.getPayload();
            var userid = payload['sub'];
        });
        ```

    ```java
    public void searchMember(String id, String name) {
        this.sortedId = id;
        this.name = name;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.searchMemberInfo(id);
        call.enqueue(searchMemberInfoCallback);
    }
    ```

- __일반 Login__

    - 받아온 비밀번호, 아이디를 HTTPS 통신으로 보내서 자체 서버에서 인증 후 토큰을 발급하고 필요한 멤버정보, 즐겨찾기 정보, 토큰을 응답값으로 보내준다

    ```java
     public void login(Member member) {
        this.sortedId = member.memberId;
        this.name = member.username;
        IRemoteService service = ServiceGenerator.createService(IRemoteService.class);
        Call<MemberInfo> call = service.login(member);
        call.enqueue(logInCallback);
    }
    ```


### (4) Main

- __MainService__

- __[CustomTabLayout](https://github.com/qskeksq/CustomTabIndicatorLayout)__

    - TabIndicator :  TabIndicator가 이미지가 아니기 때문에 canvas에 크기를 지정해서 그려줘야 한다. TabIndicator가 private로 오버라이드 할 수 없기 때문에 전체 코드를 재현하는 방법밖에 없다.

        ```java
        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            int temp_width = getChildAt(0).getWidth() / 5 * 2;
            if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {
                canvas.drawRoundRect(mIndicatorLeft + temp_width,
                        getHeight() - mSelectedIndicatorHeight * 3,
                        mIndicatorRight - temp_width,
                        getHeight() - mSelectedIndicatorHeight * 2,
                        mSelectedIndicatorHeight, mSelectedIndicatorHeight,
                        mSelectedIndicatorPaint);
            }
        }
        ```
    - setTypeFace : 탭의 속성을 담당하는 updateTextAndIcon에 폰트를 추가

        ```java
        private void updateTextAndIcon(Tab tab, TextView textView, ImageView iconView) {
                final Drawable icon = tab.getIcon();
                final CharSequence text = tab.getText();

                setTypeface(textView);

                ```
        }
        ```

    - OnTabSelectedListener : 커스텀으로 재현할 시 리스너를 따로 설정

        ```java
        private final ArrayList<OnTabSelectedListener> mSelectedListeners = new ArrayList<>();

        public void addOnTabSelectedListener(@NonNull OnTabSelectedListener listener) {
            if (!mSelectedListeners.contains(listener)) {
                mSelectedListeners.add(listener);
            }
        }

        public void removeOnTabSelectedListener(@NonNull OnTabSelectedListener listener) {
            mSelectedListeners.remove(listener);
        }

        public void clearOnTabSelectedListeners() {
            mSelectedListeners.clear();
        }

        private void dispatchTabSelected(@NonNull final Tab tab) {
            for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
                mSelectedListeners.get(i).onTabSelected(tab);
            }
        }

        private void dispatchTabUnselected(@NonNull final Tab tab) {
            for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
                mSelectedListeners.get(i).onTabUnselected(tab);
            }
        }

        private void dispatchTabReselected(@NonNull final Tab tab) {
            for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
                mSelectedListeners.get(i).onTabReselected(tab);
            }
        }
        ```

- __다중 뷰타입__

    ```java
        public class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

                    ```

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

                            ```

                }
            }

        }
    ```


- __메인화면 데이터 관리__

    - [비트맵 PreCaching](https://github.com/qskeksq/Precaching)

    - 데이터의 흐름
        - 서버 -> 로컬 데이터베이스 -> 메인화면에서 호출 -> 메모리에 저장(캐싱처리) -> 메모리에서 불러옴
        - 초기에는 탭으로 카테고리를 넘길 때마다 새로 로컬에서 데이터를 불러왔는데 그렇게 할 경우 리소스 낭비가 심하기 때문에 한 번 불러온 데이터는 임시저장소에 저장하고 로컬DB에 변경사항이 있을 경우에만 해당 임시저장소만 새로 호출한다. 원래는 임시저장소의 변경데이터만 변경하려고 했으나 그렇게 할 경우 변경된 곳에서 넘겨줘야 할 데이터가 많아지면 처리가 매우 복잡해지기 때문에 필요한 경우만 로컬에서 호출하도록 한다. 로컬데이터베이스에서 불러오지 않고 계속해서 서버에서 데이터를 받아오는 경우에도 서버에서 받아온 데이터를 임시저장소에 저장해두거나 캐싱처리를 해서 불필요한 호출이나 통신을 줄여준다.
        - 임시저장소와 함께 변경사항을 반영할 boolean 값을 맵핑해준다

    - 임시저장소(캐싱) 생성 & 맵핑

        ```java
        private void mapCategoryAndList(){
            String[] mainCategory = getMainCategoryArray();
            categorizedListMap = new HashMap<>();
            categorizedListMap.put(mainCategory[0], totalList);

                ```
            categorizedListMap.put(mainCategory[10], etcList);
        }
        ```
        ```java
        private void mapCategoryAndHasChange(){
            String[] mainCategory = getMainCategoryArray();
            hasChangeMap = new HashMap<>();
            hasChangeMap.put(mainCategory[0], totalHaschange);

                ```
            hasChangeMap.put(mainCategory[10], etcHasChange);
        }
        ```

    - 데이터 불러오기 : 기존에 불러온 적이 없거나 변경사항이 있을 경우는 로컬데이터베이스에서 불러오고, 나머지 경우는 기존에 임시저장소에 저장해 둔 데이터를 넘겨준다. 로컬이든 서버든 항상 전체 데이터를 받아올 수 없기 때문에 데이터베이스 설계할 때 정해 놓은 데이터 쿼리 칼럼으로 쿼리를 날린다. 여기서는 '메인 카테고리'로 쿼리해준다.

        ```java
        private List<Coupon> getCategorizedCouponList(int index) {
            CouponDao dao = getCouponDao();
            String[] mainCategoryArray = getMainCategoryArray();
            String mainCategory = mainCategoryArray[index];
            switch (index){
                case Const.MAIN_CATEGORY_ALL:
                    if(categorizedListMap.get(mainCategory) == null || hasChangeMap.get(mainCategory) == true){
                        List<Coupon> categorizedList = dao.readAll();
                        categorizedListMap.put(mainCategory, categorizedList);
                        hasChangeMap.put(mainCategory, false);
                        return categorizedList;
                    }
                default:
                    if(categorizedListMap.get(mainCategory) == null || hasChangeMap.get(mainCategory) == true){
                        List<Coupon> categorizedList = dao.readByValue(Const.COLUMN_MAIN_CATEGORY, mainCategory);
                        categorizedListMap.put(mainCategory, categorizedList);
                        hasChangeMap.put(mainCategory, false);
                        return categorizedList;
                    }
            }
            return categorizedListMap.get(mainCategory);
        }
        ```

    - 데이터 갱신 : 즐겨찾기, 로그인 상에서 데이터 갱신이 있을 경우(Observable 적용해보자)

        ```java
        public void notifyMainFavoriteChanged(String mainCategory) {
            if (currentPosition == 0) {
                notifyFavoriteChanged(mainCategory);
            } else {
                notifyCategoryAllChanged();
            }
        }

        // 특정 카테고리
        public void notifyFavoriteChanged(String mainCategory) {
            hasChangeMap.put(mainCategory, true);
        }

        // 전체 카테고리만
        public void notifyCategoryAllChanged(){
            hasChangeMap.put(getMainCategoryArray()[0], true);
        }

        // 카테고리 전체
        public void notifyFavoriteSetChanged(){
            for(String category : getMainCategoryArray()){
                hasChangeMap.put(category, true);
            }
        }

        // 카테고리 몇 개
        public void notifyFavoriteSetChanged(String[] changedCategory){
            for(String category : changedCategory){
                notifyFavoriteChanged(category);
            }
            notifyCategoryAllChanged();
        }
        ```

        ```java
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == Const.REQ_CODE_LOGIN && resultCode == RESULT_OK) {
                setSignInProfile(data.getStringExtra(Const.GOUTIL_EXTRA_2));
                boolean hasChange = data.getBooleanExtra(Const.GOUTIL_EXTRA_1, false);
                if (hasChange) {
                    notifyFavoriteSetChanged();
                    setCurrentData(currentPosition);
                }
            } else if (requestCode == Const.REQ_CODE_FAVORITE && resultCode == RESULT_OK) {
                boolean hasChange = data.getBooleanExtra(Const.GOUTIL_EXTRA_1, false);
                if (hasChange) {
                    notifyFavoriteSetChanged(data.getStringArrayExtra(Const.GOUTIL_EXTRA_2));
                    setCurrentData(currentPosition);
                }
            }
        }
        ```

- __로그인 이력 관리__

    - 로그인아이디나 토큰이 있으면 프로필 설정 해준다

        ```java
        private void setNaviProfile() {
            if (SignInUtil.hasSignedIn(PreferenceUtil.getString(this, Const.PREF_KEY_ID))) {
                naviName.setVisibility(View.VISIBLE);
                naviName.setText(PreferenceUtil.getString(this, Const.PREF_KEY_NAME));
                naviLogin.setText(getTempResources().getString(R.string.navigation_logout));
            } else {
                naviName.setText(getTempResources().getString(R.string.navigation_login));
            }
        }
        ```

    - 로컬에서 로그인 확인 : 이전에 로그인이 됬었음을 알려주는 것인데, 로컬에서 로그인이 된 것으로 인터넷에 연결이 돼 있는 상황이면 서버에 요청할 때마다 토은 유효성을 검사하고, 연결되지 않은 상황이면 일단 로그인 이력을 검사해서 서비스를 진행할 수 있도록 한다. 연결되지 않은 상황에서 추가된 데이터, 변경 사항은 따로 데이터베이스에 저장하거나 데이터에 표시해서 연결시 동기화를 진행한다.

        ```java
        private boolean checkSignInStatus() {
            String id = PreferenceUtil.getString(this, Const.PREF_KEY_ID);
            return SignInUtil.hasSignedIn(id);
        }

        private void goProfile() {
        boolean signedIn = checkSignInStatus();
        if (signedIn) {
            GoUtil.startActivity(this, ProfileActivity.class);
            return;
        }
        GoUtil.startActivityForResult(MainActivity.this, SignInActivity.class, Const.REQ_CODE_LOGIN);
        }
        ```

        ```java
        private boolean checkHasId() {
            String id = PreferenceUtil.getString(this, Const.PREF_KEY_ID);
            String tempId = PreferenceUtil.getString(this, Const.PREF_KEY_TEMP_ID);
            return SignInUtil.hasId(id, tempId);
        }

        private void goMyDday() {
        boolean hasId = checkHasId();
        if (!hasId) {
            showSignInDialog();
            return;
        }
        GoUtil.startActivity(this, MyDDayActivity.class);
        }
        ```

    - 로그아웃 : 소셜로그인은 로그아웃으로 세션 제거, 콜백 연결 해지 해주고 저장된 개인정보, 토큰을 모두 삭제해준다.

        ```java
        private void signInAndOut() {
            boolean signedIn = checkSignInStatus();
            if (signedIn) {
                showSignOutDialog();
                return;
            }
            GoUtil.startActivityForResult(MainActivity.this, SignInActivity.class, Const.REQ_CODE_LOGIN);
        }

        @Override
        public void onSimplePositiveButton(int id) {
            String memberId = PreferenceUtil.getString(this, Const.PREF_KEY_ID);
            if (memberId.startsWith(Const.PREFIX_KAKAO)) {
                SignInUtil.signOutKakao(this, signOutHandler);
            } else if (memberId.startsWith(Const.PREFIX_GOOGLE)) {
                SignInUtil.signOutGoogle(mGoogleApiClient, this, signOutHandler);
            } else if (memberId.startsWith(Const.PREFIX_FACEBOOK)) {
                SignInUtil.signOutFacebook(this, signOutHandler);
            } else {
                SignInUtil.deleteLocalInfo(this);
                setSignOutProfile();
            }
        }

        Handler signOutHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                notifyFavoriteSetChanged();
                setCurrentData(currentPosition);
                setSignOutProfile();
            }
        };
    ```

- __즐겨찾기 추가, 삭제__

    - 회원에 따른 사용법
        - 기존 서비스 : 기존 서비스에서는 즐겨찾기 사용을 하려면 로그인을 하도록 한다. 추후 확장될 여지가 있으나 아직 현재 기능으로는 회원제로 운영하지 않아도 되고
        임시아이디로 즐겨찾기 데이터를 축적하는 것과 회원아이디로 즐겨찾기 데이터를 축적하는데 큰 차이가 없다. 회원으로부터 가져오는 정보도 임시아이디와 다르지 않기 떄문에
        임시아이디로 즐겨찾기를 사용할 수 있도록 했다.
        - 임시아이디 : 임시아이디를 SharedPreferences에 넣어두고 임시아이디를 생성한 적이 있으면 즐겨찾기를 사용할 수 있게 한다. 임시아이디는 인증이 필요 없기 때문에
        HTTPS로만 데이터를 주고받는다
        - 회원아이디 : 인증 받은 후 전달받은 회원 아이디와 토큰값으로 인증받은 회원이 있는지 먼저

    - Work Flow
        - 아이디 확인
            - 회원아이디 확인 : 이미 로그인이 된 경우 로컬에 로그인 이력이 남는다. 만약 이 상황에서 인터넷에 연결되지 않았다면 그대로 서비스를 이용할 수 있도록 해줘야 한다. 다만 서버에 올라가지 않았음을 명시해주거나 테이블에 따로 저장해줘야 하고 서버에 연결됬을 때 먼저 토큰 유효성 확인하고 응답에 따라 재로그인 후 서버에 동기화하든지 바로 동기화 할 수 있다.
            - 회원아이디가 없을 경우 임시아이디 확인
            - 임시아이디도 없으면 아이디 생성 요구.
        - 로컬 데이터베이스에 반영 : 서버에 연결되든 되지 않든 일단 로컬데이터베이스는 반영이 돼야 함. 즐겨찾기의 경우 변경되는 곳이 2곳이거나 추후 3곳 이상이 될 수 있기 때문에 Observable과 같은 반응형 패턴을 적용해야 한다.
        - UI(화면)에 반영 : 로컬 데이터베이스에 반영된 사항은 바로 화면에 반영
        - __서버에 반영__ : 서버 반영에 실패할 경우 로컬 데이터베이스에 실패한 데이터 목록을 유지하거나 데이터에서 status를 관리해준다
            - 임시아이디 : 대부분 경우 임시아이디라는 것이 거의 사용되지 않고, 임시아이디 자격으로 즐겨찾기를 사용하거나 좋아요 기능을 사용하지 않는다. 임시아이디를
            사용할 경우에도 토큰을 받아와야 하며 요청할 때 토큰을 같이 요청해준다.
            - 회원아이디 : 인증과 함꼐 받아온 토큰, 회원을 구분할 수 있는 키(아이디), 데이터를 넘겨준다


    - 즐겨찾기인지 확인

        ```java
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
        ```

    - 즐겨찾기 추가
        - checkHasId : 임시아이디, 회원아이디 중 하나라도 생성이 됬는지 확인
        - checkCurrentId : 아이디가 있다면 임시아이디인지 회원아이디인지 분기
        - createFavorite : Favorite 객체 생성
        - createFavoriteBySignInId : 회원아이디로 추가
            - createLocalFavorite : 로컬 데이터베이스에 반영
            - updateLocalFavorite(coupon.no) : 로컬 DB Coupon 업데이트
            - deleteTempFavorite : 만약 서버가 끊긴 상태에서 삭제를 했을 경우 TempFavorite 에는 서버에 가서 삭제될 데이터가 남아있다
                그 상황에서 만약 다시 그 데이터를 추가하고 서버에 연결이 된다면 서버에 먼저 추가가 될 것이고
                그 후 Favorite 페이지에 들어갈 때 이미 추가된 데이터가 다시 삭제될 것이다. 따라서 서버가 끊긴 상태에서
                다시 추가된 데이터는 추후 삭제하러 갈 목록에서 삭제해준다.
            - setFavoriteChecked : UI(화면)에 반영
            - mainService.createFavorite(favorite) : 서버에 POST
        - createFavoriteByTempId : 임시아이디로 추가
            - createLocalFavorite : 로컬 데이터베이스에 반영
            - updateLocalFavorite(coupon.no) : 로컬 DB Coupon 업데이트
            - deleteTempFavorite : 만약 서버가 끊긴 상태에서 삭제를 했을 경우 TempFavorite 에는 서버에 가서 삭제될 데이터가 남아있다
                그 상황에서 만약 다시 그 데이터를 추가하고 서버에 연결이 된다면 서버에 먼저 추가가 될 것이고
                그 후 Favorite 페이지에 들어갈 때 이미 추가된 데이터가 다시 삭제될 것이다. 따라서 서버가 끊긴 상태에서
                다시 추가된 데이터는 추후 삭제하러 갈 목록에서 삭제해준다.
            - setFavoriteChecked : UI(화면)에 반영
            - mainService.createFavorite(favorite) : 서버에 POST
            - checkTempIdOnServer : 임시아이디가 서버에 등록되어 있는지 확인한다. 다만 임시아이디를 회원으로 취급해야 하는지는 확실하지 않군.

        ```java
        private void createFavorite(int recyclerPosition) {
            Coupon coupon = couponList.get(recyclerPosition - 1);
            // 추가할 즐겨찾기 정보
            Favorite favorite = new Favorite();
            favorite.parentNo = coupon.no;
            favorite.onServer = 0;
            checkHasId(recyclerPosition, favorite);
        }

        private void checkHasId(int recyclerPosition, Favorite favorite) {
            String signInId = PreferenceUtil.getString(context, Const.PREF_KEY_ID);
            String tempId = PreferenceUtil.getString(context, Const.PREF_KEY_TEMP_ID);
            if (SignInUtil.hasId(signInId, tempId)) {
                checkCurrentId(signInId, tempId, recyclerPosition, favorite);
                return;
            }
            showSignInDialog();
        }

        private void checkCurrentId(String signInId, String tempId, int recyclerPosition, Favorite favorite) {
            if (SignInUtil.hasSignedIn(signInId)) {
                createFavoriteBySignInId(signInId, recyclerPosition, favorite);
                return;
            }
            createFavoriteByTempId(tempId, recyclerPosition, favorite);
            return;
        }
        ```

        ```java
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
        ```
        ```java
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
        ```

    - 즐겨찾기 추가 반영 : 어댑터에서 추가하기 때문에 메인이 반영해 줘야 함

        ```java
        private void setFavoriteUnChecked(Coupon coupon, int recyclerPosition) {
            coupon.isFavorite = false;
            couponList.set(recyclerPosition - 1, coupon);
            iCouponAdapter.notifyMainFavoriteChanged(coupon.mainCategory);
            notifyItemChanged(recyclerPosition);
        }
        ```

### (5) Detail

- __DetailService__

- __지도__

    - WorkaroundMapView : 맵뷰, 맵프래그먼트를 스크롤류 안에 사용할 때 맵뷰를 스크롤 해서 지도를 움직이고 싶으면 터치이벤트를 부모가 아니라 맵뷰가 가져갈 수 있도록
    WorkaroundMapView를 커스터마이징한다

        - Wrapper : 맵뷰 위에 덮어준다

            ```java
            public class TouchableWrapper extends FrameLayout {

                @Override
                public boolean onInterceptTouchEvent(MotionEvent ev) {
                    return super.onInterceptTouchEvent(ev);
                }

                @Override
                public boolean onTouchEvent(MotionEvent event) {
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
                    return super.dispatchTouchEvent(event);
                }
            }
            ```

        - WorkaroundMapView(extends MapView)

            ```java
            private OnTouchListener mListener;

            public void setListener(OnTouchListener mListener) {
                this.mListener = mListener;
            }

            public interface OnTouchListener {
                void onTouch();
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

            ```

        - setListener

            ```java
            WorkaroundMapView.OnTouchListener touchListener = new WorkaroundMapView.OnTouchListener() {
                @Override
                public void onTouch() {
                    detailScrollView.requestDisallowInterceptTouchEvent(true);
                }
            };
            ```

    - Map

        ```java
        detailMapview.onCreate(savedInstanceState);
        detailMapview.getMapAsync(this);
        ```
        ```java
        @Override
        public void onResume() {
            super.onResume();
            mapView.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
            mapView.onPause();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
        }
        ```

    - MapListener

        ```java
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setOnMapClickListener(this);
        googleMap.setMyLocationEnabled(true);
        ```

    - Marker

        ```java
        private void placeMarker(LatLng latLng) {
            this.latLng = latLng;
            MarkerOptions options = new MarkerOptions();
            options.position(latLng);
            googleMap.clear();
            googleMap.addMarker(options);
        }
        ```

    - moveCamera

        ```java
        private void animateCamera(LatLng latLng, int zoom) {
            CameraPosition cp = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(zoom)
                    .tilt(10)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
        }

        private void moveCamera(LatLng latLng, int zoom) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
        ```

    - GeoAddress : LatLng에서 주소값 찾기

        ```java
        public static String getGeoAddress(Context context, LatLng latLng) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addressList = null;
            try {
                addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            } catch (IOException e) {
                Crashlytics.logException(e);
            }
            Address address = addressList.get(0);
            String addressStr = address.getAddressLine(0);
            return addressStr;
        }
        ```

    - GooglePlace : 이름, 주소로 위치 찾기 API

        ```java
        private void goPlaceAutoComplete() {
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            }
            ```
        }
        ```
        ```java
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                place = PlaceAutocomplete.getPlace(this, data);
                setAddress();
                animateCamera(place.getLatLng(), 17);
                placeMarker(place.getLatLng());
            }
        }
        ```

    - UiSettings

        ```java
        UiSettings settings = googleMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
        settings.setZoomControlsEnabled(true);
        settings.setRotateGesturesEnabled(false);
        settings.setMapToolbarEnabled(true);
        ```

### (6) Favorite

- __FavoriteService__

- __즐겨찾기 삭제__

    - 로컬 DB 반영
        - Coupon 데이터 업데이트(체크 여부)
        - Favorite 데이터 삭제
    - UI 반영 : 현재 화면, 메인 화면
    - 서버 반영

        ```java
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
        ```

- __leftOver 처리__

    - 로컬과 서버의 연동중에서 복잡한 부분 중 하나가 로컬의 변경 데이터를 서버에 동기화 시키는 일이다

    - checkUnPostedLeftOvers : 서버에 반영되지 않은 로컬 데이터베이스 변경사항 중 추가된 즐겨찾기 있는지 확인

        ```java
        public void checkUnPostedLeftOvers() {
            if (!NetworkUtil.isConnectedSimple(this)) return;
            List<Favorite> leftOvers = FavoriteDao.getInstance(this).readByValue(Const.COLUMN_ON_SERVER, 0);
            if (leftOvers.size() == 0) {
                LogUtil.e(TAG, "POST 할 leftOver 없음");
                checkUnDeleteLeftOvers();
                return;
            }
            favoriteService.loadLeftOvers(leftOvers);
        }
        ```

    - checkUnDeleteLeftOvers : 서버에 반영되지 않은 로컬 데이터베이스 변경사항 중 삭제된 즐겨찾기 있는지 확인

        ```java
        public void checkUnDeleteLeftOvers() {
            List<TempFavorite> tempFavorite = TempFavoriteDao.getInstance(this).readAll();
            if (tempFavorite.size() == 0) {
                LogUtil.e(TAG, "DELETE 할 leftOver 없음");
                return;
            }
            favoriteService.deleteLeftOver(tempFavorite);
        }
        ```


- __즐겨찾기 변경사항 반영__

        ```java
        @Override
        public void setHasChange(boolean hasChange) {
            getTempIntent().putExtra(Const.GOUTIL_EXTRA_1, hasChange);
            setResult(RESULT_OK, intent);
        }
        ```
        ```java
        @Override
        public void addChangedCategory(String mainCategory) {
            getCategory().add(mainCategory);
            getTempIntent().putExtra(Const.GOUTIL_EXTRA_2, hashsetToArray());
        }
        ````


### (7) 회원가입

    일반 회원가입의 경우 서비스에서 이용할 만큼의 회원정보를 입력받는다. 그 중 본인인증이 꼭 필요한 경우는 firebase에서 제공하는 인증 서비스를 이용한다. 인증이 모두 됬으면 정보를 HTTPS로 전달해서 서버에 저장하고 토큰과 저장할 정보들을 같이 보내준다. 나머지는 다르지 않음.

### (8) 쿠폰요청

- __RequestService__

- __Image & Logo File__ : 서버로 이미지 보내기

     - 이미지 파일 생성하기 : Glide에 Bitmap으로 이미지를 받아오고 SimpleTarget<Bitmap>를 통해 로딩된 시점에 동기로 이미지 파일 생성 가능

        ```java
        public static File getFilesDir(Context context) {
            String state = Environment.getExternalStorageState();
            File file = null;
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                file = context.getExternalFilesDir(null);
            } else {
                file = context.getFilesDir();
            }
            return file;
        }

        public static File getPNGFile(Context context, String name) {
            return new File(FileUtil.getFilesDir(context), name + ".png");
        }
        ```

    - 이미지 불러오기

        ```java
        private void pickFromAlbum(int reqCode){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, reqCode);
        }
        ```
        ```java
        private void attachLogoFile(){
            if (RequestUtil.isEmpty(getLogoFileName())) {
                NoticeUtil.makeToast(this, "로고파일명을 입력해주세요");
                return;
            }
            getLogoFile();
            pickFromAlbum(Const.REQ_CODE_PICK_LOGO_FROM_ALBUM);
        }

        private void attachImageFile(){
            if (StringUtil.isEmpty(getLogoFileName())) {
                NoticeUtil.makeToast(this, "로고파일명을 입력해주세요");
                return;
            }
            getImageFile();
            pickFromAlbum(Const.REQ_CODE_PICK_IMAGE_FROM_ALBUM);
        }
        ```

        ```java
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == Const.REQ_CODE_PICK_LOGO_FROM_ALBUM && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Glide.with(this).load(uri).asBitmap().into(logoTarget);
            } else if (requestCode == Const.REQ_CODE_PICK_IMAGE_FROM_ALBUM && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                imageAdapter.addData(uri);
                Glide.with(this).load(uri).asBitmap().into(imageTarget);
            }
        }
        ```

        ```java
        SimpleTarget<Bitmap> logoTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                BitmapUtil.saveBitmapToFilePNGThread(logoBitmapHandler, logoFile, bitmap);
            }
        };

        SimpleTarget<Bitmap> imageTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                isSaving = true;
                BitmapUtil.saveBitmapToFileJPEGThread(imageBitmapHandler, imageFile, bitmap);
            }
        };
        ```

    - 파일에 Bitmap 저장 : BitmapUtil

        - 이미지 형식 : JPEG, PNG
        - 해상도 : 0-100
        - 파일 & 이름


        ```java
        public static void saveBitmapToFilePNGThread(final Handler handler, final File file, final Bitmap bitmap) {
            new Thread() {
                @Override
                public void run() {
                    saveBitmapToFilePNG(file, bitmap);
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
        ```

        ```java
        private static void saveBitmapToFilePNG(File file, Bitmap bitmap) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 100, true);
                scaledBitmap.compress(Bitmap.CompressFormat.PNG, 70, fos);
                fos.close();
            } catch (Exception e) {
                Crashlytics.logException(e);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        Crashlytics.logException(e);
                    }
                }
            }
        }
        ```

    - 저장 완료된 후

        ```java
        private Handler logoBitmapHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                isSaving = false;
                mCoupon.logoFilename = logoFileName;
                Glide.with(RequestCouponActivity.this)
                    .load(logoFile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(requestCouponLogo);
            }
        };
        ```

        ```java
        private Handler imageBitmapHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                imageFiles.add(imageFile);
                isSaving = false;
            }
        };
        ```

    - 서버 전송 : Multipart
        - 쿠폰 : body로 바로 보낼 수 없기 때문에 map에 키 값을 담아서 보내준다.
        - 로고파일 : Multipart에 담아서 보내준다
        - 이미지파일 : 파일이 여러개이기 때문에 Multipart를 list에 담아서 보내준다

        ```java
        public boolean prepare(Coupon mCoupon, File logoFile, List<File> imageFiles){
            // 쿠폰
            couponMap = new HashMap<>();
            couponMap.put("coupon", mCoupon);
            // 로고파일
            partLogoFile = RemoteUtil.prepareFilePart(logoFile);
            // 이미지파일
            partImageFiles = new ArrayList<>();
            for (File imageFile : imageFiles) {
                partImageFiles.add(RemoteUtil.prepareFilePart(imageFile));
            }
            return true;
        }
        ```

        ```java
        public static MultipartBody.Part prepareFilePart(File file) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            return part;
        }
        ```

        ```java
        @Multipart
        @POST("/request/info")
        Call<CouponInfo> insertRequestInfo(@Part MultipartBody.Part logoFile,
                                        @Part List<MultipartBody.Part> imageFiles,
                                        @PartMap Map<String, Coupon> requestInfo);
        ```

- __More__

    - 입력 가능한 칸을 유동적으로 늘리는 경우

        ```java
        private void addCouponInfo(){
            if (isFirstInfo) {
                innerLayoutCouponInfo.setVisibility(View.GONE);
                requestInfoDetail.setText("");
            } else {
                requestInfoAdd.setImageResource(remove);
                addMoreInfoView(layoutCouponInfo, infoViews);
                isFirstInfo = true;
            }
        }
        ```

        ```java
        private void addMoreInfoView(final ViewGroup parent, final List<View> viewRepo) {
            final View view = getLayoutInflater().inflate(R.layout.item_add, parent, false);
            final ImageView addButton = view.findViewById(R.id.itemAddViewImg);
            addButton.setTag(false);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((boolean) v.getTag() == true) {
                        parent.removeView(view);
                        viewRepo.remove(view);
                    } else {
                        addButton.setImageResource(remove);
                        addMoreInfoView(parent, viewRepo);
                        v.setTag(true);
                    }
                }
            });
            viewRepo.add(view);
            parent.addView(view);
        }
        ```

        ```java
        private String appendInfo(){
            String info = "";
            for (View view : infoViews) {
                info += ((EditText) ((ViewGroup) view).getChildAt(0)).getText().toString();
            }
            return info;
        }
        ```

- __BusinessHour__

    - CustomDialog : 커스텀 뷰를 불러올 때마다 inflate 하지 않도록 미리 addView 할 customView를 전역으로 inflate 해 놓는다. 다만 여러 다이얼로그 중 커스텀뷰를 사용하는 다이얼로그는 한 번 addView하면 자식이 부모에 들어가므로 재사용하려면 removeView를 해줘야 한다. removeView 해주지 않으면 자식이 부모를 2개 가질 수 없기 때문에 오류가 발생한다.

        ```java
        timePickerView = getLayoutInflater().inflate(R.layout.dialog_timepicker, null);
        timePicker = timePickerView.findViewById(R.id.timePicker);
        requestBusinessHourWeekadayDetail = timePickerView.findViewById(R.id.requestBusinessHourWeekadayDetail);
        requestBusinessHourSatDetail = timePickerView.findViewById(R.id.requestBusinessHourSatDetail);
            ```
        ```

    - showDialog : DialogUtil을 통해 전체 Dialog를 한곳에서 처리해준다

        ```java
        private void showTimeDialog(int id){
            DialogUtil.showDialog(
                    timePickerView,
                    timePickerBuilder,
                    getResources().getString(R.string.dialog_confirm),
                    getResources().getString(R.string.dialog_cancel),
                    this, id);
        }
        ```

    - DialogUtil

        ```java
        public static void showDialog(final View customView, AlertDialog.Builder builder, String positiveStr, String negativeStr, final ISimpleDialog iSimpleDialog, final int id) {

            builder.setView(customView)
                    .setPositiveButton(positiveStr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            iSimpleDialog.onSimplePositiveButton(id);
                            // 하나의 뷰를 재활용 할 때 두 개의 부모를 가질 수 없기 때문에 부모뷰를 떼어내준다
                            ((ViewGroup) customView.getParent()).removeView(customView);
                        }
                    })
                    .setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            iSimpleDialog.onSimpleNegativeButton(id);
                            ((ViewGroup) customView.getParent()).removeView(customView);
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            iSimpleDialog.onSimpleCanceled(id);
                        }
                    })
                    .show();
        }
        ```

    - 응답 : 다이얼로그 내부에 들어갈 코드를 인터페이스트로 전달해준다. 이렇게 함으로써 각 다이얼로그를 구분하는 값만 넣어주면 한곳에서 모든 처리를 해 줄 수 있다.

        ```java
        @Override
        public void onSimplePositiveButton(int id) {
            switch (id){
                case R.id.requestBack:
                    break;

                    ```
            }
        }

        @Override
        public void onSimpleNegativeButton(int id) {
            switch (id){
                case R.id.requestBack: finish(); break;

                    ```
            }
        }

        @Override
        public void onSimpleCanceled(int id) {
            switch (id){
                case R.id.requestBack: finish(); break;

                    ```
            }
        }
        ```

### (9) Remote

- __IRemoteService__

    ```java
        @GET("")
        @GET("/coupon/list")
        Call<CouponInfo> selectCouponInfoByLocation(@Query("latitude") double latitude,
                                                    @Query("longitude") double longitude);
    ```
    ```java
        @POST("")
         @POST("/member/info")
        Call<MemberInfo> insertMemberInfo(@Body Member member, @Query("temp_id") String tempId);

        @POST("/favorite/leftOvers/delete")
        Call<FavoriteInfo> deleteFavoriteLeftOvers(@Body List<TempFavorite> tempFavorites);

        @Multipart
        @POST("/request/info")
        Call<CouponInfo> insertRequestInfo(@Part MultipartBody.Part logoFile,
                                            @Part List<MultipartBody.Part> imageFiles,
                                            @PartMap Map<String, Coupon> requestInfo);
    ```
    ```java
        @PUT("favorite/temp")
        Call<FavoriteInfo> updateTempFavorite(@Query("signup_id") String signupId, @Query("temp_id") String tempId);

        @DELETE("favorite/temp")
        Call<FavoriteInfo> deleteTempFavorite(@Query("temp_id") String tempId);
    ```

- __ServiceGenerator__

    - Retrofit

        ```java
        private static Retrofit buildRetrofit(){
            retrofit = new Retrofit.Builder()
                    .baseUrl(IRemoteService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(buildOkhttp())
                    .build();
            return retrofit;
        }
        ```

    - OkHttp

        ```java
        private static OkHttpClient buildOkhttp(){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            if(BuildConfig.DEBUG){
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();
            return okHttpClient;
        }
        ```


### (10) Util

- __DeviceUtil__
    - 전화번호
    - 디바이스번호
        - 디바이스 아이디
        - 안드로이드 아이디
        - 시리얼 번호
    - 키해시(keyHash)

- __GeoUtil__
    - GPS 작동 확인
        - GPS
        - 인터넷 GPS 확인
    - 현재위치 확인
    - 주소 확인(GeoAddress)

- __LogUtil__
    - d
    - e
    - i
    - w
    - v

- __NetworkUtil__
    - 인터넷 연결 확인
    - 인터넷 연결 타입 확인

- __SignInUtil__
    - 로그인 검사
    - Kakao Login
    - Facebook Login
    - Google Login

- __StringUtil__
    - 폰트
    - sdf
    - 유효성
        - null
        - 이메일
        - 이름
        - 비밀번호
        - 2차 비밀번호


### (9) Resources

- __mipmap__ : 앱 아이콘

- __drawable__ : xxhdpi로 설정하면 알아서 나머지 해상도로 맞춰줌

- __anim__

    - 둥둥 떠다니는 애니메이션

        ```html
        <set xmlns:android="http://schemas.android.com/apk/res/android"
        android:fillEnabled="true"
        android:fillAfter="true" >
            <translate
                android:interpolator="@android:anim/linear_interpolator"
                android:fromYDelta="0%"
                android:toYDelta="20%"
                android:duration="800"
                android:startOffset="0"
                android:repeatCount="infinite"
                android:repeatMode="reverse" />
        </set>
        ```

- __menu__

- __values__

    - attr : 커스텀뷰 속성

        ```html
        <resources>
            <declare-styleable name="StyledTextView">
                <attr name="typeface" format="string"/>
            </declare-styleable>
        </resources>
        ```

    - colors
        - 페이지마다 공통되는 부분은 다 따로 설정하지 않고 설계시 하나로 정해야 한다. 위젯도 같고 색상도 같은데 다른 이름을 쓸 필요 없다.
        - 스타일, 배경, 아이콘, 글자와 같이 영역을 나눈다.

        ```html
        <!-- styles -->
        <color name="colorPrimary">#3F51B5</color>
        <color name="colorPrimaryDark">#303F9F</color>
        <color name="colorAccent">#FF4081</color>

        <!-- 배경 -->
        <color name="color_back_main_toolbar">#FFFFFF</color>
        <color name="color_back_top_tab_bar">#FF3E44</color>
        <color name="color_back_main_background">#eeeeee</color>

                    ```

        <!-- 아이콘 -->
        <color name="color_icon_navigation_">#909090</color>

        <!-- 글자 -->
        <color name="color_text_main_tab_bar">#FFFFFF</color>
        <color name="color_text_coupon">#000000</color>
        <color name="color_text_bottom_tab_selected">#000000</color>

                    ```
        ```

    - dimens : 다음 프로젝트에서는 dimens도 미리 설계한다. 역시 공통되는 부분은 반복하지 않고 하나로 통일하도록 한다

    - strings

    - secrets : API 키 값들을 모아놓음


## __3. 배포__ :open_file_folder:

### (1) 어플리케이션 체크사항

- [앱 설정 확인](https://developer.android.com/studio/publish/versioning.html?hl=ko)
    - applicationId : 패키지명
    - versionName : 사용자에게 보여줄 버전
    - versionCode : 개발자 관리 버전
    - minSdkVersion : 이 버전 아래로는 앱을 설치할 수 없음
    - targetSdkVersion : 현재 앱의 개발 SDK 버전
    - 타겟 SDK 버전

- 로그 정리
    - [참고](http://gun0912.tistory.com/12)
- 불필요한 파일 정리
    - jni/ : NDK관련 소스 (c, cpp, h, mk 파일등)
    - lib/ : 라이브러리 파일 (so, jar 파일등). 테스트 용도로 쓰고 릴리즈에는 필요없는 라이브러리 파일도 삭제
    - src/ : 소스코드 파일 (.java, .aidl 등). 어떤 jar 파일도 있으면 안됨
    - res/, asset/s, res/raw/ : 불필요한 파일은 삭제 
    - [참고](http://blog.daum.net/andro_java/665)
- 난독화 & 최적화
    - 적용
        - minifyEnabled true
        - proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    - 규칙
        - dontoptimize # 최적화 하지 않기
        - dontshrink # 사용하지 않는 메소드 유지
        - keep class com.example.classname # ClassNotFoundException에러나 난독화를 진행하지 않고 유지하는 옵션
        - keepclassmembers class com.example.classname { 접근제어자 *; } # 특정 클래스의 맴버 원상태 유지
        - keepattributes InnerClasses # 내부클래스 원상태 유지 적용
    - [참고](https://www.guardsquare.com/en/proguard/manual/usage)


출처: http://dwfox.tistory.com/18 [DWFOX]
- 써드파티 라이브러리 및 서비스들의 아이디와 설정 확인 : 개발할 때는 디버그 인증, 키를 사용하기 때문에 릴리즈 할 때 릴리즈용으로 바꿔줘야 함
- 암호화 키(appkey.jks) 관리
- 테스팅
    - 에뮬레이터
    - 베타테스터

### (2) 정책 체크사항

- [구글 플레이 스토어 정책 확인](https://support.google.com/googleplay/android-developer/answer/4430948?hl=ko) : 안드로이드의 경우 사전 검수가 없기 때문에 미리 체크해야 한다
- [체크리스트](https://developer.android.com/distribute/best-practices/launch/launch-checklist.html?hl=ko)
- [현지화](https://developer.android.com/distribute/best-practices/launch/localization-checklist.html?hl=ko) 
- [개인정보처리방침](https://support.google.com/googleplay/android-developer/answer/113469?hl=ko#privacy)
- EULA(최종 사용자 계약서)
    - [참고](https://home.mcafee.com/downloads/freescan.aspx?affid=105&culture=ko-kr&ctst=1)


### (3) 스토어 배포 준비사항
 
- 앱 상세
    - 제목 : 제목도 가이드라인 따라야 함.
    - 설명 : 설계 단계에서 한 번, 배포에서 한 번 
- [앱 이미지 준비](https://support.google.com/googleplay/android-developer/answer/1078870)
    - 앱 아이콘 : 512x512px 아이콘으로 MDPI에서 48x48px, HDPI에서 72x72px, XHDPI에서 96x96px
    - 스크린샷 : 사용자들은 설명을 거의 읽지 않음
    - 그래픽 이미지 : 무조건 1024*500 이어야함
- [APK 사이즈 확인](https://developer.android.com/google/play/expansion-files.html?hl=ko)
- 베타테스트

#### 참고
    - http://www.androidside.com/bbs/board.php?bo_table=B56&wr_id=32478
    - http://wingsnote.com/84

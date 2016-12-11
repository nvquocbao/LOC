package io.hackathon.santaclaus.util;

/**
 * Created by trinhnt on 2016/12/10.
 */

public class Constants {

    public final static int INSERT_RESULT_CODE_SUCCESS = 0;

    public final static int USER_TYPE_CHILD = 0;

    public final static int USER_TYPE_PARENT = 1;

    public final static int GROWN_UP_AGE = 20;

    public static final String UPLOAD_URL = "http://47.91.16.210/upload.php";

    public static final String CREATE_USER_URL = "http://47.91.16.210:8080/LOCWS/rest/user/create";

    public static final String CHECK_LOGIN_URL = "http://47.91.16.210:8080/LOCWS/rest/user/login";

    public static final String GET_CHILD_LIST_URL = "http://47.91.16.210:8080/LOCWS/rest/user/getAllChild/";

    public static final String CREATE_MESSAGE_URL = "http://47.91.16.210:8080/LOCWS/rest/message/create";
}

package android.sales.rajesh.com.sales.Utils;

/**
 * Created by Karthik on 1/31/17.
 */

public class Constants {
    public static final int SPLASH_DISPLAY_LENGTH = 2000;

    public static String SERVER_URL = null;


    public static String NO_ERROR = "No error";
    public static final String CONNECTION_TIMED_OUT_ERROR = "Connection timed out";

    public static final int ALERT_WITH_OK_BUTTON 				= 0;
    public static final int ALERT_WITH_CLOSE_BUTTON 			= 1;
    public static final int SHOW_TOAST 							= 2;
    public static final int DISMISS_DIALOG 						= 3;
    public static final int SHOW_CONNECTIVITY_TOAST 			= 4;
    public static final int SHOW_NO_CONNECTIVITY_WITH_EXIT 		= 5;

    public static final float UPDATE_TIME_VARIATION_FACTOR 		= 0.19000f;



    public static String USER_NAME = "UserName";

    public static String XML_CONTENT_TYPE = "application/xml; charset=utf-8";
    public static String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded; charset=utf-8";
    public static String ACCEPT_ENCODING = "gzip";

    public static final String GETTING_LOGIN_REQUEST = "Sign In";

    public static final String GETTING_MERCHANT_REQUEST = "Getting Customers";

    public static final String OFFLINE_MODE = "offline mode";



    public static final String REGISTERED = "REGISTERED";


    public static final String SALES_SERVER_URL = "http://urinfotech.com/testapp/webservice";

    public static final String SALES_LOGIN_URL = "/ValidateEmployee?PassCode=";

    public static final String SALES_MERCHANTS_URL = "/GetDetails?EmpId=";

    public static final String SUBMIT_BILLS_URL = "/GetDetails?EmpId=";

    public static final String SUBMIT_LOCATION_URL = "/GetDetails?EmpId=";

    public static final String DEFAULT_LOGIN_ERROR = "Login failed";

    public static final String CHOOSE_CITY_HEADER = "Choose City";
    public static final String CHOOSE_DISTRICT_HEADER = "Choose District";





    public static final int SHOW_SPLASH 		= 	-2;




    public static final boolean DEBUG = true;

    public static String getLoginUrl(String passcode) {

        String url = SALES_SERVER_URL+SALES_LOGIN_URL+passcode;

        return url;

    }


    public static String getMerchantsUrl(String employeeId) {

        String url = SALES_SERVER_URL+SALES_MERCHANTS_URL+employeeId;

        return url;

    }
}

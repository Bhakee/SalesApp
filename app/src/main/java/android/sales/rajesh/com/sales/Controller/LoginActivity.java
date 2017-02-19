package android.sales.rajesh.com.sales.Controller;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.sales.rajesh.com.sales.Core.WebCallableCoreActivity;
import android.sales.rajesh.com.sales.Model.UserDetail;
import android.sales.rajesh.com.sales.Parser.LoginParser;
import android.sales.rajesh.com.sales.Result.LoginParseResult;
import android.sales.rajesh.com.sales.Result.ParseResult;
import android.sales.rajesh.com.sales.Utils.Constants;
import android.sales.rajesh.com.salesapp.R;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends WebCallableCoreActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    protected static final int LOAD_MERCHANTSLIST_ACTIVITY = 1;

    private Button submitBtn;

    private TextView passcodeHintTV;
    private EditText passwordET;

    private String msgFromServer;

    private String passCode;

    UserDetail userDetail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);


        TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        View homeBt = (View) toolbar.findViewById(R.id.toolbar_home_BT);
        View rightBt = (View) toolbar.findViewById(R.id.toolbar_right_action_Bt);

        rightBt.setVisibility(View.GONE);
        homeBt.setVisibility(View.GONE);


        titleView.setText(R.string.app_name);

        submitBtn = (Button) findViewById(R.id.login_submit_Btn);
        passcodeHintTV = (TextView) findViewById(R.id.login_username_ET);
        passwordET = (EditText) findViewById(R.id.login_password_ET);

//        passwordET.setText("saasf");

//        loginValidationMessage();

        submitBtn.setOnClickListener(this);

    }




    private void loginValidationMessage(){

        String passcodeStr = passwordET.getText().toString();

        this.passCode = passcodeStr;

        if(passcodeStr != null && passcodeStr.length() > 0){
            makeWebRequest(this, Constants.getLoginUrl(passcodeStr),
                    new LoginParser(this), Constants.GETTING_LOGIN_REQUEST,
                    false, "", false, false);
        }else{
            showAlertMessage("SalesApp","Please enter your Passcode", Constants.ALERT_WITH_OK_BUTTON);
        }

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_MERCHANTSLIST_ACTIVITY:
                   // finish();
                    Intent gotoMerchantActivity = new Intent(LoginActivity.this,
                            MerchantsListActivity.class);
                    gotoMerchantActivity.putExtra("user",userDetail);

                    startActivity(gotoMerchantActivity);
                    break;
            }

        }
    };

    //Loads home activity
    public void loadNext(int code) {
        switch (code) {
            case LOAD_MERCHANTSLIST_ACTIVITY:
                //finish();
                handler.sendEmptyMessage(LOAD_MERCHANTSLIST_ACTIVITY);
                break;
        }
    }


    @Override
    public void loadPrev() {
        minimizeApp();

    }


    //onClick listener for Submit button
    @Override
    public void onClick(View v) {

        if (v.getId() == submitBtn.getId()) {

            loginValidationMessage();
        }
    }

    @Override
    protected int useResponseData(ParseResult result, String identifier) {


        LoginParseResult loginResult = (LoginParseResult) result;

        this.userDetail = loginResult.getUserDetail();

        stopLoading();


        if(this.userDetail != null){

            if(this.userDetail.isAuthorized() == true) {

                this.userDetail.setPassCode(this.passCode);
                this.userDetail.persistData();
                return LOAD_MERCHANTSLIST_ACTIVITY;
            }

        }

        stopLoading();



        return -1;

    }

    @Override
    protected void handleTransportException(Exception ex) {

    }


}

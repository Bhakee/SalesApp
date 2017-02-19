package android.sales.rajesh.com.sales.Parser;

import android.sales.rajesh.com.sales.Core.CoreActivity;
import android.sales.rajesh.com.sales.Model.UserDetail;
import android.sales.rajesh.com.sales.Result.LoginParseResult;
import android.sales.rajesh.com.sales.Result.ParseResult;
import android.sales.rajesh.com.sales.Utils.Constants;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Karthik on 2/11/17.
 */

public class LoginParser extends JSONParser {

    private String TAG = "LoginParser";

    LoginParseResult result;

    public LoginParser( CoreActivity activity ) {
        this.activity = activity;
    }


    @Override
    public void doParsing(String rawResult) {

        result = new LoginParseResult();

        if (rawResult != null) {
            JSONTokener tokener = new JSONTokener(rawResult);
            result.setRawResult(rawResult);


            try{
                JSONObject jsnObj = new JSONObject(tokener);

                UserDetail userDetail = new UserDetail();

                if(jsnObj != null){


                    if(jsnObj.has("IsAuthorized")){
                        userDetail.setAuthorized(jsnObj.getBoolean("IsAuthorized"));

                    }

                    userDetail.setMsg(Constants.DEFAULT_LOGIN_ERROR);

                    if(jsnObj.has("Msg")){
                        userDetail.setMsg(jsnObj.getString("Msg"));

                    }

                    if(jsnObj.has("EmpId")){
                        userDetail.setEmpId(jsnObj.getString("EmpId"));

                    }

                    userDetail.setName("");
                }

                result.setUserDetail(userDetail);


            }catch (JSONException exception){

                Log.d(TAG,"Parsing Error ==================== "+exception.getMessage());

            }

        }

    }

    @Override
    public ParseResult getparseResult() {
        return result;
    }
}

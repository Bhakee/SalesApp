package android.sales.rajesh.com.sales.Result;

import android.sales.rajesh.com.sales.Model.Merchant;
import android.sales.rajesh.com.sales.Model.UserDetail;

import java.util.List;
import java.util.Vector;

/**
 * Created by Karthik on 2/11/17.
 */

public class LoginParseResult implements ParseResult {


    private String rawResult;

    UserDetail userDetail;

    public String getRawResult() {
        return rawResult;
    }

    public void setRawResult(String rawResult) {
        this.rawResult = rawResult;
    }




    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }


    @Override
    public void setData(List data) {

    }

    @Override
    public List getData() {
        return null;
    }
}

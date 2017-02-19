package android.sales.rajesh.com.sales.Model;

import android.sales.rajesh.com.sales.Database.DatabaseManager;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Karthik on 2/11/17.
 */

public class UserDetail implements Serializable,Comparable<UserDetail> {


    private boolean IsAuthorized;
    private String msg;
    private String empId;
    private String passCode;
    private String name;




    public boolean isAuthorized() {
        return IsAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        this.IsAuthorized = authorized;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int compareTo(UserDetail o) {
        return 0;
    }



    public static String TABLE_NAME = "USERS";
    public static final String COLUMN_USER_EMPLOYEE_ID = "employee_id";
    public static final String COLUMN_USER_EMPLOYEE_PASSCODE = "employee_passcode";
    public static final String COLUMN_USER_EMPLOYEE_NAME = "employee_name";


    private static String[] keyNames = {
            COLUMN_USER_EMPLOYEE_ID,
            COLUMN_USER_EMPLOYEE_PASSCODE,
            COLUMN_USER_EMPLOYEE_NAME
    };

    public static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_NAME
                    + " ( "
                    + COLUMN_USER_EMPLOYEE_ID + " VARCHAR, "
                    + COLUMN_USER_EMPLOYEE_PASSCODE + " VARCHAR, "
                    + COLUMN_USER_EMPLOYEE_NAME + " VARCHAR )";



    public void persistData(){

        HashMap<String, String> map = new HashMap<String, String>();

        map.put(COLUMN_USER_EMPLOYEE_ID, empId);
        map.put(COLUMN_USER_EMPLOYEE_PASSCODE, passCode);
        map.put(COLUMN_USER_EMPLOYEE_NAME, name);


        DatabaseManager.getInstance(null).open();
        DatabaseManager.getInstance(null).insert(TABLE_NAME, map);

        DatabaseManager.getInstance(null).close();


    }
}

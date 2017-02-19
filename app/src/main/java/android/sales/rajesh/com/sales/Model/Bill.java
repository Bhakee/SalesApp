package android.sales.rajesh.com.sales.Model;

import android.database.Cursor;
import android.icu.text.DateFormat;
import android.sales.rajesh.com.sales.Database.DatabaseManager;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Karthik on 2/11/17.
 */

public class Bill implements Serializable,Comparable<Bill>{

    private static final String TAG = Bill.class.getSimpleName();


    private String merchantId;
    private String id;
    private String billingNumber;
    private String date;
    private String aid;
    private String cType;
    private String billingAmount;
    private String billingBalance;

    public Bill(){

        this.merchantId = "";
        this.id = "";
        this.billingNumber = "";
        this.date = "";
        this.cType = "";
        this.billingAmount = "0";
        this.billingBalance = "0";


    }
    public Bill(String merchantId,String id,String billingNumber,String date,String aid,String cType, String billingAmount,String billingBalance){

        this.merchantId = merchantId;
        this.id = id;
        this.billingNumber = billingNumber;
        this.date = date;
        this.aid = aid;
        this.cType = cType;
        this.billingAmount = billingAmount;
        this.billingBalance = billingBalance;


    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillingNumber() {
        return billingNumber;
    }

    public void setBillingNumber(String billingNumber) {
        this.billingNumber = billingNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }
    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }


    public String getBillingBalance() {
        return billingBalance;
    }

    public void setBillingBalance(String billingBalance) {
        this.billingBalance = billingBalance;
    }

    public String getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(String billingAmount) {

        this.billingAmount = billingAmount;
    }

    @Override
    public int compareTo(Bill o) {
        return 0;
    }


    public static String TABLE_NAME = "BILLS";
    public static final String COLUMN_BILL_MERCHANT_ID = "merchant_id";
    public static final String COLUMN_BILL_ID = "_id";
    public static final String COLUMN_BILL_NUMBER = "bill_number";
    public static final String COLUMN_BILL_DATE = "date";
    public static final String COLUMN_BILL_AID = "aid";
    public static final String COLUMN_BILL_TYPE = "bill_type";
    public static final String COLUMN_BILL_AMOUNT = "bill_amount";
    public static final String COLUMN_BILL_BALANCE = "bill_balance";

    private static String[] keyNames = {
            COLUMN_BILL_MERCHANT_ID,
            COLUMN_BILL_ID,
            COLUMN_BILL_NUMBER,
            COLUMN_BILL_DATE,
            COLUMN_BILL_AID,
            COLUMN_BILL_TYPE,
            COLUMN_BILL_AMOUNT,
            COLUMN_BILL_BALANCE
    };

    public static final String CREATE_BILLS_TABLE =
            "CREATE TABLE " + TABLE_NAME
                    + " ( "
                    + COLUMN_BILL_MERCHANT_ID + " VARCHAR, "
                    + COLUMN_BILL_ID + " VARCHAR, "
                    + COLUMN_BILL_NUMBER + " VARCHAR, "
                    + COLUMN_BILL_DATE + " VARCHAR, "
                    + COLUMN_BILL_AID + " VARCHAR, "
                    + COLUMN_BILL_TYPE + " VARCHAR, "
                    + COLUMN_BILL_AMOUNT + " VARCHAR, "
                    + COLUMN_BILL_BALANCE + " VARCHAR )";


    public void persistData(){

        HashMap<String, String> map = new HashMap<String, String>();

        map.put(COLUMN_BILL_MERCHANT_ID, merchantId);
        map.put(COLUMN_BILL_ID, id);
        map.put(COLUMN_BILL_NUMBER, billingNumber);
        map.put(COLUMN_BILL_DATE, date);
        map.put(COLUMN_BILL_AID, aid);
        map.put(COLUMN_BILL_TYPE, cType);
        map.put(COLUMN_BILL_AMOUNT, billingAmount);
        map.put(COLUMN_BILL_BALANCE, billingBalance);

        DatabaseManager.getInstance(null).insert(TABLE_NAME, map);

    }

    private void deleteBills(){

    }

    public  static void deleteAllBills(){

        String deleteQuery = "delete from "+TABLE_NAME;

        DatabaseManager.getInstance(null).deleteRows(TABLE_NAME,null);

    }

    public static List<Bill> readData(String whereCondition){

        DatabaseManager.getInstance(null).open();
        Cursor cursor = DatabaseManager.getInstance(null).executeRawQuery(whereCondition);

        if(cursor == null || cursor.getCount() < 1){
            Log.i(TAG, "Cursor count is null");
            return null;
        }

        List<Bill> billList = new ArrayList<>();
        Bill bill = null;

        for (int j = 0; j < cursor.getCount(); j++) {

            cursor.moveToPosition(j);

            bill = new Bill(cursor.getString(0), cursor.getString(1),cursor.getString(2) ,cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
            billList.add(bill);
        }

        DatabaseManager.getInstance(null).close();

        return billList;
    }

}

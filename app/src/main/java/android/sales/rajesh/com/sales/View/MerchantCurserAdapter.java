package android.sales.rajesh.com.sales.View;

import android.content.Context;
import android.database.Cursor;
import android.sales.rajesh.com.sales.Model.Merchant;
import android.sales.rajesh.com.sales.Utils.Utility;
import android.sales.rajesh.com.salesapp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Karthik on 2/13/17.
 */

public class MerchantCurserAdapter extends CursorAdapter {

    Context ctx;

    private int COLUMN_MERCHANT_ID_INDEX = -1;
    private int COLUMN_MERCHANT_EMPLOYEE_ID_INDEX = -1;
    private int COLUMN_MERCHANT_NAME_INDEX = -1;
    private int COLUMN_MERCHANT_DIST_ID_INDEX = -1;
    private int COLUMN_MERCHANT_CITY_ID_INDEX = -1;
    private int COLUMN_MERCHANT_DIST_INDEX = -1;
    private int COLUMN_MERCHANT_COLLECTION_AMOUNT_INDEX = -1;
    private int COLUMN_MERCHANT_COLLECTION_AMOUNT_A_INDEX = -1;
    private int COLUMN_MERCHANT_COLLECTION_AMOUNT_E_INDEX = -1;

    private int COLUMN_MERCHANT_CITY_INDEX = -1;
    private int COLUMN_MERCHANT_COLLECTION_DATE_INDEX = -1;

//    public void changeCurser(String query){
//
//        Cursor aCurser =  Merchant.readDataCurser(query);
//
//        this.changeCursor(aCurser);
//
//    }


    public MerchantCurserAdapter(Context context, Cursor cursor) {

        super(context, cursor, 0);

        COLUMN_MERCHANT_ID_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_ID);
        COLUMN_MERCHANT_EMPLOYEE_ID_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_EMPLOYEE_ID);
        COLUMN_MERCHANT_NAME_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_NAME);
        COLUMN_MERCHANT_DIST_ID_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_DIST_ID);
        COLUMN_MERCHANT_CITY_ID_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_CITY_ID);
        COLUMN_MERCHANT_DIST_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_DIST);
        COLUMN_MERCHANT_COLLECTION_AMOUNT_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_COLLECTION_AMOUNT);
        COLUMN_MERCHANT_COLLECTION_AMOUNT_A_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_COLLECTION_AMOUNT_A);
        COLUMN_MERCHANT_COLLECTION_AMOUNT_E_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_COLLECTION_AMOUNT_E);
        COLUMN_MERCHANT_CITY_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_CITY);
        COLUMN_MERCHANT_COLLECTION_DATE_INDEX = cursor.getColumnIndexOrThrow(Merchant.COLUMN_MERCHANT_COLLECTION_DATE);

        this.ctx = context;


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.merchant_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView merchantNameTxt = (TextView) view
                .findViewById(R.id.merchantName_TV);
        TextView amountTxt = (TextView) view
                .findViewById(R.id.merchantAmount_TV);
        TextView dateTxt = (TextView) view
                .findViewById(R.id.merchants_list_item_date);

        String mName = cursor.getString(COLUMN_MERCHANT_NAME_INDEX);
        String mDistrict = cursor.getString(COLUMN_MERCHANT_DIST_INDEX);
        String mCollectionAmount = cursor.getString(COLUMN_MERCHANT_COLLECTION_AMOUNT_INDEX);
        String mCity = cursor.getString(COLUMN_MERCHANT_CITY_INDEX);
        String formatted = Utility.getFormattedCurrency(mCollectionAmount);

        merchantNameTxt.setText(mName);
        amountTxt.setText(formatted);
        dateTxt.setText(mCity+", "+mDistrict);


    }

    @Override
    public Merchant getItem(int position) {

        super.getItem(position);

        Cursor cursor = getCursor();
        cursor.moveToPosition(position);


        String mId = cursor.getString(COLUMN_MERCHANT_ID_INDEX);
        String mEmployeeId = cursor.getString(COLUMN_MERCHANT_EMPLOYEE_ID_INDEX);
        String mName = cursor.getString(COLUMN_MERCHANT_NAME_INDEX);
        String mDid = cursor.getString(COLUMN_MERCHANT_DIST_ID_INDEX);
        String mCid = cursor.getString(COLUMN_MERCHANT_CITY_ID_INDEX);
        String mDistrict = cursor.getString(COLUMN_MERCHANT_DIST_INDEX);
        String mCollectionAmount = cursor.getString(COLUMN_MERCHANT_COLLECTION_AMOUNT_INDEX);
        String mCollectionAmount_a = cursor.getString(COLUMN_MERCHANT_COLLECTION_AMOUNT_A_INDEX);
        String mCollectionAmount_e = cursor.getString(COLUMN_MERCHANT_COLLECTION_AMOUNT_E_INDEX);
        String mCity = cursor.getString(COLUMN_MERCHANT_CITY_INDEX);
        String mCollectionDate = cursor.getString(COLUMN_MERCHANT_COLLECTION_DATE_INDEX);

        Merchant selectedMErchant = new Merchant(mId,mEmployeeId,mName,mDid,mCid,mDistrict,mCollectionAmount,mCollectionAmount_a,mCollectionAmount_e,mCity,mCollectionDate);



        return selectedMErchant;
    }


}

package android.sales.rajesh.com.sales.Model;

import android.database.Cursor;
import android.sales.rajesh.com.sales.Database.DatabaseManager;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Karthik on 1/31/17.
 */

public class Merchant implements Serializable,Comparable<Merchant>{

    private static final String TAG = Merchant.class.getSimpleName();


    private String id;
    private String districtId;
    private String cityId;
    private String district;

    private String employeeId;

    private String name;
    private String collectionAmount;
    private String collectionAmount_A;
    private String collectionAmount_E;

    private String collectionDate;
    private String city;


    private List<Bill> billList;


    public Merchant(){

        this.id = "";
        this.name = "";
        this.collectionAmount = "";
        this.collectionAmount_A = "";
        this.collectionAmount_E = "";
        this.collectionDate = "";
        this.city = "";
        this.districtId = "";
        this.cityId = "";
        this.district = "";
        this.employeeId = "";



    }
    public Merchant(String id,String employeeId,String name,String districtId,String cityId,String district,String collectionAmount,String collectionAmount_a,String collectionAmount_e,String city, String collectionDate){
        this.id = id;
        this.employeeId = employeeId;
        this.name = name;
        this.collectionAmount = collectionAmount;
        this.collectionAmount_A = collectionAmount_a;
        this.collectionAmount_E = collectionAmount_e;
		this.collectionDate = collectionDate;
        this.city = city;

        this.districtId = districtId;
        this.cityId = cityId;
        this.district = district;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollectionAmount() {
        return collectionAmount;
    }

    public void setCollectionAmount(String collectionAmount) {
        this.collectionAmount = collectionAmount;
    }

    public String getCollectionAmount_A() {
        return collectionAmount_A;
    }

    public void setCollectionAmount_A(String collectionAmount_A) {
        this.collectionAmount_A = collectionAmount_A;
    }

    public String getCollectionAmount_E() {
        return collectionAmount_E;
    }

    public void setCollectionAmount_E(String collectionAmount_E) {
        this.collectionAmount_E = collectionAmount_E;
    }
    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    @Override
    public int compareTo(Merchant o) {
        return 0;
    }


    public static String TABLE_NAME = "MERCHANTS";
	public static final String COLUMN_MERCHANT_ID = "_id";
    public static final String COLUMN_MERCHANT_EMPLOYEE_ID = "employee_id";

    public static final String COLUMN_MERCHANT_NAME = "name";
    public static final String COLUMN_MERCHANT_DIST_ID = "did";
    public static final String COLUMN_MERCHANT_CITY_ID = "cid";
    public static final String COLUMN_MERCHANT_DIST = "district";
	public static final String COLUMN_MERCHANT_COLLECTION_AMOUNT = "colection_amount";
    public static final String COLUMN_MERCHANT_COLLECTION_AMOUNT_A = "colection_amount_a";
    public static final String COLUMN_MERCHANT_COLLECTION_AMOUNT_E = "colection_amount_e";
    public static final String COLUMN_MERCHANT_CITY = "city";
    public static final String COLUMN_MERCHANT_COLLECTION_DATE = "collection_date";


    private static String[] keyNames = {
            COLUMN_MERCHANT_ID,
            COLUMN_MERCHANT_EMPLOYEE_ID,
            COLUMN_MERCHANT_NAME,
            COLUMN_MERCHANT_DIST_ID,
            COLUMN_MERCHANT_CITY_ID,
            COLUMN_MERCHANT_DIST,
            COLUMN_MERCHANT_COLLECTION_AMOUNT,
            COLUMN_MERCHANT_COLLECTION_AMOUNT_A,
            COLUMN_MERCHANT_COLLECTION_AMOUNT_E,
            COLUMN_MERCHANT_CITY,
            COLUMN_MERCHANT_COLLECTION_DATE
    };

	public static final String CREATE_MERCHANT_TABLE =
	"CREATE TABLE " + TABLE_NAME
	+ " ( "
	+ COLUMN_MERCHANT_ID + " VARCHAR, "
    +COLUMN_MERCHANT_EMPLOYEE_ID + " VARCHAR, "
    + COLUMN_MERCHANT_NAME + " VARCHAR, "
    + COLUMN_MERCHANT_DIST_ID + " VARCHAR, "
    + COLUMN_MERCHANT_CITY_ID + " VARCHAR, "
    + COLUMN_MERCHANT_DIST + " VARCHAR, "
	+ COLUMN_MERCHANT_COLLECTION_AMOUNT + " VARCHAR, "
    + COLUMN_MERCHANT_COLLECTION_AMOUNT_A + " VARCHAR, "
    + COLUMN_MERCHANT_COLLECTION_AMOUNT_E + " VARCHAR, "
    +COLUMN_MERCHANT_CITY + " VARCHAR,"
	+ COLUMN_MERCHANT_COLLECTION_DATE + " VARCHAR )";


    private void persistData(){

		HashMap<String, String> map = new HashMap<String, String>();

        map.put(COLUMN_MERCHANT_ID, id);
        map.put(COLUMN_MERCHANT_EMPLOYEE_ID, employeeId);
        map.put(COLUMN_MERCHANT_NAME, name);
        map.put(COLUMN_MERCHANT_DIST_ID, districtId);
        map.put(COLUMN_MERCHANT_CITY_ID, cityId);
        map.put(COLUMN_MERCHANT_DIST, district);
        map.put(COLUMN_MERCHANT_COLLECTION_AMOUNT, collectionAmount);
        map.put(COLUMN_MERCHANT_COLLECTION_AMOUNT_A, collectionAmount_A);
        map.put(COLUMN_MERCHANT_COLLECTION_AMOUNT_E, collectionAmount_E);
        map.put(COLUMN_MERCHANT_CITY, city);
        map.put(COLUMN_MERCHANT_COLLECTION_DATE, collectionDate);

		DatabaseManager.getInstance(null).insert(TABLE_NAME, map);


        for (Bill bill:billList) {

            bill.persistData();

        }

	}


    public static void persistAllMerchants(List<Merchant> list){

        DatabaseManager.getInstance(null).open();

        DatabaseManager.getInstance(null).sqlitedb.beginTransaction();

        for (Merchant merchant:list) {
            merchant.persistData();
        }
        DatabaseManager.getInstance(null).sqlitedb.setTransactionSuccessful();

        DatabaseManager.getInstance(null).sqlitedb.endTransaction();

//        DatabaseManager.getInstance(null).close();

    }


    public  static void deleteAllMerchants(String employeeId){
        DatabaseManager.getInstance(null).open();

        DatabaseManager.getInstance(null).sqlitedb.beginTransaction();

        String whereCondn = "employee_id == '"+employeeId+"'";

        DatabaseManager.getInstance(null).deleteRows(TABLE_NAME,whereCondn);

        Bill.deleteAllBills();

        DatabaseManager.getInstance(null).sqlitedb.setTransactionSuccessful();

        DatabaseManager.getInstance(null).sqlitedb.endTransaction();

//        DatabaseManager.getInstance(null).close();


    }

    public static List readDistinctDistrict(String whereCondition) {
        List<String> districtList = new ArrayList<>();


        DatabaseManager.getInstance(null).open();
        Cursor cursor = DatabaseManager.getInstance(null).executeRawQuery(whereCondition);

        if(cursor == null || cursor.getCount() < 1){
            Log.i(TAG, "Cursor count is null");
            return null;
        }

        Vector<Merchant> merchantVector = new Vector<Merchant>();
        Merchant merchant = null;

        for (int j = 0; j < cursor.getCount(); j++) {


            cursor.moveToPosition(j);

            districtList.add(cursor.getString(0));

        }

//        DatabaseManager.getInstance(null).close();


        return districtList;

    }


    public static List readDistinctaCity(String whereCondition) {
        List<String> cityList = new ArrayList<>();


        DatabaseManager.getInstance(null).open();
        Cursor cursor = DatabaseManager.getInstance(null).executeRawQuery(whereCondition);

        if(cursor == null || cursor.getCount() < 1){
            Log.i(TAG, "Cursor count is null");
            return null;
        }

        Vector<Merchant> merchantVector = new Vector<Merchant>();
        Merchant merchant = null;

        for (int j = 0; j < cursor.getCount(); j++) {


            cursor.moveToPosition(j);

            cityList.add(cursor.getString(0));

        }

//        DatabaseManager.getInstance(null).close();


        return cityList;

    }

    public static Cursor readDataCurser(String whereCondition){

        DatabaseManager.getInstance(null).open();
        Cursor cursor = DatabaseManager.getInstance(null).executeRawQuery(whereCondition);


        return cursor;

    }


    public static Vector readData(String whereCondition){

		DatabaseManager.getInstance(null).open();
		Cursor cursor = DatabaseManager.getInstance(null).executeRawQuery(whereCondition);

		if(cursor == null || cursor.getCount() < 1){
			Log.i(TAG, "Cursor count is null");
			return null;
		}

		Vector<Merchant> merchantVector = new Vector<Merchant>();
            Merchant merchant = null;

		for (int j = 0; j < cursor.getCount(); j++) {

			cursor.moveToPosition(j);

            merchant = new Merchant(cursor.getString(0), cursor.getString(1),cursor.getString(2) ,cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10));
            merchantVector.add(merchant);
		}

		DatabaseManager.getInstance(null).close();

		return merchantVector;
	}

}

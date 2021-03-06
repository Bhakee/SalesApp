package android.sales.rajesh.com.sales.Controller;

import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.sales.rajesh.com.sales.Core.WebCallableCoreActivity;
import android.sales.rajesh.com.sales.Model.Bill;
import android.sales.rajesh.com.sales.Model.Merchant;
import android.sales.rajesh.com.sales.Result.ParseResult;
import android.sales.rajesh.com.sales.Utils.Constants;
import android.sales.rajesh.com.sales.Utils.LocationHelper;
import android.sales.rajesh.com.sales.Utils.SalesProtocol;
import android.sales.rajesh.com.sales.Utils.Utility;
import android.sales.rajesh.com.sales.View.SFTextWatcher;
import android.sales.rajesh.com.salesapp.R;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Karthik on 2/5/17.
 */

public class MerchantPaymentActivity extends WebCallableCoreActivity implements View.OnClickListener {


    private static final String TAG = MerchantPaymentActivity.class.getSimpleName();
//    private static final String currencyFormat = "Rs";

    public static final String COLLECTION_AMOUNT_EDITTEXT_TAG = "COLLECTION_AMOUNT_EDITTEXT_TAG";
    public static final String DISCOUNT_AMOUNT_EDITTEXT_TAG = "DISCOUNT_AMOUNT_EDITTEXT_TAG";
    public static final String PENDING_BILLS_TAG = "PENDING_BILLS_TAG";


    Merchant selectedMerchant;


    TextView merchantNameTV;
    TextView dateTV;

    String totalCollection;

    TextView totalAmountTV;

    TextView balanceAmountTV;


    EditText collectionAmountET;
    EditText discountAmountET;

    ScrollView paymentSV;

    Location currentLocation;

    int selectedPaymentType;

    Button homeBt, rightBt;

    List<EditText> editTextList;

    double totalEnteredPendingBillsAmount;

    private String collectionAmount = "";
    private String discountAmount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_payment_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        homeBt = (Button) toolbar.findViewById(R.id.toolbar_home_BT);
        homeBt.setOnClickListener(this);

        rightBt = (Button) toolbar.findViewById(R.id.toolbar_right_action_Bt);

        rightBt.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();

        selectedMerchant = (Merchant) getIntent().getSerializableExtra("merchant");

        selectedPaymentType = (int) getIntent().getSerializableExtra("payment_type");

        titleView.setText(R.string.payment_title);

        if (selectedPaymentType == SalesProtocol.PAYMENT_TYPE_A) {
            totalCollection = selectedMerchant.getCollectionAmount_A();
        } else {

            totalCollection = selectedMerchant.getCollectionAmount_E();

        }


        merchantNameTV = (TextView) findViewById(R.id.mp_customer_name_value_TV);
        dateTV = (TextView) findViewById(R.id.mp_date_value_TV);

        balanceAmountTV = (TextView) findViewById(R.id.mp_pending_balance_value_TV);

        collectionAmountET = (EditText) findViewById(R.id.mp_collection_amount_value_ET);

        discountAmountET = (EditText) findViewById(R.id.mp_discount_amount_value_ET);

        paymentSV = (ScrollView) findViewById(R.id.mp_activity_SV);


        LocationHelper locationHelper = new LocationHelper();

        locationHelper.getLocation(this, new LocationHelper.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                currentLocation = location;

                app.messages("Location Received Latitude: " + location.getLatitude());

            }
        });


        totalAmountTV = (TextView) findViewById(R.id.mp_total_amount_value_TV);

        collectionAmountET.setTag(COLLECTION_AMOUNT_EDITTEXT_TAG);

        collectionAmountET.addTextChangedListener(new PaymentTextWatcher(collectionAmountET));


        discountAmountET.setTag(DISCOUNT_AMOUNT_EDITTEXT_TAG);

        discountAmountET.addTextChangedListener(new PaymentTextWatcher(discountAmountET));


        merchantNameTV.setText(selectedMerchant.getName());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");

        dateTV.setText(mdformat.format(calendar.getTime()));

        int initTotalAmount = 0;

        String Totalformatted = Utility.getFormattedCurrency("" + initTotalAmount);


        totalAmountTV.setText(Totalformatted);


        String formatted = Utility.getFormattedCurrency(totalCollection);

        balanceAmountTV.setText(formatted);

        createListView();


    }


    private double getBalanceAmount() {
        String balanceAmount = totalCollection;

        Double balance = Double.parseDouble(balanceAmount);

        return balance;
    }


    private void createListView() {

        LinearLayout listLinearlayout01 = (LinearLayout) findViewById(R.id.mp_LinearLayout25);

        LinearLayout baseLinearLayoutForBills = new LinearLayout(this);
        baseLinearLayoutForBills.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams baseLinearLayoutForBillsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        baseLinearLayoutForBills.setLayoutParams(baseLinearLayoutForBillsParams);
        baseLinearLayoutForBills.setBackgroundColor(getResources().getColor(R.color.b0));


        listLinearlayout01.addView(baseLinearLayoutForBills);


        String paymentType = "" + selectedPaymentType;

        String billListquery = "select * from bills where merchant_id = '" + selectedMerchant.getId() + "' and aid = '" + paymentType + "'";


        Log.d(TAG, "billListquery : " + billListquery);

        List<Bill> billList = Bill.readData(billListquery);
        if (billList != null && billList.size() > 0) {
            editTextList = new ArrayList<>(billList.size());
        }

        for (int i = 0; i < billList.size(); i++) {


            Bill bill = billList.get(i);

            LinearLayout listLinearlayout11 = new LinearLayout(this);
            listLinearlayout11.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams11.setMargins(0, 4, 0, 4);
            listLinearlayout11.setLayoutParams(layoutParams11);

            listLinearlayout11.setBackgroundColor(getResources().getColor(R.color.c0));


            baseLinearLayoutForBills.addView(listLinearlayout11);

            LinearLayout listLinearlayout02 = new LinearLayout(this);
            listLinearlayout02.setTag("listLinearlayout" + i);
            listLinearlayout02.setOrientation(LinearLayout.HORIZONTAL);
//            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            LinearLayout.LayoutParams layoutParams02 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams02.setMargins(50, 0, 5, 0);
            listLinearlayout02.setLayoutParams(layoutParams02);
            listLinearlayout02.setPadding(0, 5, 0, 0);

            listLinearlayout11.addView(listLinearlayout02);


            TextView dateTV = new TextView(this);
            dateTV.setTag("dateTV" + i);
            LinearLayout.LayoutParams dateTVParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            dateTVParams.weight = 1;
            dateTVParams.gravity = Gravity.LEFT;
            dateTV.setLayoutParams(dateTVParams);
            dateTV.setTextColor(getResources().getColor(R.color.c2));
            dateTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            dateTV.setGravity(Gravity.CENTER_VERTICAL);
            dateTV.setTypeface(null, Typeface.NORMAL);
            dateTV.setText(bill.getDate());

            listLinearlayout02.addView(dateTV);

            TextView billTypeTV = new TextView(this);
            billTypeTV.setTag("billTypeTV" + i);
            LinearLayout.LayoutParams billTypeTVParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            billTypeTVParams.weight = 1;
            billTypeTVParams.gravity = Gravity.LEFT;
            billTypeTV.setLayoutParams(billTypeTVParams);
            billTypeTV.setTextColor(getResources().getColor(R.color.c2));
            billTypeTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            billTypeTV.setGravity(Gravity.CENTER_VERTICAL);
            billTypeTV.setTypeface(null, Typeface.NORMAL);
            billTypeTV.setText(bill.getcType());

            listLinearlayout02.addView(billTypeTV);


            LinearLayout listLinearlayout12 = new LinearLayout(this);
            listLinearlayout12.setTag("listLinearlayout12" + i);
            listLinearlayout12.setOrientation(LinearLayout.HORIZONTAL);
            int height12 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            LinearLayout.LayoutParams layoutParams12 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams12.setMargins(50, 0, 5, 0);
            listLinearlayout12.setLayoutParams(layoutParams12);

            listLinearlayout11.addView(listLinearlayout12);


            TextView totalBillBalanceTV = new TextView(this);
            totalBillBalanceTV.setTag("totalBillBalanceTV" + i);
            LinearLayout.LayoutParams totalBillBalanceTVParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            totalBillBalanceTVParams.weight = 1;
            totalBillBalanceTVParams.gravity = Gravity.LEFT;
            totalBillBalanceTV.setLayoutParams(totalBillBalanceTVParams);
            totalBillBalanceTV.setTextColor(getResources().getColor(R.color.c2));
            totalBillBalanceTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            totalBillBalanceTV.setGravity(Gravity.CENTER_VERTICAL);
            totalBillBalanceTV.setTypeface(null, Typeface.BOLD);
            totalBillBalanceTV.setText(Utility.getFormattedCurrency(bill.getBillingAmount()));

            listLinearlayout12.addView(totalBillBalanceTV);

            TextView pendingBalanceTV = new TextView(this);
            pendingBalanceTV.setTag("pendingBalanceTV" + i);
            LinearLayout.LayoutParams pendingBalanceTVParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            pendingBalanceTVParams.weight = 1;
            pendingBalanceTVParams.gravity = Gravity.LEFT;
            pendingBalanceTV.setLayoutParams(pendingBalanceTVParams);
            pendingBalanceTV.setTextColor(getResources().getColor(R.color.c16));
            pendingBalanceTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            pendingBalanceTV.setGravity(Gravity.CENTER_VERTICAL);
            pendingBalanceTV.setTypeface(null, Typeface.BOLD);
            pendingBalanceTV.setText(Utility.getFormattedCurrency(bill.getBillingBalance()));

            listLinearlayout12.addView(pendingBalanceTV);


            EditText amountInputET = new EditText(this);
            amountInputET.setTag(PENDING_BILLS_TAG + i);

            editTextList.add(amountInputET);

            amountInputET.addTextChangedListener(new PaymentTextWatcher(amountInputET));

            int amountInputETHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
            int amountInputETWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());

            LinearLayout.LayoutParams amountInputETParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    amountInputETHeight);

            amountInputETParams.setMargins(50, 0, 50, 0);
            amountInputETParams.gravity = Gravity.CENTER;

            amountInputET.setLayoutParams(amountInputETParams);

            amountInputET.setGravity(Gravity.CENTER_VERTICAL);

            amountInputET.setHint("Please enter the Amount");

            amountInputET.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

            listLinearlayout11.addView(amountInputET);


        }


    }


    public class PaymentTextWatcher implements TextWatcher {

        private EditText mEditText;

        private String lastEnteredString;


        public PaymentTextWatcher(EditText editText) {
            this.mEditText = editText;
            lastEnteredString = "";
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            Log.d(TAG, "CharSequence : s :" + s + "  start : " + start + "   before : " + before + "  count : " + count);


            //remove Extra dot(.)

            String amountString = s.toString();

            int occurences = Utility.countMatches(amountString, '.');


            if (occurences > 1) {

                this.mEditText.removeTextChangedListener(this);

                this.mEditText.setText(Utility.replaceCharecterAt(amountString, start, ' '));
                this.mEditText.setSelection(start);

                this.mEditText.addTextChangedListener(this);


                return;
            }

            String enteredAmt = amountString;

            if (enteredAmt != null && enteredAmt.equals(".")) {
                enteredAmt = "0.00";
            }

            if (this.mEditText.getTag().equals(MerchantPaymentActivity.COLLECTION_AMOUNT_EDITTEXT_TAG)) {

                this.mEditText.removeTextChangedListener(this);

                String discountEnteredAmt = discountAmountET.getText().toString();

                if (!(discountEnteredAmt != null && discountEnteredAmt.length() > 0)) {
                    discountEnteredAmt = "0.00";
                }

                if (discountEnteredAmt != null && discountEnteredAmt.equals(".")) {
                    discountEnteredAmt = "0.00";
                }


                if (!enteredAmt.equals("") && enteredAmt.length() > 0) {
                    double num = Double.parseDouble(enteredAmt);
                    double discount = Double.parseDouble(discountEnteredAmt);

                    if (before > 0) {
                        String removedStr = Utility.replaceCharecterAt(lastEnteredString, start, ' ');

                        if (Double.parseDouble(removedStr) + discount > Double.parseDouble(totalCollection)) {
                            this.mEditText.setText(lastEnteredString);
                            this.mEditText.setSelection(start);

                            showMessage(enteredAmt + " is exceeds the Total Balance");
                        }
                    } else if (!(num + discount <= Double.parseDouble(totalCollection))) {
                        this.mEditText.setText(Utility.replaceCharecterAt(enteredAmt, start, ' '));
                        this.mEditText.setSelection(start);

                        balanceAmountTV.startAnimation(AnimationUtils.loadAnimation(MerchantPaymentActivity.this, R.anim.blink));

                        showMessage("Amount Entered is exceeds the Total Balance");
                    }


                }


                this.mEditText.addTextChangedListener(this);


            } else if (this.mEditText.getTag().equals(MerchantPaymentActivity.DISCOUNT_AMOUNT_EDITTEXT_TAG)) {
                this.mEditText.removeTextChangedListener(this);

                String collectionEnteredAmt = collectionAmountET.getText().toString();

                if (!(collectionEnteredAmt != null && collectionEnteredAmt.length() > 0)) {
                    collectionEnteredAmt = "0.00";
                }
                if (collectionEnteredAmt != null && collectionEnteredAmt.equals(".")) {
                    collectionEnteredAmt = "0.00";
                }


                String discountEnteredAmt = s.toString();

                if (discountEnteredAmt != null && discountEnteredAmt.equals(".")) {
                    discountEnteredAmt = "0.00";
                }
                if (!discountEnteredAmt.equals("") && discountEnteredAmt.length() > 0) {

                    double num = Double.parseDouble(discountEnteredAmt);
                    double collection = Double.parseDouble(collectionEnteredAmt);

                    if (before > 0) {
                        String removedStr = Utility.replaceCharecterAt(lastEnteredString, start, ' ');

                        if (Double.parseDouble(removedStr) + collection > Double.parseDouble(totalCollection)) {
                            this.mEditText.setText(lastEnteredString);
                            this.mEditText.setSelection(start);

                            showMessage(enteredAmt + " is exceeds the Total Balance");
                        }
                    } else if (!(num + collection <= Double.parseDouble(totalCollection))) {


                        this.mEditText.setText(Utility.replaceCharecterAt(enteredAmt, start, ' '));
                        this.mEditText.setSelection(start);

                        balanceAmountTV.startAnimation(AnimationUtils.loadAnimation(MerchantPaymentActivity.this, R.anim.blink));

                        showMessage("Amount Entered is exceeds the Total Balance");
                    }
                }

                this.mEditText.addTextChangedListener(this);

            } else if (mEditText.getTag().toString().contains(PENDING_BILLS_TAG)) {

                this.mEditText.removeTextChangedListener(this);

                String pendingAmount = s.toString();

                if (pendingAmount != null && pendingAmount.equals(".")) {
                    pendingAmount = "0.00";
                }

                if (!pendingAmount.equals("") && pendingAmount.length() > 0) {

                    double num = Double.parseDouble(pendingAmount);
                    double collection = getTotalEnteredPendingBalanceExcept(this.mEditText.getTag().toString());


                    Log.d(TAG,"num : "+num + "  collection : "+collection + " getTotalAmount "+getTotalAmount());


                    if (before > 0) {
                        String removedStr = Utility.replaceCharecterAt(lastEnteredString, start, ' ');

                        if (Double.parseDouble(removedStr) + collection > getTotalAmount()) {
                            this.mEditText.setText(lastEnteredString);
                            this.mEditText.setSelection(start);

                            showMessage(enteredAmt + " is exceeds the Total Balance");
                        }
                    } else if (!(num + collection <= getTotalAmount())) {


                        Log.d(TAG,"enteredAmt : "+enteredAmt);
                        Log.d(TAG,"start : "+start);

                        this.mEditText.setText(Utility.replaceCharecterAt(enteredAmt, start, ' '));
                        this.mEditText.setSelection(start);
                        showMessage("Amount Entered is exceeds the Total Balance");
                    }

//                    if (!(num + collection <= getTotalAmount())) {
//                        this.mEditText.setText(pendingAmount.substring(0, pendingAmount.length() - 1));
//                        this.mEditText.setSelection(pendingAmount.length() - 1);
//
//                        showMessage("Amount Entered is exceeds the Total Amount");
//                    }
                }

                this.mEditText.addTextChangedListener(this);
            }

            lastEnteredString = this.mEditText.getText().toString();


        }

        @Override
        public void afterTextChanged(Editable s) {

            Log.d(TAG, "onTextChanged : called");


            if (this.mEditText.getTag().equals(MerchantPaymentActivity.COLLECTION_AMOUNT_EDITTEXT_TAG)) {


                double totalAmt = getTotalAmount();
                String formatted = NumberFormat.getCurrencyInstance(new Locale("en", "in")).format((totalAmt));
                totalAmountTV.setText(formatted);

            } else if (this.mEditText.getTag().equals(MerchantPaymentActivity.DISCOUNT_AMOUNT_EDITTEXT_TAG)) {

                double totalAmt = getTotalAmount();
                String formatted = NumberFormat.getCurrencyInstance(new Locale("en", "in")).format((totalAmt));
                totalAmountTV.setText(formatted);

            } else {

                double totalAmt = getTotalEnteredPendingBalanceExcept(null);


                getTotalEnteredPendingBalanceExcept(mEditText.getTag().toString());
            }


        }

        private double getTotalEnteredPendingBalanceExcept(String tag) {

            double total = 0;

            for (EditText editText : editTextList) {


                String editTextTag = editText.getTag().toString();

                if (tag != null && editTextTag.equals(tag)) {
                    continue;
                }

                String valueText = editText.getText().toString();
                if (!(valueText != null && valueText.length() > 0)) {
                    valueText = "0.0";
                }

                total = total + Double.parseDouble(valueText);

            }

//            Log.d(TAG, "Total : " + total);

            return total;


        }
    }


    private double getTotalAmount() {

        String totalStr = "";

        String collectionAmt = this.collectionAmountET.getText().toString().trim();

        if (collectionAmt != null && collectionAmt.equals(".")) {
            collectionAmt = "0.0";
        }

//        Log.d(TAG, "collection Amt :" + collectionAmt);

        String discountAmt = this.discountAmountET.getText().toString().trim();
        if (discountAmt != null && discountAmt.equals(".")) {
            discountAmt = "0.0";
        }

        discountAmt = discountAmt.trim();


        if (collectionAmt.trim().length() <= 0) {
            collectionAmt = "0.00";
        }
        if (discountAmt.trim().length() <= 0) {
            discountAmt = "0.00";
        }

        double totalAmt = Double.parseDouble(collectionAmt) + Double.parseDouble(discountAmt);


        return totalAmt;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.mp_menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.mp_submit_payment_BT) {
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void loadNext(int code) {

    }

    @Override
    public void loadPrev() {

        finish();

    }

    @Override
    protected int useResponseData(ParseResult result, String identifier) {
        return 0;
    }

    @Override
    protected void handleTransportException(Exception ex) {

    }

    @Override
    public void onClick(View v) {
        if (v == homeBt) {
            setResult(RESULT_CODE_FINISH_ACTIVITY);
            finish();
        }
        if (v == rightBt) {
            // showAlertMessage("YDKPro","Payment has been submitted Successfully.", Constants.ALERT_WITH_OK_BUTTON);

        }
    }
}

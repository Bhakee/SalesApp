package android.sales.rajesh.com.sales.Utils;

import android.content.Context;
import android.sales.rajesh.com.salesapp.R;
import android.view.View;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Karthik on 2/12/17.
 */

public class Utility {



    public static String getFormattedCurrency(String amount){


        double totalAmt = Double.parseDouble(amount);

        String formatted = NumberFormat.getCurrencyInstance(new Locale("en", "in")).format((totalAmt));

        return  formatted;
    }


    public static String replaceCharecterAt(String sourceString ,int postion, char replaceChar){

        String inputString = sourceString;
        String outPutString = "";
        char[] inputStringChars = inputString.toCharArray();
        inputStringChars[postion] = replaceChar;
        outPutString = String.valueOf(inputStringChars);

        outPutString = outPutString.replace(" ","");

        return outPutString;
    }



    public static int countMatches(String input ,char match){

        int charCount = 0;
        for(int i =0 ; i<input.length(); i++){
            if(input.charAt(i) == match){
                charCount++;
            }
        }

        return charCount;

    }



    public static void setRoundedBg(View ctx){

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ctx.setBackgroundDrawable( ctx.getResources().getDrawable(R.drawable.rounded_bg) );
        } else {
            ctx.setBackground( ctx.getResources().getDrawable(R.drawable.rounded_bg));
        }
    }
}

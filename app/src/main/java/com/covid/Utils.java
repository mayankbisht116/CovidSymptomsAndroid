package com.covid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.covid.model.AbstractData.DATE_FORMAT;

public class Utils {

    public static final String TERMS_CONDITIONS = "https://www.covidsymptom.org/terms-of-service";
    public static final String PRIVACY_POLICY = "https://www.covidsymptom.org/privacypolicy";
    private static String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean isValidEmail(String email){
        if(TextUtils.isEmpty(email))
            return false;
        else{
            return email.trim().matches(EMAIL_PATTERN);
        }
    }


    public static SpannableString getSpannableString(String string, String urlSpanStr) {
        URLSpan urlSpan = new URLSpan(urlSpanStr);
        SpannableString str = new SpannableString(string);
//        str.setSpan(urlSpan, 0, string.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        str.setSpan(new ForegroundColorSpan(Color.BLUE), 0, string.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return str;
    }

    public static void showErrorToast(Context cntxt, String msg){
        Toast.makeText(cntxt, msg, Toast.LENGTH_SHORT).show();
    }
    public static void showToast(Context cntxt, String msg){
        Toast.makeText(cntxt, msg, Toast.LENGTH_LONG).show();
    }

    private static ProgressDialog pd;
    public static void showLoading(Context cntxt, String msg){
        pd = new ProgressDialog(cntxt);
        if (msg != null) {
            pd.setMessage(msg);
        }
        pd.setCancelable(false);
        if (!pd.isShowing() && cntxt != null)
            pd.show();
    }

    public static void hideLoading(){
        if(pd != null && pd.isShowing()){
            pd.dismiss();
        }
    }

    public static String formatDateTime(long millis, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date(millis);
        String mDate = dateFormat.format(date);
        return mDate;
    }

    public static void showSingleBtnDialog(Context context, String title, String msg, String pstvBtn, DialogInterface.OnClickListener clkLstnr){
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(pstvBtn, clkLstnr)
                .show();
    }

    public static void showDoubleBtnDialog(Context context, String title, String msg, String pstvBtn, DialogInterface.OnClickListener clkLstnr,
                                           String ngtvBtn, DialogInterface.OnClickListener ngtvLstnr){
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(pstvBtn, clkLstnr)
                .setNegativeButton(ngtvBtn, ngtvLstnr)
                .show();
    }

    public static String getUserUuid(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        else
            return null;
    }

    public static String getHistoryFormatedDate(String time){
        if(TextUtils.isEmpty(time))
            return time;
        DateFormat srcDf = new SimpleDateFormat(DATE_FORMAT);
        // parse the date string into Date object
        Date date = null;
        try {
            date = srcDf.parse(time);
            DateFormat destDf = new SimpleDateFormat("MMMM dd, yyyy");
            time = destDf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getHistoryFormatedTime(String time){
        if(TextUtils.isEmpty(time))
            return time;
        DateFormat srcDf = new SimpleDateFormat(DATE_FORMAT);
        // parse the date string into Date object
        Date date = null;
        try {
            date = srcDf.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String sconds = cal.get(Calendar.SECOND) < 10?"0"+cal.get(Calendar.SECOND):""+cal.get(Calendar.SECOND);
            time = cal.get(Calendar.HOUR)+":"+sconds+" "+(cal.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }



//    public static void showSingleBtnDialog(Context context, String title, String msg, String pstvBtn, DialogInterface.OnClickListener clkLstnr){
//        new MaterialAlertDialogBuilder(context)
//                .setTitle(title)
//                .setMessage(msg)
//                .setPositiveButton(pstvBtn, clkLstnr)
//                .show();
//    }
//
//    public static void showDoubleBtnDialog(Context context, String title, String msg, String pstvBtn, DialogInterface.OnClickListener clkLstnr,
//                                     String ngtvBtn, DialogInterface.OnClickListener ngtvLstnr){
//        new MaterialAlertDialogBuilder(context)
//                .setTitle(title)
//                .setMessage(msg)
//                .setPositiveButton(pstvBtn, clkLstnr)
//                .setNegativeButton(ngtvBtn, ngtvLstnr)
//                .show();
//    }


}

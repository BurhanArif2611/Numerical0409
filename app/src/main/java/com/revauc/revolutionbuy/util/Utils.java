
package com.revauc.revolutionbuy.util;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.StyleSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.util.typeface.CustomFontTypeface;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils for app
 */
public class Utils {
    public static final String TAG = "Utils";

    @Nullable
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int drawableId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(drawableId);

        //noinspection deprecation
        return context.getResources().getDrawable(drawableId);
    }

    public static void setSpannTypeface(SpannableString spanTypeface, int start, int end, int type) {
        spanTypeface.setSpan(new StyleSpan(type), start, end, 0);

    }

    public static void setSpannColor(SpannableString spanColor, int start, int end, int colorCode) {
        spanColor.setSpan(new ForegroundColorSpan(colorCode), start, end, 0);
//        spanColor.setSpan(new BackgroundColorSpan(Color.WHITE), start, end, 0);
    }

    public static void setSpanFont(SpannableString span, int start, int end, Typeface typeface) {
        span.setSpan(new CustomFontTypeface("", typeface), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }


    public static void setSpannCommanProperty(TextView txv, SpannableString spanString) {
        txv.setMovementMethod(LinkMovementMethod.getInstance());
        txv.setText(spanString, TextView.BufferType.SPANNABLE);
        txv.setSelected(true);
    }

    /**
     * set click event on spannable string
     *
     * @param spanClick
     * @param start
     * @param end
     * @param clickableSpan
     */
    public static void setSpannClickEvent(SpannableString spanClick, int start, int end, ClickableSpan clickableSpan) {
        spanClick.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }


    public static boolean isSimulator() {
        boolean isSimulator = "google_sdk".equals(Build.PRODUCT)
                || "vbox86p".equals(Build.PRODUCT)
                || "sdk".equals(Build.PRODUCT);
        LogUtils.LOGD(TAG, "Build.PRODUCT= " + Build.PRODUCT + "  isSimulator= "
                + isSimulator);

        return isSimulator;
    }


    public static String getDollarPrize(double number) {

        String formated = NumberFormat.getNumberInstance(Locale.US).format(number);

        return "$" + formated;
    }

    public static String getDollarPrizeFormatted(double number) {
        String formated;
        if (number > 999999999) {
            int convertedAmount = (int) (number / 1000000000);
            formated = NumberFormat.getNumberInstance(Locale.US).format(convertedAmount) + " B";
            //Billion
        } else if (number > 999999) {
            //Million
            int convertedAmount = (int) (number / 1000000);
            formated = NumberFormat.getNumberInstance(Locale.US).format(convertedAmount) + " M";
        } else {
            formated = NumberFormat.getNumberInstance(Locale.US).format(number);
        }


        return "$" + formated;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern1 = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matcher1 = pattern1.matcher(email);

        return matcher1.matches();
    }

    public static String getEnteries(int total, int entered) {

        return entered + "/" + total;
    }

    public static String getSmall12hoursFormat(String time) {
        return time.replace("AM", "am").replace("PM", "pm");
    }

    public static String formatDateFromOnetoAnother(String date, String givenformat, String resultformat) {

        if (TextUtils.isEmpty(date)) {
            return "";
        }
        String result = "Date";
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;
        SimpleDateFormat sdfDay;
        try {
            sdf = new SimpleDateFormat(givenformat, Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            sdf1 = new SimpleDateFormat(resultformat, Locale.US);
            sdf1.setTimeZone(TimeZone.getDefault());
            sdfDay = new SimpleDateFormat("dd", Locale.US);
            sdfDay.setTimeZone(TimeZone.getDefault());
            int day = Integer.parseInt(sdfDay.format(sdf.parse(date)));
            String daySuffix = getDayNumberSuffix(day);
            result = sdf1.format(sdf.parse(date));
            result = result.replace("$$", daySuffix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String estTimetoCurrentTimeFormat(long millisecond,String format) {
        String currentMilliseconds = null;

        Calendar calendarUTC = Calendar.getInstance();
        calendarUTC.setTimeInMillis(millisecond);
        calendarUTC.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat currentFormat = new SimpleDateFormat(format);
        currentFormat.setTimeZone(TimeZone.getDefault());

        Calendar calendarLocal = Calendar.getInstance();
        currentMilliseconds = currentFormat.format(calendarUTC.getTime());

        try {
            calendarLocal.setTime(currentFormat.parse(currentMilliseconds));
            LogUtils.LOGD("", "Date: " + calendarLocal.getTime());
            calendarLocal.getTimeInMillis();
            LogUtils.LOGD("", "Difference: " + (calendarLocal.getTimeInMillis() - calendarUTC.getTimeInMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentMilliseconds;
    }

    public static String getLocalTimeFormatted(long millisecond,String format) {
        Calendar utcCal = Calendar.getInstance();
        utcCal.setTimeInMillis(millisecond);
        utcCal.setTimeZone(TimeZone.getTimeZone("UTC"));


        SimpleDateFormat sdfLocal = new SimpleDateFormat(format);
        sdfLocal.setTimeZone(TimeZone.getDefault());

        String localFormattedTime =  sdfLocal.format(utcCal.getTime());
        return localFormattedTime;


    }

    public static String getDuration(long createdMillis, Context context) {
        String time = "";
        String convertedCreatedDate = "";
        //Convert to Date formatter

        Calendar calendarUTC = Calendar.getInstance();
        calendarUTC.setTimeInMillis(createdMillis);
        calendarUTC.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat sdfDay = new SimpleDateFormat("dd", Locale.US);
        sdfDay.setTimeZone(TimeZone.getDefault());
        int day = Integer.parseInt(sdfDay.format(calendarUTC.getTime()));
        String daySuffix = getDayNumberSuffix(day);

        SimpleDateFormat currentFormat = new SimpleDateFormat("EEEE, MMM d$$, yyyy");
        currentFormat.setTimeZone(TimeZone.getDefault());
        convertedCreatedDate = currentFormat.format(calendarUTC.getTime());

        convertedCreatedDate = convertedCreatedDate.replace("$$", daySuffix);

        Date currentDate = new Date();

        long currentMillis = currentDate.getTime();

        if (currentMillis >= createdMillis) {
            long reqTime = (currentMillis - createdMillis);


            long sec = reqTime / 1000;
            if (sec > 0) {
                time = sec + " " + context.getString(R.string.sec);
            } else {
                time = "Just now";
            }

            if (sec >= 60) {
                long minute = sec / 60;
                time = minute + " " + context.getString(R.string.min);

                if (minute >= 60) {
                    long hrs = minute / 60;

                    time = hrs + " " + (hrs > 1 ? context.getString(R.string.hrs) : context.getString(R.string.hr));

                    if (hrs > 24) {
                        int days = (int) hrs / 24;
                        time = context.getString(R.string.yesterday);

                        if (days >= 2) {
                            time = convertedCreatedDate;//WEEK
                        }
                    }
                }
            }

        } else {
            time = "Just now";
        }
        return time;


    }


//    public static String getNotificationFormatedText(long difference) {
//        String formattedTime = null;
//
//        if (difference <= 59) {
//            formattedTime = secondsStr + "sec ago";
//        } else if (seconds <= 3599) //Max limit on minute
////        {
//
////        } else if ()
//
//            return formattedTime;
//
//    }


    public static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    public static String getPrizeNumberWithSuffix(int prizePosition) {
        String suffix;
        if (prizePosition >= 11 && prizePosition <= 13) {
            suffix = "th";
        } else {
            switch (prizePosition % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        return prizePosition + suffix;

    }

    public static String getNumberWithSuffix(String number) {
        if(number==null)
        {
            return "0th";
        }

        int prizePosition = Integer.parseInt(number);
        String suffix;
        if (prizePosition >= 11 && prizePosition <= 13) {
            suffix = "th";
        } else {
            switch (prizePosition % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        return prizePosition + suffix;

    }

    public static String getInningsWithSuffix(String innings,boolean isCompleted) {

        if(isCompleted)
        {
            return "Final";
        }
        if(innings==null)
        {
            return "Not Started";
        }

        int prizePosition = Integer.parseInt(innings);
        if(prizePosition==0)
        {
            return "Not Started";
        }
        String suffix;
        if (prizePosition >= 11 && prizePosition <= 13) {
            suffix = "th";
        } else {
            switch (prizePosition % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        return prizePosition + suffix+ " INN";

    }

    public static String getInningsWithSuffix(String innings, long timeInMillis,boolean isCompleted) {

        if(isCompleted)
        {
            return "Final";
        }
        if(innings==null)
        {
            return Utils.getLocalTimeFormatted(timeInMillis,Constants.DATE_FORMAT_EVENT_STATUS);
        }

        int prizePosition = Integer.parseInt(innings);
        if(prizePosition==0)
        {
            return Utils.getLocalTimeFormatted(timeInMillis,Constants.DATE_FORMAT_EVENT_STATUS);
        }
        String suffix;
        if (prizePosition >= 11 && prizePosition <= 13) {
            suffix = "th";
        } else {
            switch (prizePosition % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        return prizePosition + suffix+ " INN";

    }

    public static String getNFLInningsWithSuffix(String innings,boolean isCompleted) {
        if(isCompleted)
        {
            return "Final";
        }

        if(innings==null)
        {
            return "Not Started";
        }

        int prizePosition = Integer.parseInt(innings);
        if(prizePosition==0)
        {
            return "Not Started";
        }
        String suffix;
        if (prizePosition >= 11 && prizePosition <= 13) {
            suffix = "th";
        } else {
            switch (prizePosition % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        return prizePosition + suffix+ " QTR";

    }

    public static String getNFLInningsWithSuffix(String innings,long timeInMillis,boolean isCompleted) {
        if(isCompleted)
        {
            return "Final";
        }

        if(innings==null)
        {
            return Utils.getLocalTimeFormatted(timeInMillis,Constants.DATE_FORMAT_EVENT_STATUS);
        }

        int prizePosition = Integer.parseInt(innings);
        if(prizePosition==0)
        {
            return Utils.getLocalTimeFormatted(timeInMillis,Constants.DATE_FORMAT_EVENT_STATUS);
        }
        String suffix;
        if (prizePosition >= 11 && prizePosition <= 13) {
            suffix = "th";
        } else {
            switch (prizePosition % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        return prizePosition + suffix+ " QTR";

    }

    public static void setTypefaceToInputLayout(Context context, TextInputLayout inputLayout, String typeFace) {

        final Typeface tf = Typeface.createFromAsset(context.getAssets(), typeFace);

        inputLayout.getEditText().setTypeface(tf);
        try {
            // Retrieve the CollapsingTextHelper Field
            final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            collapsingTextHelperField.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
            final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(tf);
        } catch (Exception ignored) {
            // Nothing to do
        }
    }

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    public static String convertTofeetInches(Float str) throws NumberFormatException {
        Double value = Double.valueOf(str);
        int feet = (int) Math.floor(value / 30.48);
        int inches = (int) Math.round((value / 2.54) - ((int) feet * 12));
        return feet + "'" + inches + "\"";
    }

    public static void hideKeyboard(Context mContext, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showSnakbarFromTop(Context ctx, View v, String msg, boolean isError) {

        if (!StringUtils.isNullOrEmpty(msg)) {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append("");


            SpannableString spanStr = createIndentedText("" + msg, 0, 0);
            builder.append(spanStr);

//            Snackbar snackbar = Snackbar.make(v, builder, TSnackbar.LENGTH_LONG);
            TSnackbar snackbar = TSnackbar
                    .make(v, builder, TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.WHITE);

            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setGravity(Gravity.CENTER);
            if (isError) {
                snackbarView.setBackgroundColor(ContextCompat.getColor(ctx, R.color.color_snackbar_error_bg));
                textView.setTextColor(ContextCompat.getColor(ctx,R.color.color_red_border));
            } else {
                snackbarView.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimaryDark));
                textView.setTextColor(Color.WHITE);
            }



            Typeface tf = Typeface.createFromAsset(textView.getContext()
                    .getAssets(), ctx.getString(R.string.font_avenir_regular));

            textView.setTypeface(tf);
            textView.setGravity(Gravity.START);
            textView.setCompoundDrawablePadding(20);

            textView.setLineSpacing(1, 1.2f);
//            if (isError) {
//                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning_mini, 0, 0, 0);
//            } else {
//                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick_white, 0, 0, 0);
//            }
            //textView.setHeight(Utils.dpToPx(ctx,70));
            // snackbarView.setPadding(5, 25, 10, 5);
            snackbarView.setPadding(16, 32, 16, 16);
            snackbar.show();
        }
    }


    static SpannableString createIndentedText(String text, int marginFirstLine, int marginNextLines) {
        SpannableString result = new SpannableString(text);
        result.setSpan(new LeadingMarginSpan.Standard(marginFirstLine, marginNextLines), 0, text.length(), 0);
        return result;
    }

    public static void changeTabsFont(Context mContext, TabLayout view) {
        Typeface mTypeface = Typeface.createFromAsset(mContext.getAssets(), mContext.getString(R.string.font_avenir_regular));
        ViewGroup vg = (ViewGroup) view.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
                }
            }
        }
    }

    /**
     * Executed when user clicks on FINISH LAST TASK.
     */
    public static void finishAllJobs(Context mContext) {
        JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
        if (allPendingJobs.size() > 0) {
            // Finish the last one
            int jobId = allPendingJobs.get(0).getId();
            jobScheduler.cancel(jobId);
//            Toast.makeText(
//                    mContext, "cancelled" + jobId,
//                    Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(
//                    mContext, "No jobs to cancel",
//                    Toast.LENGTH_SHORT).show();
        }
    }

    public static String getSingleDigitDecimalPercentage(Double dollar) {
        String price = "";
        if (dollar != null) {
            DecimalFormat format = new DecimalFormat("0.0");
            price = format.format(dollar) + "%";
        }
        return price;
    }

    public static String getDoubleDigitDecimal(Double dollar) {
        String price = "";
        if (dollar != null) {
            DecimalFormat format = new DecimalFormat("0.00");
           price =   "$"+ format.format(dollar);
        }
        return price;
    }


    public static ColorStateList getTabTextColors(Context context) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked}, // checked
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.color_tab_inactive),
                ContextCompat.getColor(context, R.color.colorPrimary)
        };
        ColorStateList myList = new ColorStateList(states, colors);
        return myList;
    }


}

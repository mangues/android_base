/*
 * Copyright 2016. chenshufei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mangues.lifecircleapp.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mangues.lifecircleapp.LifeCircleApplication;
import com.mangues.lifecircleapp.log.MLogger;
//import com.robin.lazy.logger.LazyLogger;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 〈公共方法〉<br>
 * 〈功能详细描述〉
 *
 * @author yinzl
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@SuppressLint("NewApi")
public class PubUtils {

//    /**
//     * 对话框提示
//     *
//     * @param context
//     * @param msg
//     */
//    public static void showTipDialog(Context context, String msg) {
//        if (context instanceof Activity) {
//            Activity ac = ((Activity) context);
//            if (!ac.isFinishing()) {
//                TipDialog tipDialog = new TipDialog(context);
//                tipDialog.showTip(msg);
//            }
//        }
//
//    }
//
//    /**
//     * 对话框提示
//     *
//     * @param context
//     * @param msg
//     */
//    public static void showTipDialog(Context context, String msg, DialogInterface.OnDismissListener onDismissListener) {
//        if (context instanceof Activity) {
//            Activity ac = ((Activity) context);
//            if (!ac.isFinishing()) {
//                TipDialog tipDialog = new TipDialog(context);
//                if (null != onDismissListener)
//                    tipDialog.setOnDismissListener(onDismissListener);
//                tipDialog.showTip(msg);
//            }
//        }
//
//    }
//
//
//
//    public static void showImageTipDialog(Context context, String msg, DialogInterface.OnDismissListener onDismissListener) {
//        if (context instanceof Activity) {
//            Activity ac = ((Activity) context);
//            if (!ac.isFinishing()) {
//                TipImageDialog tipDialog = new TipImageDialog(context);
//                if (null != onDismissListener)
//                    tipDialog.setOnDismissListener(onDismissListener);
//                tipDialog.showTip(msg);
//            }
//        }
//
//    }
//
//    public static void showImageTipDialog(Context context, String msg, TipImageDialog.OnCancelDialogListener listener) {
//        if (context instanceof Activity) {
//            Activity ac = ((Activity) context);
//            if (!ac.isFinishing()) {
//                TipImageDialog tipDialog = new TipImageDialog(context);
//                if (null != listener)
//                    tipDialog.setOnCancelDialogListener(listener);
//                tipDialog.showTip(msg);
//            }
//        }
//
//    }



//    public static void showTipDialog(Context context, int msg) {
//        showTipDialog(context, context.getString(msg));
//    }

    /**
     * 返回中间删除线的文本
     */
    public static SpannableString getDeleteStr(String content) {
        SpannableString sps = new SpannableString(content);
        sps.setSpan(new StrikethroughSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sps;
    }

    /**
     * 设置文字的底部下划线文本
     *
     * @param tv
     * @param str
     */
    public static void setUnderlineStr(TextView tv, String str) {
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv.setText(str);
    }

    /**
     * 返回指定颜色的文本
     */
    public static SpannableString getColorStr(String content, int color) {

        SpannableString sps = new SpannableString(content); // 更换字体颜色
        sps.setSpan(new ForegroundColorSpan(color), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sps;
    }

    /**
     * 设置TextView的字体风格
     *
     * @param tv
     * @param tf
     */
    public static void setTextViewFont(TextView tv, Typeface tf) {
        tv.setTypeface(tf);
    }

    /**
     * 返回图片适配的高度
     *
     * @param picWidth
     * @param picHeight
     * @param desWidth
     * @return
     */
    public static int getRealHeight(int picWidth, int picHeight, int desWidth) {
        return (picHeight * desWidth) / picWidth;
    }

    /**
     * 根据图片应用在满屏的适配的高度
     *
     * @param picWidth
     * @param picHeight
     * @return
     */
    public static int getDefScreenRealHeight(int picWidth, int picHeight) {
        int sceenWidth = Density.getSceenWidth(LifeCircleApplication.getInstance());
        return getRealHeight(picWidth, picHeight, sceenWidth);
    }

    // 正则表达式验证
    public static boolean checkString(String s, String regex) {
        return s.matches(regex);
    }

    /**
     * 弹吐司
     *
     * @param ctx
     * @param txt
     */
    public static void popTipOrWarn(Context ctx, String txt) {
        Toast.makeText(ctx, txt, Toast.LENGTH_SHORT).show();
    }

    public static void popTipOrWarn(Context ctx, int txt) {
        Toast.makeText(ctx, txt, Toast.LENGTH_SHORT).show();
    }


//    public static void toLoginPage(Activity activity) {
//        Intent intent = activity.getIntent();
//        intent.setClass(activity, LoginActivity.class);
//        activity.startActivity(intent);
//    }


    // 将文件路径前加上"file://"
    public static String addFileBegin(String path) {
        if (path.startsWith("file://"))
            return path;
        return "file://" + path;
    }

    // 截取文件名称
    public static String getFileName(String path) {
        if (path == null) {
            return "";
        }
        int i = path.lastIndexOf("\\");
        if (i < 0) {
            i = path.lastIndexOf("/");
        }
        if (i < 0) {
            return path;
        }
        return path.substring(i + 1);
    }

    // 截取文件类型
    public static String getFileFormat(String path) {
        if (path == null) {
            return "";
        }
        int i = path.lastIndexOf(".");
        if (i < 0) {
            return path;
        }
        return path.substring(i + 1);
    }

    /**
     * 去掉小月份前面的0，譬如07月变成7月，12月不变。
     *
     * @param month
     * @return
     */
    public static String tranMonth(String month) {
        if ("0".equals(month.substring(0, 1))) {
            month = month.substring(1, 2);
        }
        return month;
    }

    /**
     * 产生唯一的ID号
     *
     * @return
     */
    public static String getUUId() {
        return UUID.randomUUID().toString();
    }


    // 显示整数金额
    public static final String getMoneyAmount(String payAmount) {
        Double douAmount = Double.valueOf(payAmount);
        int intAmount = douAmount.intValue();
        return ("¥" + intAmount / 100);
    }

    // 标准时间格式
    public static String getNowDatetimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        Date date = new Date();
        return sdf.format(date);
    }

    // 标准时间格式
    public static String getTomDatetimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return sdf.format(cal.getTime());
    }


    /**
     * type 0 代表 取奇数，1代表 取 偶数
     *
     * @param src
     * @param type
     * @return
     */
    public static String getOddOrEvenString(String src, int type) {
        if (src == null || src.length() <= 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int sbLength = src.length();
        for (int i = 0; i < sbLength; i++) {
            if (i % 2 == type) {
                sb.append(src.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 判断选择服务日期时间 是否早于手机当前时间 <一句话功能简述> <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean compare_date(String time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time.length() == 10) {
            time = time + " 00:00";
        }
        try {
            Date dt1 = df.parse(time);
            Date dt2 = new Date();
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 正则表达式 判断电话号是否正确
     */
    public static boolean isPhoneHomeNum(String num) {
        Pattern p = Pattern
                .compile("^(((13[0-9])|(15([0-3]|[5-9])|170)|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{8})$");
        Matcher m = p.matcher(num);
        return m.matches();
    }

    /**
     * 正则表达式 判断车牌号是否正确
     */
    public static boolean isNumCorrect(String num) {
        Pattern p = Pattern.compile("^(([\u4e00-\u9fa5])([a-z]|[A-Z])([a-z]|[A-Z]|[0-9]){5})$");
        Matcher m = p.matcher(num);
        return m.matches();
    }

    /**
     * 判断密码是否有中文 true代表无中文，false代表有中文
     */
    public static boolean isNotChin(String password) {
        return (password.getBytes().length == password.length()) ? true : false;

    }

    /**
     * 根据原始图，获取指定分辨率的小图
     *
     * @param Url
     * @param picType
     * @return
     */
    public static String getFitPicUrlPath(String Url, String picType) {
        String partOne = null;
        String partTow = null;
        if (TextUtils.isEmpty(Url)) {
            return Url;
        } else {
            int indexOf = Url.lastIndexOf(".");
            if (indexOf < 1) {
                return Url;
            }
            partOne = Url.substring(0, indexOf);
            partTow = Url.substring(indexOf);
        }

        if ("picType168X134".equals(picType)) {
            return partOne + "_168_134" + partTow;
        } else if ("picType218X174".equals(picType)) {
            return partOne + "_218_174" + partTow;
        } else if ("picType640X384".equals(picType)) {
            return partOne + "_640_384" + partTow;
        } else if ("picType168X134".equals(picType)) {
            return partOne + "_168_134" + partTow;
        } else if ("picType176X130".equals(picType)) {
            return partOne + "_176_130" + partTow;
        }
        return Url;
    }




    // PullToRefreshListView设置无数据时的界面显示
//    public static void setEmptyView(Context context, PullToRefreshListView listView) {
//        if (null == listView.getEmptyView()) {
//            View emptyView = View.inflate(context, R.layout.empty, null);
//            listView.setEmptyView(emptyView);
//        }
//    }


    public static boolean isPassword(String str) {// 判断字符格式是否正确
        if (!TextUtils.isEmpty(str)) {
            for (char c : str.toCharArray()) {
                if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '#'
                        || c == '@' || c == '_')) {
                    return false;
                }
            }
        }
        return true;
    }

    // 根据评分计算出是否需要显示半星
    public static boolean isHalfStar(String scoreStr) {// 判断是否显示半星，限制小数点后只有一位小数的，012，false，34567，true，89false，但是星的个数要+1；
        float temp = 0f;
        try {
            temp = Float.parseFloat(getScoreBy1(scoreStr));
        } catch (Exception e) { // can't reach
            e.printStackTrace();
        }
        if (temp != 0) {
            temp = temp * 10;
            int intTemp = ((int) temp) % 10;
            if (intTemp > 2 && intTemp <= 7) {
                return true;
            }
        }
        return false;
    }

    // 根据评分计算出全星的分数
    public static int starCount(String scoreStr) {// 判断全星个数，限制小数点后只有一位小数的，01234567，个数不变，89，星的个数要+1；
        float score = 0f;
        try {
            score = Float.parseFloat(getScoreBy1(scoreStr));
        } catch (Exception e) { // can't reach
            e.printStackTrace();
        }
        if (score != 0) {
            score = score * 10;
            int intTemp = (int) score;

            int intCount = intTemp / 10;
            int intYu = intTemp % 10;
            if (intYu > 7) {
                return intCount + 1;
            } else
                return intCount;
        }
        return 0;
    }

    // 商家商品评分，可能会出现多位小数，进行四舍五入，取1位小数处理
    public static String getScoreBy1(String score) {
        String grade_score = "0";
        float f = Float.parseFloat(score);
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();

        if ((int) f1 == f1)// 末尾是.0的，直接显示整数
            grade_score = String.valueOf((int) f1);
        else
            grade_score = String.valueOf(f1);
        return grade_score;
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /*
     * 将诸如20140630085447时间格式的字符串转化为2014-06-30 08:54:47
     */
    public static String getTimeStrFromStr(String intime) {
        try {
            StringBuffer timeStr = new StringBuffer();
            timeStr.append(intime.subSequence(0, 4));
            timeStr.append("-");
            intime = intime.substring(4);

            timeStr.append(intime.subSequence(0, 2));
            timeStr.append("-");
            intime = intime.substring(2);

            timeStr.append(intime.subSequence(0, 2));
            timeStr.append("   ");
            intime = intime.substring(2);

            timeStr.append(intime.subSequence(0, 2));
            timeStr.append(":");
            intime = intime.substring(2);

            timeStr.append(intime.subSequence(0, 2));
            timeStr.append(":");
            intime = intime.substring(2);

            timeStr.append(intime.subSequence(0, 2));
            return timeStr.toString();
        } catch (Exception e) {
            return intime;
        }

    }

    /**
     * 将日期格式YYYYMMDD转换为YYYY-MM-DD
     *
     * @param str
     * @return
     */
    public static String formatDate(String str) {
        if (str == null || str.length() != 8) {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        return strs;
    }

    /**
     * 将日期格式YYYY-MM-DD转换为YYYYMMDD
     *
     * @param str
     * @return
     */
    public static String formatDateStr(String str) {
        if (str == null || str.length() != 10) {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 4) + str.substring(5, 7) + str.substring(8, 10);
        return strs;
    }

    /**
     * 将日期格式YYYY-MM-DD转换为YYYYMMDD
     *
     * @param str
     * @return
     */
    public static String formatDateStrChinese(String str) {
        if (str == null || str.length() != 10) {
            return str;
        }
        String strs = "";
        strs = str.substring(0, 4) + "年" + str.substring(5, 7) + "月" + str.substring(8, 10) + "日";
        return strs;
    }

    /**
     * 将日期格式YYYY-MM转换为YYYY年MM月
     *
     * @param str
     * @return
     */
    public static String formatDateStr_chinese(String str) {
        if (str == null || str.length() != 7) {
            return str;
        }
        String strs = str.replace("-", "年") + "月";
        return strs;
    }

    /**
     * 功能描述: <获取当前时间> 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String date = sDateFormat.format(new Date());
        return date;
    }

    public static Bitmap getJieping(Activity activity) {// 获取截屏
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }


    /**
     * 同步  cookie 到 WebView中...
     *
     * @param context
     * @param url
     */
//    public static void synCookies(Context context, final String url) {
//        CookieStore cookieStore = CookieStoreHandler.getInstance();
//        final List<Cookie> cookies = cookieStore.getCookies();
//
//        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(XZCityCardApp.getInstance());
//        cookieSyncManager.sync();
//        final CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
//
//                @Override
//                public void onReceiveValue(Boolean value) {
//                    for (Cookie cookie : cookies) {
//                        cookieManager.setCookie(url, cookie.toString());
//                    }
//
//                    CookieSyncManager.getInstance().sync();
//                }
//            });
//        } else {
//            for (Cookie cookie : cookies) {
//                cookieManager.setCookie(url, cookie.toString());
//            }
//            CookieSyncManager.getInstance().sync();
//        }
//    }

    public static Map<String, String> fileFormatMap = new HashMap<String, String>();

    static {
        fileFormatMap.put("art", "image/x-jg");
        fileFormatMap.put("bmp", "image/bmp");
        fileFormatMap.put("gif", "image/gif");
        fileFormatMap.put("jpe", "image/jpeg");
        fileFormatMap.put("jpeg", "image/jpeg");
        fileFormatMap.put("jpg", "image/jpeg");
        fileFormatMap.put("png", "image/png");
    }

    // 修改图片类型
    public static String getImgRealMimeType(String imgFormat) {
        String mineType = fileFormatMap.get(imgFormat);
        if (TextUtils.isEmpty(mineType)) {
            mineType = "image/jpeg"; // 默认为jpg
        }
        return mineType;
    }

    // sim卡是否可读
    public static boolean isCanUseSim(Context context) {
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setListViewHeightBasedOnChildren(GridView listView, int col, int listHeight) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }



        int count = listAdapter.getCount();
        int totalItemHeight = 0;
        for(int i=0;i<count;i++){
            View listItem = listAdapter.getView(i, null, listView);
            if (i%col ==0){
                //TODO 计算其height高度
                // 获取item的高度和
                totalItemHeight += listItem.getMeasuredHeight();
                MLogger.e("i:"+i+" listItem.getMeasuredHeight():"+listItem.getMeasuredHeight());
            }
        }

        int gap = listHeight - totalItemHeight;

        int perGap = gap/(col-1);

        MLogger.e("listHeight:"+listHeight+" totalItemHeight:"+totalItemHeight+" gap:"+gap+" perGap:"+perGap);

        // 固定列宽，有多少列
        //int col = 3;// listView.getNumColumns();

        /*
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);*/
    }


    /**
     * 获得网络连接是否可用
     *
     * @param context
     * @return
     */
    public static boolean hasNetwork(Context context) {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo workinfo = con.getActiveNetworkInfo();
        if (workinfo == null || !workinfo.isAvailable()) {
            return false;
        } else {
            return true;
        }
    }

    public static String formatSelectText(String textData){
        if (TextUtils.isEmpty(textData)){
            return "未选择";
        }else{
            return textData;
        }
    }

    public static String decimalFormatPrice(long price) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(price / 100.00);
    }

}

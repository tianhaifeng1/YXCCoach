package com.lovezly.coach.util;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.module_common.util.GlideRoundTransform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DemoUtils {

    /**
     * 填充数据
     *
     * @return
     */
    public static List<String> getList() {
        List<String> info = new ArrayList<>();
        info.add("1");
        info.add("1");
        info.add("1");
        info.add("1");

        return info;
    }

    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 小数点前后大小不一致
     *
     * @param value
     * @return
     */
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.7f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 身份证加密
     *
     * @param idCardNum
     * @param front
     * @param end
     * @return
     */
    public static String idMask(String idCardNum, int front, int end) {
        //身份证不能为空
        if (StringUtils.isEmpty(idCardNum)) {
            return null;
        }
        //需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return null;
        }
        //需要截取的不能小于0
        if (front < 0 || end < 0) {
            return null;
        }
        //计算*的数量
        int asteriskCount = idCardNum.length() - (front + end);
        StringBuffer asteriskStr = new StringBuffer();
        for (int i = 0; i < asteriskCount; i++) {
            asteriskStr.append("*");
        }
        String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
        return idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
    }

    /**
     * 时间戳转字符串
     *
     * @return
     */
    public static String timeStamp2Date(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "MM月dd日 HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time * 1000));
    }

    /**
     * 字符串转时间戳
     *
     * @return
     */
    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /*
     * 将秒数转为时分秒
     * */
    public static String change(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        String th = h < 10 ? "0" + h : h + "";
        String td = d < 10 ? "0" + d : d + "";
        String ts = s < 10 ? "0" + s : s + "";

        return th + ":" + td + ":" + ts + "";
    }

    /*
     * 将秒数转为时分秒
     * */
    public static String change2(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        String td = d < 10 ? "0" + d : d + "";
        String ts = s < 10 ? "0" + s : s + "";

        if (h == 0) {
            if (d == 0) {
                return s + "秒";
            } else {
                return d + "分" + ts + "秒";
            }
        } else {
            return h + ":" + td + ":" + ts + "";
        }
    }

    public static String getPath(Context context) {
        String path = context.getCacheDir() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public static String ToDouble(double d) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00");
        return df.format(d);
    }

    /** 删除单个文件
     * @param filePathName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(String filePathName) {
        File file = new File(filePathName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                LogUtils.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePathName + "成功！");
                return true;
            } else {
                LogUtils.e("删除单个文件" + filePathName + "失败！");
                return false;
            }
        } else {
            LogUtils.e("删除单个文件失败：" + filePathName + "不存在！");
            return false;
        }
    }

    /**
     * 将object对象转成json格式字符串
     */
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    /**
     * 获取本地当前时间
     * yyyyMMddHHmmss
     *
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat SDF8 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        long millSecLong = System.currentTimeMillis();
        Date date = new Date(millSecLong);
        return SDF8.format(date);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    public static void bindImageViewC(Context context, Object path, int radius, ImageView imageView){
        Glide.with(context)
                .load(path)
                .transform(new CenterCrop(),new GlideRoundTransform(radius))
                .transition(withCrossFade())
                .into((ImageView) imageView);
    }
}

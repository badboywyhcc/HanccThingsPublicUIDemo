package com.developer.hancc.hanccthingspublicuisdk;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HanccThingsPublicToast {
    private static final long DROP_DUPLICATE_TOAST_TS = 2 * 1000; // 2s

    private static String sLast = "";
    private static long sLastTs = 0;
    private static Toast mBasicToast = null;

    /**
     * 上下文.
     */
    private static Context mContext = null;

    /**
     * 显示Toast.
     */
    public static final int SHOW_TOAST = 0;
    /**
     * 主要Handler类，在线程中可用
     * what：0.提示文本信息
     */
    private static Handler baseHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToast(mContext, msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Toast显示文字
     * @param context 上下文
     * @param str     要显示的文案
     */
    public static synchronized void showToast(Context context, String str){
        showToast(context,str,-1);
    }
    /**
     * Toast显示文字和icon的 toast
     * @param context 上下文
     * @param str     要显示的文案
     * @param resId   要显示的icon的索引
     */
    public static synchronized void showToast(Context context, String str,int resId) {
        try {
            mContext = context;
            long newTs = System.currentTimeMillis();
            if (str != null&& (!str.equals(sLast) || newTs < sLastTs || (newTs - sLastTs) > DROP_DUPLICATE_TOAST_TS)) {
                sLast = str;
                sLastTs = newTs;
                if (mBasicToast == null) {
                    mBasicToast = new Toast(context);
                }
                View toastView = LayoutInflater.from(context).inflate(R.layout.hanccthingspublictoast, null);
                TextView txt = (TextView) toastView.findViewById(R.id.hanccthingstoast_text);
                txt.setText(str);

                //目前暂时不支持设置图片
                ImageView contentImage = (ImageView) toastView.findViewById(R.id.hanccthingstoast_image);
                if(resId==-1){
                    contentImage.setVisibility(View.GONE);
                }else{
                    contentImage.setImageResource(resId);
                }

                mBasicToast.setView(toastView);
                //int px = dp2px(context, 60);
                //mBasicToast.setGravity(Gravity.BOTTOM, 0, 163);
                mBasicToast.setDuration(Toast.LENGTH_SHORT);// 默认只显示2S
                mBasicToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Toast显示文字和icon的 toast
     * @param context       上下文
     * @param resId         要显示的文案字符串资源ID
     * @param iconResId     要显示的icon的索引
     */
    public static synchronized void showToast(Context context, int resId,int iconResId) {
        try {
            mContext = context;
            String str = context.getResources().getString(resId);
            long newTs = System.currentTimeMillis();
            if (str != null&& (!str.equals(sLast) || newTs < sLastTs || (newTs - sLastTs) > DROP_DUPLICATE_TOAST_TS)) {
                sLast = str;
                sLastTs = newTs;
                if (mBasicToast == null) {
                    mBasicToast = new Toast(context);
                }
                View toastView = LayoutInflater.from(context).inflate(R.layout.hanccthingspublictoast, null);
                TextView txt = (TextView) toastView.findViewById(R.id.hanccthingstoast_text);
                txt.setText(str);

                //目前暂时不支持设置图片
                ImageView contentImage = (ImageView) toastView.findViewById(R.id.hanccthingstoast_image);
                if(resId==-1){
                    contentImage.setVisibility(View.GONE);
                }else{
                    contentImage.setImageResource(iconResId);
                }

                mBasicToast.setView(toastView);
                //int px = dp2px(context, 60);
                //mBasicToast.setGravity(Gravity.BOTTOM, 0, 163);
                mBasicToast.setDuration(Toast.LENGTH_SHORT);// 默认只显示2S
                mBasicToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 描述：在线程中提示文本信息.
     *
     * @param resId 要提示的字符串资源ID，消息what值为0,
     */
    public static void showToastInThread(Context context, int resId) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", context.getResources().getString(resId));
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 描述：在线程中提示文本信息.
     *
     * @param context
     * @param text
     */
    public static void showToastInThread(Context context, String text) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 让Toast立马消失
     */
    public static void cancelToast() {
        if (mBasicToast != null) {
            mBasicToast.cancel();
        }
    }

    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}

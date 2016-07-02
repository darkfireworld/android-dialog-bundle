package com.example.myapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 对话框集合
 */
final public class DialogBundle {
    /**
     * 不允许实例化
     */
    DialogBundle() {
    }

    /**
     * 提示对话框
     */
    public static class AlertDialog extends Dialog {
        /**
         * 点击事件
         */
        public interface Listener {
            void click();
        }

        /**
         * 构造器
         */
        public static class Builder {
            Context context;
            String message;
            String positiveText;
            String negativeText;

            Listener positiveListener;
            Listener negativeListener;

            public Builder(Context context) {
                this.context = context;
            }

            /**
             * 设置提示消息
             */
            public Builder setMessage(String message) {
                this.message = message;
                return this;
            }

            /**
             * 确定按钮
             */
            public Builder setPositiveButton(String positiveButtonText, Listener listener) {
                this.positiveText = positiveButtonText;
                this.positiveListener = listener;
                return this;
            }

            /**
             * 取消按钮
             */
            public Builder setNegativeButton(String negativeButtonText, Listener listener) {
                this.negativeText = negativeButtonText;
                this.negativeListener = listener;
                return this;
            }

            /**
             * 构造
             */
            public AlertDialog build() {
                return new AlertDialog(context, this);
            }
        }

        //VIEW ROOT
        LinearLayout ll_root;
        //提示消息
        TextView tv_message;
        //确定和取消栏目
        LinearLayout ll_cancel_and_sure;
        //取消
        TextView tv_cancel;
        //按钮中间虚线
        View v_middle_line;
        //确定
        TextView tv_sure;

        AlertDialog(Context context, final Builder builder) {
            super(context, R.style.alert_dialog);
            //UI
            {
                ll_root = new LinearLayout(context);
                {
                    //样式
                    {
                        ll_root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        ll_root.setOrientation(LinearLayout.VERTICAL);
                        ll_root.setGravity(Gravity.CENTER);
                        RoundRectShape roundRectShape = new RoundRectShape(new float[]{20, 20, 20, 20, 20, 20, 20, 20}, null, null);
                        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
                        drawable.getPaint().setColor(Color.parseColor("#AA404040"));
                        drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
                        ll_root.setBackgroundDrawable(drawable);
                    }
                    //内容配置
                    {
                        //内容
                        tv_message = new TextView(context);
                        //配置
                        {
                            ll_root.addView(tv_message);
                            //样式
                            {
                                tv_message.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                tv_message.setMinHeight(dp2px(context, 100));
                                tv_message.setGravity(Gravity.CENTER_VERTICAL);
                                tv_message.setPadding(dp2px(context, 20), 0, dp2px(context, 20), 0);
                                tv_message.setTextSize(15);
                                tv_message.setText(builder.message);
                                tv_message.setTextColor(Color.parseColor("#333333"));
                            }

                        }
                        //内容虚线
                        View v_message_line = new View(context);
                        {
                            ll_root.addView(v_message_line);
                            //样式
                            {
                                v_message_line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                                v_message_line.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CCCCCC")));
                            }

                        }
                        //设置 取消 和 确定
                        ll_cancel_and_sure = new LinearLayout(context);
                        //设置
                        {
                            ll_root.addView(ll_cancel_and_sure);
                            //样式
                            {
                                ll_cancel_and_sure.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 48)));
                                ll_cancel_and_sure.setGravity(Gravity.CENTER_VERTICAL);
                                ll_cancel_and_sure.setOrientation(LinearLayout.HORIZONTAL);
                            }
                            //子View
                            {
                                tv_cancel = new TextView(context);
                                //配置
                                {
                                    ll_cancel_and_sure.addView(tv_cancel);
                                    //样式
                                    {
                                        tv_cancel.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                                        tv_cancel.setGravity(Gravity.CENTER);
                                        tv_cancel.setSingleLine();
                                        tv_cancel.setTextSize(15);
                                        tv_cancel.setTextColor(Color.parseColor("#333333"));
                                        tv_cancel.setText("取消");
                                    }
                                }
                                //中间虚线
                                v_middle_line = new View(context);
                                {
                                    ll_cancel_and_sure.addView(v_middle_line);
                                    //样式
                                    {
                                        v_middle_line.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
                                        v_middle_line.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CCCCCC")));
                                    }
                                }
                                //确定按钮
                                tv_sure = new TextView(context);
                                //配置
                                {
                                    ll_cancel_and_sure.addView(tv_sure);
                                    //样式
                                    {
                                        tv_sure.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                                        tv_sure.setGravity(Gravity.CENTER);
                                        tv_sure.setSingleLine();
                                        tv_sure.setTextSize(15);
                                        tv_sure.setTextColor(Color.parseColor("#333333"));
                                        tv_sure.setText("确定");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //配置
            {
                //设置视图
                setContentView(ll_root);
                //取消
                setCancelable(true);
                // 设置居中 和 背景色
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                // 设置背景层透明度
                lp.dimAmount = 0.1f;
                lp.gravity = Gravity.CENTER;
                getWindow().setAttributes(lp);
                //设置监听器
                setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //如果点击外部，取消，则优先使用原先 取消 监听器，之后 再使用 确定监听器
                        if (builder.negativeListener != null) {
                            builder.negativeListener.click();
                        } else if (builder.positiveListener != null) {
                            builder.positiveListener.click();
                        }
                    }
                });
            }
            //根据builder 显示不同的样式
            {
                //配置按钮栏目
                if (builder.negativeListener == null && builder.positiveListener == null) {
                    //没有任何按钮
                    ll_cancel_and_sure.setVisibility(View.GONE);
                } else {
                    //存在其中一个按钮
                    ll_cancel_and_sure.setVisibility(View.VISIBLE);
                    if (builder.negativeListener != null && builder.positiveListener != null) {
                        //都存在
                        v_middle_line.setVisibility(View.VISIBLE);
                        tv_cancel.setVisibility(View.VISIBLE);
                        tv_sure.setVisibility(View.VISIBLE);
                    } else {
                        //隐藏虚线
                        v_middle_line.setVisibility(View.GONE);
                        if (builder.negativeListener != null && builder.positiveListener == null) {
                            //只有取消
                            tv_cancel.setVisibility(View.VISIBLE);
                            tv_sure.setVisibility(View.GONE);
                        } else {
                            //只有确定
                            tv_cancel.setVisibility(View.GONE);
                            tv_sure.setVisibility(View.VISIBLE);
                        }
                    }
                }
                //设置消息
                if (builder.message != null) {
                    tv_message.setText(builder.message);
                }
                //取消按钮标识
                if (builder.negativeText != null) {
                    tv_cancel.setText(builder.negativeText);
                }
                //取消监听器
                if (builder.negativeListener != null) {
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.this.dismiss();
                            builder.negativeListener.click();
                        }
                    });
                }
                //确定按钮
                if (builder.positiveText != null) {
                    tv_sure.setText(builder.positiveText);
                }
                //确定callback
                if (builder.positiveListener != null) {
                    tv_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.this.dismiss();
                            builder.positiveListener.click();
                        }
                    });
                }
            }
        }
    }

    /**
     * dp转 px.
     *
     * @param value the value
     * @return the int
     */
    private static int dp2px(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }

    /**
     * 进度对话框
     */
    public static class ProgressDialog extends Dialog {
        /**
         * 构造器
         */
        public static class Builder {
            String message;
            Context context;

            public Builder(Context context) {
                this.context = context;
            }

            /**
             * 设置消息提示
             */
            public Builder setMessage(String message) {
                this.message = message;
                return this;
            }

            /**
             * 构造
             */
            public ProgressDialog build() {
                return new ProgressDialog(context, this);
            }
        }

        //ROOT VIEW
        LinearLayout ll_root;
        //进度条Image
        ProgressBar pb_image;
        //文字提示
        TextView tv_message;

        ProgressDialog(Context context, Builder builder) {
            super(context, R.style.progress_dialog);
            //构造View
            {
                ll_root = new LinearLayout(context);
                //设置属性
                {
                    ll_root.setOrientation(LinearLayout.VERTICAL);
                    ll_root.setPadding(dp2px(context, 30), dp2px(context, 20), dp2px(context, 30), dp2px(context, 20));
                    ll_root.setGravity(Gravity.CENTER);
                    RoundRectShape roundRectShape = new RoundRectShape(new float[]{20, 20, 20, 20, 20, 20, 20, 20}, null, null);
                    ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
                    drawable.getPaint().setColor(Color.parseColor("#AA404040"));
                    drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
                    ll_root.setBackgroundDrawable(drawable);
                }
                //进度条
                {
                    pb_image = new ProgressBar(context);
                    //样式
                    {
                        ll_root.addView(pb_image);
                        pb_image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        pb_image.setBackgroundColor(Color.parseColor("#00000000"));
                    }
                }
                //提示文字
                {
                    tv_message = new TextView(context);
                    //样式
                    {
                        ll_root.addView(tv_message);
                        LinearLayout.LayoutParams lp_tv_message = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp_tv_message.setMargins(0, dp2px(context, 10), 0, 0);
                        tv_message.setLayoutParams(lp_tv_message);
                        tv_message.setTextColor(Color.parseColor("#FFFFFF"));
                        tv_message.setBackgroundColor(Color.parseColor("#00000000"));
                        tv_message.setVisibility(View.GONE);
                    }
                }
            }
            //设置基本属性
            {
                //设置ROOT VIEW
                setContentView(ll_root);
                // 按返回键是否取消
                setCancelable(false);
                // 设置居中 和 背景色
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                // 设置背景层透明度
                lp.dimAmount = 0.1f;
                lp.gravity = Gravity.CENTER;
                getWindow().setAttributes(lp);
            }
            //根据builder配置dialog
            {
                setMessage(builder.message);
            }
        }

        /**
         * 设置提示消息
         */
        public void setMessage(String message) {
            if (message != null && message.length() > 0) {
                tv_message.setText(message);
                tv_message.setVisibility(View.VISIBLE);
            } else {
                tv_message.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 显示Toast类型的Dialog
     */
    public static class ToastDialog extends Toast {
        static public class Builder {
            Context context;

            String alert;
            int icon;
            int delay;

            public Builder(Context context) {
                this.context = context;
            }

            public Builder setAlert(String alert) {
                this.alert = alert;
                return this;
            }

            public Builder setIcon(int icon) {
                this.icon = icon;
                return this;
            }

            public Builder setDelay(int delay) {
                this.delay = delay;
                return this;
            }

            public Toast build() {
                return new ToastDialog(context, this);
            }
        }

        //VIEW ROOT
        LinearLayout ll_root;
        //ICON
        ImageView iv_icon;
        //ALERT
        TextView tv_alert;

        //handler
        Handler handler;
        //builder
        int delay;

        ToastDialog(Context context, Builder builder) {
            super(context);
            //UI
            {
                ll_root = new LinearLayout(context);
                //样式
                {
                    ll_root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ll_root.setOrientation(LinearLayout.VERTICAL);
                    ll_root.setPadding(dp2px(context, 30), dp2px(context, 20), dp2px(context, 30), dp2px(context, 20));
                    ll_root.setGravity(Gravity.CENTER);
                    RoundRectShape roundRectShape = new RoundRectShape(new float[]{20, 20, 20, 20, 20, 20, 20, 20}, null, null);
                    ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
                    drawable.getPaint().setColor(Color.parseColor("#AA404040"));
                    drawable.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
                    ll_root.setBackgroundDrawable(drawable);
                }
                //子VIEW
                {
                    //ICON
                    {
                        iv_icon = new ImageView(context);
                        ll_root.addView(iv_icon);
                        //样式
                        {
                            LinearLayout.LayoutParams lp_iv_icon = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            lp_iv_icon.setMargins(0, 0, 0, dp2px(context, 15));
                            iv_icon.setLayoutParams(lp_iv_icon);
                            iv_icon.setVisibility(View.GONE);
                        }
                    }
                    //ALERT
                    {
                        tv_alert = new TextView(context);
                        ll_root.addView(tv_alert);
                        //样式
                        {
                            tv_alert.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tv_alert.setTextColor(Color.parseColor("#FFFFFF"));
                            tv_alert.setVisibility(View.GONE);
                            tv_alert.setBackgroundColor(Color.parseColor("#00000000"));
                        }
                    }
                }
            }
            //配置
            {
                setView(ll_root);
                setGravity(Gravity.CENTER, 0, 0);
                setDuration(Toast.LENGTH_SHORT);
                if (builder.icon > 0) {
                    //设置图片
                    iv_icon.setImageResource(builder.icon);
                    iv_icon.setVisibility(View.VISIBLE);
                }
                if (builder.alert != null && builder.alert.length() > 0) {
                    tv_alert.setText(builder.alert);
                    tv_alert.setVisibility(View.VISIBLE);
                }
                this.delay = builder.delay <= 0 ? Toast.LENGTH_SHORT : builder.delay;
                this.handler = new Handler(Looper.getMainLooper());
            }
        }

        @Override
        public void show() {
            super.show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToastDialog.this.cancel();
                }
            }, this.delay);
        }
    }

    public static class ListDialog extends Dialog {


        /**
         * Holder
         */
        static class Holder {
            public String what;
            @Nullable
            public Object extra;

            public Holder(String what, Object extra) {
                this.what = what;
                this.extra = extra;
            }
        }

        /**
         * 监听器
         */
        public interface Listener {
            /**
             * 点击了条目
             */
            void click(String what, @Nullable Object extra);

            /**
             * 取消了点击
             */
            void cancel();
        }

        /**
         * 构造器
         */
        public static class Builder {

            Context context;
            String title;
            List<Holder> holderList = new ArrayList<Holder>();
            Listener listener;

            public Builder(Context context) {
                this.context = context;
            }

            /**
             * 设置Title
             */
            public Builder setTile(String title) {
                this.title = title;
                return this;
            }

            /**
             * 添加一个选项
             */
            public Builder addItem(String what, @Nullable Object extra) {
                holderList.add(new Holder(what, extra));
                return this;
            }

            /**
             * 设置监听器
             */
            public Builder setListener(Listener listener) {
                this.listener = listener;
                return this;
            }

            /**
             * 构造
             */
            public ListDialog build() {
                return new ListDialog(context, this);
            }
        }

        //VIEW ROOT
        LinearLayout ll_root;
        //Title Layout
        LinearLayout ll_title;
        //Title
        TextView tv_title;
        //ListView
        ListView lv_main;

        ListDialog(final Context context, final Builder builder) {
            super(context, R.style.list_dialog);
            //UI
            {
                ll_root = new LinearLayout(context);
                //样式
                {
                    ll_root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ll_root.setOrientation(LinearLayout.VERTICAL);
                    ll_root.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ll_root.setGravity(Gravity.CENTER);
                }
                //子View
                {
                    //Title
                    {
                        ll_title = new LinearLayout(context);
                        ll_root.addView(ll_title);
                        //样式
                        {
                            ll_title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            ll_title.setOrientation(LinearLayout.VERTICAL);
                        }
                        //子View
                        {
                            //title
                            {
                                tv_title = new TextView(context);
                                ll_title.addView(tv_title);
                                //样式
                                {
                                    tv_title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 70)));
                                    tv_title.setTextColor(Color.parseColor("#4AB4FF"));
                                    tv_title.setGravity(Gravity.CENTER_VERTICAL);
                                    tv_title.setTextSize(20);
                                    tv_title.setPadding(dp2px(context, 10), 0, dp2px(context, 10), 0);
                                    tv_title.setSingleLine();
                                    tv_title.setEllipsize(TextUtils.TruncateAt.END);
                                    tv_title.setText("标题");
                                }
                            }
                            //虚线
                            {
                                View v_line = new View(context);
                                ll_title.addView(v_line);
                                //样式
                                {
                                    v_line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                                    v_line.setBackgroundColor(Color.parseColor("#CCCCCC"));
                                }
                            }
                        }
                    }
                    //列表
                    {
                        lv_main = new ListView(context);
                        ll_root.addView(lv_main);
                        //样式
                        {
                            lv_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            lv_main.setScrollbarFadingEnabled(false);
                            lv_main.setFastScrollEnabled(false);
                            lv_main.setHorizontalScrollBarEnabled(false);
                            lv_main.setVerticalScrollBarEnabled(false);
                            lv_main.setScrollingCacheEnabled(false);
                            lv_main.setSelector(new ColorDrawable(Color.parseColor("#00000000")));
                            lv_main.setFooterDividersEnabled(false);
                            lv_main.setHeaderDividersEnabled(false);
                            lv_main.setDivider(new ColorDrawable(Color.parseColor("#CCCCCC")));
                            lv_main.setDividerHeight(1);
                        }
                    }
                }
            }
            //配置
            {
                setContentView(ll_root);
                setCancelable(true);
                //高度
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                //居中
                lp.gravity = Gravity.CENTER;
                // 设置背景层透明度
                lp.dimAmount = 0.1f;
                //设置dialog的宽度和高度
                WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                if (windowManager != null) {
                    Display display = windowManager.getDefaultDisplay();
                    lp.width = (int) (display.getWidth() * 0.75);
                }
                getWindow().setAttributes(lp);
            }
            //配置View
            {
                //title
                {
                    if (builder.title != null) {
                        ll_title.setVisibility(View.VISIBLE);
                        tv_title.setText(builder.title);
                    } else {
                        ll_title.setVisibility(View.GONE);
                    }
                }
                //ListView
                {
                    lv_main.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return builder.holderList.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return null;
                        }

                        @Override
                        public long getItemId(int position) {
                            return 0;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            if (convertView == null) {
                                TextView tv_item = new TextView(context);
                                convertView = tv_item;
                                tv_item.setTextSize(17);
                                tv_item.setTextColor(Color.parseColor("#333333"));
                                tv_item.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                tv_item.setMinHeight(dp2px(context, 55));
                                tv_item.setPadding(dp2px(context, 10), dp2px(context, 10), dp2px(context, 10), dp2px(context, 10));

                            }
                            TextView tv_item = (TextView) convertView;
                            tv_item.setText(builder.holderList.get(position).what);
                            return tv_item;
                        }
                    });
                    //设置监听器
                    lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ListDialog.this.dismiss();
                            if (builder.listener != null) {
                                Holder holder = builder.holderList.get(position);
                                builder.listener.click(holder.what, holder.extra);
                            }
                        }
                    });
                }
                //设置取消监听器
                {
                    setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (builder.listener != null) {
                                builder.listener.cancel();
                            }
                        }
                    });
                }
            }
        }
    }
}

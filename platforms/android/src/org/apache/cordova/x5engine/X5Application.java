
package org.apache.cordova.x5engine;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;

public class X5Application extends Application {
    private final static String DEBUG_TAG = "X5Application";
    private Dialog LoadingDialog = null;
    private Handler LoadingHandler = null;


    @Override
    public void onCreate() {
        super.onCreate();
        initHandler();

        setHandlerAction(1);
        initX5();
    }

    private void initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
                setHandlerAction(arg0 ? 2 : 3);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    /**
     * 通知
     *
     * @param action
     */
    private void setHandlerAction(int action) {
        Message msg = LoadingHandler.obtainMessage();
        msg.what = action;
        LoadingHandler.sendMessage(msg);
    }

    /**
     * 初始化提示框
     */
    private void initHandler() {
        HandlerThread LoadingHandlerThread = new HandlerThread("X5LoadingHandlerThread");
        LoadingHandlerThread.start();
        LoadingHandler = new Handler(LoadingHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setTitle("请稍等...");
                        builder.setMessage("初始化内核引擎...");
                        builder.setCancelable(false);
                        LoadingDialog = builder.create();
                        LoadingDialog.setCancelable(false);
                        LoadingDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        LoadingDialog.show();
                        break;
                    case 2:
                        LoadingDialog.dismiss();
                        break;
                    case 3:
                        Toast.makeText(X5Application.this, "引擎初始化失败", Toast.LENGTH_LONG);
                        LoadingDialog.dismiss();
                        break;
                }
            }
        };
    }


}
package org.apache.cordova.x5engine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Build;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import java.util.HashMap;


public class X5LoadService extends IntentService {
    private Dialog LoadingDialog = null;
    private Handler LoadingHandler = null;

    public X5LoadService() {
        super("X5LoadService");
    }
    public X5LoadService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        initX5();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void initX5() {
        initHandler();
		if (Build.VERSION.SDK_INT <= 20) {
			setHandlerAction(1);
		}
		
		//启动加速 http://soft.tbs.imtt.qq.com/17421/tbs_res_imtt_tbs_release_dex2oat.doc
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        QbSdk.initTbsSettings(map);
        //  预加载X5内核
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
                        // AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        // builder.setTitle("请稍等...");
                        // builder.setMessage("初始化内核引擎...");
                        // builder.setCancelable(false);
                        // LoadingDialog = builder.create();
                        // LoadingDialog.setCancelable(false);
                        // LoadingDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
                        // LoadingDialog.show();
                        break;
                    case 2:
                        // LoadingDialog.dismiss();
                        break;
                    case 3:
                        // Toast.makeText(X5LoadService.this, "引擎初始化失败", Toast.LENGTH_LONG);
                        // LoadingDialog.dismiss();
                        break;
                }
            }
        };
    }

    //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
        @Override
        public void onViewInitFinished(boolean arg0) {
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            Log.d("app", " onViewInitFinished is " + arg0);
             
			 if (Build.VERSION.SDK_INT <= 20) {
				setHandlerAction(arg0 ? 2 : 3);
			}
        }

        @Override
        public void onCoreInitFinished() {
           
			if (Build.VERSION.SDK_INT <= 20) {
				 setHandlerAction(3);
			}
        }
    };

}
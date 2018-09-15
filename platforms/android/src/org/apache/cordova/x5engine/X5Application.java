
package org.apache.cordova.x5engine;

import android.app.Application;
import android.content.Intent;

import com.tencent.smtt.sdk.QbSdk;

public class X5Application extends Application {
    private final static String DEBUG_TAG = "X5Application";


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            //预加载x5内核
            QbSdk.setDownloadWithoutWifi(true);
            Intent intent = new Intent(this, X5LoadService.class);
            startService(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
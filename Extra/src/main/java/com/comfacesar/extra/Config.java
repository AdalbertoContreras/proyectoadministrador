package com.comfacesar.extra;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;


public class Config {
    private static String imei = null;
    private static int permiso_pedido = 0;
    public void iniciar_config(Activity activity)
    {
        TelephonyManager mngr = (TelephonyManager) activity.getBaseContext().getSystemService(activity.getBaseContext().TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //para versiones con android 6.0 o superior.
            int permissionCheck = ContextCompat.checkSelfPermission(
                    activity, android.Manifest.permission.READ_PHONE_STATE );
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if(permiso_pedido == 0)
                {
                    ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_PHONE_STATE }, 225);
                    permiso_pedido = 1;
                }

            } else {
                imei = Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } else{
            //para versiones inferiores a android 6.0.
            imei = Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        int tipo_movil = mngr.getPhoneType();
    }

    public static String getImei() {
        return imei;
    }
}

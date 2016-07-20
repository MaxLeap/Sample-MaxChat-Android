package cn.maxleap.chatdemo.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.maxleap.utils.FileHandles;
import com.maxleap.utils.FileUtils;
import com.maxleap.utils.MLUtils;

import java.util.Locale;

import static android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EHRPD;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSUPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;
import static com.maxleap.utils.ManifestInfo.hasPermission;

/**
 * A helper class for device.
 */
public final class DeviceInfo {

    private static final String HIDDEN_DEVICE_ID_FILE_NAME = ".maxleap";

    private static String deviceId;

    private static final Object lock = new Object();

    private DeviceInfo() {
    }

    /**
     * Return the current language in lower case.
     *
     * @return the language (e.g. zh)
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * Return the current locale in lower case.
     *
     * @return the locale (e.g. cn)
     */
    public static String getLocale() {
        return Locale.getDefault().toString();
    }

    /**
     * Return the android system version in number.
     *
     * @return the version (e.g. 4.2.2)
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * Return the device model in lower case.
     *
     * @return the device model (e.g. gt-i9500)
     */
    public static String getDeviceModel() {
        return MLUtils.toLowerCase(android.os.Build.MODEL);
    }


    /**
     * Return the device manufacturer in lower case.
     * @return    device manufacturer
     */

    public  static  String getDeviceManufacturer(){
        return MLUtils.toLowerCase(android.os.Build.MANUFACTURER);
    }



    /**
     * Return the device name in lower case.
     *
     * @return the device name (e.g. hammerhead)
     */
    public static String getDeviceName() {
        return MLUtils.toLowerCase(android.os.Build.DEVICE);
    }

    /**
     * Return the current national in lower case.
     *
     * @return the national (e.g. cn)
     */
    public static String getNational() {
        String country = Locale.getDefault().getCountry();
        return MLUtils.toLowerCase(country);
    }

    /**
     * Return the device type in lower case.
     *
     * @return "android"
     */
    public static String getDeviceType() {
        return "android";
    }

    /**
     * Return the device flag in lower case.
     *
     * @param context the Android context
     * @return "tablet" if current device is a tablet. "phone" if current device is a phone.
     */
    public static String getDeviceFlag(final Context context) {
        if (isTabletDevice(context)) {
            return "tablet";
        }
        return "phone";
    }

    /**
     * Check if the current device is a tablet.
     *
     * @param context the Android context
     * @return True if current device is a tablet. False if current device is a phone.
     */
    public static boolean isTabletDevice(final Context context) {
        // Verifies if the Generalized Size of the device is XLARGE to be
        // considered a Tablet
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);

        // If XLarge, checks if the Generalized Density is at least MDPI
        // (160dpi)
        if (xlarge) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();

            // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
            // DENSITY_TV=213, DENSITY_XHIGH=320
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
                    || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {

                // Yes, this is a tablet!
                return true;
            }
        }

        // No, this is not a tablet!
        return false;
    }

    /**
     * Check if the current network is available.
     *
     * @param context the Android context
     * @return True if network is available. False if network is not available.
     */
    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Return the current network in lower case.
     *
     * @param context the android context
     * @return the network (e.g. wifi, 2g, 3g, 4g or other)
     */
    public static String getNetwork(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return "other";
        }
        int networkType = info.getType();
        if (networkType == ConnectivityManager.TYPE_WIFI) {
            return "wifi";
        }
        return getMobileNetworkName(context);
    }

    private static String getMobileNetworkName(final Context context) {
        if (!hasPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            return "other";
        }
        Object object = context.getSystemService(Context.TELEPHONY_SERVICE);
        if (object == null) {
            return "other";
        }

        TelephonyManager telephonyManager = (TelephonyManager) object;
        switch (telephonyManager.getNetworkType()) {
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return "2g";
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
                return "3g";
            case NETWORK_TYPE_LTE:
                return "4g";
            default:
                return "other";
        }
    }

    /**
     * Return the current network operator in lower case.
     *
     * @param context the android context
     * @return the carrier (e.g. 460,00)
     */
    public static String getCarrier(final Context context) {
        if (hasPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            Object object = context.getSystemService(Context.TELEPHONY_SERVICE);
            if (object == null) {
                return "0" + MLUtils.Separator.COMMA.value() + "0";
            }

            TelephonyManager tm = (TelephonyManager) object;

            String operator = tm.getNetworkOperator();
            if (TextUtils.isEmpty(operator)) {
                return "0" + MLUtils.Separator.COMMA.value() + "0";
            }
            String mcc = operator.substring(0, 3);
            String mnc = operator.substring(3);
            return mcc + MLUtils.Separator.COMMA.value() + mnc;
        }
        return "0" + MLUtils.Separator.COMMA.value() + "0";
    }

    /**
     * Return the current device id.
     *
     * @param context the android context
     * @return the device id
     */
    public static String getDeviceId(final Context context) {
        synchronized (lock) {
            if (null != DeviceInfo.deviceId)
                return DeviceInfo.deviceId;

            String deviceId = null;
            if (hasPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                Object object = context.getSystemService(Context.TELEPHONY_SERVICE);
                if (object != null) {
                    TelephonyManager tm = (TelephonyManager) object;
                    deviceId = tm.getDeviceId();
                }
            }

            if (deviceId == null) {
                if (FileUtils.isSDCardEnabled()) {
                    deviceId = FileHandles.sdcard(HIDDEN_DEVICE_ID_FILE_NAME)
                            .tryReadString();
                }
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = MLUtils.getUUID();

                    if (FileUtils.isSDCardEnabled()) {
                        FileHandles.sdcard(HIDDEN_DEVICE_ID_FILE_NAME).
                                createNewFile();
                        FileHandles.sdcard(HIDDEN_DEVICE_ID_FILE_NAME).tryWriteString(
                                deviceId);
                    }
                }
            }
           DeviceInfo.deviceId = deviceId;
        }
        return deviceId;
    }

    /**
     * Return the current mac address. Permission "android.permission.ACCESS_WIFI_STATE"
     * is needed.
     *
     * @param context the android context
     * @return the mac address
     */
    public static String getMACAddress(final Context context) {
        if (hasPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            if (null != info) {
                return info.getMacAddress();
            }
            return null;
        }
        return null;
    }

    /**
     * Return the current screen resolution.
     *
     * @param context the android context
     * @return the resolution (e.g. 1080*1920)
     */
    public static String getResolution(final Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels + MLUtils.Separator.ASTERISK.value()
                + metrics.heightPixels;
    }


    public  static  int getWidth(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }


    public  static  int getHeight(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }





    /**
     * Return the current app version.
     * @param context   the android content
     * @return  app version
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";

        try {
            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


}

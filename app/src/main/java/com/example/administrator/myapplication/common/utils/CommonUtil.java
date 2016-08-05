package com.example.administrator.myapplication.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CommonUtil {

//    public static void Log(String tag, String log) {
//        if(UmsConstants.Debug) {
//            Log.d(tag, log);
//        }
//    }

    public static boolean checkPermissions(Context context, String permission) {
        PackageManager localPackageManager = context.getPackageManager();
        int flag = localPackageManager.checkPermission(permission, context.getPackageName());
        return flag == PackageManager.PERMISSION_GRANTED;
    }

//    public static String getPackageName(Context context) {
//        if (checkPermissions(context, "android.permission.GET_TASKS")) {
//            ActivityManager am = (ActivityManager)
//                    context.getSystemService(Context.ACTIVITY_SERVICE);
//            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//            return cn.getPackageName();
//        } else {
//            Log("lost permission", "android.permission.GET_TASKS");
//            return null;
//        }
//    }

//    public static String getActivityName(Context context) {
//        if (context == null) {
//            return "";
//        }
//        try {
//            if (checkPermissions(context, "android.permission.GET_TASKS")) {
//                ActivityManager am = (ActivityManager)
//                        context.getSystemService(Context.ACTIVITY_SERVICE);
//                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//                return cn.getShortClassName();
//            } else {
//                Log("lost permission", "android.permission.GET_TASKS");
//                return "";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

//    public static boolean isNetworkTypeWifi(Context context) {
//        if (checkPermissions(context, "android.permission.INTERNET")) {
//            ConnectivityManager cManager = (ConnectivityManager)
//                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo info = cManager.getActiveNetworkInfo();
//
//            if (info != null && info.isAvailable() && info.getType() == ConnectivityManager.TYPE_WIFI) {
//                return true;
//            } else {
//                Log("error", "Network not wifi");
//                return false;
//            }
//        } else {
//            Log(" lost  permission", "lost----> android.permission.INTERNET");
//            return false;
//        }
//    }

    public static String getNetworkType(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            return "WIFI";
        }
        TelephonyManager manager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = manager.getNetworkType();
        String typeString = "UNKNOWN";
        if (type == TelephonyManager.NETWORK_TYPE_CDMA) {
            typeString = "CDMA";
        }
        if (type == TelephonyManager.NETWORK_TYPE_EDGE) {
            typeString = "EDGE";
        }
        if (type == TelephonyManager.NETWORK_TYPE_EVDO_0) {
            typeString = "EVDO_0";
        }
        if (type == TelephonyManager.NETWORK_TYPE_EVDO_A) {
            typeString = "EVDO_A";
        }
        if (type == TelephonyManager.NETWORK_TYPE_GPRS) {
            typeString = "GPRS";
        }
        if (type == TelephonyManager.NETWORK_TYPE_HSDPA) {
            typeString = "HSDPA";
        }
        if (type == TelephonyManager.NETWORK_TYPE_HSPA) {
            typeString = "HSPA";
        }
        if (type == TelephonyManager.NETWORK_TYPE_HSUPA) {
            typeString = "HSUPA";
        }
        if (type == TelephonyManager.NETWORK_TYPE_UMTS) {
            typeString = "UMTS";
        }
        if (type == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
            typeString = "UNKNOWN";
        }
        if (type == TelephonyManager.NETWORK_TYPE_1xRTT) {
            typeString = "1xRTT";
        }
        if (type == 11) {
            typeString = "iDen";
        }
        if (type == 12) {
            typeString = "EVDO_B";
        }
        if (type == 13) {
            typeString = "LTE";
        }
        if (type == 14) {
            typeString = "eHRPD";
        }
        if (type == 15) {
            typeString = "HSPA+";
        }
        return typeString;
    }

//    public static boolean isNetworkAvailable(Context context) {
//        if (checkPermissions(context, "android.permission.INTERNET")) {
//            ConnectivityManager cManager = (ConnectivityManager)
//                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo info = cManager.getActiveNetworkInfo();
//            if (info != null && info.isAvailable()) {
//                return true;
//            } else {
//                Log("error", "Network error");
//                return false;
//            }
//        } else {
//            Log(" lost  permission", "lost----> android.permission.INTERNET");
//            return false;
//        }
//    }
//
//    public static boolean isWiFiActive(Context inContext) {
//        if (checkPermissions(inContext, "android.permission.ACCESS_WIFI_STATE")) {
//            Context context = inContext.getApplicationContext();
//            ConnectivityManager connectivity = (ConnectivityManager)
//                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (connectivity != null) {
//                NetworkInfo[] info = connectivity.getAllNetworkInfo();
//                if (info != null) {
//                    for (int i = 0; i < info.length; i++) {
//                        if (info[i].getTypeName().equals("WIFI")
//                                && info[i].isConnected()) {
//                            return true;
//                        }
//                    }
//                }
//            }
//            return false;
//        } else {
//            Log("lost permission", "lost--->android.permission.ACCESS_WIFI_STATE");
//            return false;
//        }
//    }

    private static String intToIp(int i) {
        return (i & 0xFF)
                + "." + ((i >> 8) & 0xFF)
                + "." + ((i >> 16) & 0xFF)
                + "." + ((i >> 24) & 0xFF);
    }

//    public static String getIpAddress(Context inContext) {
//        try{
//            if (checkPermissions(inContext, "android.permission.ACCESS_WIFI_STATE")) {
//                Context context = inContext.getApplicationContext();
//                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                if(wifiManager != null){
//                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                    return intToIp(wifiInfo.getIpAddress());
//                }else{
//                    return "";
//                }
//            } else {
//                Log("lost permission", "lost--->android.permission.ACCESS_WIFI_STATE");
//                return "";
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            return "";
//        }
//    }

    /**
     * To determine whether it contains a gyroscope
     */
    public static boolean isHaveGravity(Context context) {
        SensorManager manager = (SensorManager)
                context.getSystemService(Context.SENSOR_SERVICE);
        if (manager == null) {
            return false;
        }
        return true;
    }

//    public static UmsLocation getLatitudeAndLongitude(Context context, boolean mUseLocationService) {
//        UmsLocation posotion = new UmsLocation();
//        if (mUseLocationService) {
//            LocationManager loctionManager = (LocationManager)
//                    context.getSystemService(Context.LOCATION_SERVICE);
//            List<String> matchingProviders = loctionManager.getAllProviders();
//            for (String prociderString : matchingProviders) {
//                Location location = loctionManager.getLastKnownLocation(prociderString);
//                if (location != null) {
//                    posotion.latitude = location.getLatitude() + "";
//                    posotion.longitude = location.getLongitude() + "";
//                } else {
//                    posotion.latitude = "";
//                    posotion.longitude = "";
//                }
//            }
//        } else {
//            posotion.latitude = "";
//            posotion.longitude = "";
//        }
//        return posotion;
//    }
//
//    /**Get the base station information*/
//    public static SCell getCellInfo(Context context) throws Exception {
//        TelephonyManager mTelNet = (TelephonyManager)
//                context.getSystemService(Context.TELEPHONY_SERVICE);
//        GsmCellLocation location = null;
//        try {
//            location = (GsmCellLocation) mTelNet.getCellLocation();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        if (location == null) {
//            Log("GsmCellLocation Error", "GsmCellLocation is null");
//            return null;
//        }
//
//        try {
//            String operator = mTelNet.getNetworkOperator();
//            int mcc = Integer.parseInt(operator.substring(0, 3));
//            int mnc = Integer.parseInt(operator.substring(3));
//            int cid = location.getCid();
//            int lac = location.getLac();
//
//            SCell cell = new SCell();
//            cell.MCC = mcc;
//            cell.MCCMNC = Integer.parseInt(operator);
//            cell.MNC = mnc;
//            cell.LAC = lac;
//            cell.CID = cid;
//            return cell;
//        } catch (Exception e){
//            return null;
//        }
//    }

    public static String getVersionCode(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return String.valueOf(info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            return "1";
        }
    }

    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            if (context == null) {
                return "";
            }
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

//    public static String getDeviceIMSI(Context context) {
//        if (context == null) {
//            return "";
//        }
//        if (checkPermissions(context, "android.permission.READ_PHONE_STATE")) {
//            String imsi = "";
//            TelephonyManager tm = (TelephonyManager)
//                    context.getSystemService(Context.TELEPHONY_SERVICE);
//            imsi = tm.getSubscriberId();
//            if (imsi != null) {
//                Log("commonUtil", "imsi:" + imsi);
//                return imsi;
//            } else {
//                Log("commonUtil", "imsi is null");
//                return "";
//            }
//        } else {
//            Log("lost permissioin", "lost----->android.permission.READ_PHONE_STATE");
//            return "";
//        }
//    }

    public static String getDeviceId(Context context) {
        if (context == null) {
            return "";
        }
        if (checkPermissions(context, "android.permission.READ_PHONE_STATE")) {
            String deviceId = "";
            TelephonyManager tm = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
//            if (deviceId != null) {
//                Log.i("commonUtil", "deviceId:" + deviceId);
//                return deviceId;
//            } else {
//                Log("commonUtil", "deviceId is null");
//                return "";
//            }
        }
        return "";
    }

    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return localSimpleDateFormat.format(date);
    }


//    /**
//     * 把IMEI用一大整数代替
//     *
//     * @param imei
//     * @return
//     */
//    private static String imeiToBigInteger(String imei) {
//        BigInteger result = new BigInteger("0");
//        try {
//            BigInteger n = new BigInteger("16");
//            String md5 = UmsMd5Util.getMd5(imei);
//            int size = md5.length();
//            for (int i = 0; i < size; i++) {
//                BigInteger a = new BigInteger("" + md5.charAt(i), 16);
//                BigInteger b = n.pow(size - 1 - i);
//                result = result.add(a.multiply(b));
//            }
//            return result.toString();
//        } catch (Exception e) {
//        }
//        return result.toString();
//    }

    /**
     * 获取手机串号(IMEI), 为空时返回MAC地址
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 平板imei为null
        if (TextUtils.isEmpty(imei)) {
            imei = getMAC(context);
        }
        return imei;
    }

    /**
     * 获取手机MAC地址
     *
     * @param context
     * @return
     */
    public static String getMAC(Context context) {
        String mac = null;
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                mac = info.getMacAddress();
            }
        } catch (Exception e) {

        }
        if (mac == null) {
            mac = "";
        }
        return mac;
    }

//    /**
//     * 获取mid
//     * */
//    public static String getMID(Context context) {
//        SharedPreferences sp = getUmsPreferences(context);
//        final String mid_key = "ums_mid";
//        String mid = sp.getString(mid_key, null);
//        if(TextUtils.isEmpty(mid)) {
//            mid = imeiToBigInteger(getIMEI(context));
//            sp.edit().putString(mid_key, mid).commit();
//        }
//        return mid;
//    }

    /**
     * 获取uuid
     */
    public static String getUUID(Context context) {
        SharedPreferences sp = getUmsPreferences(context);
        final String uuid_key = "ums_uuid";
        String uuid = sp.getString(uuid_key, null);
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
            sp.edit().putString(uuid_key, uuid).commit();
        }
        return uuid;
    }



    private static SharedPreferences getUmsPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences("ums", Context.MODE_PRIVATE);
        return sp;
    }
}

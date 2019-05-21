package tools.aapt;

import java.io.*;

public class ApkUtil {

    private static final String APPLICATION_LABEL = "application-label";
    private static final String PACKAGE = "package";
    private static final String SDK_VERSION = "sdkVersion";
    private static final String TARGET_SDK_VERSION = "targetSdkVersion";

    private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";

    public static void main(String[] args) {
        String apkPath1 = "F:\\AndroidStudioProjects\\ProjectsEnZhi\\AdminPay\\adm-mobile-v2\\app\\guiyang\\release\\AdminPay-guiyang-0.1.3.2-release.apk";
        String apkPath2 = "F:\\AndroidStudioProjects\\ProjectsEnZhi\\AdminPay\\adm-mobile-v2\\app\\yunnan\\release\\AdminPay-yunnan-1.3.0-release.apk";

        String aaptPath = "E:\\Android\\sdk\\build-tools\\28.0.3\\aapt.exe";
        parseApk(apkPath1, aaptPath);
        parseApk(apkPath2, aaptPath);
    }


    public static ApkInfo parseApk(String apkPath, String aaptPath) {
        Process process = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            ApkInfo apkInfo = new ApkInfo();
            ProcessBuilder builder = new ProcessBuilder();
            builder.redirectErrorStream(true);

            process = builder.command(aaptPath, "d", "badging", apkPath).start();
            inputStream = process.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
//                System.out.println(temp);
                //package: name='com.enzhi.medicalpay' versionCode='11' versionName='0.1.5.12'
                //sdkVersion:'21'
                //targetSdkVersion:'28'
                //application-label:'慧医通'
                //application: label='慧医通' icon='res/mipmap-xhdpi-v4/icon_launcher.png'
                //launchable-activity: name='com.enzhi.medicalpay.ActivityStartUp'  label='' icon=''
                if (temp.startsWith(PACKAGE)) {
                    String[] packageInfo = temp.split(SPLIT_REGEX);
                    apkInfo.setPackageName(packageInfo[2]);
                    apkInfo.setVersionCode(packageInfo[4]);
                    apkInfo.setVersionName(packageInfo[6]);
                } else if (temp.startsWith(SDK_VERSION)) {
                    apkInfo.setSdkVersion(getPropertyInQuote(temp));
                } else if (temp.startsWith(TARGET_SDK_VERSION)) {
                    apkInfo.setTargetSdkVersion(getPropertyInQuote(temp));
                } else if (temp.startsWith(APPLICATION_LABEL)) {
                    apkInfo.setApplicationLabel(getPropertyInQuote(temp));
                }
            }
            System.out.println(apkInfo.toString());
            return apkInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getPropertyInQuote(String source) {
        int index = source.indexOf("'") + 1;
        return source.substring(index, source.indexOf('\'', index));
    }

}
package tools.aapt;

public class ApkInfo {

    private String packageName, versionCode, versionName;//packageName --> applicationId，也是多渠道配置的Id
    private String sdkVersion, targetSdkVersion;
    private String applicationLabel;//applicationName

    @Override
    public String toString() {
        return "{\"packageName\":\"" + packageName +
                "\",\"versionCode\":\"" + versionCode +
                "\",\"versionName\":\"" + versionName +
                "\",\"sdkVersion\":\"" + sdkVersion +
                "\",\"targetSdkVersion\":\"" + targetSdkVersion +
                "\",\"applicationLabel\":\"" + applicationLabel + "\"}";
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public void setTargetSdkVersion(String targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public String getApplicationLabel() {
        return applicationLabel;
    }

    public void setApplicationLabel(String applicationLabel) {
        this.applicationLabel = applicationLabel;
    }
}
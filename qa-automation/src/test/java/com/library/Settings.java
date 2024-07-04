package com.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tests.enums.platform.UserLogin;
import com.library.classes.MsGraphKeys;
import com.library.enums.DriverType;
import com.library.enums.Environment;
import com.library.helpers.PasswordHelper;
import com.library.helpers.SettingsHelper;

public class Settings
{
    private static Settings iSettings = null;
    private Environment environment = null;
    private DriverType driverType;
    private Boolean correctPageScreenshots;
    private Boolean fullHeightScreenShots;
    private Integer defaultTimeoutMS;
    private Integer defaultMaxWaitMS;
    private String resultsHost;
    private Boolean useAsyncLogging;
    private Boolean logAsyncResultsQueueSize;
    private Boolean apiLogging;
    private Integer resultsUserId;

    private Integer existingSuiteId;
    private String apiToken;
    private String passwordKey;
    private String awsAccessKeyEncrypted;
    private String awsSecretKeyEncrypted;
    private String awsS3BucketName;
    private Boolean useMaximiseScreen;
    private Boolean videoAvailable;
    private String videoUrl;
    private Boolean logAllVideo;
    private String localDownloadFolder;
    private String selenoidHost;
    private Boolean quickWatchLogging;
    private Boolean useDBLogging;
    private Boolean verboseFindLogging;

    private Boolean verbosePerformanceLogging;
    private Integer slowEnvironmentWaitMS;

    private String athenaUserName;
    private String athenaEncryptedPassword;

    private Boolean simpleSmoke;

    @JsonIgnore
    private MsGraphKeys msGraphKeys;

    private UserLogin forcedUserLoginEnum;

    public Environment getEnvironment()
    {
        if (environment == null) {
            environment = Environment.valueOf(SettingsHelper.getInstance().getProperty("environment"));
        }
        return environment;
    }

    public void setDriveType(DriverType driverType)
    {
        this.driverType = driverType;
    }

    public DriverType getDriverType()
    {
        if (driverType == null) {
            driverType = DriverType.valueOf(SettingsHelper.getInstance().getProperty("driverType"));
        }
        return driverType;
    }

    public boolean getCorrectPageScreenshots()
    {
        if (correctPageScreenshots == null) {
            correctPageScreenshots = SettingsHelper.getInstance().getProperty("correctPageScreenshots").equalsIgnoreCase("true");
        }
        return correctPageScreenshots;
    }

    public boolean getFullHeightScreenShots()
    {
        if (fullHeightScreenShots == null) {
            fullHeightScreenShots = SettingsHelper.getInstance().getProperty("fullHeightScreenShots").equalsIgnoreCase("true");
        }
        return fullHeightScreenShots;
    }

    public int getDefaultTimeoutMS()
    {
        if (defaultTimeoutMS == null) {
            defaultTimeoutMS = Integer.parseInt(SettingsHelper.getInstance().getProperty("defaultTimeoutMS"));
        }
        return defaultTimeoutMS;
    }

    public int getDefaultMaxWaitMS()
    {
        if (defaultMaxWaitMS == null) {
            defaultMaxWaitMS = Integer.parseInt(SettingsHelper.getInstance().getProperty("defaultMaxWaitMS"));
        }
        return defaultMaxWaitMS;
    }

    public String getResultsHost()
    {
        if (resultsHost == null) {
            resultsHost = SettingsHelper.getInstance().getProperty("resultsHost");
        }
        return resultsHost;
    }

    public boolean isUseAsyncLogging()
    {
        if (useAsyncLogging == null) {
            useAsyncLogging = SettingsHelper.getInstance().getProperty("useAsyncLogging").equalsIgnoreCase("true");
        }
        return useAsyncLogging;
    }

    public boolean isLogAsyncResultsQueueSize()
    {
        if (logAsyncResultsQueueSize == null) {
            logAsyncResultsQueueSize = SettingsHelper.getInstance().getProperty("logAsyncResultsQueueSize").equalsIgnoreCase("true");
        }
        return logAsyncResultsQueueSize;
    }

    public boolean isApiLogging()
    {
        if (apiLogging == null) {
            apiLogging = SettingsHelper.getInstance().getProperty("apiLogging").equalsIgnoreCase("true");
        }
        return apiLogging;
    }

    public boolean isLogAllVideo()
    {
        if (logAllVideo == null) {
            logAllVideo = SettingsHelper.getInstance().getProperty("logAllVideo").equalsIgnoreCase("true");
        }
        return logAllVideo;
    }

    public int getResultsUserId()
    {
        if (resultsUserId == null) {
            resultsUserId = Integer.parseInt(SettingsHelper.getInstance().getProperty("resultsUserId"));
        }
        return resultsUserId;
    }

    public Integer getExistingSuiteId()
    {
        if (existingSuiteId == null) {
            String property = SettingsHelper.getInstance().getProperty("existingSuiteId");
            if (property != null && property.length() > 0) {
                existingSuiteId = Integer.parseInt(property);
            }
        }
        return existingSuiteId;
    }

    public String getApiToken()
    {
        if (apiToken == null) {
            apiToken = SettingsHelper.getInstance().getProperty("apiToken");
        }
        return apiToken;
    }

    public String getPasswordKey()
    {
        if (passwordKey == null) {
            passwordKey = SettingsHelper.getInstance().getProperty("passwordKey");
        }
        return passwordKey;
    }

    public String getAwsAccessKeyEncrypted()
    {
        if (awsAccessKeyEncrypted == null) {
            awsAccessKeyEncrypted = SettingsHelper.getInstance().getProperty("awsAccessKeyEncrypted");
        }
        return awsAccessKeyEncrypted;
    }

    public String getAwsSecretKeyEncrypted()
    {
        if (awsSecretKeyEncrypted == null) {
            awsSecretKeyEncrypted = SettingsHelper.getInstance().getProperty("awsSecretKeyEncrypted");
        }
        return awsSecretKeyEncrypted;
    }

    public String getAwsS3BucketName()
    {
        if (awsS3BucketName == null) {
            awsS3BucketName = SettingsHelper.getInstance().getProperty("awsS3BucketName");
        }
        return awsS3BucketName;
    }

    public Boolean isUseMaximiseScreen()
    {
        if (useMaximiseScreen == null) {
            useMaximiseScreen = SettingsHelper.getInstance().getProperty("useMaximiseScreen").equalsIgnoreCase("true");
        }
        return useMaximiseScreen;
    }

    public Boolean isVideoAvailable()
    {
        if (videoAvailable == null) {
            videoAvailable = SettingsHelper.getInstance().getProperty("videoAvailable").equalsIgnoreCase("true");
        }
        return videoAvailable;
    }

    public String getVideoUrl()
    {
        if (videoUrl == null) {
            videoUrl = SettingsHelper.getInstance().getProperty("videoUrl");
        }
        return videoUrl;
    }

    public String getLocalDownloadFolder()
    {
        if (localDownloadFolder == null) {
            localDownloadFolder = SettingsHelper.getInstance().getProperty("localDownloadFolder");
        }
        return localDownloadFolder;
    }

    public String getSelenoidHost()
    {
        if (selenoidHost == null) {
            selenoidHost = SettingsHelper.getInstance().getProperty("selenoidHost");
        }
        return selenoidHost;
    }

    public Boolean isQuickWatchLogging()
    {
        if (quickWatchLogging == null) {
            quickWatchLogging = SettingsHelper.getInstance().getProperty("quickWatchLogging").equalsIgnoreCase("true");
        }
        return quickWatchLogging;
    }

    public Boolean isUseDBLogging()
    {
        if (useDBLogging == null) {
            useDBLogging = SettingsHelper.getInstance().getProperty("useDBLogging").equalsIgnoreCase("true");
        }
        return useDBLogging;
    }

    public Boolean isVerboseFindLogging()
    {
        if (verboseFindLogging == null) {
            verboseFindLogging = SettingsHelper.getInstance().getProperty("verboseFindLogging").equalsIgnoreCase("true");
        }
        return verboseFindLogging;
    }

    public Boolean isVerbosePerformanceLogging()
    {
        if (verbosePerformanceLogging == null) {
            verbosePerformanceLogging = SettingsHelper.getInstance().getProperty("verbosePerformanceLogging").equalsIgnoreCase("true");
        }
        return verbosePerformanceLogging;
    }

    public MsGraphKeys getMsGraphKeys()
    {
        if (msGraphKeys == null) {
            msGraphKeys = MsGraphKeys.builder()
                    .tenantId(PasswordHelper.decryptPassword(getPasswordKey(), SettingsHelper.getInstance().getProperty("msGraphTenantId")))
                    .appId(PasswordHelper.decryptPassword(getPasswordKey(), SettingsHelper.getInstance().getProperty("msGraphAppId")))
                    .secret(PasswordHelper.decryptPassword(getPasswordKey(), SettingsHelper.getInstance().getProperty("msGraphSecret")))
                    .build();
        }
        return msGraphKeys;
    }

    public UserLogin getForcedUserLoginEnum()
    {
        if (forcedUserLoginEnum == null) {
            String rawFromSettings = SettingsHelper.getInstance().getProperty("forcedUserLoginEnum");
            if (rawFromSettings != null) {
                forcedUserLoginEnum = UserLogin.valueOf(rawFromSettings);
            }
        }
        return forcedUserLoginEnum;
    }

    public Integer getSlowEnvironmentWaitMS()
    {
        if (slowEnvironmentWaitMS == null) {
            String property = SettingsHelper.getInstance().getProperty("slowEnvironmentWaitMS");
            if (property != null) {
                slowEnvironmentWaitMS = Integer.parseInt(property);
            }
        }
        return slowEnvironmentWaitMS;
    }

    public String getAthenaUserName()
    {
        if (athenaUserName == null) {
            athenaUserName = SettingsHelper.getInstance().getProperty("athenaUserName");
        }
        return athenaUserName;
    }

    public String getAthenaEncryptedPassword()
    {
        if (athenaEncryptedPassword == null) {
            athenaEncryptedPassword = SettingsHelper.getInstance().getProperty("athenaEncryptedPassword");
        }
        return athenaEncryptedPassword;
    }

    public Boolean isSimpleSmoke()
    {
        if (simpleSmoke == null) {
            simpleSmoke = SettingsHelper.getInstance().getProperty("simpleSmoke").equalsIgnoreCase("true");
        }
        return simpleSmoke;
    }
}

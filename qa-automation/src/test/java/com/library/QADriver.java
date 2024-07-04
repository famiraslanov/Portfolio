package com.library;

import com.library.enums.DriverType;
import com.library.listeners.QASuiteListener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QADriver
{
    public static void start()
    {
        RemoteWebDriver startedDriver = null;
        DriverType driverType = Store.getSettings().getDriverType();

        switch (driverType) {
            case localChrome:
            case headlessChrome:
                startedDriver = startLocalChrome(driverType);
                break;
            case selenoidChrome:
                startedDriver = startSelenoidChrome();
                break;
            case selenoidFirefox:
                startedDriver = startSelenoidFirefox();
                break;
            case none:
                break;
            default:
                Log.error("Unknown driverType: " + driverType);
        }

        Store.setDriver(startedDriver);
        setSessionId();
        setWindowSize();
    }


    public static void quit()
    {
        if (Store.getDriver() != null) {
            Log.debug("Quit Driver");
            try {
                Store.getDriver().quit();
            } catch (Exception e) {
                Log.exception(e);
            }
        }
    }

    private static Map<String, Object> setChromePrefs()
    {
        String localDownloadPath = QASuiteListener.settings.getLocalDownloadFolder();
        if (QADriver.isSelenoidBased()) {
            localDownloadPath = "/home/selenium/Downloads";
        }

        Map<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", Paths.get(localDownloadPath).toAbsolutePath().toString());
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("safebrowsing.enabled", false);
        chromePrefs.put("plugins.always_open_pdf_externally", true);
        chromePrefs.put("plugins.plugins_disabled", new ArrayList<String>()
        {
            {
                add("Chrome PDF Viewer");
            }
        });
        return chromePrefs;
    }

    private static RemoteWebDriver startLocalChrome(DriverType driverType)
    {
        // Needed for chrome 111
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        Log.debug("Start driver");
        ChromeOptions options = new ChromeOptions();
        if (driverType == DriverType.headlessChrome) {
            options.setHeadless(true);
            options.addArguments("--headless=new");
        }
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("prefs", setChromePrefs());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return new ChromeDriver(options);
    }

    private static RemoteWebDriver startSelenoidChrome()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("browserVersion", QASuiteListener.isAppleM2 ? "100.0" : "120.0");
        capabilities.setCapability("selenoid:options", Map.<String, Object>of("enableVNC", true, "enableVideo", true));

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", setChromePrefs());
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized"); // open Browser in maximized mode
        options.addArguments("--disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        RemoteWebDriver driver;
        try {
            driver = new RemoteWebDriver(URI.create(QASuiteListener.settings.getSelenoidHost() + "/wd/hub").toURL(), capabilities, false);
        } catch (MalformedURLException e) {
            Log.exception(e);
            throw new RuntimeException(e);
        }

        return driver;
    }

    private static RemoteWebDriver startSelenoidFirefox()
    {
        RemoteWebDriver driver;

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.dir", Paths.get(QASuiteListener.settings.getLocalDownloadFolder()).toAbsolutePath().toString());
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/excel, application/pdf");
        profile.setPreference("pdfjs.disabled", true);
        FirefoxOptions firefoxOption = new FirefoxOptions();
        firefoxOption.setProfile(profile);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "firefox");
        capabilities.setCapability("browserVersion", "latest");
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOption);
        try {
            driver = new RemoteWebDriver(
                    URI.create("http://localhost:4444/wd/hub").toURL(),
                    capabilities
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return driver;
    }

    public static void setWindowSize()
    {
        if (Store.getDriver() != null && !QADriver.isSelenoidBased()) {
            // Industry-standard Full HD 1080p resolution, this display has a resolution of 1920 x 1080... but 13" laptop is smaller
            Store.getDriver().manage().window().setPosition(new Point(0, 0));
            if (Store.getSettings().isUseMaximiseScreen()) {
                Log.debug("Screen size set to maximise");
                Store.getDriver().manage().window().maximize();
            } else {
                Log.debug("Screen size set to Laptop: 1440w x 711h");
                Dimension newDimension = new Dimension(1440, 711);
                Store.getDriver().manage().window().setSize(newDimension);
            }
        }
    }

    private static void setSessionId()
    {
        if (Store.getDriver() != null) {
            Store.setSessionId(Store.getDriver().getSessionId().toString());
        }
    }

    public static Boolean isSelenoidBased()
    {
        return QASuiteListener.settings.getDriverType() == DriverType.selenoidFirefox ||
                QASuiteListener.settings.getDriverType() == DriverType.selenoidChrome;
    }
}

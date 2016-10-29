package com.IgniteEvents.automation.common.config;

import static com.IgniteEvents.automation.common.config.DriverBinaryMapper.configureBinary;
import static com.IgniteEvents.automation.common.config.DriverBinaryMapper.getBinaryPath;
import static com.IgniteEvents.automation.common.config.OperatingSystem.getOperatingSystem;
import static com.IgniteEvents.automation.common.config.SystemArchitecture.getSystemArchitecture;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.IgniteEvents.automation.common.SeleneseTestCase;

//import com.IgniteEvents.ignite.automation.common.SeleneseTestCase;

public enum DriverType implements DriverSetup {

    FIREFOX {
        public DesiredCapabilities getDesiredCapabilities() {
        	DesiredCapabilities dc = DesiredCapabilities.firefox();
        	dc.setCapability(FirefoxDriver.PROFILE, SeleneseTestCase.getFirefoxProfile());
        	return dc;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new FirefoxDriver(capabilities);
        }
    },
    CHROME {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            /*capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, String> chromePreferences = new HashMap<String, String>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);*/
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
        	ChromeOptions options = new ChromeOptions();
        	options.addArguments("--start-maximized");
        	return new ChromeDriver(options);
        }

        @Override
        public String getWebDriverSystemPropertyKey() {
            return "webdriver.chrome.driver";
        }
    },
    IE {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            capabilities.setCapability("requireWindowFocus", true);
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new InternetExplorerDriver(capabilities);
        }

        @Override
        public String getWebDriverSystemPropertyKey() {
            return "webdriver.ie.driver";
        }
    },
    SAFARI {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleanSession", true);
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new SafariDriver(capabilities);
        }
    },
    PHANTOMJS {
        public DesiredCapabilities getDesiredCapabilities() {
            DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
            capabilities.setCapability("takesScreenshot", true);
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, getBinaryPath(PHANTOMJS, operatingSystem, systemArchitecture));
            return capabilities;
        }

        public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            return new PhantomJSDriver(capabilities);
        }
    };

    public static final DriverType defaultDriverType = FIREFOX;
    public static final boolean useRemoteWebDriver = Boolean.valueOf(System.getProperty("remoteDriver"));
    private static final OperatingSystem operatingSystem = getOperatingSystem();
    private static final SystemArchitecture systemArchitecture = getSystemArchitecture();

    public String getWebDriverSystemPropertyKey() {
        return null;
    }

    public WebDriver instantiateWebDriver() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = getDesiredCapabilities();

        if (useRemoteWebDriver) {
            URL seleniumGridURL = new URL(System.getProperty("gridURL"));
            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
            String desiredPlatform = System.getProperty("desiredPlatform");

            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            return new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        }

        return getWebDriverObject(desiredCapabilities);
    }

    public static DriverType determineEffectiveDriverType(String browser) {
        DriverType driverType = defaultDriverType;
        try {
            driverType = valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }

        return driverType;
    }

    public WebDriver configureDriverBinaryAndInstantiateWebDriver() {
        SeleneseTestCase.logger.info("Current Operating System: " + operatingSystem.getOperatingSystemType());
        SeleneseTestCase.logger.info("Current Architecture: " + systemArchitecture.getSystemArchitectureType());
        SeleneseTestCase.logger.info("Current Browser Selection: " + this);

        configureBinary(this, operatingSystem, systemArchitecture);

        try {
            return instantiateWebDriver();
        } catch (MalformedURLException urlIsInvalid) {
            urlIsInvalid.printStackTrace();
            return null;
        }
    }
}
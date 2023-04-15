package com.qa.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class ReadOTPMessage {
    public static void main(String[] args) throws MalformedURLException {
        // Set up Appium capabilities
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.PLATFORM_NAME, "iOS");
        caps.setCapability(CapabilityType.VERSION, "14.5");
        caps.setCapability("deviceName", "iPhone");
        caps.setCapability("udid", "your_iPhone_UDID_here");
        caps.setCapability("app", "path_to_your_app_here");

        // Launch Appium driver
        IOSDriver<IOSElement> driver = new IOSDriver<IOSElement>(new URL("http://localhost:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // Switch to messages app
        driver.executeScript("mobile: launchApp", "{\"bundleId\":\"com.apple.MobileSMS\"}");

        // Wait for OTP message to arrive
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeStaticText[contains(@name,'Your OTP code is')]")));

        // Get OTP message text
        String otpMessage = driver.findElement(By.xpath("//XCUIElementTypeStaticText[contains(@name,'Your OTP code is')]")).getText();
        String otpCode = otpMessage.replaceAll("\\D+", ""); // Extract OTP code from message text

        System.out.println("OTP code is: " + otpCode);

        // Quit driver
        driver.quit();
    }
}

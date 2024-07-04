package com.library.helpers;

import com.library.Log;
import com.library.Store;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;

public class ScreenshotHelper
{
    public static void take(String fileLabel)
    {
        take(fileLabel, Store.getSettings().getFullHeightScreenShots());
    }

    public static void take(String fileLabel, boolean fullHeight)
    {
        String fileName = fileName();
        try {
            File file = new File(fileName);
            if (fullHeight) {
                Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(Store.getDriver());
                ImageIO.write(screenshot.getImage(), "PNG", file);
            } else {
                FileUtils.copyFile(Store.getDriver().getScreenshotAs(OutputType.FILE), file);
            }
            Log.file(fileLabel, file);
        } catch (Exception e) {
            Log.exception(e);
        }
    }

    private static String fileName()
    {
        return "screenshots/" + Store.getMethodName() + "-" + DateHelper.dtInsert() + ".png";
    }
}

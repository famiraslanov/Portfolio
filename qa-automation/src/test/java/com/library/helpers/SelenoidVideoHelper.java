package com.library.helpers;

import com.library.Log;
import com.library.Store;
import com.library.enums.DriverType;
import com.library.listeners.QASuiteListener;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;

public class SelenoidVideoHelper
{
    public static void uploadVideo(boolean logVideo)
    {
        if (logVideo &&  Store.getSettings().getDriverType() == DriverType.selenoidChrome) {
            String videoId = Store.getSessionId();

            if (StringUtils.isBlank(videoId)) {
                Log.debug("Cannot save video with empty videoId");
                return;
            }

            File file = fetchVideo(videoId);

            if (file != null) {
                Log.file("video", file);
                removeLocalVideo(videoId);
            } else {
                Log.debug("Unable to create the file from FetchVideo: " + videoId);
            }
        }
    }

    public static void removeLocalVideo(String videoId)
    {
        String deleteVideoUrl = Store.getSettings().getSelenoidHost() + "/video/" + videoId + ".mp4";
        try {
            ProcessBuilder pb = new ProcessBuilder("curl", "-X", "DELETE", deleteVideoUrl);
            pb.start();
            Log.debug("curl -X DELETE " + deleteVideoUrl);
            Log.debug("Local video removed: " + videoId + ".mp4");
        } catch (Exception e) {
            Log.exception(e);
        }
    }

    private static String getLocalFileName(String videoId)
    {
        return videoId + ".mp4";
    }

    private static URL dockerVideoUrl(String videoId)
    {
        try {
            return new URL("file://" + QASuiteListener.settings.getVideoUrl() + getLocalFileName(videoId));
        } catch (Exception e) {
            Log.exception(e);
            return null;
        }
    }

    private static File fetchVideo(String videoId)
    {
        File temp = new File(getLocalFileName(videoId));
        URL videoUrl = dockerVideoUrl(videoId);

        int safety = 0;
        boolean found = false;
        while (!found && safety < 600) { // About a minute (600 / 100ms sleep)
            try {
                FileUtils.copyURLToFile(videoUrl, temp);
                found = true;
            } catch (IOException e) {
                // Ok to do nothing
            }

            Function.sleep(Duration.ofMillis(100), "Waiting for video to save: " + videoUrl);
            safety++;
        }

        if (found) {
            Log.debug("Video found in seconds: " + (safety/10));
            return temp;
        } else {
            Log.debug("LocalVideo not found: " + videoUrl);
            return null;
        }
    }
}

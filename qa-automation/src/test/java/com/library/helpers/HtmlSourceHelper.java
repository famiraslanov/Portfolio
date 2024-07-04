package com.library.helpers;

import com.library.Log;
import com.library.Store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class HtmlSourceHelper
{
    public static void save(String fileLabel)
    {
        String fileName = "sourcesnapshots/" + Store.getMethodName() + "-" + DateHelper.dtInsert() + ".html";
        try {
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(getPageSource());
            out.close();
        } catch (Exception e) {
            Log.exception(e);
        }

        Log.file(fileLabel, new File(fileName));
    }

    public static String getPageSource()
    {
        return Store.getDriver().getPageSource();
    }
}

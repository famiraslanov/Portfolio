package com.library.helpers;

import com.library.Log;
import com.library.QADriver;
import com.library.Store;
import com.library.Verify;
import com.library.listeners.QASuiteListener;
import com.tests.enums.platform.EntityCard;
import com.tests.enums.platform.ExportDateFormat;
import com.tests.enums.platform.ExportFileFormat;
import com.tests.enums.platform.ExportTemplates;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class FileHelper
{
    private static File toFile(String fileName, String extension)
    {
        return new File(Store.getSettings().getLocalDownloadFolder() + "/" + fileName + "." + extension);
    }

    public static boolean isFileDownloaded(String fileName, String extension, Duration timeOut)
    {
        return getDownloadedFile(toFile(fileName, extension), timeOut) != null;
    }

    public static File getDownloadedFile(File fileToLookFor, Duration timeOut)
    {
        File fileDownloaded = null;
        if (QADriver.isSelenoidBased()) {
            System.out.println("Looking for: " + fileToLookFor.getPath());
            fileDownloaded = getSelenoidFile(fileToLookFor, timeOut);
        } else {
            fileDownloaded = localFileFound(fileToLookFor, timeOut) ? fileToLookFor : null;
        }

        if (fileDownloaded != null) {
            Log.file(fileDownloaded.getName(), fileDownloaded, false);
        }

        return fileDownloaded;
    }

    private static int getCountOfRowCSV(File file)
    {
        int count = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    QADriver.isSelenoidBased() ?
                            new InputStreamReader(getSelenoidFileAsStream(file, Duration.ofSeconds(30)))
                            :
                            new BufferedReader(new FileReader(file))
            );

            count = (int) bufferedReader.lines().count() - 1;
        } catch (IOException e) {
            Log.exception(e);
        }
        return count;
    }

    private static int getCountOfRowXLSX(File file)
    {
        Duration duration = Duration.ofSeconds(30);

        int count = 0;
        try {
            Workbook wb;
            if (QADriver.isSelenoidBased()) {
                wb = WorkbookFactory.create(getSelenoidFileAsStream(file, duration));
            } else {
                Verify.isTrue(localFileFound(file, duration), "Unable to find file: " + file);
                wb = WorkbookFactory.create(file);
            }
            XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(0);
            count = sheet.getPhysicalNumberOfRows() - 1;
        } catch (IOException | InvalidFormatException | EncryptedDocumentException e) {
            Log.exception(e);
        }
        return count;

    }

    public static int getCountEntityInFile(String fileName, ExportFileFormat exportFileFormat)
    {
        File tmp = toFile(fileName, exportFileFormat.fileExtension);
        Verify.isTrue(isFileDownloaded(fileName, exportFileFormat.fileExtension, Duration.ofSeconds(60)), "Unable to find file: " + tmp);
        return (exportFileFormat == ExportFileFormat.csv) ? getCountOfRowCSV(tmp) : getCountOfRowXLSX(tmp);
    }

    public static void isNotEmptyFile(String fileName, ExportFileFormat exportFileFormat)
    {
        Verify.isTrue(getCountEntityInFile(fileName, exportFileFormat) > 0, "File is empty");
    }

    public static void createDownloadFolder()
    {
        File theDir = new File(Store.getSettings().getLocalDownloadFolder());
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }

    public static void clearFolder()
    {
        try {
            FileUtils.cleanDirectory(new File(Store.getSettings().getLocalDownloadFolder()));
        } catch (IOException e) {
            Log.exception(e, true);
        }
    }

    private static URL fileTOSelenoidUrl(File file) throws MalformedURLException
    {
        String path = QASuiteListener.settings.getSelenoidHost() +
                "/" +
                QASuiteListener.settings.getLocalDownloadFolder() +
                "/" +
                Store.getSessionId() +
                "/" +
                URLEncoder.encode(file.getName(), StandardCharsets.UTF_8).replace("+", "%20");
        return new URL(path);
    }

    private static boolean localFileFound(File file, Duration timeout)
    {
        long start = Instant.now().toEpochMilli() - 1000; // One second before this is called to allow for code
        long end = start + timeout.toMillis();
        while (Instant.now().toEpochMilli() < end) {
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }

    private static File getSelenoidFile(File file, Duration duration)
    {
        try {
            FileUtils.copyInputStreamToFile(getSelenoidFileAsStream(file, duration), file);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    private static InputStream getSelenoidFileAsStream(File file, Duration timeout)
    {
        long start = Instant.now().toEpochMilli() - 1000; // One second before this is called to allow for code
        long end = start + timeout.toMillis();

        InputStream inputStream = null;
        while (Instant.now().toEpochMilli() < end && inputStream == null) {
            try {
                URL url = fileTOSelenoidUrl(file);
                URLConnection connection = url.openConnection();
                inputStream = connection.getInputStream();
            } catch (Exception e) {
                // Ok to do nothing
            }

            if (inputStream == null) {
                Function.sleep(Duration.ofSeconds(1), "Small wait to allow file to download");
            }
        }

        Verify.isTrue(inputStream != null, "Downloaded file not found");

        return inputStream;
    }

    public static String getExportFileName(ExportDateFormat exportDateFormat, EntityCard entityCard, ExportTemplates exportTemplates)
    {
        return getExportFileName(exportDateFormat, entityCard, exportTemplates.value);
    }

    public static String getExportFileName(ExportDateFormat exportDateFormat, EntityCard entityCard, String exportTemplateName)
    {
        return String.format("%s_%s_%s", DateHelper.dtInsert(exportDateFormat.dateFormatString), StringHelper.toKebabCase(entityCard.value), StringHelper.toKebabCase(exportTemplateName));
    }

    public static void validateRow(String fileName, ExportFileFormat extension, int rowIndex, List<String> headersToValidate)
    {
        List<String> fileRows = null;
        String filePath = toFile(fileName, extension.fileExtension).getPath();

        switch (extension) {
            case csv -> fileRows = readRowFromCsv(filePath, rowIndex);
            case excel -> fileRows = readRowFromExcel(filePath, rowIndex);
            default -> Log.debug("Unsupported file format.");
        }

        if (fileRows == null) {
            Log.info("Failed to read headers from file.");
        }
        List<String> finalFileRows = fileRows;
        IntStream.range(0, headersToValidate.size())
                .forEach(i -> {
                    String header = headersToValidate.get(i);
                    String row = finalFileRows.get(i);
                    header = header.equals("NM") ? "-" : header;
                    Verify.contains(header, row, "File does not contain all data: " + header + " != " + row);
                });
    }

    private static List<String> readRowFromCsv(String filePath, int rowIndex)
    {
        List<String> rowData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line = br.readLine();
            if (line != null) {
                String[] rowArray = line.split(",");
                for (String row : rowArray) {
                    rowData.add(row.trim());
                }
            }
        } catch (IOException e) {
            Log.exception(e);
            return null;
        }
        return rowData;
    }

    private static List<String> readRowFromExcel(String filePath, int rowIndex)
    {
        List<String> rowData = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(filePath)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex);
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                try {
                    rowData.add(cell.getStringCellValue().trim());
                } catch (IllegalStateException exception) {
                    rowData.add(String.valueOf(cell.getNumericCellValue()));
                }
            }
        } catch (IOException e) {
            Log.exception(e);
            return null;
        }
        return rowData;
    }

}

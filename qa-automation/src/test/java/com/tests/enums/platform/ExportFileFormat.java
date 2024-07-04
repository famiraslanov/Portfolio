package com.tests.enums.platform;

public enum ExportFileFormat
{
    png("PNG", "png"),
    pdf("PDF", "pdf"),
    csv("CSV", "csv"),
    excel("Excel", "xlsx");

    public final String text;
    public final String fileExtension;

    ExportFileFormat(String text, String fileExtension)
    {
        this.text = text;
        this.fileExtension = fileExtension;
    }
}

package com.jmrodrigg.printing;

/**
 * Created by jrodriguezg on 3/05/16.
 */
public class PrintingConstants {

    // Bundle parameters:
    public static final String FILE_MIMETYPE = "mime-type";
    public static final String FILE_URI = "file-uri";

    // Margins:
    public static final int LEFT_MARGIN    = 0;
    public static final int TOP_MARGIN     = 1;
    public static final int RIGHT_MARGIN   = 2;
    public static final int BOTTOM_MARGIN  = 3;

    // Permissions requests:
    public static final int PERMISSION_EXT_STORAGE_READ = 1;

    // Printing settings:
    public enum PrintMode { PRINT_FIT_TO_PAGE, PRINT_CLIP_CONTENT, PASS_PDF_AS_IS }
    public enum MarginsMode { NO_MARGINS, PRINTER_MARGINS }
    public enum JobType { DOCUMENT, IMAGE }
}

package com.fatiner.platehandler.globals;

public class Format {

    public static final String FM_DATE = "dd.MM.yyyy";
    public static final String FM_HOUR = "%02d:%02d";
    public static final String FM_NUMBERED = "%s %d";
    public static final String FM_STATE = "%d/%d";
    public static final String FM_SHOPPING = "%s (%.2f x %s)";
    public static final String FM_AMOUNT = "%s: %d";
    public static final String FM_FILE = "%s.%s";
    public static final String FM_DIRECTORY = "%s/%s";
    public static final String FM_IMAGE = "%s%d.%s";
    public static final String FM_MEASURE = "%.2f x %s";
    public static final String FM_HINT = "%s (%s)";
    public static final String FM_UNIT = "%.2f %s";
    public static final String FM_SHARE = "%s %s\n---\n%s---\n[%s]:\n%s---\n[%s]:\n%s";
    public static final String FM_INFO = "[%s]: %s\n[%s]: %d\n[%s]: %s\n[%s]: %s\n[%s]: %s\n" +
            "[%s]: %s\n[%s]: %s\n[%s]: %s\n";
    public static final String FM_INGREDIENT = "(-) %s (%.2f x %s)\n";
    public static final String FM_STEP = "(%d) %s\n";

    public static final String FM_SELECT = "SELECT * FROM %s%s%s";
    public static final String FM_ORDER = " ORDER BY %s ASC";
    public static final String FM_WHERE = " WHERE %s";
    public static final String FM_STRING = " %s = '%s' AND";
    public static final String FM_OTHER = " %s = %s AND";
    public static final String FM_AND = " AND";
}

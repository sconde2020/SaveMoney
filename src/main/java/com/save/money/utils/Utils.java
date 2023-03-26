package com.save.money.utils;

public class Utils {

    public static String startTrace() {
        return getMethodName() + " starts";
    }

    public static String endTrace() {
        return getMethodName() + " ends";
    }

    public static String getMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[3].getMethodName();
    }
}

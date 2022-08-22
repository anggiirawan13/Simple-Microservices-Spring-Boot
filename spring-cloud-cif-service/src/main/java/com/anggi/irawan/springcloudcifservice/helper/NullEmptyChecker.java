package com.anggi.irawan.springcloudcifservice.helper;

import java.util.AbstractMap;
import java.util.Collection;

public class NullEmptyChecker {

    private static boolean doCheckNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            if (obj instanceof Collection) {
                return ((Collection) obj).isEmpty() || ((Collection) obj).size() < 1;
            } else if (obj instanceof AbstractMap) {
                return ((AbstractMap) obj).isEmpty() || ((AbstractMap) obj).size() < 1;
            } else return obj.toString().trim().equals("");
        }
    }

    public static boolean isNullOrEmpty(Object obj) {
        return !doCheckNullOrEmpty(obj);
    }
}

package io.hackathon.santaclaus.util;

/**
 * Created by trinhnt on 2016/12/10.
 */

public class Utils {

    /**
     * Get Integer from String
     *
     * @param value
     * @return
     */
    public static Integer getIntegerValue(String value) {
        return null != value ? Integer.valueOf(value) : null;
    }

    /**
     * Get String from Integer
     *
     * @param value
     * @return
     */
    public static String getStringValue(Integer value) {
        return null != value ? String.valueOf(value) : null;
    }
}

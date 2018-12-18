package com.girl.Common.utils;


import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class UUIDUtils {
    private static String node = "a";
    public static String id_file = "tk_monitor_file";
    public static String id_log = "tk_device_mac_monitor_log";
    private static Map<String, AtomicInteger> map = new HashMap();
    static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public UUIDUtils() {
    }

    public static String getId() {
        Random random = new Random();
        char[] cs = new char[12];

        for(int i = 0; i < cs.length; ++i) {
            cs[i] = digits[random.nextInt(digits.length)];
        }

        return new String(cs);
    }

    public static String getUId() {
        String id = Base62.encode(System.currentTimeMillis()) + RandomStringUtils.randomNumeric(3);
        return id;
    }

    public static String getToken() {
        String token = Base62.encode(System.currentTimeMillis()) + getId();
        return token;
    }

    private static AtomicInteger getincr(String key) {
        AtomicInteger temp = (AtomicInteger)map.get(key);
        if (temp == null) {
            String var2 = node;
            synchronized(node) {
                temp = (AtomicInteger)map.get(key);
                if (temp == null) {
                    temp = new AtomicInteger(0);
                    map.put(key, temp);
                }
            }
        }

        return temp;
    }

    public static String getSequence() {
        return getSequence("default", System.currentTimeMillis());
    }

    public static String getSequence(String key) {
        return getSequence(key, System.currentTimeMillis());
    }

    public static String getSequence(long time) {
        return getSequence("default", time);
    }

    public static String getSequence(String key, long time) {
        StringBuilder stringBuilder = new StringBuilder();
        String sequence = Long.toUnsignedString(time / 1000L, 32);
        stringBuilder.append(node).append(sequence);
        int i = getincr(key).incrementAndGet() & 32767;
        if (i < 32) {
            stringBuilder.append("00");
        } else if (i < 1024) {
            stringBuilder.append("0");
        }

        stringBuilder.append(Integer.toUnsignedString(i, 32));
        return stringBuilder.toString();
    }
}

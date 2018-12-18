package com.girl.Common.utils;

public class Base62 {
    private static final String baseDigits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length();
    private static final char[] digitsChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int FAST_SIZE = 122;
    private static final int[] digitsIndex = new int[123];

    public Base62() {
    }

    public static long decode(String s) {
        long result = 0L;
        long multiplier = 1L;

        for(int pos = s.length() - 1; pos >= 0; --pos) {
            int index = getIndex(s, pos);
            result += (long)index * multiplier;
            multiplier *= (long)BASE;
        }

        return result;
    }

    public static String encode(long number) {
        if (number < 0L) {
            throw new IllegalArgumentException("Number(Base62) must be positive: " + number);
        } else if (number == 0L) {
            return "0";
        } else {
            StringBuilder buf;
            for(buf = new StringBuilder(); number != 0L; number /= (long)BASE) {
                buf.append(digitsChar[(int)(number % (long)BASE)]);
            }

            return buf.reverse().toString();
        }
    }

    private static int getIndex(String s, int pos) {
        char c = s.charAt(pos);
        if (c > 'z') {
            throw new IllegalArgumentException("Unknow character for Base62: " + s);
        } else {
            int index = digitsIndex[c];
            if (index == -1) {
                throw new IllegalArgumentException("Unknow character for Base62: " + s);
            } else {
                return index;
            }
        }
    }

    static {
        int i;
        for (i = 0; i < 122; ++i) {
            digitsIndex[i] = -1;
        }

        for (i = 0; i < BASE; digitsIndex[digitsChar[i]] = i++) {
            ;
        }
    }
}

package com.b2camp.simple_core_banking.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringUtils {
    public static boolean hasValue(final Object o) {
        return o != null && hasValue(o.toString(), false);
    }

    public static boolean hasValue(final String s) {
        return hasValue(s, false);
    }

    public static boolean hasValue(final String s, final boolean b) {
        if (b) {
            return s != null && !s.trim().equals("");
        }
        return s != null && !s.equals("");
    }

    public static String convertNullString(final String s) {
        return (s == null) ? "" : s;
    }

    public static String upperCaseFirstCharacter(final String s) {
        if (hasValue(s)) {
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return s;
    }

    public static String lowerCaseFirstCharacter(final String s) {
        if (hasValue(s)) {
            return s.substring(0, 1).toLowerCase() + s.substring(1);
        }
        return s;
    }

    public static String prepad(final String str, final char c, final int n) {
        if (n < str.length()) {
            return str;
        }
        final int n2 = n - str.length();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n2; ++i) {
            sb.append(c);
        }
        sb.append(str);
        return sb.toString();
    }

    public static String postpad(final String str, final char c, final int n) {
        if (n < str.length()) {
            return str;
        }
        final int n2 = n - str.length();
        final StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i = 0; i < n2; ++i) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static List<String> chop(final String s) {
        List<String> list = null;
        if (hasValue(s)) {
            list = new ArrayList<String>();
            int n;
            for (n = 0; s.length() - n > 2000; n += 2000) {
                list.add(s.substring(n, n + 2000));
            }
            list.add(s.substring(n));
        }
        return list;
    }

    public static List<String> chop1024(final String s) {
        List<String> list = null;
        if (hasValue(s)) {
            list = new ArrayList<String>();
            int n;
            for (n = 0; s.length() - n > 1024; n += 1024) {
                list.add(s.substring(n, n + 1024));
            }
            list.add(s.substring(n));
        }
        return list;
    }

    public static List<byte[]> chop(final byte[] array) {
        List<byte[]> list = null;
        if (array != null && array.length > 0) {
            list = new ArrayList<byte[]>();
            int n;
            for (n = 0; array.length - n > 2000; n += 2000) {
                final byte[] array2 = new byte[2000];
                System.arraycopy(array, n, array2, 0, 2000);
                list.add(array2);
            }
            final byte[] array3 = new byte[array.length - n];
            System.arraycopy(array, n, array3, 0, array.length - n);
            list.add(array3);
        }
        return list;
    }

    public static String glueString(final List<String> list) {
        final StringBuilder sb = new StringBuilder();
        if (list != null) {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
            }
        }
        return sb.toString();
    }

    public static byte[] glueByte(final List<byte[]> list) {
        byte[] array = null;
        if (list != null) {
            for (final byte[] array2 : list) {
                if (array == null) {
                    array = array2;
                }
                else {
                    final byte[] array3 = array;
                    array = new byte[array.length + array2.length];
                    System.arraycopy(array3, 0, array, 0, array3.length);
                    System.arraycopy(array2, 0, array, array3.length, array2.length);
                }
            }
        }
        return array;
    }

    public static List<String> getList(String rawList, String delimitter) {
        if (hasValue(rawList)) {
            return Arrays.asList(rawList.split(delimitter));
        }
        return new ArrayList<>();
    }

    /**
     * Parse string scientific notation number format to basic number format. example from 6,28E+12 to 6282320000000
     * @param rawNumber string raw number. example : 6,28E+12
     * @return basic number. example : 6282320000000
     */
    public static String removeScientificNotation(String rawNumber) {
        if (StringUtils.hasValue(rawNumber)) {
            rawNumber = rawNumber.replace("E+", ";")
                    .replace(",", ".");
            String[] number = rawNumber.split(";");
            if (number.length > 1) {
                BigDecimal raw = new BigDecimal(number[0]);
                BigDecimal comma = BigDecimal.valueOf(10).pow(Integer.parseInt(number[1]));
                return raw.multiply(comma).toBigInteger().toString();
            } else {
                return rawNumber;
            }
        }
        return rawNumber;
    }

    /**
     * format Biginteger to readable file size. ex : 500 KB
     */
    public static String getFileFormatName(BigInteger size, String[] units) {
        if (null == size || BigInteger.ZERO.equals(size))
            return "0 " + units[0];
        int digitGroups = (int) (Math.log10(size.doubleValue()) / Math.log10(1024));
        if (digitGroups >= units.length)
            digitGroups = units.length - 1;
        BigDecimal sizeB = new BigDecimal(size);
        BigDecimal result = sizeB.divide(BigDecimal.valueOf(1024).pow(digitGroups));
        if (sizeB.longValue() % 1024l == 0l) {
            return result + " " + units[digitGroups];
        }
        return new DecimalFormat("##.00").format(result) + " " + units[digitGroups];
    }

    /**
     * format BigDecimal to currency format. ex :Rp5.000
     */
    public static String formatIdnCurrency(BigDecimal amount) {
        final String currency = "Rp";
        if (null == amount)
            return currency + "0";
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        return new DecimalFormat(currency+ "#,###", symbols).format(amount);
    }

    public static String likeQueryValue(String raw) {
        return '%' + raw.toLowerCase() + '%';
    }
}

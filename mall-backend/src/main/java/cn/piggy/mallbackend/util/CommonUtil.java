package cn.piggy.mallbackend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 普通工具类
 *
 * @author IMNOTHD
 * @date 2020/5/23 1:33
 */
public class CommonUtil {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");

    public static boolean isNumberOnly(String str) {
        Matcher matcher = NUMBER_PATTERN.matcher(str);
        return matcher.matches();
    }

    public static boolean isStringHasAt(String str) {
        return str.contains("@");
    }
}

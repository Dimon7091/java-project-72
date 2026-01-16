package hexlet.code.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator {
    private static final String URL_PATTERN = "^https?:\\/\\/(?:[^\\s/$.?#:]\\.?)+(?::\\d{1,5})?[^\\s]*$"
            ;

    public static Boolean validateUrl(String url) {
        Pattern pattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}

package com.fftime.ffmob.util;

import java.util.regex.Matcher;

public final class WebViewUtils {

    public static boolean isAcceptedScheme(String url){
        String lowerCaseUrl = url.toLowerCase();
        Matcher acceptedUrlSchemeMatcher = Constants.ACCEPTED_URI_SCHEME.matcher(lowerCaseUrl);
        if (acceptedUrlSchemeMatcher.matches()) {
            return true;
        }
        return false;
    }
}

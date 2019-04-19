package com.hichemromdhane.youtubechannel.Utils;

import android.text.Html;
import android.text.Spanned;

public class MyUtils {
    public static Spanned textToHtml(String text)
    {
        return  Html.fromHtml(text);
    }

}

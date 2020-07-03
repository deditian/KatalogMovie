package com.example.root.sub_katalog_dedi.util;

import java.util.Locale;
public class Language {
    public static String getCountry() {
        String country = Locale.getDefault().getCountry().toLowerCase();
        if (country.equals("id")){
            country = "id";
        }else {
            country = "en";
        }
        return country;
    }
}

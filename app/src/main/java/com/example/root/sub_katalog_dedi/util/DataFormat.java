package com.example.root.sub_katalog_dedi.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.constraint.Constraints.TAG;

public class DataFormat {

    public static   String  input (String input) {
        if (input.equals("") || input.isEmpty() || input == null) {
            Log.i(TAG, "input: kosong " + input);
            input = "kosong";
        }
        Log.i(TAG, "input: isi "+input);
        DateFormat inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = inputFormatter1.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat outputFormatter1 = new SimpleDateFormat("EEE, MMM d, yyyy");
        input = outputFormatter1.format(date1);


        return input;
    }
}

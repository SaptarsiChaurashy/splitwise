package com.ril.gamification.service.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {

    private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

        @Override
        public DateFormat get() {
            return super.get();
        }

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MM-yyyy");
        }

        @Override
        public void remove() {
            super.remove();
        }

        @Override
        public void set(DateFormat value) {
            super.set(value);
        }

    };

    public static Date convertStringToDate(String dateString) throws ParseException {
        return df.get().parse(dateString);
    }
}

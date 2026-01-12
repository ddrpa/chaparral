package cc.ddrpa.chaparral.desensitizer.impl;

import cc.ddrpa.chaparral.desensitizer.IDesensitizer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BirthdayMaskingDesensitizer implements IDesensitizer<LocalDate> {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("****-MM-dd");

    public String desensitize(LocalDate birthday) {
        return dateTimeFormatter.format(birthday);
    }
}
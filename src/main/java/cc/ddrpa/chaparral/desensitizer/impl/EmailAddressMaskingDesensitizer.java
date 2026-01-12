package cc.ddrpa.chaparral.desensitizer.impl;

import cc.ddrpa.chaparral.desensitizer.IDesensitizer;

import java.util.Objects;

import static cc.ddrpa.chaparral.Constant.EMPTY;

public class EmailAddressMaskingDesensitizer implements IDesensitizer<String> {
    public String desensitize(String s) {
        if (Objects.isNull(s) || s.trim().isEmpty()) {
            return EMPTY;
        }
        int posOfSymbol = s.indexOf('@');
        if (posOfSymbol < 2) {
            return EMPTY;
        }
        return s.charAt(0) + "***" + s.charAt(posOfSymbol - 1) + s.substring(posOfSymbol);
    }
}
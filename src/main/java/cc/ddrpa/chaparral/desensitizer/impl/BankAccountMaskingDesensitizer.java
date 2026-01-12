package cc.ddrpa.chaparral.desensitizer.impl;

import cc.ddrpa.chaparral.desensitizer.IDesensitizer;

import java.util.Objects;

import static cc.ddrpa.chaparral.Constant.DEFAULT_MASK;
import static cc.ddrpa.chaparral.Constant.EMPTY;

public class BankAccountMaskingDesensitizer implements IDesensitizer<String> {
    public String desensitize(String s) {
        if (Objects.isNull(s) || s.trim().isEmpty()) {
            return EMPTY;
        } else if (s.length() < 6) {
            return DEFAULT_MASK;
        }
        return s.substring(0, 6) + "******" + s.substring(s.length() - 4);
    }
}
package cc.ddrpa.chaparral.desensitizer;

import cc.ddrpa.chaparral.desensitizer.impl.*;
import cc.ddrpa.chaparral.enums.DesensitizeStrategy;

import java.util.HashMap;
import java.util.Map;

public class DesensitizerFactory {
    private static final Map<Class<?>, IDesensitizer<?>> classMap = new HashMap<>();
    private static final Map<DesensitizeStrategy, IDesensitizer<?>> strategyMap = new HashMap<>();

    private DesensitizerFactory() {
    }

    public static IDesensitizer<?> getDesensitizer(DesensitizeStrategy strategy, Class<?> clazz) {
        if (strategy.equals(DesensitizeStrategy.CUSTOM)) {
            return getDesensitizer(clazz);
        }
        return strategyMap.computeIfAbsent(strategy, strategyAsKey -> {
            switch (strategyAsKey) {
                case BANK_ACCOUNT:
                    return new BankAccountMaskingDesensitizer();
                case BIRTHDAY:
                    return new BirthdayMaskingDesensitizer();
                case CELL:
                    return new CellPhoneNumberMaskingDesensitizer();
                case EMAIL:
                    return new EmailAddressMaskingDesensitizer();
                case ID_CARD:
                    return new IDCardMaskingDesensitizer();
                case NAME:
                    return new NameMaskingDesensitizer();
                case SOCIAL_MEDIA:
                    return new SocialMediaUsernameMaskingDesensitizer();
                default:
                    return new NullingOutDesensitizer();
            }
        });
    }

    public static IDesensitizer<?> getDesensitizer(Class<?> clazz) {
        if (clazz.isInterface()) {
            throw new UnsupportedOperationException("desensitizer is expected as an implementation");
        }
        return classMap.computeIfAbsent(clazz, classAsKey -> {
            try {
                return (IDesensitizer<?>) clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
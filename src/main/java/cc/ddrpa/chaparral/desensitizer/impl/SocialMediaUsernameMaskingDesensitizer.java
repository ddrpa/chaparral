package cc.ddrpa.chaparral.desensitizer.impl;

import cc.ddrpa.chaparral.desensitizer.IDesensitizer;

import java.util.Objects;

import static cc.ddrpa.chaparral.Constant.EMPTY;

/**
 * 社交媒体用户名脱敏器，支持 emoji
 * <ul>
 *   <li>长度 ≤ 2：全部显示为 **</li>
 *   <li>长度 = 3：显示为 首*尾（例如"一个人" -> "一*人"）</li>
 *   <li>长度 ≥ 4：保留前 2 个字符和最后 1 个字符，中间用固定数量的星号 *** 代替</li>
 * </ul>
 */
public class SocialMediaUsernameMaskingDesensitizer implements IDesensitizer<String> {

    @Override
    public String desensitize(String s) {
        if (Objects.isNull(s) || s.trim().isEmpty()) {
            return EMPTY;
        }

        // Use code points to correctly handle emoji and other multi-char Unicode characters
        int[] codePoints = s.codePoints().toArray();
        int length = codePoints.length;

        if (length <= 2) {
            return "**";
        } else if (length == 3) {
            return new String(codePoints, 0, 1) + "*" + new String(codePoints, 2, 1);
        } else {
            // 保留前2个字符和最后1个字符，中间用固定数量的星号 *** 代替
            String prefix = new String(codePoints, 0, 2);
            String suffix = new String(codePoints, length - 1, 1);
            return prefix + "***" + suffix;
        }
    }
}

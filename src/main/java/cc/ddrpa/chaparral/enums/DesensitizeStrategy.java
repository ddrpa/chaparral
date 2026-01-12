package cc.ddrpa.chaparral.enums;

public enum DesensitizeStrategy {
    /**
     * 银行帐号脱敏，保留前6后4，中间用「*」代替，总长 16，
     * 输入不满6位时返回 {@code cc.ddrpa.chaparral.Constant.DEFAULT_MASK}
     * <p>
     * 借记卡/储蓄卡通常为 19 位，特殊情况有招商银行、建设银行（16 位），交通银行（17 位）
     * 贷记卡/信用卡统一卡号为 16 位
     */
    BANK_ACCOUNT,
    /**
     * 出生日期脱敏，年份用「****」代替，使用 yyyy-MM-dd Pattern
     */
    BIRTHDAY,
    /**
     * 中国大陆地区移动电话号码脱敏，保留前3后4，中间使用「*」代替
     */
    CELL,
    /**
     * 自定义脱敏器，配合 {@code using} 参数使用
     */
    CUSTOM,
    /**
     * 电子邮件地址脱敏，保留用户名部分的第一个和最后一个字符，以及电子邮箱域名
     */
    EMAIL,
    /**
     * 中华人民共和国居民身份证号码脱敏，保留前1后1，共18位，用「*」填充
     */
    ID_CARD,
    /**
     * 姓名脱敏，适用中文姓名
     * 无论原长多少，保留第一个字和最后一个字，中间添加一个「*」
     */
    NAME,
    /**
     * Should return null no matter what was passed
     */
    NULLING_OUT,
    /**
     * 社交媒体用户名脱敏，支持 emoji
     */
    SOCIAL_MEDIA
}

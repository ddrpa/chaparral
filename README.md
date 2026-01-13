# Chaparral

Chaparral is a data desensitize helper designed to work with FasterXML/Jackson.

「欉（cóng，音同丛）」是一款配合 FasterXML/Jackson 使用的数据脱敏工具，通过为敏感数据属性添加注解实现在
JSON 序列化过程中脱敏。

## 添加依赖

本项目制品通过 sonatype.org 同步到 Maven
中央仓库，可以访问 [MvnRepository](https://mvnrepository.com/artifact/cc.ddrpa/chaparral)
获取最新的正式版本。

```xml
<dependency>
    <groupId>cc.ddrpa</groupId>
    <artifactId>chaparral</artifactId>
    <version>${chaparral.version}</version>
</dependency>
```

## 使用方法

在 Result Bean 的属性上添加 `cc.ddrpa.chaparral.annotation.Sensitive` 注解，使用
Jackson 序列化为 JSON 字符串时会自动应用脱敏规则。

```java
public class SystemUserVO {
  // 应用预设的几种脱敏规则
  @Sensitive(strategy = DesensitizeStrategy.BANK_ACCOUNT)
  private String bankAccount;
  // 可以编写自定义规则
  @Sensitive(strategy = DesensitizeStrategy.CUSTOM, using = cc.ddrpa.chaparral.howto.CustomAddressHandler.class)
  private Address address;
  // 默认使用 nulling out 规则
  @Sensitive
  private Integer fieldShouldBeNullingOut;
}
```

典型输出如下：

```json
{
  "realName": "张*三",
  "idCard": "3****************3",
  "bankAccount": "622307******9940",
  "email": "n***y@ddrpa.cc",
  "mobile": "188****9941",
  "address": {
    "province": "浙江省",
    "city": "杭州市",
    "detail": "西湖区桃源岭*号",
    "zipCode": "310001"
  },
  "fieldShouldBeNullingOut": null
}
```

### 预置脱敏规则

| 脱敏策略         | 应用场景           | 脱敏规则                                                                            | 示例                 |
|--------------|----------------|---------------------------------------------------------------------------------|--------------------|
| BANK_ACCOUNT | 银行卡号           | 保留前6后4，中间用「*」代替，总长 16                                                           | 622307******9940   |
| BIRTHDAY     | 生日（以及其他日期）     | 出生日期脱敏，使用 yyyy-MM-dd Pattern，年份用「****」代替                                        | ****-10-01         |
| CELL         | 手机号码           | 保留前3后4，中间使用「*」代替                                                                | 188****9941        |
| EMAIL        | 电子邮件地址         | 保留用户名部分的第一个和最后一个字符，以及电子邮箱域名                                                     | n***y@ddrpa.cc     |
| ID_CARD      | 中华人民共和国居民身份证号码 | 保留前1后1，共18位，用「*」填充                                                              | 3****************3 |
| NAME         | 中文姓名           | 无论原长多少，保留第一个字和最后一个字，中间添加一个「*」                                                   | 张*三                |
| SOCIAL_MEDIA | 社交媒体账号         | 少于3个字符的，替换为「\*\*」，三个字符将中间字符替换为「\*」，超过三个字符的，保留前2后1，中间使用「\*\*\*」代替，Emoji 视为单个字符 | ab****y           |
| NULLING_OUT  | 空值化            | 直接将该字段值设为 null                                                                  | null               |

### 自定义脱敏规则

自定义脱敏规则 `DesensitizeStrategy.CUSTOM`
需要实现 `cc.ddrpa.chaparral.desensitizer.IDesensitizer`
接口，然后在 `@Sensitive` 注解中使用 `using` 属性引用该类。

## 下一步计划

- 目前对各类数据的异常情况（如位数不对等）没有统一做符合直觉的处理，考虑允许用户自行注入异常处理逻辑；
- 通过其他参数允许用户小范围地改变内置脱敏规则的行为；

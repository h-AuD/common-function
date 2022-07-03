package AuD.common.function.utils;


import java.util.UUID;

/**
 * Description: 字符工具类 --- 仅服务于自身package
 *
 * @author AuD/胡钊
 * @ClassName StringUtils
 * @date 2021/7/12 15:56
 * @Version 1.0
 */
class StringUtils {

    private StringUtils(){}

    /**
     *
     */
    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    /**
     * <p>
     * UUID创建随机字符的2个function说明: <br>
     * ps:UUID最终生成的string有如下特征: <br>
     * - 是由5个subStr组成,并且直接用"-"连接.eg:20be5155-49d1-40d2-9cf8-0d2116007dc1. <br>
     * - 是个"8+4+4+4+12"(32)个长度的随机字符. <br>
     * - 每个subStr内容范围[0-9] & [a-f]. <br>
     * 1.{@link UUID#fromString(String) }:每次生成的str都是一样的,并且参数name需要满足 "name.split("-").length==5",满足上述特征. <br>
     * 2.{@link UUID#randomUUID() }: 每次生成的str都是不一样的,满足上述特征. <br>
     * </p>
     * this function create string does not contain "-"
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }


}

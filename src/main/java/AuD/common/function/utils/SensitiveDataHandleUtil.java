package AuD.common.function.utils;

/**
 * Description: 敏感数据处理工具类. </br>
 *
 * <p> 提供如下处理方式:</br>
 * 1.PASSWORD_CATEGORY === 密码类型处理方式,结果为:********
 * 2.PHONE_CATEGORY === 手机号类型处理方式,结果为:189********
 * 3.NAME_CATEGORY === 性名加密方式,结果为:胡**
 *
 * @author AuD/胡钊
 * @ClassName SensitiveDataHandleUtil
 * @date 2021/6/25 17:25
 * @Version 1.0
 */
public final class SensitiveDataHandleUtil {

    /** 默认全部加密(即密码模式) */
    private static final String DEFAULT_ALL_ENCRYPTION = "********";

    /**
     * 敏感数据处理模式:
     * 1.PASSWORD -- 形如 ********
     * 2.PHONE -- 形如 199********
     * 3.NAME -- 形如 胡**
     */
    public static final int PASSWORD_CATEGORY = 1;

    public static final int PHONE_CATEGORY = 2;

    public static final int NAME_CATEGORY = 3;


    private SensitiveDataHandleUtil(){}

    public String handleSensitiveData(String targetStr,int model){
        AssertUtils.hasText(targetStr,"content is not text");
        // === 去除头尾空格
        targetStr = targetStr.trim();
        String res = "";
        switch (model){
            case PASSWORD_CATEGORY:
                res = DEFAULT_ALL_ENCRYPTION;
                break;
            case PHONE_CATEGORY:
                AssertUtils.isTrue(targetStr.length()>3,"encrypt content length is more than 3");
                res = targetStr.substring(0,3)+DEFAULT_ALL_ENCRYPTION;
                break;
            case NAME_CATEGORY:
                res = targetStr.substring(0,1)+"**";
                break;
            default:
                throw new IllegalArgumentException("unSupport way of SensitiveDataHandleUtil");
        }
        return res;
    }



}

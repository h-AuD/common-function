package AuD.common.function;


import AuD.common.function.crypto.EncryptUtils;
import org.junit.Test;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName Encryption_test
 * @date 2021/9/22 18:11
 * @Version 1.0
 */
public class Encryption_test {


    /**
     * 文件加密测试。MD5,SHA256... === 一般用于密码加密或者数据签名
     */
    @Test
    public void testEncrypt() {
        System.out.println(EncryptUtils.md5("286393118"));

    }

}

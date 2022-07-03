package com.AuD.common.function.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Description: 加密 & 签名工具,提供如下功能: <br>
 * -- 文本摘要加密(不可逆):MD5 & sha_type.   <br>
 * -- 数据加密 & 解密:AES.    <br>
 * -- 数据签名/验签:RSA.  <br>
 *
 * <p> oracle文档:http://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html </p>
 * <p> md5的长度为32个16进制字符,长度128位(1个16进制占4个bit位); sha256长度256位; sha512长度512位 </p>
 * <p> 推荐一篇博客:https://www.cnblogs.com/sharpest/p/10885608.html </p>
 *
 * note:    <br>
 * 1.某些实现类(eg.apache的 "DigestUtils" )中有的加密function的参数是IO类型,这就表明数据源来自IO,eg.文件,但是this仅仅只是用于文本加密.  <br>
 * 2.加密算法种类繁多,了解常用的即可.  <br>
 *
 * <p> 由于MD5、SHA加密是不可逆,通常用于数字签名 & 不需要明确消息内容(eg.password验证) </p>
 * <br>
 * <p>
 * 　　加密、签名区别:加密和签名都是为了安全性考虑,但略有不同.常有人问加密和签名是用私钥还是公钥?其实都是对加密和签名的作用有所混淆.
 * 简单的说,加密是为了防止信息被泄露,而签名是为了防止信息被篡改.这里举2个例子说明.   <br>
 * <br>
 * 1.第一个场景:战场上,B要给A传递一条消息,内容为某一指令.  <br>
 * 加密过程如下：<br>
 * - A生成一对密钥(公钥和私钥),私钥不公开,A自己保留.公钥为公开的,任何人可以获取. <br>
 * - A传递自己的公钥给B,B用A的公钥对消息进行加密.  <br>
 * - A接收到B加密的消息,利用A自己的私钥对消息进行解密.    <br>
 * 在这个过程中,只有2次传递过程,第一次是A传递公钥给B,第二次是B传递加密消息给A,
 * 即使都被敌方截获,也没有危险性,因为只有A的私钥才能对消息进行解密,防止了消息内容的泄露.    <br>
 *
 * 2.第二个场景:A收到B发的消息后,需要进行回复"收到".    <br>
 * 签名的过程如下: <br>
 * - A生成一对密钥(公钥和私钥),私钥不公开,A自己保留.公钥为公开的,任何人可以获取. <br>
 * - A用自己的私钥对消息加签,形成签名,并将加签的消息和消息本身一起传递给B.  <br>
 * - B收到消息后,在获取A的公钥进行验签,如果验签出来的内容与消息本身一致,证明消息是A回复的. <br>
 * 在这个过程中,只有2次传递过程,第一次是A传递加签的消息和消息本身给B,第二次是B获取A的公钥,即使都被敌方截获,也没有危险性,
 * 因为只有A的私钥才能对消息进行签名,即使知道了消息内容,也无法伪造带签名的回复给B,防止了消息内容的篡改.    <br>
 * <br>
 * 但是,综合两个场景你会发现,第一个场景虽然被截获的消息没有泄露,但是可以利用截获的公钥,将假指令进行加密,然后传递给A.
 * 第二个场景虽然截获的消息不能被篡改,但是消息的内容可以利用公钥验签来获得,并不能防止泄露.
 * 所以在实际应用中,要根据情况使用,也可以同时使用加密和签名,
 * 比如A和B都有一套自己的公钥和私钥,当A要给B发送消息时,先用B的公钥对消息加密,再对加密的消息使用A的私钥加签名,达到既不泄露也不被篡改,更能保证消息的安全性.    <br>
 * <br>
 * 总结:公钥加密、私钥解密、私钥签名、公钥验签.  <br>
 * </p>
 *
 * @author AuD/胡钊
 * @ClassName EncryptUtils
 * @date 2021/7/30 12:29
 * @Version 1.0
 */
public final class EncryptUtils {

    /** 通用的摘要加密function === Delegate OBJ,委托对象(代理对象) */
    private final static BasicEncrypt.AbstractMessageDigestEncrypt COMMON_DIGEST = new BasicEncrypt.AbstractMessageDigestEncrypt() {};

    /** 提供SHA类型加密function(SHA1、SHA256、SHA512) */
    public final static ShaAlgorithm SHA = new ShaAlgorithm();

    /* ================== AES类型的加密方式: ====================== */
    public final static AESAlgorithm AES_CBC_P7 = new AESAlgorithm("AES/CBC/PKCS7Padding");

    public final static AESAlgorithm AES_CBC_P5 = new AESAlgorithm("AES/CBC/PKCS5Padding");

    public final static AESAlgorithm AES = new AESAlgorithm("AES");

    /* ================== RSA类型的签名方式: ====================== */
    public final static RSASignature MD5_RSA = new RSASignature("MD5withRSA");

    public final static RSASignature SHA256_RSA = new RSASignature("SHA256withRSA");

    /**
     * MD5加密,返回的16进制字符串(结果是长度为32的字符串)
     * @param rawData 待加密数据
     * @return
     */
    public static String md5(String rawData) {
        return digestEncryptByDelegate(rawData,"MD5");
    }

    /**
     * 摘要/文本加密,采用委托模型(即真正的执行者是{@link }).    <br>
     *
     * Note:    <br>
     * provide this function aim is that user can use any algorithm they want.  <br>
     * -- 提供这个方法的目的,是给caller一个可选的机会:caller 自己选择他们想要的加密算法.   <br>
     *
     * @param rawData   原始文件数据
     * @param algorithm 加密算法
     * @return
     */
    public static String digestEncryptByDelegate(String rawData, String algorithm){
        return COMMON_DIGEST.doMessageDigestEncrypt(rawData,algorithm);
    }

    /**
     * SHA文本/摘要加密算法,包含SHA1、SHA256、SHA512。   <br>
     * this object 设计借鉴{@link java.util.Base64}
     */
    public static class ShaAlgorithm extends BasicEncrypt.AbstractMessageDigestEncrypt {

        private final static String ALGORITHM_SHA = "SHA1";

        private final static String ALGORITHM_SHA_256 = "SHA_256";

        private final static String ALGORITHM_SHA_512 = "SHA_512";

        // === 私有化构造器
        private ShaAlgorithm(){

        }

        /**
         * sha1加密,常用于数据签名.  <br>
         * -- eg:某个http request包含m个参数,根据某种规则将这些参数拼接一个字符串,做SHA1加密,得到签名值,
         * 与目标签名做对比,判断是否一致,进而判断请求来源是否合法.
         * @param rawData
         * @return
         */
        public String sha1(String rawData) {
            return doMessageDigestEncrypt(rawData, ALGORITHM_SHA);
        }

        /**
         * SHA256加密,返回的16进制字符串
         * @param rawData 待加密数据
         * @return
         */
        public String sha256(String rawData) {
            return doMessageDigestEncrypt(rawData, ALGORITHM_SHA_256);
        }

        /**
         * SHA512加密,返回的16进制字符串
         * @param rawData 待加密数据
         * @return
         */
        public String sha512(String rawData) {
            return doMessageDigestEncrypt(rawData, ALGORITHM_SHA_512);
        }

    }

    /**
     * AES加密算法算法.   <br>
     */
    public static class AESAlgorithm{

        // === 加密算法
        private final static String AES_CONSTANT = "AES";

        // === cipher算法
        private String cipherAlgorithm;

        // === 标记是否需要做base64处理
        private boolean base64 = true;

        // ==== 空字节数组
        private final static byte[] EMPTY_IV = {};

        // === 私有化
        private AESAlgorithm(String cipherAlgorithm, boolean base64){
            this.cipherAlgorithm = cipherAlgorithm;
            this.base64 = base64;
        }

        // === 私有化
        private AESAlgorithm(String cipherAlgorithm){
            this.cipherAlgorithm = cipherAlgorithm;
        }

        /**
         * AES加密逻辑.
         * @param rowData   原始数据
         * @param secreteKey    密钥(字节数组),不建议使用含有中文密钥(因为编码存在乱码的问题场景).
         * @param iv    加密使用的偏移量数据,可以为空({@link AESAlgorithm#EMPTY_IV})
         * @return
         */
        public String encrypt(String rowData,byte[] secreteKey,byte[] iv) {
            // === 结果
            String result = "";
            try {
                // 1.构建SecretKeySpec
                SecretKeySpec keySpec = buildSecretKey(secreteKey);
                // 2.根据算法获取Cipher
                Cipher cipher = Cipher.getInstance(cipherAlgorithm);
                // 3.初始化Cipher === 根据是否有偏移量
                if(iv.length==0){
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                }else {
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec,buildIvParam(iv));
                }
                // 4.加密 ==== 更具是否需要base64编码
                //byte[] bytes = cipher.doFinal(rowData.getBytes());
                if(base64){
                    result = Base64.getEncoder().encodeToString(cipher.doFinal(rowData.getBytes()));
                }else {
                    result = new String(cipher.doFinal(rowData.getBytes()));
                }
            } catch (Exception e) {
                throw new CryptoException("encrypt data("+rowData+") occur error,error info:"+e.getMessage(),e);
            }
            return result;
        }

        /**
         * 解密
         * @param encryptedData
         * @param secreteKey
         * @param iv
         * @return
         */
        public String decrypt(byte[] encryptedData,byte[] secreteKey,byte[] iv) {
            String result = "";
            try {
                // 1.构建SecretKeySpec
                SecretKeySpec keySpec = buildSecretKey(secreteKey);
                // 2.根据算法获取Cipher
                Cipher cipher = Cipher.getInstance(cipherAlgorithm);
                // 3.初始化Cipher === 根据是否有偏移量
                if(iv.length==0){
                    cipher.init(Cipher.DECRYPT_MODE, keySpec);
                }else {
                    cipher.init(Cipher.DECRYPT_MODE, keySpec,buildIvParam(iv));
                }
                // 4.解密
                //byte[] bytes = cipher.doFinal(encryptedData);
                result = new String(cipher.doFinal(encryptedData));
            } catch (Exception e) {
                throw new CryptoException("decrypt data occur error,error info:"+e.getMessage(),e);
            }
            return result;
        }

        /**
         * 构建SecretKeySpec
         * @param secreteKey
         * @return
         */
        private SecretKeySpec buildSecretKey(byte[] secreteKey){
            SecretKeySpec keySpec = new SecretKeySpec(secreteKey, AES_CONSTANT);
            return keySpec;
        }

        /**
         * 构建偏移量参数
         * @param iv
         * @return
         * @throws Exception
         */
        private AlgorithmParameters buildIvParam(byte[] iv) throws Exception {
            AlgorithmParameters ivParams = AlgorithmParameters.getInstance(AES_CONSTANT);
            ivParams.init(new IvParameterSpec(iv));
            return ivParams;
        }


    }

    /**
     * rsa 签名
     */
    public static class RSASignature extends BasicEncrypt.AbstractEncrypt {

        private final static String RAS_CONSTANT = "RSA";

        private String signatureAlgorithm;

        // === 标记是否需要做base64处理,一般都需要做base64编码转换,考虑到实用性,设计this filed.
        private boolean base64 = true;


        private RSASignature(String signatureAlgorithm){
            this.signatureAlgorithm = signatureAlgorithm;
        }

        /**
         * 签名
         *
         * @param data 待签名数据
         * @param privateKey 私钥
         * @return 签名
         */
        public String sign(String data, String privateKey) throws Exception {
            byte[] keyBytes = getPrivateKey(privateKey,RAS_CONSTANT).getEncoded();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RAS_CONSTANT);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            Signature signature = Signature.getInstance(signatureAlgorithm);
            signature.initSign(key);
            signature.update(data.getBytes());
            if(base64){
                return Base64.getEncoder().encodeToString(signature.sign());
            }else {
                return new String(signature.sign());
            }

        }

        /**
         * 验签
         *
         * @param srcData 原始字符串
         * @param publicKey 公钥
         * @param sign 签名
         * @return 是否验签通过
         */
        public boolean verify(String srcData, String publicKey, String sign) throws Exception {
            byte[] keyBytes = getPublicKey(publicKey,RAS_CONSTANT).getEncoded();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RAS_CONSTANT);
            PublicKey key = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(signatureAlgorithm);
            signature.initVerify(key);
            signature.update(srcData.getBytes());
            if(base64){
                return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
            }else {
                return signature.verify(sign.getBytes());
            }
        }

    }


}

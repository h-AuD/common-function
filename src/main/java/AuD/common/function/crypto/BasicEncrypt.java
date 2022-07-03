package AuD.common.function.crypto;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Description:
 * <p> 加密通用类 </p>
 *
 * @author AuD/胡钊
 * @ClassName BasicEncrypt
 * @date 2021/8/4 14:53
 * @Version 1.0
 */
class BasicEncrypt {

    /**
     * 抽象 MessageDigest 加密 method
     */
    abstract static class AbstractMessageDigestEncrypt{

        private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        /**
         * 将加密的字节数组转换为字符
         */
        protected char[] encodeHex(byte[] bytes) {
            char[] chars = new char[32];
            for (int i = 0; i < chars.length; i = i + 2) {
                byte b = bytes[i / 2];
                chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
                chars[i + 1] = HEX_CHARS[b & 0xf];
            }
            return chars;
        }

        /**
         * 获取{@link MessageDigest}对象
         * @param rawData
         * @param algorithm
         * @return
         */
        public String doMessageDigestEncrypt(String rawData, String algorithm){
            byte[] digest={};
            try {
                digest = MessageDigest.getInstance(algorithm).digest(rawData.getBytes(StandardCharsets.UTF_8));
            } catch (NoSuchAlgorithmException e) {
                throw new CryptoException("",e);
            }
            return new String(encodeHex(digest));
        }
    }

    /**
     * 抽象 加密 method === eg.AES、RSA
     */
    abstract static class AbstractEncrypt{

        /**
         * 获取私钥{@link PrivateKey}
         *
         * @param privateKey 私钥字符串
         * @return
         */
        protected PrivateKey getPrivateKey(String privateKey,String algorithm) throws Exception {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            return keyFactory.generatePrivate(keySpec);
        }

        /**
         * 获取公钥{@link PublicKey}
         *
         * @param publicKey 公钥字符串
         * @return
         */
        protected PublicKey getPublicKey(String publicKey,String algorithm) throws Exception {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            return keyFactory.generatePublic(keySpec);
        }

        /**
         * 获取密钥对
         *
         * @return 密钥对
         */
        protected static KeyPair getKeyPair(String algorithm,int initSize) throws Exception {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
            generator.initialize(initSize);
            return generator.generateKeyPair();
        }


    }





}

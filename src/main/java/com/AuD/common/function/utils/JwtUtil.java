package com.AuD.common.function.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

/**
 * Description:JWT工具.用于处理生成JWT数据.
 *
 * <p>
 *     通常应用于"系统登陆".
 *     i.e.当user login success,call userLoginApi 返回一个jwt(token)数据,每次call API 在request header中带上this token,
 *     后续若需要某些数据(eg.userName、uid),可以从token中获取。---- 这些数据必须在return jwt设置好.
 * </p>
 *
 * @author AuD/胡钊
 * @ClassName A
 * @date 2021/6/28 13:34
 * @Version 1.0
 */
public final class JwtUtil {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(JwtUtil.class);

    /** 默认负载信息 */
    private final static Map<String,Object> DEFAULT_CLAIMS = new HashMap<>();

    /** 默认过期时间:one day(i.e.24H) */
    private final static long DEFAULT_EXP =24*60*60*1000;

    /** 默认JWT的主体,即它的所有人 */
    private final static String DEFAULT_SUBJECT = "ALL";

    private JwtUtil(){
        throw new AssertionError("No JwtUtil instances for you!");
    }

    /**
     *
     * @param id
     * @param secretKey
     * @return
     */
    public static String createJWT(String id, String secretKey){
        return doCreateJWT(id,DEFAULT_SUBJECT,secretKey,DEFAULT_EXP,DEFAULT_CLAIMS);
    }

    /**
     *
     * @param claims
     * @param secretKey
     * @return
     */
    public static String createJWT(Map<String, Object> claims, String secretKey){
        return doCreateJWT(null,DEFAULT_SUBJECT,secretKey,DEFAULT_EXP,claims);
    }

    /**
     *
     * @param id
     * @param claims
     * @param secretKey
     * @return
     */
    public static String createJWT(String id, Map<String, Object> claims, String secretKey){
        return doCreateJWT(id,DEFAULT_SUBJECT,secretKey,DEFAULT_EXP,claims);
    }


    /**
     * 生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     * @param id
     * @param subject
     * @param secretKey
     * @param expTime
     * @param claims
     * @return
     */
    public static String doCreateJWT(String id, String subject, String secretKey, long expTime, Map<String, Object> claims) {
        AssertUtils.hasText(secretKey,"secretKey must has text");
        AssertUtils.isTrue(expTime>0,"expiration time must more than 0");
        AssertUtils.conditionMatch(StringUtils.hasText(id) || StringUtils.hasText(subject) || !ObjectUtils.isEmpty(claims),
                "at least one(jwt_id、subject、claims) must not be null ");
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(StringUtils.hasText(id)?id:StringUtils.uuid())
                //iat: jwt的签发时间
                .setIssuedAt(new Date())
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为用户的唯一标志。
                .setSubject(subject)
                .setExpiration(new Date( System.currentTimeMillis() + expTime))
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return builder.compact();
    }


    /**
     * Token的解密
     * @param token 加密后的token
     * @param secretKey  签名秘钥，和生成的签名的秘钥一模一样
     * @return
     */
    public static Optional<Claims> parseJWT(String token, String secretKey) {
        //得到DefaultJwtParser
        try {
            Claims claims = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(secretKey)
                    //设置需要解析的jwt
                    .parseClaimsJws(token).getBody();
            return Optional.of(claims);
        }catch (Exception e){
            LOG.warn("jwt parse appear error,info is:{}",e.getMessage());
            return Optional.empty();
        }
    }


    /**
     * 校验token(用于验证Claims内容).   <br>
     * == 不推荐作为一个utils方法,因为Claims交由用户验证即可(这个是业务逻辑,不属于工具范畴内).    <br>
     *
     * @param token
     * @return
     */
    //public static Boolean verifyClaims(String token, String secretKey, Map<String, Object> claims) {
    //    if(ObjectUtils.isEmpty(claims)){
    //        claims = DEFAULT_CLAIMS;
    //    }
    //    // 定义finalClaims & res 仅仅用于lambda表达式,因为lambda表达式中引用变量为final或者effectively final
    //    Map<String, Object> finalClaims = claims;
    //    AtomicBoolean res = new AtomicBoolean(true);
    //    try {
    //        //得到DefaultJwtParser
    //        Claims resClaims = Jwts.parser()
    //                //设置签名的秘钥
    //                .setSigningKey(secretKey)
    //                //设置需要解析的jwt
    //                .parseClaimsJws(token).getBody();
    //
    //        resClaims.forEach((key, value)->{
    //            if(!value.equals(finalClaims.get(key))){
    //                res.set(false);
    //                return;
    //            }
    //        });
    //    }catch (Exception e){
    //        log.warn("verify jwt appear error,info is:{}",e.getMessage());
    //        res.set(false);
    //    }
    //    return res.get();
    //}

    /**
     * 验证token(最简单的验证方式,验证通过即可,通常用于不关心token携带的数据). <br>
     *
     * @param token
     * @param secretKey
     * @return
     */
    public static Boolean verify(String token, String secretKey) {
        boolean verifyResult = true;
        try {
            // 得到DefaultJwtParser.设置签名的秘钥.设置需要解析的jwt.获取负载内容
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch (Exception e){
            LOG.warn("verify jwt appear error,info is:{}",e.getMessage());
            verifyResult = false;
        }
        return verifyResult;
    }

}
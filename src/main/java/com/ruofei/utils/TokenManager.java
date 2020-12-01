package com.ruofei.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: srf
 * @Date: 2020/12/1 10:41
 * @description:
 */
@Slf4j
public class TokenManager {

    //私钥
    private static final String SECRET = "Axmk89Li3Aji9M";

    //过期时间1分钟
    private static final int expiresTime = 60000;

    public static String createToken(String userId){
        //获取加上过期时间后的时间
        Date nowDate = new Date();
        System.out.println(nowDate);
        Date expiresDate = new Date(System.currentTimeMillis()+expiresTime);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create().withHeader(map)	//请求头
                .withClaim("iss", "Service")	//签发方
                .withClaim("aud", "Client")		//接收方
                .withClaim("userId", null==userId?null:userId) //存储信息，用户ID
                .withIssuedAt(nowDate)		//当前时间
                .withExpiresAt(expiresDate)		//过期时间
                .sign(Algorithm.HMAC256(SECRET));		//私钥

        return token;
    }

    /**
     * 验证token合法性
     * @param token
     * @return
     */
    public static boolean verifyToken(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据token获取用户id
     * @param token
     * @return
     */
    public static String getUserIdByToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT verify = verifier.verify(token);
        Map<String, Claim> claims = verify.getClaims();
        Claim userIdClaim = claims.get("userId");
        String userId = userIdClaim.asString();
        return userId;
    }

    public static void main(String[] args) {
        String token = createToken("test");
        System.out.println(token);
        System.out.println("-----------------------------------------------");
        verifyToken(token);
    }

}

package com.cm.shirotest.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author 陈萌
 * @version 1.0
 * @date 2021/12/1 0001 23:12
 * @modelName shiro-test
 */
@Log4j2
public class PWDUtil {

    /**
     * 加密算法MD5
     *
     * @param pwd            密码
     * @param salt           盐
     * @param hashIterations 散列次数
     * @return 加密后密码
     */
    public static String getMD5Pwd(String pwd, String salt, int hashIterations) {
        String md5Pwd = new SimpleHash(Md5Hash.ALGORITHM_NAME, pwd, ByteSource.Util.bytes(salt), hashIterations).toHex();
        log.info("MD5加密：pwd：{}, salt:{}, hashIterations:{}", pwd, salt, hashIterations);
        return md5Pwd;
    }

    /**
     * 生成随机字符串
     * @param saltLength 字符串长度
     * @return 生成随机字符串
     */
    public static String getSalt(Integer saltLength) {
        if (saltLength == null || saltLength == 0) {
            saltLength = 6;
        }
        return GeneratorUtil.randomSequence(saltLength);
    }

}

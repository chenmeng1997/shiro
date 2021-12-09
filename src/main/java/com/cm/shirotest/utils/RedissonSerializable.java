package com.cm.shirotest.utils;

import com.cm.shirotest.entity.User;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Base64;
import java.util.Objects;

/**
 * @author 陈萌
 * @Date 2021/12/9 0009 1:06
 * @ProjectName shiro-test
 */
@Log4j2
public class RedissonSerializable {

    public static Object deserialize(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            bis = new ByteArrayInputStream(Base64.getDecoder().decode(str));
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static String serialize(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        String str = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            str = Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /*public static void main(String[] args) {
        User user = new User();
        user.setUsername("chenmeng");
        User user1 = (User) deserialize(serialize(user));
    }*/

}

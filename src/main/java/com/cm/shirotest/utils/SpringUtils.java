package com.cm.shirotest.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author 陈萌
 * @Date 2022/1/6 0006 22:51
 * @ProjectName shiro-test
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
        environment = applicationContext.getEnvironment();
    }

    public static ApplicationContext getApplicationContext() {
        return SpringUtils.applicationContext;
    }

    public static String getProperty(String var1) {
        return environment.getProperty(var1);
    }

    public static String getProperty(String var1, String var2) {
        return environment.getProperty(var1, var2);
    }

    public static <T> T getProperty(String var1, Class<T> var2) {
        return environment.getProperty(var1, var2);
    }

    public static <T> T getProperty(String var1, Class<T> var2, T defaultValue) {
        return environment.getProperty(var1, var2, defaultValue);
    }

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static  <T> T getBean(String var1, Class<T> var2) throws BeansException {
        return applicationContext.getBean(var1, var2);
    }

}

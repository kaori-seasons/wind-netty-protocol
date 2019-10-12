package com.hx.nettyclient.client.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ziyan
 * @email zhengmy@hxmec.com
 * @date 2019年10月08日
 */
@Slf4j
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public SpringUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    public static Object getBean(String beanName) {
        try {
            return applicationContext.getBean(Class.forName(beanName));
        } catch (ClassNotFoundException e) {
            log.error("getBean failed:", e);
            return null;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }
}

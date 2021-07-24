package com.devlogmoa.util;

import org.springframework.context.ApplicationContext;

public class CustomBeanUtil {

    public static Object getBean(String beanName) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        return applicationContext.getBean(beanName);
    }
}

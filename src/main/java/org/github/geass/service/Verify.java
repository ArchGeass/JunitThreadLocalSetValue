package org.github.geass.service;

import org.github.geass.util.LoginUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Verify {

    //maybe debug: @See org.springframework.aop.framework.DynamicAdvisedInterceptor.intercept
    @Async
    public Object verify(String key) {
        Object result = LoginUser.getAttribute(key);
        if (null != result)
            System.out.println("Attribute is: " + result.toString());
        else
            System.out.println("getAttribute fail!");
        return result;
    }
}

package org.github.geass.service;

import org.github.geass.util.LoginUser;
import org.springframework.stereotype.Service;

@Service
public class Verify {

    public Object verify(String key) {
        Object result = LoginUser.getAttribute(key);
        if (result != null) {
            System.out.println("success");
            return "success";
        } else {
            System.out.println("fail");
            return "fail";
        }
    }
}

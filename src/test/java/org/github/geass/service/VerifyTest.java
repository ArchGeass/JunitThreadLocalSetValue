package org.github.geass.service;

import org.github.geass.Application;
import org.github.geass.util.LoginUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class VerifyTest {

    @Autowired
    private Verify verify;

    @Test
    public void verify() throws ExecutionException, InterruptedException {
        String key = "setKey";
        String value = "success";
        LoginUser.setAttribute(key, value);

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Object> future = service.submit(() -> verify.verify(key));

        String result = future.get().toString();
        System.out.println("run task: " + result);
        Assert.assertEquals(value, result);
    }
}
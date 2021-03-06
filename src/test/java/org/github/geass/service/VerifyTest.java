package org.github.geass.service;

import org.github.geass.Application;
import org.github.geass.util.LoginUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

    private static String key = "setKey";
    private static String value = "setValue";

    @Autowired
    private Verify verify;

    @Before
    public void setKey() {
        LoginUser.setAttribute(key, value);
    }

    @After
    public void clean() {
        LoginUser.setAttribute(key, null);
    }

    @Test
    public void verify() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<?> future = service.submit(
//                () -> verify.verify(key)
                new SetUpLoginUserDecorator(() -> verify.verify(key))
        );

        if (null == future.get()) {
            throw new RuntimeException("future is null!");
        }
        String result = future.get().toString();
        System.out.println("run task: " + result);
        Assert.assertEquals(value, result);
    }


    public static class SetUpLoginUserDecorator implements Runnable {
        Runnable delegate;

        public SetUpLoginUserDecorator(Runnable delegate) {
            this.delegate = delegate;
        }

        @Override
        public void run() {
            try {
                LoginUser.setAttribute(key, value);
                delegate.run();
            } finally {
                // cleanup
                LoginUser.setAttribute(key, null);
            }
        }
    }
}
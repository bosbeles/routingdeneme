package com.test;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MyTestCase extends AbstractTestCase {

    int x;

    @Override
    public void given() {
        x = 3;
        sleep(3, TimeUnit.SECONDS);
        System.out.println("Given");
    }

    @Override
    public void when(Future<?> given) throws Exception {
        given.get();
        sleep(3, TimeUnit.SECONDS);
        x = x+1;
    }

    @Override
    public void then(Future<?> when) throws Exception {
        when.get();
        if(x == 4) {
            System.out.println("OK");
        }
        else {
            System.out.println("Fail");
        }
    }

    public static void main(String[] args) {
        MyTestCase test = new MyTestCase();
        test.test();
    }
}

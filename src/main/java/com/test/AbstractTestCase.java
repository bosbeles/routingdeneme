package com.test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AbstractTestCase {

    protected static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    static void sleep(long t, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void given() {

    }

    public void when(Future<?> given) throws Exception {

    }

    public void then(Future<?> when) throws Exception {

    }

    public Future<?> test() {
        Future<?> g = scheduler.submit(() -> given());
        Future<?> w = scheduler.submit(()-> {
            try {
                when(g);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return scheduler.submit(()-> {
            try {
                then(w);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}

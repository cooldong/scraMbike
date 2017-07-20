package com.young.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by honey on 2017/7/20.
 */
public class ThreadWork {

    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);

}

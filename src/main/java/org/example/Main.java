package org.example;

import org.example.algo.rateLimiter.RateLimiter;
import org.example.algo.rateLimiter.config.LeakyBucketConfig;
import org.example.algo.rateLimiter.config.RateLimiterConfig;
import org.example.algo.rateLimiter.config.TokenBucketConfig;
import org.example.algo.rateLimiter.design.factory.RateLimiterFactory;
import org.example.algo.rateLimiter.enums.RateLimiterType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main
{
   private static final int THREADS = 20;
   private static final int REQ_PER_THREAD = 5;

   public static void main(String[] args) throws InterruptedException
   {
      runGlobalTest(RateLimiterType.TOKEN_BUCKET, new TokenBucketConfig(10, 5));
      runGlobalTest(RateLimiterType.LEAKY_BUCKET, new LeakyBucketConfig(10, 5));
   }

   private static void runGlobalTest(RateLimiterType rateLimiterType, RateLimiterConfig rateLimiterConfig) throws InterruptedException {
       RateLimiter rateLimiter = RateLimiterFactory.createRateLimiter(rateLimiterType, rateLimiterConfig);
       AtomicInteger allowed = new AtomicInteger(0);
       AtomicInteger denied = new AtomicInteger(0);

       ExecutorService executor = Executors.newFixedThreadPool(THREADS);

       for(int t=0;t<THREADS;t++)
       {
           executor.submit(()->{
               for(int r=0;r<REQ_PER_THREAD;r++)
               {
                   if(rateLimiter.allowRequest())
                   {
                       allowed.incrementAndGet();
                   }
                   else
                   {
                       denied.incrementAndGet();
                   }
               }
           });
       }

       executor.shutdown();
       executor.awaitTermination(5, TimeUnit.SECONDS);
   }
}
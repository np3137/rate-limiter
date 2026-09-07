package org.example.algo.rateLimiter.design.factory;

import org.example.algo.rateLimiter.RateLimiter;
import org.example.algo.rateLimiter.config.LeakyBucketConfig;
import org.example.algo.rateLimiter.config.RateLimiterConfig;
import org.example.algo.rateLimiter.config.TokenBucketConfig;
import org.example.algo.rateLimiter.config.WindowConfig;
import org.example.algo.rateLimiter.enums.RateLimiterType;
import org.example.algo.rateLimiter.impl.*;

public class RateLimiterFactory
{
    public static RateLimiter createRateLimiter(RateLimiterType rateLimiterType, RateLimiterConfig rateLimiterConfig)
    {
       switch(rateLimiterType)
       {
           case TOKEN_BUCKET:
               if(rateLimiterConfig instanceof TokenBucketConfig tokenBucketConfig)
               {
                   return new TokenBucket(tokenBucketConfig.getCapacity(), tokenBucketConfig.getRefillRatePerSec());
               }
           case LEAKY_BUCKET:
               if(rateLimiterConfig instanceof LeakyBucketConfig leakyBucketConfig)
               {
                   return new LeakyBucket(leakyBucketConfig.getCapacity(), leakyBucketConfig.getLeakRatePerSec());
               }
           case FIXED_WINDOW:
               if(rateLimiterConfig instanceof WindowConfig windowConfig)
               {
                   return new FixedWindowCounter(windowConfig.getLimit(), windowConfig.getWindowSizeMillis());
               }
           case SLIDING_WINDOW_LOG:
               if(rateLimiterConfig instanceof WindowConfig windowConfig)
               {
                   return new SlidingWindowLog(windowConfig.getLimit(), windowConfig.getWindowSizeMillis());
               }
           case SLIDING_WINDOW_COUNTER:
//               if(rateLimiterConfig instanceof WindowConfig windowConfig)
//               {
//                   return new SlidingWindowCounter(windowConfig.getLimit(), windowConfig.getWindowSizeMillis());
//               }
                 return null;
           default:
               throw new IllegalArgumentException("Unknown Algorithm");
       }
    }
}

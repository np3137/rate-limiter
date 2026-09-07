package org.example.algo.rateLimiter;

import org.example.algo.rateLimiter.config.RateLimiterConfig;
import org.example.algo.rateLimiter.design.factory.RateLimiterFactory;
import org.example.algo.rateLimiter.enums.RateLimiterType;

import java.util.concurrent.ConcurrentHashMap;

public class RateLimitRegistry
{
   private final RateLimiterType rateLimiterType;
   private final RateLimiterConfig rateLimiterConfig;
   private final ConcurrentHashMap<String, RateLimiter>userIdToBucketMap = new ConcurrentHashMap<>();


    public RateLimitRegistry(RateLimiterType rateLimiterType, RateLimiterConfig rateLimiterConfig)
    {
        this.rateLimiterType = rateLimiterType;
        this.rateLimiterConfig = rateLimiterConfig;
    }

    public boolean allowRequest(String userId)
    {
         RateLimiter rateLimiter = userIdToBucketMap.computeIfAbsent(userId, k-> RateLimiterFactory.createRateLimiter(rateLimiterType, rateLimiterConfig));
         return rateLimiter.allowRequest();
    }
}

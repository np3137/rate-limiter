package org.example.algo.rateLimiter;

import org.example.algo.rateLimiter.impl.TokenBucket;

import java.util.concurrent.ConcurrentHashMap;

public class RateLimitRegistry
{
   private final long capacity;
   private final long refillRatePerSec;
   private final ConcurrentHashMap<String, TokenBucket>userIdToBucketMap = new ConcurrentHashMap<>();


    public RateLimitRegistry(long capacity, long refillRatePerSec)
    {
        this.capacity = capacity;
        this.refillRatePerSec = refillRatePerSec;
    }

    public boolean allowRequest(String userId)
    {
         TokenBucket tokenBucket = userIdToBucketMap.computeIfAbsent(userId, k-> new TokenBucket(capacity, refillRatePerSec));
         return tokenBucket.allowRequest();
    }
}

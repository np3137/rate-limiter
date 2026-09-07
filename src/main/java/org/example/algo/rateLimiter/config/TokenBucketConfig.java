package org.example.algo.rateLimiter.config;

public class TokenBucketConfig implements RateLimiterConfig
{
   private final long capacity;
   private final long refillRatePerSec;

   public TokenBucketConfig(long capacity, long refillRatePerSec)
   {
        this.capacity = capacity;
        this.refillRatePerSec = refillRatePerSec;
   }

   public long getCapacity()
   {
      return capacity;
   }

   public long getRefillRatePerSec()
   {
      return refillRatePerSec;
   }
}

package org.example.algo.rateLimiter.impl;

import org.example.algo.rateLimiter.RateLimiter;

public class TokenBucket implements RateLimiter
{
    private final long capacity;
    private final long refillRatePerSecond;

    private double tokens;
    private long lastRefillTime;

    private final Object lock = new Object();

    public TokenBucket(long capacity, long refillRatePerSecond)
    {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    @Override
    public boolean allowRequest()
    {
        synchronized(lock)
        {
            refill();
            if(tokens>=1)
            {
                tokens-=1;
                return true;
            }
            return false;
        }
    }

    private void refill()
    {
       long now = System.currentTimeMillis();
       long elapsedTime = (now - lastRefillTime);
       double tokensAdded = elapsedTime/1000.0 * refillRatePerSecond;

       if(tokensAdded >= 1)
       {
          tokens = Math.min(capacity, tokens + tokensAdded);
          lastRefillTime = now;
       }
    }
}
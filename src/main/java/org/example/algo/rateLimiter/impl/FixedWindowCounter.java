package org.example.algo.rateLimiter.impl;

import org.example.algo.rateLimiter.RateLimiter;

public class FixedWindowCounter implements RateLimiter
{
    private final long maxRequests;
    private final long windowSizeInMs;

    private long windowStart;
    private long requestCount;

    private final Object lock = new Object();

    public FixedWindowCounter(long maxRequests, long windowSizeInMs)
    {
        this.maxRequests = maxRequests;
        this.windowSizeInMs = windowSizeInMs;
        this.windowStart = System.currentTimeMillis();
        this.requestCount = 0;
    }

    @Override
    public boolean allowRequest()
    {
        synchronized(lock)
        {
            long currTime = System.currentTimeMillis();
            if(currTime - windowStart>=windowSizeInMs)
            {
               requestCount = 0;
               windowStart = currTime;
            }
            if(requestCount<maxRequests)
            {
                requestCount++;
                return true;
            }
            return false;
        }
    }
}

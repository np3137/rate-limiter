package org.example.algo.rateLimiter.config;

public class LeakyBucketConfig implements RateLimiterConfig
{
    private final long capacity;
    private final long leakRatePerSec;

    public LeakyBucketConfig(long capacity, long leakRatePerSec)
    {
        this.capacity = capacity;
        this.leakRatePerSec = leakRatePerSec;
    }
    public long getCapacity()
    {
        return capacity;
    }

    public long getLeakRatePerSec()
    {
        return leakRatePerSec;
    }
}
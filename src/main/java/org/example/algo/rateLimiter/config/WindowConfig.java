package org.example.algo.rateLimiter.config;

public class WindowConfig implements RateLimiterConfig
{
    private final int limit;
    private final long windowSizeMillis;

    public WindowConfig(int limit, long windowSizeMillis)
    {
        this.limit = limit;
        this.windowSizeMillis = windowSizeMillis;
    }
    public int getLimit()
    {
        return limit;
    }

    public long getWindowSizeMillis()
    {
        return windowSizeMillis;
    }
}
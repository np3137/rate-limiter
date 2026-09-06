package org.example.algo.rateLimiter.impl;

import org.example.algo.rateLimiter.RateLimiter;

import java.util.ArrayDeque;
import java.util.Queue;

public class SlidingWindowLog implements RateLimiter
{
   private final long maxRequests;
   private final long windowSizeInMs;

   private final Object lock = new Object();
   private final Queue<Long>timeStampLogs = new ArrayDeque<>();


    public SlidingWindowLog(long maxRequests, long windowSizeInMs)
    {
        this.maxRequests = maxRequests;
        this.windowSizeInMs = windowSizeInMs;
    }

    @Override
    public boolean allowRequest()
    {
       synchronized(lock)
       {
          long currTime = System.currentTimeMillis();
          long newWinStart = currTime - windowSizeInMs;
          while(!timeStampLogs.isEmpty() && timeStampLogs.peek()<=newWinStart)
          {
              timeStampLogs.poll();
          }
          if(timeStampLogs.size()<maxRequests)
          {
              timeStampLogs.offer(currTime);
              return true;
          }
          return false;
        }
    }
}
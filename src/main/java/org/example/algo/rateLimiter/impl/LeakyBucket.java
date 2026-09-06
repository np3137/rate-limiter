package org.example.algo.rateLimiter.impl;

import org.example.algo.rateLimiter.RateLimiter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class LeakyBucket implements RateLimiter
{
    private final long capacity;
    private final long leakRatePerSecond;

    private final Object lock = new Object();
    private final Deque<Long> exitTimes = new ArrayDeque<>();

    public LeakyBucket(long capacity, long leakRatePerSecond)
    {
       this.capacity = capacity;
       this.leakRatePerSecond = leakRatePerSecond;
    }

    @Override
    public boolean allowRequest()
    {
       synchronized(lock)
       {
           processQueue();
           if(exitTimes.size()<capacity)
           {
               long now = System.currentTimeMillis();
               long lastProcessTime = (exitTimes.isEmpty())?now: exitTimes.peekLast();

               long processTime = lastProcessTime + (1000/leakRatePerSecond);
               exitTimes.offer(processTime);
               return true;
           }
           return false;
       }
    }

    private void processQueue()
    {
        long currTimeInMs = System.currentTimeMillis();
        while(!exitTimes.isEmpty() && exitTimes.peek()<=currTimeInMs)
        {
            exitTimes.poll();
        }
    }
}

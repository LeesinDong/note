package com.leesin.guava.第6讲_concurrent.RateLimiter.令牌桶;

/***************************************
 * @author:Alex Wang
 * @Date:2017/11/12
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class TokenBucketExample
{

    public static void main(String[] args)
    {
        final TokenBucket实现令牌桶算法 tokenBucket = new TokenBucket实现令牌桶算法();
        for (int i = 0; i < 200; i++)
        {
            new Thread(tokenBucket::buy).start();
        }
    }
}

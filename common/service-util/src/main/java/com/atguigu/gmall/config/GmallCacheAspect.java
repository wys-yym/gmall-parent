package com.atguigu.gmall.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.config
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/5 14:41
 * @Version: 1.0
 */
@Component
@Aspect
public class GmallCacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.atguigu.gmall.config.GmallCache)")
    public Object gmallAroundAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String cacheKey = methodSignature.getMethod().getName();
        //获取方法参数
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            cacheKey = cacheKey + ":" + arg;
        }
        //从缓存中获取数据
        result = redisTemplate.opsForValue().get(cacheKey);
        if (null == result) {
            try {
                //加锁
                String cacheLockKey = UUID.randomUUID().toString();
                Boolean ok = redisTemplate.opsForValue().setIfAbsent(cacheKey + ":" + "lock", cacheLockKey, 5, TimeUnit.SECONDS);
                if (ok) {
                    result = joinPoint.proceed();
                    if (null == result) {
                        //同步空缓存
                        redisTemplate.opsForValue().set(cacheKey, result, 5, TimeUnit.SECONDS);
                    } else {
                        //同步缓存
                        redisTemplate.opsForValue().set(cacheKey, result);
                    }
                    //释放锁
                    String openKey = (String) redisTemplate.opsForValue().get(cacheKey + ":" + "lock");
                    if (openKey.equals(cacheLockKey)) {
                        redisTemplate.delete(cacheKey + ":" + "lock");
                    }
                } else {
                    //过几秒后重新从缓存里取值
                    Thread.sleep(2000);
                    return redisTemplate.opsForValue().get(cacheKey);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }
}

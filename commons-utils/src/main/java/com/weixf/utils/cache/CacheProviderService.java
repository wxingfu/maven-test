package com.weixf.utils.cache;

import java.util.function.Function;

/*
 * 常见缓存功能接口
 * 未提供特殊场景下用到的统计类的接口、分布式锁、自增（减）等
 * @author weixf
 * @date 2023-02-06
 */
public interface CacheProviderService {


    /**
     * 查询缓存
     *
     * @param key 缓存键 不可为空
     **/
    <T> T get(String key);


    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     **/
    <T> T get(String key, Function<String, T> function);


    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     * @param funcParm function函数的调用参数
     **/
    <T, M> T get(String key, Function<M, T> function, M funcParm);


    /**
     * 查询缓存
     *
     * @param key        缓存键 不可为空
     * @param function   如没有缓存，调用该callable函数返回对象 可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    <T> T get(String key, Function<String, T> function, Long expireTime);


    /**
     * 查询缓存
     *
     * @param key        缓存键 不可为空
     * @param function   如没有缓存，调用该callable函数返回对象 可为空
     * @param funcParam  function函数的调用参数
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    <T, M> T get(String key, Function<M, T> function, M funcParam, Long expireTime);


    /**
     * 设置缓存键值
     *
     * @param key 缓存键 不可为空
     * @param obj 缓存值 不可为空
     **/
    <T> void set(String key, T obj);


    /**
     * 设置缓存键值
     *
     * @param key        缓存键 不可为空
     * @param obj        缓存值 不可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    <T> void set(String key, T obj, Long expireTime);


    /**
     * 移除缓存
     *
     * @param key 缓存键 不可为空
     **/
    void remove(String key);


    /**
     * 是否存在缓存
     *
     * @param key 缓存键 不可为空
     **/
    boolean contains(String key);
}

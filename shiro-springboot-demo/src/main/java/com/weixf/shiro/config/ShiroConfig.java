package com.weixf.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.weixf.shiro.realm.MyRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ShiroConfig {

    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        // 2创建加密对象，设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 2.1采用md5加密
        matcher.setHashAlgorithmName("md5");
        // 2.2迭代加密次数
        matcher.setHashIterations(3);
        // 3将加密对象存储到myRealm中
        myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }

    // 配置SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        // 1创建defaultWebSecurityManager 对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 4将myRealm存入defaultWebSecurityManager 对象
        defaultWebSecurityManager.setRealm(myRealm());
        // 4.5设置rememberMe
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        // 4.6设置缓存管理器
        defaultWebSecurityManager.setCacheManager(getEhCacheManager());
        // 5返回
        return defaultWebSecurityManager;
    }

    // 缓存管理器
    public EhCacheManager getEhCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        InputStream is = null;
        try {
            is = ResourceUtils.getInputStreamForPath("classpath:ehcache/ehcache-shiro.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        CacheManager cacheManager = new CacheManager(is);
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    // cookie属性设置
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置跨域
        // cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        return cookie;
    }

    // 创建Shiro的cookie管理对象
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("1234567890ABCDEF".getBytes());
        return cookieRememberMeManager;
    }


    // 配置Shiro内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();

        // 设置不认证可以访问的资源
        definition.addPathDefinition("/test/userLogin", "anon");
        definition.addPathDefinition("/test/login", "anon");
        // 设置登出过滤器
        definition.addPathDefinition("/logout", "logout");
        // 设置需要进行登录认证的拦截范围
        definition.addPathDefinition("/**", "authc");
        // 添加存在用户的过滤器（rememberMe）
        definition.addPathDefinition("/**", "user");
        return definition;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}

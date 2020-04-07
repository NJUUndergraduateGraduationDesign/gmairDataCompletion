package edu.nju.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/9 16:35
 * @description：configuration of shiro
 */

@Configuration
public class ShiroConfig {
    /*
    配置拦截器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 配置拦截规则
        Map<String, String> filterMap = new HashMap<>();
        // 登录页面和登录请求路径需要放行
        filterMap.put("/test/**", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/logout", "anon");
        filterMap.put("/getCurrentUid", "anon");
        // 其他未配置的所有路径都需要通过验证，否则跳转到登录页
        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /*
    配置securityManager和对应的自定义realm
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /*
    配置自定义Realm以及加密算法：
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(3);
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    /*
    shiro权限控制切面
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    /*
    shiro权限控制切点
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}

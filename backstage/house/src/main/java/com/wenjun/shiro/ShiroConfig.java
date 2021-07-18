package com.wenjun.shiro;

import com.wenjun.Filter.JWTFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wen jun tang
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // shiro内置过滤器
        /**
         * anon: 无需认证直接访问
         * authc: 必须认证才能访问
         * user: 必须拥有记住我功能才能用
         * perms: 拥有某个资源权限才能用
         * role: 拥有某个角色权限才能用
         */
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // 设置我们自定义的JWT过滤器
        filterMap.put("jwt", new JWTFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        Map<String, String> filterRuleMap = new HashMap<>(100);
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");

        // 放行不需要权限认证的接口
        filterRuleMap.put("/user/login", "anon");
        filterRuleMap.put("/user/getVerificationCode", "anon");
        filterRuleMap.put("/unauthorized/**", "anon");

        //放行Swagger接口
        filterRuleMap.put("/v2/api-docs","anon");
        filterRuleMap.put("/swagger-resources/configuration/ui","anon");
        filterRuleMap.put("/swagger-resources","anon");
        filterRuleMap.put("/swagger-resources/configuration/security","anon");
        filterRuleMap.put("/swagger-ui.html","anon");
        filterRuleMap.put("/webjars/**","anon");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }


    /**
     * 注入 securityManager
     */
    @Bean
    public DefaultWebSecurityManager  securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(customRealm);

        /*
         * 关闭shiro自带的session
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}

package com.murun.fict.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.inject.Inject;


@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceServerTokenServices tokenService;

    @Resource
    private Environment env;

    /*@Inject
    public ResourceServerConfiguration(ResourceServerTokenServices tokenService) {
        Assert.notNull(tokenService, "tokenService must not be null!");
        this.tokenService = tokenService;
    }*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(env.getProperty("security.oauth2.resource-id"));
        resources.tokenServices(tokenService);
    }

    @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(env.getProperty("security.oauth2.client.client-id"));
        tokenServices.setClientSecret(env.getProperty("security.oauth2.client.client-secret"));
        tokenServices.setCheckTokenEndpointUrl(env.getProperty("security.oauth2.check-token-endpoint-url"));
        return tokenServices;
    }
}


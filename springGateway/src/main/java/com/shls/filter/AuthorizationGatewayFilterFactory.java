package com.shls.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 自定义过滤器类,类名一定要为filterName + GatewayFilterFactory
 * 定义一个类实现GatewayFilterFactory接口, 配置文件yml可配置name:Authorization
 */
@Component
public class AuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private static final Log log = LogFactory.getLog(AuthorizationGatewayFilterFactory.class);

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            //return chain.filter(exchange);
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            //校验jwtToken的合法性
            if (null==token && token.isEmpty()) {
                //不合法
                log.info("AuthorizationGatewayFilterFactory--请求:" + exchange.getRequest().getURI().getRawPath() + ", 没带有token失败!");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            //合法
            return chain.filter(exchange);
        };
    }

}
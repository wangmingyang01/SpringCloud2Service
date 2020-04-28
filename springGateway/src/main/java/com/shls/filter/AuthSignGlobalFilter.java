package com.shls.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Gateway Filter：全局系统统计，例如请求服务、请求时长、请求过滤等
 * Global Filter：应用在有针对性服务（例如用户服务、订单服务、商品服务），单业务自定义过滤时
 */
@Component
public class AuthSignGlobalFilter implements GlobalFilter, Ordered {
    private static final Log log = LogFactory.getLog(AuthSignGlobalFilter.class);

    /**
     * 拦截请求，获取authToken，并校验
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        //不合法
        if (null==token  || token.isEmpty()) {
            //当请求不携带Token或者token为空时，直接设置请求状态码为401，返回
            log.info("AuthSignGlobalFilter--请求:" + exchange.getRequest().getURI().getRawPath() + ", 没带有token失败!");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //合法
        return chain.filter(exchange);
    }

    /**
     * 越少执行优
     */
    @Override
    public int getOrder() {
        return -400;
    }
}
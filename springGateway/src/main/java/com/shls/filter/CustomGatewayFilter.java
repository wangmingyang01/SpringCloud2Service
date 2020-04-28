package com.shls.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Gateway Filter：全局系统统计，例如请求服务、请求时长、请求过滤等
 * Global Filter：应用在有针对性服务（例如用户服务、订单服务、商品服务），单业务自定义过滤时
 */
public class CustomGatewayFilter implements GatewayFilter, Ordered {
    private static final Log log = LogFactory.getLog(CustomGatewayFilter.class);

    private static final String COUNT_Start_TIME = "countStartTime";

    /**
     * 对路由转发的耗时进行统计
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(COUNT_Start_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(COUNT_Start_TIME);
                    Long endTime=(System.currentTimeMillis() - startTime);
                    if (startTime != null) {
                        log.info(exchange.getRequest().getURI().getRawPath() + ": " + endTime + "ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}

package com.shls.config;

import com.shls.filter.CustomGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * gateway配置路由主要有两种方式，一种是用yml配置文件，一种是写代码里
 * filters/uri注意顺序
 */
@Configuration
public class GatewayRoutes {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("springRedis", r -> r.path("/redis/**")
                        .filters(
                                // 配置转发路径：Path=/user/**，但是controller中并没有以/redis/**开头，
                                // 这样转发到具体服务会自动去掉/redis
                                f -> f.stripPrefix(1)
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://springRedis")
                        .order(0)
                )
                .route("springRedisTxc", r -> r.path("/redisTxc/**")
                        .filters(f ->
                                f.stripPrefix(1)
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://springRedisTxc")
                        .order(1)
                )
                .build();
    }
}

package daggerok.config;

import daggerok.GatewayApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = GatewayApplication.class)
public class GatewayConfig {

  /**
   * httpbin.org
   * <p>
   * /get
   */
  @Bean RouteLocator routes(final RouteLocatorBuilder routeLocatorBuilder) {

    return routeLocatorBuilder
        .routes()

        .route(r -> r.path("/get")
                     .addRequestHeader("X-Spring-Cloud-Gateway", "Is-F*cking-Awesome!")
                     .uri("http://httpbin.org:80/get")
        )

        .build()
        ;
  }
}

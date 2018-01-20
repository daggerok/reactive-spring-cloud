package daggerok.config;

import daggerok.GatewayApplication;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
    GatewayApplication.class,
    GatewayAutoConfiguration.class,
})
public class GatewayConfig {

  /**
   * httpbin.org
   * <p>
   * /get
   */
  @Bean RouteLocator routes(final RouteLocatorBuilder routeLocatorBuilder) {

    return routeLocatorBuilder
        .routes()

        /* http://127.0.0.1:8080/ -> http://httpbin.org:80/get */
        .route(r -> r.path("/")
                     .addRequestHeader("X-Spring-Cloud-Gateway", "Is-F*cking-Awesome!")
                     .uri("http://httpbin.org:80/get"))

        /* absolutely same behavior as prev */
        /* http://127.0.0.1:8080/get -> http://httpbin.org:80/get */
        .route(r -> r.path("/get")
                     .addRequestHeader("X-Spring-Cloud-Gateway", "Hell-Yeah!")
                     .uri("http://httpbin.org:80")) // but not .uri("http://httpbin.org:80/"))

        /* http://127.0.0.1:8080/get Host:my.fake.host -> http://httpbin.org:80/get */
        .route(r -> r.host("my.**.host")
                     .addRequestHeader("Ololo", "Trololo")
                     .uri("http://httpbin.org:80"))

        /* http://127.0.0.1:8080/local/get Host:boo.rewrite.org -> http://httpbin.org:80/get */
        .route(r -> r.host("*.rewrite.org")
                     .rewritePath("/local/(?<segment>.*)", "/${segment}")
                     .addRequestHeader("Boo", "O.o")
                     .uri("http://httpbin.org:80"))

        /* http://127.0.0.1:8080/local/get Host:boo.rewrite.org -> http://httpbin.org:80/get */
        .route(r -> r.host("*.set.path.org")
                     .and()
                     .path("/ololo/{segment}") // like spring-mvc path mapping
                     .setPath("/{segment}") // {segment} , but not ${segment} !
                     .addRequestHeader("Set", "Me!")
                     .uri("http://httpbin.org:80"))

        .build()
        ;
  }
}

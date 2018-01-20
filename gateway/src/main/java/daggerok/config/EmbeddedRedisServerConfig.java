package daggerok.config;

import daggerok.GatewayApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Protocol;
import redis.embedded.RedisServer;

@Configuration
@ComponentScan(basePackageClasses = GatewayApplication.class)
public class EmbeddedRedisServerConfig {

  @Bean(name = "embedded")
  public RedisServer redisServer() {

    RedisServer.builder()
               .reset();

    return RedisServer.builder()
                      .port(Protocol.DEFAULT_PORT)
                      .build();
  }

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    return new JedisConnectionFactory();
  }
}

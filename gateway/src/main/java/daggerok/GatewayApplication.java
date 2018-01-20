package daggerok;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class GatewayApplication {

  @Qualifier("embedded") final RedisServer redisServer;

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @SneakyThrows
  @PostConstruct
  private void start() {

    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
      log.error("sorry, but this embedded server doesn't work on windows...");
      System.exit(0);
    }

    if (nonNull(redisServer) && !redisServer.isActive())
      redisServer.start();

    log.info(String.format("redis listen ports: %s", redisServer.ports()
                                                                .stream()
                                                                .map(port -> port.toString())
                                                                .collect(Collectors.joining(","))));
  }

  @PreDestroy
  public void stop() {
    if (nonNull(redisServer) && redisServer.isActive())
      redisServer.stop();
  }
}

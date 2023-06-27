package ru.shorty.linkshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import ru.shorty.linkshortener.properties.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableDiscoveryClient
public class LinkShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkShortenerApplication.class, args);
	}
}

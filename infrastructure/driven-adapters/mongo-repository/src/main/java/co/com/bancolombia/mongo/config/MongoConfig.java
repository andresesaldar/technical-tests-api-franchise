package co.com.bancolombia.mongo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoConnectionDetails;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.PropertiesMongoConnectionDetails;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "co.com.bancolombia.mongo")
public class MongoConfig {

    @Bean
    public MongoDBSecret dbSecret(
            @Value("${spring.data.mongodb.uri}") String uri,
            @Value("${spring.data.mongodb.database}") String database) {
        return MongoDBSecret.builder()
                .uri(uri)
                .database(database)
                .build();
    }

    @Bean
    public MongoConnectionDetails mongoProperties(MongoDBSecret secret, SslBundles sslBundles) {
        MongoProperties properties = new MongoProperties();
        properties.setUri(secret.getUri());
        properties.setDatabase(secret.getDatabase());
        return new PropertiesMongoConnectionDetails(properties, sslBundles);
    }
}

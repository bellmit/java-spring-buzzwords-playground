package pl.piotrmacha.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
final public class RandomFactory {
    @Bean
    protected SecureRandom secureRandom() {
        return new SecureRandom();
    }
}

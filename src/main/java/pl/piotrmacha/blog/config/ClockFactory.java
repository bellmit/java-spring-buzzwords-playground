package pl.piotrmacha.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
final public class ClockFactory {
    @Bean
    protected Clock create() {
        return Clock.systemDefaultZone();
    }
}

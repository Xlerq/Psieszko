package edu.prz.psieszko.foundation.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(0L);
    }
}

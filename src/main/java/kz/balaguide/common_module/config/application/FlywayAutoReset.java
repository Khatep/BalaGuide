package kz.balaguide.common_module.config.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlywayAutoReset {

    private final Flyway flyway;

    @PostConstruct
    public void resetDatabase() {
        flyway.clean();   // удаляет все таблицы
        flyway.migrate(); // запускает V1__create_schema.sql
    }
}

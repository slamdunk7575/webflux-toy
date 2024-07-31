package com.yanggang.webflux.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableR2dbcAuditing
@EnableR2dbcRepositories
public class R2dbcConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DatabaseClient databaseClient;

    public R2dbcConfig(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        databaseClient.sql("SELECT 1").fetch().one()
                .subscribe(
                        success -> {
                            log.info("[Initialize] R2DBC database connection");
                        },
                        error -> {
                            log.info("[Failed] to initialize R2DBC database connection");
                        }
                );
    }
}

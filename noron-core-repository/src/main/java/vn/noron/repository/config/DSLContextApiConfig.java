package vn.noron.repository.config;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DSLContextApiConfig {
    private final HikariDataSource dataSource;

    public DSLContextApiConfig(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Primary
    public DSLContext dslContext() {
        Settings settings = new Settings().withRenderSchema(false);
        return DSL.using(dataSource, SQLDialect.POSTGRES, settings);
    }
}

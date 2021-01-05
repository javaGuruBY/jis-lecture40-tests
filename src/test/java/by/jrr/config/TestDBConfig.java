package by.jrr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.ui.ExtendedModelMap;

@Configuration
public class TestDBConfig {

    @Profile("test1")
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return getEmbeddedDataSourceWithScripts("test-data.sql");
    }

    @Profile("test2")
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource2() {
        return getEmbeddedDataSourceWithScripts("test-data2.sql");
    }

    @Profile("test3")
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource3() {
        return getEmbeddedDataSourceWithScripts("test-data.sql", "test-data2.sql");
    }

    public EmbeddedDatabase getEmbeddedDataSourceWithScripts(String... scripts) {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("init.sql")
                .addScripts(scripts)
                .build();
    }

    @Profile("tenStudents")
    @Bean
    public EmbeddedDatabase tenStudentsData() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("init.sql")
                .addScript("tenStudents.sql")
                .build();
    }


}

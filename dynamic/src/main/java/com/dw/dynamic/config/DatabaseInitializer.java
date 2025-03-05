package com.dw.dynamic.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final DataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // authority 테이블이 비어있는지 확인
        long count = (long) entityManager.createQuery("SELECT COUNT(a) FROM Authority a")
                .getSingleResult();

        if (count == 0) {
            executeSqlScript("data.sql");
        } else {
            System.out.println("데이터가 이미 존재하므로 data.sql 실행을 건너뜁니다.");
        }
    }

    private void executeSqlScript(String scriptPath) throws SQLException {
        System.out.println("data.sql 실행 중...");
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource(scriptPath));
            System.out.println("data.sql 실행 완료!");
        }
    }
}

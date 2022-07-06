package ru.myrating.application.config;

import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.dialect.PostgreSQL95Dialect;

import java.sql.Types;
import java.util.UUID;

public class PostgreSQL95JsonDialect extends PostgreSQL95Dialect {
    public PostgreSQL95JsonDialect() {
        super();
        this.registerHibernateType(
                Types.OTHER, JsonNodeBinaryType.class.getName()
        );
        this.registerHibernateType(
                Types.OTHER, UUID.class.getName()
        );
    }
}

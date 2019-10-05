package org.portalizer.retro;

import java.sql.Types;

import io.github.jhipster.domain.util.FixedH2Dialect;

public class H2CustomDialect extends FixedH2Dialect {

    public H2CustomDialect() {
        super();
        registerColumnType(Types.BINARY, "varbinary");
    }

}


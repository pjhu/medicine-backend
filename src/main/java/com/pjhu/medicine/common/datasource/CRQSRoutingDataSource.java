package com.pjhu.medicine.common.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class CRQSRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    public void setTargetDataSources(@SuppressWarnings("NullableProblems") Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            log.info("current is Slave datasource");
            return DataSourceType.SLAVE;
        }
        log.info("current is Master datasource");
        return DataSourceType.MASTER;
    }
}

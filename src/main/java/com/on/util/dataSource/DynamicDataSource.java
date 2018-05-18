package com.on.util.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.logging.Logger;

//public class DynamicDataSource extends AbstractRoutingDataSource {
//
//    public static final String DATA_SOURCE_DEFAULT = "dataSourceDefault";
//    public static final String DATA_SOURCE_DCDB = "dataSourceDCDB";
//    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
//    public static void setCustomerType(String customerType) {
//        contextHolder.set(customerType);
//    }
//
//    public static String getCustomerType() {
//        return contextHolder.get();
//    }
//
//    public static void clearCustomerType() {
//        contextHolder.remove();
//    }
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//
//        return getCustomerType();
//    }
//}

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    public Logger getParentLogger() {
        return null;
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDbType();
    }

}
package com.on.util.dataSource;


public class DataSourceContextHolder {
    public static  final String DATASOURCE_DEFAULT = "dataSourceDefault";
    public static  final String DATASOURCE_READONLY = "dataSourceReadOnly";
    public static  final String DATASOURCE_DCDB = "dataSourceDCDB";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        String rt = DATASOURCE_DEFAULT;
        if ( null != ((String) contextHolder.get()) ) {
            rt = ((String) contextHolder.get());
        } else {
            setDbType(rt);
        }
        return rt;
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}

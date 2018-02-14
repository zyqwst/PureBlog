package com.tale.init;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.sql2o.Sql2o;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.blade.ioc.Ioc;
import com.blade.jdbc.Base;
import com.tale.exception.TipException;

/**
 * 数据库操作
 * Created by biezhi on 2017/2/23.
 */
public final class TaleJdbc {

    private static final Properties jdbc_prop = new Properties();

    public static JdbcConf jdbcConf;

    private TaleJdbc() {
    }

    static {
        jdbc_prop.put("driverClassName", "com.mysql.jdbc.Driver");
        jdbc_prop.put("initialSize", "5");
        jdbc_prop.put("maxActive", "10");
        jdbc_prop.put("minIdle", "3");
        jdbc_prop.put("maxWait", "60000");
        jdbc_prop.put("removeAbandoned", "true");
        jdbc_prop.put("removeAbandonedTimeout", "180");
        jdbc_prop.put("timeBetweenEvictionRunsMillis", "60000");
        jdbc_prop.put("minEvictableIdleTimeMillis", "300000");
        jdbc_prop.put("validationQuery", "SELECT 1 FROM DUAL");
        jdbc_prop.put("testWhileIdle", "true");
        jdbc_prop.put("testOnBorrow", "false");
        jdbc_prop.put("testOnReturn", "false");
        jdbc_prop.put("poolPreparedStatements", "true");
        jdbc_prop.put("maxPoolPreparedStatementPerConnectionSize", "50");
        jdbc_prop.put("filters", "stat");

        InputStream in = TaleJdbc.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties props = new Properties();
        try {
            props.load(in);
            String db_host = props.get("db_host").toString();
            String db_name = props.get("db_name").toString();
            if (!isNull(db_host) && !isNull(db_name)) {
                String username = props.get("db_user").toString();
                String password = props.get("db_pass").toString();
                String url = "jdbc:mysql://" + db_host + "/" + db_name + "?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
                put("url", url);
                put("username", username);
                put("password", password);
                jdbcConf = new JdbcConf(db_host, db_name, username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注入数据库查询对象到ioc容器
     *
     * @param ioc
     * @return
     */
    public static boolean injection(Ioc ioc) {
        if (jdbc_prop.containsKey("url") && jdbc_prop.containsKey("username") && jdbc_prop.containsKey("password")) {
            DataSource dataSource;
            try {
                dataSource = DruidDataSourceFactory.createDataSource(jdbc_prop);
                Sql2o sql2o = new Sql2o(dataSource);
                Base.open(sql2o);
            } catch (Exception e) {
                throw new TipException("数据库连接失败, 请检查数据库配置");
            }
            return true;
        }
        return false;
    }
    public static void put(String key, String value) {
        jdbc_prop.remove(key);
        jdbc_prop.put(key, value);
    }

    private static boolean isNull(Object value) {
        return null == value || "null".equals(value.toString()) || "".equals(value.toString());
    }
}

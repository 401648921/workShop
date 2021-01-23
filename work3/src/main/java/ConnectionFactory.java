import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;

public class ConnectionFactory {

        private static DruidDataSource dataSource = null;

        static {
            try {
                String driver = "com.mysql.cj.jdbc.Driver";
                String url = "jdbc:mysql://localhost:3306/pestilence?serverTimezone=GMT%2B8&useSSL=false";
                String user = "root";
                String password = "zhen415322";

                dataSource = new DruidDataSource();
                dataSource.setDriverClassName(driver);
                dataSource.setUrl(url);
                dataSource.setUsername(user);
                dataSource.setPassword(password);
                dataSource.setInitialSize(5);
                dataSource.setMinIdle(1);
                dataSource.setMaxActive(10);

                dataSource.setPoolPreparedStatements(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static synchronized Connection getConnection() {
            Connection conn = null;
            try {
                conn = dataSource.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        }

    public static void main(String[] args) {
        getConnection();
    }
}


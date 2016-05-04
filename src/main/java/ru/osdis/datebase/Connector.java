package ru.osdis.datebase;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

        import java.sql.Connection;
        import java.sql.Driver;
        import java.sql.DriverManager;
        import java.sql.SQLException;

        /**
 * Created by Vladimir on 21.04.2016.
 */
        public class Connector {


            //константы. url, логи и пароль для БД.
        private static final String URL ="jdbc:mysql://localhost:3306/mydatebase";
        private static final String LOGIN = "root";
        private static final String PASSWORD = "1234";


        private static Connection connection;


                public static Connection getConnection() {
                return connection;
            }


        public void getDateBase(String url, String login, String password)  {

               try {
                    Driver driverSQL = new FabricMySQLDriver();

                    DriverManager.registerDriver(driverSQL);

                     connection = DriverManager.getConnection(url,login,password);

               } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }

            }
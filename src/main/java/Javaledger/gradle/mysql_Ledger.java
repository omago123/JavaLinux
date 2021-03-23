package Javaledger.gradle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Mysql {


    Connection conn = null;

    //mysql 연결
    Mysql() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookkeeping?autoReconnect=true&useSSL=false&serverTimezone=CTT",
                    "root",
                    "tiger"
            );

            //Statement stmt = conn.createStatement();
            //ResultSet rs = stmt.executeQuery("select s.sname from s,p,sp where p.pname='e' && s.sno=sp.sno && sp.pno=p.pno");
            //stmt.executeUpdate("drop database test1");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}



public class mysql_Ledger {



}

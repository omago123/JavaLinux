package mysql;

import java.sql.*;

public class Test {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javadb?autoReconnect=true&useSSL=false&serverTimezone=CTT",
                    "root",
                    "4321"
            );
            Statement stmt = conn.createStatement();
            //stmt.executeUpdate("insert into supply values('s1','p1',10),('s1','p2',7),('s3','p1',6),('s2','p1',9),('s2','p3',10)");
            ResultSet rs = stmt.executeQuery("select sname from supplier,spuply,product where pname='grape' && supply.pnum = product.pnum && supply.snum=supplier.snum");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
//"create table supply(snum char(10) not null, pnum char(20) not null, quantity int not null, primary key(snum,pnum), foreign key(snum) references supplier(snum) , foreign key(pnum) references product(pnum))"
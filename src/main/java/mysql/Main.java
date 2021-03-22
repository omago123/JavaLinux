package mysql;




import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;


class Mysql{

    Connection conn = null;

    //mysql 연결
    Mysql(){
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

    //mysql 닫기
    public void close(){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void insertMonthlyBalance(){
        try {
            PreparedStatement pstmt = conn.prepareStatement("insert into monthlyBalance values(?,?,?,?,?,?)");

            Statement stat1 = conn.createStatement();
            ResultSet res1 = stat1.executeQuery("select * from ledger");

            HashMap<Integer,ArrayList<String>> ledger = new HashMap<>();
            while(res1.next()){
                ArrayList<String> temp = new ArrayList<>();
                temp.add(res1.getString(2));
                temp.add("0.0");
                temp.add("0.0");
                temp.add("0.0");
                temp.add("0.0");
                ledger.put(res1.getInt(1),temp);
            }


            res1 = stat1.executeQuery("select * from journal");


            while(res1.next()){
                String date = res1.getString(3);
                String[] date2 = date.split("-");

                if(ledger.containsKey(res1.getInt(1))) {
                    if (date2[1].equals("01")) {
                        ledger.get(res1.getInt(1)).set(1, String.valueOf(Double.parseDouble(ledger.get(res1.getInt(1)).get(1)) + Double.parseDouble(res1.getString(5))));
                    }
                    if (date2[1].equals("02"))
                        ledger.get(res1.getInt(1)).set(2, String.valueOf(Double.parseDouble(ledger.get(res1.getInt(1)).get(2)) + Double.parseDouble(res1.getString(5))));
                    if (date2[1].equals("03"))
                        ledger.get(res1.getInt(1)).set(3, String.valueOf(Double.parseDouble(ledger.get(res1.getInt(1)).get(3)) + Double.parseDouble(res1.getString(5))));
                    if (date2[1].equals("04"))
                        ledger.get(res1.getInt(1)).set(4, String.valueOf(Double.parseDouble(ledger.get(res1.getInt(1)).get(4)) + Double.parseDouble(res1.getString(5))));
                }
            }

            Iterator<Integer> iter11 = ledger.keySet().iterator();

            while(iter11.hasNext()){
                int temp = iter11.next();
                pstmt.setInt(1,temp);
                pstmt.setString(2,ledger.get(temp).get(0));
                pstmt.setString(3,ledger.get(temp).get(1));
                pstmt.setString(4,ledger.get(temp).get(2));
                pstmt.setString(5,ledger.get(temp).get(3));
                pstmt.setString(6,ledger.get(temp).get(4));

                try{
                    pstmt.executeUpdate();
                }catch(Exception EE){}

            }

            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertMonthlyBalance2(){
        try {
            PreparedStatement pstmt = conn.prepareStatement("insert into monthlyBalance values(?,?,?,?,?,?)");

            HashMap<Integer,ArrayList<String>> list = new HashMap<>();

            for(int i=1;i<5;i++) {
                PreparedStatement res1 = conn.prepareStatement("select ledger.Acct_No,ledger.name,sum(debit_credit) from ledger,journal where Month(journal.Date)=? && ledger.Acct_No = journal.Acct_No group by ledger.Acct_No");
                res1.setInt(1 ,i);
                ResultSet rs = res1.executeQuery();
                while (rs.next()) {

                    if(!list.containsKey(rs.getInt(1))){
                        ArrayList<String> temp = new ArrayList<>();
                        temp.add(rs.getString(2));
                        temp.add("0.0");
                        temp.add("0.0");
                        temp.add("0.0");
                        temp.add("0.0");
                        list.put(rs.getInt(1),temp);
                    }
                    list.get(rs.getInt(1)).set(i,rs.getString(3));

                }
            }

            Iterator<Map.Entry<Integer,ArrayList<String>>> iter = list.entrySet().iterator();

            while(iter.hasNext()){
                Map.Entry<Integer,ArrayList<String>> temp =  iter.next();
                int key = temp.getKey();
                pstmt.setInt(1,key);
                pstmt.setString(2,list.get(key).get(0));
                pstmt.setDouble(3,Double.parseDouble(list.get(key).get(1)));
                pstmt.setDouble(4,Double.parseDouble(list.get(key).get(2)));
                pstmt.setDouble(5,Double.parseDouble(list.get(key).get(3)));
                pstmt.setDouble(6,Double.parseDouble(list.get(key).get(4)));

                try{
                    pstmt.executeUpdate();
                }catch(Exception ABC){

                }
            }

            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createMonthlyBalanceTextFile(){

        try{
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("select * from monthlyBalance");
            FileWriter fileWriter = new FileWriter("./src/main/java/mysql/monthlyBalance");
            StringBuilder stringBuilder = new StringBuilder("");
            while(res.next()){
                stringBuilder.append( res.getString(1)+",");
                stringBuilder.append( res.getString(2)+",");
                stringBuilder.append( res.getString(3)+",");
                stringBuilder.append( res.getString(4)+",");
                stringBuilder.append( res.getString(5)+",");
                stringBuilder.append( res.getString(6)+"&");
            }
            fileWriter.write(stringBuilder.toString());

            fileWriter.close();
            stat.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (IOException throwables) {
            throwables.printStackTrace();
        }

    }

    public void insertLedger(){
        try {
            PreparedStatement pstmt = conn.prepareStatement("insert into ledger values(?,?)");

            FileReader reader = new FileReader("./src/main/java/mysql/Ledger.txt");

            int c;
            StringBuilder sb = new StringBuilder("");
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            StringTokenizer st = new StringTokenizer(sb.toString(), "&");
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                String[] sArr = temp.split(",");
                pstmt.setInt(1, Integer.parseInt(sArr[0]));
                pstmt.setString(2, sArr[1]);
                try {
                    pstmt.executeUpdate();
                } catch (Exception alreadyExist) {
                    //System.out.println(alreadyExist);
                }

            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void createLedgerTextFile(){

        try{
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("select * from ledger");
            FileWriter fileWriter = new FileWriter("./src/main/java/mysql/ledger.txt");
            StringBuilder stringBuilder = new StringBuilder("");
            while(res.next()){
                stringBuilder.append( res.getString(1)+",");
                stringBuilder.append( res.getString(2)+"&");
            }
            fileWriter.write(stringBuilder.toString());

            fileWriter.close();
            stat.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (IOException throwables) {
            throwables.printStackTrace();
        }

    }


    public void insertJornal(){
        try {
            PreparedStatement pstmt = conn.prepareStatement("insert into journal values(?,?,?,?,?)");

            FileReader reader = new FileReader("./src/main/java/mysql/JournalMysql.txt");

            int c;
            StringBuilder sb = new StringBuilder("");
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            StringTokenizer st = new StringTokenizer(sb.toString(), "&");
            while (st.hasMoreTokens()) {
                String temp = st.nextToken();
                String[] sArr = temp.split(",");
                pstmt.setInt(1, Integer.parseInt(sArr[0]));
                pstmt.setInt(2, Integer.parseInt(sArr[1]));
                pstmt.setString(3, sArr[2]);
                pstmt.setString(4, sArr[3]);
                pstmt.setDouble(5, Double.parseDouble(sArr[4]));
                try {
                    pstmt.executeUpdate();
                } catch (Exception alreadyExist) {
                    //System.out.println(alreadyExist);
                }

            }
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createJornalTextFile(){

        try{
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("select * from journal");
            FileWriter fileWriter = new FileWriter("./src/main/java/mysql/Journal.txt");
            StringBuilder stringBuilder = new StringBuilder("");
            while(res.next()){
                stringBuilder.append( res.getString(1)+",");
                stringBuilder.append( res.getString(2)+",");
                stringBuilder.append( res.getString(3)+",");
                stringBuilder.append( res.getString(4)+",");
                stringBuilder.append( res.getString(5)+"&");
            }
            fileWriter.write(stringBuilder.toString());

            fileWriter.close();
            stat.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }catch (IOException throwables) {
            throwables.printStackTrace();
        }

    }

}

public class Main {
    public static void main(String[] args) {

        Mysql mysql = new Mysql();

        //mysql.insertMonthlyBalance();

        mysql.insertMonthlyBalance2();

        //mysql.createMonthlyBalanceTextFile();

        //mysql.insertJornal();

        //mysql.createJornalTextFile();

        //mysql.insertLedger();

//        mysql.createLedgerTextFile();

        mysql.close();

    }
}



//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.*;
//import java.util.*;
//
//
//class Mysql{
//
//    Connection conn = null;
//
//    //mysql 연결
//    Mysql(){
//        try {
//            conn = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/bookkeeping?autoReconnect=true&useSSL=false&serverTimezone=CTT",
//                    "root",
//                    "tiger"
//            );
//
//            //Statement stmt = conn.createStatement();
//            //ResultSet rs = stmt.executeQuery("select s.sname from s,p,sp where p.pname='e' && s.sno=sp.sno && sp.pno=p.pno");
//            //stmt.executeUpdate("drop database test1");
//
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    //mysql 닫기
//    public void close(){
//        try {
//            conn.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//
//    public void insertMonthlyBalance(){
//        try {
//            PreparedStatement pstmt = conn.prepareStatement("insert into monthlyBalance values(?,?,?,?,?,?)");
//
//            Statement stat1 = conn.createStatement();
//            ResultSet res1 = stat1.executeQuery("select * from ledger");
//
//            HashMap<Integer,ArrayList<String>> ledger = new HashMap<>();
//            while(res1.next()){
//                ArrayList<String> temp = new ArrayList<>();
//                temp.add(res1.getString(2));
//                temp.add("0.0");
//                temp.add("0.0");
//                temp.add("0.0");
//                temp.add("0.0");
//                ledger.put(res1.getInt(1),temp);
//            }
//
//
//            res1 = stat1.executeQuery("select * from journal");
//
//
//            while(res1.next()){
//                String date = res1.getString(3);
//                String[] date2 = date.split("-");
//
//                if(ledger.containsKey(res1.getInt(1))) {
//                    if (date2[1].equals("01")) {
//                        ledger.get(res1.getInt(1)).set(1, String.valueOf(Double.parseDouble(ledger.get(res1.getInt(1)).get(1)) + Double.parseDouble(res1.getString(5))));
//                    }
//                    if (date2[1].equals("02"))
//                        ledger.get(res1.getInt(1)).set(2, String.valueOf(Double.parseDouble(ledger.get(res1.getInt(1)).get(2)) + Double.parseDouble(res1.getString(5))));
//                    if (date2[1].equals("03"))
//                        ledger.get(res1.getInt(1)).set(3, String.valueOf(Double.parseDouble(ledger.get(res1.getInt(1)).get(3)) + Double.parseDouble(res1.getString(5))));
//                    if (date2[1].equals("04"))
//                        ledger.get(res1.getInt(1)).set(4, String.valueOf(Double.parseDouble(ledger.get(res1.getInt(1)).get(4)) + Double.parseDouble(res1.getString(5))));
//                }
//            }
//
//            Iterator<Integer> iter11 = ledger.keySet().iterator();
//
//            while(iter11.hasNext()){
//                int temp = iter11.next();
//                pstmt.setInt(1,temp);
//                pstmt.setString(2,ledger.get(temp).get(0));
//                pstmt.setString(3,ledger.get(temp).get(1));
//                pstmt.setString(4,ledger.get(temp).get(2));
//                pstmt.setString(5,ledger.get(temp).get(3));
//                pstmt.setString(6,ledger.get(temp).get(4));
//
//                try{
//                    pstmt.executeUpdate();
//                }catch(Exception EE){}
//
//            }
//
//            pstmt.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    public void insertMonthlyBalance2(){
//        try {
//            PreparedStatement pstmt = conn.prepareStatement("insert into monthlyBalance values(?,?,?,?,?,?)");
//
//            HashMap<Integer,ArrayList<String>> list = new HashMap<>();
//
//            for(int i=1;i<5;i++) {
//                PreparedStatement res1 = conn.prepareStatement("select ledger.Acct_No,ledger.name,sum(debit_credit) from ledger,journal where Month(journal.Date)=? && ledger.Acct_No = journal.Acct_No group by ledger.Acct_No");
//                res1.setInt(1 ,i);
//                ResultSet rs = res1.executeQuery();
//                while (rs.next()) {
//
//                    if(!list.containsKey(rs.getInt(1))){
//                        ArrayList<String> temp = new ArrayList<>();
//                        temp.add(rs.getString(2));
//                        temp.add("0.0");
//                        temp.add("0.0");
//                        temp.add("0.0");
//                        temp.add("0.0");
//                        list.put(rs.getInt(1),temp);
//                    }
//                    list.get(rs.getInt(1)).set(i,rs.getString(3));
//
//                }
//            }
//
//            Iterator<Map.Entry<Integer,ArrayList<String>>> iter = list.entrySet().iterator();
//
//            while(iter.hasNext()){
//                Map.Entry<Integer,ArrayList<String>> temp =  iter.next();
//                int key = temp.getKey();
//                pstmt.setInt(1,key);
//                pstmt.setString(2,list.get(key).get(0));
//                pstmt.setDouble(3,Double.parseDouble(list.get(key).get(1)));
//                pstmt.setDouble(4,Double.parseDouble(list.get(key).get(2)));
//                pstmt.setDouble(5,Double.parseDouble(list.get(key).get(3)));
//                pstmt.setDouble(6,Double.parseDouble(list.get(key).get(4)));
//
//                try{
//                    pstmt.executeUpdate();
//                }catch(Exception ABC){
//
//                }
//            }
//
//            pstmt.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    public void createMonthlyBalanceTextFile(){
//
//        try{
//            Statement stat = conn.createStatement();
//            ResultSet res = stat.executeQuery("select * from monthlyBalance");
//            FileWriter fileWriter = new FileWriter("./src/main/java/mysql/monthlyBalance");
//            StringBuilder stringBuilder = new StringBuilder("");
//            while(res.next()){
//                stringBuilder.append( res.getString(1)+",");
//                stringBuilder.append( res.getString(2)+",");
//                stringBuilder.append( res.getString(3)+",");
//                stringBuilder.append( res.getString(4)+",");
//                stringBuilder.append( res.getString(5)+",");
//                stringBuilder.append( res.getString(6)+"&");
//            }
//            fileWriter.write(stringBuilder.toString());
//
//            fileWriter.close();
//            stat.close();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }catch (IOException throwables) {
//            throwables.printStackTrace();
//        }
//
//    }
//
//    public void insertLedger(){
//        try {
//            PreparedStatement pstmt = conn.prepareStatement("insert into ledger values(?,?)");
//
//            FileReader reader = new FileReader("./src/main/java/mysql/Ledger.txt");
//
//            int c;
//            StringBuilder sb = new StringBuilder("");
//            while ((c = reader.read()) != -1) {
//                sb.append((char) c);
//            }
//            StringTokenizer st = new StringTokenizer(sb.toString(), "&");
//            while (st.hasMoreTokens()) {
//                String temp = st.nextToken();
//                String[] sArr = temp.split(",");
//                pstmt.setInt(1, Integer.parseInt(sArr[0]));
//                pstmt.setString(2, sArr[1]);
//                try {
//                    pstmt.executeUpdate();
//                } catch (Exception alreadyExist) {
//                    //System.out.println(alreadyExist);
//                }
//
//            }
//            pstmt.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void createLedgerTextFile(){
//
//        try{
//            Statement stat = conn.createStatement();
//            ResultSet res = stat.executeQuery("select * from ledger");
//            FileWriter fileWriter = new FileWriter("./src/main/java/mysql/ledger.txt");
//            StringBuilder stringBuilder = new StringBuilder("");
//            while(res.next()){
//                stringBuilder.append( res.getString(1)+",");
//                stringBuilder.append( res.getString(2)+"&");
//            }
//            fileWriter.write(stringBuilder.toString());
//
//            fileWriter.close();
//            stat.close();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }catch (IOException throwables) {
//            throwables.printStackTrace();
//        }
//
//    }
//
//
//    public void insertJornal(){
//        try {
//            PreparedStatement pstmt = conn.prepareStatement("insert into journal values(?,?,?,?,?)");
//
//            FileReader reader = new FileReader("./src/main/java/mysql/JournalMysql.txt");
//
//            int c;
//            StringBuilder sb = new StringBuilder("");
//            while ((c = reader.read()) != -1) {
//                sb.append((char) c);
//            }
//            StringTokenizer st = new StringTokenizer(sb.toString(), "&");
//            while (st.hasMoreTokens()) {
//                String temp = st.nextToken();
//                String[] sArr = temp.split(",");
//                pstmt.setInt(1, Integer.parseInt(sArr[0]));
//                pstmt.setInt(2, Integer.parseInt(sArr[1]));
//                pstmt.setString(3, sArr[2]);
//                pstmt.setString(4, sArr[3]);
//                pstmt.setDouble(5, Double.parseDouble(sArr[4]));
//                try {
//                    pstmt.executeUpdate();
//                } catch (Exception alreadyExist) {
//                    //System.out.println(alreadyExist);
//                }
//
//            }
//            pstmt.close();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void createJornalTextFile(){
//
//        try{
//            Statement stat = conn.createStatement();
//            ResultSet res = stat.executeQuery("select * from journal");
//            FileWriter fileWriter = new FileWriter("./src/main/java/mysql/Journal.txt");
//            StringBuilder stringBuilder = new StringBuilder("");
//            while(res.next()){
//                stringBuilder.append( res.getString(1)+",");
//                stringBuilder.append( res.getString(2)+",");
//                stringBuilder.append( res.getString(3)+",");
//                stringBuilder.append( res.getString(4)+",");
//                stringBuilder.append( res.getString(5)+"&");
//            }
//            fileWriter.write(stringBuilder.toString());
//
//            fileWriter.close();
//            stat.close();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }catch (IOException throwables) {
//            throwables.printStackTrace();
//        }
//
//    }
//
//}
//
//public class Main {
//    public static void main(String[] args) {
//
//        Mysql mysql= new Mysql();
//
//        //mysql.insertMonthlyBalance();
//
//        mysql.insertMonthlyBalance2();
//
//        //mysql.createMonthlyBalanceTextFile();
//
//        //mysql.insertJornal();
//
//        //mysql.createJornalTextFile();
//
//        //mysql.insertLedger();
//
////        mysql.createLedgerTextFile();
//
//        mysql.close();
//
//    }
//}
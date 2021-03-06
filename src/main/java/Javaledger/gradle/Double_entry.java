package Javaledger.gradle;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

class Journal_list{      // 계정과목을 하나의 클래스로 만들어 저장
    private int Acct_No;
    private int Check_No;
    private String Date;
    private String Description;
    private double amount;
    private int mon;


    public  Journal_list(int Acct_No,int Check_No,String Date,String Description,double amount,int mon){
        this.Acct_No =Acct_No; this.Check_No =Check_No; this.Date = Date; this.Description = Description; this.amount = amount; this.mon = mon;
    }

    public int getAcct_No(){
        return Acct_No;
    }

    public int getCheck_No(){
        return Check_No;
    }

    public String getDate(){
        return Date;
    }

    public String getDescription(){
        return Description;
    }

    public double getAmount(){
        return amount;
    }

    public int getMon(){
        return mon;
    }
}

class Ledger_list{
    private int account_no;
    private String subject;
    private int month;
    private double total;

    public Ledger_list(){};


    public Ledger_list(int account_no,String subject )
    {
        this.account_no=account_no; this.subject = subject; this.month = 0; this.total = 0.00;
    }

    public Ledger_list(int account_no,String subject,int month,double total){
        this.account_no = account_no; this.subject = subject; this.month = month; this.total = total;
    }

    public int getAccount_no(){
        return account_no;
    }

    public String getSubject(){
        return subject;
    }

    public int getMonth(){
        return month;
    }

    public double getTotal(){
        return total;
    }
}


public class Double_entry{

    public static void main(String[] args) throws IOException {

        ArrayList <String> Ledger = new ArrayList<>();   // 파일 읽기용
        ArrayList <String> Journal = new ArrayList<>();  // 파일 읽기용

        Vector<Journal_list> jv = new Vector<>();
        Vector<Ledger_list> lv = new Vector<>();

        ArrayList<Ledger_list> ll = new ArrayList<>();  // 월별 데이터저장


        makeJournal();
        makeLedger();

        readFile(Ledger,"Ledger.txt");
        readFile(Journal,"journal.txt");

        monthly_Ledger(ll);
        insertLedger(Ledger,Journal,jv,lv);

        merge(jv,lv,ll);
    }

    public static void makeJournal() throws IOException {

        ArrayList < Journal_list> jl = new ArrayList<>();  // 저널 파일쓰기 입력

        // jl 라는 ArrayList에 Journal_list라는 클래스형태로 회계정보를 입력한다.
        jl.add(new Journal_list(101,1271,"04/02/86","Auto expense               ",-78.70,4));
        jl.add(new Journal_list(510,1271,"04/02/97","Tune-up and minor repair   ",78.70,4));
        jl.add(new Journal_list(101,1272,"04/02/97","Rent                       ",-500.00,4));
        jl.add(new Journal_list(550,1272,"04/02/97","Rent for April             ",500.00,4));
        jl.add(new Journal_list(101,1273,"04/04/97","Advertising                ",-87.50,4));
        jl.add(new Journal_list(505,1273,"04/04/97","Newspaper ad re:new product",87.50,4));
        jl.add(new Journal_list(102,670   ,"04/02/97","Office expense              ",-32.78,4));
        jl.add(new Journal_list(540,670   ,"04/02/97","Printer cartridge           ",32.78,4));
        jl.add(new Journal_list(101,1274,"04/02/97","Auto expense               ",-31.83,4));
        jl.add(new Journal_list(510,1274,"04/02/97","Oil change                 ",31.83,4));

        Iterator <Journal_list> it = jl.iterator();

        // 모든 타입을 String으로 만든다.
        StringBuilder sb = new StringBuilder();
        FileWriter temp = new FileWriter("./journal.txt");

        // 이터레이터를 실행시켜 필요한 정보들을 담는다.
        while(it.hasNext()) {

            Journal_list ji = it.next();


            sb.append(ji.getAcct_No() + "," + ji.getCheck_No() + "," + ji.getDate() + "," + ji.getDescription() + "," + ji.getAmount()+","+ji.getMon()+"@");
        }
        try{
            temp.write(sb.toString());
            temp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeLedger( ) {

        FileWriter temp = null;
        try {
            temp = new FileWriter("./Ledger.txt");
            temp.write("101,Checking account #1@102,Checking account #2@505,Advertising expense@510,Auto expense");
            temp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile( ArrayList <String> tp, String a) {

        FileReader fin = null;
        int c;
        String temp = "";
        try{
            fin = new FileReader(a);
            while((c=fin.read()) !=-1){
                temp += (char) c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String [] get = temp.split("@");

        for (int i = 0; i < get.length ; i++) {

            tp.add(get[i]);
        }
    }

    public static void insertLedger(ArrayList <String> Ledger,ArrayList <String> Journal, Vector<Journal_list> a,Vector<Ledger_list> b) {

        Iterator<String> li = Ledger.iterator();
        Iterator<String> ji = Journal.iterator();


        while (ji.hasNext()) {

            String[] t = ji.next().split(",");

            a.add(new Journal_list(Integer.parseInt(t[0]), Integer.parseInt(t[1]), t[2], t[3], Double.parseDouble(t[4]),Integer.parseInt(t[5])));
        }

        while (li.hasNext()) {

            String[] p = li.next().split(",");

            b.add(new Ledger_list(Integer.parseInt(p[0]), p[1]));
        }
    }


    public static void merge(Vector<Journal_list> j, Vector<Ledger_list> l, ArrayList<Ledger_list> ll){
        Iterator<Ledger_list> bit = l.iterator();
        Iterator<Ledger_list> lit = ll.iterator();


        while(bit.hasNext()&&lit.hasNext()) {
            Ledger_list lt = bit.next();

            // Ledger_list 가 한번 다 돌때마다 Journal_list가 다시 호출되서 돌아야한다.
            Iterator<Journal_list> ait = j.iterator();
            System.out.println("-----------------------------------------------------------------");
            System.out.println(lt.getAccount_no() + "   " + lt.getSubject());

            HashMap <Integer,Double> currentTotal = new HashMap<>();
            double temp = 0;
            while (ait.hasNext()) {

                Journal_list jl = ait.next();
                if (jl.getAcct_No() == lt.getAccount_no()) {
                    System.out.println("       " + jl.getCheck_No() + "    " + jl.getDate() + "    " + jl.getDescription() + "       " + jl.getAmount());
                    temp += jl.getAmount();
                }
            }



            System.out.print("Prev.bal:  "+getPreviousBalance(ll,4).get(lt.getAccount_no()));
            System.out.println("                              New bal:  " +(getPreviousBalance(ll,4).get(lt.getAccount_no())+temp));
            //System.out.println("New bal: " +(getPreviousBalance(ll,4).get(lt.getAccount_no()) + temp));
        }
    }


    // 지난달의 잔액합계를 불러오기 위해서 만든 함수
    public static HashMap <Integer,Double> getPreviousBalance(ArrayList<Ledger_list> ll,int currentMon) {

        Iterator<Ledger_list> it = ll.iterator();
        HashMap<Integer, Double> match = new HashMap<>();


        while (it.hasNext()) {
            Ledger_list getAmount = it.next();
            // 현재달에서 -1을 하면 지난달의 시리얼넘버와 총잔액을 넘겨준다.
            if (getAmount.getMonth() == currentMon - 1) {
                match.put(getAmount.getAccount_no(), getAmount.getTotal());
            }
        }
        return match;
    }
    public static void monthly_Ledger( ArrayList<Ledger_list> ll) {


        //Jan
        ll.add(new Ledger_list(101, "Checking account #1   ", 1, 1032.57));
        ll.add(new Ledger_list(102, "Checking account #2   ", 1, 543.78));
        ll.add(new Ledger_list(505, "Advertising expense   ", 1, 25));
        ll.add(new Ledger_list(510, "    Auto expenses     ", 1, 195.40));
        ll.add(new Ledger_list(515, "    Bank charges      ", 1, 0.00));
        ll.add(new Ledger_list(520, "Books and publications", 1, 27.95));
        ll.add(new Ledger_list(525, "   Interest expense   ", 1, 103.50));
        ll.add(new Ledger_list(535, "Miscellaneous expense ", 1, 12.45));
        ll.add(new Ledger_list(540, "   Office expense     ", 1, 57.50));
        ll.add(new Ledger_list(545, "Postage and shipping  ", 1, 21));
        ll.add(new Ledger_list(550, "        Rent          ", 1, 500.00));
        ll.add(new Ledger_list(555, "       Supplies       ", 1, 112.00));

        //Feb
        ll.add(new Ledger_list(101, "Checking account #1   ", 2, 2114.56));
        ll.add(new Ledger_list(102, "Checking account #2   ", 2, 3094.17));
        ll.add(new Ledger_list(505, "Advertising expense   ", 2, 25));
        ll.add(new Ledger_list(510, "    Auto expenses     ", 2, 307.92));
        ll.add(new Ledger_list(515, "    Bank charges      ", 2, 0.00));
        ll.add(new Ledger_list(520, "Books and publications", 2, 27.95));
        ll.add(new Ledger_list(525, "   Interest expense   ", 2, 255.20));
        ll.add(new Ledger_list(535, "Miscellaneous expense ", 2, 17.87));
        ll.add(new Ledger_list(540, "   Office expense     ", 2, 105.25));
        ll.add(new Ledger_list(545, "Postage and shipping  ", 2, 27.63));
        ll.add(new Ledger_list(550, "        Rent          ", 2, 1000.00));
        ll.add(new Ledger_list(555, "       Supplies       ", 2, 167.50));

        //Mar
        ll.add(new Ledger_list(101, "Checking account #1   ", 3, 5219.23));
        ll.add(new Ledger_list(102, "Checking account #2   ", 3, 1321.20));
        ll.add(new Ledger_list(505, "Advertising expense   ", 3, 25));
        ll.add(new Ledger_list(510, "    Auto expenses     ", 3, 501.12));
        ll.add(new Ledger_list(515, "    Bank charges      ", 3, 0.00));
        ll.add(new Ledger_list(520, "Books and publications", 3, 87.40));
        ll.add(new Ledger_list(525, "   Interest expense   ", 3, 380.27));
        ll.add(new Ledger_list(535, "Miscellaneous expense ", 3, 23.87));
        ll.add(new Ledger_list(540, "   Office expense     ", 3, 138.37));
        ll.add(new Ledger_list(545, "Postage and shipping  ", 3, 57.45));
        ll.add(new Ledger_list(550, "        Rent          ", 3, 1500.00));
        ll.add(new Ledger_list(555, "       Supplies       ", 3, 2441.80));

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Acct.No.   Account title             Jan             Feb           Mar");

        Iterator<Ledger_list> lit = ll.iterator();

        while (lit.hasNext()) {
            Ledger_list Ledger = lit.next();

            if (Ledger.getMonth() == 1) {
                System.out.print(Ledger.getAccount_no() + "     " + Ledger.getSubject());
                Iterator<Ledger_list> lit2 = ll.iterator();
                while (lit2.hasNext()) {
                    Ledger_list Ledger2 = lit2.next();
                    if (Ledger.getAccount_no() == Ledger2.getAccount_no()){
                        System.out.printf("%13.2f", Ledger2.getTotal());
                    }
                }
                System.out.println();
            }

        }

    }
}











































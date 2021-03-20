package Javaledger.gradle;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


class Ledger{

    private final int accountNum;
    private final String name;

    public Ledger(int accountNum,String name){

        this.accountNum = accountNum;
        this.name = name;

    }

    public int getAccountNum() {
        return accountNum;
    }

    public String getName() {
        return name;
    }
}

class Journal{

    private final int accountNum;
    private final int checkNum;
    private final String date;
    private final String description;
    private final double amount;

    public Journal(int accountNum,int checkNum,String date, String description, double amount){
        this.accountNum=accountNum;
        this.checkNum=checkNum;
        this.date=date;
        this.description=description;
        this.amount=amount;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public double getAmount() {
        return amount;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}


class LedgerList{

    private final ArrayList<Ledger> ll;

    public ArrayList<Ledger> getLl() {
        return ll;
    }

    public LedgerList(){

        ll=new ArrayList<>();

    }

    public void insertLedger(int accountNum,String name){

        Iterator<Ledger> li = ll.iterator();
        while(li.hasNext()){
            if(li.next().getAccountNum()==accountNum)
                return;
        }
        ll.add(new Ledger(accountNum,name));

    }

    public void initLedgerList(){

        insertLedger(101,"Checking account #1");
        insertLedger(102,"Checking account #2");
        insertLedger(505,"Advertising expense");
        insertLedger(510,"Auto expenses");

    }

    public void show(){
        System.out.println("====== Ledger 출력 ======");

        Iterator<Ledger> li = ll.iterator();
        while(li.hasNext()){

            Ledger temp = li.next();
            System.out.println(temp.getAccountNum()+"\t"+temp.getName());

        }
        System.out.println();
    }

    public void showCurrentBalance(JournalList jl){
        Iterator<Ledger> li = ll.iterator();
        while(li.hasNext()) {
            showAcct(li.next().getAccountNum(),jl,4);
        }

    }

    private void showAcct(int accountNum,JournalList jl,int month) {

        Iterator<Ledger> li = ll.iterator();
        while (li.hasNext()) {
            Ledger temp = li.next();
            if (temp.getAccountNum() == accountNum) {
                System.out.println(temp.getAccountNum() + "\t" + temp.getName());
                Iterator<Journal> ji = jl.getJl().iterator();
                double newSum = 0;
                double prevSum = 0;
                while (ji.hasNext()) {
                    Journal temp2 = ji.next();
                    if (temp2.getAccountNum() == accountNum) {
                        int m = Integer.parseInt(temp2.getDate().split("/")[0]);
                        if (month == m || m == 0) {
                            System.out.printf("%5d", temp2.getAccountNum());
                            System.out.printf("%9d", temp2.getCheckNum());
                            System.out.printf("%12s", temp2.getDate());
                            System.out.printf("%30s", temp2.getDescription());
                            System.out.printf("%10.2f\n", temp2.getAmount());
                            newSum += temp2.getAmount();
                        }
                        if (month-1 == m) {
                            prevSum += temp2.getAmount();
                        }
                    }
                }

                System.out.printf("%25s%.2f%30s%.2f\n","Prev bal : ", prevSum, "New bal : ", newSum);

                return;

            }
        }
    }

    public void showAllMonthBalance(JournalList jl){

        System.out.println("Acct.No    /      Account title      /   Jan   /   Feb   /   Mar   /   Apr");
        Iterator<Ledger> li = ll.iterator();
        while(li.hasNext()) {
            Ledger temp = li.next();
            System.out.printf("%5d%30s",temp.getAccountNum(),temp.getName());

            double[] balance = new double[12];
            Iterator<Journal> ji = jl.getJl().iterator();
            while (ji.hasNext()) {
                Journal temp2 = ji.next();
                if(temp.getAccountNum() == temp2.getAccountNum()) {
                    int m = Integer.parseInt(temp2.getDate().split("/")[0]);
                    balance[m-1]+=temp2.getAmount();
                }
            }
            for(int i=0; i<4; i++){
                System.out.printf("%10.2f",balance[i]);
            }
            System.out.println();
        }
        System.out.println();

    }

    public void writeFile(){

        try{

            FileWriter fw = new FileWriter("./Ledger.txt");

            Iterator<Ledger> li = ll.iterator();

            StringBuilder sb = new StringBuilder();
            while(li.hasNext()){
                Ledger temp = li.next();
                sb.append(temp.getAccountNum()+","+temp.getName()+"&");
            }
            fw.write(sb.toString());
            fw.close();

        }catch(IOException e){
            System.out.println(e);
        }

    }

    public void readFile(){

        try {

            FileReader fr = new FileReader("./Ledger.txt");

            StringBuilder sb = new StringBuilder();

            int c;
            while((c = fr.read())!=-1){
                sb.append((char)c);
            }


            StringTokenizer st = new StringTokenizer(sb.toString(),"&");
            while(st.hasMoreTokens()){
                String[] strArr = st.nextToken().split(",");
                this.insertLedger(Integer.parseInt(strArr[0]),strArr[1]);
            }

        }catch(IOException e){
            System.out.println(e);
        }

    }

}



class JournalList{

    private final ArrayList<Journal> jl;

    public ArrayList<Journal> getJl() {
        return jl;
    }

    public JournalList(){

        jl= new ArrayList<>();

    }

    public void insertJournal(int accountNum,int checkNUm,String date,String description, double Amount){

        Iterator<Journal> ji = jl.iterator();

        while(ji.hasNext()){
            Journal temp = ji.next();
            if(temp.getAccountNum()==accountNum && temp.getCheckNum()==checkNUm&& temp.getDate()==date) {
                return;
            }
        }

        jl.add(new Journal( accountNum, checkNUm, date, description,  Amount));

    }

    public void initJournalList(){

        ///////////////////////// 1월 ///////////////////////
        insertJournal(101,1271,"01/02/97","Auto expense",-94.30);
        insertJournal(510,1271,"01/02/97","Tune-up and minor repair",94.30);
        insertJournal(101,1272,"01/02/97","Rent",-350.00);
        insertJournal(550,1272,"01/02/97","Rent for April",350.00);
        insertJournal(101,1273,"01/04/97","Advertising",-65.70);
        insertJournal(505,1273,"01/04/97","Newspaper ad re:new product",65.70);
        insertJournal(102,670,"01/02/97","Office expense",-54.34);
        insertJournal(540,670,"01/02/97","Printer cartridge",54.34);
        insertJournal(101,1274,"01/02/97","Auto expense",-34.65);
        insertJournal(510,1274,"01/09/97","Oil change",34.65);

        ///////////////////////// 2월 ///////////////////////
        insertJournal(101,1271,"02/02/97","Auto expense",-23.74);
        insertJournal(510,1271,"02/02/97","Tune-up and minor repair",23.74);
        insertJournal(101,1272,"02/02/97","Rent",-550.00);
        insertJournal(550,1272,"02/02/97","Rent for April",550.00);
        insertJournal(101,1273,"02/04/97","Advertising",-63.55);
        insertJournal(505,1273,"02/04/97","Newspaper ad re:new product",63.55);
        insertJournal(102,670,"02/02/97","Office expense",-64.54);
        insertJournal(540,670,"02/02/97","Printer cartridge",64.54);
        insertJournal(101,1274,"02/02/97","Auto expense",-53.53);
        insertJournal(510,1274,"02/09/97","Oil change",53.53);

        ///////////////////////// 3월 ///////////////////////
        insertJournal(101,1271,"03/02/97","Auto expense",-45.65);
        insertJournal(510,1271,"03/02/97","Tune-up and minor repair",45.65);
        insertJournal(101,1272,"03/02/97","Rent",-430.00);
        insertJournal(550,1272,"03/02/97","Rent for April",430.00);
        insertJournal(101,1273,"03/04/97","Advertising",-75.45);
        insertJournal(505,1273,"03/04/97","Newspaper ad re:new product",75.45);
        insertJournal(102,670,"03/02/97","Office expense",-45.44);
        insertJournal(540,670,"03/02/97","Printer cartridge",45.44);
        insertJournal(101,1274,"03/02/97","Auto expense",-34.46);
        insertJournal(510,1274,"03/09/97","Oil change",34.46);

        ///////////////////////// 4월 ///////////////////////
        insertJournal(101,1271,"04/02/97","Auto expense",-78.70);
        insertJournal(510,1271,"04/02/97","Tune-up and minor repair",78.70);
        insertJournal(101,1272,"04/02/97","Rent",-500.00);
        insertJournal(550,1272,"04/02/97","Rent for April",500.00);
        insertJournal(101,1273,"04/04/97","Advertising",-87.50);
        insertJournal(505,1273,"04/04/97","Newspaper ad re:new product",87.50);
        insertJournal(102,670,"04/02/97","Office expense",-32.78);
        insertJournal(540,670,"04/02/97","Printer cartridge",32.78);
        insertJournal(101,1274,"04/02/97","Auto expense",-31.83);
        insertJournal(510,1274,"04/09/97","Oil change",31.83);

    }

    public void show(int month){

        System.out.println("============================ Journal 출력 ============================");
        System.out.println("Acct.No / Check.No /  Date  /      Description      /    Debit/credit");
        System.out.println("======================================================================");

        Iterator<Journal> ji = jl.iterator();
        while(ji.hasNext()){
            Journal temp = ji.next();
            int m = Integer.parseInt(temp.getDate().split("/")[0]);
            if(month == m||month==0) {
                System.out.printf("%5d", temp.getAccountNum());
                System.out.printf("%9d", temp.getCheckNum());
                System.out.printf("%12s", temp.getDate());
                System.out.printf("%30s", temp.getDescription());
                System.out.printf("%10.2f\n", temp.getAmount());
                //System.out.println(temp.getAccountNum()+"\t"+temp.getCheckNum()+"\t"+temp.getDate()+"\t"+temp.getDescription()+"\t"+temp.getAmount());
            }
        }
        System.out.println();
    }

    public void writeFile(){

        try{

            FileWriter fw = new FileWriter("./Journal.txt");

            Iterator<Journal> ji = jl.iterator();

            String a;
            StringBuilder sb = new StringBuilder();

            while(ji.hasNext()){
                Journal temp = ji.next();
                sb.append(temp.getAccountNum()+","+temp.getCheckNum()+","+temp.getDate()+","+temp.getDescription()+","+temp.getAmount()+"&");
            }
            fw.write(sb.toString());
            fw.close();

        }catch(IOException e){
            System.out.println(e);
        }

    }

    public void readFile(){

        try {

            FileReader fr = new FileReader("./Journal.txt");

            StringBuilder sb = new StringBuilder();

            int c;
            while((c = fr.read())!=-1){
                sb.append((char)c);
            }


            StringTokenizer st = new StringTokenizer(sb.toString(),"&");
            while(st.hasMoreTokens()){
                String[] strArr = st.nextToken().split(",");
                this.insertJournal(Integer.parseInt(strArr[0]),Integer.parseInt(strArr[1]),strArr[2],strArr[3],Double.parseDouble(strArr[4]));
            }

        }catch(IOException e){
            System.out.println(e);
        }

    }
}

public class Main {

    public static void main(String[] args) {

        LedgerList ll = new LedgerList();
        JournalList jl = new JournalList();

        ll.initLedgerList();
        jl.initJournalList();
//
        //ll.show();
        //jl.show(0);
//
//        ll.showAcct(101,jl,4);
//        ll.showAcct(102,jl,4);
//        ll.showAcct(505,jl,4);
//        ll.showAcct(510,jl,4);
//
//        ll.showMonthBalance(jl);
//
        //ll.writeFile();
        //jl.writeFile();

        //ll.readFile();
        //jl.readFile();

        //ll.show();
        //jl.show(4);

        //ll.showAllMonthBalance(jl);

        ll.showCurrentBalance(jl);



    }
}
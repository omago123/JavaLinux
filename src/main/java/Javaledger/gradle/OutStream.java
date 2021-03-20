package Javaledger.gradle;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;





public class OutStream {

    public static void main(String[] args) {

        Vector <String> v = new Vector<>();

        Vector<String> v1 = File("list1.txt");
        Vector<String> v2 = File("list2.txt");

        merge(v,v1,v2);

        record(v);

    }

    public static Vector <String> File(String a){
        FileReader fin = null;
        int c;
        Vector<String> v = new Vector<>();
        String temp = "";
        try {
            fin = new FileReader(a);
            while ((c = fin.read()) != -1) {
                temp += (char) c;
            }
            String[] temp1 =temp.split(",");

            for (int i = 0; i <temp1.length; i++) {
                v.add(temp1[i]);
            }
            fin.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return v;
    }

    public static Vector <String>  merge(Vector<String> v,Vector<String> v1,Vector<String> v2){


        Iterator <String> it = v1.iterator();

        while(it.hasNext()){
            String iString1 = it.next();
            // list1을 호출할 때마다  list2의 이터레이터는 계속 선언되어야 한다. 이터레이터는 한번 끝까지 돌고나면 끝이기 때문이다.
            Iterator <String> it2= v2.iterator();

            while(it2.hasNext()){
                String iString2 = it2.next();
                if(iString1.equals(iString2)) {
                    v.add(iString1);
                    break;
                }
            }
        }

        return v;
    }

    private static void record(Vector <String> v) {

        String temp= "";
        Iterator <String> it = v.iterator();
        while(it.hasNext()){
            temp = temp.concat(it.next()+",");
        }

        File file = new File("./list3.txt");

        try{
            BufferedWriter writer =new BufferedWriter(new FileWriter(file));
            writer.write(temp);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
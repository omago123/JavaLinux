package Javaledger.gradle;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;


public class OutStream {
    public static void main(String[] args) {
        FileReader fin = null;
        String temp = "";
        Vector<String> vec = new Vector<>();
        int c;
        try{
            fin = new FileReader("./list1.txt");
            while((c =fin.read()) != -1) {
                temp += (char)c;
            }
            StringTokenizer st = new StringTokenizer(temp,",");
            while(st.hasMoreElements()){
                vec.add((String) st.nextElement());
            }

            Iterator <String> it = vec.iterator();

            while(it.hasNext()){
                System.out.println(it.next());
            }

            fin.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


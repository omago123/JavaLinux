package Javaledger.gradle;

import java.io.BufferedOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class FileList{
    FileReader fin = null;
    int c;

    public Vector<String> read(String a){

        String temp="";
        Vector<String> vec = new Vector<>();

        try {

            fin = new FileReader(a);

            while ((c = fin.read()) != -1) {
                temp += (char) c;

            }

            StringTokenizer st  = new StringTokenizer(temp,",");
            while(st.hasMoreElements()){
                vec.add((String)st.nextElement());
            }

            vec.stream().forEach(System.out::println);



            fin.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return vec;
    }

}

public class Test {
    public static void main(String[] args) {

        FileList file = new FileList();
        Vector<String> list1 = file.read("./list1.txt");
        Vector<String> list2 = file.read("./list2.txt");

        Iterator<String> iter1 = list1.iterator();



        while(iter1.hasNext()){

            Iterator<String> iter2 = list2.iterator();

            while(iter2.hasNext()){

            }
        }

    }
}
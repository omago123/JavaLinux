package Javaledger.gradle;

import java.util.*;
import java.util.stream.*;

class Student {
    String name;
    int score;

    public Student(String name,int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }
}


public class Test1{
    public static void main(String[] args) {

        Vector <Student> v = new Vector<>();
        v.add(new Student("한영",30));
        v.add(new Student("영찬",75));
        v.stream().sorted((Student a,Student b) ->{
            if(a.getScore() <b.getScore()){
                return -1;
            }else if(a.getScore() >b.getScore()){
                return 1;
            }
            return 0;
        });

        v.stream().forEach(System.out::println);

    }
}




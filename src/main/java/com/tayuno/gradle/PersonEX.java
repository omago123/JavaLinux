package com.tayuno.gradle;

import java.util.Scanner;
import java.util.Stack;

class Person {
    private String name;
    private int age;
    private String address;

    public Person(String name,int age,String address){
        this.name = name; this.age =age; this.address =address;
    }

    void show(){
        System.out.println("이름 : " + name +" ㅣ "+ " 나이 : " + age + " ㅣ " + " 주소 : " + address);
    }
}

class Student extends Person {
    private int grade;
    private String subject;
    private int score;

    public Student(int grade, String subject, int score, String name, int age, String address) {
        super(name, age, address);
        this.grade = grade;
        this.subject = subject;
        this.score = score;
    }

    @Override
    void show() {
        super.show();
        System.out.println("학년 : " + grade + " ㅣ " + " 과목 : " + subject + " ㅣ " + " 점수 : " + score);
    }
}
class JobStudent extends Student {
    private String jobname;



    public JobStudent(String name, int age, String address, int grade, String subject, int score, String jobname) {
        super( grade,  subject,  score,  name, age,address );
        this.jobname = jobname;


    }
    @Override
    void show() {
         super.show();
        System.out.println("업종 : " +jobname);
    }
}




public class PersonEX {

    static void init(Person [] a) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i <a.length; i++) {

            if (i ==0) {
                System.out.println("이름,나이,주소를 입력");
                a[i] = new Person(scanner.next(), scanner.nextInt(), scanner.next());
            }
            else if (i ==1)
            {
                System.out.println("학년,과목,점수,이름,나이,주소를 입력");
                a[i] = new Student(scanner.nextInt(),scanner.next(),scanner.nextInt(),scanner.next(),scanner.nextInt(),scanner.next());

            }
            else {
                System.out.println("이름,나이,주소,학년,과목,점수,직업을 입");
                a[i] = new JobStudent(scanner.next(),scanner.nextInt(),scanner.next(),scanner.nextInt(),scanner.next(),scanner.nextInt(),scanner.next());
            }
        }
    }

    static Person[] doStack(Person[]a){

        Stack <Person> stack = new Stack<>();
        Person [] b = new Person[a.length];
        for (Person p : a) {
            stack.push(p);
        }
        for (int i =0; i<a.length;i++){
            b[i]=stack.pop();
        }
        return b;

    }

    static void show(Person[]a){
        for (int i = 0; i<a.length;i++){
            a[i].show();
        }
    }
      public static void main(String[] args) {

          Person [] student = new Person[3];
          Person []result;
          init(student);
          show(student);
          result = doStack(student);
          show(result);
        }
}


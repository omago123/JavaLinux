package collection.gradle;



import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// class Student implements Comparable<Student>{
//    String name;
//    int score;
//
//    public Student(String name,int score){
//        this.name = name;
//        this.score = score;
//    }
//
//    public String getName(){
//        return this.name;
//    }
//
//    public int getScore(){
//        return this.score;
//    }
//
//
//    @Override
//    public String toString() {
//        return
//                "name='" + name + '\'' +
//                ", score=" + score ;
//    }
//
//    @Override
//    public int compareTo(Student s){
//        if(this.score < s.getScore()){
//            return -1;
//        } else if(this.score > s.getScore()){
//            return -1;
//        } else if (this.score > s.getScore()){
//            return -1;
//        }
//        return 0;
//    }
//}
//
//public class Test01{
//    public static void main(String[] args) {
//        List<Student> list = new ArrayList<Student>();
//        list.add(new Student("a",9));
//        list.add(new Student("b",8));
//        list.add(new Student("c",6));
//        list.add(new Student("d",3));
//        list.add(new Student("e",10));
//
//
//
//        Collections.sort(list);
//        list.stream().forEach(n-> System.out.println(n));
//    }
//}

class Student implements Comparable<Student>{
    String name;
    int score;
    public Student(String name,int score){
        this.name= name; this.score =score;
    }

    public int getScore(){
        return score;
    }

    public String getName(){
        return name;
    }

        @Override
    public int compareTo(Student s){
        if(this.score < s.getScore()){
            return 1;
        } else if(this.score > s.getScore()){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}


//public class Test01{
//    public static void main(String[] args) {
//        List<Student> list =new ArrayList<Student>();
//
//        list.add(new Student("a",5));
//        list.add(new Student("b",10));
//        list.add(new Student("c",1));
//        list.add(new Student("d",52));
//        list.add(new Student("e",23));
//
//        Collections.sort(list, new Comparator<Student>() {
//            @Override
//            public int compare(Student s1, Student s2) {
//                if((s1.getName()).compareTo(s2.getName()) > 0){
//                    return 1;
//                }else if((s1.getName()).compareTo(s2.getName())< 0){
//                    return -1;
//                }
//                return 0;
//            }
//        });
//
//        System.out.println(list);
//
//    }
//}

public class Test01{

    public static void main(String[] args) {

        Vector<Student> ls = new Vector<>();

        ls.add(new Student("이용식",53));
        ls.add(new Student("이싱식",44));
        ls.add(new Student("이넉식",35));

//        ls.sort((Student a,Student b)->{
//            if(a.score<b.score)
//                return -1;
//            else if(a.score>b.score)
//                return 1;
//            return 0;
//        });

        Stream<Student> ls2 = ls.stream().sorted((Student a,Student b)->{
            if(a.score<b.score)
                return -1;
            else if(a.score>b.score)
                return 1;
            return 0;
        });

        ls2.forEach(System.out::println);

        System.out.println();
        ls.stream().forEach(System.out::println);
        //System.out.println(ls);

    }


}

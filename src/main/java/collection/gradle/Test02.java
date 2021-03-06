package collection.gradle;

import java.util.ArrayList;
import java.util.Optional;

class Member{
    private String name;
    private int age;
    private String phoneNum;
    private String address;

    public Member(){};

    public Member(String name, int age, String phoneNum, String address){
        this.name =name;
        this.age =age;
        this.phoneNum = phoneNum;
        this.address =address;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name =name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age =age;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String toString(){
        return name +" " + age+ " " + phoneNum +" " + address;
    }

}


public class Test02 {

    public static void main(String[] args) {
        ArrayList <Member> members = new ArrayList<>();
        members.add(new Member("김푸름",25,"010-123-4124","서울"));
        members.add(new Member("김하늘",30,"010-421-7524","부산"));
        members.add(new Member("김푸름",29,"010-723-7841","대전"));

        Optional<Member> mem1 = members.stream()
                .filter((m)->m.getAge() >20)
                .findFirst();

        if(mem1.isPresent())
            System.out.println(mem1.get());

        members.stream()
                .filter((m)->m.getAge() > 20)
                .findFirst()
                .ifPresent(System.out::println);

        Member mem2 = members.stream()
                .filter((m)->m.getAge() > 30)
                .findFirst()
                .orElseGet(Member::new);

        System.out.println(mem2);
    }
}

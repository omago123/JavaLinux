//package collection.gradle;
//
//import java.util.ArrayList;
//import java.util.stream.IntStream;
//import java.util.stream.Stream;
//
//class Contact{
//    private String name;
//    private String phoneNum;
//
//    public Contact(String name, String phoneNum){
//        this.name =name;
//        this.phoneNum = phoneNum;
//    }
//
//    public String getName(){
//        return name;
//    }
//
//    public void setName(String name){
//        this.name =name;
//    }
//
//    public String getPhoneNum(){
//        return phoneNum;
//    }
//
//    public void setPhoneNum(String phoneNum){
//        this.phoneNum = phoneNum;
//    }
//
//    @Override
//    public String toString() {
//        return name+ " " + phoneNum;
//    }
//}
//
//public class Test03 {
//
//    public static void main(String[] args) {
//        ArrayList<Member> members = new ArrayList<>();
//        members.add(new Member("김푸름", 25, "010-112-4631","서울"));
//        members.add(new Member("김하늘", 30, "010-645-5674","부산"));
//        members.add(new Member("오정임", 29, "010-733-6742","대전"));
//
//        System.out.println("<<회원 정보>>");
//        Stream<Member> stream = members.stream();
//        stream.forEach(System.out::println);
//
//        System.out.println("<<연락처>>");
//        Stream<Contact> contactList =members.stream()
//                .map((m) -> new Contact(m.getName(),m.getPhoneNum()));
//        contactList.forEach(System.out::println);
//
//        System.out.println("<<연락처 검색>>");
//        Stream<Contact> searchList = members.stream()
//                .filter((m)->m.getName().equals("김하늘"))
//                .map((m->new Contact(m.getName(), m.getPhoneNum())));
//        searchList.forEach(System.out::println);
//    }
//}

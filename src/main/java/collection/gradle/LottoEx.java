package collection.gradle;



import java.util.*;

public class LottoEx{

    public static void main(String[] args) {
        HashMap<Integer,HashSet<Integer>> LottoList = new HashMap<>();  // HashMap 정의
        HashMap<Integer,String> phone = new HashMap<>();

        lotto_generator(LottoList,1000);  // lotto 번호 발생시킨 후 해시맵에 저장
        phone_num(phone,100);
        // 발생된 로또번호를 구매자 휴대폰번호와 매칭시키는 해시맵
        Buy_lotto(LottoList,phone);
        HashMap<HashMap<Integer,String>,HashMap<Integer,HashSet<Integer>>> Buy = new HashMap<>();




    }

    static void lotto_generator(HashMap<Integer,HashSet<Integer>> LottoList ,int n){
        Random num = new Random();

        HashSet <Integer> lotto = null;
        for (int i = 0; i <n; i++) {
            lotto = new HashSet <Integer>();

             while (lotto.size() < 6) {    //  size 가 5일때 조건 충족해서
                lotto.add(num.nextInt(46));   // 총 숫자는 6개가 된다.
            }
            LottoList.put(i,lotto);
        }
    }

    static void phone_num(HashMap<Integer,String> phone,int n) {
        Random num = new Random();

        String temp = "";

        for (int i = 0; i < n; i++) {
            temp = "010-";
            for (int j = 0; j < 8; j++) {
                if (j == 4) {
                    temp = temp.concat("-");
                }
                temp = temp.concat(Integer.toString(num.nextInt(9)));
            }
            phone.put(i,temp);
        }
    }

    static void Buy_lotto(HashMap<Integer,HashSet<Integer>> LottoList,HashMap<Integer,String> phone,int n){
        Random num = new Random();
        

        HashMap<String> Buy = new HashMap<>();

        for(int i = 0 ; i < n; i++) {
            Buy.put(phone.get(i), LottoList.get(num.nextInt(1000)));
        }
    }
}
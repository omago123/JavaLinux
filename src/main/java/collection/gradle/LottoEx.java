package collection.gradle;



import java.util.*;

public class LottoEx{

    public static void main(String[] args) {
        lotto_generator(5);
        phonenumber(100);


    }

    static void lotto_generator(int n){
        Random num = new Random();

        HashSet <Integer> lotto = null;
        for (int i = 0; i <n; i++) {
            lotto = new HashSet <Integer>();

            for (int j = 0; lotto.size() <= 6 ; j++) {
                lotto.add(num.nextInt(46));
            }
        }
    }

    static void phonenumber(int n) {
        Random num = new Random();

        HashSet <Integer> number = null;

    }
}
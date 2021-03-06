package collection.gradle;



import java.util.*;

class Lotto{
    HashSet<Integer> lotto_num;
    int bonusball;

    public Lotto(){
        this.lotto_num = null;
        this.bonusball = 0;
    }
}

public class LottoEx{

    public static void main(String[] args) {

        HashMap<Integer,Lotto> makelotto = new HashMap<>();
        HashMap<Integer,String> phone = new HashMap<>();
        HashMap<String,Lotto> buyer = new HashMap<>();



        lotto_generator(makelotto,1000);
        phone_num(phone,100);
        Buy_lotto(phone,makelotto,buyer);
        Win_lotto(makelotto,buyer);





    }

    static void lotto_generator(  HashMap < Integer,Lotto> makelotto,int n){


        Random num = new Random();
        for (int i = 0; i <n; i++) {
            Lotto lotto = new Lotto();

            lotto.lotto_num  = new HashSet<Integer>();
            while (lotto.lotto_num.size() < 6) {    //  size 가 5일때 조건 충족해서
            lotto.lotto_num.add(num.nextInt(46));   // 총 숫자는 6개가 된다.
            }
            lotto.bonusball = num.nextInt(9);

            makelotto.put(i,lotto);

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
            System.out.println(temp);
            phone.put(i,temp);
        }
    }

    static void Buy_lotto(HashMap<Integer,String> phone,HashMap < Integer,Lotto> makelotto,HashMap<String,Lotto> buyer ){
        Random num = new Random();
        for (int i = 0; i <100 ; i++) {
            buyer.put(phone.get(i),makelotto.get(i));
        }
    }


    static void Win_lotto(HashMap<Integer,Lotto> makelotto, HashMap<String,Lotto> buyer) {
        Random num = new Random();
        int index = num.nextInt(100);


//        Scanner phone = new Scanner(System.in);
//        System.out.println("번호 입력");
//
//        String phonenumber = phone.next();


       // List<Integer> L = new ArrayList<Integer>(buyer.get(phonenumber).lotto_num);

        int number = 0;
        int count = 0;

        //Iterator<Integer> check = L.iterator();
//        while (check.hasNext()) {
//            number = check.next();
//            if (makelotto.get(index).lotto_num.contains(number)) count++;
//        }
//        if (count == 5 && buyer.get(phonenumber).bonusball == makelotto.get(index).bonusball) {
//            System.out.println("2등 당첨자 : " + phonenumber);
//            System.out.println("번호 : " + buyer.get(phonenumber).lotto_num + "보너스볼 : " + buyer.get(phonenumber).bonusball);
//        }else if(count == 5 &&buyer.get(phonenumber).bonusball != makelotto.get(index).bonusball)
//        {
//            System.out.println("3등 당첨자 : " + phonenumber);
//            System.out.println("번호 : " + buyer.get(phonenumber).lotto_num + "보너스볼 : " + buyer.get(phonenumber).bonusball);
//        }else if(count == 4){
//            System.out.println("4등 당첨자 : " + phonenumber);
//            System.out.println("번호 : " + buyer.get(phonenumber).lotto_num + "보너스볼 : " + buyer.get(phonenumber).bonusball);
//        }else if(count == 3){
//            System.out.println("5등 당첨자 : " + phonenumber);
//            System.out.println("번호 : " + buyer.get(phonenumber).lotto_num + "보너스볼 : " + buyer.get(phonenumber).bonusball);
//        }else{
//            System.out.println("다음 기회에");
//        }


        System.out.println("당첨 번호 : " + makelotto.get(index).lotto_num + " 보너스 볼 : " + makelotto.get(index).bonusball);
        Iterator<String> iter = buyer.keySet().iterator();
        while (iter.hasNext()) {
            String s = iter.next();

            if (buyer.get(s).lotto_num == makelotto.get(index).lotto_num) {
                System.out.println("1등 당첨자 : " + s);
                System.out.println("당첨번호 : " + buyer.get(s).lotto_num);
            }
            List<Integer> L = new ArrayList<Integer>(buyer.get(s).lotto_num);

            Iterator<Integer> check = L.iterator();

            int lum = 0;
            while (check.hasNext()) {
                number = check.next();
                if (makelotto.get(index).lotto_num.contains(number)) lum++;
            }
            if (lum == 5 && buyer.get(s).bonusball == makelotto.get(index).bonusball) {
                System.out.println("2등 당첨자 : " + s);
                System.out.println("번호 : " + buyer.get(s).lotto_num + "보너스볼 : " + buyer.get(s).bonusball);
            }else if(lum == 5 &&buyer.get(s).bonusball != makelotto.get(index).bonusball)
            {
                System.out.println("3등 당첨자 : " + s);
                System.out.println("번호 : " + buyer.get(s).lotto_num + "보너스볼 : " + buyer.get(s).bonusball);
            }else if(lum == 4){
                System.out.println("4등 당첨자 : " + s);
                System.out.println("번호 : " + buyer.get(s).lotto_num + "보너스볼 : " + buyer.get(s).bonusball);
            }else if(lum == 3){
                System.out.println("5등 당첨자 : " + s);
                System.out.println("번호 : " + buyer.get(s).lotto_num + "보너스볼 : " + buyer.get(s).bonusball);
            }
        }



    }








    }

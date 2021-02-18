package second.gradle;


import java.util.Random;

class ListNode{
    private int coef;
    private int exp;
    public ListNode link;

    public ListNode(){
        this.coef = 0;
        this.exp = 0;
        this.link = null;
    }

    public ListNode(int coef,int exp){
        this.coef = coef;
        this.exp = exp;
        this.link = null;
    }
    public int getCoef() { return this.coef;}
    public int getExp() { return this.exp;}

    // newNode의 값을 새로 설정하는 함수
    public void setCoef(int coef){ this.coef = coef;}
    public void setExp(int exp){this.exp =exp;}
}

class Polynomial {
    ListNode first;

    Polynomial() {
        this.first = null;
    }

    public void insertNode(int coef, int exp) {
        ListNode newNode = new ListNode(coef, exp);

        // first 가 null 일때 newNode 가 first 가 된다.
        if (first == null) {
            first = newNode;
        } else {
            // first 값은 고정한채로 대신 움직여줄 변수 temp 를 만든다.
            ListNode temp = first;

            //  newNode의 지수가 큰경우
            if (temp.getExp() < exp) {
                newNode.link = temp;
                first = newNode;
                return;
            }

            // newNdde의 지수가 작아질때까지
            while (temp.getExp() > exp) {
                if (temp.link == null) {
                    temp.link = newNode;
                    return;
                }
                temp = temp.link;
            }

            if (temp.getExp() == exp) {
                temp.setCoef(temp.getCoef() + coef);
            } else {
                ListNode temp2 = new ListNode(temp.getCoef(), temp.getExp());
                temp2.link = temp.link;
                temp.setCoef(coef);
                temp.setExp(exp);
                temp.link = temp2;
            }

        }
    }

    public void printlist() {
        ListNode temp = first;
        while (temp != null) {
            if (temp.getExp() == 0) {
                System.out.print(temp.getCoef() + " ");
            } else {
                System.out.print(temp.getCoef() + "x^" + temp.getExp() + " " + "+" + " ");
            }
            temp = temp.link;
        }
        System.out.println();
    }

    public Polynomial add(Polynomial b) {
        Polynomial h = new Polynomial();
        ListNode p = this.first;
        ListNode q = b.first;
        while (p != null) {
            h.insertNode(p.getCoef(), p.getExp());
            p = p.link;
        }
        while (q != null) {
            h.insertNode(q.getCoef(), q.getExp());
            q = q.link;
        }
        return h;
    }

    public Polynomial multiply(Polynomial b){
        Polynomial h = new Polynomial();
        ListNode p = this.first;
        ListNode q = b.first;

        while(p != null){
            while(q != null){
                h.insertNode(p.getCoef()* q.getCoef(), p.getExp() + q.getExp());
                q = q.link;
            }
            q = b.first;
            p = p.link;
        }
        return h;
    }
}


public class PolynomialMulti {

    static void init(Polynomial a,int n){
        Random r =new Random();
        for (int i = 0; i < n ; i++) {
            a.insertNode(r.nextInt(10)+1,r.nextInt(5));
        }
    }

    static void print(Polynomial a){
        a.printlist();
    }


    public static void main(String[] args) {
        int n = 5;

        Polynomial a = new Polynomial();
        Polynomial b = new Polynomial();


        init(a,n);
        System.out.print("a : ");print(a);
        init(b,n);
        System.out.print("b : ");print(b);
//        h = a.add(b);
//        System.out.print("h : ");print(h);
        Polynomial h = a.multiply(b);
        System.out.print("h : ");print(h);

    }
}

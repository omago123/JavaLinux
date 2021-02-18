package com.tayuno.gradle;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Random;

class ListNode{
    private int data; // 데이터 저장 변수
    public ListNode link; // 다른 노드를 참조할 링크 노드

    public ListNode() {
        this.data = 0;
        this.link = null;
    }

    public ListNode(int data){
        this.data = data;
        this.link = null;
    }

    public ListNode(int data, ListNode link){
        this.data = data;
        this.link = link;
    }

    public int getData() {
        return this.data;
    }
}

 class LinkedList {

    private ListNode head; // ListNode 타입의 head 노드 인스턴스 변수

     // LinkedList 생성자
     public LinkedList() {
         head = null;  // head 노드 초기화
     }

     public ListNode getHead(){
         return this.head;
     }

     public void setHead(ListNode head){this.head=head;}

     // Node 삽입 (중간에 삽입)
     public void insertNode(ListNode preNode, int data) {
         ListNode newNode = new ListNode(data);  // 새로운 노드 생성

         newNode.link = preNode.link;
         preNode.link = newNode;
     }

     // Node 삽입 (마지막에 삽입)
     public void insertNode(int data) {
         ListNode newNode = new ListNode(data);

         if (head == null) {
             // head 노드가 null인 경우 새로운 노드를 참조하도록 함
             this.head = newNode;
         } else {
             // head 노드가  null이 아닌 경우 temp 노드가 head를 참조
             // tempNode는 마지막 노드를 찾아서 참조하기 위해 사용.
//             ListNode tempNode = head;
//
//             while (tempNode.link != null) {
//                 tempNode = tempNode.link; // 다음 노드를 참조
//             }

             // tempNode(마지막 노드) 의 link가 다음 노드를 참조하도록 함
             newNode.link = head;
             head = newNode;
         }
     }

     public void printList() {
         ListNode tempNode = this.head;  // tempNode에 head가 가리키는 첫번째 노드를 할당
//
//        // tempNode가 null이 아닐 때까지 반복하여 출력

         while (tempNode != null) {
             System.out.print(tempNode.getData()+" ");
             tempNode = tempNode.link;
         }
         System.out.println();
     }

     public LinkedList split(LinkedList b) {
         int count = 0;
         ListNode n = this.head;
         while (n != null) {
             n = n.link;
             count++;
         }

         int left = 2;
         int right = 1;
         int sum=left+right;
         while(count>left*2){
             left*=2;
             //left=left*2;
             right*=2;
             sum=left+right;
         }

         if(sum>count){
             sum=sum-count;
             left-=sum;
         }
//         if(sum<count){
//             sum=sum-count;
//             right+=sum;
//         }


        //System.out.println(left);
        // System.out.println(right);

         b.head = this.head;
         for (int i = 0; i < left-1; i++) {
             this.head = this.head.link;
         }

         LinkedList temp = new LinkedList();
         temp.head = this.head.link;
         this.head.link = null;
         b.printList();
        return temp;
     }

     public LinkedList merge(LinkedList b) {
         LinkedList sort = new LinkedList();

         //sort head 설정
         if (this.head.getData() < b.head.getData()) {
             sort.head = this.head;
             this.head = this.head.link;
         } else {
             sort.head = b.head;
             b.head = b.head.link;
         }

         ListNode temp = sort.head;

         while (this.head != null && b.head != null) {

             if (this.head.getData() < b.head.getData()) {
                 temp.link = this.head;
                 this.head = this.head.link;
                 temp= temp.link;
             } else {
                 temp.link = b.head;
                 b.head = b.head.link;
                 temp = temp.link;
             }
         }

         if (this.head != null) {
             temp.link = this.head;
         } else if (b.head != null) {
             temp.link = b.head;
         }
         return sort;
     }
 }

public class MergeSortEx {

    static void init(LinkedList[] a, int n) {
        for (int i = 0; i < n; i++) {
            a[i] = new LinkedList();
        }
        System.out.println();
    }


    static void getData(LinkedList[] l, int n, int m) {

        Random r = new Random();
        for (int i = 0; i < m; i++) {
            l[0].insertNode(r.nextInt(97));
        }
    }

    static int OneElement(LinkedList l) {
        int count = 0;
        ListNode n = l.getHead();
        while (n != null) {
            n = n.link;
            count++;
        }
        return count;
    }

    static void split(LinkedList[] l, int n) {

       // int k = 0;
        for (int i = 0; i < n - 1; i++) {
            if (OneElement(l[i]) > 1) {

                l[i*2+2] = l[i].split(l[i*2+1]);//1
                l[i].setHead(null);
            } else {
                continue;
            }
        }
    }

    static void merge(LinkedList[] l,int n) {
        int index = n-2;
        for(int i = 0; i<n-1; i++){
            l[index] =l[2*index+1].merge(l[2*index+2]);
            index--;
        }
        l[0].printList();
    }


    static void show(LinkedList[] l) {
        for (int i = 0; i < l.length; i++) {
            if (l[i].getHead() == null)
                continue;
            System.out.print("l[" + i + "] = ");
            l[i].printList();
            System.out.println();
        }
    }


    public static void main(String[] args) {

        int n = 100;
        int m = 20;
        LinkedList[] l = new LinkedList[n];

        init(l,n);
        getData(l,n,m);
        show(l);
        split(l,n);
        show(l);
        merge(l,m);
   }
}









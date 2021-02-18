package com.tayuno.gradle;

import java.util.Arrays;
import java.util.Random;

public class HeapSort2 {

    static void init(int[]A) {

        int num = 10;
        A[0] = num;
        Random r = new Random();
        for (int i = 1; i < num; i++) {
            A[i] = r.nextInt(30);
        }
    }

    static void makeHeap(int[]A){
        int root = 1;
        for (int i = 2; i < A.length; i++) {
            int c =i;

            while(c>1){
                // 부모위치 찾기
                root = c/2;
                // 루트 값보다 자식의 값이 크다면 값을 교환
                if (A[root] < A[c]) {
                    int temp =A[root];
                    A[root]=A[c];
                    A[c]=temp;
                }
                // 자식이 또 다른 부모가 된다.
                c= root;
            }
        }
    }
    static int deleteHeap(int [] a) {
        int temp = a[1];
        int n = a[0]; //개
        int x = a[n];
        int level = 1;

        while (level < n/2+1) {
                if (a[2 * level] < a[2 * level + 1]) {
                    if (x > a[2 * level + 1]) {
                        a[level] = x;
                        break;
                    }
                    a[level] = a[2 * level + 1];
                    level = 2 * level + 1;
                }else if(a[2 * level] >= a[2 * level + 1] ){
                    if (x > a[2 * level]) {
                        a[level] = x;
                        break;
                    }
                    a[level] = a[2 * level];
                    level = 2 * level;
                }
        }
        a[level] = x;
        a[0]--;
        return temp;
    }
    static  int[] sort(int[]heap) {
        int[] result = new int[heap.length];
        int n = heap[0];
        for (int i = 1; i <= n; i++) {
            result[i] = deleteHeap(heap);
           // System.out.println("result = " + result[i]);
        }
        result[0] = n;
        return result;
    }


    static void show(int[]A) {
        int num = 10;
        System.out.print("number : " +A[0]+ "ㅣ");
        for (int i = 1; i < num; i++) {
            System.out.print(A[i]+" ");
        }
        System.out.println();
        System.out.println("--------------------------------");
    }

    public static void main(String[] args) {
        int number = 50;
        int []heap = new int[number];
        int [] output;
        init(heap);
        show(heap);
        makeHeap(heap);
        show(heap);
        output = sort(heap);
        show(output);
    }
}


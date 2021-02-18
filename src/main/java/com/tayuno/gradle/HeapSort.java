package com.tayuno.gradle;

import java.util.Random;

public class HeapSort {

    static void init(int[]A) {

        Random r = new Random();
        for (int i = 1; i < A.length; i++) {
            A[i] = r.nextInt(30);
        }
    }

    static void makeHeap(int[]A){
        int root = 0;
        for (int i = 1; i < A.length; i++) {
            int c =i;
            while(c>0){
                // 부모위치 찾기
                root = (c-1)/2;
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
        static void sort(int[] heap) {
        // 크기를 줄여가면서 힙을 반복적으로 생성
        for (int i = heap.length - 1; i >= 0; i--) {
            // 루트값을 제일 밑으로
            int temp = heap[0];
            heap[0] = heap[i];
            heap[i] = temp;
            int c = 0;
            int root = 0;
            while (c < i) {
                // 자식위치 선정
                c = 2 * root + 1;
                // 자식들 중에 큰 값 찾기
                if (c < i - 1 && heap[c] < heap[c + 1]) c++;
                // 루트보다 자식 값이 작으면 교환
                if (c < i && heap[root] < heap[c]) {
                    temp = heap[root];
                    heap[root] = heap[c];
                    heap[c] = temp;
                }// 자식의 위치를 다시 부모에 넣어준다.
                root = c;
            }
        }
    }



    static void show(int[]A) {
        for (int i = 0; i < A.length; i++) {
            System.out.print(A[i]+" ");
        }
        System.out.println();
        System.out.println("--------------------------------");
    }

    public static void main(String[] args) {
        int number = 13;
        int []heap = new int[number];
        init(heap);
        show(heap);
        makeHeap(heap);
        show(heap);
        sort(heap);
        show(heap);
    }
}




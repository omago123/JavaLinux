package com.tuyano.gradle;

import java.util.Random;

public class Matrix{

     static void Init (int [][]a){
        Random random = new Random();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++)
                a[i][j] = random.nextInt(10);
        }
    }

     static int [][] add (int [][]a,int [][]b){
         int [][]add = new int[a.length][a[0].length];

         for (int i = 0; i < a.length; i++) {
             for (int j = 0; j < a[0].length; j++) {
                 if (a.length != b.length && a[0].length != b[0].length) {
                     System.out.println("서로의 행과열이 같지 않습니다.");
                     break;
                 }
                 add[i][j]= a[i][j]+b[i][j];
             }
         }
         return add;
     }

     static int [][] multi(int [][]a,int [][]b) {
         int [][]multi = new int[a.length][b[0].length];
         int mul;

         for (int i = 0; i < a.length; i++) {
             for (int j = 0; j < b[0].length; j++) {
                 mul=0;
                 for (int k = 0; k < a[0].length; k++) {
                     if (a[0].length != b.length) {
                         System.out.println("A의열과 B의행이 같지 않아 곱셈을 할 수 없습니다.");
                         break;
                     }
                     mul += a[i][k]*b[k][j];
                 }
                 multi[i][j]= mul;
             }
         }
         return multi;
     }

     static void print(int [][]a) {
         for (int i = 0; i < a.length; i++) {
             for (int j = 0; j < a[0].length; j++) {
                 System.out.print(a[i][j] + " ");
             }
             System.out.println();
         }
         System.out.println("----------------------");
     }


    public static void main(String[] args) {
         int x = 2;
         int y = 2;
         int z = 3;
         int a[][] = new int[x][y];
         int b[][] = new int[x][y];
         int c[][] = new int[x][z];
         int d[][] = new int[z][x];

         //Matrix m=new Matrix();  // 객체 생성

         Init(a);
         Init(b);
         Init(c);
         Init(d);


         print(a);
         print(b);
         print(add(a,b));

         print(c);
         print(d);
         print(multi(c,d));
    }
}


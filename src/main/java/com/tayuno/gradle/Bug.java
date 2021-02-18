package com.tuyano.gradle;

import java.util.Arrays;
import java.util.Random;

public class Bug {
    static void init(int[][]A,int len) {
        for (int i = 0; i < len; i++) {
            for(int j=0; j<len; j++)
                A[i][j] = 0;
        }

    }
    static void show(int[][]A,int len) {
        for (int i = 0; i < len; i++) {
            for(int j=0; j<len; j++)
                System.out.print(A[i][j]+"       ");
            System.out.println();
            System.out.println();
            System.out.println();
        }
        System.out.println("--------------------------------");
    }


    static int[][] move(int[][]A,int len) {

        Random random = new Random();

        int x = random.nextInt(len);
        int y = random.nextInt(len);

        while (true) {

            int num = random.nextInt(8);

            if (x == 0) {
                if (num == 1 || num == 2 || num == 3)
                    continue;
            }

            if (y == 0) {
                if (num == 3 || num == 4 || num == 5)
                    continue;
            }

            if (x == len - 1) {
                if (num == 5 || num == 6 || num == 7)
                    continue;
            }

            if (y == len - 1) {
                if (num == 0 || num == 1 || num == 7)
                    continue;
            }


            if (num == 0) {
                y = y + 1;
            } else if (num == 1) {
                x = x - 1;
                y = y + 1;
            } else if (num == 2) {
                x = x - 1;
            } else if (num == 3) {
                x = x - 1;
                y = y - 1;
            } else if (num == 4) {
                y = y - 1;
            } else if (num == 5) {
                x = x + 1;
                y = y - 1;
            } else if (num == 6) {
                x = x + 1;
            } else if (num == 7) {
                x = x + 1;
                y = y + 1;
            }
            System.out.println("x = " + x + ", y = " + y);
            A[x][y] += 1;

            int count = 0;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if(A[i][j]==0)
                        count++; // A[i][j] = 0일시 count는 1씩 증가
                }
            }
            if (count == 0) //A의 배열이 전체값이 0이 아니면 break
                break;
        }
        return A;
    }
    public static void main(String[] args) {
            int LEN = 10;
            int [][]A = new int[LEN][LEN];
            init(A,LEN);
            show(A,LEN);
            move(A,LEN);
            show(A,LEN);
    }
}

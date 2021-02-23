package test.gradle;

import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;

import java.io.BufferedOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BufferedIOEx {
    public static void main(String[] args){
        FileReader fin = null;
        int c;
        try {
            fin = new FileReader("./a.txt");
            BufferedOutputStream out = new BufferedOutputStream(System.out, 15);
            while (( c =fin.read()) != -1){
                out.write(c);
            }
            //  파일 데이터가 모두 출력된 상태
            new Scanner(System.in).nextLine(); // <Enter> 키 입력
            out.flush(); // 버퍼에 남아 있던 문자 모두 출력
            fin.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

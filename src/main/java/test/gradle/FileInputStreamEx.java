package test.gradle;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputStreamEx {
    public static void main(String[] args) {
        byte b[] = new byte [6];  // 비어 있는 byte 배열 
        try {
            FileInputStream fin = new FileInputStream("/home/omago/go/src/javacoding/test.out");
            int n=0, c;
            while((c = fin.read())!=-1){ // -1은 파일 끝(EOF)
                b[n] = (byte)c;  // 읽은 바이트를 배열에 저장
                n++;
            }
            // 배열 b[]의 바이트 값을 모두 화면에 출력
            System.out.println("/home/omago/go/src/javacoding/test.out 에서 읽은 배열을 출력합니다.");
            for (int i = 0; i <b.length; i++)
                System.out.print(b[i]+ " ");
            System.out.println();
            
            fin.close();
        }catch(IOException e){
            System.out.println("/home/omago/go/src/javacoding/test.out 에서 읽지 못했습니다. 경로명을 체크해보세요");
        }
    }
}

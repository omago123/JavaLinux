package test.gradle;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.util. *;



public class FileOutputStreamEx{
    public static void main(String[] args) {
        byte b[] = {7,51,3,4,-1,24};

        try {
            FileOutputStream fout = new FileOutputStream(("/home/omago/go/src/javacoding/test.out"));
            for(int i =0; i<b.length; i++)
                fout.write(b[i]); // 배열 b의 바이너리를 그대로 기록
            fout.close();
        }catch(IOException e ){
            System.out.println("/home/omago/go/src/javacoding/test.out 에 저장할 수 없었습니다. 경로명을 확인해 주세요.");
            return;
        }
        System.out.println("/home/omago/go/src/javacoding/test.out 에 저장하였습니다.");
    }
}
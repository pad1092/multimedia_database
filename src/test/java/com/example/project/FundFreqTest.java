package com.example.project;

import com.example.project.util.FundamentalFrequency;
import com.example.project.util.MainProcess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
public class FundFreqTest {
    @Autowired
    FundamentalFrequency fundFreqDetect;
    @Autowired
    MainProcess mainProcess;

    @Test
    void test(){
        String dirPath = "src/main/resources/static/wav/child";
//        String dirPath = "D:/Study/4-2/CSDL-DPT/dataset/women";
        int cnt = 0;
        double avg = 0;
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dirPath))){
            for (Path path : directoryStream){
                File file = new File(String.valueOf(path));
                System.out.println("-----File: " + file.getName() + "-----" );
                cnt += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

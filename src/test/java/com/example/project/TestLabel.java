package com.example.project;

import com.example.project.util.FindLabel;
import com.example.project.util.MainProcess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
public class TestLabel {
    @Autowired
    MainProcess mainProcess;
    @Autowired
    FindLabel findLabel;
    @Test
    void testChild(){
        String dirPath = "src/main/resources/static/wav/test/child";
        System.out.println("Test child label");
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dirPath))){
            for (Path path : directoryStream){
                System.out.println("------");
                File file = new File(String.valueOf(path));
                Entity entity = mainProcess.attributeProcess(file);
                findLabel.findLabel(entity.getFundFreq(), entity.getZcr());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMen(){
        String dirPath = "src/main/resources/static/wav/test/men";
        System.out.println("Test men label");

        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dirPath))){
            for (Path path : directoryStream){
                System.out.println("------");
                File file = new File(String.valueOf(path));
                Entity entity = mainProcess.attributeProcess(file);
                findLabel.findLabel(entity.getFundFreq(), entity.getZcr());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWomen(){
        String dirPath = "src/main/resources/static/wav/test/women";
        System.out.println("Test women label");

        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dirPath))){
            for (Path path : directoryStream){
                System.out.println("------");
                File file = new File(String.valueOf(path));
                Entity entity = mainProcess.attributeProcess(file);
                findLabel.findLabel(entity.getFundFreq(), entity.getZcr());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

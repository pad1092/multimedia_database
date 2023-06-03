package com.example.project;

import com.example.project.util.MainProcess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
public class SaveAlLFile {
    @Autowired
    MainProcess mainProcess;
    @Autowired
    EntityRepository repository;

    @Test
    void saveMenData(){
        String dirPath = "src/main/resources/static/wav/men";

        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dirPath))){
            for (Path path : directoryStream){
                File file = new File(String.valueOf(path));
                Entity entity = mainProcess.attributeProcess(file);
                entity.setLabel("MEN");
                System.out.println(entity);
                repository.save(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveWomenData(){
        String dirPath = "src/main/resources/static/wav/women";

        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dirPath))){
            for (Path path : directoryStream){
                File file = new File(String.valueOf(path));
                Entity entity = mainProcess.attributeProcess(file);
                entity.setLabel("WOMEN");
                System.out.println(entity);
                repository.save(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void saveChild(){
        String dirPath = "src/main/resources/static/wav/child";

        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dirPath))){
            for (Path path : directoryStream){
                File file = new File(String.valueOf(path));
                Entity entity = mainProcess.attributeProcess(file);
                entity.setLabel("CHILD");
                System.out.println(entity);
                repository.save(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

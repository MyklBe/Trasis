package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.controller.TradeController;
import pl.michal.converter.ConverterCSV;
import pl.michal.model.TradeModel;



import java.io.*;
import java.nio.file.Files;

import java.nio.file.Paths;



@RunWith(SpringRunner.class)
public class TradeControllerTest {

    private MultipartFile multipartFile;

    @Before
    public void setUp() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("TradeList.csv").getFile());
        byte[] content = Files.readAllBytes(Paths.get(file.getPath()));
        this.multipartFile = new MockMultipartFile(file.getName(), content);
    }


    @Test
    public void tradeUploadAndSaveTest(){
        TradeController tradeController = new TradeController();
        tradeController.fileUpload(multipartFile);

    }


}

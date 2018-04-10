package test.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class TradeControllerTest {

    private MultipartFile file;
    private MultipartFile wrongFile;

    @Before
    public void setUp() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("TradeList.csv").getFile());
        byte[] content = Files.readAllBytes(file.toPath());
        this.file = new MockMultipartFile(file.getName(), content);
    }

    @Test
    public void shouldUploadTradeListToDatabaseFromFile(){

    }

    @Test
    public void shouldReturnWrongFileException(){

    }

    @Test
    public void shouldReturnListOfConnectedTradesFromDatabaseBoundedWithDates() {

    }








}

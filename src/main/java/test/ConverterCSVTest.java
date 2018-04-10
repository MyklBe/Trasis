package test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.util.ConverterCSV;
import pl.michal.model.TradeModel;
import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
public class ConverterCSVTest {

    private MultipartFile file;
    private List<TradeModel> tradeModelList;

    @Before
    public void setUp(){
        ClassLoader classLoader = new ConverterCSVTest().getClass().getClassLoader();
        File simpleFile = new File(classLoader.getResource("TradeList.csv").getFile());
        try {
            this.file = new MockMultipartFile("TradeList.csv", Files.readAllBytes(simpleFile.toPath()));
            tradeModelList = ConverterCSV.parseCSVToTradeModelList(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfAllLinesConvertToTradeModelList() throws IOException {
        Assert.assertNotNull(tradeModelList);

        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        long countLines = br.lines().count() - 1;
        long count = tradeModelList.size();
        log.info("Number of lines in CSV: " + String.valueOf(count));

        Assert.assertTrue(count == countLines);
    }

    @Test
    public void checkIfWholeLineIsParsed() throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        Date date = formatDate.parse("20160609");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss.SSS");
        Date time = formatTime.parse("15:58:25.057");

        TradeModel tradeExample = new TradeModel();
        tradeExample.setActivityFee(0.00);
        tradeExample.setTime(time);
        tradeExample.setDate(date);
        tradeExample.setClearingFee(0.0507);
        tradeExample.setRegFee(0.00);
        tradeExample.setPrice(250.69);
        tradeExample.setGateway("EDGX");
        tradeExample.setDestination("ROUT");
        tradeExample.setGrossPL(6.00);
        tradeExample.setShare(300);
        tradeExample.setSide("Buy");
        tradeExample.setSymbol("BIIB.NQ");
        tradeExample.setGatewayFee(0.90);
        tradeExample.setState("Filled");

        log.info("Line from CSV : BIIB.NQ;Buy;250.69;300;6.00;Filled;EDGX;15:58:25.057;0.90;ROUT;0.00;20160609;0.0507;0.00;;;;;;;;;");

        Assert.assertTrue(tradeModelList.get(0).equals(tradeExample));

    }


}

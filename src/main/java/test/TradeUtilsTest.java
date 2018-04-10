package test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.util.ConverterCSV;
import pl.michal.model.TradeModel;
import pl.michal.util.TradeListUtils;
import java.io.File;
import java.nio.file.Files;
import java.util.*;


@RunWith(SpringRunner.class)
public class TradeUtilsTest {

    private MultipartFile file;
    private List<TradeModel> tradeModelList;

    @Before
    public void setUp() {
        ClassLoader classLoader = new ConverterCSVTest().getClass().getClassLoader();
        File simpleFile = new File(classLoader.getResource("TradeList.csv").getFile());
        try {
            this.file = new MockMultipartFile("TradeList.csv", Files.readAllBytes(simpleFile.toPath()));
            tradeModelList = ConverterCSV.parseCSVToTradeModelList(file);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void checkIfTheListIsSortedBySymbol() {
        TradeListUtils.sortTradesBySymbolDateTime(tradeModelList);
        TreeSet<String> sortedSymbols = new TreeSet<>(Arrays.asList("ABBV.NY", "ATVI.NQ", "BIIB.NQ", "EVHC.NY", "HSY.NY", "LNKD.NY", "MNST.NQ", "RH.NY", "SHPG.NQ", "TMH.NY"));
        Boolean isSorted = tradeModelList.stream()
                .map(trade -> trade.getSymbol())
                .distinct()
                .allMatch(symbol -> symbol.equals(sortedSymbols.pollFirst()));
        Assert.assertTrue(isSorted);
    }



    @Test
    public void testCreatingTradeFromModelList() {

    }


}

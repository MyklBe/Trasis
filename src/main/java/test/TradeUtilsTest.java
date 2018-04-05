package test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.michal.converter.ConverterCSV;
import pl.michal.model.TradeModel;
import pl.michal.util.TradeListUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


@RunWith(SpringRunner.class)
public class TradeUtilsTest {

    private File file;
    private List<TradeModel> tradeModelList;

    @Before
    public void setUp() {
        ClassLoader classLoader = new ConverterCSVTest().getClass().getClassLoader();
        this.file = new File(classLoader.getResource("TradeList.csv").getFile());
        try {
            tradeModelList = ConverterCSV.parseCSVToTradeModelList(file.toString());
        } catch (FileNotFoundException e) {
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

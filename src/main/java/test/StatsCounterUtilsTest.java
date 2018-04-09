package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.michal.converter.ConverterCSV;
import pl.michal.converter.StatsCounter;
import pl.michal.trade.Trade;
import pl.michal.model.StatsModel;
import pl.michal.model.TradeModel;
import pl.michal.util.StatsCounterUtils;
import pl.michal.util.TradeListUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
public class StatsCounterUtilsTest {

    private static List<Trade> tradeList;
    private static StatsCounterUtils statistics;

    @Before
    public void setUp() {
        ClassLoader classLoader = new ConverterCSVTest().getClass().getClassLoader();
        File file = new File(classLoader.getResource("TradeListShort.csv").getFile());
        List<TradeModel> tradeModelList = new LinkedList<>();
        try {
            tradeModelList = ConverterCSV.parseCSVToTradeModelList(file.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tradeList = TradeListUtils.ConnectTrades(tradeModelList);
        statistics = new StatsCounterUtils(tradeList);
    }

    @Test
    public void shouldReturnAverageDailyNet() {
        double avgDailyNet = truncateDouble(statistics.countAvgDayNet());
        Assert.assertTrue(avgDailyNet == 3.8);
    }

    @Test
    public void shouldReturnAverageDayVolume() {
        Assert.assertTrue(statistics.countAvgDayVol() == 910);
    }

    @Test
    public void shouldReturnAverageNetPerWinningTrade() {
        Assert.assertTrue(statistics.countAvgWinTrade() == 28);
    }

    @Test
    public void shouldReturnAverageNetPerLossingTrade() {
        Assert.assertTrue(statistics.countAvgLossTrade() == -13.05);
    }

    @Test
    public void shouldReturnTotalFees() {
        double totalFees = statistics.countTotalFees();
        totalFees = truncateDouble(totalFees);
        Assert.assertTrue(totalFees == (5.46));
    }

    @Test
    public void shouldReturnLargestGain() {
        Assert.assertTrue(statistics.countLargestGain() == 46.00);
    }

    @Test
    public void shouldReturnLargestLoss() {
        Assert.assertTrue(statistics.countLargestLoss() == -32.00);
    }

    @Test
    public void shouldReturnAverageNetPerTrade() {
        double returnPerTrade = truncateDouble(statistics.countAvgTradeGainLoss());
        Assert.assertTrue(returnPerTrade == 0.63);
    }

    @Test
    public void shouldReturnWinnersToAllRatio() {
        double returnPerTrade = truncateDouble(statistics.countWinTradesToAll());
        Assert.assertTrue(returnPerTrade == 0.33);
    }

    @Test
    public void shouldReturnLosersToAllRatio() {
        double returnPerTrade = truncateDouble(statistics.countLossTradesToAll());

        Assert.assertTrue(returnPerTrade == 0.67);

    }

    @Test
    public void shouldReturnAverageEquityExposurePerTrade() {
        double exposurePerTrade = truncateDouble(statistics.countAvgExposureTrade());
        Assert.assertTrue(exposurePerTrade == 15784.18);
    }

    @Test
    public void shouldReturnMaxEquityExposurePerTrade() {
        double maxExposure = truncateDouble(statistics.countMaxExposureTrade());
        Assert.assertTrue(maxExposure == 4600.0);
    }

    @Test
    public void shouldReturnTotalNumberOfTrades() {
        Assert.assertEquals(statistics.countTotalNumberTrades(), 6);
    }

    @Test
    public void shouldReturnLongestConsecutiveLosses() {
        Assert.assertEquals(statistics.countConsecutiveLosses(), 3);
    }

    @Test
    public void shouldReturnLongestConsecutiveWinners() {
        Assert.assertEquals(statistics.countConsecutiveGains(), 1);
    }

    @Test
    public void shouldCountReturnOnEquity(){
        Assert.assertTrue(truncateDouble(statistics.countReturnOnEquity() * 100) == 0.08 );
    }

    @Test
    public void shouldReturnTotalGains(){
        Assert.assertTrue(truncateDouble(statistics.countTotalGains()) == 3.8);
    }


    @Test
    public void shouldReturnStatsModelWithProperData() {
        StatsModel statistic = StatsCounter.createStatisticModelFromTradeData(tradeList);
    }

    private double truncateDouble(double d) {
        return BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


}

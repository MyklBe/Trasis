package pl.michal.converter;

import org.springframework.util.StopWatch;
import pl.michal.entity.TradeEntity;
import pl.michal.measurer.Measurement;
import pl.michal.model.StatsModel;
import pl.michal.util.StatsCounterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatsCounter {

    @Measurement
    public static StatsModel createStatisticModelFromTradeDataInParallel(List<TradeEntity> tradeList) {
        StatsCounterUtils statsCounter = new StatsCounterUtils(tradeList);
        StatsModel statsModel = new StatsModel();

        List<Callable<Void>> callables = new ArrayList<>();
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setAvgDayGainLoss(statsCounter.countAvgDayNet());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setAvgDayVol(statsCounter.countAvgDayVol());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setAvgExposureTrade(statsCounter.countAvgExposureTrade());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setAvgLossTrade(statsCounter.countAvgLossTrade());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setConsecutiveGains(statsCounter.countConsecutiveGains());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setAvgWinTrade(statsCounter.countAvgWinTrade());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setConsecutiveLosses(statsCounter.countConsecutiveLosses());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setLargestGain(statsCounter.countLargestGain());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setLargestLoss(statsCounter.countLargestLoss());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setMaxExposureTrade(statsCounter.countMaxExposureTrade());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setTotalNumberTrades(statsCounter.countTotalNumberTrades());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setLossToAllRatio(statsCounter.countLossTradesToAll());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setWinToAllRatio(statsCounter.countWinTradesToAll());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setTotalFees(statsCounter.countTotalFees());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setReturnOnEquity(statsCounter.countReturnOnEquity());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setAvgTradeGainLoss(statsCounter.countAvgTradeGainLoss());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });
        callables.add(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                statsModel.setTotalGains(statsCounter.countTotalGains());
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });

        StopWatch stopWatch = new StopWatch();
        StringBuilder sb = new StringBuilder();
        stopWatch.start();
        ExecutorService ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            ex.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        sb.append(stopWatch.getLastTaskInfo().getTimeSeconds());
        System.out.println(sb.toString());
        ex.shutdown();
        return statsModel;
    }

    @Measurement
    public static StatsModel createStatisticModelFromTradeData(List<TradeEntity> tradeList){
        StatsCounterUtils statsCounter = new StatsCounterUtils(tradeList);
        StatsModel statsModel = new StatsModel();

        StopWatch stopWatch = new StopWatch();
        StringBuilder sb = new StringBuilder();
        stopWatch.start();

        statsModel.setAvgDayGainLoss(statsCounter.countAvgDayNet());
        statsModel.setAvgDayVol(statsCounter.countAvgDayVol());
        statsModel.setAvgExposureTrade(statsCounter.countAvgExposureTrade());
        statsModel.setAvgLossTrade(statsCounter.countAvgLossTrade());
        statsModel.setConsecutiveGains(statsCounter.countConsecutiveGains());
        statsModel.setAvgWinTrade(statsCounter.countAvgWinTrade());
        statsModel.setConsecutiveLosses(statsCounter.countConsecutiveLosses());
        statsModel.setLargestGain(statsCounter.countLargestGain());
        statsModel.setLargestLoss(statsCounter.countLargestLoss());
        statsModel.setMaxExposureTrade(statsCounter.countMaxExposureTrade());
        statsModel.setTotalNumberTrades(statsCounter.countTotalNumberTrades());
        statsModel.setLossToAllRatio(statsCounter.countLossTradesToAll());
        statsModel.setWinToAllRatio(statsCounter.countWinTradesToAll());
        statsModel.setTotalFees(statsCounter.countTotalFees());
        statsModel.setReturnOnEquity(statsCounter.countReturnOnEquity());
        statsModel.setAvgTradeGainLoss(statsCounter.countAvgTradeGainLoss());
        statsModel.setTotalGains(statsCounter.countTotalGains());

        stopWatch.stop();
        sb.append(stopWatch.getLastTaskInfo().getTimeSeconds());
        System.out.println(sb.toString());
        return statsModel;
    }

}

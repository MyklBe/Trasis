package pl.michal.util;


import pl.michal.entity.TradeEntity;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StatsCounterUtils {

    private final List<TradeEntity> trades = new LinkedList<>();

    public StatsCounterUtils(List<TradeEntity> tradeEntityList) {
        trades.addAll(tradeEntityList);
    }

    public double countAvgDayNet() {
        long countDays = trades.stream().map(TradeEntity::getDate).distinct().count();
        double sumOfNet = trades.stream().map(t -> t.getGross()).mapToDouble(net -> net.doubleValue()).sum();
        double avgDayNet = sumOfNet / countDays;
        return avgDayNet;
    }

    public double countAvgDayVol() {
        long countDays = trades.stream().map(TradeEntity::getDate).distinct().count();
        long sumOfVolume = trades.stream().map(t -> t.getShares()).mapToLong(shares -> shares.longValue()).sum();
        return sumOfVolume / countDays;
    }

    public double countAvgWinTrade() {
        double sumWinningTrades = trades.stream()
                .mapToDouble(t -> t.getGross())
                .filter(gross -> gross > 0)
                .sum();
        long countWinTrades = trades.stream().filter(tradeEntity -> tradeEntity.getGross() > 0).count();
        return sumWinningTrades / countWinTrades;
    }

    public double countAvgLossTrade() {
        double sumWinningTrades = trades.stream()
                .mapToDouble(t -> t.getGross())
                .filter(gross -> gross <= 0)
                .sum();
        long countLossTrades = trades.stream().filter(tradeEntity -> tradeEntity.getGross() <= 0).count();
        return sumWinningTrades / countLossTrades;
    }

    public double countTotalFees() {
        return trades.stream().mapToDouble(t -> t.getFees()).sum();
    }

    public double countLargestGain() {
        return trades.stream().mapToDouble(t -> t.getGross()).max().getAsDouble();
    }

    public double countLargestLoss() {
        return trades.stream().mapToDouble(t -> t.getGross()).min().getAsDouble();
    }

    public double countAvgTradeGainLoss() {
        double sumTradesNet = trades.stream()
                .mapToDouble(t -> t.getGross())
                .sum();
        return sumTradesNet / trades.size();
    }

    public double countWinTradesToAll() {
        double winTrades = trades.stream().filter(t -> t.getGross() > 0).count();
        double allTrades = countTotalNumberTrades();
        double tradeAccuracy = (winTrades / allTrades);

        return tradeAccuracy;
    }

    public double countLossTradesToAll() {
        double lossTrades = trades.stream().filter(t -> t.getGross() <= 0).count();
        double allTrades = countTotalNumberTrades();
        double tradeAccuracy = lossTrades / allTrades;

        return tradeAccuracy;
    }

    public double countAvgExposureTrade() {
        double totalMoneyVolume = trades.stream().mapToDouble(t -> t.getShares() * t.getPriceEntry()).sum();
        long totalTrades = trades.size();

        return totalMoneyVolume / totalTrades;
    }

    public synchronized double countMaxExposureTrade() {
        return trades.stream().mapToDouble(t -> (t.getGross() * t.getShares())).max().getAsDouble();
    }

    public double countReturnOnEquity() {
        double totalGross = trades.stream().mapToDouble(t -> t.getGross()).sum();
        double maxExposure = countMaxExposureTrade();

        return totalGross / maxExposure;
    }

    public synchronized int countTotalNumberTrades() {
        return trades.size();
    }


    public int countConsecutiveLosses() {
        trades.sort(Comparator.comparing(TradeEntity::getDate).thenComparing(Comparator.comparing(TradeEntity::getTimeOut)));
        int consLoss = 0;
        int counter = 0;
        for (TradeEntity trade : trades) {
            if (trade.getGross() <= 0) {
                counter++;
                if (counter > consLoss) {
                    consLoss = counter;
                }
            } else {
                counter = 0;
            }
        }
        return consLoss;
    }

    public int countConsecutiveGains() {
        trades.sort(Comparator.comparing(TradeEntity::getDate).thenComparing(Comparator.comparing(TradeEntity::getTimeOut)));
        int consGain = 0;
        int counter = 0;
        for (TradeEntity trade : trades) {
            if (trade.getGross() > 0) {
                counter++;
                if (counter > consGain) {
                    consGain = counter;
                }
            } else {
                counter = 0;
            }
        }
        return consGain;
    }

    public double countTotalGains() {
        double totalGains = trades.stream().mapToDouble(t -> t.getGross()).sum();
        return totalGains;
    }

    public Map<String, Double> countVolForEachStock() {
//        Map<String, Double> volume = trades.stream()
//                .map(t -> t.getSymbol())
//                .distinct()
//                .collect(Collectors.groupingBy(Function.identity(), Collector.of()))
//                        );


        return null;
    }

}

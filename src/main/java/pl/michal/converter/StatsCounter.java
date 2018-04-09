package pl.michal.converter;


import pl.michal.trade.Trade;

import pl.michal.model.StatsModel;
import pl.michal.util.StatsCounterUtils;

import java.util.List;


public class StatsCounter {


    public static StatsModel createStatisticModelFromTradeData(List<Trade> tradeList){
        StatsCounterUtils statsCounter = new StatsCounterUtils(tradeList);
        StatsModel statsModel = new StatsModel();

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

        return statsModel;
    }

}

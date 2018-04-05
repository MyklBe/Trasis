package pl.michal.model;


import lombok.Data;

@Data
public class StatsModel {
    private double avgDayGainLoss;
    private double avgDayVol;
    private double avgWinTrade;
    private double avgLossTrade;
    private double totalGains;
    private double totalFees;
    private double largestGain;
    private double largestLoss;
    private double avgTradeGainLoss;
    private double WinToAllRatio;
    private double LossToAllRatio;
    private double avgExposureTrade;
    private double maxExposureTrade;
    private double returnOnEquity;
    private int totalNumberTrades;
    private int consecutiveLosses;
    private int consecutiveGains;
}

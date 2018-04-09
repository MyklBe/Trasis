package pl.michal.util;


import pl.michal.trade.Trade;
import pl.michal.model.TradeModel;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class TradeListUtils {

    public static List<Trade> ConnectTrades(List<TradeModel> tradeList){
        sortTradesBySymbolDateTime(tradeList);
        List<Trade> entityList = new LinkedList<>();
        int share = 0;
        LinkedList<TradeModel> tempList = new LinkedList<>();
        for(int i = 0; i < tradeList.size(); i++) {
            if((share != 0 || tempList.size() == 0) && (tradeList.get(i).getSide().equals("ShortSell") || tradeList.get(i).getSide().equals("Sell"))){
                share += (tradeList.get(i).getShare() * -1);
                tempList.add(tradeList.get(i));
            }
            else if ((share != 0 || tempList.size() == 0)  && (tradeList.get(i).getSide().equals("Buy"))) {
                share += tradeList.get(i).getShare();
                tempList.add(tradeList.get(i));
            }
            if(share == 0 && tempList.size() > 0) {
                entityList.add(createNewTrade(tempList));
                tempList.clear();
            }
        }
        return entityList;
    }

    public static Trade createNewTrade(LinkedList<TradeModel> tempList){
        Trade trade = new Trade();
        trade.setSymbol(tempList.getFirst().getSymbol());
        trade.setPriceEntry(tempList.getFirst().getPrice());
        trade.setPriceOut(tempList.getLast().getPrice());
        trade.setDate(tempList.getFirst().getDate());
        trade.setTimeEntry(tempList.getFirst().getTime());
        trade.setTimeOut(tempList.getLast().getTime());
        trade.setSide(tempList.getFirst().getSide());
        double fees = 0;
        int shares = 0;
        double gross = 0;
        for (int i = 0; i < tempList.size(); i++) {
            shares += tempList.get(i).getShare();
            fees += tempList.get(i).getActivityFee()+ tempList.get(i).getClearingFee() + tempList.get(i).getGatewayFee()+tempList.get(i).getRegFee();
            gross += tempList.get(i).getGrossPL();
        }
        trade.setShares(shares/2);
        trade.setFees(fees);
        trade.setGross(gross);
        return trade;
    }

    public static void sortTradesBySymbolDateTime(List<TradeModel> tradeModelList){
        Comparator<TradeModel> comparator = Comparator.comparing(TradeModel::getSymbol)
                .thenComparing(TradeModel::getDate).thenComparing(TradeModel::getTime);
        tradeModelList.sort(comparator);
    }




}

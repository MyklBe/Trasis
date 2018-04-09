package pl.michal.trade;

import lombok.Data;

import java.util.Date;


@Data
public class Trade {

    private String symbol;
    private String side;
    private Date date;
    private Date timeEntry;
    private Date timeOut;
    private double priceEntry;
    private double priceOut;
    private double fees;
    private int shares;
    private double gross;
    private int userNr;

}

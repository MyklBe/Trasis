package pl.michal.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;



@Data
public class TradeEntity {

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

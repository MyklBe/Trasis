package pl.michal.model;


import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.conversions.DateConversion;
import com.univocity.parsers.conversions.DoubleConversion;
import lombok.Data;


import javax.persistence.*;
import java.util.Date;



@Data
@Entity
@Table(name = "TradeModel")
public class TradeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    @Parsed(field = "Symbol")
    private String symbol;
    @Parsed(field = "Side")
    private String side;
    @Convert(conversionClass = DoubleConversion.class)
    @Parsed(field = "Price")
    private double price;
    @Parsed(field = "Share")
    private int share;
    @Convert(conversionClass = DoubleConversion.class)
    @Parsed(field = "GrossPL")
    private double grossPL;
    @Convert(conversionClass = DateConversion.class, args = "HH:mm:ss.SSS")
    @Parsed(field = "Time")
    private Date time;
    @Convert(conversionClass = DateConversion.class, args = "yyyyMMdd")
    @Parsed(field = "Date")
    private Date date;
    @Convert(conversionClass = DoubleConversion.class)
    @Parsed(field = "GatewayFee")
    private double gatewayFee;
    @Parsed(field = "Gateway")
    private String gateway;
    @Parsed(field = "Destination")
    private String destination;
    @Convert(conversionClass = DoubleConversion.class)
    @Parsed(field = "RegFee")
    private double regFee;
    @Convert(conversionClass = DoubleConversion.class)
    @Parsed(field = "ClearingFee")
    private double clearingFee;
    @Convert(conversionClass = DoubleConversion.class)
    @Parsed(field = "ActivityFee")
    private double activityFee;
    @Parsed(field = "State")
    private String state;
}

package test.databaseTests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.michal.model.TradeModel;
import pl.michal.repository.TradeRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class})
public class RepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void testSaveTradeModel() throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        Date date = formatDate.parse("20160609");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss.SSS");
        Date time = formatTime.parse("15:58:25.057");

        TradeModel tradeExample = new TradeModel();
        tradeExample.setActivityFee(0.00);
        tradeExample.setTime(time);
        tradeExample.setDate(date);
        tradeExample.setClearingFee(0.0507);
        tradeExample.setRegFee(0.00);
        tradeExample.setPrice(250.69);
        tradeExample.setGateway("EDGX");
        tradeExample.setDestination("ROUT");
        tradeExample.setGrossPL(6.00);
        tradeExample.setShare(300);
        tradeExample.setSide("Buy");
        tradeExample.setSymbol("BIIB.NQ");
        tradeExample.setGatewayFee(0.90);
        tradeExample.setState("Filled");

        tradeRepository.save(tradeExample);
        Assert.assertNotNull(tradeExample.getId());

        TradeModel fetchedTrade = tradeRepository.findOne(tradeExample.getId());

        Assert.assertNotNull(fetchedTrade);
        Assert.assertEquals(tradeExample, fetchedTrade);
    }




}

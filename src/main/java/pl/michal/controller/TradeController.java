package pl.michal.controller;


import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.util.ConverterCSV;
import pl.michal.model.TradeModel;
import pl.michal.repository.TradeRepository;
import pl.michal.trade.Trade;
import pl.michal.util.TradeListUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/")
public class TradeController {

    private static final Logger log = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeRepository tradeRepository;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile file) {

        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!fileExtension.equals(".cvs")) {
            return "File of invalid type, should be .cvs is ." + fileExtension;
        }

//        nawet jesli plik jest csv to musi zawierac odpowiednie dane, trzeba sprawdzic...jak?
        try {
            List<TradeModel> tradeModelList = ConverterCSV.parseCSVToTradeModelList(file);
            tradeRepository.save((Iterable) tradeModelList);
            log.info("file uploaded and saved into database " + LocalDate.now().toString());
            return "SUCCESS";
        } catch (IOException e) {
            return "Failed to upload file" + e.getMessage();
        }


//        ma wziasc file, sprawdzic czy to csv (a najlepiej jaki to plik i odpowiednio dobrac metode do parsowania), na razie mozna zrobic prostego case'a
//        sparsowac i sprawdzic daty czy juz taki nie ma, jesli sa to nie zapisuje, jesli nie ma to zapisuje do bazy
//        moze sprawdz tylko daty z pliku, pierwsza i ostatnia, czy sa w bazie danych

//        Jesli mialbym postawic apke na serwerze, to mozna darowac sobie baze danych niech zapisuje plik i wysweitla dane wwslanie z niego, codziennie o polnocy czysci resourses/csv

    }


    @RequestMapping("/showByDate")
    public List<Trade> showTradesByDate(@RequestParam(name = "startDate")String from,
                                        @RequestParam(name = "endDate") String to) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date startDate = sdf.parse(from);
        Date endDate = sdf.parse(to);

        List<TradeModel> tradeModelList = tradeRepository.findAllByDateBetween(startDate,endDate);
        List<Trade> trades = TradeListUtils.ConnectTrades(tradeModelList);
        return trades;
    }

    @RequestMapping("/symbol")
    public List<Trade> showBySymbol(@RequestParam(name = "symbol") String symbol){
        List<TradeModel> tradeModelList = tradeRepository.findAllBySymbol(symbol);
        List<Trade> trades = TradeListUtils.ConnectTrades(tradeModelList);
        return trades;
    }



}

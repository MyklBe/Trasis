package pl.michal.controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.converter.ConverterCSV;
import pl.michal.entity.TradeEntity;
import pl.michal.model.TradeModel;
import pl.michal.repository.TradeRepository;
import pl.michal.util.TradeListUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/")
public class TradeController {
    private final String path = "C:\\Users\\MLBA\\Downloads\\myklbe-micbarupgda-6c4105ddc0f7\\myklbe-micbarupgda-6c4105ddc0f7\\Project\\Backend\\src\\main\\resources";

    @Autowired
    private TradeRepository tradeRepository;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file")MultipartFile file){
        String name = file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + name)));
            stream.write(bytes);
            stream.close();

            List<TradeModel> tradeModelList = ConverterCSV.parseCSVToTradeModelList(path + name);
            List<TradeEntity> tradeList = TradeListUtils.ConnectTrades(tradeModelList);

            FileWriter fileWriter = new FileWriter(path + "\\JSONTradesRepo\\" + name);
            fileWriter.write(JSONArray.toJSONString(tradeList));

            tradeRepository.save((Iterable) tradeList);
            return "Success";
        } catch (IOException e) {
            return "FAIL" + e.getMessage();
        }

    }

    @RequestMapping("/showByDate")
    public List<TradeEntity> showTradesByDate(@RequestParam(name = "startDate")String from,
                                             @RequestParam(name = "endDate") String to) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date startDate = new Date();
        startDate = sdf.parse(from);
        Date endDate = new Date();
        endDate = sdf.parse(to);
        List<TradeEntity> tradeEntities = tradeRepository.findAllByDateBetween(startDate,endDate);
        return tradeEntities;
    }
    @RequestMapping("/symbol")
    public List<TradeEntity> showBySymbol(@RequestParam(name = "symbol") String symbol){
        return tradeRepository.findAllBySymbol(symbol);
    }


}

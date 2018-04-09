package pl.michal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.converter.ConverterCSV;
import pl.michal.model.TradeModel;
import pl.michal.repository.TradeRepository;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/")
public class TradeController {
    private final String path = "C:\\Users\\MLBA\\Downloads\\myklbe-micbarupgda-6c4105ddc0f7\\myklbe-micbarupgda-6c4105ddc0f7\\Project\\Backend\\src\\main\\resources";

    @Autowired
    private TradeRepository tradeRepository;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        String name = file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + name)));
            stream.write(bytes);
            stream.close();

            List<TradeModel> tradeModelList = ConverterCSV.parseCSVToTradeModelList(path + name);

            List<TradeModel> tradeList = tradeModelList.stream().map(t -> (TradeModel) t).collect(Collectors.toList());


            tradeRepository.save((Iterable) tradeList);
            return "Success";
        } catch (IOException e) {
            return "FAIL" + e.getMessage();
        }

    }
//
//    @RequestMapping("/showByDate")
//    public List<Trade> showTradesByDate(@RequestParam(name = "startDate")String from,
//                                        @RequestParam(name = "endDate") String to) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Date startDate = new Date();
//        startDate = sdf.parse(from);
//        Date endDate = new Date();
//        endDate = sdf.parse(to);
//        List<Trade> tradeEntities = tradeRepository.findAllByDateBetween(startDate,endDate);
//        return tradeEntities;
//    }
//    @RequestMapping("/symbol")
//    public List<Trade> showBySymbol(@RequestParam(name = "symbol") String symbol){
//        return tradeRepository.findAllBySymbol(symbol);
//    }


}

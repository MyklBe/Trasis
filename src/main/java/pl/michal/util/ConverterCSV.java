package pl.michal.util;


import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.model.TradeModel;


import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConverterCSV {

    public static List<TradeModel> parseCSVToTradeModelList(MultipartFile file) throws IOException {
        BeanListProcessor<TradeModel> rowProcessor = new BeanListProcessor<>(TradeModel.class);
        CsvParserSettings parSet = new CsvParserSettings();

        parSet.setProcessor(rowProcessor);
        parSet.setHeaderExtractionEnabled(true);
        parSet.setDelimiterDetectionEnabled(true, ';');

        CsvParser parser = new CsvParser(parSet);

        parser.parse(file.getInputStream());

        List<TradeModel> tradeList = rowProcessor.getBeans();
        parser.stopParsing();
        return tradeList;
    }


}

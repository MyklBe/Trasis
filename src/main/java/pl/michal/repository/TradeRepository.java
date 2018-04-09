package pl.michal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import pl.michal.model.TradeModel;

import java.util.Date;
import java.util.List;


@Repository
@Transactional
public interface TradeRepository extends JpaRepository<TradeModel, Long> {

    List<TradeModel> findAllByDateBetween (Date startDate, Date endDate);

    List<TradeModel> findAllBySymbol(String symbol);

}

package pl.michal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.michal.entity.TradeEntity;
import pl.michal.model.TradeModel;

import java.util.Date;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<TradeModel, Long> {

    List<TradeEntity> findAllByDateBetween (Date startDate, Date endDate);

    List<TradeEntity> findAllBySymbol(String symbol);

}

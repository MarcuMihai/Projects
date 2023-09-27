package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.ConsumptionHistory;
import ro.tuc.ds2020.entities.SensorEntity;

import java.util.List;
import java.util.UUID;

public interface ConsumptionHistoryRepository extends JpaRepository<ConsumptionHistory, UUID> {
    List<ConsumptionHistory> findByReadSensor(SensorEntity sensor);
}

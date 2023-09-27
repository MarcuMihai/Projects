package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.ConsumptionHistoryDTO;
import ro.tuc.ds2020.entities.ConsumptionHistory;

public class ConsumptionHistoryBuilder {
    public ConsumptionHistoryBuilder() {
    }
    public static ConsumptionHistoryDTO toConsumptionHistoryDTO(ConsumptionHistory consumptionHistory){
        return new ConsumptionHistoryDTO(consumptionHistory.getId(),consumptionHistory.getReadSensor(), consumptionHistory.getValue(), consumptionHistory.getTimestamp());
    }

    public static ConsumptionHistory toEntity(ConsumptionHistoryDTO consumptionHistoryDTO){
        return new ConsumptionHistory(
                consumptionHistoryDTO.getId(),
                consumptionHistoryDTO.getReadSensor(),
                consumptionHistoryDTO.getValue(),
                consumptionHistoryDTO.getTimestamp()
        );
    }
}

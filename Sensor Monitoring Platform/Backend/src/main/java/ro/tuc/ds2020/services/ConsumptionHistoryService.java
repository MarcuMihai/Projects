package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.ConsumptionHistoryDTO;
import ro.tuc.ds2020.dtos.builders.ConsumptionHistoryBuilder;
import ro.tuc.ds2020.entities.ConsumptionHistory;
import ro.tuc.ds2020.repositories.ConsumptionHistoryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConsumptionHistoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumptionHistoryService.class);
    private final ConsumptionHistoryRepository consumptionRepository;

    public ConsumptionHistoryService(ConsumptionHistoryRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    public List<ConsumptionHistoryDTO> findEntries() {
        List<ConsumptionHistory> entriesList = consumptionRepository.findAll();
        return entriesList.stream()
                .map(ConsumptionHistoryBuilder::toConsumptionHistoryDTO)
                .collect(Collectors.toList());
    }

    public ConsumptionHistoryDTO findEntryById(UUID id) {
        Optional<ConsumptionHistory> prosumerOptional = consumptionRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Entry with id {} was not found in db", id);
            throw new ResourceNotFoundException(ConsumptionHistory.class.getSimpleName() + " with id: " + id);
        }
        return ConsumptionHistoryBuilder.toConsumptionHistoryDTO(prosumerOptional.get());
    }

    public UUID insert(ConsumptionHistoryDTO consumptionHistoryDTO) {
        ConsumptionHistory entry = ConsumptionHistoryBuilder.toEntity(consumptionHistoryDTO);
        entry = consumptionRepository.save(entry);
        return entry.getId();
    }

    public UUID delete(ConsumptionHistoryDTO consumptionHistoryDTO) {
        ConsumptionHistory entry = ConsumptionHistoryBuilder.toEntity(consumptionHistoryDTO);
        consumptionRepository.delete(entry);
        return entry.getId();
    }

    public UUID update(ConsumptionHistoryDTO consumptionHistoryDTO) {
        ConsumptionHistory entry = ConsumptionHistoryBuilder.toEntity(consumptionHistoryDTO);
        entry = consumptionRepository.save(entry);
        return entry.getId();
    }

    public List<ConsumptionHistoryDTO> findEntryByUser(UUID userId){
        List<ConsumptionHistory> entriesList = consumptionRepository.findAll();
        return entriesList.stream()
                .filter(x -> x.getReadSensor().getDevice().getUser().getId().equals(userId))
                .map(ConsumptionHistoryBuilder::toConsumptionHistoryDTO)
                .collect(Collectors.toList());
    }
}

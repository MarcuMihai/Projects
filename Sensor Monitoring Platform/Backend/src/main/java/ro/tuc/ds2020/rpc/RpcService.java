package ro.tuc.ds2020.rpc;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.ConsumptionHistoryDTO;
import ro.tuc.ds2020.services.ConsumptionHistoryService;

import java.util.List;
import java.util.UUID;

@AutoJsonRpcServiceImpl
@Service
public class RpcService implements RpcInterface {

    private final ConsumptionHistoryService consumptionService;

    @Autowired
    public RpcService(ConsumptionHistoryService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @Override
    public ResponseEntity<List<ConsumptionHistoryDTO>> getReadValues(UUID userId) {
        return new ResponseEntity<>(consumptionService.findEntryByUser(userId), HttpStatus.OK);
    }
}

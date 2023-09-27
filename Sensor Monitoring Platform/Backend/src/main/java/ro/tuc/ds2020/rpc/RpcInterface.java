package ro.tuc.ds2020.rpc;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import org.springframework.http.ResponseEntity;
import ro.tuc.ds2020.dtos.ConsumptionHistoryDTO;

import java.util.List;
import java.util.UUID;

@JsonRpcService("/rpc")
public interface RpcInterface {
    ResponseEntity<List<ConsumptionHistoryDTO>> getReadValues(@JsonRpcParam(value = "userId") UUID userId);
}

package ro.tuc.ds2020.reader;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ConfigurableApplicationContext;
import ro.tuc.ds2020.controllers.ConsumptionHistoryController;
import ro.tuc.ds2020.dtos.ConsumptionHistoryDTO;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.UUID;

public class Reader {
    private final static String QUEUE_NAME = "sensor_values";
    public void getData(ConfigurableApplicationContext context) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://tkkyazrm:XCNAqvjWn6E7vpLA-8Hk1MLEqm0__uba@roedeer.rmq.cloudamqp.com/tkkyazrm");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            ConsumptionHistoryDTO consumptionHistoryDTO=new ConsumptionHistoryDTO();
            try {
                JSONObject json = new JSONObject(message);
                consumptionHistoryDTO.setValue(Float.parseFloat(json.get("value").toString()));
                consumptionHistoryDTO.setTimestamp(new Date((Long) json.get("timestamp")));
                context.getBean(ConsumptionHistoryController.class).insertProsumer(UUID.fromString(json.get("sensor_id").toString()), consumptionHistoryDTO);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}

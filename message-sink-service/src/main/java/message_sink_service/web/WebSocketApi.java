package message_sink_service.web;



import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebSocketApi {

	@MessageMapping("/sendMessage")
	@SendTo("/queue/vehicles")
	public String sendMessage(String message) throws Exception {
		return message;
	}
}

package cn.web.rabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQSender
{
  @Autowired
  private RabbitTemplate template;
  
  public void Sender(int userId, int artId)
  {
    SubMessage message = new SubMessage();
    message.setArtID(String.valueOf(artId));
    message.setUserID(String.valueOf(userId));
    ObjectMapper mapper = new ObjectMapper();
    try
    {
      this.template.convertAndSend("queue-subscribe", mapper.writeValueAsString(message));
    }
    catch (JsonProcessingException e)
    {
      e.printStackTrace();
    }
  }
}

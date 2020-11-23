package cn.how2j;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生成者
 */
public class TestProducer {
    public final static String EXCHANGE_NAME="fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
    	RabbitMQUtil.checkServer();
    	
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
        //链接
        Connection connection = factory.newConnection();
        
        //通道
        Channel channel  = connection.createChannel();
        
        
        channel .exchangeDeclare(EXCHANGE_NAME, "fanout");
        		for(int i=0;i<=30;i++){
        			String message="哈哈"+i;
        			channel .basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));		
        	        System.out.println("发送消息： " + message);
        		}
        
        		channel.close();
        		connection.close();
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
        		
    }
}
package cn.how2j;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import cn.hutool.core.util.RandomUtil;

public class TestCustomer {
    public final static String EXCHANGE_NAME="fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
    	//为当前消费者取随机名
    	String name = "consumer-"+ RandomUtil.randomString(5);
    	
    	//判断服务器是否启动
    	RabbitMQUtil.checkServer();
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost("localhost");
        //创建一个新的连接
        Connection newConnection = factory.newConnection();
        //通道
        Channel createChannel = newConnection.createChannel();
        createChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //获取队列
        String queue = createChannel.queueDeclare().getQueue();
        //绑定
        createChannel.queueBind(queue, EXCHANGE_NAME, "");
        
        
        Consumer  consumer=new DefaultConsumer(createChannel){
        		@Override
        		public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
        				throws IOException {
        			String message=new String(body,"UTF-8");
        			
        			  		}
          };
          createChannel.basicConsume(queue, true, consumer);
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope,
//                                       AMQP.BasicProperties properties, byte[] body)
//                    throws IOException {
//                String message = new String(body, "UTF-8");
//            }
//        };
//        //自动回复队列应答 -- RabbitMQ中的消息确认机制
//        channel.basicConsume(queueName, true, consumer);
//    }
}}
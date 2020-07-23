package com.zhangbao.eblog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sunny
 * @create 2020-07-2020/7/21-17:51
 */
@Configuration
public class RabbitMpConfig {

    public final static String ES_QUEUE = "es_queue";//队列名称
    public final static String ES_EXCHANGE = "es_exchange";//交换机名称
    public final static String ES_BIND_KEY = "es_bind_KEY";


    @Bean
    public Queue esQueue(){
        return new Queue(ES_QUEUE);
    }

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(ES_EXCHANGE);
    }

    //绑定交换机和队列
    @Bean
    Binding binding(Queue queue,DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ES_BIND_KEY);
    }

}

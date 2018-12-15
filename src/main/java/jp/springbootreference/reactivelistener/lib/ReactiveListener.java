package jp.springbootreference.reactivelistener.lib;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ErrorHandler;


public class ReactiveListener {

    private SimpleMessageListenerContainer simpleMessageListenerContainer;

    public ReactiveListener(String host){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setPublisherConfirms(true);
        simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setMessageListener(message -> { });
        simpleMessageListenerContainer.setMessageConverter(defaultMessageConverter());
    }

    public void setQueue(String queueName){
        simpleMessageListenerContainer.setQueueNames(queueName);
    }

    public void setMessageConverter(Jackson2JsonMessageConverter converter){
        simpleMessageListenerContainer.setMessageConverter(converter);
    }

    public void listen(){
        simpleMessageListenerContainer.start();
    }

    public void stop(){
        simpleMessageListenerContainer.stop();
    }


    public void setCallBackMethod(ListenerInterface listenerInterface){
        MessageListener messageListener = message -> listenerInterface.callBack(message);
        simpleMessageListenerContainer.setMessageListener(messageListener);
    }

    public void setErrorHandler(ListenerInterface listenerInterface){
        ErrorHandler errorHandler = throwable -> listenerInterface.errorCallBack(throwable);
        simpleMessageListenerContainer.setErrorHandler(errorHandler);
    }

    private Jackson2JsonMessageConverter defaultMessageConverter(){
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .dateFormat(new StdDateFormat())
                .build();
        Jackson2JsonMessageConverter jackson2JsonMessageConverter
                = new Jackson2JsonMessageConverter(objectMapper);
        return jackson2JsonMessageConverter;

    }

}

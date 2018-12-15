# reactiveListener

![apache licensed](https://img.shields.io/badge/License-Apache_2.0-d94c32.svg)
![Java](https://img.shields.io/badge/Language-Java-f88909.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/jp.spring-boot-reference/reactive-listener/badge.svg)](https://maven-badges.herokuapp.com/maven-central/jp.spring-boot-reference/reactive-listener)


reactiveListener is Objective Queue Subscriber.

## Usage

### initialize and subscribe
you can use `ReactiveListener` like below.
```Java
// initialize object with hostname.
ReactiveListener reactiveListener = new ReactiveListener("localhost");

// set queuename you want to subscribe
reactiveListener.setQueue("messageQueue");

// setCallBackClass which implements `ListenerInterface` interface
reactiveListener.setCallBackMethod(test);

// subscribe start
reactiveListener.listen();
```

### set callback action
you should create class which implements ListenerInterface to set callback method.
```Java
public class Test implements ListenerInterface {

    private String name;

    Test(String name){
        this.name = name;
    }

    // callback method that is called when subsribing a message.
    @Override
    public void callBack(Message message) {
        System.out.println("receiveMessage::"+message.toString());
    }

    // callback method that is called when error happend by subscribing.
    @Override
    public void errorCallBack(Throwable throwable) {
        System.out.println("error::"+throwable.getMessage());
    }
}

```

### stop
you can stop subscribing by calling stop() method.
```Java
reactiveListener.stop();
```

### messageConverter
setting messageConverter like below. 
```Java
// set messageConverter
reactiveListener.setMessageConverter(messageConverter);


// default messageConverter is this .
private Jackson2JsonMessageConverter defaultMessageConverter(){
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .dateFormat(new StdDateFormat())
                .build();
        Jackson2JsonMessageConverter jackson2JsonMessageConverter
                = new Jackson2JsonMessageConverter(objectMapper);
        return jackson2JsonMessageConverter;
}
```



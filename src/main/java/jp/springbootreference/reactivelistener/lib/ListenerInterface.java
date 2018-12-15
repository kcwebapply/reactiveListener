package jp.springbootreference.reactivelistener.lib;

import org.springframework.amqp.core.Message;

public interface ListenerInterface {

    void callBack(Message message);

    void errorCallBack(Throwable throwable);
}

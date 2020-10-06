package cn.fzn.classsign.teacher.callback;

import cn.fzn.classsign.teacher.util.RedisUtil;
import cn.fzn.classsign.teacher.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RedisReceiver extends KeyExpirationEventMessageListener {

    @Autowired
    private SignUtil signUtil;
    public static final String SIGN_CHANNEL = "__keyevent@1__:expired";

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReceiver.class);

    public RedisReceiver(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        if (!SIGN_CHANNEL.equals(channel)) {
            return;
        }
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info(channel + ":" + msg);
        String[] keys = msg.split("-");
        if (!"s".equals(keys[0])) {
            return;
        }
        String signCode = keys[1];
        signUtil.stopSignIn(signCode);
    }
}
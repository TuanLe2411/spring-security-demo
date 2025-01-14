package com.practice.security.events.listeners;

import com.practice.security.events.publishers.OnUserVerifyRequestPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OnUserVerifyRequestListener implements ApplicationListener<OnUserVerifyRequestPublisher> {

    @Async
    @Override
    public void onApplicationEvent(OnUserVerifyRequestPublisher event) {

    }
}

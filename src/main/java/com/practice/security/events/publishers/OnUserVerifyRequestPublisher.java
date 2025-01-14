package com.practice.security.events.publishers;

import com.practice.security.models.UserVerifyRequest;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnUserVerifyRequestPublisher extends ApplicationEvent {
    private final UserVerifyRequest userVerifyRequest;

    public OnUserVerifyRequestPublisher(Object source, UserVerifyRequest userVerifyRequest) {
        super(source);
        this.userVerifyRequest = userVerifyRequest;
    }
}

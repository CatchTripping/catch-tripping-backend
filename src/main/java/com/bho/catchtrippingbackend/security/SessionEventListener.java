package com.bho.catchtrippingbackend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionEventListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionEventListener.class);

    @EventListener
    public void handleSessionCreatedEvent(HttpSessionCreatedEvent event) {
        logger.info("Session create: {}", event.getSession().getId());
    }

    @EventListener
    public void handleSessionDestroyedEvent(HttpSessionDestroyedEvent event) {
        logger.info("Session Destroyed: {}", event.getSession().getId());
    }
}

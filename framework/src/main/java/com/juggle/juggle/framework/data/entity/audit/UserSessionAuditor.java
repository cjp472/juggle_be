package com.juggle.juggle.framework.data.entity.audit;

import com.juggle.juggle.framework.common.session.UserSession;
import org.springframework.data.domain.AuditorAware;

import java.io.Serializable;
import java.util.Optional;

public class UserSessionAuditor implements AuditorAware<Serializable> {
    @Override
    public Optional<Serializable> getCurrentAuditor() {
        return Optional.of(UserSession.getUserInterface().getId());
    }
}

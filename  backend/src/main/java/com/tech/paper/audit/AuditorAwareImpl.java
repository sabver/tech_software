package com.tech.paper.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("tech youkennhou");
        // Use below commented code when will use Spring Security.
    }
}

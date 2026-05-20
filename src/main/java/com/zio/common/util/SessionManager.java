package com.zio.common.util;

import com.zio.common.data.api.Error;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionManager {

    public static Long getUserId() throws ZioException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (Long) auth.getPrincipal();
        } catch (Exception e) {
            throw new ZioException(new Error(515, 1, "NO_SESSION"));
        }

    }
}

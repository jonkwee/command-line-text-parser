package com.kwee.jonathan;

import com.kwee.jonathan.exceptions.SystemExitException;

import java.security.Permission;

public class CustomSecurityManager extends SecurityManager {

    @Override
    public void checkPermission(Permission perm) {}

    @Override
    public void checkExit(int status) {
        throw new SystemExitException(status);
    }
}

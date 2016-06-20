/*
 * Sonar Crowd Plugin
 * Copyright (C) 2009 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.crowd;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.crowd.exception.*;
import com.atlassian.crowd.service.client.CrowdClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.security.Authenticator;
import org.sonar.api.security.LoginPasswordAuthenticator;

import javax.annotation.Nonnull;

/**
 * @author Evgeny Mandrikov
 */
public class CrowdAuthenticator extends Authenticator {

    private static final Logger LOG = LoggerFactory.getLogger(CrowdAuthenticator.class);

    private final CrowdClient client;

    public CrowdAuthenticator(CrowdClient client) {
        this.client = client;
    }

    @Override
    public boolean doAuthenticate(Context context) {
        LOG.error("trying to authenticate: {}", context);
        String username = context.getUsername();
        try {
            LOG.error("trying to authenticate: {}", username);
            User user = client.authenticateUser(username, context.getPassword());
            LOG.error("user authenticated: {}", user == null ? "user is null" : user.getEmailAddress());
            return true;
        } catch (UserNotFoundException e) {
            LOG.error("User {} not found", username);
            return false;
        } catch (InactiveAccountException e) {
            LOG.error("User {} is not active", username);
            return false;
        } catch (ExpiredCredentialException e) {
            LOG.error("Credentials of user {} have expired", username);
            return false;
        } catch (ApplicationPermissionException e) {
            LOG.error("The application is not permitted to perform the requested operation"
                    + " on the crowd server", e);
            return false;
        } catch (InvalidAuthenticationException e) {
            LOG.error("Invalid credentials for user {}", username);
            return false;
        } catch (OperationFailedException e) {
            LOG.error("Unable to authenticate user " + username, e);
            return false;
        }
    }
}

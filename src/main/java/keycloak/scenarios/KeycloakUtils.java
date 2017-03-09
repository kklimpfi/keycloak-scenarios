package keycloak.scenarios;

import keycloak.scenarios.bootstrap.Configuration;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p/>
 * KeycloakUtils.
 * <p/>
 * $Id$
 * <p/>
 * $HeadURL$
 * <p/>
 *
 * @author KKlimpfi (Last Modified By: $Author$)
 * @version $Revision$
 * @since 08.03.2017 16:03 (Last Modified on: $Date$)
 */
public class KeycloakUtils {

    private static Keycloak keycloak;

    private static AuthzClient authzClient;

    public static Keycloak createKeycloakAdminCLI(Configuration config) {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(config.getKeycloakAuthServer())
                .realm(config.getRealm())
                .clientId(config.getAdminClientName())
                .username(config.getAdminUserName())
                .password(config.getAdminPassword())
                .build();

        return keycloak;
    }

    public static AuthzClient createAuthzClient(Configuration config) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", config.getClientSecret());

        org.keycloak.authorization.client.Configuration configuration = new org.keycloak.authorization.client.Configuration(
                config.getKeycloakAuthServer(),
                config.getRealm(),
                config.getClientName(),
                clientCredentials,
                null);

        authzClient = AuthzClient.create(configuration);
        return authzClient;
    }

    public static String getClientIdForClientName(String realm, String clientName) {
        return keycloak.realm(realm).clients().findByClientId(clientName).get(0).getId();
    }

    public static List<PolicyRepresentation> getPolicyRepresentationsFromClient(String realm, String clientId) {
        return keycloak
                .realm(realm)
                .clients()
                .get(clientId)
                .authorization()
                .policies()
                .policies();
    }


    public static String getEntitlements(String userAccessToken, String clientName) {
        return authzClient.entitlement(userAccessToken).getAll(clientName).getRpt();
    }

    public static String getUserAccessToken(String username, String userpassword) {
        return authzClient.obtainAccessToken(username, userpassword).getToken();
    }
}

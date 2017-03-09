package keycloak.scenarios.bootstrap;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * <p/>
 * Configuration.
 * <p/>
 * $Id$
 * <p/>
 * $HeadURL$
 * <p/>
 *
 * @author KKlimpfi (Last Modified By: $Author$)
 * @version $Revision$
 * @since 08.03.2017 13:56 (Last Modified on: $Date$)
 */
public class Configuration {

    public static final String PROPERTY_FILE_PROPERTY = "keycloak.config";

    private final PropertiesConfiguration properties;

    public Configuration() {
        String keycloakPropertiesFile = System.getProperty(PROPERTY_FILE_PROPERTY);

        try {
            properties = new Configurations().properties(new File(keycloakPropertiesFile));
        } catch (ConfigurationException e) {
            throw new RuntimeException();
        }
    }

    public String getKeycloakAuthServer() {
        return getString("keycloak.server.address");
    }

    public String getRealm() {
        return getString("keycloak.realm.name");
    }

    public String getClientName() {
        return getString("keycloak.client.name");
    }

    public String getAdminClientName() {
        return getString("keycloak.admin.client");
    }

    public String getAdminUserName() {
        return getString("keycloak.admin.username");
    }

    public String getAdminPassword() {
        return getString("keycloak.admin.password");
    }

    public String getUserUserName() {
        return getString("keycloak.user.username");
    }

    public String getUserPassword() {
        return getString("keycloak.user.password");
    }

    public String getClientSecret() {
        return getString("keycloak.client.secret");
    }

    private String getString(String key) {
        return properties.getString(key);
    }
}

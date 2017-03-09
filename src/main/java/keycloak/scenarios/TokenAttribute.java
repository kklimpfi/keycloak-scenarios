package keycloak.scenarios;

/**
  * <p/>
 * TokenAttribute.
 * <p/>
 * $Id$
 * <p/>
 * $HeadURL$
 * <p/>
 *
 * @author KKlimpfi (Last Modified By: $Author$)
 * @version $Revision$
 * @since 08.03.2017 15:00 (Last Modified on: $Date$)
 */
public enum TokenAttribute {
    AUTHORIZATION("authorization"),
    PERMISSIONS("permissions"),
    RESOURCE_SET_ID("resource_set_id"),
    RESOURCE_SET_NAME("resource_set_name"),
    SCOPES("scopes"),
    SESSION_STATE("session_state");

    private final String name;

    TokenAttribute(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
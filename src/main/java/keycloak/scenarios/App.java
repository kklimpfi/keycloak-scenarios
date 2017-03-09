package keycloak.scenarios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jwt.SignedJWT;
import keycloak.scenarios.bootstrap.Configuration;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Keycloak entitlement
 *
 */
public class App 
{
    Configuration configuration;

    // just running our small sample
    public static void main( String[] args ) throws ParseException {
        System.out.println( "Starting" );
        new App(new Configuration()).RunSample();
        System.out.println( "Ending" );
    }

    public App(Configuration config) throws ParseException {
        this.configuration = config;
        KeycloakUtils.createKeycloakAdminCLI(configuration);
        KeycloakUtils.createAuthzClient(configuration);
    }

    public void RunSample() throws ParseException {
        // Policies & Permissions
        System.out.println( "Policies in Realm='" + configuration.getRealm() + "' in client='" + configuration.getClientName() + "':\n" );
        List<PolicyRepresentation> policies = KeycloakUtils.getPolicyRepresentationsFromClient(configuration.getRealm(), KeycloakUtils.getClientIdForClientName(configuration.getRealm(), configuration.getClientName()));
        printPoliciesAndPermissions(policies);

        // getting access token & entitlement token for user
        System.out.println( "Entitlements for User='" + configuration.getUserUserName() + "' in client='" + configuration.getClientName() + "':\n" );
        String userAccessToken = KeycloakUtils.getUserAccessToken(configuration.getUserUserName(), configuration.getUserPassword());
        System.out.println("userAccessToken = " + userAccessToken);
        String entitlementToken = KeycloakUtils.getEntitlements(userAccessToken, configuration.getClientName());
        System.out.println("entitlementToken = " + entitlementToken);
        System.out.println("\n");

        // Printing the entitlements
        printEntitlementToken(entitlementToken);
    }

    public void printPoliciesAndPermissions(List<PolicyRepresentation> policies) {
        System.out.println("Policies: ");
        policies.stream()
                .filter(p -> StringUtils.containsIgnoreCase(p.getName(), "Policy") && !StringUtils.containsIgnoreCase(p.getName(), "Default"))
                .forEach(p -> printPolicy(p));

        System.out.println("Permissions: ");
        policies.stream()
                .filter(p -> StringUtils.containsIgnoreCase(p.getName(), "Permission") && !StringUtils.containsIgnoreCase(p.getName(), "Default"))
                .forEach(p -> printPermission(p));

        System.out.println("\n");
    }

    private void printPermission(PolicyRepresentation p) {
            System.out.println( " > " + p.getName());
            System.out.println( "\t Type: " + p.getType());
            System.out.println( "\t DecisionStrategy: " + p.getDecisionStrategy().toString());
            System.out.println( "\t Description: " + p.getDescription().toString());
            // System.out.println( "\t config:");
            // printHashMap(p.getConfig());
    }

    private void printPolicy(PolicyRepresentation p) {
        System.out.println( " > " + p.getName());
        System.out.println( "\t Type: " + p.getType());
        System.out.println( "\t Logic: " + p.getLogic().toString());
        System.out.println( "\t config" + p.getConfig() + "\n");
    }

    private void printEntitlementToken(String entitlementToken) throws ParseException {
        SignedJWT jwt = SignedJWT.parse(entitlementToken);
        JSONObject jsonObject = jwt.getPayload().toJSONObject();

        System.out.println("Entitlement Token:");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = gson.toJson(jsonObject);
        System.out.println(prettyJsonString + "\n\n");

        JSONArray permissions = (JSONArray) ((JSONObject) jsonObject
                .get(TokenAttribute.AUTHORIZATION.toString()))
                .get(TokenAttribute.PERMISSIONS.toString());

        System.out.println("Entitlements:\n");
        for (Object permission : permissions) {

            String id = ((net.minidev.json.JSONObject) permission).get(TokenAttribute.RESOURCE_SET_ID.toString()).toString();
            String name = ((net.minidev.json.JSONObject) permission).get(TokenAttribute.RESOURCE_SET_NAME.toString()).toString();

            List<String> scopes = new ArrayList<>();
            Object scopesJson = ((net.minidev.json.JSONObject) permission).get(TokenAttribute.SCOPES.toString());
            if (scopesJson != null) {
                scopes = ((net.minidev.json.JSONArray) scopesJson)
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
            }

            System.out.println("Entitlement: ID = " + id);
            System.out.println("\t Name = " + name);
            System.out.println("\t Scopes = " + StringUtils.join(scopes, ", "));
        }
        System.out.println("\n");


    }

    private static void printHashMap(Map<String, String> map) {
        Map<String, String> reversedMap = new TreeMap<String, String>(map);
        for (HashMap.Entry entry : reversedMap.entrySet()) {
            System.out.println("\t\t " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
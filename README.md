# Keycloak Scenario Project

To run this sample first configure the ``setup-keycloak.bat``:
- set the path to the keycloak home directory
- set the path to the migration file to import the sample data

Run the script ``setup-keycloak.bat`` to start keycloak and import the data. 

Run the java application and pass the system property to the config file. e.g.:
 ``-Dkeycloak.config=D:\keycloakentitlementscenario\scripts\configuration.properties``
 
You can use IntelliJ Idea to open and debug the project.
It should also work with Linux ;)


### Retrieving the entitlements:
The java application is connecting to the keycloak server and printing for the ``TestClient`` the policies & permissions (filtered the default ones).
Then it fetches the user access token for the user ``marta`` and retrieves and prints the entitlement token for the ``TestClient`` (and the permissions contained within).

In the output of the entitlement token you can see that ``marta`` would have access to:
 - scope read on resource-a
 - scope write on resource-a
 - scope execute on resource-a
 - scope read on resource-c
 - scope write on resource-c
 - scope execute on resource-c
 
I thought it would be only access to:
 - scope read on resource-a
 - resource-c (no scope)

 
### Sample Data:
###### User:
- marta
- kolo

###### Scopes:
- read
- write
- execute

###### Resources:
 - resource-a
 - resource-b
 - resource-c
 - resource-all
 
###### Policies:
 - Policy-IsUser-Marta 
 - Policy-IsUser-Kolo
  
###### Permissions:
Resource-Based:
- ResourcePermission-ResourceC-Marta:  resource-c + Policy-IsUser-Marta

Scope-Based:
- ScopePermission-ResourceA-Read-Marta:  resource-a + scope-read + Policy-IsUser-Marta
- ScopePermission-ResourceAll-Read-Kolo:  resource-a + scope-read + Policy-IsUser-Kolo


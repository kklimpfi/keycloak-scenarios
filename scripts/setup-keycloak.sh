#!/bin/bash
# ===== Configuration ===== #
# Set this path to the Keycloak Home directory e.g.: D:\keycloak-2.5.4.Final
export KEYCLOAK_HOME=/opt/EAD/keycloak/keycloak-2.5.4.Final
# Set this path to the import JSON e.g.: D:\keycloak-migration.json
export KEYCLOAK_IMPORTFILE=/opt/EAD/keycloak/keycloakentitlementscenario/scripts/keycloak-migration.json

## if needed alos set the matching java paths
# echo "SET JDK 8 for Keycloak"
# export JAVA_HOME=/opt/EAD/keycloak/java/jdk1.8.0_112
# export JRE_HOME=/opt/EAD/keycloak/java/jdk1.8.0_112/jre



# ===== Starting Keycloak ===== #
export PATH=$PATH:$KEYCLOAK_HOME/bin
echo "Start Keycloak and Import"
$KEYCLOAK_HOME/bin/standalone.sh -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=$KEYCLOAK_IMPORTFILE

# standalone.bat -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=myRealmExport.json
# standalone.bat -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=myRealmExport.json
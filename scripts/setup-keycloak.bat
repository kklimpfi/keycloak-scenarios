:: ===== Configuration ===== ::
:: Set this path to the Keycloak Home directory e.g.: D:\keycloak-2.5.4.Final
@SET KEYCLOAK_HOME=D:\_LIBS_\keycloak-2.5.4.Final
:: Set this path to the import JSON e.g.: D:\keycloak-migration.json
@SET KEYCLOAK_IMPORTFILE=D:\_LIBS_\keycloak-2.5.4.Final\keycloak-migration.json

:: ===== Starting Keycloak ===== ::
@SET PATH=%PATH%;%KEYCLOAK_HOME%\bin
@ECHO Start Keycloak and Import
@START "Keycloak Server" /D "%KEYCLOAK_HOME%\bin" "%KEYCLOAK_HOME%\bin\standalone.bat" -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=%KEYCLOAK_IMPORTFILE%

::standalone.bat -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=myRealmExport.json
::standalone.bat -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=myRealmExport.json
@exit

set /p DOMAIN_NAME=Enter your domain name  (ex:org.sklsft) : 
set /p PROJECT_NAME=Enter your project name (ex:demo) : 

:PROMPT_SKL_TYPE
set /p SKELETON_NUMBER=Enter your skeleton type (1:SPRING_HIBERNATE_RICHFACES, 2:BASIC_SPRING_HIBERNATE_RICHFACES) : 
if %SKELETON_NUMBER% == 1 (set SKELETON_TYPE=SPRING_HIBERNATE_RICHFACES& goto PROMPT_SKL_TYPE_OK)
if %SKELETON_NUMBER% == 2 (set SKELETON_TYPE=BASIC_SPRING_HIBERNATE_RICHFACES& goto PROMPT_SKL_TYPE_OK)
goto PROMPT_SKL_TYPE
:PROMPT_SKL_TYPE_OK

:PROMPT_DATABASE_ENGINE
set /p DATABASE_ENGINE_NUMBER=Enter your database engine (1:ORACLE, 2:POSTGRESQL) : 
if %DATABASE_ENGINE_NUMBER% == 1 (set DATABASE_ENGINE=ORACLE& goto PROMPT_DATABASE_ENGINE_OK)
if %DATABASE_ENGINE_NUMBER% == 2 (set DATABASE_ENGINE=POSTGRESQL& goto PROMPT_DATABASE_ENGINE_OK)
goto PROMPT_DATABASE_ENGINE
:PROMPT_DATABASE_ENGINE_OK

set /p DATABASE_NAME=Enter your database name (ex:DEMO) : 
set /p DATABASE_HOSTNAME=Enter your database host name (ex:localhost) : 
set /p DATABASE_PORT=Enter your database port (ex:5432) : 
set /p DATABASE_USERNAME=Enter your database username (ex:postgres) : 
set /p DATABASE_PASSWORD=Enter your database password (ex:postgres) : 

:PROMPT_AUDITED
set /p AUDITED_NUMBER=Do you want to activate Hibernate envers fonctionality (0:False, 1:True): 
if %AUDITED_NUMBER% == 0 (set AUDITED=False& goto PROMPT_AUDITED_OK)
if %AUDITED_NUMBER% == 1 (set AUDITED=True& goto PROMPT_AUDITED_OK)
goto PROMPT_AUDITED
:PROMPT_AUDITED_OK
echo .
echo domain name : %DOMAIN_NAME%
echo project name : %PROJECT_NAME%
echo skeleton type : %SKELETON_TYPE%
echo database engine : %DATABASE_ENGINE%
echo database name : %DATABASE_NAME%
echo database host name : %DATABASE_HOSTNAME%
echo database port : %DATABASE_PORT%
echo database username : %DATABASE_USERNAME%
echo database password : %DATABASE_PASSWORD%
echo hibernate envers activated : %AUDITED%


set INIT_ARGS="%CD%" "%DOMAIN_NAME%" "%PROJECT_NAME%" "%SKELETON_TYPE%" "%DATABASE_ENGINE%" "%DATABASE_NAME%" "%DATABASE_HOSTNAME%" "%DATABASE_PORT%" "%DATABASE_USERNAME%" "%DATABASE_PASSWORD%" "%AUDITED%"

echo .

:PROMPT_CONTINUE
set /p INIT_OK=continue ? Y/N : 
if %INIT_OK% == Y goto :PROMPT_CONTINUE_OK
if %INIT_OK% == N goto :PROMPT_CONTINUE_OK
goto PROMPT_CONTINUE
:PROMPT_CONTINUE_OK
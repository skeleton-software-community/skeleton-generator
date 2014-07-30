@REM ----------------------------------------------------------------------------
@REM bat file to execute classes from generator-bash 2.0.0
@REM ----------------------------------------------------------------------------

@echo off

set SKLGEN_VERSION=2.0.0-SNAPSHOT
set SKLGEN_RUNNABLE_JAR=%SKLGEN_HOME%\boot\generator-bash-%SKLGEN_VERSION%.jar
set SKLGEN_LIB=%SKLGEN_HOME%\lib
set SKLGEN_CLASSPATH=%SKLGEN_RUNNABLE_JAR%;%SKLGEN_LIB%\*

set GENERATE_CLASS=org.sklsft.generator.command.CodeGeneratorLauncher
set INIT_CLASS=org.sklsft.generator.command.ProjectInitializerLauncher
set BUILDDB_CLASS=org.sklsft.generator.command.DatabaseBuilderLauncher
set POPULATEDB_CLASS=org.sklsft.generator.command.DatabasePopulatorLauncher

echo current directory : %CD%
echo generator home : %SKLGEN_HOME%
echo version : %SKLGEN_VERSION%

@REM ==== START VALIDATION ====
if not "%JAVA_HOME%" == "" goto OK_JAVA_HOME

echo.
echo ERROR: JAVA_HOME not found in your environment.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation
echo.
goto ERROR

:OK_JAVA_HOME
if exist "%JAVA_HOME%\bin\java.exe" goto CHECK_JAVA_HOME

echo.
echo ERROR: JAVA_HOME is set to an invalid directory.
echo JAVA_HOME = %JAVA_HOME%
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation
echo.
goto ERROR

:CHECK_JAVA_HOME
if not "%SKLGEN_HOME%"=="" goto OK_SKLGEN_HOME

echo.
echo ERROR: SKLGEN_HOME not found in your environment.
echo Please set the SKLGEN_HOME variable in your environment to match the
echo location of the Skeleton generator installation
echo.
goto ERROR

:OK_SKLGEN_HOME
if exist "%SKLGEN_HOME%\bin\sklgen.bat" goto CHECK_SKLGEN_HOME

echo.
echo ERROR: SKLGEN_HOME is set to an invalid directory.
echo SKLGEN_HOME = %SKLGEN_HOME%
echo Please set the SKLGEN_HOME variable in your environment to match the
echo location of your skeleton generator installation
echo.
goto ERROR

@REM ==== END VALIDATION ====
:CHECK_SKLGEN_HOME


:GET_SKLGEN_CMD_LINE_ARGS
@REM ==== START COMMAND LINE ARGS ====
set SKLGEN_CMD_LINE_ARGS=%1
set DATABASE_NAME=%2

if %SKLGEN_CMD_LINE_ARGS%==help goto HELP
if %SKLGEN_CMD_LINE_ARGS%==init goto INIT
if %SKLGEN_CMD_LINE_ARGS%==generate goto GENERATE
if %SKLGEN_CMD_LINE_ARGS%==builddb goto BUILDDB
if %SKLGEN_CMD_LINE_ARGS%==populatedb goto POPULATEDB

goto INVALID_SKLGEN_CMD_LINE_ARGS

@REM ==== END COMMAND LINE ARGS ====


@REM ==== COMMANDS ====
:HELP
echo use one of the following commands :
echo . init
echo . generate
echo . buildbdb
echo . populatedb
goto END

:INIT
call sklgen-do-init.bat
if not %INIT_OK%==Y goto INIT_CANCEL
echo start initializing project
%JAVA_HOME%\bin\java -classpath %SKLGEN_CLASSPATH% %INIT_CLASS% %INIT_ARGS%
echo end initializing project
goto END
:INIT_CANCEL
echo initialization cancelled
goto END

:GENERATE
echo start generating code
%JAVA_HOME%\bin\java -classpath %SKLGEN_CLASSPATH% %GENERATE_CLASS% "%CD%"
echo end generating code
goto END

:BUILDDB
echo start building database
%JAVA_HOME%\bin\java -classpath %SKLGEN_CLASSPATH% %BUILDDB_CLASS% "%CD%" "%DATABASE_NAME%"
echo end building database
goto END

:POPULATEDB:
echo start populating database
%JAVA_HOME%\bin\java -classpath %SKLGEN_CLASSPATH% %POPULATEDB_CLASS% "%CD%" "%DATABASE_NAME%"
echo end populating database
goto END


:INVALID_SKLGEN_CMD_LINE_ARGS
echo.
echo ERROR: This command line argument is not valid.
echo run help to check the list of available commands.
goto ERROR

:ERROR
echo FAILED

:END
echo END
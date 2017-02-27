@REM ----------------------------------------------------------------------------
@REM bat file to execute generator-bash
@REM ----------------------------------------------------------------------------

@echo off

set SKLGEN_VERSION=2.1.2
set SKLGEN_RUNNABLE_JAR=%SKLGEN_HOME%\boot\generator-bash-%SKLGEN_VERSION%.jar
set SKLGEN_LIB=%SKLGEN_HOME%\lib
set SKLGEN_CLASSPATH=%SKLGEN_RUNNABLE_JAR%;%SKLGEN_LIB%\*

set RUNNABLE_CLASS=org.sklsft.generator.bash.launcher.MainLauncher

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
set DATABASE=%2

@REM ==== END COMMAND LINE ARGS ====


:RUN
echo start sklgen
"%JAVA_HOME%\bin\java" -classpath %SKLGEN_CLASSPATH% %RUNNABLE_CLASS% "%SKLGEN_CMD_LINE_ARGS%" "%CD%" "%DATABASE%"
echo end sklgen
goto END



:ERROR
echo FAILED

:END
echo END
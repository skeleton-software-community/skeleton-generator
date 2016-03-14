@echo off
set /p NEW_VERSION=Enter new version :

call mvn versions:set -DnewVersion=%NEW_VERSION% -DgenerateBackupPoms=false

pause
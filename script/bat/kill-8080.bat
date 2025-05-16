@echo off
setlocal enabledelayedexpansion

echo Checking for process using port 8080...

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr LISTENING') do (
    set PID=%%a
    echo Found process using port 8080 with PID: !PID!
    echo Killing process !PID! ...
    taskkill /F /PID !PID!
    echo Process !PID! killed.
    goto end
)

echo No process is using port 8080.
:end
pause

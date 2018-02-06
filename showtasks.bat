call runcrud.bat
if "%ERRORLEVEL%" == "0" goto browser
echo runcrud.bat finished with ERRORS
goto finished

:browser
start "" http://localhost:8080/crud/v1/task/getTasks

:finished
echo Process finished
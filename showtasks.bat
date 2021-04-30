call runcrud
if "%ERRORLEVEL%" == "0" goto openweb
echo.
echo runcrud has errors – breaking work
goto fail

:openweb
start "Google Chrome" http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.

echo failed to load website
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.
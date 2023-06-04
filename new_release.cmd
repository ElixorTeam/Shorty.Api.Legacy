@echo off

for /f "tokens=1-3 delims=.-/ " %%a in ('date /t') do (
    set "day=%%a"
    set "month=%%b"
    set "year=%%c"
)
for /f "tokens=1-2 delims=: " %%a in ('time /t') do (
    set "hour=%%a"
    set "minute=%%b"
)

set "new_version=%year%-%month%-%day%-%hour%-%minute%"
set "new_version=%new_version:.=-%"
set "new_version=%new_version:--=-%"

echo Release: %new_version%

docker build -t baggerfast/shorty_api_gateway:%new_version% ApiGateWay
docker push baggerfast/shorty_api_gateway:%new_version%
docker tag baggerfast/shorty_api_gateway:%new_version% baggerfast/shorty_api_gateway:latest
docker push baggerfast/shorty_api_gateway:latest

docker build -t baggerfast/shorty_eureka_server:%new_version% EurekaServer
docker push baggerfast/shorty_eureka_server:%new_version%
docker tag baggerfast/shorty_eureka_server:%new_version% baggerfast/shorty_eureka_server:latest
docker push baggerfast/shorty_eureka_server:latest

docker build -t baggerfast/shorty_links_shortener:%new_version% LinkShortener
docker push baggerfast/shorty_links_shortener:%new_version%
docker tag baggerfast/shorty_links_shortener:%new_version% baggerfast/shorty_links_shortener:latest
docker push baggerfast/shorty_links_shortener:latest

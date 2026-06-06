$ErrorActionPreference = 'Stop'

$searchRoots = @(
    (Join-Path $env:USERPROFILE '.gradle\caches\modules-2\files-2.1\com.h2database\h2'),
    (Join-Path $env:USERPROFILE '.m2\repository\com\h2database\h2')
)

$h2Jar = $null

foreach ($root in $searchRoots) {
    if (-not (Test-Path $root)) {
        continue
    }

    $h2Jar = Get-ChildItem -Path $root -Recurse -Filter 'h2-*.jar' -File |
        Sort-Object LastWriteTime -Descending |
        Select-Object -First 1

    if ($h2Jar) {
        break
    }
}

if (-not $h2Jar) {
    throw 'H2 jar를 찾지 못했습니다. Gradle 또는 Maven 캐시에 H2가 먼저 내려받아져 있어야 합니다.'
}

Write-Output "Using H2 jar: $($h2Jar.FullName)"
Write-Output 'Starting H2 TCP server on tcp://localhost:9092'
Write-Output 'Target database: jdbc:h2:tcp://localhost/~/termdb'

java -cp $h2Jar.FullName org.h2.tools.Server -tcp -tcpPort 9092 -ifNotExists

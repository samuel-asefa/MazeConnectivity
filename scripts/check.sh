#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
if command -v javac >/dev/null 2>&1; then
    compiler=(javac)
else
    compiler=(java com.sun.tools.javac.Main)
fi
mkdir -p build
"${compiler[@]}" --release 17 -Xlint:all -d build "@sources.txt"
"${compiler[@]}" --release 17 -Xlint:all -cp build -d build tests/MazeConnectivityChecks.java
java -cp build MazeConnectivityChecks

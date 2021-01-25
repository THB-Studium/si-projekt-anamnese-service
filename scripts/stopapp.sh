#!/usr/bin/bash

# test if the file exists (running instance) and kill the app before deployment
echo 'Application stop hook'
if pgrep java; then
    echo 'Stopping current app instance'
    pkill java;
fi

#!/usr/bin/bash

# install jq for parsing json
if [ -e /usr/bin/jq ]; then
  echo "jq installed";
else
  echo "Installing jq";
  sudo yum -y install jq
fi

# install postgresql client
if [ -e /usr/bin/psql ]; then
  echo "postgresql client installed";
else
  echo "Installing postgresql client ";
  sudo yum -y install postgresql
fi

# install dos2unix to enable aws recognizing script as such
if [ -e /usr/bin/dos2unix ]; then
  echo "dos2unix installed";
else
  echo "Installing dos2unix";
  sudo yum -y install dos2unix
fi
scriptDir="$(dirname "${BASH_SOURCE[0]}")"
find ${scriptDir} -name "*.sh" | xargs dos2unix

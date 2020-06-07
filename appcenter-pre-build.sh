#!/usr/bin/env bash

echo "Running PreBuild Script..."

LOCAL_PROPERTIES=$APPCENTER_SOURCE_DIRECTORY/local.properties
KEYSTORE_PROPERTIES=$APPCENTER_SOURCE_DIRECTORY/keystore.properties

echo "Setting up local.properties"

if [[ -e "$LOCAL_PROPERTIES" ]]
then
    touch local.properties
    echo appCenterKey=$APP_CENTER_TOKEN >> local.properties
    echo googleMapsKey=$GOOGLE_MAPS_KEY >> local.properties
fi

echo "Setting up keystore.properties"

if [[ -e "$KEYSTORE_PROPERTIES" ]]
then
    touch keystore.properties

    echo debugKeyAlias=$DEBUG_KEY_ALIAS >> keystore.properties
    echo debugKeyPass=$DEBUG_KEY_PASS >> keystore.properties
    echo debugKeyStorePass=$DEBUG_KEY_STORE_PASS >> keystore.properties

    echo releaseKeyAlias=$RELEASE_KEY_ALIAS >> keystore.properties
    echo releaseKeyPass=$RELEASE_KEY_PASS >> keystore.properties
    echo releaseKeyStorePass=$RELEASE_KEY_STORE_PASS >> keystore.properties
    echo releaseKeyStoreFile=$RELEASE_KEY_STORE_FILE >> keystore.properties

fi

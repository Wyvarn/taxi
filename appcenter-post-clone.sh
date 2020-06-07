#!/usr/bin/env bash

echo "Setting up local.properties"

touch local.properties
echo appCenterKey=$APP_CENTER_TOKEN >> local.properties
echo googleMapsKey=$GOOGLE_MAPS_KEY >> local.properties

echo "Setting up keystore.properties"

touch keystore.properties

echo debugKeyAlias=$DEBUG_KEY_ALIAS >> keystore.properties
echo debugKeyPass=$DEBUG_KEY_PASS >> keystore.properties
echo debugKeyStorePass=$DEBUG_KEY_STORE_PASS >> keystore.properties

echo releaseKeyAlias=$RELEASE_KEY_ALIAS >> keystore.properties
echo releaseKeyPass=$RELEASE_KEY_PASS >> keystore.properties
echo releaseKeyStorePass=$RELEASE_KEY_STORE_PASS >> keystore.properties
echo releaseKeyStoreFile=$RELEASE_KEY_STORE_FILE >> keystore.properties
#!/bin/bash
set -x

eval $(minikube docker-env)

cd ./ProductService
./gradlew bootBuildImage --imageName productservice --refresh-dependencies
cd ../StoreService
./gradlew bootBuildImage --imageName storeservice --refresh-dependencies
cd ../ShoppingService
./gradlew bootBuildImage --imageName shoppingservice --refresh-dependencies

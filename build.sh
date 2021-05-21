#!/usr/bin/env bash
mvn clean install
cd order-service
docker build -t eop/order-service .

cd ../order-bulk-service
docker build -t eop/order-bulk-service .

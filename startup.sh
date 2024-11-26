#!/bin/bash

# build postgres
docker-compose build postgres

# run postgres
docker-compose up -d postgres

# build app image
docker-compose build app

# run app
docker-compose up -d app

#!/bin/bash

docker run --name psql-main -v ~/tmp/postgresql/data:/var/lib/postgresql/data -e POSTGRES_PASSWORD=postgresql -p 5432:5432 -d postgres


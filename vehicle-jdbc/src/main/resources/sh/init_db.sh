#!/bin/bash

docker cp ../ddl/vehicle_db.sql psql-main:/tmp/vehicle_db.sql
docker exec -u postgres psql-main psql -U postgres -f /tmp/vehicle_db.sql

docker cp ../ddl/vehicle.sql psql-main:/tmp/vehicle.sql
docker exec -u postgres psql-main psql -d vehicle -U postgres -f /tmp/vehicle.sql

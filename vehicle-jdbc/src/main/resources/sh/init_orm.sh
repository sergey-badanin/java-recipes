#!/bin/bash

docker cp ../ddl/vehicle_orm.sql psql-main:/tmp/vehicle_orm.sql
docker exec -u postgres psql-main psql -d vehicle -U postgres -f /tmp/vehicle_orm.sql

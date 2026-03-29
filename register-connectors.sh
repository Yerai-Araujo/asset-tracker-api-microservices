#!/bin/bash

echo "Registering Kafka connectors..."

curl -X POST http://localhost:8083/connectors \
-H "Content-Type: application/json" \
-d '{
  "name": "user-outbox-connector",
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "database.hostname": "postgres-user",
    "database.port": "5432",
    "database.user": "user_service",
    "database.password": "12345678",
    "database.dbname" : "user_db",
    "topic.prefix": "user-service",
    "plugin.name": "pgoutput",
    "slot.name": "debezium",
    "publication.name": "dbz_publication",
    "table.include.list": "public.outbox_events",
    "transforms": "outbox",
    "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter",
    "transforms.outbox.table.fields.additional.placement": "type:header:type"
  }
}'

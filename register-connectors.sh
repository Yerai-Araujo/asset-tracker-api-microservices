#!/bin/bash

echo "Registering Kafka connectors..."

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CONNECTORS_DIR="$SCRIPT_DIR/kafka-connectors"

# Check if connectors directory exists
if [ ! -d "$CONNECTORS_DIR" ]; then
  echo "Error: Connectors directory not found at $CONNECTORS_DIR"
  exit 1
fi

# Loop through all JSON files in the connectors directory
for connector_file in "$CONNECTORS_DIR"/*.json; do
  if [ -f "$connector_file" ]; then
    connector_name=$(basename "$connector_file" .json)
    echo "Registering connector: $connector_name"
    
    curl -X POST http://localhost:8083/connectors \
      -H "Content-Type: application/json" \
      -d @"$connector_file"
    
    if [ $? -eq 0 ]; then
      echo "✓ Connector $connector_name registered successfully"
    else
      echo "✗ Failed to register connector $connector_name"
    fi
    echo ""
  fi
done

echo "All connectors registration completed."

#!/bin/sh
# wait-for-mysql.sh

set -e

>&2 echo "Waiting for MySQL..."

HOST="db"
USER="root"
PWD="root"

until mysql -h $HOST --protocol=tcp -u $USER -p$PWD -e ";"; do
    >&2 echo "MySQL is unavailable - waiting..."
    sleep 1
done
  
>&2 echo "MySQL is up - end of wait-for-mysql.sh"

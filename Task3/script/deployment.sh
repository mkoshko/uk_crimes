#!/bin/bash
dbuser="postgres"
dbname="crimebox"
tables=("crime" "location" "category_name" "stop_and_search" "street" "outcome_object" "outcome_status")
project_folder=
api=("" "street-level-crimes" "stop-and-search-by-area" "stop-and-search-by-force")
selectedApi=0

help() {
  echo "Usage: deployment.sh [OPTION]..."
  echo "Options:"
  echo "  -f <project folder>,   Specify path to the project."
  echo "  -b,                    Build project with maven. Make sure you specify path to the project through -f option."
  echo "  -d,                    Drop tables data."
  echo "  -w <index>             Index of api method to download data."
  echo "                            -1   street level crimes"
  echo "                            -2   stop and search by area"
  echo "                            -3   stop and search by force"
  echo "                            -all download from all available api methods"
}

check_postgres() {
  status=$(service postgresql-10 status | grep "running");
  if [[ -z "$status" ]]; then
    service postgresql-10 start
    sleep 10
  else
    echo "Postgres service is already running."
  fi
}

create_tables() {
  echo "Creating tables..."
  psql -U "$dbuser" "$dbname" -q -f "$project_folder/sql/create_schema_script.sql"
  psql -U "$dbuser" "$dbname" -q -f "$project_folder/sql/stop-and-search.sql"
  echo "Done."
}

create_tables_if_not_exists() {
  local dbtables=
  local isTableMissed=
  dbtables=$(psql -U "$dbuser" "$dbname" -c \\dt)
  if [[ -z "$dbtables" ]]; then
    create_tables
  else
    for i in "${tables[@]}"; do
      if [[ ! $dbtables =~ $i ]]; then
        echo "Table $i doesn't exists."
        isTableMissed=1
      fi
    done
    if [[ $isTableMissed -eq 1 ]]; then
      create_tables
      echo "Tables was successfully created."
      return 0
    else
      echo "Tables already exists."
    fi
  fi
}

query_to_database() {
  psql -U "$dbuser" "$dbname" -c "$1"
}

drop_table_data() {
  echo "Truncating tables..."
  local tbls="${tables[*]}"
  query_to_database "TRUNCATE ${tbls// /, }" >/dev/null
  echo "Done."
  echo "Resetting sequences..."
  query_to_database "ALTER SEQUENCE category_name_id_seq RESTART;" >/dev/null
  query_to_database "ALTER SEQUENCE stop_and_search_id_seq RESTART;" >/dev/null
  query_to_database "ALTER SEQUENCE outcome_status_id_seq RESTART;" >/dev/null
  query_to_database "ALTER SEQUENCE outcome_object_id_seq RESTART;" >/dev/null
  echo "Done."
}

build_project() {
  echo "Building project..."
  mvn -q -f "$project_folder" clean package
  echo "Done"
}

run_application() {
  if [[ $selectedApi -eq 0 ]]; then
    return 0
  elif [[ "$selectedApi" == "all" ]]; then
    check_postgres
    create_tables_if_not_exists
    for i in "${api[@]:1:3}"; do
      java -jar "$project_folder/target/crime.jar" -Dapi="$i" -Dfile="$project_folder/data/LondonStations.csv" -Dfrom=2019-01 -Dto=2019-05
    done
  else
    check_postgres
    create_tables_if_not_exists
    java -jar "$project_folder/target/crime.jar" -Dapi="${api[selectedApi]}" -Dfile="$project_folder/data/LondonStations.csv" -Dfrom=2019-01 -Dto=2019-05
  fi
}

parse_arguments() {
  while getopts dbhf:w: opt "$@"; do
  case $opt in
    f)
    project_folder="$OPTARG"
    ;;
    h)
    help
    exit 0
    ;;
    w)
    selectedApi="$OPTARG"
    ;;
    d)
    check_postgres
    drop_table_data
    ;;
    b)
    build_project
    ;;
    *)
    echo "Unknown argument."
    help
    exit 1
    ;;
  esac
  done
}

parse_arguments "$@"
run_application

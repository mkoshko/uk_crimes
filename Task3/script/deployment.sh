#!/bin/bash
quiet="-q"
dbuser="postgres"
dbname="crimebox"
tb1="crime"
tb2="location"
tb3="category_name"
tb4="street"
tb5="outcome_status"
tb6="stop_and_search"
tb7="outcome_object"
project_folder=
rows=0


help() {
  echo "Usage: deployment.sh [OPTION]..."
  echo "Options:"
  echo "  -f <project folder>,   path to the project."
  echo "  -B,                    build maven project from specified path."
  echo "  -D,                    drop tables data."
  echo "  -Q <index>,            index of a query."
  echo "  -v,                    display non-script output."
}

check_postgres() {
  status=$(service postgresql-10 status | grep "running");
  if [[ -z "$status" ]]; then
    service postgresql-10 start
  fi
}

clear_database() {
  psql -U "$dbuser" -c \\dn
}

create_tables() {
  echo "Creating tables..."
  psql -U "$dbuser" "$dbname" -q -f "$project_folder/sql/create_schema_script.sql"
  psql -U "$dbuser" "$dbname" -q -f "$project_folder/sql/stop-and-search.sql"
  if [[ "$?" -eq 0 ]]; then
    echo "Done."
  else
    echo "Tables wasn't created."
  fi
}

create_tables_if_not_exists() {
  local tables=$(psql -U "$dbuser" "$dbname" -c \\dt 2>&1)
  if [[ "$tables" == "Did not find any relations." ]]; then
    create_tables
  else
    local numOfTables=$(echo "$tables" | grep -e "$tb1" -e "$tb2" -e "$tb3" -e "$tb4" -e "$tb5" -e "$tb6" -e "$tb7" -c)
    if [[ $numOfTables -ne 7 ]]; then
      create_tables
    else
      echo "Tables already exists."
    fi
  fi
}

query_to_database() {
  psql -U "$dbuser" "$dbname" -c "$1"
}

drop_table_data() {
  echo "Table cleaning..."
  query_to_database "TRUNCATE $tb1, $tb2, $tb3, $tb4, $tb5, $tb6, $tb7" >/dev/null
  query_to_database "ALTER SEQUENCE category_name_id_seq RESTART;" >/dev/null
  query_to_database "ALTER SEQUENCE stop_and_search_id_seq RESTART;" >/dev/null
  query_to_database "ALTER SEQUENCE outcome_status_id_seq RESTART;" >/dev/null
  query_to_database "ALTER SEQUENCE outcome_object_id_seq RESTART;" >/dev/null
  echo "Done."
}

build_project() {
  echo "Building project..."
  echo "$1"
  mvn -f "$1" clean package
  echo "Done"
}

run_application() {
  java -jar "$project_folder/target/crime.jar" -Dapi=street-level-crimes -Dfile="$project_folder/data/LondonStations.csv" -Dfrom=2019-01 -Dto=2019-05
  java -jar "$project_folder/target/crime.jar" -Dapi=stop-and-search-by-area -Dfile="$project_folder/data/LondonStations.csv" -Dfrom=2019-01 -Dto=2019-05
  java -jar "$project_folder/target/crime.jar" -Dapi=stop-and-search-by-force -Dfrom=2019-01 -Dto=2019-05
}

anazyle() {
  java -jar "$project_folder/target/crime.jar" -Dapi="$1" -Drows="$2" -Dfile="$project_folder/sql/queries/"
}

parse_arguments() {
  while getopts DBvhr:Q:f: opt "$@"; do
  case $opt in
    v)
    quiet="-"
    ;;
    h)
    help
    exit 0
    ;;
    f)
    project_folder=$OPTARG
    ;;
    D)
    drop_table_data
    exit 0
    ;;
    B)
    build_project "$project_folder"
    ;;
    r)
    rows=$OPTARG
    ;;
    Q)
    anazyle "$OPTARG" "$rows"
    exit 0;
    ;;
    *)
    echo "Unknown argument."
    exit 0
    ;;
  esac
  done
}

parse_arguments "$@"
check_postgres
create_tables_if_not_exists
run_application

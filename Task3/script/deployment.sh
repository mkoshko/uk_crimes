#!/bin/bash
quiet=" -q "
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
anazyleQuery=0
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
  echo "  -q <index>,            Index of a query (1-6)."
  echo "  -r <number>,           Number of rows to output. Default value is 0."
  echo "  -v,                    Display non-script output."
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
  psql"$quiet"-U "$dbuser" "$dbname" -q -f "$project_folder/sql/create_schema_script.sql"
  psql"$quiet"-U "$dbuser" "$dbname" -q -f "$project_folder/sql/stop-and-search.sql"
  echo "Done."
}

create_tables_if_not_exists() {
  local tables=$(psql -U "$dbuser" "$dbname" -c \\dt)
  if [[ -z "$tables" ]]; then
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
  psql"$quiet"-U "$dbuser" "$dbname" -c "$1"
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
  mvn"$quiet"-f "$project_folder" clean package
  echo "Done"
}

run_application() {
  if [[ "$selectedApi" == "all" ]]; then
    for i in "${api[@]}"; do
      java -jar "$project_folder/target/crime.jar" -Dapi="$i" -Dfile="$project_folder/data/LondonStations.csv" -Dfrom=2019-01 -Dto=2019-05
    done
  elif [[ $selectedApi -eq 0 ]]; then
    return 0
  else
    java -jar "$project_folder/target/crime.jar" -Dapi="${api[selectedApi]}" -Dfile="$project_folder/data/LondonStations.csv" -Dfrom=2019-01 -Dto=2019-05
  fi
}

analyze() {
  if [[ $anazyleQuery -eq 0 ]]; then
    return 0;
  fi
  java -jar "$project_folder/target/crime.jar" -Dapi="$anazyleQuery" -Drows="$rows" -Dfile="$project_folder/sql/queries/"
}

parse_arguments() {
  while getopts dbvhr:q:f:w: opt "$@"; do
  case $opt in
    f)
    project_folder="$OPTARG"
    ;;
    v)
    quiet=" "
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
    r)
    rows=$OPTARG
    ;;
    q)
    anazyleQuery="$OPTARG"
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
check_postgres
create_tables_if_not_exists
run_application
analyze

#!/bin/bash

QUIET_VERBOSE="-q"
SILENT="-s"
SKIP_MAVEN=0
SKIP_JAVA=0
SKIP_POSTGRES=0
SKIP_GIT=0

help() {
  echo "Usage: setup [options]"
  echo "Install java, git, maven, postgresql, set all required environment variables and start required services."
  echo "Options:"
  echo "  -J,       skip java installation."
  echo "  -M,       skip maven installation."
  echo "  -P,       skip postgresql installation."
  echo "  -G,       skip git installation."
  echo "  -h,       display this help and exit."
  echo "  -v,       display non-script output."
}

parse_arguments() {
  while getopts JMPGhv opt "$@"; do
  case $opt in
    v | --verbose)
    QUIET_VERBOSE="-v"
    SILENT="-"
    ;;
    M)
    SKIP_MAVEN=1
    ;;
    P)
    SKIP_POSTGRES=1
    ;;
    J)
    SKIP_JAVA=1
    ;;
    G)
    SKIP_GIT=1
    ;;
    h)
    help
    exit 0
    ;;
  esac
  done
}

lang_script() {
  echo "Setting up language variables..."
  export LANG=en_US.UTF-8
  export LANGUAGE=en_US.UTF-8
  export LC_COLLATE=C
  export LC_CTYPE=en_US.UTF-8
  echo "Done."
}

packages_install() {
  echo "Installing required packages..."
  yum "$QUIET_VERBOSE" -t -y install curl-devel expat-devel gettext-devel openssl-devel zlib-devel
  yum "$QUIET_VERBOSE" -t -y install gcc perl-ExtUtils-MakeMaker
  echo "Done."
}

postgres_install() {
  if [[ $SKIP_POSTGRES = 1 ]]; then
    echo "Skip postgres installation."
    return 0
  fi
  echo "Installing postgresql10..."
  yum "$QUIET_VERBOSE" -t -y install https://download.postgresql.org/pub/repos/yum/reporpms/EL-6-x86_64/pgdg-redhat-repo-latest.noarch.rpm
  yum "$QUIET_VERBOSE" -t -y install postgresql10 postgresql10-server
  echo "Done."
  echo "Starting postgres service..."
  service postgresql-10 initdb
  service postgresql-10 start
  echo "Done."
}

maven_install() {
  if [[ $SKIP_MAVEN = 1 ]]; then
    echo "Skip maven installation."
    return 0
  fi

  local MAVEN_FOLDER="apache-maven-3.6.3"
  local MAVEN_ARCHIVE="apache-maven-3.6.3-bin.tar.gz"
  local MAVEN_DOWNLOAD_URL="http://ftp.byfly.by/pub/apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz"

  echo "Installing Apache Maven..."

  if [[ ! (-f "$MAVEN_ARCHIVE") ]]; then
    echo "Downloading archive..."
    curl "$SILENT"LO "$MAVEN_DOWNLOAD_URL"
  else
    echo "Archive already exists."
  fi

  if [[ ! ($?) ]]; then
    echo "Error while downloading archive from $MAVEN_DOWNLOAD_URL"
    return 1;
  fi

  echo "Extracting archive..."
  tar "$VERBOSE"xzf "$MAVEN_ARCHIVE" -C /opt

  if [[ ! ($?) ]]; then
    echo "Error during extracting $MAVEN_ARCHIVE."
    return 1
  fi
  rm -f "$MAVEN_ARCHIVE"

  echo "Configuring required environment variables..."

  local temp=$(cat /root/.bashrc | grep "$MAVEN_FOLDER")
  if [[ -z $temp ]]; then
    local M2_HOME="/opt/apache-maven-3.6.3"
    echo "Setting up M2_HOME environment variable..."
    echo "export M2_HOME=$M2_HOME" >> "/root/.bashrc"
    echo "export PATH=$M2_HOME/bin:\$PATH" >> "/root/.bashrc"
    echo "Done."
  else
    echo "Environment variables already setted up."
  fi
}

git_install() {
  if [[ $SKIP_GIT = 1 ]]; then
    echo "Skip git installation."
    return 0
  fi
  local git_archive="git-2.25.0.tar.gz"
  echo "Installing git..."
  packages_install
  if [[ ! -e "$git_archive" ]]; then
    echo "Downloading git-2.25.0 ..."
    curl "$SILENT"LO https://mirrors.edge.kernel.org/pub/software/scm/git/git-2.25.0.tar.gz
    echo "Done."
  fi
  echo "Extracting archive..."
  tar "$VERBOSE"xzf git-2.25.0.tar.gz -C /usr/src
  echo "Done."
  echo "Compiling the source code..."
  make -C /usr/src/git-2.25.0 prefix=/usr/local/git all
  make -C /usr/src/git-2.25.0 prefix=/usr/local/git install
  echo "Done."
  local git_path=$(cat /root/.bashrc | grep "/usr/local/git/bin")
  if [[ -z $git_path ]]; then
    echo "Setting up PATH variable..."
    echo "export PATH=/usr/local/git/bin:\$PATH" >> "/root/.bashrc"
    echo "Done."
  fi
  echo "Cleanup actions, one moment."
  rm -f "$git_archive"
  rm -rf /usr/src/git-2.25.0
  echo "Done."
  echo "Git sucessfully installed."
}

java_install() {
  if [[ $SKIP_JAVA = 1 ]]; then
    echo "Skip java installation."
    return 0
  fi
  echo "Installing java..."
  local JAVA_ARCHIVE="openjdk-11.0.2_linux-x64_bin.tar.gz"
  if [[ ! -e "$JAVA_ARCHIVE" ]]; then
    echo "Downloading openjdk-11.0.2..."
    curl "$SILENT"LO https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_linux-x64_bin.tar.gz
  fi
  echo "Extracting archive..."
  mkdir -p /usr/lib/jvm/jdk-11
  tar "$VERBOSE"xzf "$JAVA_ARCHIVE" -C /usr/lib/jvm
  mv /usr/lib/jvm/jdk-11.0.2/* /usr/lib/jvm/jdk-11
  rm -rf /usr/lib/jvm/jdk-11.0.2
  rm -f "$JAVA_ARCHIVE"
  local java_folder="/usr/lib/jvm/jdk-11"
  local java_home=$(cat /root/.bashrc | grep "$java_folder")
  if [[ -z $java_home ]]; then
    local JAVA_HOME="/usr/lib/jvm/jdk-11"
    echo "Setting up JAVA_HOME environment variable..."
    echo "export JAVA_HOME=$JAVA_HOME" >> "/root/.bashrc"
  fi
  local java_path=$(cat /root/.bashrc | grep "$java_folder/bin")
  if [[ -z $java_path ]]; then
    echo "Setting up PATH variable..."
    echo "export PATH=$java_folder/bin:\$PATH" >> "/root/.bashrc"
  fi
  echo "JDK 11 was succesfully installed."
}

parse_arguments "$@"
lang_script

java_install
maven_install
git_install
postgres_install
exec bash
#!/bin/sh

usage() {
    echo "Usage: sh run.sh [ --start | --stop | --restart ] [ redis | api ]"
    exit 1
}

execute() {
    action=$1
    module=$2

    case $module in
        "redis")
            container="weather-service-redis"
        ;;
        "api")
            container="weather-service-api"
        ;;
        *)
            usage
        ;;
    esac

    echo "Performing action: $action on module: $module (Container: $container)"
    docker $action $container
}

case $1 in
    "--start")
        execute "start" $2
    ;;
    "--stop")
        execute "stop" $2
    ;;
    "--restart")
        execute "restart" $2
    ;;
    *)
        usage
    ;;
esac

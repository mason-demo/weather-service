#!/bin/bash

clean() {
    echo "==== Performing Clean ===="
    rm -rf artifacts
    mkdir -p artifacts/jar artifacts/docker
}

maven_build() {
    echo "==== Performing Maven Build... ===="
    ./mvnw clean package
    echo "==== Maven Build Completed! ===="
}

copy_docker() {
    echo "==== Copying docker folder to the artifacts folder ===="
    cp -r ./docker/* ./artifacts/docker
}

__copy_each_jar() {
    module_dir=$1
    file_name=$2

    echo "==== Begin to copy $file_name ===="

    mkdir -p "./artifacts/docker/$module_dir/jar"
    cp "$module_dir/target/*.jar" "./artifacts/docker/$module_dir/jar"
    cp "$module_dir/target/*.jar" "./artifacts/jar"
}

copy_jar() {
    echo "==== Copying jars ===="

    rm -rf "./docker/jar"
    rm -rf "./artifacts/jar"

    mkdir -p "./docker/jar"
    mkdir -p "./artifacts/jar"

    cp "./weather-service-api/target/weather-service-api-0.1.jar" "./docker/jar"
    cp "./weather-service-api/target/weather-service-api-0.1.jar" "./artifacts/jar"
}

zip_docker() {
    echo "==== Zipping the artifacts/docker folder ===="

    tar -czvf ./artifacts/docker.tar.gz -C ./artifacts/docker .
    rm -rf ./artifacts/docker
    mkdir ./artifacts/docker
    mv ./artifacts/docker.tar.gz ./artifacts/docker
}

# Main script starts here
echo "==== 🚀🚀 Running build process 🚀🚀 ===="

clean
maven_build
copy_jar
copy_docker
zip_docker

echo "==== ✅🎉 Build process completed successfully! 🎉✅ ===="

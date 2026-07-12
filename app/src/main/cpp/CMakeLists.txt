cmake_minimum_required(VERSION 3.22)
project(cpputils_android)

# Bring in cpputils
add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/acl-libcpputils)

# Build JNI bridge
add_library(${PROJECT_NAME} SHARED jni/asymmetric_key.cpp)
target_include_directories(${PROJECT_NAME} PRIVATE
    acl-libcpputils/code/lib/include
    acl-libcpputils/dep/android/openssl/${ANDROID_ABI}/include)
target_link_libraries(${PROJECT_NAME} cpputils)
cmake_minimum_required(VERSION 3.22)
project(logos_android)

# Bring in cpputils
add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/acl-libcpputils)

# Build JNI bridge
add_library(native-lib SHARED jni/native-lib.cpp)
target_include_directories(native-lib PRIVATE
    acl-libcpputils/code/lib/include
    acl-libcpputils/dep/android/openssl/${ANDROID_ABI}/include)
target_link_libraries(native-lib cpputils)
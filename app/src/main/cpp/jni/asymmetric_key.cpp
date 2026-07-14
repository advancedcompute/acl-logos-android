
#include <jni.h>
#include <string>

#include "ed25519.h"


extern "C"
JNIEXPORT jlong JNICALL
Java_com_advancedcomputation_logos_1android_crypto_AsymmetricKey_create_1key(JNIEnv *env, jobject thiz)
{
    auto edPtr = new cpp::utils::ED25519;
    edPtr->generate_keypair();

    jlong ptr = reinterpret_cast<jlong>(edPtr);
    return ptr;
}



extern "C"
JNIEXPORT void JNICALL
Java_com_advancedcomputation_logos_1android_crypto_AsymmetricKey_destroy_1key(JNIEnv *env, jobject thiz, jlong handle)
{
    auto ptr = reinterpret_cast<cpp::utils::ED25519*>(handle);
    delete ptr;
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_advancedcomputation_logos_1android_crypto_AsymmetricKey_get_1public_1key_1pem(JNIEnv *env, jobject thiz, jlong handle)
{
    auto ptr = reinterpret_cast<cpp::utils::ED25519*>(handle);
    if(ptr)
    {
        std::string output;
        if(ptr->get_public_key_pem(output))
        {
            return env->NewStringUTF(output.c_str());
        }
    }
    return 0L;
}

extern "C"
JNIEXPORT bool JNICALL
        Java_com_advancedcomputation_logos_1android_crypto_AsymmetricKey_set_1public_1key_1pem(JNIEnv *env, jobject thiz, jlong handle, jstring pem)
{
    auto ptr = reinterpret_cast<cpp::utils::ED25519*>(handle);
    if(ptr)
    {
        const char* chars = env->GetStringUTFChars(pem, nullptr);
        std::string pemStr(chars);
        return ptr->set_public_key_pem(pemStr);
    }
    return false;
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_advancedcomputation_logos_1android_crypto_AsymmetricKey_get_1private_1key_1pem(JNIEnv *env, jobject thiz, jlong handle, jstring pem)
{
    auto ptr = reinterpret_cast<cpp::utils::ED25519*>(handle);
    if(ptr)
    {
        std::string output;
        if(ptr->get_private_key_pem(output))
        {
            return env->NewStringUTF(output.c_str());
        }
    }
    return env->NewStringUTF("");
}

extern "C"
JNIEXPORT bool JNICALL
        Java_com_advancedcomputation_logos_1android_crypto_AsymmetricKey_set_1private_1key_1pem(JNIEnv *env, jobject thiz, jlong handle, jstring pem)
{
    auto ptr = reinterpret_cast<cpp::utils::ED25519*>(handle);
    if(ptr)
    {
        const char* chars = env->GetStringUTFChars(pem, nullptr);
        std::string pemStr(chars);
        return ptr->set_private_key_pem(pemStr);
    }
    return false;
}
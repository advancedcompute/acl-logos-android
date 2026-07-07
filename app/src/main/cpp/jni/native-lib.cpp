
#include <jni.h>
#include <string>

#include "ecc.h"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_advancedcomputation_logos_1android_MainActivity_stringFromJNI(JNIEnv* env, jobject /* this */)
{
    cpp::utils::ECC ecc;
    ecc.generate_own_keypair();

    std::string pemContent;
    ecc.get_own_public_key_pem(pemContent);
    return env->NewStringUTF(pemContent.c_str());
}

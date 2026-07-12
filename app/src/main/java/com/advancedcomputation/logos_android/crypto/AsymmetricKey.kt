package com.advancedcomputation.logos_android.crypto

import java.io.File


class AsymmetricKey {
    private var keyHandle: Long = 0L

    external fun create_key(): Long
    external fun destroy_key(handle: Long)

    external fun get_public_key_pem(handle: Long): String
    external fun get_private_key_pem(handle: Long): String

    fun GetPublicKeyPEM(): String
    {
        return get_public_key_pem(keyHandle)
    }

    fun GetPrivateKeyPEM(): String
    {
        return get_private_key_pem(keyHandle)
    }


    public fun CreateKey()
    {
        keyHandle = create_key()
    }

    public fun DestroyKey()
    {
        if(keyHandle != 0L) {
            destroy_key(keyHandle)
        }
    }





    public fun Handle(): Long { return keyHandle }

    fun close()
    {
        if(keyHandle != 0L) {
            destroy_key(keyHandle)
        }
    }

}
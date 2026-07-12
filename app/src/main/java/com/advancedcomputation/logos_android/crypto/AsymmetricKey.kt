package com.advancedcomputation.logos_android.crypto

import java.io.File


class AsymmetricKey {
    private var keyHandle: Long = 0L

    external fun create_key(): Long
    external fun destroy_key(handle: Long)

    external fun get_public_key_pem(handle: Long): String
    external fun get_private_key_pem(handle: Long): String

    external fun set_public_key_pem(handle: Long, pubKey: String): Boolean

    external fun set_private_key_pem(handle: Long, privKey: String): Boolean

    fun GetPublicKeyPEM(): String
    {
        return get_public_key_pem(keyHandle)
    }

    fun GetPrivateKeyPEM(): String
    {
        return get_private_key_pem(keyHandle)
    }

    fun LoadKey(pubKey: String, privKey: String)
    {
        set_public_key_pem(keyHandle, pubKey)
        set_private_key_pem(keyHandle, privKey)
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
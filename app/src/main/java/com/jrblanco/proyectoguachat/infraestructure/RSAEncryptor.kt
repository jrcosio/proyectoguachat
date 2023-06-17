package com.jrblanco.proyectoguachat.infraestructure

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import java.util.Base64

/**
 * Clase que proporciona métodos para encriptar y desencriptar mensajes utilizando el algoritmo RSA.
 */
class RSAEncryptor {
    private val algoritmo = "RSA"

    private val keyGen = KeyPairGenerator.getInstance(algoritmo)
    private val cipher = Cipher.getInstance(algoritmo)
    private val keyFactory = KeyFactory.getInstance(algoritmo)

    init {
        keyGen.initialize(1024)
    }

    /**
     * Genera un par de claves pública y privada.
     *
     * @return el par de claves generado.
     */
    fun generateKeyPair(): KeyPair = keyGen.generateKeyPair()

    /**
     * Encripta un mensaje utilizando una clave privada.
     *
     * @param msg el mensaje a encriptar.
     * @param key la clave privada utilizada para la encriptación.
     * @return el mensaje encriptado en formato Base64.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(msg: String, key: PrivateKey): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return Base64.getEncoder().encodeToString(cipher.doFinal(msg.toByteArray()))
    }

    /**
     * Desencripta un mensaje utilizando una clave pública.
     *
     * @param msg el mensaje encriptado en formato Base64.
     * @param key la clave pública utilizada para la desencriptación.
     * @return el mensaje desencriptado.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(msg: String, key: PublicKey): String {
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedMsg = Base64.getDecoder().decode(msg)
        return String(cipher.doFinal(decodedMsg))
    }

    /**
     * Convierte una clave privada a una cadena de texto en formato Base64.
     *
     * @param privateKey la clave privada a convertir.
     * @return la representación en formato Base64 de la clave privada.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun privateKeyToString(privateKey: PrivateKey): String {
        return Base64.getEncoder().encodeToString(privateKey.encoded)
    }

    /**
     * Convierte una clave pública a una cadena de texto en formato Base64.
     *
     * @param publicKey la clave pública a convertir.
     * @return la representación en formato Base64 de la clave pública.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun publicKeyToString(publicKey: PublicKey): String {
        return Base64.getEncoder().encodeToString(publicKey.encoded)
    }

    /**
     * Convierte una cadena de texto en formato Base64 a una clave privada.
     *
     * @param privateKey la cadena de texto en formato Base64 que representa la clave privada.
     * @return la clave privada generada a partir de la cadena de texto.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToPrivateKey(privateKey: String): PrivateKey {
        val byteKey = Base64.getDecoder().decode(privateKey.toByteArray())
        val spec = PKCS8EncodedKeySpec(byteKey)
        return keyFactory.generatePrivate(spec)
    }

    /**
     * Convierte una cadena de texto en formato Base64 a una clave pública.
     *
     * @param publicKey la cadena de texto en formato Base64 que representa la clave pública.
     * @return la clave pública generada a partir de la cadena de texto.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToPublicKey(publicKey: String): PublicKey {
        val byteKey = Base64.getDecoder().decode(publicKey.toByteArray())
        val spec = X509EncodedKeySpec(byteKey)
        return keyFactory.generatePublic(spec)
    }
}

/*
val rsaCipher = RSACipher()

// Generar pares de claves para los usuarios A y B
val keyPairA = rsaCipher.generateKeyPair()
val keyPairB = rsaCipher.generateKeyPair()

// Usuario A cifra un mensaje y usuario B lo descifra
val encryptedMsg = rsaCipher.encrypt("Hola, soy el usuario A", keyPairA.private)
val decryptedMsg = rsaCipher.decrypt(encryptedMsg, keyPairA.public)
println("Mensaje descifrado: $decryptedMsg")

// Usuario B cifra un mensaje y usuario A lo descifra
val encryptedMsgB = rsaCipher.encrypt("Hola, soy el usuario B", keyPairB.private)
val decryptedMsgB = rsaCipher.decrypt(encryptedMsgB, keyPairB.public)
println("Mensaje descifrado: $decryptedMsgB")

 */

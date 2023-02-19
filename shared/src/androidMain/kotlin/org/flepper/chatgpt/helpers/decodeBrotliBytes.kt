package org.flepper.chatgpt.helpers

import org.brotli.dec.BrotliInputStream
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

actual fun decodeBrotliBytes(byteArray: ByteArray): String {
    return BufferedReader(InputStreamReader(BrotliInputStream(ByteArrayInputStream(byteArray)))).readText()
}
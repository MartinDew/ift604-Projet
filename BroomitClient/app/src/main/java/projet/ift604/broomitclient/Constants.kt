package projet.ift604.broomitclient

val API_URL = "http://10.0.2.2:5150"

// Extension to ByteArray to convert it to a Hex string
fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
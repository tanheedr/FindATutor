<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();

    $plaintext = $_POST["Message"];
    $plaining = "AES-128-CTR"; // Stores cipher method
    $option = 0; // Holds bitwise disjunction of flags
    $encryption_key = "8y/B?E(H+MbQeThW"; // 128 bit key
    $encryption_iv = "9432982127646892"; // Initialization Vector
    $encryption = openssl_encrypt($plaintext, $plaining, $encryption_key, $option, $encryption_iv);
    
    $db -> sendMessage(
            $_POST["SenderID"],
            $_POST["ReceiverID"],
            $encryption);
}else{
    echo "Invalid Request";
}

?>
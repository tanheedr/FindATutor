<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    $db = new Operations();
    $chat = $db -> getChat(htmlspecialchars($_GET["SenderID"]), htmlspecialchars($_GET["RecipientID"]));
    for ($i = 0; $i < sizeof($chat); $i++){
        $response[$i]["SenderID"] = $chat[$i]["SenderID"];
        
        $ciphertext = $chat[$i]["Message"];
        $ciphering = "AES-128-CTR"; // Stores cipher method
        $option = 0; // Holds bitwise disjunction of flags
        $decryption_key = "8y/B?E(H+MbQeThW"; // 128 bit key
        $decryption_iv = "9432982127646892"; // Initialization Vector
        $decryption = openssl_decrypt($ciphertext, $ciphering, $decryption_key, $option, $decryption_iv);

        $response[$i]["Message"] = $decryption;
        $response[$i]["Timestamp"] = $chat[$i]["Timestamp"];
    }
}else{
    $response["Message"] = "Invalid request";
}

echo json_encode($response);

?>
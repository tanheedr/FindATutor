<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    $db = new Operations();
    $user = $db -> getUsers($_GET["ID"]);
    for ($i = 0; $i < sizeof($user); $i++){
        $response[$i]["RecipientID"] = $user[$i]["ID"];
        $response[$i]["FirstName"] = $user[$i]["FirstName"];
        $response[$i]["Surname"] = $user[$i]["Surname"];
        $response[$i]["Photo"] = $user[$i]["Photo"];

        //$response[$i]["Message"] = $user[$i]["Message"];
        //$response[$i]["Message"] = $chat[$i]["Message"];
        $ciphertext = $user[$i]["Message"];
        $ciphering = "AES-128-CTR"; // Stores cipher method
        $option = 0; // Holds bitwise disjunction of flags
        $decryption_key = "8y/B?E(H+MbQeThW"; // 128 bit key
        $decryption_iv = "9432982127646892"; // Initialization Vector
        $decryption = openssl_decrypt($ciphertext, $ciphering, $decryption_key, $option, $decryption_iv);

        $response[$i]["Message"] = $decryption;
        $response[$i]["Timestamp"] = $user[$i]["Timestamp"];
    }
}else{
    $response["Message"] = "Invalid request";
}

echo json_encode($response);

?>
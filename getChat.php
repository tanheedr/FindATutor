<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    $db = new Operations();
    $chat = $db -> getChat($_GET["SenderID"], $_GET["RecipientID"]);
    for ($i = 0; $i < sizeof($chat); $i++){
        $response[$i]["SenderID"] = $chat[$i]["SenderID"];
        $response[$i]["Message"] = $chat[$i]["Message"];
        $response[$i]["Timestamp"] = $chat[$i]["Timestamp"];
    }
}else{
    $response["Message"] = "Invalid request";
}

echo json_encode($response);

?>
<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    $db = new Operations();
    $user = $db -> getAllMessages($_GET["ID"]);
    for ($i = 0; $i < sizeof($user); $i++){
        $response[$i]["RecipientID"] = $user[$i]["ID"];
        $response[$i]["FirstName"] = $user[$i]["FirstName"];
        $response[$i]["Surname"] = $user[$i]["Surname"];
        $response[$i]["Photo"] = $user[$i]["Photo"];
        $response[$i]["Message"] = $user[$i]["Message"];
        $response[$i]["Timestamp"] = $user[$i]["Timestamp"];
    }
}else{
    $response["Message"] = "Invalid request";
}

echo json_encode($response);

?>
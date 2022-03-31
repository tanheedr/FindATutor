<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    $db = new Operations();
    $user = $db -> getNames(htmlspecialchars($_GET["ID"]));
    for ($i = 0; $i < sizeof($user); $i++){
        $response[$i]["ID"] = $user[$i]["ID"];
        $response[$i]["FirstName"] = $user[$i]["FirstName"];
        $response[$i]["Surname"] = $user[$i]["Surname"];
    }
}else{
    $response["Message"] = "Invalid request";
}

echo json_encode($response);

?>
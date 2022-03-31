<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $user = $db -> getParentData(htmlspecialchars($_POST["ID"]));
    $response["Photo"] = $user["Photo"];
    $response["FirstName"] = $user["FirstName"];
    $response["Surname"] = $user["Surname"];
}else{
    $response["Message"] = "Invalid request";
}

echo json_encode($response);

?>
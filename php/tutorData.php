<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $user = $db -> getTutorData($_POST["ID"]);
    $response["Photo"] = substr($user["Photo"], 0, -2);
    $response["Subjects"] = $user["Subjects"];
    $response["HourlyCost"] = $user["HourlyCost"];
    $response["Qualifications"] = $user["Qualifications"];
    $response["Description"] = $user["Description"];
}else{
    $response["Message"] = "Invalid request";
}

echo json_encode($response);

?>
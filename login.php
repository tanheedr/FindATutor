<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    if($db -> loginUser(htmlspecialchars($_POST["Email"]), htmlspecialchars($_POST["Password"]))){
        $user = $db -> getUserData(htmlspecialchars($_POST["Email"]));
        $response["ID"] = $user["ID"];
        $response["AccountType"] = $user["AccountType"];
        $response["Message"] = "Login Successful";
    }else{
        $response = "Invalid details";
        }
}else{
    $response = "Invalid request";
}

if (count((array)$response) == 1){
    echo $response;
}else{
    echo json_encode($response);
}

?>
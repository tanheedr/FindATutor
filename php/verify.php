<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    $db = new Operations();
    if(isset($_GET["VerificationKey"])){
        $response = $db -> verify($_GET["VerificationKey"]);
    }else{
        $response = "Error";
    }
}else{
    $response = "Invalid request";
}

echo $response;

?>
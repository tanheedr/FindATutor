<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> forgetPassword($_POST["Email"]);
}else{
    echo "Invalid Request";
}

?>
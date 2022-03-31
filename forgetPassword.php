<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> forgetPassword(htmlspecialchars($_POST["Email"]));
}else{
    echo "Invalid Request";
}

?>
<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> editParentData(
        $_POST["ID"],
        $_POST["Photo"]);
}else{
    echo "Invalid Request";
}

?>
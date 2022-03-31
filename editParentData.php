<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> editParentData(
        htmlspecialchars($_POST["ID"]),
        htmlspecialchars($_POST["Photo"]));
}else{
    echo "Invalid Request";
}

?>
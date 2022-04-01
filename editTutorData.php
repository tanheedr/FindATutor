<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> editTutorData(
        $_POST["ID"],
        $_POST["Photo"],
        $_POST["Subjects"],
        $_POST["HourlyCost"],
        $_POST["Qualifications"],
        $_POST["Description"]);
}else{
    echo "Invalid Request";
}

?>
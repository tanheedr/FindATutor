<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> editTutorData(
        htmlspecialchars($_POST["ID"]),
        htmlspecialchars($_POST["Photo"]),
        htmlspecialchars($_POST["Subjects"]),
        htmlspecialchars($_POST["HourlyCost"]),
        htmlspecialchars($_POST["Qualifications"]),
        htmlspecialchars($_POST["Description"]));
}else{
    echo "Invalid Request";
}

?>
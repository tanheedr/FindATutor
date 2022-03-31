<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> registerUser(
        htmlspecialchars($_POST["AccountType"]),
        htmlspecialchars(ucfirst($_POST["FirstName"])),
        htmlspecialchars(ucfirst($_POST["Surname"])),
        htmlspecialchars($_POST["Email"]),
        htmlspecialchars($_POST["Password"]),
        htmlspecialchars($_POST["ConfirmPassword"]));
}else{
    echo "Invalid Request";
}

?>
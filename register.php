<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> registerUser(
            $_POST["AccountType"],
            ucfirst($_POST["FirstName"]),
            ucfirst($_POST["Surname"]),
            $_POST["Email"],
            $_POST["Password"],
            $_POST["ConfirmPassword"]);
}else{
    echo "Invalid Request";
}

?>
<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> sendMessage(
            $_POST["SenderID"],
            $_POST["ReceiverID"],
            $_POST["Message"]);
}else{
    echo "Invalid Request";
}

?>
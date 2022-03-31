<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> setSession(
        htmlspecialchars($_POST["TutorID"]),
        htmlspecialchars($_POST["ParentID"]),
        htmlspecialchars($_POST["StartTime"]),
        htmlspecialchars($_POST["EndTime"]));
}else{
    echo "Invalid Request";
}

?>
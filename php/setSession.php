<?php

require_once "operations.php";

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    $db -> setSession(
            $_POST["TutorID"],
            $_POST["ParentID"],
            $_POST["StartTime"],
            $_POST["EndTime"]);
}else{
    echo "Invalid Request";
}

?>
<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    $db = new Operations();
    $session = $db -> getSessions($_GET["ID"], $_GET["Date"]);
    for ($i = 0; $i < sizeof($session); $i++){
        $response[$i]["ID"] = $session[$i]["ID"];
        $response[$i]["FirstName"] = $session[$i]["FirstName"];
        $response[$i]["Surname"] = $session[$i]["Surname"];
        $response[$i]["StartTime"] = $session[$i]["StartTime"];
        $response[$i]["EndTime"] = $session[$i]["EndTime"];
    }
}else{
    $response["Message"] = "Invalid request";
}

echo json_encode($response);

?>
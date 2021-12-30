<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "GET"){
    $db = new Operations();
    if(isset($_GET["Subjects"])){
        $tutor = $db -> getTutorsWithSearch($_GET["ID"], $_GET["Subjects"]);
        for ($i = 0; $i < sizeof($tutor); $i++){
            $response[$i]["ID"] = $tutor[$i]["ID"];
            $response[$i]["FirstName"] = $tutor[$i]["FirstName"];
            $response[$i]["Surname"] = $tutor[$i]["Surname"];
            $response[$i]["Photo"] = $tutor[$i]["Photo"];
            $response[$i]["Subjects"] = $tutor[$i]["Subjects"];
            $response[$i]["HourlyCost"] = $tutor[$i]["HourlyCost"];
            $response[$i]["Qualifications"] = $tutor[$i]["Qualifications"];
            $response[$i]["Description"] = $tutor[$i]["Description"];
        }
        echo json_encode($response);
    }else{
    $tutor = $db -> getTutorsWithoutSearch($_GET["ID"]);
        for ($i = 0; $i < sizeof($tutor); $i++){
            $response[$i]["ID"] = $tutor[$i]["ID"];
            $response[$i]["FirstName"] = $tutor[$i]["FirstName"];
            $response[$i]["Surname"] = $tutor[$i]["Surname"];
            $response[$i]["Photo"] = $tutor[$i]["Photo"];
            $response[$i]["Subjects"] = $tutor[$i]["Subjects"];
            $response[$i]["HourlyCost"] = $tutor[$i]["HourlyCost"];
            $response[$i]["Qualifications"] = $tutor[$i]["Qualifications"];
            $response[$i]["Description"] = $tutor[$i]["Description"];
        }
        echo json_encode($response);
    }
}else{
    $response["Message"] = "Invalid request";
    echo json_encode($response);
}

?>
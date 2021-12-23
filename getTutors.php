<?php

require_once "operations.php";

$response = array();

if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    if(isset($_POST["Subjects"])){
        $tutor = $db -> getTutorsWithSearch($_POST["Subjects"]);
        for ($i = 0; $i < sizeof($tutor); $i++){
            $response[$i]["FirstName"] = $tutor[$i][0];
            $response[$i]["Surname"] = $tutor[$i][1];
            $response[$i]["Photo"] = $tutor[$i][2];
            $response[$i]["Subjects"] = $tutor[$i][3];
            $response[$i]["HourlyCost"] = $tutor[$i][4];
            $response[$i]["Qualifications"] = $tutor[$i][5];
            $response[$i]["Description"] = $tutor[$i][6];
        }
        echo json_encode($response);
    }else{
        $tutor = $db -> getTutorsWithoutSearch();
        for ($i = 0; $i < sizeof($tutor); $i++){
            $response[$i]["FirstName"] = $tutor[$i][0];
            $response[$i]["Surname"] = $tutor[$i][1];
            $response[$i]["Photo"] = $tutor[$i][2];
            $response[$i]["Subjects"] = $tutor[$i][3];
            $response[$i]["HourlyCost"] = $tutor[$i][4];
            $response[$i]["Qualifications"] = $tutor[$i][5];
            $response[$i]["Description"] = $tutor[$i][6];
        }
        echo json_encode($response);
    }
}else{
    $response["Message"] = "Invalid request";
}

/*require "connection.php";

if (isset($_GET['subject'])){
    $subject = $_GET['subject'];
    $query = "SELECT FirstName, Surname, Photo, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE Subjects LIKE '%$subject%' AND TutorID = ID";
    $result = mysqli_query($connection, $query);
    $response = array();
    while($row = mysqli_fetch_assoc($result)){
        array_push($response,
        array(
            'FirstName' => $row['FirstName'],
            'Surname' => $row['Surname'],
            'Photo' => $row['Photo'],
            'Subjects' => $row['Subjects'],
            'HourlyCost' => $row['HourlyCost'],
            'Qualifications' => $row['Qualifications'],
            'Description' => $row['Description'])
        );
    }
    echo json_encode($response);
}else{
    $query = "SELECT FirstName, Surname, Photo, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE TutorID = ID";
    $result = mysqli_query($connection, $query);
    $response = array();
    while($row = mysqli_fetch_assoc($result)){
        array_push($response,
        array(
            'FirstName' => $row['FirstName'],
            'Surname' => $row['Surname'],
            'Photo' => $row['Photo'],
            'Subjects' => $row['Subjects'],
            'HourlyCost' => $row['HourlyCost'],
            'Qualifications' => $row['Qualifications'],
            'Description' => $row['Description'])
        );
    }
    echo json_encode($response);
}

mysqli_close($connection);*/

?>
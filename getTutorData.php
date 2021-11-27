<?php

require_once "connection.php";

if (isset($_GET['subject'])){
    $subject = $_GET['subject'];
    $query = "SELECT FirstName, Surname, Subjects, HourlyCost, Qualifications, Description FROM accounts, tutordescription WHERE Subjects LIKE '%$subject%' AND TutorID = ID";
    $result = mysqli_query($connection, $query);
    $response = array();
    while($row = mysqli_fetch_assoc($result)){
        array_push($response,
        array(
            'TutorID' = $row['TutorID'],
            'Subjects' = $row['Subjects'],
            'HourlyCost' = $row['HourlyCost'],
            'Qualifications' = $row['Qualifications'],
            'Description' = $row['Description'])
        );
    }
    echo json_encode($response);
}else{
    $query = "SELECT * FROM tutordescription";
    $result = mysqli_query($connection, $query);
    $response = array()
    while($row = mysqli_fetch_assoc($result)){
        array_push($response,
        array(
            'TutorID' = $row['TutorID'],
            'Subjects' = $row['Subjects'],
            'HourlyCost' = $row['HourlyCost'],
            'Qualifications' = $row['Qualifications'],
            'Description' = $row['Description'])
        );
    }
    echo json_encode($response);
}

mysqli_close($connection);

?>
<?php

require "connection.php";
//header("Content-Type: application/json");
//header("Access-Control-Allow-Origin: *");

$photo = $_POST["Photo"];
$subjects = $_POST["Subjects"];
$hourlyCost = $_POST["HourlyCost"];
$qualifications = $_POST["Qualifications"];
$description = $_POST["Description"];

//echo $_SESSION['ID'][0];
$TutorID = 3;

if ($connection){
    if(preg_match("/^[0-9]+(?:\.[0-9]{1,2})?$/", $hourlyCost) !== 1){ //https://stackoverflow.com/questions/4982291/how-to-check-if-an-entered-value-is-currency
        echo "Not a valid price";
    }
    else{
        $sqlUpdateData = "UPDATE accounts, tutordescription SET Photo = '$photo', Subjects = '$subjects', HourlyCost = '$hourlyCost', Qualifications = '$qualifications', Description = '$description' WHERE TutorID =  '$TutorID' AND TutorID = ID";
        if (mysqli_query($connection, $sqlUpdateData)){
            echo "Successfully Updated";
        }
        else{
            echo "Updating Error";
        }
    }
}

?>
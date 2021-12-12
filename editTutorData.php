<?php

require "connection.php";

$photo = $_POST["Photo"];
$subjects = $_POST["Subjects"];
$hourlyCost = $_POST["HourlyCost"];
$qualifications = $_POST["Qualifications"];
$description = $_POST["Description"];

if ($connection){
    if(preg_match("/^[0-9]+(?:\.[0-9]{1,2})?$/", $hourlyCost) !== 1){ //https://stackoverflow.com/questions/4982291/how-to-check-if-an-entered-value-is-currency
        echo "Not a valid price";
    }
    else{
        $sqlUpdateData = "UPDATE tutordescription SET Photo = '$photo', Subjects = '$subjects', HourlyCost = '$hourlyCost', Qualifications = '$qualifications', Description = '$description' WHERE TutorID = 3";
        if (mysqli_query($connection, $sqlUpdateData)){
            echo "Successfully Updated";
        }
        else{
            echo "Updating Error";
        }
    }
}

?>
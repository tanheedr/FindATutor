<?php

require "connection.php";

$subjects = $_POST["Subjects"];
$hourlyCost = $_POST["HourlyCost"];
$qualifications = $_POST["Qualifications"];
$description = $_POST["Description"];

if ($connection){
    if($hourlyCost !== /\b\d{1,3}(?:,?\d{3})*(?:\.\d{2})?\b/){ ///https://stackoverflow.com/questions/4982291/how-to-check-if-an-entered-value-is-currency
        echo "Not a valid price";
    }
    else{
        $sqlUpdateData = "UPDATE tutordescription SET Subjects = '$subjects', HourlyCost = '$hourlyCost', Qualifications = '$qualifications', Description = '$description' WHERE TutorID = 3";
        if (mysqli_query($connection, $sqlUpdateData)){
            echo "Successfully Updated";
        }
        else{
            echo "Updating Error";
        }
    }
}

?>
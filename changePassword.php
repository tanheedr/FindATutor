<?php

require_once "operations.php";

//if($_SERVER["REQUEST_METHOD"] == "POST"){
    $db = new Operations();
    if(isset($_GET["Email"], $_POST["Password"], $_POST["ConfirmPassword"])){
        $db -> changePassword($_GET["Email"], $_POST["Password"], $_POST["ConfirmPassword"]);
    }else{
        echo "Enter All Details";
    }
/* }else{
    echo "Invalid Request";
} */

?>

<!DOCTYPE html>
<html>
    <head>
        <title>Reset Password</title>
    </head>
<body>
    <h1>Change Password</h1>
    <div class="container">
        <form method="POST" action="#">
            <div class=form_input>
                <input type="password" name="Password" placeholder="Password"/>
            </div>
            <div class=form_input>
                <input type="password" name="ConfirmPassword" placeholder="Confirm Password"/>
            </div>
            <button type="submit" name="submit" value="submit">Submit</button>
        </form>
    </div>
</body>
</html>
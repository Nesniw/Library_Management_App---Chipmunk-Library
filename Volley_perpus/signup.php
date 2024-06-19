<?php
require "Database.php";
$db = new Database();
if (isset($_POST['username']) && isset($_POST['nama_lengkap']) && isset($_POST['email']) && isset($_POST['password'])) 
{
    if ($db->dbConnect())
    {
        if ($db->signing("user", $_POST['username'],  $_POST['nama_lengkap'], $_POST['email'], $_POST['password']))
        {
            echo "Sign Up Success";
        } 
        
        else echo "Sign Up Failed";
    } 

    else echo "Error: Database Connection";
} 

else echo "All fields are required";

?>
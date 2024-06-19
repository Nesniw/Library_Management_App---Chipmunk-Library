<?php
include "koneksi.php";
require "Database.php";
$db = new Database();

if (isset($_POST['username']) && isset($_POST['password'])) {
    if ($db->dbConnect()) {
        if ($db->login("user", $_POST['username'], $_POST['password'])) {
            $result = $db->getUserID($_POST['username']); // Retrieve kd_user
            if ($result) {
                $row = mysqli_fetch_assoc($result);
                $kd_user = $row['kd_user'];
                
                // Update status_login for the kd_user
                $status = "login"; // Set the status_login value
                
                $updateQuery = "UPDATE user SET status_login='".$status."' WHERE kd_user='".$kd_user."'";
                $updateResult = mysqli_query($db->connect, $updateQuery);
                if ($updateResult) {
                    echo "Login Success";
                } else {
                    echo "Error: Failed to update status_login";
                }
            } else {
                echo "Error: Failed to get kd_user";
            }
        } else {
            echo "Username or Password wrong";
        }
    } else {
        echo "Error: Database Connection";
    }
} else {
    echo "All fields are required";
}
?>





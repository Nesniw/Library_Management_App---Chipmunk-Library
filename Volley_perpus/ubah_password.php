<?php
include 'koneksi.php';
require "Database.php";
$db = new Database();

$response = array();

if ($db->dbConnect()) {
    if (isset($_POST['old_password']) && isset($_POST['new_password'])) {
        $oldPassword = $_POST['old_password'];
        $newPassword = $_POST['new_password'];

        // Get the current user's username from the session or wherever you store it
        $status = "login";

        // Retrieve the user's data from the database
        $result = $db->getUserDataByStatus($status);
        if ($result) {
            $row = mysqli_fetch_assoc($result);
            $kd_user = $row['kd_user'];
            $hashedPassword = $row['password'];

            // Verify the old password
            if (password_verify($oldPassword, $hashedPassword)) {
                // Generate a new hashed password
                $newHashedPassword = password_hash($newPassword, PASSWORD_DEFAULT);

                // Update the user's password in the database
                $updateQuery = "UPDATE user SET password='$newHashedPassword' WHERE kd_user='".$kd_user."'";
                $updateResult = mysqli_query($db->connect, $updateQuery);
                if ($updateResult) {
                    $response["message"] = "Password changed successfully";
                    $response["success"] = true;
                } else {
                    $response["message"] = "Error: Failed to update password";
                    $response["success"] = false;
                }
            } else {
                $response["message"] = "Error: Old password is incorrect";
                $response["success"] = false;
            }
        } else {
            $response["message"] = "Error: User not found";
            $response["success"] = false;
        }
    } else {
        $response["message"] = "Error: Required fields are missing";
        $response["success"] = false;
    }
} else {
    $response["message"] = "Error: Database Connection";
    $response["success"] = false;
}

mysqli_close($conn);
echo json_encode($response);
?>
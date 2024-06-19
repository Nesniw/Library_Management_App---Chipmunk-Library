<?php
    include 'koneksi.php';
    
    $kd = $_POST['kd_user'];
    $user = $_POST['username'];
    $full = $_POST['nama_lengkap'];
    $ema = $_POST['email'];


    $query = "UPDATE user SET username='".$user."', nama_lengkap='".$full."', email='".$ema."' WHERE kd_user='".$kd."'";
    $result = mysqli_query ($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        $response['message']="Success Update";
    }
    else{
        $response['message']="Failed Update";
    }
    echo json_encode($response);
    mysqli_close($conn);
?>
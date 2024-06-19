<?php
    include 'koneksi.php';
    $kd = $_POST['kd_buku'];

    $check = mysqli_query($conn, "SELECT COUNT(*) as total FROM peminjaman WHERE kd_buku='$kd'");
    $check_result = mysqli_fetch_assoc($check);
    if ($check_result['total'] > 0) {
        $response["message"] = "Buku tidak dapat dihapus karena pernah dipinjam";
        echo json_encode($response);
        mysqli_close($conn);
    }

    $data = mysqli_query($conn, "SELECT * FROM buku WHERE kd_buku='$kd'");
    $d = mysqli_fetch_array($data);
    unlink($d['image']);

    $query = "DELETE FROM buku WHERE kd_buku = '".$kd."'";
    $result = mysqli_query($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        $response["message"] = "Success Delete";
    } else {
        $response["message"] = "Failed Delete";
    }
    echo json_encode($response);
    mysqli_close($conn);
?>
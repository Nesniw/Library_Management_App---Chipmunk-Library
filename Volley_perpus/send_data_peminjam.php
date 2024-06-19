<?php
    include 'koneksi.php';

    $nama = $_POST['nama_peminjam'];
    $notelp = $_POST['no_telp'];
    $alamat = $_POST['alamat'];
    $kd_buku = $_POST['kd_buku'];
    $status = $_POST['status'];
    $tgl_pinjam = $_POST['tgl_pinjam'];
    $statusbuku = $_POST['statusb'];



    $query = "INSERT INTO peminjaman (nama_peminjam,no_telp,alamat,tgl_pinjam,kd_buku,status) VALUES ('".$nama."','".$notelp."','".$alamat."','".$tgl_pinjam."','".$kd_buku."','".$status."')";
    $query2 = "UPDATE buku SET status='".$statusbuku."' WHERE kd_buku='".$kd_buku."'";
    $result = mysqli_query($conn, $query) or die('Error query: '.$query);
    $result2 = mysqli_query($conn, $query2) or die('Error query: '.$query2);
    if($result == 1 && $result2 == 1){
        $response["message"]="Success Insert Data";
    }
    else{
        $response["message"]="Failed To Insert Data";
    }

    echo json_encode($response);
    mysqli_close($conn);
?>
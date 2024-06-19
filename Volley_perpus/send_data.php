<?php
    include 'koneksi.php';

    $img = $_POST['image'];
    $jud = $_POST['judul'];
    $gen = $_POST['genre'];
    $pbit = $_POST['penerbit'];
    $parn = $_POST['pengarang'];
    $thn = $_POST['tahun_terbit'];
    $rak = $_POST['no_rak'];
    $isbn = $_POST['isbn'];
    $stat = $_POST['status'];

    date_default_timezone_set('Asia/Jakarta');
    $path = 'images/'.date(format:"d-m-Y-his").'-'.rand(100, 10000).'.jpg';

    $query = "INSERT INTO buku (image,judul,genre,penerbit,pengarang,tahun_terbit,stok,no_rak,isbn,status) VALUES ('".$path."','".$jud."','".$gen."','".$pbit."','".$parn."','".$thn."','".$rak."','".$isbn."','".$stat."')";
    $result = mysqli_query($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        file_put_contents($path, base64_decode($img));
        $response["message"]="Success Insert Image";
    }
    else{
        $response["message"]="Failed To Insert Image";
    }

    echo json_encode($response);
    mysqli_close($conn);
?>

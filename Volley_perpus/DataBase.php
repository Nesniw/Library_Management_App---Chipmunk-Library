<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function login($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
                $kode["kodeuser"] = $row['kd_user'];
                //echo json_encode($kode);
            } else $login = false;
        } else $login = false;

        return $login;
    }

    public function getUserID($username) {
        $this->sql = "SELECT kd_user FROM user WHERE username = '$username'";
        $result = mysqli_query($this->connect, $this->sql);
        return $result;
    }

    public function getUserIDByStatus($status){
    $status = $this->prepareData($status);
    $this->sql = "SELECT kd_user FROM user WHERE status_login = '".$status."'";
    $result = mysqli_query($this->connect, $this->sql);
    return $result;
    }

    public function getUserDataByStatus($status){
        $status = $this->prepareData($status);
        $this->sql = "SELECT * FROM user WHERE status_login = '".$status."'";
        $result = mysqli_query($this->connect, $this->sql);
        return $result;
        }


    function signing($table, $username, $fullname, $email, $password)
    {
        $username = $this->prepareData($username);
        $fullname = $this->prepareData($fullname);
        $email = $this->prepareData($email);
        $password = $this->prepareData($password);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (username, nama_lengkap, email, password) VALUES ('" . $username . "','" . $fullname . "','" . $email . "','" . $password . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

}

?>
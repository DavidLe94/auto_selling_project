<?php
  $host = 'mysql.hostinger.vn';
  $db = 'u233973288_abc';
  $user = 'u233973288_admin';
  $pass = 'abc123!@#';

  //create connect
  $conn = new mysqli($host, $user, $pass, $db);

  // Check connection
  if ($conn->connect_error) {
      die("Connection failed: " . $conn->connect_error);
  }
?>

<?php

	require_once 'include/config.php';



	$query = "SELECT * FROM products WHERE staDelete = 0";
	// Change character set to utf8
	mysqli_set_charset($conn,"utf8");
	$result = $conn->query($query);

	if(mysqli_num_rows($result) > 0){
		$json["author"]='haule';
		$json["version"]='1.0.0';
		$json["success"]=1;
		$json["products"]=array(); //mang con
  //
	// 	//duyet tat ca san pham dua vao json
		while($row = mysqli_fetch_array($result)){
			$products=array();

			$products["id"]=$row["id"];
			$products["nameProduct"]=$row["nameProduct"];
			$products["model"]=$row["model"];
			$products["brand"]=$row["brand"];
			$products["price"]=$row["price"];
			$products["image"]=$row["image"];
			$products["description"]=$row["description"];
			//dua san pham vao mang
			array_push($json["products"],$products);
		}
  //
	}else{
		$json["success"]=0;
		$json["message"]="no have products on table";
	}

	echo json_encode($json);
?>

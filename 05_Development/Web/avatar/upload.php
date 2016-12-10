﻿<?php 
 
 //importing dbDetails file 
 require_once 'dbDetails.php';
 
 //this is our upload folder 
 $upload_path = 'uploads/';
 
 //Getting the server ip 
 $server_ip = gethostbyname(gethostname());
 
 //creating the upload url 
 $upload_url = 'http://'.$server_ip.'/AndroidImageUpload/'.$upload_path; 
 
 //response array 
 $response = array(); 
 
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
	 //checking the required parameters from the request 
	 if(isset($_POST['name']) and isset($_FILES['image']['name'])){
	 
		 //connecting to the database 
		 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
		 
		 //getting name from the request 
		 $name = $_POST['name'];
		 
		 //getting file info from the request 
		 $fileinfo = pathinfo($_FILES['image']['name']);
		 
		 //getting the file extension 
		 $extension = $fileinfo['extension'];
		 
		 //file url to store in the database 
		 $file_url = $upload_url . getFileName() . '.' . $extension;
		 
		 //file path to upload in the server 
		 $file_path = $upload_path . getFileName() . '.'. $extension; 
		 
		 //trying to save the file in the directory 
		 try{
			 //saving the file 
			 move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
			 //filling response array with values 
			 $response['resultCode'] = 0; 
			 $response['url'] = $file_url;
		 }catch(Exception $e){
			 $response['resultCode']=1;
			 $response['message']=$e->getMessage();
		 } 
		 //displaying the response 
		 echo json_encode($response);
		 
		 //closing the connection 
		 mysqli_close($con);
	 }else{
		 $response['resultCode']=1;
		 $response['message']='Please choose a file';
	 }
 }
 
 /*
 We are generating the file name 
 so this method will return a file name for the image to be upload 
 */
 function getFileName(){
	 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
	 $sql = "SELECT max(id) as id FROM images";
	 $result = mysqli_fetch_array(mysqli_query($con,$sql));
	 
	 mysqli_close($con);
	 if($result['id']==null)
		return 1; 
	 else 
		return ++$result['id']; 
 }
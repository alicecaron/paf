<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<head/>
<body>

<?php 

$source = '../docTagged/'.$_GET['doc'].'.html'; 
$text = file_get_contents($source); 

$pos=0; 
$searchText=strtolower($text); 
$searchWord=strtolower($_GET['word']); 
while ($pos<strlen($searchText)) 
{ 
  if (($start=strpos($searchText,'<p',$pos))===false || ($end=strpos($searchText,'</p>',$start+3))===false) 
    break; 
 // echo $start.' -> ';
  $start=strpos($searchText,'>',$start)+1;
 // echo $start.'end: '.$end.'<br/>';
  $contents=substr($searchText,$start,$end-$start); 
  if (strpos($contents,$searchWord)!==false){ 
    echo substr($text,$start,$end-$start);
    break; 
  }
  $pos=$end+4; 
}

?>

</body>
</html>


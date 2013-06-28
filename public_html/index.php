<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<title>Exploitation des données d'un corpus de textes</title>
    <script type="text/javascript" src="js/jquery_min.js"></script>
    <script type="text/javascript" src="js/jquery.csv-0.71.min.js"></script>
	<link rel="stylesheet" href="css/style.css" type="text/css"/>
</head>
<body>

<h1>Exploitation des données d'un corpus de textes<br/>concernant le programme scolaire français</h1>
<div style="text-align:center;padding:5px"><a href="tree.php">Visualisation</a> de l'ontologie de Bloom pour les verbes présents dans le corpus - <a href="readme.php">Infos</a> sur la réalisation</div>
<div style="clear:both;height:30px"></div>
<div style="border:2px black solid">
<?php

$dirname = 'docTagged/';
$dir = opendir($dirname); 

$tab= array();
while($file = readdir($dir)) {
	if($file != '.' && $file != '..' && !is_dir($dirname.$file))
		$tab[]=$file;
}
closedir($dir);
sort($tab);

$arrays=array_chunk($tab, 10, false);
$list="";
foreach($arrays as $key=>$array[$i]){
	$list.=makeTableau($array[$i]);
}

$tt="<center><h2>Liste des documents du corpus:</h2><table><tr>".$list."</tr></table><center><h2>Visualisation statistisque du corpus:</h2></center><table><tr><td><a title=\"Donut sur les verbes de l'ontologie de Bloom\" href='donut.php'><img style='height:300px' src='img/donut.svg' /></a></td><td><a title='Nuage de mots' href='img/cloud3.svg'><img style='height:300px' src='img/cloud3.svg'/></a></td></tr></table></center>";
echo $tt;

$first=true;
function makeTableau($tableau){
	$tab="<td><table style=\"border-right:2px black solid\">";
	foreach($tableau as $filename){
		$originalFilename=$filename;
		$filename=str_replace('.html','',$filename);
		$file=split("_",$filename);
/* 		if(strcmp($file[0],"lycee") && $first){
			$tab.="<tr><td>Lycée</td></tr>";
			$first=false;
		}
 */		$tab.='<tr><td><span class="docInfos" filename="'.$originalFilename.'">'.ucfirst(str_replace("-"," ",$file[1])).' '.$file[2].'</span></td><td><a href="docViewer/index.php?displayDoc='.$filename.'"><img src="img/doc_icon.png" title="Accéder au document" style="height:20px"/></a></td></tr>';
	}
	$tab.="</table></td>";
	return $tab;
}

?>
</table>
</div>

<script type="text/javascript" src="js/index.js"></script>
<?php include_once "footer.php"  ?></body>
</html>

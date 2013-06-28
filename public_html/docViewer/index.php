<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE><?php echo $_GET['displayDoc'] ?></TITLE>
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/flat-ui.css">
	<link rel="stylesheet" type="text/css" href="../css/style.css" />

<script>
var mot="Paris";
var currentWord=mot;
var query1="SELECT distinct ?country ?wikipage ?image WHERE { ?country rdfs:label \"";
var query2="\"@fr . ?country foaf:isPrimaryTopicOf ?wikipage . ?country foaf:depiction ?image } ORDER BY ?country";
var query3="SELECT distinct ?country ?wikipage WHERE { ?country rdfs:label \"";
var query4="\"@fr . ?country foaf:isPrimaryTopicOf ?wikipage } ORDER BY ?country";

function sparqlQueryJson(m, endpoint, callback, isDebug,retry) {
	m=m.latinize();
	var querypart = "query=";
	if(retry)
		querypart+=escape(query3+m+query4);
	else	
		querypart+=escape(query1+m+query2);
	var xmlhttp = null;
	if(window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
		} else if(window.ActiveXObject) {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		} else {
		alert('Your browser may not support XMLHttpRequests... :(');
		}

		// Set up a POST with JSON result format.
		xmlhttp.open('POST', endpoint, true);
		xmlhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xmlhttp.setRequestHeader("Accept", "application/sparql-results+json");

		// Set up callback to get the response asynchronously.
		xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState == 4) {
		 if(xmlhttp.status == 200) {
		   callback(xmlhttp.responseText,m,retry);
		 } else {
		//   alert("Sparql query error: " + xmlhttp.status + " " + xmlhttp.responseText);
		 }
		}
	};
	xmlhttp.send(querypart);
	currentWord=m;
};
</script>
</HEAD>
<body prefix="bm:http://shadok.enst.fr/ilot/demo1/resource/Bloom_LRE.owl">

<nav><br>
	<div class="toggle">
		<label class="toggle-radio" for="normalBtn">&#9675; Normal   </label>
		<input id="normalBtn" type="radio" checked="checked">
		<label class="toggle-radio" for="bigWordsBtn">&#9679; Mots clefs amplifiés   </label>
		<input id="bigWordsBtn" type="radio">		
	</div>
	<ul><br>
		<label for="item1" style="color:red">
			<input type="checkbox" class="item" concerns="verbeOnto" name="io1" checked /><span class="search"> Verbes de l'ontologie</span>
		</label>
		<label for="item2" style="color:blue">
			<input type="checkbox" class="item" concerns="verbePasOnto" name="io2" checked /><span class="search"> Autres verbes</span>
		</label>
		<label for="item3" style="color:orange" >
			<input type="checkbox" class="item" concerns="nomsPropres" name="io3" checked /><span class="search"> Noms propres</span>
		</label>
		<label for="item4" style="color:green">
			<input type="checkbox" class="item" concerns="highTfidf" name="io4"  checked /><span class="search"> Fortes TFIDF</span>
		</label>
	</ul>
	<a href="../index.php">Accueil</a><br>
	<a href="../tree.php">Ontologie de Bloom</a>
</nav>

	<?php include '../docTagged/'.$_GET['displayDoc'].'.html'; ?>
<div id="results" style="color:black;position:absolute;z-index=100001;background:#fdeee9"></div>

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/latinize.js"></script>
<script type="text/javascript" src="js/bigWords.js"></script>
<script src="js/toggle.js"></script>

<script>
$(".nomsPropres").each(function(ev){
	$(this).mouseover(function(e) {
	 $("#results").css({left: e.pageX+'px',top: (e.pageY+10)+'px'});
	  sparqlQueryJson(capitalize($(this).text()), endpoint, myCallback, true,false);
	});
});

$(".highTfidf").each(function(ev){
	$(this).hover(function() { $(this).css('cursor','pointer'); });
	$(this).click(function(e) {
	 $("#results").css({left: e.pageX+'px',top: (e.pageY+10)+'px'});
	  sparqlQueryJson(capitalize($(this).text()), endpoint, myCallback, true,false);
	});
});
$(document).click(function(e) {
	$("#results").html("");
});
$(".verbeOnto").each(function(){
	$(this).attr("property","bm:LRE-BloomActionValues#"+$(this).text());
});
var endpoint = "http://fr.dbpedia.org/sparql";
function myCallback(str,mot,retry) {
	var jsonObj = eval('(' + str + ')');
	var result = " <table border='2' cellpadding='9'>" ;
	if(jsonObj.results.bindings.length==0){
		$("#results").html(result+"<tr><td>...</td></tr></table>");
		if(!retry)sparqlQueryJson(currentWord, endpoint, myCallback, true,true);
		return;
	}
	
	for(var i = 0; i<jsonObj.results.bindings.length; i++) {
		if(jsonObj.results.bindings[i].wikipage.value!=undefined){
			result += "<td>"+mot+"<br/> <a target=\"_blank\" href=\"" + jsonObj.results.bindings[i].wikipage.value+"\"><img title=\"Page Wikipedia\" style=\"height:60px\" src=\"../img/logo_wiki.png\"/></a></td>";
			if(jsonObj.results.bindings[i].image!=undefined)
				result += "<td><a target=\"_blank\" href=\""+jsonObj.results.bindings[i].image.value+"\"><img style=\"height:100px\" src=\"" + jsonObj.results.bindings[i].image.value+"\"></a></td>";
			result += "</tr>";
			result += "</table>" ;
			$("#results").html(result);
		}
	}
}

function capitalize(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
</script>
<?php include_once "../buttons.php"  ?>
</body>
</HTML>






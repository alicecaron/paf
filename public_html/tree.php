<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
    <script type="text/javascript" src="js/jquery_min.js"></script>
    <script type="text/javascript" src="d3/d3.v3.js"></script>
    <script type="text/javascript" src="d3/d3.layout.js"></script>
	<title>Visualisation des verbes de l'ontologie de Bloom dans le corpus</title>
  <style type="text/css">
	.node circle {
	  cursor: pointer;
	  fill: #fff;
	  stroke: steelblue;
	  stroke-width: 1.5px;
	}
	.node text {
	  font-size: 11px;
	}
	path.link {
	  fill: none;
	  stroke: #ccc;
	  stroke-width: 1.5px;
	}
  </style>
  </head>
  <body style="height:900px;overflow:auto" >
	<div id="results" style="color:black;z-index=100001;background:#dee ;position:fixed; bottom:40px; padding:10px; right:0px; width:40%; height:28%; overflow:auto "></div>
    <?php include "ontology.php"?>
 	<div>
		<h1>Visualisation des verbes de l'ontologie de Bloom dans le corpus</h1>
	</div>
	<div id="body" style="width:750px;border:2px black solid"></div>
	
    <script type="text/javascript">

var m = [50, 120, 50, 120],
    w = 1280 - m[1] - m[3],
    h = 800 - m[0] - m[2],
    i = 0,
    root;
	
var tree = d3.layout.tree()
    .size([h, w]);

var diagonal = d3.svg.diagonal()
    .projection(function(d) { return [d.y, d.x]; });

var vis = d3.select("#body").append("svg:svg")
    .attr("width", w + m[1] + m[3])
    .attr("height", h + m[0] + m[2])
  .append("svg:g")
    .attr("transform", "translate(" + m[3] + "," + m[0] + ")");
d3.json("data/tree.json", function(json) {
  root = json;
  root.x0 = h / 2;
  root.y0 = 0;

  function toggleAll(d) {
    if (d.children) {
      d.children.forEach(toggleAll);
      toggle(d);
    }
  }

  // Initialize the display to show a few nodes.
  root.children.forEach(toggleAll);
 // toggle(root.children[1]);
  //toggle(root.children[1].children[2]);
 // toggle(root.children[9]);
//  toggle(root.children[9].children[0]);

  update(root);
});


//get infos about the word
function displayInfos(d){
 if(d.corpusFreq!=undefined){
		$("#info").html("<b>Mot:</b> <span id=\"selectedWord\">"+d.name+"</span>");
		if(d.file!=undefined)
			$("#info").html($("#info").html()+"<br><b>Acc&eacute;der au document: </b><a href=\"docViewer/index.php?displayDoc="+d.file.replace(".txt","")+"\">ici</a>");
		$("#info").html($("#info").html()+"<hr><b>Fr&eacute;quence dans le corpus:</b> "+d.corpusFreq);

	 if(d.nbApparition!=undefined)
		$("#info").html($("#info").html()+"<br><b>Occurence dans ce document: </b>"+d.nbApparition);
	 if(d.tfidf!=undefined)
		$("#info").html($("#info").html()+"<br><b>TFIDF:</b> "+d.tfidf);
	 if(d.nbDocApparition!=undefined & d.otherDocs!=undefined)
		$("#info").html($("#info").html()+"<hr><b>Document(s) poss&eacute;dant ce mot: </b>"+d.nbDocApparition+""+parseOtherDocs(d.otherDocs));
 }
}

function parseOtherDocs(otherDocs){
	var oDocs="<br>";
	eachDoc=otherDocs.split(",");
	for(var i in eachDoc){
		d=eachDoc[i].split("_");
		if(d[2]!=undefined){
			d[2]=d[2].replace(".txt","");//replace(".txt","");
			oDocs+="<a href=\"docViewer/index.php?displayDoc="+eachDoc[i].replace(".txt","")+"\">"+d[1]+"</a> ("+d[2]+")<br>";
		}
	}
	return oDocs;
}

function update(source) {
  var duration = d3.event && d3.event.altKey ? 5000 : 500;

  // Compute the new tree layout.
  var nodes = tree.nodes(root).reverse();

  // Normalize for fixed-depth.
  nodes.forEach(function(d) { d.y = d.depth * 180; });

  // Update the nodesâ?¦
  var node = vis.selectAll("g.node")
      .data(nodes, function(d) { return d.id || (d.id = ++i); });

  // Enter any new nodes at the parent's previous position.
  var nodeEnter = node.enter().append("svg:g")
      .attr("class", "node")
      .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
      .on("click", function(d) { toggle(d); update(d); });

  nodeEnter.append("svg:circle")
      .attr("r", 1e-6)
      .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

  nodeEnter.append("svg:text")
      .attr("x", function(d) { return d.children || d._children ? -10 : 10; })
      .attr("dy", ".35em")
      .attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
     .append("svg:a")
	 .on("mouseover",function(d) {
		linkToOccurence(d);
          })
	 .on("click", function(d) {
		if(d.corpusFreq!=undefined)
			$("#owlauto").val($(this).text());
		displayInfos(d);
		})
	 .text(function(d) { return d.name; })
	 .style("cursor","pointer");
     

  // Transition nodes to their new position.
  var nodeUpdate = node.transition()
      .duration(duration)
      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

  nodeUpdate.select("circle")
      .attr("r", 4.5)
      .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

  nodeUpdate.select("text")
      .style("fill-opacity", 1);

  // Transition exiting nodes to the parent's new position.
  var nodeExit = node.exit().transition()
      .duration(duration)
      .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
      .remove();

  nodeExit.select("circle")
      .attr("r", 1e-6);

  nodeExit.select("text")
      .style("fill-opacity", 1e-6);

  // Update the linksâ?¦
  var link = vis.selectAll("path.link")
      .data(tree.links(nodes), function(d) { return d.target.id; });

  // Enter any new links at the parent's previous position.
  link.enter().insert("svg:path", "g")
      .attr("class", "link")
      .attr("d", function(d) {
        var o = {x: source.x0, y: source.y0};
        return diagonal({source: o, target: o});
      })
    .transition()
      .duration(duration)
      .attr("d", diagonal);

  // Transition links to their new position.
  link.transition()
      .duration(duration)
      .attr("d", diagonal);

  // Transition exiting nodes to the parent's new position.
  link.exit().transition()
      .duration(duration)
      .attr("d", function(d) {
        var o = {x: source.x, y: source.y};
        return diagonal({source: o, target: o});
      })
      .remove();

  // Stash the old positions for transition.
  nodes.forEach(function(d) {
    d.x0 = d.x;
    d.y0 = d.y;
  });
}

// Toggle children.
function toggle(d) {
  if (d.children) {
    d._children = d.children;
    d.children = null;
  } else {
    d.children = d._children;
    d._children = null;
  }
}

function linkToOccurence(d) {
	if (d.file!=undefined){
		$("#results").empty();
		$("#results").append('<i>Contexte :</i><br/>');		
		$("#results").append($('<div>').load("search/search.php?doc="+d.file.substring(0,d.file.length -4)+"&word="+d.name));
		//$("#results").load("search/search.php?doc="+d.file.substring(0,d.file.length -4)+"&word="+d.name,function () {
		//});	
	}
}

    </script>
	   <?php include "buttons.php"?>

  </body>
</html>

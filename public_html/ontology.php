<!--!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:DC="http://purl.org/metadata/dublin_core_elements#" xml:lang="en" lang="en">
<head>
    <meta name="DC.creator" content="David Decraene" />
	<meta name="DC.publisher" content="Ontology Online" />
    <meta name="Keywords" content="OWL, Ubiquity, jQuery, jOWL, ontology, semantic web"/>
    <meta name="Description" content="jOWL Ontology Browser, visualizes ontologies loaded with the ubiquity command 'view_ontology'"/>
    <title>jOWL Ontology Browser - Ubiquity</title-->
	<link rel="stylesheet" href="css/jOWL.css" type="text/css"/>
	<link type="text/css" rel="stylesheet" href="css/jq/custom-theme/jquery-ui-1.7.custom.css"></link>

    <style type="text/css">
		img {border:0pt none;display:block}	

		/** custom widget settings */
		.jowl_link {text-decoration:underline;color:blue;cursor:pointer;}
		.jowl_tooltip {text-decoration:underline;color:steelblue;}
		#thingtooltip {border:0px;}
		.stats {text-align:right;font-size:smaller;}
		.resourcebox {text-align:left}
    </style>
    <script type="text/javascript" src="js/jquery_min.js"></script>	
	<script type="text/javascript" src="js/jquery_ui.js"></script>
	<script type="text/javascript" src="js/jquery.tooltip.js"></script>
	<script type="text/javascript" src="js/jOWL.js"></script>
	<script type="text/javascript" src="js/jOWL_UI.js"></script>
	<script type="text/javascript" src="js/jOWLBrowser.js"></script>
    <script type="text/javascript">
/** 
Modify this configuration object to fine-tune the visualisation of the jOWL browser.
*/
	var configuration = {
		ontology : "data/Bloom_LRE.owl", //the ontology to load
		owlClass       : null, //The class to show when loading
		classOverview  : true, //show or hide the class overview list.
		propertiesTab  : true, //show or hide the properties panel
		individualsTab : true, //show or hide the individuals panel
		sparqldlTab    : true  //show or hide the sparq-dl panel
	}

/** 
Do not Modify the code below unless you know what you are doing.
*/
$(document).ready(function() {
	if(!configuration.propertiesTab) { $('#propertyPanel').remove();   $('#tab2').remove(); }
	if(!configuration.individualsTab){ $('#thingwidget').appendTo("body").hide(); $('#individualPanel').remove(); $('#tab3').remove(); }
	if(!configuration.sparqldlTab)   { $('#sparqldlPanel').remove();   $('#tab4').remove(); }

	$("#tabs").tabs();

	jOWL.load(configuration.ontology, initjOWL, {reason : true, locale : 'fr' });
});

function initjOWL(){

		createOntologyWidget();
		var conceptWidget = createConceptWidget();

		if(configuration.classOverview){
			jOWLBrowser.views.push({query: "Class(?x)", element : $('#classlist'), widget : conceptWidget}); 
			}

		if(configuration.propertiesTab){
			var propertyWidget = createPropertyWidget(); 
			jOWLBrowser.views.push({query: "ObjectProperty(?x)", element : $('#OPlist'), widget : propertyWidget});
			jOWLBrowser.views.push({query: "DatatypeProperty(?x)", element : $('#DPlist'), widget : propertyWidget});
			}

		if(configuration.individualsTab){
			var thingWidget = createIndividualsWidget();

			setTimeout(function(){
				var arr = new jOWL.Ontology.Array();
				for(key in jOWL.index('Thing')){
						arr.concat(jOWL.index('Thing')[key], true);
					}
				showOverviewResults(arr, $('#thinglist'), thingWidget);
			}, 200);
			
			}

		if(configuration.sparqldlTab){
			createSparqlDLWidget(); 
			}

		createOverviewWidget();
}
</script>
	<!--/head>
	<body-->
		<div class="column span-24" style="width:40%;right:0;top:100px;position:fixed">
			<div id="conceptwidget" style="background:green">
				<div id="info" style="width:50%;height:300px;position:absolute;left:0;overflow:auto"></div>
				<div id="browser" style="width:30%;position:absolute;right:30px;height:300px;">
					<div id="treeview"></div>
					Rechercher un mot:<br>
					<input id="owlauto" type="text" size="40" style="display:block;width:95%;margin:5px 0px;"/>
				</div>
			</div>
		</div>
	<!--/body>
</html-->

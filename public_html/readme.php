<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<title>Infos outils du PAF</title>
    <script type="text/javascript" src="js/jquery_min.js"></script>
    <script type="text/javascript" src="js/jquery.csv-0.71.min.js"></script>
	<link rel="stylesheet" href="css/style.css" type="text/css"/>
	<style>
	.tab{
		margin-left:4em;
	}
	</style>
</head>
<body>

<h1>Outils et informations à propos de ce PAF</h1>
<div id="readme">
	<h2>Objectif</h2>
	<span class="tab"></span>A partir d'un corpus de documents issus du programme scolaire français, extraire les liens entre les documents, 
	leurs similarités et les notions qui en ressortent. Le premier but de ce traitement est d'avoir une analyse statistique des mots dans un contexte (ici le corpus de documents défini autour du programme scolaire français)
	<br><span class="tab"></span>Dans un second temps, l'objectif est d'extraire de cette masse de mots, sans véritable sens pour une machine, des informations pertinentes et éventuellement de les relier à des sources d'informations externes (Wikipédia...).
	On utilise aussi des ontologies (comme l'ontologie de Bloom) qui permettent de dégager des concepts d'un corpus.
	<br><span class="tab"></span>Il s'agit donc de donner aux textes bruts, un sens, repérable par une machine (grâce à des métatags).

	<h2>Outils et technologies utilisés</h2>
	<ul>
		<li>Eclipse (traitement des textes en java):
			<ul>
				<li>Extraction des textes bruts des pages HTML et des PDF constituant le corpus;</li>
				<li>Taggage des mots avec Lia_Tagg pour repérer leur type et lemme (sémantique des mots);</li>
				<li>Filtrage des mots inutiles sémantiquement (bruit);</li>
				<li>Comptage des occurences des mots distincts du corpus;</li>
				<li>Comptage des occurences des lemmes différents du corpus;</li>
				<li>Calcul des distances entre les documents pour pouvoir effectuer des rapprochements entre eux;</li>
				<li>Taggage des mots pour recréer un fichier métataggé de sortie (verbes de l'ontologie de Bloom, mots de TF-IDF supérieure à un seuil);</li>
				<li>Création du Json pour la visualisation;</li>
			</ul>
		</li>
		<li>Ontologie de <a href="http://wiki.knoesis.org/index.php/BLOOMS" target="_blank" class="readme">Bloom</a>: pour classer les verbes selon les concepts qu'ils reprennent;</li>
		<li><a href="http://lia.univ-avignon.fr/chercheurs/bechet/download_fred.html" target="_blank" class="readme">Lia_Tagg</a>: étiqueteur/sémantiseur pour obtenir le type et le lemme de chaque mot du corpus et permettre par la suite de filtrer les mots bruitant (déterminants, auxiliaires, conjonctions...) et de regrouper les mots proches sémantiquement (de même lemme);</li>
		<li>Requêtes SPARQL en Javascript: pour interroger les plateformes comme <a class="readme" target="_blank" href="http://fr.dbpedia.org/">DBPedia</a> et ainsi lier des mots du corpus avec des ressources extérieures (lien wikipédia, photos, position géographique...);</li>
		<li>Javascript, JQuery, HTML, CSS, PHP : pour la réalisation du site et une visualisation plus parlante des résultats du traitement préalable du corpus;</li>
		<li><a href="http://d3js.org/" class="readme" target="_blank">D3.js</a>: visualisation des verbes de chaque document(présents dans l'ontologie de bloom) sous forme d'un graphe (SVG);</li>
		<li><a href="http://jowl.ontologyonline.org/" class="readme" target="_blank">JOWL-lib</a>: visualisation sous forme de graphe d'une ontologie OWL</li>
	</ul>
	<h2>Notions abordées</h2>
	<ul>
		<li><span id="tfidf" />TF-IDF (= Term Frequency-Inverse Document Frequency):<br>C'est une mesure statistique qui permet d'évaluer l'importance d'un terme contenu dans un document, par rapport à un corpus.
		Sa valeur par terme et par document est proportionnelle au nombre d'occurrences du terme dans le document. 
		Elle dépend aussi de sa fréquence d'apparition dans le corpus.<br>On obtient ainsi une pondération des mots en relation avec un corpus et non plus cantonnée à un document, ce qui permet de mieux les comparer.<br>Cependant, cette mesure ne tient pas compte de la longueur respective des documents, ce qui peut limiter sa pertinence.
		<br><br><center><img style="height:75px" src="img/tfidf.png" title="Formule de la TF-IDF" /></center>
		</li>
		<li><span id="distanceCosine" />Distance cosine entre documents:
		<br>Elle indique dans quelle mesure deux documents utilisent un lexique similaire.
		<br>Elle s'exprime par la formule ci-jointe, où on compare le document 'a' et le document 'b' et où p<sub>t</sub>(a) et p<sub>t</sub>(b) représentent respectivement les fréquences d'apparition du mot t dans les documents a et b.
		<br><br><center><img style="height:75px" src="img/distance.png" title="Formule de la distance inter-document" /></center>
		</li>
	</ul>
	<h2>Traitement des données</h2>
	<ul>
		<li>Taggage final pour la relecture en javascript</li>
		<li>filtrage</li>
		<li>Extraction des lemmes</li>
		<li>Ontologie (OWL et RDF)</li>
	</ul>


	<h2>Visualisation des données</h2>
	<ul>
		<li>Nuage de mot de l'ensemble des mots du corpus (réalisé avec D3.js), filtrage préalable des mots parasites grâce à l'étiquetteur Lia_Tagg;</li>
		<li>Graphe des verbes de l'ontologie de Bloom pour chaque document du corpus (réalisé avec D3.js pour le graphe et JOWL-lib pour visualiser l'ontologie);</li>
		<li>Taggage de documents source pour mettre en avant: les verbes du corpus (et/ou de l'ontologie de Bloom), les mots de forte TF-IDF, les noms propres;</li>
		<li>Lien entre noms propres et ressources externes pour récupérer des informations grâce à des requêtes SPARQL en Javascript;</li>
	</ul>

	<h2>Vocabulaire</h2>
	<ul>
		<li>Ontologie:<br>
		Une ontologie représente un ensemble structuré de termes et de concepts présents dans un ensemble de données textuelles. Cet ensemble encadre ces données pour les relier entre elles ou du moins leur apporter un sens individuel.
		<br>Les champs d'informations ajoutés sont appelés des métadonnées.
		<br>L'ontologie constitue un modèle représentant l'ensemble des concepts abordés dans un domaine (programme scolaire par exemple) ainsi que les relations existantes entre eux.	
		</li>
		<li>Lemme:<br>
		C'est la forme de référence d'un mot, c'est-à-dire sans les modifications qu'il subit lorsqu'il est utilisé dans le discours.
		<br><u>Par exemple:</u> la forme infinitive d'un verbe, le masculin singulier pour un adjectif, le singulier d'un nom...
		<br>les entrées des dictionnaires sont des lemmes. On parle aussi de forme canonique.
		<br>Dans ce projet, on effectue la lemmatisation des mots du corpus (avec Lia_Tagg) afin de faire ressortir les notions abordées en regroupant les différentes déclinaisons possibles d'un même lemme. Cela nous a permis par exemple de compter le nombre d'utilisation d'un verbe présent dans l'ontologie de Bloom.
		</li>
		<li>Etiqueteur morpho-syntaxique:<br>
		(Ou encore POS tagging (part-of-speech tagging)<br>
		Nous avons utilisé l'étiqueteur Lia_Tagg pour lemmatiser et typer les mots du corpus.
		<br>Un étiqueteur est un outils utilisé en linguistique pour associer à chaque mot sa nature (type: verbe infinitif, nom propre féminin, adjectif masculin pluriel...)
		</li>
		<li><a href="#tfidf">TF-IDF</a></li>
		<li><a href="#distanceCosine">Distance cosine</a></li>
	</ul>
</div>
<?php include 'footer.php' ?>
<?php include 'buttons.php'?>
</body>
</html>

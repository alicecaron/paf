Programme java d'extraction-filtrage-comptage-taggage des documents du corpus


********************PACKAGES***********************
- analyse: établissement des statistiques
- cloud: génère un texte (cloudTxt/resultat.txt) pour le cloud data d3js.org (http://www.jasondavies.com/wordcloud/#http%3A%2F%2Fsearch.twitter.com%2Fsearch.json%3Frpp%3D100%26q%3D%7Bword%7D=cloud)
- reader: permet d'extraire les textes bruts des documents du corpus (menu.java permet d'extraire le textes des pdf ou des sites html dont les adresses et les noms des futurs fichiers sont données dans /src/liste.txt.
- jsonCreator: création du fichier json: json/tree.json pour visualiser les verbes de la taxonomy de Bloom qui sont dans le corpus.
- tagger: tagger les mots des textes pour les documents html finaux
- ontology: retrouver les verbes de la taxonomie de Bloom et détecter les verbes du corpus qui en font partie.



********************ORGANISATION********************

owl: fichiers owl (Bloom)
json: fichier json (tree.json pour la visualisation de l'arbre)
csv: fichiers csv 
	- data.csv pour visualiser le donut des verbes de la taxonomie de Bloom qui reviennent le plus,
	- similarites.csv pour connaître pour chaque document, sa distance avec les autres
cloudTxt: fichier txt qui contient les lemmes des mots les plus utilisés. Chacun est écrit autant de fois qu'il apparaît dans le corpus. Ensuite on visualise ce texte avec le cloud.
docTxt/docSaved: fichiers HTML récupérés par le convertisseur pdf2HTML (obtenus un à un en ligne
	/docTagged: fichiers HTML de sortie, tagués par le programme
	/pdf: fichiers PDF de base
	/texts: fichiers txt bruts


********************UTILISATION*********************

Démarrer l'extraction des textes bruts (en txt): src/reader/menu.java
Stock des fichiers txt dans docTxt/texts
Démarrer le programme d'extraction-filtrage-comptage-taggage: src/ontology/MyOntology.java
Les fichiers tagués sont stockés dans docTxt/docTagged


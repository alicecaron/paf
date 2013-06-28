var similContent;
var array=new Array();

var simil=new Array();
var doc=new Array();

getSimilCSV();
var s,str;
function parseCSVContent(){
	jsonText=csvToJson();
	json=JSON.parse(jsonText);
}
function setMessage(message,error){
	s='<p>'+message+'</p>';if(error)
	s.className="error";else
	s.className="";
}

function parseCSVLine(line)
{line=line.split(',');for(var i=0;i<line.length;i++)
{var chunk=line[i].replace(/^[\s]*|[\s]*$/g,"");var quote="";if(chunk.charAt(0)=='"'||chunk.charAt(0)=="'")quote=chunk.charAt(0);if(quote!=""&&chunk.charAt(chunk.length-1)==quote)quote="";if(quote!="")
{var j=i+1;if(j<line.length)chunk=line[j].replace(/^[\s]*|[\s]*$/g,"");while(j<line.length&&chunk.charAt(chunk.length-1)!=quote)
{line[i]+=','+line[j];line.splice(j,1);chunk=line[j].replace(/[\s]*$/g,"");}
if(j<line.length)
{line[i]+=','+line[j];line.splice(j,1);}}}
for(var i=0;i<line.length;i++)
{line[i]=line[i].replace(/^[\s]*|[\s]*$/g,"");if(line[i].charAt(0)=='"')line[i]=line[i].replace(/^"|"$/g,"");else if(line[i].charAt(0)=="'")line[i]=line[i].replace(/^'|'$/g,"");}
return line;}
function csvToJson()
{var message="";var error=false;var csvText=similContent;var jsonText="";setMessage(message,error);if(csvText==""){error=true;message="Enter CSV text below.";}
if(!error)
{benchmarkStart=new Date();csvRows=csvText.split(/[\r\n]/g);for(var i=0;i<csvRows.length;i++)
{if(csvRows[i].replace(/^[\s]*|[\s]*$/g,'')=="")
{csvRows.splice(i,1);i--;}}
if(csvRows.length<2){error=true;message="The CSV text MUST have a header row!";}
else
{objArr=[];for(var i=0;i<csvRows.length;i++)
{csvRows[i]=parseCSVLine(csvRows[i]);}
benchmarkParseEnd=new Date();for(var i=1;i<csvRows.length;i++)
{if(csvRows[i].length>0)objArr.push({});for(var j=0;j<csvRows[i].length;j++)
{objArr[i-1][csvRows[0][j]]=csvRows[i][j];}}
benchmarkObjEnd=new Date();jsonText=JSON.stringify(objArr,null,"\t");benchmarkJsonEnd=new Date();/* f.elements["json"].value=jsonText ;*/benchmarkPopulateEnd=new Date();message=getBenchmarkResults();return jsonText;}}
setMessage(message,error);}
function getBenchmarkResults()
{var message="";var totalTime=benchmarkPopulateEnd.getTime()-benchmarkStart.getTime();var timeDiff=(benchmarkParseEnd.getTime()-benchmarkStart.getTime());var mostTime="parsing CSV text";if((benchmarkObjEnd.getTime()-benchmarkParseEnd.getTime())>timeDiff){timeDiff=(benchmarkObjEnd.getTime()-benchmarkParseEnd.getTime());mostTime="converting to objects";}
if((benchmarkJsonEnd.getTime()-benchmarkObjEnd.getTime())>timeDiff){timeDiff=(benchmarkJsonEnd.getTime()-benchmarkObjEnd.getTime());mostTime="building JSON text";}
if((benchmarkPopulateEnd.getTime()-benchmarkJsonEnd.getTime())>timeDiff){timeDiff=(benchmarkPopulateEnd.getTime()-benchmarkJsonEnd.getTime());mostTime="populating JSON text";}
message+=csvRows.length+" CSV line"+(csvRows.length>1?'s':"")+" converted into "+objArr.length+" object"+(objArr.length>1?'s':"")+" in "+(totalTime/1000)+" seconds, with an average of "+((totalTime/1000)/csvRows.length)+" seconds per object. Most of the time was spent on "+mostTime+", which took "+(timeDiff/1000)+" seconds.";return message;
}
function splitProches(d){
	f=new Array();
	proches=d.proches;
	pr=proches.split('%');
	str="";
	for(var i in pr){
		//pr[i]=pr[i].replace("*"," ");
		pr[i]=pr[i].split("*");
	}
	for(i in pr){
		if(i<5){
			//alert(pr[i]);
			f.push(pr[i][0]);
		}
	}
	
	$(".docInfos").each(function(){
		$(this).css("color","black");
		$(this).attr("title","");
		for(i in f){
			if(f[i].replace("txt","html")==$(this).attr("filename")){
				$(this).css("color","#3498db");
				$(this).attr("title",pr[i][1]);
			}
		}
	});
	
}

	/* array=similContent.split("\n");
	alert(array);
	for(var i in array){
		split1=array[i].split(",");
		simil.push(split1[0]);
		doc.push(split1[1]);
	}
	alert(simil);
	alert (doc);
	for(var i in doc){
		data=doc.split("%");
	} */


function getSimilCSV(){
	$.ajax({
	  url: "data/similarites.csv",
	  success: function( data ) {
		similContent=data;
		parseCSVContent();
	  }
	});
}
function getProches(doc){
	doc=doc.replace("html","txt");
	for(var i in json){
		if(json[i].document==doc)
			splitProches(json[i]);
	}
}
$(".docInfos").each(function(){
	
	$(this).click(function(e){
		doc=$(this).attr("filename");
		getProches(doc);
		$(this).css("color","blue");
		//alert(doc);
	});
});

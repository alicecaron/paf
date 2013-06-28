
function makeBigWords (){
	$("[tfidf]").each(function() {
		$(this).css("font-size", sizeof($(this))+"px");
	});
}

function offBigWords (){
	$("[tfidf]").each(function() {
		$(this).css("font-size","");
	});
}

function sizeof(obj){
	return (10+3*Math.sqrt(obj.attr("tfidf")));
}

/* var last="";
$(".search").each(function(e){
	$(this).on("click",function(ev){
		alert("Asked: "+$(this).prev("input").attr("concerns")+"\n"+last$("."+$(this).prev("input").attr("concerns")).next($("."+$(this).prev("input").attr("concerns"))).html());
		last=
	});
}); */






$(".item").each(function(){
	$(this).on("click",function(){
		if($(this).attr('checked')=="checked"){
			$(this).attr('checked', false);
			$("."+$(this).attr("concerns")).each(function(){
				$(this).addClass("neutre");
			});
		}
		else{
			$(this).attr('checked', true);
			$("."+$(this).attr("concerns")).each(function(){
				$(this).removeClass("neutre");
			});
		}
	});
});

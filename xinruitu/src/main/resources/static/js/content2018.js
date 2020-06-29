;(function(){ var bp = document.createElement('script'); var curProtocol = window.location.protocol.split(':')[0]; if (curProtocol === 'https') { bp.src = 'push.js'/*tpa=https://zz.bdstatic.com/linksubmit/push.js*/; } else { bp.src = 'push-1.js'/*tpa=http://push.zhanzhang.baidu.com/push.js*/; } var s = document.getElementsByTagName("script")[0]; s.parentNode.insertBefore(bp, s); })(); (function(){ var src = (document.location.protocol == "http:") ? "http://js.passport.qihucdn.com/11.0.1.js?3f0b91cfa40f4dfa0cc28ea2fec01c54":"https://jspassport.ssl.qhimg.com/11.0.1.js?3f0b91cfa40f4dfa0cc28ea2fec01c54"; document.write('<\/script>');
})();

	
//扫码浏览
document.write("<script src='/gzgov/xhtml/js/qrcode.js'></script>");
document.write("<script src='/gzgov/xhtml/js/jquery.qrcode.js'></script>");
document.write("<script src='http://pv.sohu.com/cityjson?ie=utf-8'></script>");

if(window.screen.width<481){ //获取屏幕的的宽度
 var page_heig = $(document).scrollTop();            /* 初始化。用于第一次获取滚动条的高度 */
 var navigation = $('.contop').outerHeight();    
 
 $(window).scroll(function() {                       /* 滚动条触发事件 */
    var real_heig = $(document).scrollTop();    
 	$('.mainnav').hide();
	$(".allmenu i").addClass("icon-menu-outline");
	$(".allmenu i").removeClass("icon-close-outline");
    if (real_heig > navigation){     
        $('.contop').addClass('hide');          /* True 添加隐藏属性 */
    }else {
        $('.contop').removeClass('hide');       /* False 删除隐藏属性 */
    }
 
    if (real_heig < page_heig){                /* 触发后的高度 与 上次触发后的高度 */
       $('.contop').removeClass('hide');       /* True 删除隐藏属性 */
    }
    page_heig = $(document).scrollTop();     /* 再次获取滚动条的高度，用于下次触发事件后的对比 */
 });
}

//mp4格式视频兼容方法：
function convertMedia(){
	var videoDom = document.createElement("video") ;
	if (typeof(videoDom.canPlayType) != "undefined" && $(".zoomcon .embed").length> 0){
		for(var i = 0 ; i < mediaDom.length ; i++) {
			var el = mediaDom[i] ;
			var newMediaDom = document.createElement("video") ;
			newMediaDom.setAttribute("controls" , "controls") ;
			newMediaDom.setAttribute("preload" , "preload") ;
			newMediaDom.setAttribute("src" , el.getAttribute("flashvars").replace(/^vcastr_file=/ , "")) ;
			newMediaDom.setAttribute("width" , "60%") ; // el.width
			newMediaDom.setAttribute("height" , "auto") ; // el.height
			var parentDom = el.parentNode ;
			el.style.display="none" ;
			parentDom.appendChild(newMediaDom) ;
			//parentDom.removeChild(el) ;
		}
		var len = mediaDom.length ;
		for(var i = 1 ; mediaDom.length > 0 ; i++ ) {
			
			var parentDom = mediaDom[0].parentNode ;
			parentDom.removeChild(mediaDom[0]) ;
			
			if (i >= len){
				break ;
			}
		}
	}
}


$(function(){
	//顶部导航
	$(".allmenu i").click(function(){
		$(".mainnav").slideToggle("fast");
		$(".allmenu i").toggleClass("icon-menu-outline");
		$(".allmenu i").toggleClass("icon-close-outline");
	});

	//来源
	var lycon=$(".ly").text();
	var dwly=$(".curmb").text().split(">");
	var dwlycon=dwly[dwly.length-1]
	if($.trim(lycon)=="来源："){
		$(".ly").text(lycon+dwlycon)
	}
	//下拉显示标题
	if (loadBrowser() != 'mobile'){
		$('.content_slide').TMStickUp({});
	}

	//字体大小
	$(".fontsize i").click(function(){
            var thisEle = $("#zoomcon").css("font-size"); 
            //parseFloat的第二个参数表示转化的进制，10就表示转为10进制
            var textFontSize = parseFloat(thisEle , 10);
            var unit = thisEle.slice(-2); //获取单位
            var cName = $(this).attr("class");
            if(cName == "l" && textFontSize <30){
                textFontSize += 2;
            }else if(cName == "s" && textFontSize >12){
                textFontSize -= 2;
            }else if(cName == "m"){
				textFontSize = 16;
            }
            //设置para的字体大小
            $("#zoomcon").css("font-size",  textFontSize + unit );
            $("#zoomcon").children().css("font-size",  textFontSize + unit );
     });
	 
	//浏览次数
	$.post("/sofpro/gzyyqt/info_count/info_count3.jsp?url="+window.location.href.replace("?","&"), function(data){
   		var llcs= $.trim(data);
   		$(".view").html(llcs+"次");
 	});
	

	//获取点赞数
	
	var Num;
	var pageUrl = parent.document.location.href;
	var indexSplit = pageUrl.lastIndexOf("/");
	var indexShtml = pageUrl.lastIndexOf(".shtml");
	var info_id = pageUrl.substring(indexSplit+1,indexShtml);
	var urls1 = "/sofpro/gzyyqt/zan/dianzannew.jsp?url="+pageUrl;
	var urls2 = "/sofpro/gzyyqt/zan/zanSubmitnew.jsp?info_id="+info_id+"&ipAddr="+returnCitySN["cip"];
	
$.ajax({
	type:'get',
	url:urls1,
	success:function(res){
		Num=$.trim(res);
		//isZan=result.isDz; //是否点赞？  0是没有  1是有
		//zanNum=result.dzNum; //获取当前点赞数
		$(".nums").html(Num);
		
		//if(isZan==0){
		//}else{
			
		//}
	}
});

	//点赞
$('.dianzan').click(function(){
		$.post(urls2,function(result){
			if($.trim(result)=="SUCCESS"){
				$('.dianzan').append('<div id="zhan"><b>+1<\/b></\div>');
				$('#zhan').css({'position':'absolute','z-index':'1', 'color':'#C30','left':'20px','top':'0px'}).animate({top:-20,left:+10,opacity:0},'slow',function(){
					Num++;
					$('.nums').html(Num);
					$('.dianzan').addClass("yzan");
				});
			}else if($.trim(result)=="EXSIT"){
				alert("您已赞过!");
				$('.dianzan').addClass("yzan");
				return false;
			}else if($.trim(result)=="ERROR"){
				alert("提交出错!");
				return false;
			}
		});
		
	});
	
	//判断是否有副标题
	if ($.trim($(".sub_title").html())==$.trim($(".content_title").html())) {
      $(".sub_title").remove();
    }
	//判断是否有相关新闻
	if ($(".info_xgxw ul").children().length==0) {
      $(".info_xgxw").remove();
    }
	//政府规章的相关
	if (pageUrl.indexOf("s8263")!=-1) {
      $(".info_xgxw_title").html("相关解读");
    }
	//多媒体
	convertMedia();


});


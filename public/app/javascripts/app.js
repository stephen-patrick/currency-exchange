(function ($) {
	
  var App = function ()   {
	var pieChart;
	var barChart;
	
	function _init() {
		 _initLayout();
		 
		 
		 var $body, id;
		 $body = $("body");
		 if($body.length > 0) {
			 id = $body.attr("class");
				
			 if(id.length >0) {
				var idParts = id.split(" ");
				id = idParts[0];
				
				if(id) {
				    _initPage(id);
				}
			 }
		 }
		
		 
		 
	};
	 
	function _initLayout() {
		 $('#app_mnu-add-currency').click(function(e) {
			  e.stopPropagation();
		 });
		 
		 $('.date-picker').datepicker({
			 dateFormat: 'dd-M-yy',
		     onSelect: function(dateText) {
		    	 var $this = $(this);
		    	 var d = new Date();
		    	 dateText = dateText + " "  + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
		    	 $this.val(dateText);
		     }
			 
		 });
		 
	
		 
	};
	
	function _initPage(id) {
		if(id==='home') {
			_initHomePage();

		}
	};
	
	function _initHomePage() {
		var $container = $('#stats_container');
		var url = $container.attr("data-url");
		var urlWs = $container.attr("data-url-ws");
		var urlSSE= $container.attr("data-url-sse");
		
		var currencyView = new UI.Views.CurrencyView(dataModels, {
			addUrl: url
		});
		
		//rabbitmq not enabled
		if($container.attr("data-client-enabled")) {
			if($container.attr("data-client-enabled")==='false') {
				alert("NOT ENABLED");
				return;
			}
		}
		
		
		if($container.attr("data-client-type")==='ws') {
			var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
			var socketWs = new WS(urlWs);
					
			socketWs.onmessage = function(event) {
				var data = JSON.parse(event.data);
				currencyView.update(data);
			}
		}
		else {
			if(!!window.EventSource) {
				var source = new EventSource(urlSSE);
				
				source.addEventListener('message', function(e) {
					alert("1");
					var data = JSON.parse(event.data);
					currencyView.update(data);
				});
			}
			else {
				alert("Server Sent Events Not supported");
				
			}
		}
	}
	

	return {
        //main function to initiate template pages
        init: function () {
           _init();
        }
    };

	}();

$(document).ready(function () {

    App.init();
});

}(jQuery))
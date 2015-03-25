
if (typeof UI === 'undefined') {
    UI = {};
}

if (typeof UI.Views === 'undefined') {
    UI.Views = {};
}

(function (views) {
	
	function CurrencyView(data, options) {
		var self = this;
		self.options = options;
		self.models = [];
		self.currenyVolMap = {};
		self.currenyPairVolModels = [];
		self.currenyCountryVolModels = [];
		var lineChart = null;
		var barChart = null;
		
		
		self.update = function(message) {
			_updateStats(message);
		};
		
	   _init = function(data) {
		   _initModels(data);
		   
	
		   //send a sample currency message to the server via a normal post request
		   $('#butt_addCurrency').click(function() {
				var data = {userId: $("#userId").val(),
						currencyFrom: $('#currencyFrom').val(),
						currencyTo: $('#currencyTo').val(),
						amountBuy: $('#amountBuy').val(),
						amountSell: $('#amountSell').val(),
						rate: $('#rate').val(),
						orginatingCountry: $('#orginatingCountry').val(),
						timePlaced:  $('#timePlaced').val(),
				};
				
				data = ko.toJSON(data);
			
				$.ajax( {
		        	url: self.options.addUrl,
					"data": data,
					contentType: "application/json",
					dataType: "json",
					type: "POST",
					beforeSend: function(xhr, settings) {
					   $("#loading").show();
					},
					success: function(data) {
						var values = [];
						if(data.success) {
							alert("Success - Currency Request Added");
						}
					},
					error: function(xhr, status,error) {
						var data = JSON.parse(xhr.responseText);
						if(data.error) {
							alert(data.errorMessage +  ":"  + xhr.responseText);
						}
						if(data.errorMessage){
							alert(data.errorMessage +  ":"  + xhr.responseText);
						}
					},
					complete: function() {
						 $("#loading").hide();
					}
		        });
				
			});
		   
		   _initCharts();
	   };
	   
	   _initCharts = function() {
		   var barLabels = [];
		   var barStats = [];
		   
		   $(self.currenyPairVolModels).each(function(i,model) {
			   barLabels.push(model.name);
			   barStats.push(model.count);
		   });
		   
			var ctx = $("#chart_bar-volume").get(0).getContext("2d");
			// This will get the first returned node in the jQuery collection.
			self.barChart = new Chart(ctx).Bar({
			    labels: barLabels,
			    datasets: [
				    {
				        label: "My First dataset",
				        fillColor: "rgba(220,220,220,0.5)",
				        strokeColor: "rgba(220,220,220,0.8)",
				        highlightFill: "rgba(220,220,220,0.75)",
				        highlightStroke: "rgba(220,220,220,1)",
				        data: barStats
				    }
			       
			    ]
			},{});
			
			var lineLabels = [];
			var lineStats = [];
			   
			$(self.currenyCountryVolModels).each(function(i,model) {
				lineLabels.push(model.name);
				lineStats.push(model.count);
			});
			
		
			var ctx = $("#chart_line-volume").get(0).getContext("2d");
			// This will get the first returned node in the jQuery collection.
			self.lineChart = new Chart(ctx).Line({
			    labels: lineLabels,
			    datasets: [
			        {
			            label: "Requests By Country",
			            fillColor: "rgba(220,220,220,0.2)",
			            strokeColor: "rgba(220,220,220,1)",
			            pointColor: "rgba(220,220,220,1)",
			            pointStrokeColor: "#fff",
			            pointHighlightFill: "#fff",
			            pointHighlightStroke: "rgba(220,220,220,1)",
			            data: lineStats
			        },
			    ]
			},{});
			
		
	   };
	   
	   _updateCharts = function(fxVolPair, fxVolCountry) {
		   if(fxVolPair.count<=1) {
			   self.barChart.addData([fxVolPair.count], fxVolPair.name);
		   }
		   else {
			   self.barChart.datasets[0].bars[self.currenyPairVolModels.indexOf(fxVolPair)].value =fxVolPair.count;
			   self.barChart.update();
		   }
		   
		   if(fxVolCountry.count<=1) {
			   self.lineChart.addData([fxVolCountry.count], fxVolCountry.name);
		   }
		   else {
			   self.lineChart.datasets[0].points[self.currenyCountryVolModels.indexOf(fxVolCountry)].value =fxVolCountry.count;
			   self.lineChart.update();
		   }  
	   }
	   
	   _updateStats = function(model) {
		   var fx = new UI.Models.FxMessage(model);
		   
		   var fxVolPair = _addStat(self.currenyPairVolModels,new UI.Models.FxVolume({name: fx.currencyFrom + " - " + fx.currencyTo}));
		   var fxVolCountry =  _addStat(self.currenyCountryVolModels,new UI.Models.FxVolume({name: fx.orginatingCountry}));

	   	   _updateCharts(fxVolPair, fxVolCountry);
	   };
	   
	   
	   _initModels = function(data) {
		   	$(data).each(function(i, data) {
		   		var fx = new UI.Models.FxMessage(data);
		   		//self.models.push(fx);
		   		
		   		_addStat(self.currenyPairVolModels, new UI.Models.FxVolume({name: fx.currencyFrom + " - " + fx.currencyTo}));
		   		_addStat(self.currenyCountryVolModels, new UI.Models.FxVolume({name: fx.orginatingCountry}));
	
		   	});
	   };
	   
	   _addStat = function(store, model) {
		   var stat = null;
		   
		   if(!self.currenyVolMap[model.name]) {
	   			self.currenyVolMap[model.name] = model;
	   			store.push(model);
	   			stat = model;
	   		}
	   		else {
	   			self.currenyVolMap[model.name].increment();
	   			stat = self.currenyVolMap[model.name];
	   		}
		   
		    return stat;
	   };
	   
	   _init(data);
	}

	
	
	
	
	
	views.CurrencyView = CurrencyView;
	
}(UI.Views));
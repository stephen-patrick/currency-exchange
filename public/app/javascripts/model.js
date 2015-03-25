
if (typeof UI === 'undefined') {
    UI = {};
}

if (typeof UI.Models === 'undefined') {
    UI.Models = {};
}

(function (models) {
	
	function FxMessage(data) {
		var self = this;
		self.userId = null;
		self.currencyFrom = null
		self.currencyTo = null;
		self.amountSell = null;
		self.amountBuy = null;
		self.rate = null;
		self.orginatingCountry = null;
		
		self.update(data);
	}
	
	ko.utils.extend(FxMessage.prototype, {
		update: function(data) {
			var self = this;
			self.userId = (data.userId || null);
			self.currencyFrom = (data.currencyFrom|| null);
		   	self.currencyTo = (data.currencyTo || null);
		   	self.amountSell = (data.amountSell || null);
		   	self.amountBuy = (data.amountBuy || null);
		   	self.rate = (data.rate || null);
		   	self.orginatingCountry= (data.orginatingCountry || null);
		}
   });
	
   models.FxMessage = FxMessage;
  
   function FxVolume(data) {
	   var self = this;
	   var name = null;
	   var count = 0;

	   self.update(data);
	   
	   self.increment = function() {
		   self.count = self.count + 1;
	   }
   }
   
   ko.utils.extend(FxVolume.prototype, {
		update: function(data) {
			var self = this;
			self.name = (data.name || null);
			self.count = (data.count || 1);
		   
		}
  });
   
  models.FxVolume = FxVolume;
  
 
  
  
}(UI.Models));
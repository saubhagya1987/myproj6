function numberCVEN(inputId,outputId){
	alert("ok");
	var NUMBER2TEXT = {
	    ones: ['', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine', 'ten', 'eleven', 'twelve', 'thirteen', 'fourteen', 'fifteen', 'sixteen', 'seventeen', 'eighteen', 'nineteen'],
	    tens: ['', '', 'twenty', 'thirty', 'fourty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'],
	    sep: ['', ' thousand ', ' million ', ' billion ', ' trillion ', ' quadrillion ', ' quintillion ', ' sextillion ']
	};
	
	(function( ones, tens, sep ) {
	
	    var input = document.getElementById(inputId),
	        output = document.getElementById(outputId);
	    
	    input.onkeyup = function() {
	        var val = this.value,
	            arr = [],
	            str = '',
	            i = 0;
	        
	        if ( val.length === 0 ) {
	            output.textContent = 'Please type a number into the text-box.';
	            return;  
	        }
	        
	        val = parseInt( val, 10 );
	        if ( isNaN( val ) ) {
	            output.textContent = 'Invalid input.';
	            return;   
	        }
	        
	        while ( val ) {
	            arr.push( val % 1000 );
	            val = parseInt( val / 1000, 10 );   
	        }
	        
	        while ( arr.length ) {
	            str = (function( a ) {
	                var x = Math.floor( a / 100 ),
	                    y = Math.floor( a / 10 ) % 10,
	                    z = a % 10;
	                
	                return ( x > 0 ? ones[x] + ' hundred ' : '' ) +                 
	                       ( y >= 2 ? tens[y] + ' ' + ones[z] : ones[10*y + z] ); 
	            })( arr.shift() ) + sep[i++] + str;                     
	        }
	        
	        output.textContent = str;        
	    };
	    
	})( NUMBER2TEXT.ones, NUMBER2TEXT.tens, NUMBER2TEXT.sep );
}



function numberCVVI(inputId,outputId){
	var NUMBER2TEXT = {
	    ones: ['', 'một', 'hai', 'ba', 'bốn', 'năm', 'sáu', 'bảy', 'tám', 'chín', 'mười', 'mười một', 'mười hai', 'mười ba', 'mười bốn', 'mười lăm', 'mười sáu', 'mười bảy', 'mười tám', 'mười chín'],
	    tens: ['', '', 'hai mươi', 'ba mươi', 'bốn mươi', 'năm mươi', 'sáu mươi', 'bảy mươi', 'tám mươi', 'chín mươi'],
	    sep: ['', ' ngàn ', ' triệu ', ' tỷ ', ' ngàn tỷ ', ' triệu tỷ ', ' tỷ tỷ ', ' mười tỷ tỷ ']
	};
	
	(function( ones, tens, sep ) {
	
	    var input = document.getElementById(inputId),
	        output = document.getElementById(outputId);
	    
	    input.onkeyup = function() {
	        var val = this.value,
	            arr = [],
	            str = '',
	            i = 0;
	        
	        if ( val.length === 0 ) {
	            output.textContent = 'Please type a number into the text-box.';
	            return;  
	        }
	        
	        val = parseInt( val, 10 );
	        if ( isNaN( val ) ) {
	            output.textContent = 'Invalid input.';
	            return;   
	        }
	        
	        while ( val ) {
	            arr.push( val % 1000 );
	            val = parseInt( val / 1000, 10 );   
	        }
	        
	        while ( arr.length ) {
	            str = (function( a ) {
	                var x = Math.floor( a / 100 ),
	                    y = Math.floor( a / 10 ) % 10,
	                    z = a % 10;
	                
	                return ( x > 0 ? ones[x] + ' trăm ' : '' ) +                 
	                       ( y >= 2 ? tens[y] + ' ' + ones[z] : ones[10*y + z] ); 
	            })( arr.shift() ) + sep[i++] + str;                     
	        }
	        
	        output.textContent = str;        
	    };
	    
	})( NUMBER2TEXT.ones, NUMBER2TEXT.tens, NUMBER2TEXT.sep );
}
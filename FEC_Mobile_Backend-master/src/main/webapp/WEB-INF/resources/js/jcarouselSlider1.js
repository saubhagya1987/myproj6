(function($) {
    $(function() {
        $('.slider1 .jcarousel').jcarousel();

        $('.slider1 .jcarousel-control-prev')
            .on('jcarouselcontrol:active', function() {
                $(this).removeClass('inactive');
            })
            .on('jcarouselcontrol:inactive', function() {
                $(this).addClass('inactive');
            })
            .jcarouselControl({
                target: '-=1'
            });

        $('.slider1 .jcarousel-control-next')
            .on('jcarouselcontrol:active', function() {
                $(this).removeClass('inactive');
            })
            .on('jcarouselcontrol:inactive', function() {
                $(this).addClass('inactive');
            })
            .jcarouselControl({
                target: '+=1'
            });

        $('.slider1 .jcarousel-pagination')
            .on('jcarouselpagination:active', 'a', function() {
                $(this).addClass('active');
            })
            .on('jcarouselpagination:inactive', 'a', function() {
                $(this).removeClass('active');
            });
        var size = $('.slider1 .jcarousel ul li').size();
        if (size>1) {
        	$('.slider1 .jcarousel-pagination').jcarouselPagination({
                perPage: 1,
                item: function(page) {
                    return '<a href="#' + page + '"><label></label></a>';
                }
            });
		}
        
        
    });
})(jQuery);

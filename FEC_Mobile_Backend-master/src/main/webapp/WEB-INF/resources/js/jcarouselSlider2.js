(function($) {
    $(function() {
        var jcarousel = $('.slider2 .jcarousel');

        jcarousel
            .on('jcarousel:reload jcarousel:create', function () {
                var carousel = $(this),
                    width = carousel.innerWidth();
                var size = $(this).children().children().size();
                if (size == null || size == 0) {
                	size = 1;
				}
                if (size > 4) {
                	size = 4;
				}
                
                if (width >= 600) {
                    width = width /size;
                }

                carousel.jcarousel('items').css('width', Math.ceil(width) + 'px');
            })
            .jcarousel({
                wrap: 'circular'
            });

        $('.slider2 .jcarousel-control-prev')
            .jcarouselControl({
                target: '-=1'
            });

        $('.slider2 .jcarousel-control-next')
            .jcarouselControl({
                target: '+=1'
            });

        $('.slider2 .jcarousel-pagination')
            .on('jcarouselpagination:active', 'a', function() {
                $(this).addClass('active');
            })
            .on('jcarouselpagination:inactive', 'a', function() {
                $(this).removeClass('active');
            })
            .on('click', function(e) {
                e.preventDefault();
            })
            .jcarouselPagination({
                perPage: 1,
                item: function(page) {
                    return '<a href="#' + page + '">' + page + '</a>';
                }
            });
    });
})(jQuery);

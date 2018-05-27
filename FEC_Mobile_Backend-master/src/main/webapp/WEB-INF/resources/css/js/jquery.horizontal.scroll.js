(function (factory) {
  'use strict';

  if (typeof define === 'function' && define.amd) {
    // AMD. Register as an anonymous module.
    define(['jquery'], factory);
  } else if (typeof exports === 'object') {
    // Node/CommonJS
    factory(require('jquery'));
  } else {
    // Browser globals
    factory(jQuery);
  }
})(function ($) {
  'use strict';

  function int(x) {
    if (typeof x === 'string') {
      return parseInt(x, 10);
    } else {
      return ~~x;
    }
  }

  var defaultSettings = {
    wheelSpeed: 1,
    wheelPropagation: false,
    minScrollbarLength: null,
    maxScrollbarLength: null,
    useBothWheelAxes: false,
    useKeyboard: true,
    suppressScrollX: false,
    suppressScrollY: false,
    scrollXMarginOffset: 0,
    scrollYMarginOffset: 0,
    includePadding: false
  };

  var getEventClassName = (function () {
    var incrementingId = 0;
    return function () {
      var id = incrementingId;
      incrementingId += 1;
      return '.perfect-scrollbar-' + id;
    };
  })();

  $.fn.perfectScrollbar = function (suppliedSettings, option) {

    return this.each(function () {
      var settings = $.extend(true, {}, defaultSettings);
      var $this = $(this);

      if (typeof suppliedSettings === "object") {
        // Override default settings with any supplied
        $.extend(true, settings, suppliedSettings);
      } else {
        // If no setting was supplied, then the first param must be the option
        option = suppliedSettings;
      }

      // Catch options
      if (option === 'update') {
        if ($this.data('perfect-scrollbar-update')) {
          $this.data('perfect-scrollbar-update')();
        }
        return $this;
      }
      else if (option === 'destroy') {
        if ($this.data('perfect-scrollbar-destroy')) {
          $this.data('perfect-scrollbar-destroy')();
        }
        return $this;
      }

      if ($this.data('perfect-scrollbar')) {
        // if there's already perfect-scrollbar
        return $this.data('perfect-scrollbar');
      }


      // Or generate new perfectScrollbar

      $this.addClass('ps-container');

      var containerWidth;
      var containerHeight;
      var contentWidth;
      var contentHeight;

      var isRtl = $this.css('direction') === "rtl";
      var eventClassName = getEventClassName();
      var ownerDocument = this.ownerDocument || document;

      var $scrollbarXRail = $("<div class='ps-scrollbar-x-rail'>").appendTo($this);
      var $scrollbarX = $("<div class='ps-scrollbar-x'>").appendTo($scrollbarXRail);
      var scrollbarXActive;
      var scrollbarXWidth;
      var scrollbarXLeft;
      var scrollbarXBottom = int($scrollbarXRail.css('bottom'));
      var isScrollbarXUsingBottom = scrollbarXBottom === scrollbarXBottom; // !isNaN
      var scrollbarXTop = isScrollbarXUsingBottom ? null : int($scrollbarXRail.css('top'));
      var railBorderXWidth = int($scrollbarXRail.css('borderLeftWidth')) + int($scrollbarXRail.css('borderRightWidth'));

      var $scrollbarYRail = $("<div class='ps-scrollbar-y-rail'>").appendTo($this);
      var $scrollbarY = $("<div class='ps-scrollbar-y'>").appendTo($scrollbarYRail);
      var scrollbarYActive;
      var scrollbarYHeight;
      var scrollbarYTop;
      var scrollbarYRight = int($scrollbarYRail.css('right'));
      var isScrollbarYUsingRight = scrollbarYRight === scrollbarYRight; // !isNaN
      var scrollbarYLeft = isScrollbarYUsingRight ? null : int($scrollbarYRail.css('left'));
      var railBorderYWidth = int($scrollbarXRail.css('borderTopWidth')) + int($scrollbarXRail.css('borderBottomWidth'));

      function updateScrollTop(currentTop, deltaY) {
        var newTop = currentTop + deltaY;
        var maxTop = containerHeight - scrollbarYHeight;

        if (newTop < 0) {
          scrollbarYTop = 0;
        } else if (newTop > maxTop) {
          scrollbarYTop = maxTop;
        } else {
          scrollbarYTop = newTop;
        }

        var scrollTop = int(scrollbarYTop * (contentHeight - containerHeight) / (containerHeight - scrollbarYHeight));
        $this.scrollTop(scrollTop);
      }

      function updateScrollLeft(currentLeft, deltaX) {
        var newLeft = currentLeft + deltaX;
        var maxLeft = containerWidth - scrollbarXWidth;

        if (newLeft < 0) {
          scrollbarXLeft = 0;
        } else if (newLeft > maxLeft) {
          scrollbarXLeft = maxLeft;
        } else {
          scrollbarXLeft = newLeft;
        }

        var scrollLeft = int(scrollbarXLeft * (contentWidth - containerWidth) / (containerWidth - scrollbarXWidth));
        $this.scrollLeft(scrollLeft);
      }

      function getThumbSize(thumbSize) {
        if (settings.minScrollbarLength) {
          thumbSize = Math.max(thumbSize, settings.minScrollbarLength);
        }
        if (settings.maxScrollbarLength) {
          thumbSize = Math.min(thumbSize, settings.maxScrollbarLength);
        }
        return thumbSize;
      }

      function updateCss() {
        var xRailOffset = {width: containerWidth, display: scrollbarXActive ? "inherit" : "none"};
        if (isRtl) {
          xRailOffset.left = $this.scrollLeft() + containerWidth - contentWidth;
        } else {
          xRailOffset.left = $this.scrollLeft();
        }
        if (isScrollbarXUsingBottom) {
          xRailOffset.bottom = scrollbarXBottom - $this.scrollTop();
        } else {
          xRailOffset.top = scrollbarXTop + $this.scrollTop();
        }
        $scrollbarXRail.css(xRailOffset);

        var railYOffset = {top: $this.scrollTop(), height: containerHeight, display: scrollbarYActive ? "inherit" : "none"};

        if (isScrollbarYUsingRight) {
          if (isRtl) {
            railYOffset.right = contentWidth - $this.scrollLeft() - scrollbarYRight - $scrollbarY.outerWidth();
          } else {
            railYOffset.right = scrollbarYRight - $this.scrollLeft();
          }
        } else {
          if (isRtl) {
            railYOffset.left = $this.scrollLeft() + containerWidth * 2 - contentWidth - scrollbarYLeft - $scrollbarY.outerWidth();
          } else {
            railYOffset.left = scrollbarYLeft + $this.scrollLeft();
          }
        }
        $scrollbarYRail.css(railYOffset);

        $scrollbarX.css({left: scrollbarXLeft, width: scrollbarXWidth - railBorderXWidth});
        $scrollbarY.css({top: scrollbarYTop, height: scrollbarYHeight - railBorderYWidth});
      }

      function updateGeometry() {
        // Hide scrollbars not to affect scrollWidth and scrollHeight
        $scrollbarXRail.hide();
        $scrollbarYRail.hide();

        containerWidth = settings.includePadding ? $this.innerWidth() : $this.width();
        containerHeight = settings.includePadding ? $this.innerHeight() : $this.height();
        contentWidth = $this.prop('scrollWidth');
        contentHeight = $this.prop('scrollHeight');

        if (!settings.suppressScrollX && containerWidth + settings.scrollXMarginOffset < contentWidth) {
          scrollbarXActive = true;
          scrollbarXWidth = getThumbSize(int(containerWidth * containerWidth / contentWidth));
          scrollbarXLeft = int($this.scrollLeft() * (containerWidth - scrollbarXWidth) / (contentWidth - containerWidth));
        } else {
          scrollbarXActive = false;
          scrollbarXWidth = 0;
          scrollbarXLeft = 0;
          $this.scrollLeft(0);
        }

        if (!settings.suppressScrollY && containerHeight + settings.scrollYMarginOffset < contentHeight) {
          scrollbarYActive = true;
          scrollbarYHeight = getThumbSize(int(containerHeight * containerHeight / contentHeight));
          scrollbarYTop = int($this.scrollTop() * (containerHeight - scrollbarYHeight) / (contentHeight - containerHeight));
        } else {
          scrollbarYActive = false;
          scrollbarYHeight = 0;
          scrollbarYTop = 0;
          $this.scrollTop(0);
        }

        if (scrollbarXLeft >= containerWidth - scrollbarXWidth) {
          scrollbarXLeft = containerWidth - scrollbarXWidth;
        }
        if (scrollbarYTop >= containerHeight - scrollbarYHeight) {
          scrollbarYTop = containerHeight - scrollbarYHeight;
        }

        updateCss();

        if (scrollbarXActive) {
          $this.addClass('ps-active-x');
        } else {
          $this.removeClass('ps-active-x');
        }

        if (scrollbarYActive) {
          $this.addClass('ps-active-y');
        } else {
          $this.removeClass('ps-active-y');
        }

        // Show scrollbars if needed after updated
        if (!settings.suppressScrollX) {
          $scrollbarXRail.show();
        }
        if (!settings.suppressScrollY) {
          $scrollbarYRail.show();
        }
      }

      function bindMouseScrollXHandler() {
        var currentLeft;
        var currentPageX;

        $scrollbarX.bind('mousedown' + eventClassName, function (e) {
          currentPageX = e.pageX;
          currentLeft = $scrollbarX.position().left;
          $scrollbarXRail.addClass('in-scrolling');
          e.stopPropagation();
          e.preventDefault();
        });

        $(ownerDocument).bind('mousemove' + eventClassName, function (e) {
          if ($scrollbarXRail.hasClass('in-scrolling')) {
            updateScrollLeft(currentLeft, e.pageX - currentPageX);
            updateGeometry();
            e.stopPropagation();
            e.preventDefault();
          }
        });

        $(ownerDocument).bind('mouseup' + eventClassName, function (e) {
          if ($scrollbarXRail.hasClass('in-scrolling')) {
            $scrollbarXRail.removeClass('in-scrolling');
          }
        });

        currentLeft =
        currentPageX = null;
      }

      function bindMouseScrollYHandler() {
        var currentTop;
        var currentPageY;

        $scrollbarY.bind('mousedown' + eventClassName, function (e) {
          currentPageY = e.pageY;
          currentTop = $scrollbarY.position().top;
          $scrollbarYRail.addClass('in-scrolling');
          e.stopPropagation();
          e.preventDefault();
        });

        $(ownerDocument).bind('mousemove' + eventClassName, function (e) {
          if ($scrollbarYRail.hasClass('in-scrolling')) {
            updateScrollTop(currentTop, e.pageY - currentPageY);
            updateGeometry();
            e.stopPropagation();
            e.preventDefault();
          }
        });

        $(ownerDocument).bind('mouseup' + eventClassName, function (e) {
          if ($scrollbarYRail.hasClass('in-scrolling')) {
            $scrollbarYRail.removeClass('in-scrolling');
          }
        });

        currentTop =
        currentPageY = null;
      }

      // check if the default scrolling should be prevented.
      function shouldPreventDefault(deltaX, deltaY) {
        var scrollTop = $this.scrollTop();
        if (deltaX === 0) {
          if (!scrollbarYActive) {
            return false;
          }
          if ((scrollTop === 0 && deltaY > 0) || (scrollTop >= contentHeight - containerHeight && deltaY < 0)) {
            return !settings.wheelPropagation;
          }
        }

        var scrollLeft = $this.scrollLeft();
        if (deltaY === 0) {
          if (!scrollbarXActive) {
            return false;
          }
          if ((scrollLeft === 0 && deltaX < 0) || (scrollLeft >= contentWidth - containerWidth && deltaX > 0)) {
            return !settings.wheelPropagation;
          }
        }
        return true;
      }

      function bindMouseWheelHandler() {
        var shouldPrevent = false;

        function getDeltaFromEvent(e) {
          var deltaX = e.originalEvent.deltaX;
          var deltaY = -1 * e.originalEvent.deltaY;

          if (typeof deltaX === "undefined" || typeof deltaY === "undefined") {
            // OS X Safari
            deltaX = -1 * e.originalEvent.wheelDeltaX / 6;
            deltaY = e.originalEvent.wheelDeltaY / 6;
          }

          if (e.originalEvent.deltaMode && e.originalEvent.deltaMode === 1) {
            // Firefox in deltaMode 1: Line scrolling
            deltaX *= 10;
            deltaY *= 10;
          }

          if (deltaX !== deltaX && deltaY !== deltaY/* NaN checks */) {
            // IE in some mouse drivers
            deltaX = 0;
            deltaY = e.originalEvent.wheelDelta;
          }

          return [deltaX, deltaY];
        }

        function mousewheelHandler(e) {
          var delta = getDeltaFromEvent(e);

          var deltaX = delta[0];
          var deltaY = delta[1];

          shouldPrevent = false;
          if (!settings.useBothWheelAxes) {
            // deltaX will only be used for horizontal scrolling and deltaY will
            // only be used for vertical scrolling - this is the default
            $this.scrollTop($this.scrollTop() - (deltaY * settings.wheelSpeed));
            $this.scrollLeft($this.scrollLeft() + (deltaX * settings.wheelSpeed));
          } else if (scrollbarYActive && !scrollbarXActive) {
            // only vertical scrollbar is active and useBothWheelAxes option is
            // active, so let's scroll vertical bar using both mouse wheel axes
            if (deltaY) {
              $this.scrollTop($this.scrollTop() - (deltaY * settings.wheelSpeed));
            } else {
              $this.scrollTop($this.scrollTop() + (deltaX * settings.wheelSpeed));
            }
            shouldPrevent = true;
          } else if (scrollbarXActive && !scrollbarYActive) {
            // useBothWheelAxes and only horizontal bar is active, so use both
            // wheel axes for horizontal bar
            if (deltaX) {
              $this.scrollLeft($this.scrollLeft() + (deltaX * settings.wheelSpeed));
            } else {
              $this.scrollLeft($this.scrollLeft() - (deltaY * settings.wheelSpeed));
            }
            shouldPrevent = true;
          }

          updateGeometry();

          shouldPrevent = (shouldPrevent || shouldPreventDefault(deltaX, deltaY));
          if (shouldPrevent) {
            e.stopPropagation();
            e.preventDefault();
          }
        }

        if (typeof window.onwheel !== "undefined") {
          $this.bind('wheel' + eventClassName, mousewheelHandler);
        } else if (typeof window.onmousewheel !== "undefined") {
          $this.bind('mousewheel' + eventClassName, mousewheelHandler);
        }
      }

      function bindKeyboardHandler() {
        var hovered = false;
        $this.bind('mouseenter' + eventClassName, function (e) {
          hovered = true;
        });
        $this.bind('mouseleave' + eventClassName, function (e) {
          hovered = false;
        });

        var shouldPrevent = false;
        $(ownerDocument).bind('keydown' + eventClassName, function (e) {
          if (e.isDefaultPrevented && e.isDefaultPrevented()) {
            return;
          }

          if (!hovered) {
            return;
          }

          var activeElement = document.activeElement ? document.activeElement : ownerDocument.activeElement;
          // go deeper if element is a webcomponent
          while (activeElement.shadowRoot) {
            activeElement = activeElement.shadowRoot.activeElement;
          }
          if ($(activeElement).is(":input,[contenteditable]")) {
            return;
          }

          var deltaX = 0;
          var deltaY = 0;

          switch (e.which) {
          case 37: // left
            deltaX = -30;
            break;
          case 38: // up
            deltaY = 30;
            break;
          case 39: // right
            deltaX = 30;
            break;
          case 40: // down
            deltaY = -30;
            break;
          case 33: // page up
            deltaY = 90;
            break;
          case 32: // space bar
          case 34: // page down
            deltaY = -90;
            break;
          case 35: // end
            if (e.ctrlKey) {
              deltaY = -contentHeight;
            } else {
              deltaY = -containerHeight;
            }
            break;
          case 36: // home
            if (e.ctrlKey) {
              deltaY = $this.scrollTop();
            } else {
              deltaY = containerHeight;
            }
            break;
          default:
            return;
          }

          $this.scrollTop($this.scrollTop() - deltaY);
          $this.scrollLeft($this.scrollLeft() + deltaX);

          shouldPrevent = shouldPreventDefault(deltaX, deltaY);
          if (shouldPrevent) {
            e.preventDefault();
          }
        });
      }

      function bindRailClickHandler() {
        function stopPropagation(e) { e.stopPropagation(); }

        $scrollbarY.bind('click' + eventClassName, stopPropagation);
        $scrollbarYRail.bind('click' + eventClassName, function (e) {
          var halfOfScrollbarLength = int(scrollbarYHeight / 2);
          var positionTop = e.pageY - $scrollbarYRail.offset().top - halfOfScrollbarLength;
          var maxPositionTop = containerHeight - scrollbarYHeight;
          var positionRatio = positionTop / maxPositionTop;

          if (positionRatio < 0) {
            positionRatio = 0;
          } else if (positionRatio > 1) {
            positionRatio = 1;
          }

          $this.scrollTop((contentHeight - containerHeight) * positionRatio);
        });

        $scrollbarX.bind('click' + eventClassName, stopPropagation);
        $scrollbarXRail.bind('click' + eventClassName, function (e) {
          var halfOfScrollbarLength = int(scrollbarXWidth / 2);
          var positionLeft = e.pageX - $scrollbarXRail.offset().left - halfOfScrollbarLength;
          var maxPositionLeft = containerWidth - scrollbarXWidth;
          var positionRatio = positionLeft / maxPositionLeft;

          if (positionRatio < 0) {
            positionRatio = 0;
          } else if (positionRatio > 1) {
            positionRatio = 1;
          }

          $this.scrollLeft((contentWidth - containerWidth) * positionRatio);
        });
      }

      function bindTouchHandler() {
        function applyTouchMove(differenceX, differenceY) {
          $this.scrollTop($this.scrollTop() - differenceY);
          $this.scrollLeft($this.scrollLeft() - differenceX);

          updateGeometry();
        }

        var startOffset = {};
        var startTime = 0;
        var speed = {};
        var breakingProcess = null;
        var inGlobalTouch = false;

        function globalTouchStart(e) {
          inGlobalTouch = true;
        }
        function globalTouchEnd(e) {
          inGlobalTouch = false;
        }

        function getTouch(e) {
          if (e.originalEvent.targetTouches) {
            return e.originalEvent.targetTouches[0];
          } else {
            // Maybe IE pointer
            return e.originalEvent;
          }
        }
        function touchStart(e) {
          var touch = getTouch(e);

          startOffset.pageX = touch.pageX;
          startOffset.pageY = touch.pageY;

          startTime = (new Date()).getTime();

          if (breakingProcess !== null) {
            clearInterval(breakingProcess);
          }

          e.stopPropagation();
        }
        function touchMove(e) {
          if (!inGlobalTouch && e.originalEvent.targetTouches.length === 1) {
            var touch = getTouch(e);

            var currentOffset = {pageX: touch.pageX, pageY: touch.pageY};

            var differenceX = currentOffset.pageX - startOffset.pageX;
            var differenceY = currentOffset.pageY - startOffset.pageY;

            applyTouchMove(differenceX, differenceY);
            startOffset = currentOffset;

            var currentTime = (new Date()).getTime();

            var timeGap = currentTime - startTime;
            if (timeGap > 0) {
              speed.x = differenceX / timeGap;
              speed.y = differenceY / timeGap;
              startTime = currentTime;
            }

            e.preventDefault();
          }
        }
        function touchEnd(e) {
          clearInterval(breakingProcess);
          breakingProcess = setInterval(function () {
            if (Math.abs(speed.x) < 0.01 && Math.abs(speed.y) < 0.01) {
              clearInterval(breakingProcess);
              return;
            }

            applyTouchMove(speed.x * 30, speed.y * 30);

            speed.x *= 0.8;
            speed.y *= 0.8;
          }, 10);
        }

        $(window).bind("touchstart" + eventClassName, globalTouchStart);
        $(window).bind("touchend" + eventClassName, globalTouchEnd);
        $this.bind("touchstart" + eventClassName, touchStart);
        $this.bind("touchmove" + eventClassName, touchMove);
        $this.bind("touchend" + eventClassName, touchEnd);

        if (window.PointerEvent) {
          $(window).bind("pointerdown" + eventClassName, globalTouchStart);
          $(window).bind("pointerup" + eventClassName, globalTouchEnd);
          $this.bind("pointerdown" + eventClassName, touchStart);
          $this.bind("pointermove" + eventClassName, touchMove);
          $this.bind("pointerup" + eventClassName, touchEnd);
        } else if (window.MSPointerEvent) {
          $(window).bind("MSPointerDown" + eventClassName, globalTouchStart);
          $(window).bind("MSPointerUp" + eventClassName, globalTouchEnd);
          $this.bind("MSPointerDown" + eventClassName, touchStart);
          $this.bind("MSPointerMove" + eventClassName, touchMove);
          $this.bind("MSPointerUp" + eventClassName, touchEnd);
        }
      }

      function bindScrollHandler() {
        $this.bind('scroll' + eventClassName, function (e) {
          updateGeometry();
        });
      }

      function destroy() {
        $this.unbind(eventClassName);
        $(window).unbind(eventClassName);
        $(ownerDocument).unbind(eventClassName);
        $this.data('perfect-scrollbar', null);
        $this.data('perfect-scrollbar-update', null);
        $this.data('perfect-scrollbar-destroy', null);
        $scrollbarX.remove();
        $scrollbarY.remove();
        $scrollbarXRail.remove();
        $scrollbarYRail.remove();

        // clean all variables
        $scrollbarXRail =
        $scrollbarYRail =
        $scrollbarX =
        $scrollbarY =
        scrollbarXActive =
        scrollbarYActive =
        containerWidth =
        containerHeight =
        contentWidth =
        contentHeight =
        scrollbarXWidth =
        scrollbarXLeft =
        scrollbarXBottom =
        isScrollbarXUsingBottom =
        scrollbarXTop =
        scrollbarYHeight =
        scrollbarYTop =
        scrollbarYRight =
        isScrollbarYUsingRight =
        scrollbarYLeft =
        isRtl =
        eventClassName = null;
      }

      var supportsTouch = (('ontouchstart' in window) || window.DocumentTouch && document instanceof window.DocumentTouch);
      var supportsIePointer = window.navigator.msMaxTouchPoints !== null;

      function initialize() {
        updateGeometry();
        bindScrollHandler();
        bindMouseScrollXHandler();
        bindMouseScrollYHandler();
        bindRailClickHandler();
        bindMouseWheelHandler();

        if (supportsTouch || supportsIePointer) {
          bindTouchHandler();
        }
        if (settings.useKeyboard) {
          bindKeyboardHandler();
        }
        $this.data('perfect-scrollbar', $this);
        $this.data('perfect-scrollbar-update', updateGeometry);
        $this.data('perfect-scrollbar-destroy', destroy);
      }

      initialize();

      return $this;
    });
  };
});



(function($){var defaultSettings={wheelSpeed:10,wheelPropagation:false};$.fn.perfectScrollbar=function(suppliedSettings,option){var settings=$.extend(true,{},defaultSettings);if(typeof suppliedSettings==="object"){$.extend(true,settings,suppliedSettings)}else{option=suppliedSettings}if(option==="update"){if($(this).data("perfect_scrollbar_update")){$(this).data("perfect_scrollbar_update")()}return $(this)}else if(option==="destroy"){if($(this).data("perfect_scrollbar_destroy")){$(this).data("perfect_scrollbar_destroy")()}return $(this)}if($(this).data("perfect_scrollbar")){return $(this).data("perfect_scrollbar")}var $this=$(this).addClass("ps-container"),$content=$(this).children(),$scrollbar_x=$("<div class='ps-scrollbar-x'></div>").appendTo($this),$scrollbar_y=$("<div class='ps-scrollbar-y'></div>").appendTo($this),container_width,container_height,content_width,content_height,scrollbar_x_width,scrollbar_x_left,scrollbar_x_bottom=parseInt($scrollbar_x.css("bottom"),10),scrollbar_y_height,scrollbar_y_top,scrollbar_y_right=parseInt($scrollbar_y.css("right"),10);var updateContentScrollTop=function(){var scroll_top=parseInt(scrollbar_y_top*content_height/container_height,10);$this.scrollTop(scroll_top);$scrollbar_x.css({bottom:scrollbar_x_bottom-scroll_top})};var updateContentScrollLeft=function(){var scroll_left=parseInt(scrollbar_x_left*content_width/container_width,10);$this.scrollLeft(scroll_left);$scrollbar_y.css({right:scrollbar_y_right-scroll_left})};var updateBarSizeAndPosition=function(){container_width=$this.width();container_height=$this.height();content_width=$content.outerWidth(false);content_height=$content.outerHeight(false);if(container_width<content_width){scrollbar_x_width=parseInt(container_width*container_width/content_width,10);scrollbar_x_left=parseInt($this.scrollLeft()*container_width/content_width,10)}else{scrollbar_x_width=0;scrollbar_x_left=0;$this.scrollLeft(0)}if(container_height<content_height){scrollbar_y_height=parseInt(container_height*container_height/content_height,10);scrollbar_y_top=parseInt($this.scrollTop()*container_height/content_height,10)}else{scrollbar_y_height=0;scrollbar_y_left=0;$this.scrollTop(0)}$scrollbar_x.css({left:scrollbar_x_left+$this.scrollLeft(),bottom:scrollbar_x_bottom-$this.scrollTop(),width:scrollbar_x_width});$scrollbar_y.css({top:scrollbar_y_top+$this.scrollTop(),right:scrollbar_y_right-$this.scrollLeft(),height:scrollbar_y_height})};var moveBarX=function(current_left,delta_x){var new_left=current_left+delta_x,max_left=container_width-scrollbar_x_width;if(new_left<0){scrollbar_x_left=0}else if(new_left>max_left){scrollbar_x_left=max_left}else{scrollbar_x_left=new_left}$scrollbar_x.css({left:scrollbar_x_left+$this.scrollLeft()})};var moveBarY=function(current_top,delta_y){var new_top=current_top+delta_y,max_top=container_height-scrollbar_y_height;if(new_top<0){scrollbar_y_top=0}else if(new_top>max_top){scrollbar_y_top=max_top}else{scrollbar_y_top=new_top}$scrollbar_y.css({top:scrollbar_y_top+$this.scrollTop()})};var bindMouseScrollXHandler=function(){var current_left,current_page_x;$scrollbar_x.bind("mousedown.perfect-scroll",function(e){current_page_x=e.pageX;current_left=$scrollbar_x.position().left;$scrollbar_x.addClass("in-scrolling");e.stopPropagation();e.preventDefault()});$(document).bind("mousemove.perfect-scroll",function(e){if($scrollbar_x.hasClass("in-scrolling")){moveBarX(current_left,e.pageX-current_page_x);updateContentScrollLeft();e.stopPropagation();e.preventDefault()}});$(document).bind("mouseup.perfect-scroll",function(e){if($scrollbar_x.hasClass("in-scrolling")){$scrollbar_x.removeClass("in-scrolling")}})};var bindMouseScrollYHandler=function(){var current_top,current_page_y;$scrollbar_y.bind("mousedown.perfect-scroll",function(e){current_page_y=e.pageY;current_top=$scrollbar_y.position().top;$scrollbar_y.addClass("in-scrolling");e.stopPropagation();e.preventDefault()});$(document).bind("mousemove.perfect-scroll",function(e){if($scrollbar_y.hasClass("in-scrolling")){moveBarY(current_top,e.pageY-current_page_y);updateContentScrollTop();e.stopPropagation();e.preventDefault()}});$(document).bind("mouseup.perfect-scroll",function(e){if($scrollbar_y.hasClass("in-scrolling")){$scrollbar_y.removeClass("in-scrolling")}})};var bindMouseWheelHandler=function(){var shouldPreventDefault=function(deltaX,deltaY){var scrollTop=$this.scrollTop();if(scrollTop===0&&deltaY>0&&deltaX===0){return!settings.wheelPropagation}else if(scrollTop>=content_height-container_height&&deltaY<0&&deltaX===0){return!settings.wheelPropagation}var scrollLeft=$this.scrollLeft();if(scrollLeft===0&&deltaX<0&&deltaY===0){return!settings.wheelPropagation}else if(scrollLeft>=content_width-container_width&&deltaX>0&&deltaY===0){return!settings.wheelPropagation}return true};$this.mousewheel(function(e,delta,deltaX,deltaY){$this.scrollTop($this.scrollTop()-deltaY*settings.wheelSpeed);$this.scrollLeft($this.scrollLeft()+deltaX*settings.wheelSpeed);updateBarSizeAndPosition();if(shouldPreventDefault(deltaX,deltaY)){e.preventDefault()}})};var bindMobileTouchHandler=function(){var applyTouchMove=function(difference_x,difference_y){$this.scrollTop($this.scrollTop()-difference_y);$this.scrollLeft($this.scrollLeft()-difference_x);updateBarSizeAndPosition()};var start_coords={},start_time=0,speed={},breaking_process=null;$this.bind("touchstart.perfect-scroll",function(e){var touch=e.originalEvent.targetTouches[0];start_coords.pageX=touch.pageX;start_coords.pageY=touch.pageY;start_time=(new Date).getTime();if(breaking_process!==null){clearInterval(breaking_process)}});$this.bind("touchmove.perfect-scroll",function(e){var touch=e.originalEvent.targetTouches[0];var current_coords={};current_coords.pageX=touch.pageX;current_coords.pageY=touch.pageY;var difference_x=current_coords.pageX-start_coords.pageX,difference_y=current_coords.pageY-start_coords.pageY;applyTouchMove(difference_x,difference_y);start_coords=current_coords;var current_time=(new Date).getTime();speed.x=difference_x/(current_time-start_time);speed.y=difference_y/(current_time-start_time);start_time=current_time;e.preventDefault()});$this.bind("touchend.perfect-scroll",function(e){breaking_process=setInterval(function(){if(Math.abs(speed.x)<.01&&Math.abs(speed.y)<.01){clearInterval(breaking_process);return}applyTouchMove(speed.x*30,speed.y*30);speed.x*=.8;speed.y*=.8},10)})};var destroy=function(){$scrollbar_x.remove();$scrollbar_y.remove();$this.unbind("mousewheel");$this.unbind("touchstart.perfect-scroll");$this.unbind("touchmove.perfect-scroll");$this.unbind("touchend.perfect-scroll");$(window).unbind("mousemove.perfect-scroll");$(window).unbind("mouseup.perfect-scroll");$this.data("perfect_scrollbar",null);$this.data("perfect_scrollbar_update",null);$this.data("perfect_scrollbar_destroy",null)};var isMobile=/Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent);var initialize=function(){updateBarSizeAndPosition();bindMouseScrollXHandler();bindMouseScrollYHandler();if(isMobile)bindMobileTouchHandler();if($this.mousewheel)bindMouseWheelHandler();$this.data("perfect_scrollbar",$this);$this.data("perfect_scrollbar_update",updateBarSizeAndPosition);$this.data("perfect_scrollbar_destroy",destroy)};initialize();return $this}})(jQuery);


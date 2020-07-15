;(function(window, document, $){

    function getViewHeight(){
        var height = document.documentElement.clientHeight||document.body.clientHeight;
        return height;
    }

    function getViewWidth(){
        var width = document.documentElement.clientWidth||document.body.clientWidth;
        return width;
    }

    var LeftRightLayout = function(){
        this.$left = $('#layout-left');
        this.$right = $('#layout-right');
        this.$split = $('#layout-split');
    };


    LeftRightLayout.prototype.display = function(opt){
        var viewHeight = getViewHeight();
        var viewWidth = getViewWidth();

        this.opt = $.extend({x: 0.2 * viewWidth}, this.opt, opt);
        this.leftWidth = this.opt.x;
        this.splitWidth = 2;
        this.splitLeft = this.leftWidth+1;
        this.rightLeft = this.splitLeft + this.splitWidth + 2;
        this.rightWidth = viewWidth - this.rightLeft;

        this.$left.css({
            position:'absolute',
            top:0,
            left:0,
            'min-width':this.leftWidth+'px',
            width:this.leftWidth+'px',
            height:viewHeight+'px',
            'max-height':viewHeight+'px',
            overflow:'auto'
        });

        this.$split.css({
            position:'absolute',
            left: this.splitLeft+'px',
            top:0,
            'max-width':this.splitWidth+'px',
            width:this.splitWidth+'px',
            height:viewHeight+'px',
            'max-height':viewHeight+'px',
            'background-color':'#b0c4de',
            'border-color':'#b0c4de',
            'border-style':'solid',
            'cursor':'e-resize',
        });

        this.$right.css({
            position:'absolute',
            top:0,
            left:this.rightLeft+'px',
            'max-width':this.rightWidth+'px',
            width:this.rightWidth+'px',
            height:viewHeight+'px',
            'max-height':viewHeight+'px',
            overflow:'auto',
        });
    }

    LeftRightLayout.prototype.bind = function(){
        var self = this;
        var viewWidth = getViewWidth();
        var maxLeft = self.splitWidth;
        var maxRight = viewWidth - self.splitWidth;

        self.$split.off('mousedown').on('mousedown', function(ev){
            ev.preventDefault();
            self.resize = true;

            $(document).off('mousemove').on('mousemove', function(e){
                if(self.resize){
                    var pos = {x:e.clientX, y:e.clientY};
                    if(pos.x<=maxLeft || pos.x>=maxRight) return;
                    self.display(pos);
                }
            });

            $(document).off('mouseup').on('mouseup', function(e){
                if(self.resize){
                    self.resize = false;
                    var pos = {x:e.clientX, y:e.clientY};
                    if(pos.x<=maxLeft || pos.x>=maxRight) return;
                    self.display(pos);
                }
            });

        });
    }

    var layout = new LeftRightLayout();
    layout.display();
    layout.bind();

    window.onresize = function(){
        layout.display();
    };
}(window, document, jQuery))
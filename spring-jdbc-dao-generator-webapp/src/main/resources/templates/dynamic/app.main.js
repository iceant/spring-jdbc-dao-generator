;(function(window, document, $){
    $layoutLeft = $('#layout-left');
    $layoutRight = $('#layout-right');

    var Fragment = function(content){
        var self = this;
        self.__proto__.content = content.content;
    };
    Fragment.prototype.run=function(){
        var self = this;
        var args = arguments;
        var n = Array.prototype.shift.apply(args, args);
        var c = self.content;
        if(!c) return;
        var s = c[n];
        if(s) {
            var f = eval(s);
            if($app.isFunction(f)) {
                return f.apply(this, args);
            }
        }
    }
    Fragment.prototype.part=function(n){
        return this.content[n];
    }

    $.when(
        $.get('@rocker.$.path("/fragments/Sidebar")')
    ).then(function(
        SidebarContent
    ){
       var Sidebar = new Fragment(SidebarContent);
       var $sidebar = Sidebar.run('main.js');
       $sidebar.display({el:'#layout-left'});
    });

}(window, document, jQuery));
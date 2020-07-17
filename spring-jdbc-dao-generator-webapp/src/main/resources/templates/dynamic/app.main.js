;(function(window, document, $){
    $layoutLeft = $('#layout-left');
    $layoutRight = $('#layout-right');

    let Fragment = function(content){
        let self = this;
        self.content = content.content;
        self.cc = []; /*code cache*/
    };
    Fragment.prototype.run=function(){
        let self = this;
        let args = arguments;
        let n = Array.prototype.shift.apply(args, args);
        let c = self.content;
        if(!c) return;
        let s = c[n];
        if(s) {
            let f = self.cc[s];
            console.log(f);
            if(!f){
                f = eval(s);
                self.cc[s] = f;
            }
            if($app.isFunction(f)) {
                return f.apply(this, args);
            }
        }
    }
    Fragment.prototype.part=function(n){
        return this.content[n];
    }

    $.when(
        $.get('@rocker.$.path("/fragments/Widgets")')
    ).then(function(
        Widgets
    ){
       let $fragment = new Fragment(Widgets);
       let $sidebar = $fragment.run('main.js');
       $sidebar.display({el:'#layout-left'});
    });

}(window, document, jQuery));
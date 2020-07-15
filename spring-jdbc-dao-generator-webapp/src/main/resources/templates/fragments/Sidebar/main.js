;(function(window, document, $){

    return function(){
        var widget = this;
        var _id = $app.uid();
        var _event = $Event.create(_id);
        return {
            id:_id,
            $event:_event,
            events:{
                PROJECT_ITEM_CLICK:'project.item.click'
            },
            options:{},
            showProject:function(opt){
                let id = opt.id;
                if(!id) return;
                let $detail  = widget.run('js/project_detail.js');
                $detail.display({id:id, el:'#layout-right'});
            },
            display:function(opt){
                var self = this;
                opt = $.extend(self.options, opt);
                var template = widget.part('htmls/sidebar.html');
                $service.listProjects({}, function(data){
                    var model=$.extend({
                        wid:self.id
                    }, data.results);
                    var html = Mustache.render(template, model);
                    $(opt.el).html(html);
                    self.handleEvent();
                });
            },
            handleEvent:function(){
                let self = this;
                // -----------------------------------------------------------
                // create project
                $('#btnCreate'+self.id).off('click').on('click', function(){
                    let template = widget.part('htmls/project_new.html');
                    let model = {wid:self.id};
                    let html = Mustache.render(template, model);

                    $.confirm({
                        autoClose:false,
                        closeIcon:true,
                        columnClass:'large',
                        title:'@rocker.$.msg("text.project.create_new")',
                        content:html,
                        buttons:{
                            confirm:{
                                btnClass:'btn btn-primary',
                                text:'@rocker.$.msg("text.confirm")',
                                action:function(){
                                    let name = $('#projectName'+self.id).val();
                                    let folder = $('#projectFolder'+self.id).val();
                                    $service.createProject({name:name, folder:folder}, function(data){
                                        self.display();
                                    });
                                }
                            },
                            close:{
                                btnClass: 'btn btn-success',
                                text:'@rocker.$.msg("text.close")',
                                action:function(){}
                            }
                        }
                    });
                });

                // -----------------------------------------------------------
                // click project item
                $('[id^="btnItem"]').each(function(i, e){
                    let $e = $(e);
                    $e.off('click').on('click', function(){
                        let id = $e.attr('data-id');
                        self.showProject({id:id});
                    });
                });
            }
        }
    };

}(window, document, jQuery));
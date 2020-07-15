;(function(window, document, $){
    return function(){
        var widget = this;
        var _id = $app.uid();
        var _event = $Event.create(_id);
        return {
            $el:null,
            id:_id,
            $event:_event,
            events:{},
            options:{},
            model:{},
            display:function (opt) {
                let self = this;
                opt = $.extend(self.options, opt);
                let template = widget.part("htmls/project_detail.html");
                $service.findProject({id:opt.id}, function(data){
                    let model = $.extend({wid:_id}, data.results.result);
                    self.model = data.results.result;
                    let html = Mustache.render(template, model);
                    self.$el = $(opt.el);
                    self.$el.html(html);
                    self.handleEvent();
                });
            },
            handleEvent:function(){
                let self = this;
                $('#btnSubmit'+_id).off('click').on('click', function(e){
                    let $repositoryBeanPackage = $('#repositoryBeanPackage'+_id);
                    let repositoryBeanPackage = $repositoryBeanPackage.val();
                    if(!repositoryBeanPackage){
                        $repositoryBeanPackage.focus();
                        return false;
                    }
                    let $daoBeanPackage = $('#daoBeanPackage'+_id);
                    let daoBeanPackage = $daoBeanPackage.val();
                    if(!daoBeanPackage){
                        $daoBeanPackage.focus();
                        return false;
                    }

                    let $tableBeanPackage = $('#tableBeanPackage'+_id);
                    let tableBeanPackage = $tableBeanPackage.val();
                    if(!tableBeanPackage){
                        $tableBeanPackage.focus();
                        return false;
                    }

                    let $jdbcUrl = $('#jdbcUrl'+_id);
                    let jdbcUrl = $jdbcUrl.val();
                    if(!jdbcUrl){
                        $jdbcUrl.focus();
                        return false;
                    }

                    let $jdbcUsername = $('#jdbcUsername'+_id);
                    let jdbcUsername = $jdbcUsername.val();
                    if(!jdbcUsername){
                        $jdbcUsername.focus();
                        return false;
                    }

                    let $jdbcPassword = $('#jdbcPassword'+_id);
                    let jdbcPassword = $jdbcPassword.val();
                    if(!jdbcPassword){
                        $jdbcPassword.focus();
                        return false;
                    }

                    let $driverClassName = $('#driverClassName'+_id);
                    let driverClassName = $driverClassName.val();
                    if(!driverClassName){
                        $driverClassName.focus();
                        return false;
                    }

                    let $folder = $('#folder'+_id);
                    let folder = $folder.val();
                    if(!folder){
                        $folder.focus();
                        return false;
                    }

                    let param = $.extend({}, self.model, {
                        id:self.options.id,
                        repositoryBeanPackage:repositoryBeanPackage,
                        daoBeanPackage:daoBeanPackage,
                        tableBeanPackage:tableBeanPackage,
                        jdbcUrl:jdbcUrl,
                        jdbcUsername:jdbcUsername,
                        jdbcPassword:jdbcPassword,
                        driverClassName:driverClassName,
                        folder:folder,
                    });

                    $service.generateCode(param, function(data){
                        console.log(data);
                        let html = Mustache.render("{{#result}}<li><a href='{{.}}'>{{.}}</a>{{/result}}", data.results);
                        $.confirm({
                            columnClass:'xlarge',
                            title:'Result',
                            content:html,
                            buttons:{
                                close:{
                                    text:'@rocker.$.msg("text.close")'
                                }
                            }
                        });
                    });
                });
            }
        }
    };
}(window, document, jQuery));
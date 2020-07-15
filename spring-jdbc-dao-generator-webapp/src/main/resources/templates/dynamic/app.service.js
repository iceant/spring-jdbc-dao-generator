;(function (window, document, $) {

    $(document).ready(function () {
        $.ajaxSetup({
            headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')}
        });

        $(document).ajaxError(function (event, xhr, options, exc) {
            console.log(xhr);
            var dialogOptions = {};
            if (xhr.status === 403 || xhr.statusText === 'parsererror') {
                dialogOptions = {
                    closeIcon:true,
                    title: '@rocker.$.msg("text.error")',
                    content: '@rocker.$.msg("text.error_visit_address"):<a href="' + options.url + '">' + options.url + '</a><br/>' +
                        '@rocker.$.msg("text.error_status"):' + xhr.status + '<br/>' +
                        '@rocker.$.msg("text.error_message"):' + xhr.statusText,
                    buttons: {
                        refresh: {
                            text: '@rocker.$.msg("text.refresh")',
                            btnClass: 'btn btn-primary btn-sm',
                            action: function () {
                                window.location.reload();
                            }
                        }
                    }
                };
            } else {
                dialogOptions = {
                    closeIcon:true,
                    title: '@rocker.$.msg("text.error")',
                    content: '@rocker.$.msg("text.error_visit_address"):<a href="' + options.url + '">' + options.url + '</a><br/>' +
                        '@rocker.$.msg("text.error_status"):' + xhr.status + '<br/>' +
                        '@rocker.$.msg("text.error_message"):' + xhr.responseJSON.message,
                };
            }

            $.confirm(dialogOptions);
        });
    });

    var ServiceClient = {
        proxy: function (callback) {
            return callback.before(function (data) {
                if (!data.status) {
                    $.confirm({
                        closeIcon: true,
                        autoClose: false,
                        columnClass: 'large',
                        title: '@rocker.$.msg("text.error")',
                        content: data.message,
                        buttons: {
                            close: {
                                text: '@rocker.$.msg("text.close")',
                                btnClass: 'btn btn-primary',
                                action: function () {
                                }
                            }
                        }
                    });
                    return false;
                }
                return true;
            });
        },
        createProject: function (op, cb) {
            $.post('@rocker.$.path("/api/project/create")', op, this.proxy(cb));
        },
        listProjects:function(op, cb) {
            $.post('@rocker.$.path("/api/project/list")', op, this.proxy(cb));
        },
        findProject:function(op, cb){
            $.post('@rocker.$.path("/api/project/find")', op, this.proxy(cb));
        },
        generateCode:function(op, cb){
            $.post('@rocker.$.path("/api/project/generate")', op, this.proxy(cb));
        }
    };

    window.$service = ServiceClient;
}(window, document, jQuery));
;(function(window, document, $){

    var $app = function(){};

    $app.prototype.uid = function(len){
        len = len||9;
        return "_"+(Number(String(Math.random()).slice(2)) +
            Date.now() +
            Math.round(performance.now())).toString(36).substr(2, len);
    };

    function isObjFunc(name) {
        var toString = Object.prototype.toString;
        return function () {return toString.call(arguments[0]) === '[object ' + name + ']';}
    }

    $app.prototype.isFunction=isObjFunc('Function');
    $app.prototype.isString=isObjFunc('String');
    $app.prototype.isNumber=isObjFunc('Number');
    $app.prototype.isBoolean=isObjFunc('Boolean');
    $app.prototype.isObject=isObjFunc('Object');


    window.$app = new $app();

}(window, document, jQuery));
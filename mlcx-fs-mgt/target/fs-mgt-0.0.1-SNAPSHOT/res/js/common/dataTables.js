/**
 * Created by apple on 2016/1/18.
 */
(function(window, document, undefined){

    var factory = function( $, DataTable ) {
        "use strict";
        $.extend(true, DataTable.defaults, {
            "processing": true,
            "serverSide": true,
            "language": {
                //"url": "http://cdn.datatables.net/plug-ins/1.10.10/i18n/Chinese.json"
                //"url": "../../DataTables/Chinese.lang"
            	"url": "res/dep/DataTables/Chinese.json"
            },
            "ordering": false/*,
            "autoWidth": false*/
        });
    };

// Define as an AMD module if possible
    if ( typeof define === 'function' && define.amd ) {
        define( ['jquery', 'datatables'], factory );
    }
    else if ( typeof exports === 'object' ) {
        // Node/CommonJS
        factory( require('jquery'), require('datatables') );
    }
    else if ( jQuery ) {
        // Otherwise simply initialise as normal, stopping multiple evaluation
        factory( jQuery, jQuery.fn.dataTable );
    }

})(window, document);
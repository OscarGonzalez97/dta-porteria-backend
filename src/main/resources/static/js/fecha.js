/*$(document).ready(() =>{
    $('#example').DataTable({
    "language": {
        "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
    }});
});*/

$(document).ready(function() {
    $('#example').DataTable( {
        dom: 'Blfrtip',
        buttons: [
            'excel'
        ],
        columnDefs: [
                    {
                        target: 12, //para que no se pueda buscar detalles
                        searchable: false,
                    },
                    {//para ocultar la vista de columnas
                        target: 7,
                        searchable: false,
                        visible: false,
                    },
                    {
                        target: 8,
                        searchable: false,
                        visible: false,
                    },
                    {
                        target: 9,
                        searchable: false,
                        visible: false,
                    },
                    {
                        target: 10,
                        searchable: false,
                        visible: false,
                    },
                    {
                        target: 11,
                        searchable: false,
                        visible: false,
                    },
                ],
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        }

    });
    var minDate, maxDate;

    // Custom filtering function which will search data in column four between two values
    $.fn.dataTable.ext.search.push(
        function( settings, data, dataIndex ) {
            var min = minDate.val();
            var max = maxDate.val();
            var date = new Date( data[6] );

            if (
                ( min === null && max === null ) ||
                ( min === null && date <= max ) ||
                ( min <= date   && max === null ) ||
                ( min <= date   && date <= max )
            ) {
                return true;
            }
            return false;
        }
    );
    // Create date inputs
    minDate = new DateTime($('#min'), {
        format: 'MMMM Do YYYY'
    });
    maxDate = new DateTime($('#max'), {
        format: 'MMMM Do YYYY'
    });

    // DataTables initialisation
    var table = $('#example').DataTable();

    // Refilter the table
    $('#min, #max').on('change', function () {
        table.draw();
    });




});

var recordType = "vehicles";
var tableList;
var sudoNotify = $('.notification-container').sudoNotify();
overlayLoading();

$(document).ready(function() {
    recordLoad();
});

function recordLoad() {
    $('#' + recordType + '-list').DataTable().destroy();
    $('.recordAction').html('Load ')

    tableList = $('#' + recordType + '-list').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": getRequestMapping(recordType, 0),
            "type": getRequestMethod(),
            "dataType": "json",
            "contentType": "application/json",
            "data": function (d) {
                return JSON.stringify(d);
            }
        },
        "drawCallback": function (settings) {
            if (settings.jqXHR != null && settings.jqXHR.readyState == 4) {
                commonActions('RemoveOverlay', '', '');
            }
        },
        "columns": [
            {"data": "brand", "width": "30%"},
            {"data": "model", "width": "30%"},
            {"data": "plate", "width": "20%"},
            {"data": "license", "width": "10%"},
            {"data": "", "width": "10%"}
        ],
        "columnDefs": [
            /*{ className: "dt-head-center", targets: [ 3 ] },*/
            { className: "dt-body-center", targets: [ 3 ] },
            {
                "targets": -1,
                "orderable": false,
                "data": null,
                "defaultContent": renderDatatableActionButton()
            }
        ]
    });

    $('#' + recordType + '-list').css('display','table');

    $('#' + recordType + '-list tbody').on( 'click', 'button.dt-delete', function () {
        var data = tableList.row( $(this).parents('tr') ).data();
        $('.recordType').html(recordType);
        $('#recordName').html(data.branch + ' ' + data.model);
        $('#hdf_Id').val(data.id);
        commonActions('DisplayPanel', 'dv_DeleteConfirm');
    } );

    $('#' + recordType + '-list tbody').on( 'click', 'button.dt-edit', function () {
        var data = tableList.row( $(this).parents('tr') ).data();
        recordEdit(data);
    } );
}

function recordNew() {
    newRecordStyleConfiguration(recordType);
    $('#hdf_Id').val(0);
    $('#txt_Brand').val('');
    $('#txt_Model').val('');
    $('#txt_Plate').val('');
    $('#txt_License').val('');
    commonActions('DisplayPanel', 'dv_Form', 'txt_Branch');
};

function recordEdit(data) {
    editRecordStyleConfiguration(recordType);
    $('#hdf_Id').val(data.id);
    $("#txt_Brand").val(data.brand);
    $("#txt_Model").val(data.model);
    $("#txt_Plate").val(data.plate);
    $("#txt_License").val(data.license);
    commonActions('DisplayPanel', 'dv_Form', 'txt_Branch');
}

function recordDelete() {
    overlayLoading();
    $('.recordAction').html('Delete ');
    var id = $('#hdf_Id').val();
    deleteAjaxCall(id, 'deleted', recordType);
};

function saveAll() {
    overlayLoading();
    var data = {};
    data.id = $("#hdf_Id").val();
    data.brand = $("#txt_Brand").val();
    data.model = $("#txt_Model").val();
    data.plate = $("#txt_Plate").val();
    data.license = $("#txt_License").val();
    saveAjaxCall(data, 'saved', recordType);
};
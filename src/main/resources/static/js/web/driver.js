var recordType = "drivers";
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
            {"data": "name", "width": "40%"},
            {"data": "surName", "width": "40%"},
            {"data": "license", "width": "10%"},
            {"data": "", "width": "10%"}
        ],
        "columnDefs": [
            /*{ className: "dt-head-center", targets: [ 2 ] },*/
            { className: "dt-body-center", targets: [ 2 ] },
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
        $('#recordName').html(data.name + ' ' + data.surName);
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
    $('#txt_Name').val('');
    $('#txt_Surname').val('');
    $('#txt_License').val('');
    commonActions('DisplayPanel', 'dv_Form', 'txt_Name');
};

function recordEdit(data) {
    editRecordStyleConfiguration(recordType);
    $('#hdf_Id').val(data.id);
    $("#txt_Name").val(data.name);
    $("#txt_Surname").val(data.surName);
    $("#txt_License").val(data.license);
    commonActions('DisplayPanel', 'dv_Form', 'txt_Name');
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
    data.name = $("#txt_Name").val();
    data.surName = $("#txt_Surname").val();
    data.license = $("#txt_License").val();
    saveAjaxCall(data, 'saved', recordType);
};
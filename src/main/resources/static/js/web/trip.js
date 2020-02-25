var recordType = "trips";
var tableList;
var sudoNotify = $('.notification-container').sudoNotify();
var stepper1;
var date;
overlayLoading();

$(document).ready(function() {
    stepper1 = new Stepper(document.querySelector('#stepper1'));
    date = new Date();
    $("#txt_Date").datetimepicker({ locale: "es", format: "L", defaultDate: date });

    recordLoad();
    //loadVehicles();
    //loadDrivers();
});

function recordLoad() {
    $('#' + recordType + '-list').DataTable().destroy();
    $('.recordAction').html('Load ')

    tableList = $('#' + recordType + '-list').DataTable({
        "processing": true,
        "serverSide": true,
        "scrollX": true,
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
            {"data": "driverId", "width": "auto"},
            {"data": "driverName", "width": "auto"},
            {"data": "driverSurname", "width": "auto"},
            {"data": "driverLicense", "width": "auto"},
            {"data": "vehicleId", "width": "auto"},
            {"data": "vehicleBranch", "width": "auto"},
            {"data": "vehicleModel", "width": "auto"},
            {"data": "vehiclePlate", "width": "auto"},
            {"data": "vehicleLicense", "width": "auto"},
            {
                "data": "date",
                "width": "auto",
                "render": function (data, type, full, meta) {
                    var d = full["date"];
                    d = d.substring(8, 10) + "-" + d.substring(5, 7) + "-" + d.substring(0, 4);
                    return "<div class='date'>" + d + "<div>";
                }
            },
        ],
        "columnDefs": [
            /*{ className: "dt-head-center", targets: [ 2 ] },*/
            { className: "dt-body-center", targets: [ 2 ] },
            {
                "targets": -1,
                "orderable": false,
                "data": null
            }
        ]
    });

    $('#' + recordType + '-list').css('display','table');
}

function loadVehicles() {

    var data = {};
    data.date = formatDate($("#txt_Date").val());

    $.ajax({
        type: 'POST',
        url: '/vehicles2/checkAvailabilityByDate',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        cache: false,
        success: function (response) {
            if (response.length > 0) {
                $("#slc_VehicleId").empty();
                $('#slc_VehicleId').append('<option value="0">- select option -</option>');
                $.each(response, function (i, item) {
                    $('#slc_VehicleId').append('<option value="' + item.id + '" data-prefix-branch="' + item.branch + '" data-prefix-model="' + item.model + '" data-prefix-plate="' + item.plate + '" data-prefix-license="' + item.license + '">' + item.branch + ' ' + item.model + ' -> ' + item.plate + ' (License Required: ' + item.license + ')</option>');
                });
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr);
        }
    });
}

function loadDrivers() {
    var data = {};
    data.driverName = "Stingo";
    data.vehicleBranch = "Toyota";
    data.tripDate = "20200224";

    $.ajax({
        type: 'POST',
        url: '/drivers2/checkAvailabilityByDateAndLicense',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        cache: false,
        success: function (response) {
            if (response.length > 0) {
                $('#slc_DriverId').append('<option value="0">- select option -</option>');
                $.each(response, function (i, item) {
                    $('#slc_DriverId').append('<option value="' + item.id + '" data-prefix-name="' + item.name + '" data-prefix-surName="' + item.surName + '" data-prefix-license="' + item.license + '">' + item.name + ' ' + item.surName + ' (License: ' + item.license + ')</option>');
                });
            }
        },
        error: function (xhr, status, error) {
            console.log(xhr);
        }
    });
}

function recordNew() {
    newRecordStyleConfiguration(recordType);
    $('#hdf_Id').val(0);
    //$('#txt_Name').val('');
    commonActions('DisplayPanel', 'dv_Form', 'txt_Name');
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

function formatDate(date) {
    var d = moment(date, "DD/MM/YYYY").format("YYYY-MM-DD");
    return d;
}
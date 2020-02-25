var modal;

function commonActions(action, value, focus) {
    if (action == "") { return false; }
    switch (action) {
        case "DisplayPanel":
            displayPanel(value);
            break;
        case "RemoveOverlay":
            $('#overlay').addClass('hidden')
            break;
    }
    if (focus != "") {
        $("#" + focus).focus();
    }
};

function displayPanel(panel) {
    modal = "";
    modal = $.remodal.lookup[$('[data-remodal-id=' + panel + ']').data('remodal')];
    modal.open();
};

function overlayLoading() {
    $('#overlay').removeClass('hidden');
};

function getEditButton(){
    return "<button type=\"button\" class=\"btn background-color-blue dt-edit\" data-toggle=\"tooltip\" title=\"Edit\">\n" +
        "<span class=\"color-white\">\n" +
        "<i class=\"fas fa-edit\"></i>\n" +
        "</span></button>";
};

function getButtonSpace() {
    return "<span style=\"margin-right:6px;\"></span>";
}

function getDeleteButton(){
    return "<button type=\"button\" class=\"btn background-color-red dt-delete\" data-toggle=\"tooltip\" title=\"Delete\">\n" +
        "<span class=\"color-white\">\n" +
        "<i class=\"fas fa-trash-alt\"></i>\n" +
        "</span></button>";
};

function renderDatatableActionButton(){
    return getEditButton() + getButtonSpace() + getDeleteButton();
};

function resetForm(formNumber) {
    var validator = $("#form" + formNumber).validate();
    validator.resetForm();
};

function enableForm(formId) {
    $("form#" + formId + " .form-control").prop("disabled", false);
    $(".btn-save").prop("disabled", false);
};

function validateForm(formId) {
    var result = $("#form" + formId).valid();
    return result;
};

function getRequestMethod() {
    switch ($('.recordAction').html()) {
        case "Edit ":
            return "PUT";
            break;
        case "Delete ":
            return "DELETE";
            break;
        default:
            return "POST";
            break;
    }
};

function getRequestMapping(type, id) {
    switch ($('.recordAction').html()) {
        case "Add ":
            return "/" + type + "2/add";
            break;
        case "Edit ":
        case "Delete ":
            return "/" + type + "2/" + id;
            break;
        default:
            return "/" + type + "2";
            break;
    }
};

function newRecordStyleConfiguration(type) {
    enableForm("form1");
    resetForm(1);
    $('.recordType').html(type);
    $('.recordAction').html('Add ');
    $('.panel').removeClass( 'panel-primary').addClass('panel-success');
    $('.btn-save').removeClass( 'btn-primary').addClass('btn-success');
};

function editRecordStyleConfiguration(type) {
    resetForm(1);
    enableForm("form1");
    $('.recordType').html(type);
    $('.recordAction').html('Edit ');
    $('.panel').removeClass( 'panel-success').addClass('panel-primary');
    $('.btn-save').removeClass( 'btn-success').addClass('btn-primary');
    $('.recordClass').html('primary');
};

function deleteAjaxCall(id, action, type) {
    $.ajax({
        url: getRequestMapping(type, id),
        type: getRequestMethod(),
        success: function () {
            successResponseAction(action);
        },
        error: function (xhr, status, error) {
            errorResponseAction(xhr, error);
        }
    });
};

function saveAjaxCall(data, action, type) {
    $.ajax({
        url: getRequestMapping(type, data.id),
        type: getRequestMethod(),
        contentType: 'application/json',
        cache: false,
        data: JSON.stringify(data),
        success: function () {
            successResponseAction(action);
        },
        error: function (xhr, status, error) {
            errorResponseAction(xhr, error);
        }
    });
};

function successResponseAction(action) {
    modal.close();
    getMessage(1, '', action);
    recordLoad();
};

function errorResponseAction(xhr, error) {
    modal.close();
    console.log(xhr);
    getMessage(-1, formatErrorMessage(xhr, error), '');
    commonActions('RemoveOverlay', '', '');
};

function formatErrorMessage(xhr, error) {
    if (xhr.status === 0) {
        return ('Not connected. Please verify your network connection.');
    } else if (xhr.status == 404) {
        return ('The requested page not found [404].');
    } else if (xhr.status == 500) {
        return ('Internal Server Error [500].');
    } else if (error === 'parsererror') {
        return ('Requested JSON parse failed.');
    } else if (error === 'timeout') {
        return ('Time out error.');
    } else if (error === 'abort') {
        return ('Ajax request aborted.');
    } else {
        return ('Uncaught Error.');
    }
};
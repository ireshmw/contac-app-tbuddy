/**
 * Created by iresh on 1/3/2017.
 */

var csrfToken = $('#_csrf').attr("content");
var csrfHeader = $('#_csrf_header').attr("content");

$("#table-body").on('click', '.delete-record', function (event) {
    event.preventDefault();
    var recId = $(this).parent().siblings(".rec-id").html();
    var rec = $(this).parent().siblings(".rec-id");

    console.log(recId);
    console.log("click the button...");
    $.ajax({
        method: 'POST',
        url: '/delete',
        data: JSON.stringify({id: recId}),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            if (data["state"] == 1) {
                console.log("inside the if statement...");
                console.log(data["state"]);
                rec.parent().remove();

            }
        },
        error: function (error) {
            console.log(error);
        }
    });
    event.preventDefault();
});
/**
 * Created by iresh on 12/17/2016.
 */



$(document).ready(function () {


    $("#table-body .full-info").click(function () {

        $("body").append("<div id='overlay'> </div>");
        $("#overlay").append("<div id='data-table'> </div>");

        var recordId = $(this).parent().siblings(".rec-id").html();
        console.log("you click the full info button...");
        console.log(recordId);


        $(function () {
            var token = $("input[name='_csrf']").val();
            var header = "X-CSRF-TOKEN";
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });


        $("#data-table").on('click', '#info-closing-button', function () {

            $("#overlay").remove();
            console.log("close button clicked..")

        });


        $.ajax({
            method: 'POST',
            url: '/findContactByID',
            data: JSON.stringify({"id": recordId}),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data, textStats, jqXHR) {

                console.log("inside the success ...")
                console.log(data);


                var phones = "";
                var mails = "";

                for (var j = 0; j < data["contact"]["phoneNumbers"]["length"]; j++) {
                    phones = phones + data["contact"]["phoneNumbers"][j]["phoneNumber"] + "</br>";
                }

                for (var i = 0; i < data["contact"]["mails"]["length"]; i++) {
                    mails = mails + data["contact"]["mails"][i]["email"] + "</br>";
                }


                $("#data-table").append('<span class="input-group-btn" id="full-info-closing-button">\
                <button type="button" class="btn btn-default btn-sm" id="info-closing-button" >\
                    <span class="glyphicon glyphicon-remove-circle"></span>\
                    </button>\
                    </span>\
                <table id="full-info-table">\
                    <tr>\
                    <td>First Name</td>\
                <td>' + data["contact"]["fName"] + '</td>\
                </tr>\
                <tr>\
                <td>Last Name</td>\
                <td>' + data["contact"]["lName"] + '</td>\
                </tr>\
                <tr>\
                <td>Phone Number</td>\
                <td>' + phones + '</td>\
                </tr>\
                <tr>\
                <td>Emails</td>\
                <td>' + mails + '</td>\
                </tr>\
                <tr>\
                <td>Address</td>\
                <td>' + data["contact"]["address"] + '</td>\
                </tr>\
                <tr>\
                <td>Comment</td>\
                <td>' + data["contact"]["comment"] + '</td>\
                </tr>\
                </table>'
                );


            },


            error: function (error) {
                console.log(error);
            }
        });
        event.preventDefault();


    });


});
/**
 * Created by iresh on 11/30/2016.
 */


$(document).ready(function (event) {
    console.log("ready!");

    // var csrfToken = $('#_csrf').attr("content");
    // var csrfHeader = $('#_csrf_header').attr("content");

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $("#form-submit").click(function (event) {
        $("#success_text").empty();
        $("#fName-error").empty();
        $("#lName-error").empty();
        $("#phoneNumbers-error").empty();
        $("#mails-error").empty();
        var fdata = $("#contact-form").serialize();
        // console.log(JSON.stringify (fdata));
        // console.log("header ..."+csrfHeader);


        $(function (event) {
            var token = $("input[name='_csrf']").val();
            var header = "X-CSRF-TOKEN";
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });

        $.ajax({
            method: 'POST',
            url: '/form',

            // beforeSend: function(xhr){
            //     xhr.setRequestHeader(csrfHeader, csrfToken);},

            data: fdata,
            // beforeSend: function(xhr) {
            //     // here it is
            //     xhr.setRequestHeader(header, token);
            // },

            //dataType    : 'json',
            //contentType: 'application/json',
            success: function (data, textStats, jqXHR) {
                if (data["state"] == 0) {
                    $("#success_text").empty();
                    $.each(data["errorObjects"], function (key, value) {
                        //console.log(value["errorField"] + "--->" + value["errorMessage"]);
                        var id = "#" + value["errorField"] + "-error";
                        var message = value["errorMessage"];
                        console.log(id);
                        console.log(message);

                        $("#success_text").empty();
                        $(id).html(message);

                    });

                }
                else if (data["state"] == 1) {
                    console.log(data);
                    $("#success_text").empty();
                    $("#fName-error").empty();
                    $("#lName-error").empty();
                    $("#phoneNumbers-error").empty();
                    $("#mails-error").empty();

                    $("#success-text").html("Contact has successfully added.");
                    var contactObject = data["contact"];
                    var conId = contactObject["id"];;;;;;;;;;;;;;;;;;;;
                    var conName = contactObject["fName"];
                    var conPhone = contactObject["phoneNumbers"][0]["phoneNumber"];

                    console.log(conId);
                    console.log(conName);
                    console.log(conPhone);

                    //$('<tr"> <th scope="row" class="rec-id">' +conId +'</th> <td >' +conName +'</td> <td >' +conPhone +'</td> <td> <button name="delete-record" class="delete-record">Delete</button> </td></tr>').appendTo( "#tbody" );
                    //
                    $('<tr> <td class="col-xs-2 rec-id">' + conId + '</td> <td class="col-xs-3" >' + conName + '</td> <td class="col-xs-3">' + conPhone + '</td> <td class="col-xs-4"  > <button type="button" class="btn btn-default btn-sm full-info"> <span class="glyphicon glyphicon-briefcase"></span> </button> <button type="button" class="btn btn-default btn-sm"> <span class="glyphicon glyphicon-pencil"></span> </button> <button type="button" class="btn btn-default btn-sm delete-record" name="delete-record" > <span class="glyphicon glyphicon-trash"></span> </button> </td> </tr>').appendTo("#table-body");


                }

            },


            error: function (error) {
                console.log(error);
            }
        });
        event.preventDefault();
    });

    var maxPhoneInput = 4;
    var i = 1;

    $("#phone-numb").on('click', '#extra-phn-numb', function (event) {

        if (i < maxPhoneInput) {
            i++;
            //alert("you click the extra phone ");
            $("#phone-numbers").append('<div class="input-group">\
        <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>\
        <input name="phone" placeholder="0777070707" class="form-control" type="text">\
        <span class="input-group-btn remove-extra-phone">\
        <button type="button" class="btn btn-default btn-sm " >\
        <span class="glyphicon glyphicon-minus"></span>\
        </button>\
        </div>');

        }


    });

    $("#phone-numb").on("click",".remove-extra-phone", function(e){ //user click on remove text
        //console.log("removed clicked..");
        e.preventDefault();
        $(this).parent('div').remove();
        i--;

    });


    var maxMailInput = 4;
    var j = 1;
    $("#email").on('click', '#extra-email', function (event) {

        if (j < maxMailInput) {
            j++;
            //alert("you click the extra phone ");
            $("#emails").append('<div class="input-group">\
            <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>\
            <input name="email" placeholder="E-Mail Address" class="form-control"  type="text">\
            <span class="input-group-btn remove-extra-email">\
            <button type="button" class="btn btn-default btn-sm" >\
            <span class="glyphicon glyphicon-minus"></span>\
            </button>\
        </div>');
        }

    });


    $("#email").on("click",".remove-extra-email", function(e){ //user click on remove text
        //console.log("removed clicked..");
        e.preventDefault();
        $(this).parent('div').remove();
        j--;

    });
});
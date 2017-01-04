$(document).ready(function (event) {

    var dialog = $("#dialog");
    dialog.dialog({
        autoOpen: false,
        close: function () {
            $("#contact-form input").val(null);
            $(".text-warning").html(null);
            $(this).dialog('close');

        },
        title: "Add a New Contact",
        //minWidth:'auto',
        //  width: 600,
        //  autoOpen: true,
        //  modal: true,
        //  responsive: true,
        width: 'auto', // overcomes width:'auto' and maxWidth bug
        maxWidth: 'auto',
        height: 'auto',
        modal: true,
        fluid: true, //new option
        resizable: true,
        classes: {
            "ui-dialog-titlebar": "custom-red",
            "ui-dialog-content": "component-custom"
        }

    });


    $("#table-body").on('click', '.edit-record', function (event) {
        //event.preventDefault();
        //--------------------------------------------------------on clicking edit icon -> open the dialog window
        dialog.dialog('open');
        //--------------------- end of the dialog open.

        //-------------------------------------------- send a CSRF token with ajax request
        $(function (event) {
            var token = $("input[name='_csrf']").val();
            var header = "X-CSRF-TOKEN";
            $(document).ajaxSend(function (e, xhr) {
                xhr.setRequestHeader(header, token);
            });
        });
        //---------------------end of the token send function.

        //-------------------------------------------------------send a ajax request for get corresponding data of the row.

        var recordId = $(this).parent().siblings(".rec-id").html();//getting corresponding row id of the edit icon
        $("#edit-record").val(true);
        $("#edit-record-id").val(recordId);

        console.log("you click the edit button...");
        console.log(recordId);

        $.ajax({
            method: 'POST',
            url: '/findContactByID',
            data: JSON.stringify({"id": recordId}),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data, jqXHR) {
                console.log(data);
                $("#first_name").val(data['contact']['fName']);
                $("#last_name").val(data['contact']['lName']);

                $(".extra-phone").remove();

                var phoneNumbCount = data['contact']['phoneNumbers']['length'];
                if (phoneNumbCount<2){
                   $("#phone-numb input").val(data['contact']['phoneNumbers']['0']['phoneNumber']);
                }
                else {
                    $("#phone-numb input").val(data['contact']['phoneNumbers']['0']['phoneNumber']);
                    for (var i = 1; i < phoneNumbCount; i++) {
                        $("#phone-numbers").append('<div class="input-group extra-phone">\
                            <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>\
                            <input name="phone" placeholder="0777070707" value="'+ (data['contact']['phoneNumbers'][i]['phoneNumber'])+' " class="form-control" type="text">\
                            <span class="input-group-btn remove-extra-phone">\
                            <button type="button" class="btn btn-default btn-sm " >\
                            <span class="glyphicon glyphicon-minus"></span>\
                            </button>\
                            </div>');
                    }
                }

                $(".extra-mail").remove();

                var emailCount = data['contact']['mails']['length'];
                if (emailCount<2){
                    $("#email input").val(data['contact']['mails']['0']['email']);
                }
                else {
                    $("#email input").val(data['contact']['mails']['0']['email']);
                    for (var i = 1; i < phoneNumbCount; i++) {
                        $("#emails").append('<div class="input-group extra-mail">\
                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>\
                        <input name="email" placeholder="E-Mail Address" value="'+ (data['contact']['mails'][i]['email'])+' " class="form-control"  type="text">\
                        <span class="input-group-btn remove-extra-email">\
                        <button type="button" class="btn btn-default btn-sm" >\
                        <span class="glyphicon glyphicon-minus"></span>\
                        </button>\
                        </div>');
                    }
                }

                $("#address").val(data['contact']['address']);
                $("#comment").val(data['contact']['comment']);
            },

            error: function (error) {
                console.log(error);

            }
        });

        //------------------------------------------------------- end of the ajax request.
    });


});
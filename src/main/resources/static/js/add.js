


$( document ).ready(function() {
    console.log( "ready!" );

    var csrfToken = $('#_csrf').attr("content");
    var csrfHeader = $('#_csrf_header').attr("content");


    // $.ajaxSetup({ headers: { 'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content') } });

    // $.ajaxSetup({
    //     data: {csrfmiddlewaretoken: '{{ csrf_token }}' },
    // });

    var dialog = $("#dialog");

    dialog.dialog({
        autoOpen: false,


    });

    $("#add").click(function() {
        dialog.dialog('open');
        dialog.dialog({
            title: "Add a New Contact",
           //minWidth:'auto',
           //  width: 600,
           //  autoOpen: true,
           //  modal: true,
           //  responsive: true,

            width: 'auto', // overcomes width:'auto' and maxWidth bug
            maxWidth: 300,
            height: 'auto',
            modal: true,
            fluid: true, //new option
            resizable: false,

            // position: { my: "left bottom", at: "right"},

            classes: {
                "ui-dialog-titlebar": "custom-red",
                "ui-dialog-content":"component-custom"
            }
        });

        dialog.dialog({
            closeOnEscape: false
        });

    });
    $("#table-body").on('click', '.delete-record', function () {
        //event.preventDefault();
        var recId = $(this).parent().siblings(".rec-id").html();
        var rec = $(this).parent().siblings(".rec-id");

        console.log(recId);
        console.log("click the button...")
        $.ajax({
            method:'POST',
            url:'/delete',
            data: JSON.stringify ({id:recId}),
            beforeSend: function(xhr){
                xhr.setRequestHeader(csrfHeader, csrfToken);},
            dataType : 'json',
            contentType:'application/json',
            success:function(data){
                if (data["state"] == 1){
                    console.log("inside the if statement...");
                    console.log(data["state"]);
                    rec.parent().remove();

                }
            },
            error:function(error){
                console.log(error);
            }
        });
        //event.preventDefault();
    });




});




$( document ).ready(function() {
    console.log( "ready!" );

    // $.ajaxSetup({ headers: { 'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content') } });

    // $.ajaxSetup({
    //     data: {csrfmiddlewaretoken: '{{ csrf_token }}' },
    // });

    var dialog = $("#dialog");

    dialog.dialog({
        autoOpen: false,
        title: "Add a New Contact",
        close: function () {
            $("#contact-form input").val(null);
            $(".text-warning").html(null);
            $(this).dialog('close');

        },
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

        // position: { my: "left bottom", at: "right"},

        classes: {
            "ui-dialog-titlebar": "custom-red",
            "ui-dialog-content": "component-custom"
        }


    });

    $("#add").click(function (event) {
        dialog.dialog('open');
        // dialog.dialog({
        //     closeOnEscape: false
        // });

    });

});

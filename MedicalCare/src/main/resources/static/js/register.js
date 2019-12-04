$(document).on('click','#registerButton', function(e){
    e.preventDefault();
    $.post({
        url: "register",
        data: JSON.stringify({
            "email" : $("#email").val(),
            "lozinka" : $("#password1").val(),
            "ime" : $("#ime").val(),
            "prezime" : $("#prezime").val(),
            "adresaPrebivalista" : $("#adresa").val(),
            "grad" : $("#grad").val(),
            "drzava" : $("#drzava").val(),
            "telefon" : $("#telefon").val(),
            "brojOsiguranja" : $("#brosiguranika").val()
        }),
        contentType: 'application/json',
        success: function(data){
            window.location.href = "/index.html";
        },
        error: function(data){
            alert('something went wrong');
        }
    });
});
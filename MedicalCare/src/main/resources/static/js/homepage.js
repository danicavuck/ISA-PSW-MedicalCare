$(document).on('click','#loginButton', function(event){
    event.preventDefault();
    email = $('#loginEmail').val();
    lozinka = $('#loginPassword').val();    
    $('#error').hide();
    $.post({
        url: '/login',
        type: 'POST',
        data: JSON.stringify({email: email, lozinka: lozinka}),
        contentType: 'application/json',
        success: function() {
            window.location.href = "/genericHomepage.html";
        }
    });
});


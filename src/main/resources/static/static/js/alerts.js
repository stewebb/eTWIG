function showAlert(message) {
      var newAlert = $('<div class="alert alert-warning alert-dismissible fade show" role="alert">' + message +
        '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
        '<span aria-hidden="true">&times;</span>' +
        '</button></div>').appendTo('#alertContainer');

      newAlert.slideDown(500).delay(10000).slideUp(500, function() {
        $(this).remove(); // Remove the alert from DOM after hiding
      });
    }
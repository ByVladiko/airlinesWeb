$(document).ready(function () {
    $('#modalDelWindow').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let recipient = button.data('action');
        let modal = $(this);
        modal.find($('#delRef')).attr("href", recipient);
    });
});

function getById(id) {
    $.ajax({
        type: 'GET',
        url: window.location.href + '/getById/' + id,
        success: function (object) {
            for (let key in object) {
                let field = $('#modalEditWindow ' + '#' + key + '-edit');
                if (!field.length) break;
                if (field[0].type === 'select-one') {
                    let options = field[0].options;
                    for (let i = 0; i < options.length; i++) {
                        if (options[i].value === object[key].id || options[i].text === object[key]) {
                            options[i].selected = true;
                            break;
                        }
                    }
                } else {
                    field.val(object[key]);
                }
            }
            $('#modalEditWindow').modal();
        }
    })
}
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

function getTicketsByClient(id) {
    $.ajax({
        type: 'GET',
        url: document.location.origin + '/tickets/getByClient/' + id,
        success: function (list) {
            let tbody = document.querySelector('table tbody').innerHTML;
            let tickets = Object.values(list);
            console.log(list);
            for (let ticket in tickets) {
                console.log(ticket);
                // let row =
                //     `<tr>
                //     <td>${ticket.flight.route}</td>
                //     <td>${ticket.flight.dateOfDeparture}</td>
                //     <td>${ticket.flight.dateOfArrival}</td>
                //     <td>${ticket.category}</td>
                //     <td>${ticket.cost}</td>
                //     <td>${ticket.baggage}</td>
                //     <td>${ticket.status}</td>
                //     </tr>`;
                // tbody.append(row);
            }
            $('#modalTicketsOfClient').modal();
        }
    })
}
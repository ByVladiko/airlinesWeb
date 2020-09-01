$(document).ready(function () {
    $('#modalDelWindow').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let recipient = button.data('action');
        let modal = $(this);
        modal.find($('#delRef')).attr("href", recipient);
    });

    $('#modalTicketsOfClient').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let recipient = button.data('action');
        console.log(recipient);
        let modal = $(this);
        modal.find($('#buyTicket')).attr("href", recipient);
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
            let table = document.getElementById("tableTickets");
            let tbody = table.getElementsByTagName('tbody')[0];

            if (tbody.rows.length > 0) {
                for (let i = 0; i < tbody.rows.length;) {
                    tbody.rows[i].remove();
                }
            }

            let tickets = Object.values(list);
            for (let i = 0; i < tickets.length; i++) {
                let row = tbody.insertRow(i);
                let flight = tickets[i].flight;
                row.insertCell(row.cells.length).innerHTML = i + 1;
                row.insertCell(row.cells.length).innerHTML = flight.route.startPoint + ' -> ' + flight.route.endPoint;
                row.insertCell(row.cells.length).innerHTML = flight.dateOfDeparture;
                row.insertCell(row.cells.length).innerHTML = flight.dateOfArrival;
                row.insertCell(row.cells.length).innerHTML = tickets[i].category;
                row.insertCell(row.cells.length).innerHTML = tickets[i].cost;
                row.insertCell(row.cells.length).innerHTML = tickets[i].baggage;
                row.insertCell(row.cells.length).innerHTML = tickets[i].status;
            }
            $('#modalTicketsOfClient').modal();
        }
    })
}
$(document).ready(function() {
    var n = ["Tight Spaces",
        "Sharp Inclines",
        "Construction Nearby",
        "Low-Quality Sidewalk",
        "Overgrown Foilage",
        "Lack of Elevator",
        "Broken Benches",
        "Insufficient Light"];
    var c = [11, 42, 91, 1, 0, 77, 14, 0];

    for (var i = 0; i < c.length; i++) {
        if (c[i] > 0) {
            $('.tags').append('<button type="button" class="btn-' + i + '">' +
                format(n[i], c[i]) + '</button>');
        }
    }

    $.ajax({
        url: "http://rest-service.guides.spring.io/greeting"
    }).then(function(data) {
        $('.greeting-id').append(data.id);
        $('.greeting-content').append(data.content);
    });

    $("button").click(function() {
        var color = $(this).css('background-color');
        var text  = $(this).text();
        var i     = $(this).attr('class').charAt(4);
        if (color == 'rgb(116, 187, 56)') {
            $(this).css('background-color', '#487623');
            $(this).text(format(n[i], c[i] + 1));
        } else {
            $(this).css('background-color', '#74BB38');
            $(this).text(format(n[i], c[i]));
        }
    });

    function format(name, count) {
        return name + ' (' + count + ')';
    }
});


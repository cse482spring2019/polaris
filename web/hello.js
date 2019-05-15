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
    var c2 = new Array(c.length).fill(0);
    var editing = false;

    addButtons($('.buttons'));
    for (var i = 0; i < n.length; i++) {
        var btn = $('#btn-' + i);
        btn.html(format(n[i], c[i]));
        if (c[i] == 0) {
            btn.hide();
        }
    }

    $.ajax({
        url: "http://rest-service.guides.spring.io/greeting"
    }).then(function(data) {
        $('.greeting-id').append(data.id);
        $('.greeting-content').append(data.content);
    });

    $(".tag").click(function() {
        if (editing) {
            var color = $(this).css('background-color');
            var text  = $(this).text();
            var i     = $(this).attr('id').charAt(4);
            if (color == 'rgb(116, 187, 56)') {
                $(this).css('background-color', '#487623');
                $(this).html(format(n[i], c[i] + 1));
                c2[i] = 1;
            } else {
                $(this).css('background-color', '#74BB38');
                $(this).html(format(n[i], c[i]));
                c2[i] = 0;
            }
        }
    });

    $('#edit').click(function() {
        if (!editing) {
            $('#save').disabled = true;
            editing = true;
        }
    });

    $('#save').click(function() {
        if (editing) {
            console.log($('.tag')[0]);
            editing = false;
        }
    });

    /*
     * Returns a String in the format "name (count)"
     */
    function format(name, count) {
        return name + ' (' + count + ')';
    }

    /*
     * Adds a button to the given container for each of the defined tags
     */
    function addButtons(container) {
        for (var i = n.length - 1; i >= 0; i--) {
            container.prepend('<button type="button" id="btn-' + i + '" class="tag"></button>');
        }
    }
});


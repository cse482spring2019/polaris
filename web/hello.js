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
    var images = ['a.jpg', 'b.jpg', 'c.jpg', 'd.jpg']
    var editing = false;

    /* Puts the initial buttons on the page */
    for (var i = 0; i < n.length; i++) {
        $('.buttons').prepend(
            '<button type="button" id="btn-' + i + '" class="tag" disabled></button>'
        );

        var btn = $('#btn-' + i);
        btn.html(format(n[i], c[i]));
        if (c[i] == 0) {
            btn.hide();
        }
    }

    for (var i = 0; i < images.length; i++) {
        $('.images').append(
            '<img src="' + images[i] + '" alt="bus stop">'
        );
    }

    // TODO: should actually get the data from the service
    $.ajax({
        url: "http://rest-service.guides.spring.io/greeting"
    }).then(function(data) {
        $('.greeting-id').append(data.id);
        $('.greeting-content').append(data.content);
    });

    /* Behavior for clicking on a tag */
    $(".tag").click(function() {
        if (editing) {
            var i     = $(this).attr('id').charAt(4);
            if ($(this).css('background-color') == 'rgb(116, 187, 56)') {
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

    /* Goes into edit mode when the 'edit' button is clicked */
    $('#edit').click(function() {
        $('#save').attr('disabled', false);
        $('#edit').attr('disabled', true);
        editing = true;

        for (var i = 0; i < n.length; i++) {
            var btn = $('#btn-' + i);
            btn.attr('disabled', false);
            btn.show();
            if (c2[i] == 1) {
                btn.css('background-color', '#487623');
            }
        }
    });

    /* Save by updating count and color in the tag */
    $('#save').click(function() {
        $('#save').attr('disabled', true);
        $('#edit').attr('disabled', false);
        editing = false;

        for (var i = 0; i < n.length; i++) {
            var btn = $('#btn-' + i);
            btn.attr('disabled', true);
            if (c[i] + c2[i] == 0) {
                btn.hide();
            }
            btn.html(format(n[i], c[i] + c2[i]))
            btn.css('background-color', '#74BB38');
        }

        //TODO; send the data to the service
    });

    var files;

    $('input[type="file"]').change(function(e) {
        files = e.target.files;
    });

    $('#submit').bind('click', function() {
        console.log(files);
        $('input[type="file"]').val('');
    });

    /*
     * Returns a String in the format "name (count)"
     */
    function format(name, count) {
        return name + ' (' + count + ')';
    }
});

$(document).ready(function() {
    var data = [{name: "Tight Spaces", count: 11},
                {name: "Sharp Inclines", count: 42},
                {name: "Construction Nearby", count: 91},
                {name: "Low-Quality Sidewalk", count: 1},
                {name: "Overgrown Foilage", count: 0},
                {name: "Lack of Elevator", count: 77},
                {name: "Broken Benches", count: 14},
                {name: "Insufficient Light", count: 0}];
    var added = new Array(data.length).fill(0);

    data.sort(function(a, b) { return b.count - a.count; });

    var images = ['a.jpg', 'b.jpg']

    /* Puts the initial buttons on the page */
    for (var i = 0; i < data.length; i++) {
        $('.tags').append(
            '<button type="button" id="btn-' + i + '" class="' + getClass(i) + '"></button>'
        );
        $('#btn-' + i).html(format(data[i].name, data[i].count));
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
        var i = $(this).attr('id').charAt(4);
        if (!$(this).attr('class').includes('tag-selected')) {
            added[i] = 1;
            $(this).attr('class', 'tag tag-selected');
            $(this).html(format(data[i].name, data[i].count + 1));
        } else {
            added[i] = 0;
            $(this).attr('class', getClass(i));
            $(this).html(format(data[i].name, data[i].count));
        }
    });

    /* Save by updating count and color in the tag */
    $('button.save').click(function() {
        $('p.save').text('changes saved');
        for (var i = 0; i < data.length; i++) {
            //TODO; send the data to the service
        }
    });

    var files;
    $('input[type="file"]').change(function(e) {
        files = e.target.files;

        for (var i = 0; i < files.length; i++) {
            images.push(files[i].name);
        }
        $('output').text('images uploaded!');

        $('.images').empty();
        for (var i = 0; i < images.length; i++) {
            $('.images').append(
                '<img src="' + images[i] + '" alt="bus stop">'
            );
        }
    });

    /*
     * Returns a String in the format "name (count)"
     */
    function format(name, count) {
        return name + ' (' + count + ')';
    }

    function getClass(i) {
        return (data[i].count + added[i]) == 0 ? 'tag' : 'tag tag-default';
    }
});

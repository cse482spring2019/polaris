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
        $('.tag-container').append(getTag(i));
    }

    for (var i = 0; i < images.length; i++) {
        $('.image-container').append(getCard(images[i]));
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
        $('p.save').text('saving...');

        for (var i = 0; i < data.length; i++) {
            //TODO; send the data to the service
        }
    });

    $('.card').click(function() {
        $('#stopModal').show();
        $('#modalImage').attr('src', $(this).find('img').attr('src'));
    });

    $('#close').click(function() {
        $('#stopModal').hide();
    });

    $(document).keyup(function(e) {
        if (e.keyCode == 27) {
            $('#stopModal').hide();
        }
    });

    $('input[type="file"]').change(function(e) {
        var files = e.target.files;
        var oldSize = images.length;

        for (var i = 0; i < files.length; i++) {
            images.push(files[i].name);
        }
        $('output').text('images uploaded!');

        for (var i = oldSize; i < images.length; i++) {
            $('.image-container').append(getCard(images[i]));
        }

        // do it again since we added some cards
        $('.card').click(function() {
            $('#stopModal').show();
            $('#modalImage').attr('src', $(this).find('img').attr('src'));
        });
    });

    /*
     * Returns a String in the format "name (count)"
     */
    function format(name, count) {
        return name + ' (' + count + ')';
    }

    /*
     * Returns the class of the tag with the given index based on counts
     */
    function getClass(i) {
        return (data[i].count + added[i]) == 0 ? 'tag' : 'tag tag-default';
    }

    /*
     * Returns the query parameter with the given name or false if there
     * is no such query parameter included
     */
    function getQueryParam(name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)')
            .exec(window.location.search);
        return (results !== null) ? results[1] || 0 : false;
    }

    function getTag(i) {
        return (
            '<button type="button" id="btn-' + i + '" class="' + getClass(i) + '">' +
                format(data[i].name, data[i].count) +
            '</button>'
        );
    }

    function getCard(image) {
        return (
            '<div class="card">' +
                '<img src="' + image + '" alt="bus stop">' +
                '<div>date uploaded: 5/18/2019</div>' +
            '</div>'
        );
    }
});

var ESCAPE = 27; /* keycode of the escape button */

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
    var files = [];

    /* Puts the initial tags on the page */
    for (var i = 0; i < data.length; i++) {
        $('.tag-container').append(getTag(i));
    }

    /* Puts the initial images on the page */
    if (images.length == 0) {
        $('.image-container').append(
            '<div class="empty">no images to show for this stop</div>'
        );
    } else {
        for (var i = 0; i < images.length; i++) {
            $('.image-container').append(getCard(images[i]));
        }
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
    $('#save-button').click(function() {
        $(this).text('Saving...');

        for (var i = 0; i < data.length; i++) {
            //TODO; send the data to the service
        }
    });

    /* Opens the modal to upload an image */
    $('#upload-button').click(function() {
        $('#upload-modal').show();
        $('input[type="file"]').focus();
    });

    /* Zoom in when an image is clicked */
    $('.card').click(function() {
        $('#stop-modal').show();
        $('#modal-image').attr('src', $(this).find('img').attr('src'));
    });

    /* Close the modal */
    $('.close').click(function() {
        resetModal();
        $('.modal').hide();
    });

    /* Close any modal when escape is pressed */
    $(document).keyup(function(e) {
        if (e.keyCode == ESCAPE) {
            resetModal();
            $('.modal').hide();
        }
    });

    /* Close the modal when 'Cancel' is clicked */
    $('#cancel').click(function() {
        resetModal();
        $('.modal').hide();
    });

    /* Upload images to server and update the page */
    $('#ok').click(function() {
        images.push(files[0].name);
        $('.image-container').append(getCard(files[0].name, $('textarea').val()));
        $('.empty').remove();  /* removes the default empty text if necessary */

        // do the binding again since we added some cards
        $('.card').click(function() {
            $('#stop-modal').show();
            $('#modal-image').attr('src', $(this).find('img').attr('src'));
        });

        // clean up the form
        resetModal();
        $('.modal').hide();
    });

    /* Locally save uploaded files and enable the 'Ok' button */
    $('input[type="file"]').change(function(e) {
        files = e.target.files;

        if (files.length > 0) {
            $('#ok').attr('disabled', false);
            $('#ok').attr('title', '');
        } else {
            $('#ok').attr('disabled', true);
            $('#ok').attr('title', 'please select an image before uploading');
        }
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
     * Resets the upload image modal
     */
    function resetModal() {
        $('textarea').val('');
        $('#ok').attr('disabled', true);
        $('#ok').attr('title', 'please select an image before uploading');
        $('input[type="file"]').val('');
        files = null;
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

    /* Returns the tag HTML for the given index in the data */
    function getTag(i) {
        return (
            '<button type="button" id="btn-' + i + '" class="' + getClass(i) + '">' +
                format(data[i].name, data[i].count) +
            '</button>'
        );
    }

    /* Returns the image HTML for the given image and alt text */
    function getCard(image, alt = "a bus stop") {
        return (
            '<div class="card">' +
                '<img src="' + image + '" alt="' + alt + '">' +
                '<div>date uploaded: 5/18/2019</div>' +
            '</div>'
        );
    }
});

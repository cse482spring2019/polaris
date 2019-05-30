var stop;   /* queried stop data */
var added;  /* array of updated tag values */
var files;  /* files being uploaded */

const ESCAPE = 27;                              /* keycode of the escape button */
const DEFAULT_ALT = "a picture of a bus stop";  /* default alt text */

/* constants for interaction with the blob store */
const ACC_NAME = 'polarisImages'
const CONTAINER = 'images';
const URI = 'https://' + ACC_NAME + '.blob.core.windows.net';
const SAS = '?sv=2018-03-28&ss=b&srt=sco&sp=rwdlac&se=2019-06-29T03:' +
            '16:47Z&st=2019-05-28T19:16:47Z&spr=https&sig=7fmidcPpGw' + 
            'wNu2CPqV%2B10d9jketDmm7p08BgVYiuMhc%3D';
const BLOB_STORE = AzureStorage.Blob.createBlobServiceWithSas(URI, SAS);

/* sets everything up once we have the stop data */
function setup(data) {
    stop = data;
    $('#title').text(stop.name + '    |    Stop # ' + stop.id.substring(2) + ' - ' + stop.direction + ' bound');
    $('#access-text').html(
        '<i>accessible to ' + stop.yes + ' users, inaccessible to ' + stop.no + ' users</i>'
    );

    /* reformat the data for use later */
    stop.data = [];
    Object.keys(stop.tags.tagStore).forEach(function(tag) {
        stop.data.push({
            'name': tag,
            'count': stop.tags.tagStore[tag]
        });
    });
    stop.data.sort(function(a, b) { return b.count - a.count; });
    stop.images.sort(function(a, b) {
        let d1 = Date.parse(a.dateUploaded);
        let d2 = Date.parse(b.dateUploaded);

        if (d1 > d2) return -1;
        else if (d2 > d1) return 1;
        else return 0;
    });

    /* for keeping track of updated tags */
    added = new Array(stop.data.length).fill(0);
    files = [];

    /* Puts the initial tags on the page */
    for (let i = 0; i < stop.data.length; i++) {
        if (i == 4) {
            $('#tags').append('<div class="w-100"></div>');
        }
        $('#tags').append(getTag(i));
    }

    for (let i = 0; i < stop.data.length; i++) {
        if (i == 4) {
            $('#modal-tags').append('<div class="w-100"></div>');
        }
        $('#modal-tags').append(getTag(i));
    }

    /* Puts the initial images on the page */
    if (stop.images.length == 0) {
        $('.image-container').append(
            '<div class="empty">no images to show for this stop</div>'
        );
    } else {
        for (let i = 0; i < stop.images.length; i++) {
            $('.image-container').append(getCard(stop.images[i]));
        }
    }

    /* Behavior for clicking on a tag */
    $(".tag").click(function() {
        $('#save-button').text('Save');
        let i = $(this).attr('id').charAt(4);
        if (!$(this).attr('class').includes('tag-selected')) {
            added[i] = 1;
            $(this).attr('class', 'col btn btn-outline-success tag tag-selected');
            $(this).html(format(stop.data[i].name, stop.data[i].count + 1));
        } else {
            added[i] = 0;
            $(this).attr('class', getClass(i));
            $(this).html(format(stop.data[i].name, stop.data[i].count));
        }
    });

    /* Save by updating count and color in the tag */
    $('#save-button').click(function() {
        $(this).text('Saving...');

        let tags = {};
        for (let i = 0; i < added.length; i++) {
            tags[stop.data[i].name] = stop.data[i].count + added[i];
        }
        let msg = {'tags': tags};

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/stops/' + stop.id + '/tags',
            data: JSON.stringify(msg),
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                $('#save-button').text('Saved!');
            }
        });
    });

    /* Opens the modal to upload an image */
    $('#upload-button').click(function() {
        $('#upload-modal').show();
        $('input[type="file"]').focus();

    });

    // /* Zoom in when an image is clicked */
    // $('.card').click(function() {
    //     $('#img-modal').show();
    //     $('#img-modal img').attr('src', $(this).find('img').attr('src'));
    // });

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

    $('#yes').click(function() {
        $('#no').attr('disabled', true);
        $('#yes').html('Saving...');
        // TODO: send to the server
    });

    $('#no').click(function() {
        $('#yes').attr('disabled', true);
        $('#no').html('Saving...');
        // TODO: send to the server
    });

    /* Close the modal when 'Cancel' is clicked */
    // $('#cancel').click(function() {
    //     resetModal();
    //     $('.modal').hide();
    // });

    /* Upload images to server and update the page */
    $('#ok').click(function() {
        BLOB_STORE.createBlockBlobFromBrowserFile('images',
            files[0].name,
            files[0],
            (error, result) => {
                if(!error) {
                    let date = new Date();
                    let newImage = {
                        'imageUrl': URI + "/" + CONTAINER + "/" + 
                                    files[0].name + SAS,
                        'altText': $('textarea').val(),
                        'dateUploaded': date.getMonth() + 1 + '/' +
                        date.getDate() + '/' +
                        date.getFullYear()
                    };
                    stop.images.push(newImage);
                    $('.image-container').prepend(getCard(newImage));

                    $('.empty').remove();  /* remove default empty text */

                    // do the binding again since we added some cards
                    $('.card').click(function() {
                        $('#img-modal').show();
                        $('#img-modal img').attr('src',
                            $(this).find('img').attr('src')
                        );
                    });

                    $.ajax({
                        type: 'PUT',
                        url: 'http://localhost:8080/stops/' + stop.id + '/image',
                        data: JSON.stringify(newImage),
                        dataType: 'json',
                        contentType: 'application/json',
                        success: function (result) {
                            /* don't need to do anything */
                        }
                    });

                    // clean up the form
                    resetModal();
                    $('.modal').hide();
                } else {
                    /* TODO: handle blob error */
                }
            });
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
}

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
    let res = 'col btn btn-outline-success';
    return (stop.data[i].count + added[i]) == 0 ? 'tag ' + res : 'tag tag-default ' + res;
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
    let results = new RegExp('[\?&]' + name + '=([^&#]*)')
        .exec(window.location.search);
    return (results !== null) ? results[1] || 0 : false;
}

/* Returns the tag HTML for the given index in the data */
function getTag(i) {
    return (
        '<button id="btn-' + i + '"class="' + getClass(i) +
        '"href="#">' + format(stop.data[i].name, stop.data[i].count) + '</button>'
    );
}

/* Returns the image HTML for the given image and alt text */
function getCard(image) {
    let alt = image.altText;
    if (alt.length == 0) {
        alt = DEFAULT_ALT;
    }

    return (
        '<div class="card">' +
        '<img src="' + image.imageUrl + '" alt="' + alt + '">' +
        '<div>date uploaded: ' + image.dateUploaded + '</div>' +
        '</div>'
    );
}

/* make a GET request and then load the page */
$(document).ready(function() {
    let data = {
        "id":"1_1000",
        "name":"Pine St & 9th Ave",
        "direction":"NE",
        "yes":21,
        "no":7,
        "images":[],
        "tags":{
            "tagStore":{
                "Overgrowing Foliage":0,
                "Construction Nearby":0,
                "Tight Spaces":0,
                "Lack of Elevator":0,
                "Sharp Inclines":0,
                "Insufficient Light":0,
                "Low-Quality Sidewalk":0,
                "Broken Benches":0
            }
        }
    };

    $('#loading').hide();
    setup(data);
});

var ESCAPE = 27;                                /* keycode of the escape button */
var DEFAULT_ALT = "a picture of a bus stop";    /* default alt text */

var stop;           /* queried stop data */
var added;          /* array of updated tag values */
var files;          /* files being uploaded */

const account = {
    name: "polarisimages",
    sas:  "?sv=2018-03-28&ss=b&srt=sco&sp=rwdlac&se=2019-06-17T09:30:48Z&st=2019-05-20T01:30:48Z&spr=https,http&sig=UKXLk49SPrGAF7rXcW6y4NpzcxsX3YA2tHwKWAZJBXs%3D"
};

const blobUri = 'https://' + account.name + '.blob.core.windows.net';
const blobService = AzureStorage.Blob.createBlobServiceWithSas(blobUri, account.sas);
const container = 'images';

/* sets everything up once we have the stop data */
function setup(data) {

    /* for keeping track of updated tags */
    added = new Array(stop.data.length).fill(0);
    files = [];

    /* Puts the initial tags on the page */
    for (var i = 0; i < stop.data.length; i++) {
        $('.tag-container').append(getTag(i));
    }

    /* Puts the initial images on the page */
    if (stop.images.length == 0) {
        $('.image-container').append(
            '<div class="empty">no images to show for this stop</div>'
        );
    } else {
        for (var i = 0; i < stop.images.length; i++) {
            $('.image-container').append(getCard(stop.images[i]));
        }
    }

    /* Behavior for clicking on a tag */
    $(".tag").click(function() {
        var i = $(this).attr('id').charAt(4);
        if (!$(this).attr('class').includes('tag-selected')) {
            added[i] = 1;
            $(this).attr('class', 'tag tag-selected');
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

        for (var i = 0; i < stop.data.length; i++) {
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
       blobService.createBlockBlobFromBrowserFile('images',
                                                  files[0].name,
                                                  files[0],
                                                  (error, result) => {
                                                     if(error) {
                                                         // Handle blob error
                                                     } else {
                                                         console.log('Upload is successful');
                                                     }
                                                  });
        var date = new Date();
        var newImage = {
            'imageUrl': blobUri + "/" + container + "/" + files[0].name + account.sas,
             'altText': $('textarea').val(),
             'dateUploaded': date.getMonth() + 1 + '/' +
                             date.getDate() + '/' +
                             date.getFullYear(),
             'score': 0,
             'outdatedScore': 0
        };
        stop.images.push(newImage);
        $('.image-container').append(getCard(newImage));

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
    return (stop.data[i].count + added[i]) == 0 ? 'tag' : 'tag tag-default';
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
            format(stop.data[i].name, stop.data[i].count) +
        '</button>'
    );
}

/* Returns the image HTML for the given image and alt text */
function getCard(image) {
    var alt = image.altText;
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
    var data = {
        'id': 'Tyler',
        'images': [
            {'imageUrl': 'a.jpg',
             'altText': 'a nice bus stop',
             'dateUploaded': '5/19/2018',
             'score': 0,
             'outdatedScore': 0
            },
            {'imageUrl': 'b.jpg',
             'altText': 'a pretty nice bus stop',
             'dateUploaded': '5/11/2019',
             'score': 0,
             'outdatedScore': 0
            },
            {'imageUrl': 'c.jpg',
             'altText': 'a super nice bus stop',
             'dateUploaded': '1/1/2010',
             'score': 0,
             'outdatedScore': 0
            }
        ],
        'tags': {
            'tagStore': {
                'Overgrowing Foliage': 0,
                'Construction Nearby': 0,
                'Tight Spaces': 0,
                'Lack of Elevator': 0,
                'Sharp Inclines': 0,
                'Insufficient Light': 0,
                'Low-Quality Sidewalk': 0,
                'Broken Benches': 0
            }
        }
    }
    stop = data;
    $('#title').html('Tyler\'s favorite bus stop');
    $('#name').html('Tyler\'s favorite bus stop');
    $('#direction').html('Stop # ' + stop.id.substring(2) + ' - S bound');

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
        var d1 = Date.parse(a.dateUploaded);
        var d2 = Date.parse(b.dateUploaded);

        if (d1 > d2) return -1;
        else if (d2 > d1) return 1;
        else return 0;
    });

    setup(data);

    /*
    $.get({
        type: 'GET',
        url: 'http://localhost:8080/stops/' + getQueryParam('id'),
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            setup(data);
        },
        error: function (msg, url, line) {
            console.log('GET failed');
        }
    });
    */
});

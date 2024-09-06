let md5Clipboard = $("#copy-md5");
let md5Value = $("#md5").attr("value");

let sha256Clipboard = $("#copy-sha256");
let sha256Value = $("#sha256").attr("value");

let message = $(".copy-message");

if (md5Value.length > 0) {
    addHover(md5Clipboard)
    md5Clipboard.click(function () {
        copyTextToClipboard(md5Value);
        alertUser("MD5");
    });
}

if (sha256Value.length > 0) {
    addHover(sha256Clipboard)
    sha256Clipboard.click(function () {
        copyTextToClipboard(sha256Value);
        alertUser("SHA256");
    });
}

function copyTextToClipboard(text) {
    navigator.clipboard.writeText(text).then(function () {
        console.log('Copying to clipboard was successful.');
    }, function (err) {
        console.error('Could not copy text.', err);
    });
}

function alertUser(hashType) {
    setTimeout(function () {
        message.addClass("show");
        $(".copy-message > p").text(`${hashType} hash was copied!`);
    }, 100);

    message.click(function () {
        $(this).removeClass("show").fadeOut();
    });

    setTimeout(function () {
        message.removeClass("show");
    }, 6000);
}

function addHover(elementId) {
    $(document).ready(function () {
        $(this).css("background-color", "#88e4ff");
        elementId.mouseenter(function () {
            $(this).css("background-color", "#88e4ff");
        }).mouseout(function () {
            $(this).css("background-color", "#ceeeff");
        });
    });
}
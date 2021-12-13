var score = document.getElementById("score");
score.addEventListener("input", function () {
    var numberValue = parseInt(score.value);
    if (numberValue < 0 || numberValue > 10) {
        score.setAttribute("class", "form-control is-invalid");
    } else {
        score.setAttribute("class", score.value === "" ? "form-control" : "form-control is-valid");
    }
});

var age = document.getElementById("age");
age.addEventListener("input", function () {
    var numberValue = age.value === "" ? -1 : parseInt(age.value);
    if (numberValue < 0 || numberValue > 127) {
        age.setAttribute("class", "form-control is-invalid");
    } else {
        age.setAttribute("class", "form-control is-valid");
    }
});

var phone = document.getElementById("phone");
var oldVal;
phone.addEventListener("input", function () {
    var val = this.value.replace(/[^ \-+()0-9]/g, '');
    if (!oldVal || oldVal.length < val.length)
        switch (val.length) {
            case 1:
                val = "+" + val;
                break;
            case 3:
                val += " ";
                break;
            case 5:
                val = val.slice(0, 3) + " (" + val.substring(4);
                break;
            case 7:
                val += ") ";
                break;
            case 13:
                val += "-";
                break;
        }
    oldVal = val;
    phone.setAttribute("class", val.match(/\+[0-9]{2} \([0-9]{2}\) [0-9]{4}-[0-9]{4}/g) ? "form-control is-valid" : "form-control is-invalid");
    phone.value = val;
});

function validateForm() {
    return document.getElementsByClassName("is-invalid").length === 0;
}
function generatePassword() {

    let length = document.getElementById("length-input").value;

    const categories = fillCategories();  // Constructs an array containing the aspects the user would like to include in the generation of their password

    if (validateInput(length)) {

        let password = "";

        for (let i = 0; i < length; i++) {

            let random = Math.ceil(Math.random() * (categories.length) - 1);
            let category = categories[random];
            
            if (category == "u") {
                password += letter();
            }
            else if (category  == "l") {
                password += letter().toLowerCase();
            }
            else if (category  == "n") {
                password += number();
            }
            else if (category == "s") {
                password += special();
            }

            document.getElementById("password").innerHTML = password;
        }
    }
}

function fillCategories() {
    const categories = [];
    if (document.getElementById("uppercase").checked) {
        categories.push("u");
    }
    if (document.getElementById("lowercase").checked) {
        categories.push("l");
    }
    if (document.getElementById("numbers").checked) {
        categories.push("n");
    }
    if (document.getElementById("special").checked) {
        categories.push("s");
    }
    return categories;
}

function letter() {
    const letters = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
    let number = Math.abs(Math.ceil((Math.random() * letters.length) - 1));
    return letters[number];
}

// Returns a whole number, 0 - 9, rounded up
function number() {   
    return Math.ceil(Math.random() * 10) - 1;
}

function special() {
    const special = ['!', '@', '#', '$', '%', '&', '*', '?'];
    return special[Math.ceil(Math.random() * special.length) - 1];
}

function validateInput(userInput) {
    if (isNaN(userInput) || userInput == "" || userInput == 0 || userInput > 50) {
        alert("You must input a non-zero numeric value into the length box that is not greater than 50.");
        return false;
    }
    return true;
}
function getUpdateValues() {
    return {
        name: document.getElementById("updateCoffeeName").value,
        price: Number(document.getElementById("updateCoffeePrice").value)
    }
}

function getPostValues() {
    return {
        name: document.getElementById("newCoffeeName").value,
        price: Number(document.getElementById("newCoffeePrice").value)
    }
}

function clearPostForm() {
    document.getElementById("newCoffeeName").value = "";
    document.getElementById("newCoffeePrice").value = "";
}

function clearUpdateForm() {
    document.getElementById("updateCoffeeName").value = "";
    document.getElementById("updateCoffeePrice").value = "";
}
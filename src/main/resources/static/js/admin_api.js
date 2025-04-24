function getUpdateValues() {
    return {
        name: document.getElementById("updateCoffeeName").value,
        price: Number(document.getElementById("updateCoffeePrice").value),
        img: document.getElementById("updateCoffeeImageUrl").value
    }
}

function getPostValues() {
    return {
        name: document.getElementById("newCoffeeName").value,
        price: Number(document.getElementById("newCoffeePrice").value),
        img: document.getElementById("newCoffeeImageUrl").value
    }
}

function clearPostForm() {
    document.getElementById("newCoffeeName").value = "";
    document.getElementById("newCoffeePrice").value = "";
    document.getElementById("newCoffeeImageUrl").value = "";
}
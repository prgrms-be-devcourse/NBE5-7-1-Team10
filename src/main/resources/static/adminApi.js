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
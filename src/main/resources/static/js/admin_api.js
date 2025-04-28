function getUpdateValues() {
    return {
        name: document.getElementById("updateCoffeeName").value,
        price: Number(document.getElementById("updateCoffeePrice").value),
        img: document.getElementById("updateCoffeeImageUrl").value
    }
}

function getPostValues() {
    const name = document.getElementById("newCoffeeName").value.trim();
    const price = document.getElementById("newCoffeePrice").value;
    const img = document.getElementById("newCoffeeImageUrl").value.trim();

    if (!name) {
        alert("커피 이름을 입력해주세요!");
        return null;
    }

    if (!price || Number(price) <= 0) {
        alert("가격을 올바르게 입력해주세요!");
        return null;
    }

    if (!img) {
        alert("이미지 경로를 입력해주세요!");
        return null;
    }

    return {
        name: name,
        price: Number(price),
        img: img
    };
}

function clearPostForm() {
    document.getElementById("newCoffeeName").value = "";
    document.getElementById("newCoffeePrice").value = "";
    document.getElementById("newCoffeeImage").value = "";
}
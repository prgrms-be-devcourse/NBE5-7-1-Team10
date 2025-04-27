let coffeeMap = {};
let selectedCoffeeId = null;

function loadCoffees() {
    fetch(`/coffees/all`)
        .then(res => res.json())
        .then(response => {
            const data = response.data;
            coffeeMap = {};
            const tbody = document.getElementById("coffeeTable")

            if (data.length === 0) {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="5" style="text-align: center; color: gray;">등록된 커피가 없습니다 ☕</td>
                    </tr>
                `;
                return;
            }

            data.forEach(coffee => {
                coffeeMap[coffee.id] = coffee;

            });
            tbody.innerHTML = ""
            data.forEach(coffee => {
                tbody.innerHTML += `
                    <tr id="coffee-${coffee.id}">
                        <td class="name">${coffee.name}</td>
                        <td class="price">${coffee.price}원</td>
                        <td class="img"><img src="${coffee.img}" height="50" width="auto" alt=""></td>
                        <td><button class="btn btn-dark col-12 order-btn" onclick="showEditForm(${coffee.id})">수정</button></td>
                        <td><button class="btn btn-dark col-12 order-btn" onclick="deleteCoffee(${coffee.id})">삭제</button></td>
                    </tr>
                `;
            });
        }).catch(err => {
            alert("에러발생!!");
            console.log(err);
    })
}

function showEditForm(id) {
    const coffee = coffeeMap[id];
    if(!coffee) {
        alert("해당 id를 가진 커피는 없습니다!")
    }

    selectedCoffeeId = id;

    // 기본 값을 넣어준다.
    document.getElementById("updateCoffeeName").value = coffee.name;
    document.getElementById("updateCoffeePrice").value = coffee.price;
    document.getElementById("updateCoffeeImageUrl").value = coffee.img;
    document.getElementById("editForm").style.display = "block";
}

function addCoffee() {
    const data = getPostValues();
    fetch(`/coffees`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => {
        if (!res.ok) {
            throw new Error("서버 응답 오류");
        }
        return res.json();
    })
        .then(newCoffee => {
            clearPostForm();
            loadCoffees();
        })
}

function updateCoffee() {
    if(selectedCoffeeId == null) return;
    const data = getUpdateValues();
    fetch(`/coffees/${selectedCoffeeId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(response => {
            const updateCoffee = response.data;
            document.querySelector(`#coffee-${updateCoffee.id} td.name`).innerText = updateCoffee.name;
            document.querySelector(`#coffee-${updateCoffee.id} td.price`).innerText = updateCoffee.price + "원";
            document.querySelector(`#coffee-${updateCoffee.id} td.img`).innerText = updateCoffee.img;
            coffeeMap[updateCoffee.id] = updateCoffee;
            hideEditForm();
            alert("수정이 반영되었습니다!")
        }).catch(err => {
        alert("에러 발생!!");
        console.log(err);
    });
}

function deleteCoffee(id) {
    if(id==null) return;

    const confirmed = confirm("정말 삭제하시겠습니까?");
    if (!confirmed) return;

    fetch(`/coffees/${id}`, {
        method: 'DELETE',
    })
        .then(() => {
            const row = document.getElementById(`coffee-${id}`);
            if (row) row.remove();

            delete coffeeMap[id];

            alert("삭제되었습니다!");

            const tbody = document.getElementById("coffeeTable");
            if (tbody.children.length === 0) {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="5" style="text-align: center; color: gray;">
                            등록된 커피가 없습니다 ☕
                        </td>
                    </tr>
                `;
            }
        })
        .catch(err => {
            alert("에러 발생!!");
            console.log(err);
        });
}

function cancelUpdate() {
    hideEditForm();
}

function hideEditForm() {
    document.getElementById("editForm").style.display = "none";
}

window.onload = loadCoffees;
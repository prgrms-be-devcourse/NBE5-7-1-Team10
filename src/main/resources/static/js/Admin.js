let coffeeMap = {};
let selectedCoffeeId = null;

function loadCoffees() {
    fetch(`/coffees`)
        .then(res => res.json())
        .then(data => {
            coffeeMap = {};
            data.forEach(coffee => {
                coffeeMap[coffee.id] = coffee;
            });

            const tbody = document.getElementById("coffeeTable")
            tbody.innerHTML = ""
            data.forEach(coffee => {
                tbody.innerHTML += `
                    <tr>
                        <td>${coffee.name}</td>
                        <td>${coffee.price}원</td>
                        <td>
                            <button onclick="showEditForm(${coffee.id})">수정</button>
                         </td>
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
            alert("추가 완료!");
            clearPostForm();
            loadCoffees();
        })
        .catch(err => {
            alert("에러 발생!!");
            console.error(err);
        });
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
        .then(updateCoffee => {
            coffeeMap[updateCoffee.id] = updateCoffee;
            loadCoffees();
            hideEditForm();
            alert("수정이 반영되었습니다!")
        }).catch(err => {
        alert("에러발생!!");
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
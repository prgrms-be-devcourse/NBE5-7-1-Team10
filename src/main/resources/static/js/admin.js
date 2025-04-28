let coffeeMap = {};
let selectedCoffeeId = null;
let coffeeImageIndex = {};
// { coffeeId: 현재 인덱스 }
function prevImage(coffeeId) {
    const coffee = coffeeMap[coffeeId];
    if (!coffee || !coffee.images || coffee.images.length === 0) return;

    if (!(coffeeId in coffeeImageIndex)) {
        coffeeImageIndex[coffeeId] = 0;
    }

    coffeeImageIndex[coffeeId]--;
    if (coffeeImageIndex[coffeeId] < 0) {
        coffeeImageIndex[coffeeId] = coffee.images.length - 1; // 마지막으로 이동
    }

    const imgTag = document.getElementById(`coffee-img-${coffeeId}`);
    imgTag.src = coffee.images[coffeeImageIndex[coffeeId]];
}

function nextImage(coffeeId) {
    const coffee = coffeeMap[coffeeId];
    if (!coffee || !coffee.images || coffee.images.length === 0) return;

    if (!(coffeeId in coffeeImageIndex)) {
        coffeeImageIndex[coffeeId] = 0;
    }

    coffeeImageIndex[coffeeId]++;
    if (coffeeImageIndex[coffeeId] >= coffee.images.length) {
        coffeeImageIndex[coffeeId] = 0; // 처음으로 이동
    }

    const imgTag = document.getElementById(`coffee-img-${coffeeId}`);
    imgTag.src = coffee.images[coffeeImageIndex[coffeeId]];
}


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
                    <tr id="coffee-${coffee.id}" style="height: 200px; width: auto;">
                        <td class="name">${coffee.name}</td>
                        <td class="price">${coffee.price}원</td>
                        <td class="img">
                            ${coffee.images.length > 1 ? `<button onclick="prevImage(${coffee.id})">◀</button>` : ''}
                            <img id="coffee-img-${coffee.id}" src="${coffee.images[0]}" height="100" width="auto" alt="">
                            ${coffee.images.length > 1 ? `<button onclick="nextImage(${coffee.id})">▶</button>` : ''}
                        </td>      
                        <td class="stock">${coffee.stock}</td>                                                                      
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
    document.getElementById("updateCoffeeImageUrl").value = "";
    document.getElementById("editForm").style.display = "block";
}

function addCoffee() {
    const name = document.getElementById("newCoffeeName").value;
    const price = document.getElementById("newCoffeePrice").value;
    const stock = document.getElementById("newCoffeeStock").value;
    const images = document.getElementById("newCoffeeImage").files;

    const formData = new FormData();
    formData.append('name', name);
    formData.append('price', price);
    formData.append('stock', stock)

    for (let i = 0; i < images.length; i++) {
        formData.append('images', images[i]);
    }

    fetch(`/coffees`, {
        method: 'POST',
        body: formData
    }).then(res => {
        if (!res.ok) {
            throw new Error("서버 응답 오류");
        }
        return res.json();
    }).then(newCoffee => {
        clearPostForm();
        loadCoffees();
    }).catch(err => {
        alert("에러 발생!!");
        console.log(err);
    });
}


function updateCoffee() {
    if (selectedCoffeeId == null) return;
    const formData = new FormData();
    formData.append('name', document.getElementById('updateCoffeeName').value);
    formData.append('price', document.getElementById('updateCoffeePrice').value);
    formData.append('stock', document.getElementById('updateCoffeeStock').value);

    const imageInput = document.getElementById('updateCoffeeImageUrl');
    for (let i = 0; i < imageInput.files.length; i++) {
        formData.append('images', imageInput.files[i]); // 여러 이미지 처리
    }

    fetch(`/coffees/${selectedCoffeeId}`, {
        method: 'PUT',
        body: formData
    })
        .then(res => res.json())
        .then(response => {
            const updateCoffee = response.data;
            document.querySelector(`#coffee-${updateCoffee.id} td.name`).innerText = updateCoffee.name;
            document.querySelector(`#coffee-${updateCoffee.id} td.price`).innerText = updateCoffee.price + "원";
            document.querySelector(`#coffee-${updateCoffee.id} td.stock`).innerText = updateCoffee.stock;
            document.querySelector(`#coffee-${updateCoffee.id} td.img`).innerHTML = `
            ${updateCoffee.images.length > 1 ? `<button onclick="prevImage(${updateCoffee.id})">◀</button>` : ''}
            <img id="coffee-img-${updateCoffee.id}" src="${updateCoffee.images[0]}" height="300" width="300" alt="">
            ${updateCoffee.images.length > 1 ? `<button onclick="nextImage(${updateCoffee.id})">▶</button>` : ''}
             `;

            coffeeMap[updateCoffee.id] = updateCoffee;
            hideEditForm();
            alert("수정이 반영되었습니다!");
        })
        .catch(err => {
            alert("수정 에러 발생!!");
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
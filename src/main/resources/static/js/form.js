updateRemoveButton("address");
updateRemoveButton("creditCard");

let cpfInput = document.getElementById("cpf");
let telephoneInput = document.getElementById("telephone");
telephoneMask(telephoneInput);
cpfMask(cpfInput);


let items = document.querySelectorAll('.addressItem');
items.forEach((item, index) => {
    let cepInput = item.querySelector(`#cep_${index}`);
    if (cepInput) {
        cepMask(cepInput);
    }
});

items = document.querySelectorAll('.creditCardItem');
items.forEach((item, index) => {
    let creditCardInput = item.querySelector(`#creditCardNumber_${index}`);
    if (creditCardInput) {
        creditCardNumberMask(creditCardInput);
    }
});

function updateRemoveButton(type){
    document.querySelectorAll(`.${type}RemoveButton`).forEach(btn => {
        btn.onclick = function() {
            let container = document.getElementById(`${type}Container`)
            let addressItem = this.closest(`.${type}Item`)
            let index = container.children.length;
            if (index < 2) {
                return;
            }
            addressItem.remove();
            reindexAddress(type);
        }
    })
}

function reindexAddress(type){
    let addresses = document.querySelectorAll(`.${type}Item`);
    addresses.forEach((address, index) =>{
           address.querySelectorAll("input, textarea, select").forEach(input => {
                let name = input.name.replace(/\[\d+]/, `[${index}]`)
                let id = input.id.replace(/_\d+$/,  `_${index}`)
                input.name = name;
                input.id = id;
                input.setAttribute("for", id)
           })
           address.querySelectorAll("label").forEach(label => {
            let labelFor = label.htmlFor.replace(/_\d+$/,`_${index}`);
            label.htmlFor = labelFor;
        })

    })
}

function addItem(type){
    let container = document.getElementById(`${type}Container`)
    let index = container.children.length;
    let firstAddress = container.querySelector(`.${type}Item`);
    if(!firstAddress){
        return;
    }

    let newAddress = firstAddress.cloneNode(true);

    newAddress.querySelectorAll("input, textarea, select").forEach(input => {
        input.value = "";
        let name = input.name.replace(/\[\d+]/, `[${index}]`)
        let id = input.id.replace(/_\d+$/, `_${index}`)
        input.name = name;
        input.id = id;
        input.setAttribute("for", id)
    })

    newAddress.querySelectorAll("label").forEach(label => {
        label.htmlFor = label.htmlFor.replace(/_\d+$/, `_${index}`);
    })

    newAddress.querySelectorAll("span.error").forEach(span => span.textContent = "");

    container.appendChild(newAddress);

    updateRemoveButton(type);
}

function cpfMask(input){
    let value = input.value.replace(/\D/g, "");
    value = value.replace(/^(\d{3})(\d)/, "$1.$2");
    value = value.replace(/(\d{3}).(\d{3})(\d)/, "$1.$2.$3")
    value = value.replace(/(\d{3}).(\d{3}).(\d{3})(\d)/, "$1.$2.$3-$4")
    input.value = value;
}

function telephoneMask(input){
    let value = input.value.replace(/\D/g, "");
    value = value.replace(/^(\d{2})(\d)/, "($1) $2")
    value = value.replace(/(\d{5})(\d)/, "$1-$2")
    input.value = value;
}

function cepMask(input){
    let value = input.value.replace(/\D/g, "");
    value = value.replace(/(\d{5})(\d)/, "$1-$2");
    input.value = value;
}

function creditCardNumberMask(input){
    let value = input.value.replace(/\D/g, "");
    value = value.replace(/^(\d{4})(\d)/, "$1 $2");
    value = value.replace(/^(\d{4})\s(\d{4})(\d)/, "$1 $2 $3");
    value = value.replace(/^(\d{4})\s(\d{4})\s(\d{4})(\d)/, "$1 $2 $3 $4");
    input.value = value;
}
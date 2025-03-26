function modifyQuantity(button, value){
  const indexMatch = button.id.match(/(\d)$/);
  const index = indexMatch[1];
  const quantityItem = document.getElementById(`quantityItem_${index}`);
  let currentQuantity = parseInt(quantityItem.innerText);
  if(value < 0 && currentQuantity < 2){
    return
  }
  quantityItem.innerText = currentQuantity + value;
}
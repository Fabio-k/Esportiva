let traces = []
let stateTraces = []
let barLabels = []
let barValues = []
const select = document.getElementById("historySelect");
let startDate = null;
let endDate = null;

async function fetchHistory(historyId, isCategory){
    // Busca no BD as informações para plotar, filtrando por data, se é uma categoria
    // Parâmetros via URL
    let url = `/api/sales-history?id=${historyId}&isCategory=${isCategory}`;
    if(startDate) url += `&startDate=${startDate}`;
    if(endDate) url += `&endDate=${endDate}`;

    // Obtém a resposta do back-end
    const response = await fetch(url);
    const data = await response.json();

     if (!response.ok) {
            const errorMessageDiv = document.querySelector(".errorMessageDiv");
            errorMessageDiv.querySelector("span").innerText = data.error || "Erro desconhecido.";
            console.log(data);
            errorMessageDiv.classList.remove("hidden");
     }

    return data;
}

// Listener para selecionar as opções a serem filtradas (Categorias ou produtos)
document.getElementById("historySelect").addEventListener("change", async () => {
    let historyId = select.value;
    const selectedOption = select.options[select.selectedIndex];
    const isCategory = selectedOption.dataset.iscategory === "true";
    if(historyId == null || historyId === "Selecione uma categoria ou produto") return;

    // Busca os dados da opção selecionada
    fetchHistory(historyId, isCategory)
    .then(data => {
        // Obtém os dados por estado (Gráfico de barras)
        console.log(data);
        const salesByState = data.salesHistoryByState;
        const states = salesByState.map(item => item.state);
        const totalsNum = salesByState.map(item => item.totalQuantity);
        let stateTrace = {
            x: states,
            y: totalsNum,
            type: 'bar',
            name: selectedOption.innerText
        };
        stateTraces.push(stateTrace);
        renderSalesByState()

        // Obtém os dados por data (Gráfico de linha)
        const salesHistory = data.salesHistoryByDate;
        const dates = salesHistory.map(item => item.purchaseDate);
        const totals = salesHistory.map(item => item.totalQuantity);

        barLabels.push(selectedOption.innerText);
        barValues.push(totals.reduce((total, value) => total + value,0))

        let trace = {
          x: dates,
          y: totals,
          type: 'scatter',
          name: selectedOption.innerText
        };

        traces.push(trace);

        renderTraces();
    });

    // Atualiza a interface, adicionando o elemento
    // E ocultando a opção da lista de seleção
    select.selectedIndex = 0;
    selectedOption.hidden = true;
    const traceButton = document.createElement("button");
    traceButton.innerHTML = `<img src="/images/icons/close-blue.svg" class="h-5"><p>${selectedOption.innerText}</p>`;
    traceButton.addEventListener("click", function(){deleteTrace(this)})
    traceButton.classList = "flex justify-between items-center gap-2 px-4 py-2 bg-blue-100 hover:bg-blue-200 rounded-xl cursor-pointer text-blue-500"
    document.getElementById("traces").appendChild(traceButton);
})


document.getElementById("startDate").addEventListener("change", function(){
    startDate = this.value;
    applyDateFilter(this)
});
document.getElementById("endDate").addEventListener("change", function(){
    endDate = this.value;
    applyDateFilter(this);
});








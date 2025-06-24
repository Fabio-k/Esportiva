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

function deleteTrace(button){
    // Refaz os gráficos quando uma opção é removida
    // Procura o botão clicado para remover
    const option = Array.from(select.options).find(option => option.innerText === button.innerText);
    // Faz a opção aparecer de volta na lista de opções
    if (option){
        option.hidden = false;
    }
    button.remove();

    // Remove os valores dele do input do gráfico
    traces = traces.filter(trace => trace.name !== button.innerText);
    stateTraces = stateTraces.filter(trace => trace.name !== button.innerText);

    // Diminui a quantidade de rótulos nos gráficos
    const index = barLabels.indexOf(button.innerText);
    if (index !== -1) {
        barLabels.splice(index, 1);
        barValues.splice(index, 1);
    }

    // Refaz os gráficos
    renderTraces();
    renderSalesByState();
}

document.getElementById("startDate").addEventListener("change", function(){
    startDate = this.value;
    applyDateFilter(this)
});
document.getElementById("endDate").addEventListener("change", function(){
    endDate = this.value;
    applyDateFilter(this);
});

function applyDateFilter() {
    // Obtém os dados a serem plotados (Valores, nome da categoria, etc)
    let promises = traces.map(trace => {
        let selectedOption = Array.from(select.options).find(option => option.innerText === trace.name);
        let historyId = selectedOption.value;
        let isCategory = selectedOption.dataset.iscategory === "true";

        // Com essas informações, busca os dados no BD
        return fetchHistory(historyId, isCategory)
            .then(data => {
                const salesHistory = data.salesHistoryByDate;
                let values = salesHistory.map(item => item.totalQuantity)
                let barIndex = barLabels.indexOf(trace.name);
                if (values.length > 0) barValues[barIndex] = values.reduce((total, value) => total + value, 0);
                else barValues[barIndex] = 0;

                trace.x = salesHistory.map(item => item.purchaseDate);
                trace.y = values;
            });
    });

    // Aguarda a execução de todas as promessas, antes de plotar o gráfico
    Promise.all(promises).then(() => renderTraces());

    // Obtém os dados a serem plotados (Valores, nome da categoria, etc)
    promises = stateTraces.map(trace => {
        let selectedOption = Array.from(select.options).find(option => option.innerText === trace.name);
        let historyId = selectedOption.value;
        let isCategory = selectedOption.dataset.iscategory === "true";

        // Com essas informações, busca os dados no BD (Por estado)
        return fetchHistory(historyId, isCategory)
            .then(data => {
                const salesHistory = data.salesHistoryByState;
                let values = salesHistory.map(item => item.totalQuantity)

                trace.x = salesHistory.map(item => item.state);
                trace.y = values;
            });
    });

    // Faz o gráfico por estados
    Promise.all(promises).then(() => renderSalesByState());
}

function getLayout(title){
    // Configurações do Plotly
    return {
        autosize: true,
        title: {
            text: title
          },
        legend: {
            orientation: 'v',
            x: 0.5,
            xanchor: 'center',
            y: -1.0,    // Afasta a legenda do texto do eixo X
            yanchor: 'top'
        },
        margin: {} // A margem não afasta a legenda do gráfico
    };
}

function renderTraces(){
    // Cria o gráfico com as vendas em função do tempo e o valor total
    // Gráfico de linha com o histórico de vendas
    Plotly.react('myDiv', traces, getLayout("Histórico de vendas"), {displayModeBar: false, responsive: true});
    
    // Obtém os valores totais para fazer o gráfico de barras com o valor total
    const totalQuantityData = [];

        for(let i = 0; i < barLabels.length; i++){
            let bar = {
                x: [""],
                y: [barValues[i]],
                name: barLabels[i],
                type: 'bar',
                hovertemplate:  
                '<b>%{x}</b>' + // Exibe o x em negrito
                'Valor: %{y}',  // Exibe o valor y
            };
            totalQuantityData.push(bar);
        }

        Plotly.newPlot('totalQuantity', totalQuantityData, getLayout("Número total de vendas"), {displayModeBar: false, responsive: true});
}

function renderSalesByState(){
    // Cria o gráfico, porém por estado
    Plotly.react('salesStateDiv', stateTraces, getLayout("Vendas por estado"),  {displayModeBar: false, responsive: true})
}
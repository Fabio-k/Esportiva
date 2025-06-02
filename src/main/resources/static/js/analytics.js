let traces = []
let stateTraces = []
let pieLabels = []
let pieValues = []
const select = document.getElementById("historySelect");
let startDate = null;
let endDate = null;

function fetchHistory(historyId, isCategory){
    let url = `/api/sales-history?id=${historyId}&isCategory=${isCategory}`;
    if(startDate) url += `&startDate=${startDate}`;
    if(endDate) url += `&endDate=${endDate}`;
    return fetch(url)
        .then(response => {
            if(!response.ok) throw new Error(`Erro: ${response.status}`);
            return response.json();
        })
        .catch(err => {
            console.error("Erro na requisição: ", err);
            return [];
            }
        );
}

document.getElementById("historySelect").addEventListener("change", () => {
    let historyId = select.value;
    const selectedOption = select.options[select.selectedIndex];
    const isCategory = selectedOption.dataset.iscategory === "true";
    if(historyId == null || historyId === "Selecione uma categoria ou produto") return;

    fetchHistory(historyId, isCategory)
    .then(data => {
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

        const salesHistory = data.salesHistoryByDate;
        const dates = salesHistory.map(item => item.purchaseDate);
        const totals = salesHistory.map(item => item.totalQuantity);

        pieLabels.push(selectedOption.innerText);
        pieValues.push(totals.reduce(sum,0))

        let trace = {
          x: dates,
          y: totals,
          type: 'scatter',
          name: selectedOption.innerText
        };

        traces.push(trace);

        renderTraces();
    });

    select.selectedIndex = 0;
    selectedOption.hidden = true;
    const traceButton = document.createElement("button");
    traceButton.innerHTML = `<img src="/images/icons/close-blue.svg" class="h-5"><p>${selectedOption.innerText}</p>`;
    traceButton.addEventListener("click", function(){deleteTrace(this)})
    traceButton.classList = "flex justify-between items-center gap-2 px-4 py-2 bg-blue-100 hover:bg-blue-200 rounded-xl cursor-pointer text-blue-500"
    document.getElementById("traces").appendChild(traceButton);
})

function deleteTrace(button){
    const option = Array.from(select.options).find(option => option.innerText === button.innerText);
    if (option){
        option.hidden = false;
    }
    button.remove();
    traces = traces.filter(trace => trace.name !== button.innerText);
    stateTraces = stateTraces.filter(trace => trace.name !== button.innerText);

    const index = pieLabels.indexOf(button.innerText);
    if (index !== -1) {
        pieLabels.splice(index, 1);
        pieValues.splice(index, 1);
    }

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
    let promises = traces.map(trace => {
        let selectedOption = Array.from(select.options).find(option => option.innerText === trace.name);
        let historyId = selectedOption.value;
        let isCategory = selectedOption.dataset.iscategory === "true";

        return fetchHistory(historyId, isCategory)
            .then(data => {
                const salesHistory = data.salesHistoryByDate;
                let values = salesHistory.map(item => item.totalQuantity)
                let pieIndex = pieLabels.indexOf(trace.name);
                if (values.length > 0) pieValues[pieIndex] = values.reduce(sum, 0);
                else pieValues[pieIndex] = 0;

                trace.x = salesHistory.map(item => item.purchaseDate);
                trace.y = values;
            });
    });

    Promise.all(promises).then(() => renderTraces());

    promises = stateTraces.map(trace => {
        let selectedOption = Array.from(select.options).find(option => option.innerText === trace.name);
        let historyId = selectedOption.value;
        let isCategory = selectedOption.dataset.iscategory === "true";

        return fetchHistory(historyId, isCategory)
            .then(data => {
                const salesHistory = data.salesHistoryByState;
                let values = salesHistory.map(item => item.totalQuantity)

                trace.x = salesHistory.map(item => item.state);
                trace.y = values;
            });
    });

   Promise.all(promises).then(() => renderSalesByState());

}

function getLayout(title){
    return {
        autosize: true,
        title: {
            text: title
          },
          legend: {
              orientation: 'v',
              x: 0.5,
              xanchor: 'center',
              y: -0.2,
              yanchor: 'top'
            },
        margin: {}
    };
}

function renderTraces(){
    Plotly.react('myDiv', traces, getLayout("Histórico de vendas"), {displayModeBar: false, responsive: true});
    const totalQuantityData = [];

        for(let i = 0; i < pieLabels.length; i++){
            let bar = {
                x: [""],
                y: [pieValues[i]],
                name: pieLabels[i],
                type: 'bar'
            };
            totalQuantityData.push(bar);
        }

        Plotly.newPlot('totalQuantity', totalQuantityData, getLayout("Número total de vendas"), {displayModeBar: false, responsive: true});
}

function renderSalesByState(){
    Plotly.react('salesStateDiv', stateTraces, getLayout("Venda por estado"),  {displayModeBar: false, responsive: true})
}

function sum(total, value, index, array){
    return total + value;
}
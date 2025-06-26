function analytics() {
  return {
    labels: [],
    startDate: null,
    endDate: null,
    historyChart: null,
    totalQuantityChart: null,
    stateChart: null,

    init() {
      this.select = document.getElementById("historySelect");
      this.historyChart = new HistoryChart("myDiv", "Histórico de vendas");
      this.totalQuantityChart = new TotalQuantityChart(
        "totalQuantity",
        "Número total de vendas"
      );
      this.stateChart = new HistoryChart("salesStateDiv", "Venda por estado");
      console.log("analytics OK!");
    },

    async renderNewData(event) {
      const select = event.target;
      const historyId = select.value;

      const selectedOption = select.options[select.selectedIndex];
      const selectedLabel = selectedOption.innerText;
      const isCategory = selectedOption.dataset.iscategory === "true";

      if (!historyId || historyId === "Selecione uma categoria ou produto")
        return;

      this.labels.push(selectedLabel);

      const data = await fetchHistory(
        historyId,
        isCategory,
        this.startDate,
        this.endDate
      );

      this.historyChart.addTrace(
        formatHistoryGraphicTrace(data, selectedLabel)
      );

      this.stateChart.addTrace(
        formatStateGraphicTrace(data.salesHistoryByState, selectedLabel)
      );

      this.totalQuantityChart.addBar(
        selectedLabel,
        data.salesHistoryByDate.map((item) => item.totalQuantity)
      );

      this.renderGraphics();

      select.selectedIndex = 0;
      selectedOption.hidden = true;
    },

    async applyDateFilter() {
      // Atualiza os dados por data e por estado em paralelo

      this.historyChart.traces = [];
      this.stateChart.traces = [];
      this.totalQuantityChart.barLabels = [];
      this.totalQuantityChart.barValues = [];

      for (const label of this.labels) {
        const allOptions = [
          ...document.getElementById("historySelect").options,
          ...document.getElementById("selectCategory").options,
        ];
        const selectedOption = Array.from(allOptions).find(
          (option) => option.innerText === label
        );
        const historyId = selectedOption.value;
        const isCategory = selectedOption.dataset.iscategory === "true";

        const data = await fetchHistory(
          historyId,
          isCategory,
          this.startDate,
          this.endDate
        );

        this.totalQuantityChart.addBar(
          label,
          data.salesHistoryByDate.map((item) => item.totalQuantity)
        );

        this.historyChart.addTrace(formatHistoryGraphicTrace(data, label));

        this.stateChart.addTrace(
          formatStateGraphicTrace(data.salesHistoryByState, label)
        );
      }

      this.renderGraphics();
    },

    removeTraceByLabel(label) {
      // Reexibe a opção no select
      const allOptions = [
        ...document.getElementById("historySelect").options,
        ...document.getElementById("selectCategory").options,
      ];
      const selectedOption = Array.from(allOptions).find(
        (option) => option.innerText === label
      );
      if (selectedOption) selectedOption.hidden = false;

      this.labels = this.labels.filter((l) => l !== label);

      // Remove traces e valores associados
      this.historyChart.removeTraceByLabel(label);
      this.totalQuantityChart.removeBar(label);
      this.stateChart.removeTraceByLabel(label);

      this.renderGraphics();
    },

    renderGraphics() {
      this.historyChart.render();
      this.totalQuantityChart.render();
      this.stateChart.render();
    },
  };
}

function formatHistoryGraphicTrace(data, label) {
  const salesHistory = data.salesHistoryByDate;
  const dates = salesHistory.map((item) => item.purchaseDate);
  const totals = salesHistory.map((item) => item.totalQuantity);

  let trace = {
    x: dates,
    y: totals,
    type: "scatter",
    name: label,
  };

  return trace;
}

function formatStateGraphicTrace(salesByState, label) {
  const states = salesByState.map((item) => item.state);
  const totalsNum = salesByState.map((item) => item.totalQuantity);
  let stateTrace = {
    x: states,
    y: totalsNum,
    type: "bar",
    name: label,
  };
  return stateTrace;
}

function getLayout(title) {
  // Configurações do Plotly
  return {
    autosize: true,
    title: {
      text: title,
    },
    legend: {
      orientation: "v",
      x: 0.5,
      xanchor: "center",
      y: -1.0, // Afasta a legenda do texto do eixo X
      yanchor: "top",
    },
    margin: {}, // A margem não afasta a legenda do gráfico
  };
}

async function fetchHistory(id, isCategory, startDate, endDate) {
  let url = `/api/sales-history?id=${id}&isCategory=${isCategory}`;
  if (startDate) url += `&startDate=${startDate}`;
  if (endDate) url += `&endDate=${endDate}`;

  const response = await fetch(url);
  const data = await response.json();
  if (!response.ok) {
    const errorMessageDiv = document.querySelector(".errorMessageDiv");
    errorMessageDiv.querySelector("span").innerText =
      data.error || "Erro desconhecido.";
    errorMessageDiv.classList.remove("hidden");
    throw new Error(data.error || "Erro desconhecido.");
  }
  return data;
}

class HistoryChart {
  constructor(divId, title) {
    this.divId = divId;
    this.title = title;
    this.traces = [];
  }

  addTrace(trace) {
    this.traces.push(trace);
  }

  removeTraceByLabel(label) {
    this.traces = this.traces.filter((trace) => trace.name !== label);
  }

  render() {
    Plotly.react(this.divId, this.traces, getLayout(this.title), {
      displayModeBar: false,
      responsive: true,
    });
  }
}

class TotalQuantityChart {
  constructor(divId, title) {
    this.divId = divId;
    this.title = title;
    this.barLabels = [];
    this.barValues = [];
  }

  addBar(label, values) {
    this.barLabels.push(label);
    const total = Array.isArray(values)
      ? values.reduce((a, b) => a + b, 0)
      : values;
    this.barValues.push(total);
  }

  removeBar(label) {
    const idx = this.barLabels.indexOf(label);
    if (idx !== -1) {
      this.barLabels.splice(idx, 1);
      this.barValues.splice(idx, 1);
    }
  }

  async updateBar(label, values) {
    const idx = this.barLabels.indexOf(label);
    this.barValues[idx] =
      values.length > 0 ? values.reduce((a, b) => a + b, 0) : 0;
  }

  render() {
    const data = this.barLabels.map((label, i) => ({
      x: [""],
      y: [this.barValues[i]],
      name: label,
      type: "bar",
      hovertemplate: "<b>%{x}</b>Valor: %{y}",
    }));
    Plotly.newPlot(this.divId, data, getLayout(this.title), {
      displayModeBar: false,
      responsive: true,
    });
  }
}

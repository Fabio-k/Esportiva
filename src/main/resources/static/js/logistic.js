function logistic() {
  return {
    isTransaction: true,
    selectedStage: "todos",
    filterParams: { clientName: "", productName: "", id: "", date: "" },
    stageLabels: {
      todos: { title: "todos", isTransaction: null },
      em_processamento: { title: "em processamento", isTransaction: true },
      em_transito: { title: "em transito", isTransaction: true },
      entregue: { title: "entregue", isTransaction: null },
      compra_cancelada: { title: "compra cancelada", isTransaction: true },
      em_troca: { title: "em troca", isTransaction: false },
      trocado: { title: "trocado", isTransaction: false },
      troca_finalizada: { title: "troca finalizada", isTransaction: false },
      troca_recusada: { title: "troca recusada", isTransaction: false },
    },
    items: [],
    showModal: false,
    modalItemId: null,
    modalAction: null,
    modalItemStatus: null,
    addToStock: false,

    openModal(item, action) {
      this.modalItemStatus = item.status;
      this.modalItemId = item.id;
      this.modalAction = action; // 'approve' ou 'reprove'
      this.showModal = true;
    },

    action(id, isApproved) {
      let transactionId = null;
      let orderId = null;
      if (this.isTransaction) {
        transactionId = id;
      } else {
        orderId = id;
      }
      fetch(`/admin/api/logistic/action`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          transactionId,
          orderId,
          isApproved: isApproved,
          isReturnToStock: this.addToStock,
        }),
      }).then(() => this.updateItems());
    },

    init() {
      this.updateItems();
    },

    updateItems() {
      this.addToStock = false;
      let url = this.isTransaction
        ? `/admin/api/logistic`
        : `/admin/api/logistic/orders`;
      fetch(url)
        .then((response) => response.json())
        .then((data) => {
          this.items = Array.isArray(data) ? data : [];
          console.log(data);
        });
    },

    isDisabled(status) {
      console.log(status);
      if (
        (this.isTransaction && status == "ENTREGUE") ||
        status == "COMPRA_CANCELADA" ||
        status == "TROCA_RECUSADA" ||
        status == "TROCA_FINALIZADA"
      ) {
        return true;
      }

      return false;
    },

    filteredItems() {
      return this.items.filter(
        (item) =>
          this.selectedStage === "todos" ||
          this.selectedStage === item.status.toLowerCase()
      );
    },
  };
}

let messages = [];

async function sendMessage() {
    const message = document.getElementById("message").value.trim();
    const messagesDiv = document.getElementById("messagesDiv");

    if (message != "") {
        // Insere a mensagem do usuário
        messages.push(message)
        const spanUser = document.createElement("span");
        spanUser.textContent = message;
        spanUser.style.display = "block";
        spanUser.classList = "userMessage"
        messagesDiv.appendChild(spanUser);
        document.getElementById("message").value = "";
        messagesDiv.scrollTop = messagesDiv.scrollHeight;

        // Envia a mensagem para a IA
        const body = {
            // Pega a consulta atual do usuário e a interação anterior para contexto do histórico de conversa
            message: messages.slice(-3),
        };
        let iaResponse = await sendAI(body);

        // Insere a resposta da IA
        console.log(iaResponse)
        messages.push(iaResponse)
        const spanAI = document.createElement("span");

        // Converte ** para <b></b>
        iaResponse = iaResponse.replace(/\*\*(.*?)\*\*/g, "<b>$1</b>");

        // Adiciona os outros atributos da resposta
        spanAI.textContent = iaResponse;
        spanAI.innerHTML = iaResponse;
        spanAI.style.display = "block";
        spanAI.classList = "aiMessage"
        messagesDiv.appendChild(spanAI);
        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    }
}

// Envia a mensagem para a IA e recebe a resposta
async function sendAI(body) {
    const response = await fetch("/api/ai/message", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    }
    )
    if (!response.ok) return null;
    const responseText = await response.json()
    return responseText.reply;
}

function toggleChat(){
    document.getElementById("chatModal").classList.toggle("hidden");
}

document.addEventListener("click", (event) => {
    const modal = document.getElementById("chatModal");
    const chatButton = document.getElementById("chatButton");

    if (!modal.classList.contains("hidden") && !modal.contains(event.target) && !chatButton.contains(event.target)) {
        modal.classList.add("hidden");
    }
})


// Permite que ao apertar ENTER no input do chat, envie ele para a IA
const inputChat = document.getElementById('message');
inputChat.addEventListener('keydown', function (event) {
    // Verifica se a tecla pressionada é Enter
    if (event.key === 'Enter') {
        event.preventDefault(); // Se necessário, previne a ação padrão
        sendMessage(); // Chama a função para enviar a mensagem
    }
});
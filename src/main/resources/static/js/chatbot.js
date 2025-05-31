let messages = [];

// Cria uma mensagem padrão inicial da IA no chat:
// Nota: Não precisa enviar essa mensagem para o chatbot
const messagesDivFirstMessage = document.getElementById("messagesDiv");
const firstIAMessage = document.createElement("span");
firstIAMessage.textContent = "Olá! Seja bem-vindo ao Esportiva! Sou sua assistente virtual. Em que posso ajudar?";
firstIAMessage.innerHTML = "Olá! Seja bem-vindo ao Esportiva! Sou sua assistente virtual. Em que posso ajudar?";
firstIAMessage.style.display = "block";
firstIAMessage.classList = "aiMessage"
messagesDivFirstMessage.appendChild(firstIAMessage);

let savedMessages = localStorage.getItem("chat");
if(savedMessages){
    try {
        messages = JSON.parse(savedMessages);
    }catch(e){
        localStorage.removeItem("chat");
        messages = [];
    }

    const messagesDiv = document.getElementById("messagesDiv");
    for(let i = 0; i < messages.length; i++){
        const isAi = i % 2 != 0;
        let messageText = messages[i];
        console.log(messages);

        if(isAi){
            messagesDiv.appendChild(getAiMessageElement(messageText));
        } else {
            messagesDiv.appendChild(getUserMessageElement(messageText));
        }
    }
}



async function sendMessage() {
    const message = document.getElementById("message").value.trim();
    const messagesDiv = document.getElementById("messagesDiv");

    if(message.trim() === "") return;

    // Insere a mensagem do usuário
    messages.push(message)
    localStorage.setItem("chat", JSON.stringify(messages));

    messagesDiv.appendChild(getUserMessageElement(message));
    document.getElementById("message").value = "";
    messagesDiv.scrollTop = messagesDiv.scrollHeight;

    // Envia a mensagem para a IA
    const body = {
        // Pega a consulta atual do usuário e a interação anterior para contexto do histórico de conversa
        message: messages.slice(-3),
    };
    let iaResponse = await sendAI(body);

    // Insere a resposta da IA

    messages.push(iaResponse)
    localStorage.setItem("chat", JSON.stringify(messages));

    messagesDiv.appendChild(getAiMessageElement(iaResponse));
    messagesDiv.scrollTop = messagesDiv.scrollHeight;

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

function toggleChat() {
    document.getElementById("chatModal").classList.toggle("hidden");
    document.getElementById("messagesDiv").scrollTop = document.getElementById("messagesDiv").scrollHeight;
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

function getAiMessageElement(iaResponse){
    const spanAI = document.createElement("span");

    // Converte para link
    iaResponse = iaResponse.replace(/\*\*\s*(\d+)\s*\|\s*(.*?)\s*\*\*/g, (fullMatch, productId, productName) => {
        return `<a style="text-decoration:none;font-weight:bold;color:blue" href="/products/${productId}">${productName}</a>`
    });

    iaResponse = iaResponse.replace(/\n/g, "<br>");

    // Adiciona os outros atributos da resposta
    spanAI.textContent = iaResponse;
    spanAI.innerHTML = iaResponse;
    spanAI.style.display = "block";
    spanAI.classList = "aiMessage"

    return spanAI;
}

function getUserMessageElement(message){
    const spanUser = document.createElement("span");
    spanUser.textContent = message;
    spanUser.style.display = "block";
    spanUser.classList = "userMessage"
    return spanUser;
}
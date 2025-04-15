getNotifications()
setInterval(() => getNotifications(), 5000);

document.getElementById('notificationContainer').addEventListener('mouseenter', () => {
    let newNotifications = []
    document.querySelectorAll(".notificationCard").forEach(notification => {
        if (notification.dataset.isViewed === "false"){
            newNotifications.push(notification.dataset.id);
        }
    });

    if(newNotifications.length > 0){
        fetch("/api/notifications/viewed", {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newNotifications)
        })
    }

    document.getElementById('notificationIcon').src = "/images/icons/notification.svg";
});

function formatDate(dateTime){
    const date = new Date(dateTime);
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes}`;
}

async function getNotifications(){
    const response = await fetch("/api/notifications");
    const result = await response.json();
    const notificationDiv = document.getElementById("notificationList");
    let newNotification = false;

    notificationDiv.innerHTML = "";

    for(let notification of result){
        if(!notification.isViewed)
            newNotification = true;

        const div = document.createElement("div");
        div.classList.add("notificationCard");
        div.style = "display: flex; justify-content: space-between";
        div.innerHTML = `<p>${notification.message}</p><p>${formatDate(notification.createdAt)}</p>`;
        div.dataset.id = notification.id;
        div.dataset.isViewed = notification.isViewed;
        notificationDiv.appendChild(div);
    }

    if(newNotification){
        document.getElementById('notificationIcon').src = "/images/icons/notification-on.svg";
    }
}


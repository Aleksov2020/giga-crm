const CommandMessagesOut = {
    EXTRACT_LINKS: "EXTRACT_LINKS",
    EXTRACT_PAGE: "EXTRACT_PAGE",
    EXTRACT_PAGES_FROM_LINKS: "EXTRACT_PAGES_FROM_LINKS",
    EXTRACT_TBANKROT_DATA: "EXTRACT_TBANKROT_DATA",
    EXTRACT_FULL_CYCLE: "EXTRACT_FULL_CYCLE",
    TEST_REMAP: "TEST_REMAP",
}

const avitoGrabBtn = document.getElementById("extractBtn");
avitoGrabBtn.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, {command: CommandMessagesOut.EXTRACT_LINKS}));
const avitoGrabPage = document.getElementById("extractPage");
avitoGrabPage.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, {command: CommandMessagesOut.EXTRACT_PAGE}));
const avitoGrabPagesFromLinks = document.getElementById("extractPagesFromLinks");
avitoGrabPagesFromLinks.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, {command: CommandMessagesOut.EXTRACT_PAGES_FROM_LINKS}));

const tbankrotGrabData = document.getElementById("extractBrokerData");
tbankrotGrabData.addEventListener("click", () => chrome.runtime.sendMessage(
    chrome.runtime.id, 
    {
        command: CommandMessagesOut.EXTRACT_TBANKROT_DATA
    }
));

const extractFullCycle = document.getElementById("extractFullCycle");
extractFullCycle.addEventListener("click", 
    () => {
        chrome.runtime.sendMessage(    
            chrome.runtime.id, 
            {
                command: CommandMessagesOut.EXTRACT_FULL_CYCLE, 
                data: {
                    link: document.getElementById("linkToAvitoMap").value,
                    creds: {
                        username: localStorage.getItem("username"),
                        key: localStorage.getItem("key"),
                    }
                }
            })
    }
);

const testRemap = document.getElementById("testRemap");
testRemap.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, {command: CommandMessagesOut.EXTRACT_LINKS}));



const creds = {
    username: null,
    key: null,
}

const loginBtn = document.getElementById("login");
loginBtn.addEventListener("click", () => {
    creds.username = document.getElementById("username").value;
    creds.key = document.getElementById("key").value;

    localStorage.setItem("key", creds.key);
    localStorage.setItem("username", creds.username);
    
    document.getElementById("auth-panel").classList.add("hidden");
    document.getElementById("main-panel").classList.remove("hidden");
});

const quitBtn = document.getElementById("backToLogin");
quitBtn.addEventListener("click", () => {
    localStorage.setItem("key", null);
    localStorage.setItem("username", null);
    
    document.getElementById("auth-panel").classList.remove("hidden");
    document.getElementById("main-panel").classList.add("hidden");
});

const additionalBtn = document.getElementById("additionalBtns");
additionalBtn.addEventListener("click", () => {   
    document.getElementById("additional-panel").classList.remove("hidden");
    document.getElementById("main-panel").classList.add("hidden");
});

const backToMainBtn = document.getElementById("backToMain");
backToMainBtn.addEventListener("click", () => {   
    document.getElementById("additional-panel").classList.add("hidden");
    document.getElementById("main-panel").classList.remove("hidden");
});

if (localStorage.getItem("key") && localStorage.getItem("username")) {
    document.getElementById("auth-panel").classList.add("hidden");
    document.getElementById("main-panel").classList.remove("hidden");
}
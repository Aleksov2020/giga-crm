const CommandMessagesOut = {
    EXTRACT_LINKS: "EXTRACT_LINKS",
    EXTRACT_PAGE: "EXTRACT_PAGE",
    EXTRACT_PAGES_FROM_LINKS: "EXTRACT_PAGES_FROM_LINKS",
    EXTRACT_TBANKROT_DATA: "EXTRACT_TBANKROT_DATA",
    EXTRACT_FULL_CYCLE: "EXTRACT_FULL_CYCLE",
    TEST_REMAP: "TEST_REMAP",
}

const avitoGrabBtn = document.getElementById("extractBtn");
avitoGrabBtn.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, CommandMessagesOut.EXTRACT_LINKS));
const avitoGrabPage = document.getElementById("extractPage");
avitoGrabPage.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, CommandMessagesOut.EXTRACT_PAGE));
const avitoGrabPagesFromLinks = document.getElementById("extractPagesFromLinks");
avitoGrabPagesFromLinks.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, CommandMessagesOut.EXTRACT_PAGES_FROM_LINKS));

const tbankrotGrabData = document.getElementById("extractBrokerData");
tbankrotGrabData.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, CommandMessagesOut.EXTRACT_TBANKROT_DATA));
const extractFullCycle = document.getElementById("extractFullCycle");
extractFullCycle.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, CommandMessagesOut.EXTRACT_FULL_CYCLE));

const testRemap = document.getElementById("testRemap");
testRemap.addEventListener("click", () => chrome.runtime.sendMessage(chrome.runtime.id, CommandMessagesOut.TEST_REMAP));

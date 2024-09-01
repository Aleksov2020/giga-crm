const CommandMessagesIn = {
    EXTRACT_LINKS: "EXTRACT_LINKS",
    EXTRACT_PAGE: "EXTRACT_PAGE",
    EXTRACT_PAGES_FROM_LINKS: "EXTRACT_PAGES_FROM_LINKS",
    EXTRACT_TBANKROT_DATA: "EXTRACT_TBANKROT_DATA",
    EXTRACT_FULL_CYCLE: "EXTRACT_FULL_CYCLE",
    TEST_REMAP: "TEST_REMAP",
}

chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
    const mapMessagesToActions = {
        [CommandMessagesIn.EXTRACT_LINKS]: PerformExtractLinks,
        [CommandMessagesIn.EXTRACT_PAGE]: PerformExtractPage,
        [CommandMessagesIn.EXTRACT_PAGES_FROM_LINKS]: PerformExtractPagesFromLinks,
        [CommandMessagesIn.EXTRACT_TBANKROT_DATA]: PerformExtractTBankrotData,
        [CommandMessagesIn.EXTRACT_FULL_CYCLE]: PerformFullCycle,
        [CommandMessagesIn.TEST_REMAP]: PerformTestRemap,
    };
    if (mapMessagesToActions[message]) {
        mapMessagesToActions[message]();
    }
});

async function PerformExtractPagesFromLinks() {
    console.log("double extract begin");
    await chrome.tabs.query({ active: true }, async (tabs) => {
        var tab = tabs.filter((valtab) => !valtab.url.startsWith("chrome://"))[0];
        if (tab) {
            const target_tabs = [];
            const delay = new Promise((r) => setTimeout(r, 2000));
            await chrome.scripting.executeScript(
                {
                    target: { tabId: tab.id, allFrames: true },
                    func: extractLinks,
                },
                async (results) => {
                    const headings = results[0].result;

                    for (let i = 0; i < headings.length; i++) {
                        let tab1 = await chrome.tabs.create({ url: headings[i] });
                        target_tabs.push({ id: tab1.id, url: headings[i] });
                    }

                    return true;
                }
            );
            await delay;
            const delay1 = new Promise((r) => setTimeout(r, 5000));
            await delay1;
            const extractedData = [];
            for (const target of target_tabs) {
                const target_id = target.id;
                const target_url = target.url;
                await chrome.scripting.executeScript(
                    {
                        target: { tabId: target_id, allFrames: true },
                        func: extractPage,
                    },
                    (results) => {
                        const data = results[0].result;
                        console.log(data);
                        extractedData.push({ url: target_url, data: data });
                    }
                );
            }
            const delay2 = new Promise((r) => setTimeout(r, 5000));
            await delay2;

            downloadBlob(extractedData, "extractedData.txt");
        } else {
            alert("There are no active tabs");
        }
    });
}

function PerformExtractLinks() {
    chrome.tabs.query({ active: true }, (tabs) => {
        var tab = tabs.filter((valtab) => !valtab.url.startsWith("chrome://"))[0];
        if (tab) {
            chrome.scripting.executeScript(
                {
                    target: { tabId: tab.id, allFrames: true },
                    func: extractLinks,
                },
                (results) => {
                    console.error(results);
                    const headings = results[0].result;

                    for (let i = 0; i < headings.length; i++) {
                        chrome.tabs.create({ url: headings[i] });
                    }

                    const textContent = headings.join("\n"); // Соединяем заголовки в одну строку с помощью символа новой строки

                    //const url = URL.createObjectURL(blob); //doesnt work in background
                    const filename = "extracted_links.txt";
                    // use BlobReader object to read Blob data
                    downloadBlob([textContent], filename);

                    return true;
                }
            );
        } else {
            alert("There are no active tabs");
        }
    });
}

function PerformExtractPage() {
    chrome.tabs.query({ active: true }, (tabs) => {
        var tab = tabs.filter((valtab) => !valtab.url.startsWith("chrome://"))[0];
        if (tab) {
            chrome.scripting.executeScript(
                {
                    target: { tabId: tab.id },
                    func: extractTBankrotData,
                },
                (results) => {
                    const data = results[0].result;
                    const filename = "tbankrotData.txt";
                    downloadBlob(data, filename);
                    //downloadTextFile(data);
                }
            );
        } else {
            alert("There are no active tabs");
        }
    });
}

function PerformExtractTBankrotData() {
    chrome.tabs.query({ active: true }, (tabs) => {
        var tab = tabs.filter((valtab) => !valtab.url.startsWith("chrome://"))[0];
        if (tab) {
            chrome.scripting.executeScript(
                {
                    target: { tabId: tab.id },
                    func: extractTBankrotData,
                },
                (results) => {
                    const data = results[0].result;
                    const filename = "tBankrotData.txt";
                    downloadBlob(data, filename);
                    //downloadTextFile(data);
                }
            );
        } else {
            alert("There are no active tabs");
        }
    });
}

async function PerformFullCycle() {
    let tbankrotData = {};
    const extractedData = [];
    const step0Tabs = await chrome.tabs.query({ active: true });
    const step0Tab = step0Tabs.filter((valtab) => !valtab.url.startsWith("chrome://"))[0];
    if (step0Tab) {
        await chrome.scripting.executeScript(
            {
                target: { tabId: step0Tab.id },
                func: extractTBankrotData,
            },
            (results) => {
                tbankrotData = results[0].result;
            }
        );
    } else {
        alert("failed to get step 0 tab");
        return;
    }
    const avitoUrl = getAvitoLinkFromKad(tbankrotData.kad);
    const step1Tab = await chrome.tabs.create({ url: avitoUrl });
    const step2Tabs = [];
    const delay = new Promise((r) => setTimeout(r, 4000));
    await delay;
    if (step1Tab) {
        let step1Results = await chrome.scripting.executeScript(
            {
                target: { tabId: step1Tab.id, allFrames: true },
                func: extractLinks,
            },
        );
        const step1Links = step1Results[0].result;

        for (let i = 0; i < step1Links.length; i++) {
            await sleep(2000);
            let tab1 = await chrome.tabs.create({ url: step1Links[i] });
            step2Tabs.push({ id: tab1.id, url: step1Links[i] });
        }

    } else {
        alert("could not get step 1 tab");
        return;
    }


    for (const step2Tab of step2Tabs) {
        const targetId = step2Tab.id;
        const targetUrl = step2Tab.url;
        const step2Results = await chrome.scripting.executeScript(
            {
                target: { tabId: targetId, allFrames: true },
                func: extractPage,
            },
        );
        const data = step2Results[0].result;
        console.log(data);
        extractedData.push({ url: targetUrl, data: data });
    }
    const final = {
        tbankrot: tbankrotData,
        properties: extractedData
    }
    downloadBlob(final, "final.txt");
    //todo finish

}

async function PerformTestRemap() {
    const data = {
        "tbankrot": {
            "properties": [
                {
                    "area": "38.1",
                    "kad": "77:09:0001008:15104",
                    "lat": "55.871367426112",
                    "lon": "37.49244977234",
                    "Вид объекта недвижимости:": "Помещение",
                    "Вид, номер и дата государственной регистрации права:": "№ 77-77-20/014/2009-478 От (Собственность)",
                    "Дата внесения кад. стимости:": "01.01.2023",
                    "Дата обновления информации:": "02.02.2024",
                    "Дата определения кад. стимости:": "20.12.2023",
                    "Дата присвоения кадастрового номера:": "27.05.2012",
                    "Кадастровая стоимость (руб):": "7247369.81",
                    "Назначение:": "Жилое помещение",
                    "Ограничение прав и обременение объекта недвижимости:": "№ 77-77-09/008/2008-587 ()\n                                                            № 77:09:0001008:15104-77/055/2021-8 (Ипотека)\n                                                            № 77:09:0001008:15104-77/055/2021-9 (Запрещение регистрации)",
                    "Форма собственности:": "Собственность (индивидуальная)",
                    "Этаж:": "5"
                }
            ],
            "title": "Квартира 38,1 кв.м, к.н. 77:09:0001008:15104, г. Москва, Ховрино, ул. Клинская, д. 18, корп. 1, кв. 66 | 6116290 - торги по банкротству на TBankrot.ru",
            "url": "https://tbankrot.ru/item?id=6116290"
        },
        "properties": [
            {
                "url": "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/dom_198_m_na_uchastke_52_sot._3541306142",
                "data": {
                    "adName": "Дом 198 м² на участке 5,2 сот.",
                    "price": "22900000",
                    "pricePerMeter": "115 657 ₽ за м²",
                    "squareField": "5.2 сот.",
                    "squareHouse": "198 м²",
                    "viewsCount": "2792 просмотра"
                }
            },
            {
                "url": "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/dom_195_m_na_uchastke_33_sot._2714409473",
                "data": {
                    "adName": "Дом 195 м² на участке 3,3 сот.",
                    "price": "17500000",
                    "pricePerMeter": "89 744 ₽ за м²",
                    "squareField": "3.3 сот.",
                    "squareHouse": "195 м²",
                    "viewsCount": "6770 просмотров"
                }
            },
            {
                "url": "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/dom_195_m_na_uchastke_33_sot._3211419936",
                "data": {
                    "adName": "Дом 195 м² на участке 3,3 сот.",
                    "price": "19800000",
                    "pricePerMeter": "101 538 ₽ за м²",
                    "squareField": "3.3 сот.",
                    "squareHouse": "195 м²",
                    "viewsCount": "1045 просмотров"
                }
            },
            {
                "url": "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/dom_195_m_na_uchastke_33_sot._4090709726",
                "data": {
                    "adName": "Дом 195 м² на участке 3,3 сот.",
                    "price": "17500000",
                    "pricePerMeter": "89 744 ₽ за м²",
                    "squareField": "3.3 сот.",
                    "squareHouse": "195 м²",
                    "viewsCount": "1029 просмотров"
                }
            },
            {
                "url": "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/dom_187_m_na_uchastke_52_sot._3848176754",
                "data": {
                    "adName": "Дом 187 м² на участке 5,2 сот.",
                    "price": "14499999",
                    "pricePerMeter": "77 540 ₽ за м²",
                    "squareField": "5.2 сот.",
                    "squareHouse": "187 м²",
                    "viewsCount": "381 просмотр"
                }
            },
            {
                "url": "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/dom_195_m_na_uchastke_33_sot._2872359453",
                "data": {
                    "adName": "Дом 195 м² на участке 3,3 сот.",
                    "price": "19300000",
                    "pricePerMeter": "98 974 ₽ за м²",
                    "squareField": "3.3 сот.",
                    "squareHouse": "195 м²",
                    "viewsCount": "6396 просмотров"
                }
            },
            {
                "url": "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/dom_373_m_na_uchastke_77_sot._3710663455",
                "data": {
                    "adName": "Дом 373 м² на участке 7,7 сот.",
                    "price": "33900000",
                    "pricePerMeter": "90 885 ₽ за м²",
                    "squareField": "7.7 сот.",
                    "squareHouse": "373 м²",
                    "viewsCount": "457 просмотров"
                }
            },
            {
                "url": "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/dom_280_m_na_uchastke_8_sot._519805852",
                "data": {
                    "adName": "Дом 280 м² на участке 8 сот.",
                    "price": "33500000",
                    "pricePerMeter": "119 643 ₽ за м²",
                    "squareField": "8 сот.",
                    "squareHouse": "280 м²",
                    "viewsCount": "13990 просмотров"
                }
            }
        ]
    }
    const remapped = remapFinalDataForPost(data);
    downloadBlob(remapped, "remap.json");
}

function extractLinks() {
    try {
        const adsFromMapPageList = document.querySelectorAll('[itemprop="url"]');
        const linkList = [];
        for (let i = 0; i < Math.min(24, adsFromMapPageList.length); i++) {
            if (i % 2 != 0) {
                const currentAd = adsFromMapPageList[i];
                const href = currentAd.getAttribute("href");
                linkList.push("https://www.avito.ru" + href);
            }
        }

        return linkList;
    } catch (ex) {
        console.error(ex);
        return ex;
    }
}

function extractPage() {
    // Попытка извлечь название объявления
    let adName = "N/A";
    const adNameElement = document.querySelector(
        ".styles-module-root-GKtmM.styles-module-root-YczkZ.styles-module-size_xxxl-MrhiK.styles-module-size_xxxl-c1c6_.stylesMarningNormal-module-root-S7NIr.stylesMarningNormal-module-header-3xl-scwbO"
    );
    if (adNameElement) {
        adName = adNameElement.textContent.trim();
    }

    // Попытка извлечь площадь участка
    let squareField = "N/A";
    index = 0;
    const squareFieldElements = document.querySelectorAll(
        ".params-paramsList__item-_2Y2O"
    );
    squareField = squareFieldElements[2].textContent.split(":")[1].trim();

    // Попытка извлечь площадь дома
    let squareHouse = "N/A";
    const squareHouseElements = document.querySelectorAll(
        ".params-paramsList__item-_2Y2O"
    );
    squareHouse = squareHouseElements[1].textContent.split(":")[1].trim();

    // Попытка извлечь цену
    let price = "N/A";
    const priceElement = document.querySelector('[itemprop="price"]');
    if (priceElement) {
        price = priceElement.getAttribute("content");
    }

    // Попытка извлечь цену за квадратный метр
    let pricePerMeter = "N/A";
    const pricePerMeterElement = document.querySelector(
        ".styles-module-root-YczkZ.styles-module-size_s-AGMw8.styles-module-size_s-_z7mI.stylesMarningNormal-module-root-S7NIr.stylesMarningNormal-module-paragraph-s-Yhr2e.styles-module-root_top-p0_50.styles-module-margin-top_8-m2Rk2"
    );
    if (pricePerMeterElement) {
        pricePerMeter = pricePerMeterElement.textContent.trim();
    }

    // Попытка извлечь изображение телефона
    // let phone = "N/A";
    // const imageButton = document.querySelector('[data-marker="item-phone-button/card"]');
    // if (imageButton) {
    //     imageButton.click();
    //     const image = document.querySelector('.styles-module-root-SfSd4.styles-module-margin-top_none-urOXk.styles-module-margin-bottom_none-YEOJI.styles-module-root_align_center-Oadab');
    //     if (image) {
    //         phone = image.src;
    //     }
    // }

    // Попытка извлечь количество просмотров
    let viewsCount = "N/A";
    const viewsCountElement = document.querySelector(
        '[data-marker="item-view/total-views"]'
    );
    if (viewsCountElement) {
        viewsCount = viewsCountElement.textContent.trim();
    }

    // Создание объекта данных для отправки в фоновый скрипт
    const data = {
        adName: adName,
        squareField: squareField,
        squareHouse: squareHouse,
        price: price,
        pricePerMeter: pricePerMeter,
        viewsCount: viewsCount,
    };

    return data;
}

function extractTBankrotData() {
    const mapItemInfoStringsToDataKeys = {
        "Кадастровый номер:": "kad",
        "Площадь, кв.м:": "area",
        "Адрес (местоположение):": "address",
    };
    const propertiesInLot = [];
    let rootBlock = document.getElementById("real_estate_info");
    for (const property_block of rootBlock.children) {
        if (property_block.classList.contains("kadastr__maps")) continue;
        const currentProperty = {
            lat: property_block.getAttribute("data-lat"),
            lon: property_block.getAttribute("data-lon"),
        };

        for (const propertyTab of property_block.children) {
            if (propertyTab.id.startsWith("RosreestrInfo_")) {
                for (const itemInfo of propertyTab.children[0].children) {
                    let selectedKey = itemInfo.firstChild.textContent.trim();
                    for (const [key, value] of Object.entries(
                        mapItemInfoStringsToDataKeys
                    )) {
                        if (selectedKey.startsWith(key)) {
                            selectedKey = value;
                        }
                    }
                    currentProperty[selectedKey] =
                        itemInfo.children[0].textContent.trim();
                }
            }
        }
        propertiesInLot.push(currentProperty);
    }
    return {
        url: document.URL,
        title: document.title,
        properties: propertiesInLot,
    };
}

function remapFinalDataForPost(final) {
    const rx_takeOnlyNumbers = /\d+(\.\d+)?/;
    const result = {};
    result.name = final.tbankrot.title;
    result.link = final.tbankrot.url;
    result.kad = final.tbankrot.properties[0].kad; result.comment = "";
    result.currentBid = -1; //NOTIMPL
    result.comment = "";
    result.status = "PROGRESS";
    result.dateStart = new Date();
    result.properties = final.properties.map((prop) => {
        const propResult = {};
        propResult.name = prop.data.adName;
        propResult.address = "NOTIMPL";
        propResult.link = prop.url;
        propResult.price = Number.parseFloat(prop.data.price);
        propResult.propertyArea = Number.parseFloat(rx_takeOnlyNumbers.exec(prop.data.squareField)[0]) * 100;
        propResult.indoorsArea = Number.parseFloat(rx_takeOnlyNumbers.exec(prop.data.squareHouse.replace(" ", ""))[0]);
        propResult.comment = "";
        return propResult;
    });
    return result;
}

function getAvitoLinkFromKad(kad) {
    //todo this is a placeholder
    return "https://www.avito.ru/novorossiysk/doma_dachi_kottedzhi/prodam/dom-ASgBAQICAUSUA9AQAUDYCBTOWQ?context=H4sIAAAAAAAA_0q0MrSqLraysFJKK8rPDUhMT1WyLrYytlLKTSxQsq4FBAAA__8Xe4TEHwAAAA&localPriority=0&map=eyJzZWFyY2hBcmVhIjp7ImxhdEJvdHRvbSI6NDQuNjY2MjA0NzcxMTIxMTQsImxhdFRvcCI6NDQuNjY5MjUzMDc2MTA2MzQ0LCJsb25MZWZ0IjozNy43ODIyOTQ3NjYzNTU1MiwibG9uUmlnaHQiOjM3Ljc4OTkxMjIzOTk1NzgxNX0sInpvb20iOjE4fQ%3D%3D";
}

function downloadBlob(data, filename) {
    const blob = new Blob([JSON.stringify(data, null, 2)], {
        type: "text/plain",
    });
    const reader = new FileReader();
    reader.onload = () => {
        const buffer = reader.result;
        const blobUrl = `data:${blob.type};base64,${btoa(
            new Uint8Array(buffer).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                ""
            )
        )}`;
        chrome.downloads.download(
            {
                url: blobUrl,
                filename: filename,
                saveAs: true,
                conflictAction: "uniquify",
            },
            () => { }
        );
    };
    reader.readAsArrayBuffer(blob);
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// function downloadTextFile(data) {
//     const content = JSON.stringify(data, null, 2); // Преобразуем объект data в строку JSON
//     const blob = new Blob([content], { type: 'text/plain' }); // Создаем объект Blob с текстовым содержимым
//     const url = URL.createObjectURL(blob); // Создаем URL для объекта Blob

//     const filename = 'data.txt'; // Имя файла

//     // Создаем ссылку для скачивания файла
//     const link = document.createElement('a');
//     link.href = url;
//     link.download = filename;

//     // Добавляем ссылку на страницу и эмулируем клик по ней для скачивания файла
//     document.body.appendChild(link);
//     link.click();

//     // Удаляем ссылку после скачивания файла
//     document.body.removeChild(link);
// }

// chrome.action.onClicked.addListener((tab) => {
//     alert("Loaded");
// });

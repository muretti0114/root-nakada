const serverAddr = getServerAddr();
let shelter = {};
let marker = {};
let infoWindow = {};

let centerMarker = null;

function getCurrentPosition(options) {
    return new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject, options);
    })
}


function getServerAddr() {
    let e = location.href;
    let path = e.substr(0, e.lastIndexOf("/"));

    return path;
}

function createShelter() {
    let endpoint = serverAddr + "/admin/shelters/create/";

    let id = $('#create_id').val();
    let name = $('#create_name').val();
    let address = $('#create_address').val();
    let lng = $('#create_lng').val();
    let lat = $('#create_lat').val();
    let area = $('#create_area').val();
    let max_families = $('#create_max_families').val();
    let contact_phone = $('#create_contact_phone').val();
    let note = $('#create_note').val();
    let local_government_code = $('#create_local_government_code').val();

    let data = {
        "id": id,
        "name": name,
        "address": address,
        "lng": lng,
        "lat": lat,
        "area": area,
        "max_families": max_families,
        "contact_phone": contact_phone,
        "note": note,
        "local_government_code": local_government_code
    };

    /* 
        contentTypeなし，JSON.stringfyあり -> 415
        contentTypeあり，JSON.stringfyなし -> 400
        どっちもなし -> 415
        このデータの渡し方だとnoteとかcontact_phoneにnullではなく，""が渡される
    */
    $.ajax({
        type: "POST",
        url: endpoint,
        data: JSON.stringify(data),
        /*{
            id: id,
            name: name,
            address: address,
            lng: lng,
            lat: lat,
            area: area,
            max_families: max_families,
            contact_phone: contact_phone,
            note: note,
            local_government_code: local_government_code
        },
        */
        contentType: 'application/json',
        dataType: "JSON"
    }).done(function(data, dataType) {
        showMessage("避難所：" + $('#create_id').val() + " を作成しました");
        clearCreateForm();
    }).fail(function(xhr) {
        let res = $.parseJSON(xhr.responseText);

        console.log(res);

        switch (res.code) {
            case "E10":
                showMessage("避難所ID：" + $('#create_id').val() + " は既に存在します");
                break;

            case "E11":
                showMessage("入力項目に誤りがあります");
                break;
        }
    })
}

function getAllShelters() {
    let endpoint = "https://wsapp.cs.kobe-u.ac.jp/shelter_navi/shelters";
    let id = $('#shelter_id').val();
    $('#shelters td').remove();

    $.ajax({
        type: "GET",
        url: endpoint + "/" + id,
        dataType: "JSON"
    }).done(function(data, dataType) {
        $.each(data.result, function(index, shelter) {
            let id = shelter.id;
            let name = shelter.name;
            let address = shelter.address;
            let lng = shelter.lng;
            let lat = shelter.lat;
            let area = shelter.area;
            let maxFamilies = shelter.max_families;
            let contactPhone = shelter.contact_phone;
            let note = shelter.note;
            let localGovernmentCode = shelter.local_government_code;

            $('#shelters').append('<tr><td>' + id + '</td><td>' + name + '</td><td>' + address + '</td><td>' + lng + '</td><td>' + lat + '</td><td>' + area + '</td><td>' + maxFamilies + '</td><td>' + contactPhone + '</td><td>' + note + '</td><td>' + localGovernmentCode + '</td></tr>');


        });
    }).fail(function(xhr) {
        console.log(xhr);
        showMessage("避難所が存在しません");
    });
}

function getShelters() {
    let endpoint = "https://wsapp.cs.kobe-u.ac.jp/shelter_navi/shelters";
    let id = $('#shelter_id').val();

    $('result table').remove();

    $.ajax({
        type: "GET",
        url: endpoint + "/" + id,
        dataType: "JSON",
    }).done(function(data, dataType) {
        shelter = data.result;

        let id = shelter.id;
        let name = shelter.name;
        let address = shelter.address;
        lng = shelter.lng;
        lat = shelter.lat;
        let area = shelter.area;
        let maxFamilies = shelter.max_families;
        let contactPhone = shelter.contact_phone;
        let note = shelter.note;
        let localGovernmentCode = shelter.local_government_code;


        $('result').append('<table><tr><th scope="col">避難所ID</th><th scope="col">施設名</th><th scope="col">住所</th><th scope="col">経度</th><th scope="col">緯度</th><th scope="col">地域</th><th scope="col">収容人数</th><th scope="col">電話番号</th><th scope="col">備考</th><th scope="col">地方自治体コード</th></tr><tr><td>' + id + '</td><td>' + name + '</td><td>' + address + '</td><td>' + lng + '</td><td>' + lat + '</td><td>' + area + '</td><td>' + maxFamilies + '</td><td>' + contactPhone + '</td><td>' + note + '</td><td>' + localGovernmentCode + '</td></tr></table>');
    }).fail(function(xhr) {
        console.log(xhr);
        showMessage("避難所が存在しません");
    });


}

function showMessage(message) {
    $('#resultMessage').text(message);
}

function clearCreateForm() {
    $('#create_id').val("");
    $('#create_name').val("");
    $('#crate_address').val("");
    $('#create_lng').val("");
    $('#create_lat').val("");
    $('#create_area').val("");
    $('#create_max_families').val("");
    $('#create_contact_phone').val("");
    $('#create_note').val("");
    $('#create_local_government_code').val("");
}

//地図の初期化
async function initMap() {
    let position = await getCurrentPosition();

    let latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

    const mapOptions = {
        zoom: 14,
        center: latlng,
    };
    map = new google.maps.Map(document.getElementById('gmap'), mapOptions);

    //地図の中心が変わるたびに更新する
    map.addListener("center_changed", updateMap);

    updateMap();
}


//地図の更新
function updateMap() {

    //地図の中心点
    let latlng = map.getCenter();
    $('#curLat').text(latlng.lat());
    $('#curLng').text(latlng.lng());

    //中心マーカーをいったん消す
    if (centerMarker != null) {
        centerMarker.setMap(null);
    }
    //中心マーカーを作成する
    centerMarker = new google.maps.Marker({
        map: map,
        position: latlng,
        //animation: google.maps.Animation.DROP,
        icon: {
            fillColor: "#0000FF", //塗り潰し色
            fillOpacity: 0.8, //塗り潰し透過率
            path: google.maps.SymbolPath.BACKWARD_CLOSED_ARROW, //すべての辺が閉じている後方を指す矢印
            scale: 8, //円のサイズ
            strokeColor: "#0000FF", //枠の色
            strokeWeight: 1.0 //枠の透過率
        },
    });
    //latlngを中心とした避難所をマークしていく
    addHinanjo(latlng);
}

//避難所の追加
function addHinanjo(latlng) {
    let endpoint = "https://wsapp.cs.kobe-u.ac.jp/shelter_navi/shelters";
    let data = {
        "userLng": latlng.lng(), // 経度：134.3756
        "userLat": latlng.lat(), // 緯度：34.8381
        "distance": 1.0 // 半径：1.0km
    };

    const ajaxResult = { "code": "OK", "message": null, "result": [{ "id": 2810200044, "name": "神戸大学工学部", "address": "神戸市灘区六甲台町1-1", "lng": 135.237488, "lat": 34.726994, "area": 200, "max_families": 26, "contact_phone": "078-803-6333(昼)、078-803-6361(夜)", "note": "土砂災害：○，洪水：○，津波：○", "local_government_code": 28102 }, { "id": 2810200043, "name": "神戸大学農学部", "address": "神戸市灘区六甲台町1-1", "lng": 135.233272, "lat": 34.72464, "area": 200, "max_families": 26, "contact_phone": "078-803-5921(昼)、078-803-5777(夜)", "note": "土砂災害：○，洪水：○，津波：○", "local_government_code": 28102 }, { "id": 2810200048, "name": "高羽小学校", "address": "神戸市灘区高羽町3-11-11", "lng": 135.2394699, "lat": 34.7232671, "area": 300, "max_families": 39, "contact_phone": "078-841-0541", "note": "土砂災害：○，洪水：○，津波：○", "local_government_code": 28102 }, { "id": 2810200049, "name": "親和女子高等学校・親和中学校", "address": "神戸市灘区土山町6-1", "lng": 135.242026, "lat": 34.726756, "area": 500, "max_families": 65, "contact_phone": "078-854-3800", "note": "土砂災害：○，洪水：○，津波：○", "local_government_code": 28102 }, { "id": 2810200046, "name": "神戸松蔭女子学院大学", "address": "神戸市灘区篠原伯母野山町1-2-1", "lng": 135.2295239, "lat": 34.72736, "area": 200, "max_families": 26, "contact_phone": "078-882-6121", "note": "土砂災害：○，洪水：○，津波：○", "local_government_code": 28102 }, { "id": 2810200042, "name": "鶴甲小学校", "address": "神戸市灘区鶴甲2-10-1", "lng": 135.2363925, "lat": 34.7335836, "area": 300, "max_families": 39, "contact_phone": "078-821-0444", "note": "土砂災害：△，洪水：○，津波：○ 《土砂災害時》正門が土砂災害警戒区域内にあるので注意、西門を利用すること", "local_government_code": 28102 }, { "id": 2810200051, "name": "六甲小学校", "address": "神戸市灘区八幡町4-4-1", "lng": 135.2333847, "lat": 34.7176856, "area": 300, "max_families": 39, "contact_phone": "078-881-1071", "note": "土砂災害：○，洪水：○，津波：○", "local_government_code": 28102 }, { "id": 2810200063, "name": "鷹匠中学校", "address": "神戸市灘区高徳町2-2-19", "lng": 135.2438533, "lat": 34.7201547, "area": 500, "max_families": 65, "contact_phone": "078-841-0041", "note": "土砂災害：△，洪水：○，津波：○ 《土砂災害時》土砂災害警戒区域外（高羽小学校）へ避難すること、ただし、警戒区域外への避難が困難な方や余裕のない場合は利用可", "local_government_code": 28102 }] };

    const shelters = ajaxResult.result;
    shelters.forEach((h, i) => {
        //for (const [i, h] of shelters) {
        //避難所の緯度・経度
        let pos = new google.maps.LatLng(h.lat, h.lng);
        let id = h.id;
        //h用のマーカーを作成
        marker[id] = new google.maps.Marker({ // マーカーの追加
            position: pos,
            map: map,
            title: h.name,
            icon: {
                fillColor: "#FF0000", //塗り潰し色
                fillOpacity: 0.8, //塗り潰し透過率
                path: google.maps.SymbolPath.CIRCLE, //円を指定
                scale: 16, //円のサイズ
                strokeColor: "#FF0000", //枠の色
                strokeWeight: 1.0 //枠の透過率
            },
            label: {
                text: String(i + 1), //ラベル文字
                color: '#FFFFFF', //文字の色
                fontSize: '20px' //文字のサイズ
            }



        });
        //h用の情報ウィンドウ（吹き出し）を作成
        infoWindow[id] = new google.maps.InfoWindow({ // 吹き出しの追加
            content: '<div class="info">' + h.name + ' (' + h.address + ') ' + '</div>' // 吹き出しに表示する内容
        });
        console.log("adding " + h.name);
        //マーカーとウィンドウのバインド（別関数でやらないとダメ．クリック時のidが変わるので）
        bind_info(id);
    });

    /*-------------
    $.ajax({
        type: "GET",
        url: endpoint + "/search/distance",
        data: data,
        contentType: "application/json",
        dataType: "JSON",
    }).done(
        function(data, dataType) {
            console.log(data);
            const shelters = data.result;
            for (const h of shelters) {
                //避難所の緯度・経度
                let pos = new google.maps.LatLng(h.lat, h.lng);
                let id = h.id;
                //h用のマーカーを作成
                marker[id] = new google.maps.Marker({ // マーカーの追加
                    position: pos,
                    map: map,
                    title: h.name
                });
                //h用の情報ウィンドウ（吹き出し）を作成
                infoWindow[id] = new google.maps.InfoWindow({ // 吹き出しの追加
                    content: '<div class="info">' + h.name + ' (' + h.address + ') ' + '</div>' // 吹き出しに表示する内容
                });
                console.log("adding " + h.name);
                //マーカーとウィンドウのバインド（別関数でやらないとダメ．クリック時のidが変わるので）
                bind_info(id);
            }
        }
    ).fail(function(xhr) {
        console.log(xhr);
        showMessage("避難所が存在しません");
    });
    */
}
//マーカークリック時に情報ウィンドウを開く
function bind_info(id) {
    marker[id].addListener('click', function() { // マーカーをクリックしたとき
        for (const m in marker) {
            //他のすべてのマーカーの吹き出しを閉じて，
            infoWindow[m].close(map, marker[m]);
        }
        infoWindow[id].open(map, marker[id]); // 吹き出しの表示
    });
}



function f() {
    getShelters()


}
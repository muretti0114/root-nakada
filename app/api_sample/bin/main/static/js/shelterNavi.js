const serverAddr = getServerAddr();
let shelter = {};
let marker = {};
let infoWindow = {};

let centerMarker = null;

//ブラウザから現在の緯度経度を取得する．Promiseオブジェクトを返す
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
    }).done(function (data, dataType) {
        showMessage("避難所：" + $('#create_id').val() + " を作成しました");
        clearCreateForm();
    }).fail(function (xhr) {
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
    }).done(function (data, dataType) {
        $.each(data.result, function (index, shelter) {
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
    }).fail(function (xhr) {
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
    }).done(function (data, dataType) {
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
    }).fail(function (xhr) {
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
    map.addListener("dragend", updateMap);

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
    //マーカー全消し
    clear_all_markers();
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
    $.ajax({
        type: "GET",
        url: endpoint + "/search/distance",
        data: data,
        contentType: "application/json",
        dataType: "JSON",
    }).done(
        function (data, dataType) {
            console.log(data);
            const shelters = data.result;
            $('#shelterlist').append('<tr><th>#</th><th>避難所名</th><th>住所</th><th>混雑状況</th></tr>');
            shelters.forEach((h, i) => {

                //for (const [i, h] of shelters) {
                //避難所の緯度・経度
                let pos = new google.maps.LatLng(h.lat, h.lng);
                let id = h.id;
                //ここから中田がコード追加
                let name = h.name;
                let address = h.address;
                let lng = h.lng;
                let lat = h.lat;
                let area = h.area;
                let maxFamilies = h.max_families;
                let contactPhone = h.contact_phone;
                let note = h.note;
                let localGovernmentCode = h.local_government_code;

                let pinColor;
                if (maxFamilies < 30) {
                    pinColor = "#00FF00";
                } else if (30<= maxFamilies && maxFamilies < 70) {
                    pinColor = "yellow";
                } else if (70 <= maxFamilies) {
                    pinColor = "#FF0000";
                }
                $('#shelterlist').append('<tr><td>' + String(i+1) + '</td><td>' + name + '</td><td>' + address + '</td><td>' + maxFamilies + '</td></tr>');
                /*<table><tr><th scope="col">避難所ID</th><th scope="col">施設名</th><th scope="col">住所</th><th scope="col">経度</th><th scope="col">緯度</th><th scope="col">地域</th><th scope="col">収容人数</th><th scope="col">電話番号</th><th scope="col">備考</th><th scope="col">地方自治体コード</th></tr></table>*/
                //ここまで
                //h用のマーカーを作成
                marker[id] = new google.maps.Marker({ // マーカーの追加
                    position: pos,
                    map: map,
                    title: h.name,
                    icon: {
                        fillColor: pinColor, //塗り潰し色
                        fillOpacity: 0.8, //塗り潰し透過率
                        path: google.maps.SymbolPath.CIRCLE, //円を指定
                        scale: 16, //円のサイズ
                        strokeColor: pinColor, //枠の色
                        strokeWeight: 1.0 //枠の透過率
                    },
                    label: {
                        text: String(i + 1), //ラベル文字
                        color: '#000000', //文字の色
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
        }
    ).fail(function (xhr) {
        console.log(xhr);
        showMessage("避難所が存在しません");
    });
}
//マーカークリック時に情報ウィンドウを開く
function bind_info(id) {
    marker[id].addListener('click', function () { // マーカーをクリックしたとき
        for (const m in marker) {
            //他のすべてのマーカーの吹き出しを閉じて，
            infoWindow[m].close(map, marker[m]);
        }
        infoWindow[id].open(map, marker[id]); // 吹き出しの表示
    });
}

function clear_all_markers() {
    //中心マーカーをいったん消す
    for (const m in marker) {
        marker[m].setMap(null);
        marker[m] = null;
    }
    marker = {};
    //中田追加
    $('#shelterlist tr').remove();
    //ここまで
}


function f() {
    getShelters()


}
